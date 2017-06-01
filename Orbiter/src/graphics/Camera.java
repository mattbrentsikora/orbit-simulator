package graphics;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;
import java.awt.image.BufferedImage;


public class Camera extends GameObject 
{
	private Component parentComponent;
	private BufferedImage backBuffer;
	
	private Point2D.Float previousPoint;
	private Point2D.Float srcPoint;
	private Point2D.Float destPoint;
	
	private AffineTransform cameraTransformation;

	private GameObject followObject;
	
	public Camera(Component component, BufferedImage backBuffer)
	{
		this.backBuffer = backBuffer;
		parentComponent = component;		
		
		previousPoint = new Point2D.Float();
		srcPoint = new Point2D.Float();
		destPoint = new Point2D.Float();
		
		cameraTransformation = new AffineTransform();
	}
	
	public void setBackBuffer(BufferedImage backBuffer)
	{
		this.backBuffer = backBuffer;
	}
	
	@Override
	public void update()
	{
		super.update();		
		
		if(followObject != null)
		{
			position.x = -followObject.getPosition().x;
			position.y = -followObject.getPosition().y;
			rotation = -followObject.getRotation();
		}
		
	}
	
	public void setFollowObject(GameObject o)
	{
		followObject = o;
	}
	public GameObject getFollowObject()
	{
		return followObject;
	}
	public void nextFollowObject()
	{
		if(followObject == null && subObjects.size() != 0)
		{
			followObject = subObjects.get(0);
		}
		else
		{
			int i = subObjects.indexOf(followObject);
			i++;
			
			if(i < subObjects.size())
			{
				followObject = subObjects.get(i);
			}
			else
			{
				followObject = null;
			}
		}
	}
	
	
	@Override
	public void render(Graphics2D g2d)
	{		
		cameraTransformation.setToIdentity();
		cameraTransformation.translate(position.x, position.y);
		cameraTransformation.scale(scale,scale);
		cameraTransformation.rotate(rotation);

		
		g2d.translate(backBuffer.getWidth()/2.0f, backBuffer.getHeight()/2.0f);
		g2d.scale(scale, scale);				
		g2d.rotate(rotation);
		g2d.translate(position.x, position.y);
	
		currentTransformation = g2d.getTransform();
		
		for(GameObject go: subObjects)
		{
			go.render(g2d);
			g2d.setTransform(currentTransformation);
		}
	}
	
	public void addMouseDraggedEvent(MouseEvent event)
	{
		GraphicMessage gm = new GraphicMessage(event, GraphicMessage.MESSAGE_TYPE.MOUSEDRAGGED);
		try 
		{
			messages.put(gm);
		} 
		catch (InterruptedException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addMouseWheelMoved(MouseEvent event)
	{
		GraphicMessage gm = new GraphicMessage(event, GraphicMessage.MESSAGE_TYPE.WHEELMOVED);
		try 
		{
			messages.put(gm);
		} 
		catch (InterruptedException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addMouseMovedEvent(MouseEvent event)
	{
		GraphicMessage gm = new GraphicMessage(event, GraphicMessage.MESSAGE_TYPE.MOUSEMOVED);
		try 
		{
			messages.put(gm);
		} 
		catch (InterruptedException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void addMouseDownEvent(MouseEvent event)
	{
		GraphicMessage gm = new GraphicMessage(event, GraphicMessage.MESSAGE_TYPE.MOUSECLICKED);
		try 
		{
			messages.put(gm);
		} 
		catch (InterruptedException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Point2D.Float translatePoint(MouseEvent e)
	{
		
		srcPoint.x = e.getX();
		srcPoint.y = e.getY();
		
		//this.currentTransform.transform(src, dest);
		
		try
		{
			this.cameraTransformation.inverseTransform(srcPoint, destPoint);
		} 
		catch (NoninvertibleTransformException e1)
		{
			e1.printStackTrace();
		}
			
		return destPoint;
	}
	
	@Override
	protected void handleMessage(Object o)
	{
		super.handleMessage(o);
		
		if(o instanceof GraphicMessage)
		{
			GraphicMessage temp = (GraphicMessage)o;
				
			MouseEvent e = null;
						
			if(temp.object instanceof MouseEvent)
			{
				e = (MouseEvent)temp.object;
				
				float xx = (float)(this.parentComponent.getWidth())/(float)(backBuffer.getWidth());
				float yy = (float)(this.parentComponent.getHeight())/(float)(backBuffer.getHeight());
				
				destPoint.x = e.getX()/super.getScale()/xx;
				destPoint.y = e.getY()/super.getScale()/yy;		
			}
			
			if(temp.type == GraphicMessage.MESSAGE_TYPE.MOUSEDRAGGED)
			{			
				super.position.x -= (previousPoint.x - destPoint.x);
				super.position.y -= (previousPoint.y - destPoint.y);
				this.previousPoint.x = destPoint.x;
				this.previousPoint.y = destPoint.y;
				
			}
			if(temp.type == GraphicMessage.MESSAGE_TYPE.MOUSEMOVED)
			{		
				this.previousPoint.x = destPoint.x;
				this.previousPoint.y = destPoint.y;				
			}
			if(temp.type == GraphicMessage.MESSAGE_TYPE.MOUSECLICKED)
			{
				this.previousPoint.x = destPoint.x;
				this.previousPoint.y = destPoint.y;	
			}
			
			if(temp.type == GraphicMessage.MESSAGE_TYPE.WHEELMOVED)
			{
				MouseWheelEvent ee = (MouseWheelEvent)temp.object;
				
				if(ee.getWheelRotation() > 0)
					scale *= 1.1f;
				else
					scale /= 1.1f;
			}
		}
	}
}
