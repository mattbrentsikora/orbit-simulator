package input;

import java.awt.event.KeyEvent;

public class InputMessage 
{
	public enum TYPE {KEYDOWN, KEYUP};
	
	
	public TYPE type;
	public KeyEvent keyEvent;
}
