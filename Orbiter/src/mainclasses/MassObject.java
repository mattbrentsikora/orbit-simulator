package mainclasses;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.LinkedList;

import graphics.GameObject;

public class MassObject extends GameObject 
{
	protected Color color;
	protected float radius; 
	protected float mass;
	protected LinkedList<MassObject> masses;
	protected LinkedList<Force> forces;
	
	private float netYForces;
	private float netXForces;
	
	public static float G = 180000f;
	
	public MassObject(LinkedList<MassObject> massObjects)
	{
		masses = massObjects;
		color = Color.YELLOW;
		radius = 15;
		mass = 1.0f;
		forces = new LinkedList<Force>();
	}
	
	
	public void createOrbitAroundObject(MassObject mo2)                                        
	{
		float u = MassObject.G * mo2.mass;
		float dx = mo2.getPosition().x - getPosition().x;
		float dy = mo2.getPosition().y - getPosition().y;
		
		float r = (float) Math.sqrt((dx*dx) + (dy*dy));
		float v = (float) Math.sqrt(u*Math.abs((2/r)-(1/r)));
		
		getVelocityPosition().y = dx*(v/r)+mo2.getVelocityPosition().y;
		getVelocityPosition().x = dy*(v/r)+mo2.getVelocityPosition().x;
		
	}
	
	public void update()
	{
		for(MassObject o: masses)
		{
			if(o != this)
			{
				float dx = getPosition().x - o.getPosition().x;
				float dy = getPosition().y - o.getPosition().y;
				float ddx = dx*dx;
				float ddy = dy*dy;
				float rad = (float)Math.sqrt(ddx+ddy);
				
				float Fg = G*o.getMass()*mass/(rad*rad);
				
				Force f = new Force();
				f.magnitude = Fg;
				
				float t1 = (float) Math.atan(dx/dy);
				t1-=Math.PI;
				
				if(o.getPosition().x > position.x)
				{
					if(o.getPosition().y > position.y)
					{
						t1 += Math.PI;
					}
					else
					{
						
					}
				}
				else
				{
					if(o.getPosition().y > position.y)
					{
						t1 += Math.PI;
					}
					else
					{
						t1 += 2*Math.PI;
					}
				}				
				f.direction = t1;
				forces.add(f);	
				
				if(collisionTest(o))
				{
					collision(o,f);				
				}
			}	
		}
		
		netYForces = 0.0f;
		netXForces = 0.0f;
		while(!forces.isEmpty())
		{
			Force f = forces.remove();
		//	System.out.println(this+" "+f.magnitude+" "+f.direction);
			f.magnitude = f.magnitude/mass;
			netXForces += (float) (f.magnitude*Math.sin(f.direction));
			netYForces += (float) (f.magnitude*Math.cos(f.direction));
		}
		
		
		this.accelerationPosition.x = netXForces;
		this.accelerationPosition.y = netYForces;
			
		super.update();		
	}
	
	public void collision(MassObject o, Force f)
	{
		Force f2 = new Force();
		f2.magnitude = f.magnitude;
		f2.direction = (float) (Math.PI+f.direction);
		forces.add(f2);
		
		if(o.handleCollision(this, f))
		{
			velocityPosition.x = ((mass*velocityPosition.x) + (o.getMass()*o.getVelocityPosition().x))/(mass+o.getMass());
			velocityPosition.y = ((mass*velocityPosition.y) + (o.getMass()*o.getVelocityPosition().y))/(mass+o.getMass());	
		}
	}
	
	public boolean handleCollision(MassObject t,Force f)
	{
		return true;
	}
	
	/*@Override
	public void update()
	{
		
		netYForces = 0.0f;
		netXForces = 0.0f;
		for(MassObject o: masses)
		{
			if(o != this)
			{
				float dx = getPosition().x - o.getPosition().x;
				float dy = getPosition().y - o.getPosition().y;
				float ddx = dx*dx;
				float ddy = dy*dy;
				float radius = (float)Math.sqrt(ddx+ddy);
				
				float Fg = G*o.getMass()*mass/radius;
				float a = Fg/mass;
				
				netXForces -= a*dx/radius;
				netYForces -= a*dy/radius;
				
				if(collisionTest(o))
				{
					netXForces += a*dx/radius;
					netYForces += a*dy/radius;
				}
			}
			
			
			
		}
		
		while(!forces.isEmpty())
		{
			Force f = forces.remove();
			f.magnitude = f.magnitude/mass;
			netXForces += (float) (f.magnitude*Math.sin(f.direction));
			netYForces -= (float) (f.magnitude*Math.cos(f.direction));
		}
		
		
		this.accelerationPosition.x = netXForces;
		this.accelerationPosition.y = netYForces;
		
		
		
		super.update();		
	}*/
	
	public boolean collisionTest(MassObject ob2)
	{
		float dx = position.x-ob2.getPosition().x;
		float dy = position.y-ob2.getPosition().y;
		float distance = (float) Math.sqrt((dx*dx)+(dy*dy));
		
		if(distance > radius+ob2.getRadius())
			return false;
		
		return true;
	}
	
	public float getMass()
	{
		return this.mass;
	}
	
	public void setMass(float m)
	{
		this.mass = m;
	}
	
	public void setColor(Color c)
	{
		color = c;
	}
	
	public void setRadius(float r)
	{
		radius = r;
	}
	
	public float getRadius()
	{
		return radius;
	}
	
	@Override
	public void render(Graphics2D g2d)
	{
		super.render(g2d);
		
		g2d.setColor(color);
		g2d.fillOval((int)(-radius), (int)(-radius), (int)(radius*2), (int)(radius*2));
	}
}
