package mainclasses;

import graphics.GameObject;
import graphics.GraphicMessage;

import input.InputControl;
import input.InputListener;
import input.InputMessage;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

public class SpaceShip extends MassObject implements InputListener
{
	private float boostX;
	private float boostY;
	
	private InputControl inputControl;
	
	
	private boolean key_up;
	private boolean key_down;
	private boolean key_left;
	private boolean key_right;
	
	public SpaceShip(LinkedList<MassObject> massObjects) 
	{
		super(massObjects);
		radius = 100f;
		inputControl = new InputControl();
		height = 1.0f;
	}

	public void render(Graphics2D g2d)
	{
		super.render(g2d);
		
		g2d.setColor(Color.BLUE);
		g2d.drawLine(0, 0, 0, -50);
		g2d.drawLine(1,0,1,-50);
		g2d.drawLine(-1,0,-1,-50);
		g2d.drawLine(0,-50,10,-40);
		g2d.drawLine(0,-50,-10,-40);
	}
	
	
	
	
	
	@Override
	public void update()
	{
		insertKeyMessages();
		super.update();			
	}
	
	private void insertKeyMessages()
	{
		if(key_down)
		{
			Force thrust = new Force();
			thrust.magnitude = -0.20f;
			thrust.direction = -rotation;
			
			super.forces.add(thrust);
		}
		if(key_left)
		{
			rotation -= 0.02f;
		}
		if(key_right)
		{
			rotation += 0.02f;
		}
		
	}

	@Override
	public void keyDown(KeyEvent e) 
	{
		if(e.getKeyCode() == KeyEvent.VK_DOWN)
			this.key_down = true;
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
			this.key_left = true;
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			this.key_right = true;
	}

	@Override
	public void keyUp(KeyEvent e) 
	{
		if(e.getKeyCode() == KeyEvent.VK_DOWN)
			this.key_down = false;
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
			this.key_left = false;
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			this.key_right = false;
	}
	
}
