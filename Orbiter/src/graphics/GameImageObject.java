package graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GameImageObject extends GameObject 
{
	protected Image image;
	
	public GameImageObject()
	{
		
	}
	
	public void render(Graphics2D g2d)
	{
		super.render(g2d);
		
		if(image != null)
		{
			g2d.drawImage(image, (int)(-bounds.x/2), (int)(-bounds.y/2), (int)(bounds.x/2), (int)(bounds.y/2), 0, 0, image.getWidth(null), image.getHeight(null), null);
		}
	}
	
	public void loadImage(String filename)
	{
		try
		{
			image = ImageIO.read(new File(filename));
		} 
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
