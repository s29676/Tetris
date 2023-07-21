import java.awt.*;

public class Square {
	private int row, col;
	private boolean filled;
	Color color;
	public Square(int x, int y) {
		color = Color.BLACK;
		this.row = x;
		this.col = y;
	}
	
	public int getX() {
		return row;
	}
	
	public int getY() {
		return col;
	}
	
	public boolean isFilled() {
		return filled;
	}
	
	public void setFilled(boolean bool) {
		filled = bool; 
		if (!filled) {
			color = Color.BLACK;
		}
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}
}
