import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
public class Panel extends JPanel {
	
	/**
	 * True if mine, false otherwise
	 */
	public static boolean[][] mineMatrix;
	private final int WIDTH = 10;
	private final int HEIGHT = 10;
	private ButtonPanel buttonPanel;
	private Random rng = new Random();
	
	public Panel() {
		setLayout(new BorderLayout());
		
		JPanel topPanel = new JPanel(new FlowLayout());
		JButton resetButton = new JButton("Reset");
		resetButton.addActionListener(new resetListener());
		topPanel.add(resetButton);
		
		add(topPanel, BorderLayout.PAGE_START);
		
		mineMatrix = new boolean[WIDTH][HEIGHT];
		buttonPanel = new ButtonPanel(WIDTH, HEIGHT);
		add(buttonPanel, BorderLayout.CENTER);
		
		// init mineMatrix
		for (int i = 0; i < WIDTH; i++) {
			for (int j = 0; j < HEIGHT; j++) {
				int randomInt = rng.nextInt(10);
				if (randomInt < 3) {
					mineMatrix[i][j] = true;
				}
				else
					mineMatrix[i][j] = false;
			}
		}
		
		for (int i = 0; i < WIDTH; i++) {
			System.out.println(i + " " + Arrays.toString(mineMatrix[i]));
		}
	}
	
	private class resetListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// init mineMatrix
			for (int i = 0; i < WIDTH; i++) {
				for (int j = 0; j < HEIGHT; j++) {
					int randomInt = rng.nextInt(10);
					if (randomInt < 2) {
						mineMatrix[i][j] = true;
					}
					else
						mineMatrix[i][j] = false;
				}
			}
			buttonPanel.resetMines();
		}
	}
}
