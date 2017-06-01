package graphics;

import graphics.GraphicMessage.MESSAGE_TYPE;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.Collections;
import java.util.LinkedList;
import java.util.concurrent.ArrayBlockingQueue;


public class GameObject implements Comparable<GameObject>
{
	/**
	 * Basic physic variables. Note that the position of the object
	 * will me the center of it. So the bounds extend to the left and 
	 * right and up and down. -(1/2)bounds.x to the left, etc 
	 */
	protected Point2D.Float position;
	protected Point2D.Float velocityPosition;
	protected Point2D.Float accelerationPosition;
	protected float scale;
	protected float velocityScale;
	protected float accelerationScale;
	protected float rotation;
	protected float velocityRotation;
	protected float accelerationRotation;
	
	protected Point2D.Float bounds;
	protected float height;
	protected AffineTransform currentTransformation;
	
	/**
	 * Each object is a layer. These variables hold and control those
	 * aspects. 
	 */
	protected GameObject parentObject;
	protected LinkedList<GameObject> subObjects;
	
	/**
	 * Messages
	 */
	protected ArrayBlockingQueue<Object> messages;
	
	public class Force
	{
		public float magnitude;
		public float direction;
	}
		
	public GameObject()
	{
		initBasicVariables();
	}
	public GameObject(GameObject parent)
	{
		initBasicVariables();
		this.parentObject = parent;
	}
	
	private void initBasicVariables()
	{
		position = new Point2D.Float(0.0f, 0.0f);
		velocityPosition = new Point2D.Float(0.0f, 0.0f);
		accelerationPosition = new Point2D.Float(0.0f, 0.0f);
		scale = 1.0f;
		rotation = 0.0f;
		bounds = new Point2D.Float(100f, 100f);
		height = 0.0f;
	
		currentTransformation = new AffineTransform();
		
		subObjects = new LinkedList<GameObject>();
		
		messages = new ArrayBlockingQueue<Object>(100);
	}
	
	
	public void update()
	{
		Collections.sort(subObjects);
		
		//Loop through the messages and handle those first
		while(!messages.isEmpty())
		{
			try 
			{
				handleMessage(messages.take());
			} 
			catch (InterruptedException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//Update the physical attributes
		velocityPosition.x += accelerationPosition.x;
		velocityPosition.y += accelerationPosition.y;
		position.x += velocityPosition.x;
		position.y += velocityPosition.y;
		velocityScale += accelerationScale;
		scale += velocityScale;
		velocityRotation += accelerationRotation;
		rotation += velocityRotation;

		for(GameObject go: subObjects)
		{
			go.update();
		}
		
	}
	
	public void render(Graphics2D g2d)
	{
		g2d.translate(position.x, position.y);
		g2d.scale(scale, scale);
		g2d.rotate(rotation);
		
		currentTransformation = g2d.getTransform();
	
		
		for(GameObject go: subObjects)
		{
			go.render(g2d);
			g2d.setTransform(currentTransformation);
		}
	}
	
	protected void handleMessage(Object o)
	{
		if(o instanceof GraphicMessage)
		{
			GraphicMessage temp = (GraphicMessage)o;
			if(temp.type == GraphicMessage.MESSAGE_TYPE.ADD)
			{
				subObjects.add((GameObject)temp.object);
			}
			if(temp.type == GraphicMessage.MESSAGE_TYPE.SUBTRACT)
			{
				subObjects.remove(temp.object);
			}
			if(temp.type == GraphicMessage.MESSAGE_TYPE.SUBTRACTALL)
			{
				subObjects.clear();
			}
		}
		
	}
	
	public void addSubObject(GameObject o)
	{	
		try 
		{
			o.setParentObject(this);	
			GraphicMessage temp = new GraphicMessage(o,MESSAGE_TYPE.ADD);
			messages.put(temp);
		} 
		catch (InterruptedException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void removeSubObject(GameObject o)
	{
		try
		{
			GraphicMessage temp = new GraphicMessage(o, MESSAGE_TYPE.SUBTRACT);
			messages.put(temp);
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	public void removeAllSubObject()
	{
		try
		{
			GraphicMessage temp = new GraphicMessage(null, MESSAGE_TYPE.SUBTRACTALL);
			messages.put(temp);
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public int compareTo(GameObject arg0) 
	{
		return Float.compare(height, arg0.height);		
	}
	public Point2D.Float getPosition() {
		return position;
	}
	public void setPosition(Point2D.Float position) {
		this.position = position;
	}
	public Point2D.Float getVelocityPosition() {
		return velocityPosition;
	}
	public void setVelocityPosition(Point2D.Float velocityPosition) {
		this.velocityPosition = velocityPosition;
	}
	public Point2D.Float getAccelerationPosition() {
		return accelerationPosition;
	}
	public void setAccelerationPosition(Point2D.Float accelerationPosition) {
		this.accelerationPosition = accelerationPosition;
	}
	public float getScale() {
		return scale;
	}
	public void setScale(float scale) {
		this.scale = scale;
	}
	public float getVelocityScale() {
		return velocityScale;
	}
	public void setVelocityScale(float velocityScale) {
		this.velocityScale = velocityScale;
	}
	public float getAccelerationScale() {
		return accelerationScale;
	}
	public void setAccelerationScale(float accelerationScale) {
		this.accelerationScale = accelerationScale;
	}
	public float getRotation() {
		return rotation;
	}
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}
	public float getVelocityRotation() {
		return velocityRotation;
	}
	public void setVelocityRotation(float velocityRotation) {
		this.velocityRotation = velocityRotation;
	}
	public float getAccelerationRotation() {
		return accelerationRotation;
	}
	public void setAccelerationRotation(float accelerationRotation) {
		this.accelerationRotation = accelerationRotation;
	}
	public Point2D.Float getBounds() {
		return bounds;
	}
	public void setBounds(Point2D.Float bounds) {
		this.bounds = bounds;
	}
	public float getHeight() {
		return height;
	}
	public void setHeight(float height) {
		this.height = height;
	}
	public GameObject getParentObject() {
		return parentObject;
	}
	public void setParentObject(GameObject parentObject) {
		this.parentObject = parentObject;
	}
	public ArrayBlockingQueue<Object> getMessages()
	{
		return this.messages;
	}
	
	
}
