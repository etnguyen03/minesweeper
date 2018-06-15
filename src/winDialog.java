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

public class winDialog {
	/**
	 * Keeps track of user's time to show on window
	 */
	private int myTime;
	/**
	 * JPanel for win dialog
	 */
	private JPanel winPanel;
	/**
	 * JFrame for windialog
	 */
	private JFrame winFrame;
	
	/**
	 * Constructor
	 * @param time Time to show on the window
	 */
	public winDialog(int time) {
		myTime = time;
		
		winPanel = new JPanel(new BorderLayout());
		
		JPanel topPanel = new JPanel(new GridLayout(2, 1));
		JLabel winLabel = new JLabel("You Win!");
		winLabel.setHorizontalAlignment(JLabel.CENTER);
		topPanel.add(winLabel);
		
		JLabel timeLabel = new JLabel("Time: " + myTime + " seconds");
		timeLabel.setHorizontalAlignment(JLabel.CENTER);
		topPanel.add(timeLabel);
		
		winPanel.add(topPanel, BorderLayout.CENTER);
		
		JButton closeButton = new JButton("Close");
		closeButton.addActionListener(new CloseButtonListener());
		winPanel.add(closeButton, BorderLayout.PAGE_END);
	}
	
	public void showDialog() {
		winFrame = new JFrame("You win!");
		winFrame.setSize(300, 300);
		winFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		winFrame.setContentPane(winPanel);
		winFrame.setVisible(true);
	}
	
	private class CloseButtonListener implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			winFrame.setVisible(false);
		}
	}
}
