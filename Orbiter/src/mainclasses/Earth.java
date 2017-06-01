package mainclasses;

import graphics.GameObject.Force;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.LinkedList;

public class Earth extends MassObject 
{
	//protected MassObject core;
	
	
	public Earth(LinkedList<MassObject> massObjects) 
	{
		super(massObjects);
		//core = new MassObject(massObjects);
		

		this.setColor(Color.BLUE);
		this.setRadius(11000f);
		this.setMass(100f);
		
		//core.setColor(Color.GREEN);
		//core.setRadius(10000f);
		//core.setMass(0f);		
		//core.setPosition(this.position);
	}
	
	/*@Override
	public void collision(MassObject o, Force f)
	{
		if(!core.collisionTest(o))
		{
			Force f2 = new Force();
			f2.magnitude = f.magnitude/100;
			f2.direction = (float) (Math.PI+f.direction);
			forces.add(f2);
		}
		else
		{
			super.collision(o, f);
		}
		//velocityPosition.x = ((mass*velocityPosition.x) + (o.getMass()*o.getVelocityPosition().x))/(mass+o.getMass());
		//velocityPosition.y = ((mass*velocityPosition.y) + (o.getMass()*o.getVelocityPosition().y))/(mass+o.getMass());		
	}*/
	
	
	@Override
	public boolean handleCollision(MassObject t,Force f)
	{
		return true;
	}
	
	@Override
	public void render(Graphics2D g2d)
	{
		super.render(g2d);
		//core.render(g2d);
	}
}
