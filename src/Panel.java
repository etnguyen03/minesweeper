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
import java.util.*;

/**
 * Panel class
 */
public class Panel extends JPanel {
	
	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 1073272024441043521L;
	/**
	 * True if mine, false otherwise
	 */
	public static boolean[][] mineMatrix;
	/**
	 * Width
	 */
	private int HEIGHT = 10;
	/**
	 * Height
	 */
	private int WIDTH = 10;
	/**
	 * Button panel
	 */
	private ButtonPanel buttonPanel;
	/**
	 * Random number generator
	 */
	private Random rng = new Random();
	/**
	 * Mine count
	 */
	public static int mineCount = 0;
	/**
	 * Mine count label
	 */
	public static JLabel mineCountLabel;
	/**
	 * Timer label
	 */
	public static JLabel timerLabel;
	/**
	 * Timer
	 */
	public static javax.swing.Timer timer;
	/**
	 * Actual mine count
	 */
	public static int actualMineCount = 0;
	
	/**
	 * Constructor
	 */
	public Panel() {
		setLayout(new BorderLayout());
		
		JPanel topPanel = new JPanel(new GridLayout(1, 5, 50, 10));
		topPanel.add(new JLabel(""));	// spacer
		mineCountLabel = new JLabel();
		topPanel.add(mineCountLabel);
		JButton resetButton = new JButton("Reset");
		resetButton.addActionListener(new resetListener());
		topPanel.add(resetButton);
		
		timerLabel = new JLabel("0");
		topPanel.add(timerLabel, null);
		
		topPanel.add(new JLabel(""));	// spacer
		add(topPanel, BorderLayout.PAGE_START);
		
		mineMatrix = new boolean[WIDTH][HEIGHT];
		buttonPanel = new ButtonPanel(WIDTH, HEIGHT);
		add(buttonPanel, BorderLayout.CENTER);
		
		// init mineMatrix
		mineCount = 0;
		for (int i = 0; i < WIDTH; i++) {
			for (int j = 0; j < HEIGHT; j++) {
				int randomInt = rng.nextInt(10);
				if (randomInt < rng.nextInt(3) + 1) {
					mineMatrix[i][j] = true;
					mineCount++;
				}
				else
					mineMatrix[i][j] = false;
			}
		}
		
		mineCountLabel.setText("" + mineCount);
		actualMineCount = mineCount;
		
		// init timer
		timer = new javax.swing.Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				timerLabel.setText("" + (Integer.parseInt(timerLabel.getText()) + 1));
			}
		});
	}
	
	/**
	 * Listener for reset button
	 */
	private class resetListener implements ActionListener {
		/**
		 * Listener for reset button
		 */
		public void actionPerformed(ActionEvent e) {
			// init mineMatrix
			mineCount = 0;
			for (int i = 0; i < WIDTH; i++) {
				for (int j = 0; j < HEIGHT; j++) {
					int randomInt = rng.nextInt(10);
					if (randomInt < 2) {
						mineMatrix[i][j] = true;
						mineCount++;
					}
					else
						mineMatrix[i][j] = false;
				}
			}
			mineCountLabel.setText("" + mineCount);
			buttonPanel.resetMines();
			actualMineCount = mineCount;
			
			timerLabel.setText("0");
		}
	}
}
