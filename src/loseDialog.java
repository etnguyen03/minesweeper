import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class loseDialog {
	/**
	 * Keeps track of user's time to show on dialog
	 */
	private int myTime;
	/**
	 * JPanel for lose dialog
	 */
	private JPanel losePanel;
	/**
	 * JFrame for losedialog
	 */
	private JFrame loseFrame;
	
	/**
	 * Constructor
	 * @param time Time to show on the lose dialog
	 */
	public loseDialog(int time) {
		myTime = time;
		
		losePanel = new JPanel(new BorderLayout());
		
		JPanel topPanel = new JPanel(new GridLayout(2, 1));
		JLabel loseLabel = new JLabel("You lose!");
		loseLabel.setHorizontalAlignment(JLabel.CENTER);
		topPanel.add(loseLabel);
		
		JLabel timeLabel = new JLabel("Time: " + myTime + " seconds");
		timeLabel.setHorizontalAlignment(JLabel.CENTER);
		topPanel.add(timeLabel);
		
		losePanel.add(topPanel, BorderLayout.CENTER);
		
		JButton closeButton = new JButton("Close");
		closeButton.addActionListener(new CloseButtonListener());
		losePanel.add(closeButton, BorderLayout.PAGE_END);
	}
	
	public void showDialog() {
		loseFrame = new JFrame("You lose!");
		loseFrame.setSize(300, 300);
		loseFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		loseFrame.setContentPane(losePanel);
		loseFrame.setVisible(true);
	}
	
	private class CloseButtonListener implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			loseFrame.setVisible(false);
		}
	}
}
