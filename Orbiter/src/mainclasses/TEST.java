package mainclasses;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;

public class TEST {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		Point2D.Float p1 = new Point2D.Float();
		Point2D.Float p2 = new Point2D.Float();
		
		p1.setLocation(0, 0);
		p2.setLocation(-3,4.0f);
		
		float dx = p1.x - p2.x;
		float dy = p1.y - p2.y;
		
		float radius = (float) Math.sqrt((dx*dx)+(dy*dy));
		
		System.out.println("Radius is "+radius);
		
		float theta = (float) Math.atan(dx/dy);
		System.out.println("Theta is "+theta);
		System.out.println(Math.toDegrees(theta));
		
		
	}

}
