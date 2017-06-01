package graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable, KeyListener, WindowListener, MouseListener, MouseMotionListener, MouseWheelListener
{
	/**
	 * Loop variables. Thread that executes to update the graphics
	 * and a boolean determining if it should continue to run or not.
	 * Setting the 'running' boolean to false will kill the thread.
	 */
	private Thread gameLoop;
	private boolean runningLoop;
	private boolean pauseLoop;
	
	/**
	 * Graphic variables
	 */
	protected BufferedImage backBuffer;
	protected Graphics2D g2d;
	protected static AffineTransform identity;
	
	/**
	 * Single GameObject called world that manages everything else
	 */
	protected GameObject world;
	protected Camera camera;
	
	/**
	 * Basic input handlers
	 */
	protected Point2D.Float previousMousePoint;
	
	static 
	{
		identity = new AffineTransform();
		identity.setToIdentity();
	}
	
	public GamePanel(Dimension resolution)
	{
		backBuffer = new BufferedImage(resolution.width, resolution.height, BufferedImage.TYPE_INT_ARGB);
		g2d = backBuffer.createGraphics();
		g2d.setBackground(Color.LIGHT_GRAY);
		
		world = new GameObject();
		camera = new Camera(this, backBuffer);
		world.addSubObject(camera);
		
		previousMousePoint = new Point2D.Float();
		
		gameLoop = new Thread(this);
		runningLoop = true;
		pauseLoop = false;
		gameLoop.start();
	}
	
	@Override
	public void run() 
	{
		while(runningLoop)
		{
			if(!pauseLoop)
			{
				g2d.setTransform(identity);
				g2d.clearRect(0, 0, backBuffer.getWidth(), backBuffer.getHeight());
				
				world.update();
				world.render(g2d);
				
				super.repaint();
				
				try
				{
					Thread.sleep(30);
					
				}
				catch(InterruptedException e)
				{
					e.printStackTrace();
				}			
			}
		}
	}
	
	public void setBackGroundColor(Color color)
	{
		g2d.setBackground(color);
	}
	
	@Override
	public void paint(Graphics g)
	{
		g.drawImage(backBuffer, 0, 0, getWidth(), getHeight(), 0, 0, backBuffer.getWidth(), backBuffer.getHeight(), null);
	}
	
	public Graphics2D getG2D()
	{
		return g2d;
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) 
	{
		camera.addMouseWheelMoved(arg0);
	}
	
	@Override
	public void mouseDragged(MouseEvent arg0) 
	{
		camera.addMouseDraggedEvent(arg0);
	}
	
	
	@Override
	public void mouseMoved(MouseEvent arg0) 
	{
		camera.addMouseMovedEvent(arg0);
	}
	@Override
	public void mouseClicked(MouseEvent arg0) 
	{
	
	}
	@Override
	public void mouseEntered(MouseEvent arg0) 
	{
	
	}
	@Override
	public void mouseExited(MouseEvent arg0) 
	{
	
	}
	@Override
	public void mousePressed(MouseEvent arg0) 
	{
		camera.addMouseDownEvent(arg0);	
	}
	@Override
	public void mouseReleased(MouseEvent arg0) 
	{
	
	}
	@Override
	public void windowActivated(WindowEvent arg0) 
	{
		
	}
	@Override
	public void windowClosed(WindowEvent arg0) 
	{
		
	}
	@Override
	public void windowClosing(WindowEvent arg0) 
	{
		runningLoop = false;
	}
	@Override
	public void windowDeactivated(WindowEvent arg0) 
	{
		
	}
	@Override
	public void windowDeiconified(WindowEvent arg0) 
	{
		
	}
	@Override
	public void windowIconified(WindowEvent arg0) 
	{
		
	}
	@Override
	public void windowOpened(WindowEvent arg0) 
	{
		
	}
	@Override
	public void keyPressed(KeyEvent arg0) 
	{
		
	}
	@Override
	public void keyReleased(KeyEvent arg0) 
	{
	
	}
	@Override
	public void keyTyped(KeyEvent arg0) 
	{
		
	}
	
	public GameObject getWorld()
	{
		return world;
	}
	public Camera getCamera()
	{
		return camera;
	}
}
