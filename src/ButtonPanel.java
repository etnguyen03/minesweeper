/*
Minesweeper
Copyright © Ethan Nguyen 2018. All rights reserved.

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Mine button panel
 */
public class ButtonPanel extends JPanel {
	
	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 110888847104096271L;
	/**
	 * Contains a matrix of JButtons corresponding to the buttons on screen.
	 */
	private JButton[][] buttonMatrix;
	/**
	 * Keeps track of first click
	 * User cannot click on a mine on the first click
	 */
	private boolean firstClick = true;
	/**
	 * Width and height variables
	 */
	int WIDTH, HEIGHT;
	/**
	 * ImageIcon for the flag
	 */
	private ImageIcon flagIcon;
	/**
	 * Keeps track of how many squares were uncovered.
	 */
	private int squaresClicked;
	
	/**
	 * Constructor
	 * @param width Width of the button grid
	 * @param height Height of the button grid
	 */
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
		
		flagIcon = (ImageIcon) UIManager.getIcon("OptionPane.warningIcon");
		
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
		if (x < 0 || y < 0 || x > WIDTH - 1 || y > HEIGHT - 1 || Panel.mineMatrix[x][y] || !buttonMatrix[x][y].getText().equals(""))
		{
			return;
		}
		int surroundingMines = getSurroundingMines(x, y);
		if (surroundingMines == 0)
			buttonMatrix[x][y].setText(" ");
		else
			buttonMatrix[x][y].setText("" + getSurroundingMines(x, y));
		
		buttonMatrix[x][y].setEnabled(false);
		squaresClicked++;
		
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
	
	/**
	 * Reset mines to normal.
	 */
	public void resetMines() {
		for (int i = 0; i < buttonMatrix.length; i++) {
			for (int j = 0; j < buttonMatrix[i].length; j++) {
				buttonMatrix[i][j].setEnabled(true);
				buttonMatrix[i][j].setBackground(null);
				buttonMatrix[i][j].setIcon(null);
				buttonMatrix[i][j].setText("");
			}
		}
		firstClick = true;
	}
	
	/**
	 * Index of function on a matrix.
	 * @param matrix Matrix to search in
	 * @param needle Element to find
	 * @return Int array of indices
	 */
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
	
	/**
	 * Right click listener
	 */
	private class ButtonRightClickListener implements MouseListener {
		/**
		 * Right click listener
		 * Flags the button
		 */
		public void mouseClicked(MouseEvent e) {
			// check if button disabled
			if (((JButton)e.getSource()).isEnabled() == false)
					return;
			
			// if right click
			if (e.getButton() == MouseEvent.BUTTON3) {
				boolean isFlagged;
				try {
					isFlagged = ((JButton) e.getSource()).getIcon().toString().equals(flagIcon.toString());
				}
				catch (Exception ex) {
					isFlagged = false;
				}
				if (isFlagged) {
					Panel.mineCount++;
					Panel.mineCountLabel.setText("" + Panel.mineCount);
					((JButton)e.getSource()).setIcon(null);
				}
				else {
					Panel.mineCount--;
					Panel.mineCountLabel.setText("" + Panel.mineCount);
					((JButton)e.getSource()).setIcon(flagIcon);
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
	
	/**
	 * Click listener for buttons
	 */
	private class ButtonListener implements ActionListener {
		/**
		 * Click listener for buttons
		 */
		public void actionPerformed(ActionEvent e) {
			// get width and height
			int[] widthHeight = indexOfMatrix(buttonMatrix, e.getSource());
			
			// Prevent clicks on red colored squares (ones that the player has flagged)
			try {
				if (((JButton) e.getSource()).getIcon().toString().equals(flagIcon.toString()))
					return;
			}
			catch (NullPointerException ex) {
				// do nothing
			}
			
			
			// Player can't lose on first click
			if (firstClick == true) {
				Panel.timer.start();
				Panel.mineMatrix[widthHeight[0]][widthHeight[1]] = false;
				firstClick = false;
			}
			if (Panel.mineMatrix[widthHeight[0]][widthHeight[1]] == true) {
				// mine
				for (int i = 0; i < Panel.mineMatrix.length; i++) {
					for (int j = 0; j < Panel.mineMatrix[i].length; j++) {
						// set buttons disabled
						buttonMatrix[i][j].setEnabled(false);
						
						
						try {
							// unflagged mines are turned "X"
							if (Panel.mineMatrix[i][j] && buttonMatrix[i][j].getIcon() == null)
								buttonMatrix[i][j].setIcon(UIManager.getIcon("OptionPane.errorIcon"));
							
							// false flags are turned red
							else if (!Panel.mineMatrix[i][j] && buttonMatrix[i][j].getIcon().equals((Icon)flagIcon))
								buttonMatrix[i][j].setBackground(Color.RED);
							
							// good flags are turned green
							else if (Panel.mineMatrix[i][j] && buttonMatrix[i][j].getIcon().equals((Icon)flagIcon))
								buttonMatrix[i][j].setBackground(Color.GREEN);
							
						}
						catch (NullPointerException ex) {
							// empty
						}
					}
				}
				
				Panel.timer.stop();
				loseDialog dialog = new loseDialog(Integer.parseInt(Panel.timerLabel.getText()));
				dialog.showDialog();
			}
			else {
				uncoverSquare(widthHeight[0], widthHeight[1]);
				// check to see if game is won
				if (Panel.mineCount == 0 && squaresClicked == WIDTH * HEIGHT - Panel.actualMineCount) {
					for (int i = 0; i < Panel.mineMatrix.length; i++) {
						for (int j = 0; j < Panel.mineMatrix[i].length; j++) {
							// set buttons disabled
							buttonMatrix[i][j].setEnabled(false);
							
							try {
								// unflagged mines are turned "X"
								if (Panel.mineMatrix[i][j] && buttonMatrix[i][j].getIcon() == null)
									buttonMatrix[i][j].setIcon(UIManager.getIcon("OptionPane.errorIcon"));
								
								// false flags are turned red
								else if (!Panel.mineMatrix[i][j] && buttonMatrix[i][j].getIcon().equals((Icon)flagIcon))
									buttonMatrix[i][j].setBackground(Color.RED);
								
								// good flags are turned green
								else if (Panel.mineMatrix[i][j] && buttonMatrix[i][j].getIcon().equals((Icon)flagIcon))
									buttonMatrix[i][j].setBackground(Color.GREEN);
								
							}
							catch (NullPointerException ex) {
								// empty
							}
						}
					}
					
					// display the window
					Panel.timer.stop();
					winDialog dialog = new winDialog(Integer.parseInt(Panel.timerLabel.getText()));
					dialog.showDialog();
				}
			}
		}
	}
}
