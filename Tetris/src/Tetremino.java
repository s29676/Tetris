import java.awt.Color;
import java.util.*;
public class Tetremino {
	enum Shape { //different pieces
		I, O, T, J, L, S, Z;
	}
	Shape shape;
	int coords[][] = new int[4][2];
	Color color;
	
	public Tetremino(int size) {

		int xLoc = 5;
		int yLoc = 0;
		//determines shape
		Random rand = new Random();
		switch (rand.nextInt(7)) {
		case 0:
			coords[0][0] = xLoc; coords[1][0] = xLoc; coords[2][0] = xLoc; coords[3][0] = xLoc;
			coords[0][1] = yLoc+2; coords[1][1] = yLoc+1; coords[2][1] = yLoc; coords[3][1] = yLoc-1;
			shape = Shape.I; 
			color = Color.CYAN;  
			
			break;
		case 1:
			coords[0][0] = xLoc; coords[1][0] = xLoc; coords[2][0] = xLoc+1; coords[3][0] = xLoc+1;
			coords[0][1] = yLoc; coords[1][1] = yLoc+1; coords[2][1] = yLoc+1; coords[3][1] = yLoc;
			shape = Shape.O;
			color = Color.YELLOW;
			break;
		case 2:
			coords[0][0] = xLoc-1; coords[1][0] = xLoc; coords[2][0] = xLoc+1; coords[3][0] = xLoc;
			coords[0][1] = yLoc; coords[1][1] = yLoc; coords[2][1] = yLoc; coords[3][1] = yLoc+1;
			shape = Shape.T;
			color = Color.MAGENTA;
			break;
		case 3:
			coords[0][0] = xLoc-1; coords[1][0] = xLoc; coords[2][0] = xLoc; coords[3][0] = xLoc;
			coords[0][1] = yLoc; coords[1][1] = yLoc; coords[2][1] = yLoc-1; coords[3][1] = yLoc-2;
			shape = Shape.J;
			color = Color.BLUE;
			break;
		case 4:
			coords[0][0] = xLoc+1; coords[1][0] = xLoc; coords[2][0] = xLoc; coords[3][0] = xLoc;
			coords[0][1] = yLoc; coords[1][1] = yLoc; coords[2][1] = yLoc-1; coords[3][1] = yLoc-2;
			shape = Shape.L;
			color = Color.ORANGE;
			break;
		case 5:
			coords[0][0] = xLoc-1; coords[1][0] = xLoc; coords[2][0] = xLoc; coords[3][0] = xLoc+1;
			coords[0][1] = yLoc; coords[1][1] = yLoc; coords[2][1] = yLoc-1; coords[3][1] = yLoc-1;
			shape = Shape.S;
			color = Color.GREEN;
			break;
		case 6:
			coords[0][0] = xLoc-1; coords[1][0] = xLoc; coords[2][0] = xLoc; coords[3][0] = xLoc+1;
			coords[0][1] = yLoc; coords[1][1] = yLoc; coords[2][1] = yLoc+1; coords[3][1] = yLoc+1;
			shape = Shape.Z;
			color = Color.RED;
			break;
		}
	}
		
	public Tetremino(int size, Shape shape) {

		int xLoc = 5;
		int yLoc = 0;
		this.shape = shape;
		//determines shape
		switch (shape) {
		case I:
			coords[0][0] = xLoc; coords[1][0] = xLoc; coords[2][0] = xLoc; coords[3][0] = xLoc;
			coords[0][1] = yLoc+2; coords[1][1] = yLoc+1; coords[2][1] = yLoc; coords[3][1] = yLoc-1;
			color = Color.CYAN;  
			
			break;
		case O:
			coords[0][0] = xLoc; coords[1][0] = xLoc; coords[2][0] = xLoc+1; coords[3][0] = xLoc+1;
			coords[0][1] = yLoc; coords[1][1] = yLoc+1; coords[2][1] = yLoc+1; coords[3][1] = yLoc;
			color = Color.YELLOW;
			break;
		case T:
			coords[0][0] = xLoc-1; coords[1][0] = xLoc; coords[2][0] = xLoc+1; coords[3][0] = xLoc;
			coords[0][1] = yLoc; coords[1][1] = yLoc; coords[2][1] = yLoc; coords[3][1] = yLoc+1;
			color = Color.MAGENTA;
			break;
		case J:
			coords[0][0] = xLoc-1; coords[1][0] = xLoc; coords[2][0] = xLoc; coords[3][0] = xLoc;
			coords[0][1] = yLoc; coords[1][1] = yLoc; coords[2][1] = yLoc-1; coords[3][1] = yLoc-2;
			color = Color.BLUE;
			break;
		case L:
			coords[0][0] = xLoc+1; coords[1][0] = xLoc; coords[2][0] = xLoc; coords[3][0] = xLoc;
			coords[0][1] = yLoc; coords[1][1] = yLoc; coords[2][1] = yLoc-1; coords[3][1] = yLoc-2;
			color = Color.ORANGE;
			break;
		case S:
			coords[0][0] = xLoc-1; coords[1][0] = xLoc; coords[2][0] = xLoc; coords[3][0] = xLoc+1;
			coords[0][1] = yLoc; coords[1][1] = yLoc; coords[2][1] = yLoc-1; coords[3][1] = yLoc-1;
			color = Color.GREEN;
			break;
		case Z:
			coords[0][0] = xLoc-1; coords[1][0] = xLoc; coords[2][0] = xLoc; coords[3][0] = xLoc+1;
			coords[0][1] = yLoc; coords[1][1] = yLoc; coords[2][1] = yLoc+1; coords[3][1] = yLoc+1;
			color = Color.RED;
			break;
		}
	}
	
	public Tetremino(Tetremino another) { //clone constructor
		this.color = another.getColor();
		this.shape = another.getShape();
		for (int i = 0; i < 4; i++) {
			coords[i][0] = another.getX()[i];
			coords[i][1] = another.getY()[i];
		}
	}
	
	public void move(char dir) {
		switch (dir) {
		case 'D':
			for (int i = 0; i < 4; i++) {
				coords[i][1]++;
			}
			break;
		case 'L':
			for (int i = 0; i < 4; i++) {
				coords[i][0]--;
			}
			break;
		case 'R':
			for (int i = 0; i < 4; i++) {
				coords[i][0]++;
			}
			break;
		}
	}
	
	public void rotate() {
		//fix up a little
		switch (shape) {
		case I:
			rotatePiece(coords[1][0], coords[1][1]); //not correct
			break;
		case L:
			rotatePiece(coords[2][0], coords[2][1]);
			break;
		case J:
			rotatePiece(coords[2][0], coords[2][1]);
			break;
		case T:
			rotatePiece(coords[1][0], coords[1][1]);
			break;
		case O:
			//can't rotate
			break;
		case S:
			rotatePiece(coords[2][0], coords[2][1]);
			break;
		case Z:
			rotatePiece(coords[1][0], coords[1][1]);
			break;
		}
	}
	
	public void setColor (Color color) {
		this.color = color;
	}
	
	private void rotatePiece(int xOrigin, int yOrigin) { //rotate cw
		for (int i = 0; i < 4; i++) {
			int[] newVec = new int[2];
			
			//offsets rotation point from origin
			newVec[0] = coords[i][0] - xOrigin;
			newVec[1] = coords[i][1] - yOrigin; 
			
			//rotates uses transformation matrix
			int[][] transMat = { {0, -1}, {1,0} }; //transformation matrix for rotating cw
			newVec = matTransform(newVec, transMat);
			
			//returns point to original area
			newVec[0] += xOrigin;
			newVec[1] +=  yOrigin; 
			coords[i] = newVec;
			
		}
	}
		 
	private int[] matTransform(int[] vec, int[][] mat) { //multiplies vector with transformation matrix
		int[] vec2 = new int[vec.length];
		if (vec.length != mat.length && mat.length == mat[0].length) { //if vector and SQUARE matrix are compatible
			System.out.println("Error");
			return null;
		}
		else {
			for (int i = 0; i < mat.length; i++) {
				int val = 0;
				for (int j = 0; j < mat[0].length; j++) {
					val += vec[j]*mat[i][j];
				}
				vec2[i] = val;
			}
			return vec2;
		}
	}
	
	public int[] getX() {
		int x[] = new int[4];
		for (int i = 0; i < 4; i++) {
			x[i] = coords[i][0];
		}
		return x;
	}
	
	public int[] getY() {
		int y[] = new int[4];
		for (int i = 0; i < 4; i++) {
			y[i] = coords[i][1];
		}
		return y;
	}
	
	public Color getColor() {
		return color;
	}
	
	public Shape getShape() { //used for swapping
		return shape;
	}
}
