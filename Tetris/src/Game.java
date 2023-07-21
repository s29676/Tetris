import java.awt.event.*;
import java.awt.event.KeyListener;
import java.awt.*;
import javax.swing.*;
import java.util.*;
import javax.swing.Timer;
import java.awt.event.ActionListener;

public class Game extends JPanel implements ActionListener{
	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 800;
	static int gridCols = 10;
	static int gridRows = 24;
	static int squareSize = 30;
	boolean gameRunning;
	boolean keyDown;
	boolean holdEmpty = true; //if there is a piece being held
	boolean canHold = true; //make it so can't swap twice in a row
	int DELAY = 300;
	int score;
	int level;
	int rowsBroken;
	boolean delayBot; //lazy fix to allow move for a sec at the bottom
	Timer timer;
	Tetremino mainPiece; //piece that is at bat
	Tetremino piece2; //piece on deck
	Tetremino piece3; //piece in the hole
	Tetremino piece4; //clean up piece
	Tetremino pieceHold; //piece being held
	//creates grid
	Square[][] grid = new Square[gridCols][gridRows];
	
	Game() {
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.GRAY);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		init();
	}
	
	private void init() { //initialization
		gameRunning = true; 
		score = 0;
		level = 1;
		rowsBroken = 0;
		delayBot = true;
		//sets up grid
		for (int i = 0; i < gridCols; i++) {
			for (int j = 0; j < gridRows; j++) {
				grid[i][j] = new Square(i, j);
			} 
		}
		
		timer = new Timer(DELAY,this);
		timer.start();
		piece2 = new Tetremino(squareSize);
		piece3 = new Tetremino(squareSize);
		piece4 = new Tetremino(squareSize);
		spawnPiece();
		
	}
	
	
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
		
	}
	

	
	public void draw(Graphics g) {
		//draws the grid
		for (int i = 0; i < gridCols; i++) {
			for (int j = 0; j < gridRows; j++) {
				g.setColor(grid[i][j].getColor());
				g.fillRect(i*squareSize, j*squareSize, squareSize, squareSize );
				g.setColor(Color.WHITE);//border
				g.drawRect(i*squareSize, j*squareSize, squareSize, squareSize );
			}
		}
		
		//draw score
		g.drawString("Score: " + score + " | Level: " + level, 475, 100);
		g.drawString("Delay: " + DELAY, 500, 125);
		
		//game over
		if (!gameRunning) {
			g.drawString("Game Over", 500, 200);
		}
		
		
		//draws mainPiece
		for (int i = 0; i < 4; i++) {
			g.setColor(mainPiece.getColor());
			g.fillRect(mainPiece.getX()[i]*squareSize, mainPiece.getY()[i]*squareSize, squareSize, squareSize);
			g.setColor(Color.WHITE);
			g.drawRect(mainPiece.getX()[i]*squareSize, mainPiece.getY()[i]*squareSize, squareSize, squareSize);
		}
		
		//draws piece on deck
		g.drawString("NEXT:", 375, 20);
		for (int i = 0; i < 4; i++) {
			g.setColor(piece2.getColor());
			g.fillRect((piece2.getX()[i]+8)*squareSize, (piece2.getY()[i]+3)*squareSize, squareSize, squareSize);
			g.setColor(Color.WHITE);
			g.drawRect((piece2.getX()[i]+8)*squareSize, (piece2.getY()[i]+3)*squareSize, squareSize, squareSize);
		}
		
		//draws piece in the hole
		for (int i = 0; i < 4; i++) {
			g.setColor(piece3.getColor());
			g.fillRect((piece3.getX()[i]+8)*squareSize, (piece3.getY()[i]+8)*squareSize, squareSize, squareSize);
			g.setColor(Color.WHITE);
			g.drawRect((piece3.getX()[i]+8)*squareSize, (piece3.getY()[i]+8)*squareSize, squareSize, squareSize);
		}
		
		//draws clean-up piece
				for (int i = 0; i < 4; i++) {
					g.setColor(piece4.getColor());
					g.fillRect((piece4.getX()[i]+8)*squareSize, (piece4.getY()[i]+13)*squareSize, squareSize, squareSize);
					g.setColor(Color.WHITE);
					g.drawRect((piece4.getX()[i]+8)*squareSize, (piece4.getY()[i]+13)*squareSize, squareSize, squareSize);
				}
		
		//draws piece being held
		g.drawString("Hold: ", 375, 550);
				if (pieceHold != null) {
					for (int i = 0; i < 4; i++) {
						g.setColor(pieceHold.getColor());
						g.fillRect((pieceHold.getX()[i]+8)*squareSize, (pieceHold.getY()[i]+20)*squareSize, squareSize, squareSize);
						g.setColor(Color.WHITE);
						g.drawRect((pieceHold.getX()[i]+8)*squareSize, (pieceHold.getY()[i]+20)*squareSize, squareSize, squareSize);
					}
				}
	}
	
	/*
	 * 
	 * Adds a new piece to the top of the board and shifts the pieces in queue up by one
	 */
	private void spawnPiece() {
		mainPiece = piece2;
		piece2 = piece3;
		piece3 = piece4;
		piece4 = new Tetremino(squareSize);
		
		//detects if game is over/blocks stacked to max
		for (int i = 0; i < 4; i++) {
			int x = mainPiece.getX()[i];
			int y = mainPiece.getY()[i];
			if (y >= -1) {
				if (grid[x][y+1].isFilled()) {
					gameRunning = false;
					//gameOver;
				}
			}
			else if (y > -1) {
				if (grid[x][y].isFilled()) {
					gameRunning = false;
					//gameOver;
				}
			}
		}
	}
	
	/*
	 * Allows players to switch pieces
	 */
	private void swap() { 
		if (!holdEmpty) {
			Tetremino pieceSwap;
			pieceSwap = new Tetremino(squareSize, mainPiece.getShape());
			mainPiece = new Tetremino(squareSize, pieceHold.getShape());
			pieceHold = pieceSwap;
		}
		else {
			pieceHold = new Tetremino(squareSize, mainPiece.getShape());
			spawnPiece();
			holdEmpty = false;
		}
		canHold = false;
	}

	/*
	 * Game loop that is continuously running
	 * @param e: action event that runs the game
	 */
	public void actionPerformed(ActionEvent e) {
		if (gameRunning) {
			if (!hitBelow()) {
				//allows piece to constantly fall
				mainPiece.move('D');
			}
			checkCollision();
		}
		repaint();
		
	}
	
	/*
	 * Deletes rows that are full
	 */
	public void deleteFullRows() {
		int rowsFilled = 0; //combo
		for (int i = 0; i < gridRows; i++) {
			boolean filled = true;
			for (int j = 0; j < gridCols; j++) {
				if (grid[j][i].isFilled() == false) {
					filled = false;
				}
				
			}
			if (filled) { //if entire row is filled
				rowsFilled++;
				if (rowsBroken == 3) {
					rowsBroken = 0;
					level++;
					DELAY -= 25;
					timer.setDelay(DELAY);
				}
				else {
					rowsBroken++;
				}
				for (int j = 0; j < gridCols; j++)  {
					 if (i > 0) { //if not the top row
						for (int row = i; row > 0; row--) {
							if (i > 0) {
								grid[j][row] = grid[j][row-1];
							}
						}
					}
					
				}
			}
			switch (rowsFilled) { //increases score
			case 1:
				score += level*40;
				break;
			case 2:
				score += level*100;
				break;
			case 3:
				score += level*300;
				break;
			case 4:
				score += level*1200;
				break;
			}
		}
	}
	
	/*
	 * turns squares that mainPieced dropped on into filled = true
	 * @param piece: piece that is being turned
	 */
	private void dropPiece(Tetremino piece) {
		for (int i = 0; i < 4; i++) {
			grid[mainPiece.getX()[i]][ mainPiece.getY()[i]].setFilled(true); 
			grid[mainPiece.getX()[i]][ mainPiece.getY()[i]].setColor(mainPiece.getColor());
		}
		deleteFullRows();
	}
	
	/*
	 * spawn new piece if piece is dropped, 
	 */
	private void checkCollision() {
		if (hitBelow()) {
			if (delayBot) {
				dropPiece(mainPiece);
				canHold = true;
				spawnPiece();
				delayBot = false;
			}
			else {
				delayBot = true;
			}
		}
	
	}
	
	public boolean hitBelow() { //checks if hit the bottom
		for (int i = 0; i < 4; i++) {
			int x = mainPiece.getX()[i];
			int y = mainPiece.getY()[i];
			if (y+1 == gridRows) { //if at the very last row
				return true;
			}
			else if (x <= gridCols && x >= 0 && y <= gridRows && y > 0) { //if in bound
				if (grid[x][y+1].isFilled()) { //touching piece below it
					return true;	
				}
				
			}
		}
		return false;
	}
	
	public boolean hitLeft() { //if able to move left
		for (int i = 0; i < 4; i++) {
			int x = mainPiece.getX()[i];
			int y = mainPiece.getY()[i];
			if (x == 0) { //if at very left of screen
				return true;
			}
			else if (x <= gridCols && x >= 0 && y <= gridRows && y > 0) { // if in bound
				if (grid[x-1][y].isFilled()) { //touching any piece to left
					return true;
				}
			}
		}
		return false;
	}

	public boolean hitRight() { //touching right of screen
		for (int i = 0; i < 4; i++) {
			int x = mainPiece.getX()[i];
			int y = mainPiece.getY()[i];
			if (x == (gridCols-1)) { //if at very right of screen
				return true;
			}
			else if (x <= gridCols && x >= 0 && y <= gridRows && y > 0) { //if in bound
				if (grid[x+1][y].isFilled()) { //if touching any piece to right
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean canRotate() {
		Tetremino testPiece = new Tetremino(mainPiece);
		testPiece.rotate();
		for (int i = 0; i < 4; i++) {
			int x = testPiece.getX()[i];
			int y = testPiece.getY()[i];
			if (x < 0 || x >= gridCols || y < 0 || y >= gridRows) {
				return false;
			}
			else if (grid[x][y].isFilled()) {
				return false;
			}
		}
		
		return true;
	}
	

	public class MyKeyAdapter extends KeyAdapter { //gets keys pressed
		public void keyPressed(KeyEvent e) {
		    if (gameRunning && keyDown == false) {
		        switch (e.getKeyCode()) {
		            case KeyEvent.VK_LEFT:
		            	if (!hitLeft()) {
		            		keyDown = true;
		            		mainPiece.move('L');
		            	}
		                break;
		            case KeyEvent.VK_RIGHT:
		            	if (!hitRight()) {
		            		keyDown = true;
		            		mainPiece.move('R');
		            	}
		                break;
		            case KeyEvent.VK_DOWN:
		            	if (!hitBelow()) {
		            		keyDown = true;
		            		mainPiece.move('D');
		            	}
		                break;
		            case KeyEvent.VK_UP:
		            	if (canRotate()) {
		            		keyDown = true;
		            		mainPiece.rotate();
		            	}
		                break;
		            case KeyEvent.VK_C:
		            	if (canHold) {
		            		swap();
		            	}
		                break;
		            case KeyEvent.VK_SPACE:
		            	if (!hitBelow()) {
		            		keyDown = true;
		            		dropPress();
		            	}
		        }
		    }
		}
		
		private void dropPress() {
			while (!hitBelow()) {
				mainPiece.move('D');
			}
			
		}

		public void keyReleased(KeyEvent e) { //makes it so user cannot hold a button, has to press multiple times
			if (gameRunning && keyDown == true) {
		        switch (e.getKeyCode()) {
		            case KeyEvent.VK_LEFT:
		            	keyDown = false;
		                break;
		            case KeyEvent.VK_RIGHT:
		            	keyDown = false;
		                break;
		            case KeyEvent.VK_DOWN:
		            	keyDown = false;
		                break;
		            case KeyEvent.VK_UP:
		            	keyDown = false;
		                break;
		            case KeyEvent.VK_C:
		            	keyDown = false;
		                break;
		            case KeyEvent.VK_SPACE:
		            	keyDown = false;
		            	break;
		        }
		    }
		}
	}
	
}

