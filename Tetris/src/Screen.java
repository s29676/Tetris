import javax.swing.JFrame;
public class Screen extends JFrame{
	
	public Screen() {
		
		this.add(new Game());
		this.setTitle("Tetris");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
		this.pack(); 
		this.setLocationRelativeTo(null);
		
	}
	

}
