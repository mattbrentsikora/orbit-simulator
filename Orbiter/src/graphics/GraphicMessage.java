package graphics;

public class GraphicMessage 
{
	public Object object;
	
	public enum MESSAGE_TYPE {ADD, SUBTRACT, SUBTRACTALL, MOUSEDRAGGED, MOUSEMOVED, MOUSECLICKED, WHEELMOVED};
	
	public MESSAGE_TYPE type;
	
	public GraphicMessage(Object o, MESSAGE_TYPE type)
	{
		this.type = type;
		object = o;
	}
}
