package mainclasses;

import input.InputMessage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.LinkedList;

import graphics.GameObject;
import graphics.GamePanel;

import javax.swing.JFrame;


/**
 * Orbiter
 * @author Matthew Sikora
 * 20170531
 * 
 * This application simulates objects orbiting eachother. This class is the primary main class to launch to get the simulation up.
 *
 */
public class Orbiter extends JFrame implements KeyListener
{
	private GamePanel gamePanel;
	private LinkedList<MassObject> masses;
	private SpaceShip spaceShip;
	
	public Orbiter()
	{
		this.setSize(500,500);
		gamePanel = new GamePanel(new Dimension(500,500));
		gamePanel.setBackGroundColor(Color.BLACK);
		
		this.add(gamePanel);
		
		
		masses = new LinkedList<MassObject>();
				
		spaceShip = new SpaceShip(masses);
		spaceShip.getPosition().x = 0f;
		//spaceShip.getVelocityPosition().x = 100f;
		spaceShip.getPosition().y = 0f;
		spaceShip.setMass(1f);
		
		masses.add(spaceShip);
		gamePanel.getCamera().addSubObject(spaceShip);
		
		gamePanel.getCamera().setFollowObject(spaceShip);
		
		
		this.addMouseListener(gamePanel);
		this.addMouseMotionListener(gamePanel);
		this.addMouseWheelListener(gamePanel);
		this.addWindowListener(gamePanel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.addKeyListener(this);
	}

	public MassObject addNewMass()
	{
		MassObject mo = new MassObject(masses);
		gamePanel.getCamera().addSubObject(mo);
		masses.add(mo);
		
		
		
		return mo;
	}
	
	public static void main(String[] args)
	{
		Orbiter o = new Orbiter();
		
		o.setLocation(new Point(0,0)); //Sets location on the screen
		
		Earth e = new Earth(o.masses);
		o.gamePanel.getCamera().addSubObject(e);
		o.masses.add(e);
		
		o.spaceShip.getPosition().y = 11530;
		o.spaceShip.createOrbitAroundObject(e);
		
		
		
		/*float u = MassObject.G * mo1.mass;
		float dx = mo1.getPosition().x - o.spaceShip.getPosition().x;
		float dy = mo1.getPosition().y - o.spaceShip.getPosition().y;
		float r = (float) Math.sqrt((dx*dx) + (dy*dy));
		
		float v = (float) Math.sqrt(u*Math.abs((2/r)-(1/r)));
		o.spaceShip.getVelocityPosition().x = v;
		
		

		MassObject mo2 = o.addNewMass();
		
		mo2.getPosition().y = -125100f;
		mo2.getPosition().x = -0f;
		mo2.setColor(Color.YELLOW);
		mo2.setMass(400f);
		mo2.setRadius(40000f);
		
		
		mo1.createOrbitAroundObject(mo2);		
		o.spaceShip.createOrbitAroundObject(mo1);
		
		//o.spaceShip.getVelocityPosition().x += mo1.getVelocityPosition().x;
		//o.spaceShip.getVelocityPosition().y += mo1.getVelocityPosition().y;
		
	
		MassObject mo3 = o.addNewMass();
		mo3.getPosition().y = 75000;
		mo3.getPosition().x = 0f;
		mo3.setMass(60f);
		mo3.setRadius(4000);
		mo3.setColor(Color.GRAY);
		
		mo3.createOrbitAroundObject(mo1);
		
		MassObject mo4 = o.addNewMass();
		mo4.getPosition().y = -325100-425000f;
		mo4.getPosition().x = 0f;
		mo4.setMass(80f);
		mo4.setRadius(7000);
		mo4.setColor(Color.RED);
		mo4.createOrbitAroundObject(mo2);*/
			
	}

	@Override
	public void keyPressed(KeyEvent arg0) 
	{	
		spaceShip.keyDown(arg0);
		
		if(arg0.getKeyCode() == arg0.VK_SPACE)
		{
			this.gamePanel.getCamera().nextFollowObject();
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) 
	{
		spaceShip.keyUp(arg0);
	}

	@Override
	public void keyTyped(KeyEvent arg0) 
	{
		
	}
}
