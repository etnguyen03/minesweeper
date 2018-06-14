import javax.swing.*;

public class Driver {

	public static void main(String[] args) {
		JFrame frame = new JFrame("Minesweeper");
		frame.setSize(800, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(new Panel());
		frame.setVisible(true);
	}

}
