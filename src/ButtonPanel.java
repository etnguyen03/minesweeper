import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class ButtonPanel extends JPanel {
	
	private JButton[][] buttonMatrix;
	private boolean firstClick = true;
	int WIDTH, HEIGHT;
	
	public ButtonPanel(int width, int height) {
		setLayout(new GridLayout(width, height));
		buttonMatrix = new JButton[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				buttonMatrix[i][j] = new JButton();
				buttonMatrix[i][j].addActionListener(new ButtonListener());
				buttonMatrix[i][j].addMouseListener(new ButtonRightClickListener());
				add(buttonMatrix[i][j]);
			}
		}
		
		WIDTH = width;
		HEIGHT = height;
	}
	
	/**
	 * Get number of surrounding mines.
	 * @param x X-coord
	 * @param y Y-coord
	 * @return Number of surrounding mines.
	 */
	public int getSurroundingMines(int x, int y) {
		int mineCount = 0;
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++ ) {
				try {
					if (Panel.mineMatrix[x+i][y+j] == true) {
						mineCount++;
					}
				}
				catch (Exception e) {
					// nothing
				}
			}
		}
		return mineCount;
	}
	
	/**
	 * Uncover squares function - for squares that are not mines
	 * Disable button, add number
	 * @param x X coord of pressed button
	 * @param y Y coord of pressed button
	 */
	public void uncoverSquare(int x, int y) {
		
		if (x < 0 || y < 0 || x > WIDTH - 1 || y > HEIGHT - 1 || Panel.mineMatrix[x][y])
		{
			return;
		}
		buttonMatrix[x][y].setText("" + getSurroundingMines(x, y));
		buttonMatrix[x][y].setEnabled(false);
		
		// If this square was a zero, then uncover surrounding squares and recurse
		if (getSurroundingMines(x, y) == 0) {
			uncoverSquare(x + 1, y);
			uncoverSquare(x - 1, y);
			uncoverSquare(x + 1, y + 1);
			uncoverSquare(x - 1, y - 1);
			uncoverSquare(x, y + 1);
			uncoverSquare(x, y - 1);
			uncoverSquare(x + 1, y - 1);
			uncoverSquare(x - 1, y + 1);
		}
		
		return;
	}
	
	public void resetMines() {
		for (int i = 0; i < buttonMatrix.length; i++) {
			for (int j = 0; j < buttonMatrix[i].length; j++) {
				buttonMatrix[i][j].setEnabled(true);
				buttonMatrix[i][j].setBackground(null);
				buttonMatrix[i][j].setText("");
			}
		}
	}
	
	private int[] indexOfMatrix(Object[][] matrix, Object needle) {
		for (int i = 0; i < matrix.length; i++ ) {
			for (int j = 0; j < matrix[i].length; j++ ) {
				if (needle == matrix[i][j]) {
					return new int[] {i, j};
				}
			}
		}
		return new int[] {-1, -1};
	}
	
	private class ButtonRightClickListener implements MouseListener {
		public void mouseClicked(MouseEvent e) {
			// if right click
			if (e.getButton() == MouseEvent.BUTTON3) {
				if (((JButton)e.getSource()).getBackground() == Color.RED) {
					((JButton)e.getSource()).setBackground(null);
				}
				else {
					((JButton)e.getSource()).setBackground(Color.RED);
				}
			}
		}
		
		// The other abstract methods are not used but must be implemented
		public void mouseEntered(MouseEvent e) {
			// empty
		}
		public void mousePressed(MouseEvent e) {
			// empty
		}
		public void mouseReleased(MouseEvent e) {
			// empty
		}
		public void mouseExited(MouseEvent e) {
			// empty
		}
	}
	
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// get width and height
			int[] widthHeight = indexOfMatrix(buttonMatrix, e.getSource());
			
			// Prevent clicks on red colored squares (ones that the player has flagged)
			if (((JButton)e.getSource()).getBackground() == Color.RED)
				return;
			
			// Player can't lose on first click
			if (firstClick == true) {
				Panel.mineMatrix[widthHeight[0]][widthHeight[1]] = false;
				firstClick = false;
			}
			if (Panel.mineMatrix[widthHeight[0]][widthHeight[1]] == true) {
				// mine
				for (int i = 0; i < Panel.mineMatrix.length; i++) {
					for (int j = 0; j < Panel.mineMatrix[i].length; j++) {
						// set buttons disabled
						buttonMatrix[i][j].setEnabled(false);
						if (Panel.mineMatrix[i][j] == true) {
							buttonMatrix[i][j].setBackground(Color.RED);
							buttonMatrix[i][j].setText("X");
						}
					}
				}
			}
			else {
				uncoverSquare(widthHeight[0], widthHeight[1]);
			}
		}
	}
}
