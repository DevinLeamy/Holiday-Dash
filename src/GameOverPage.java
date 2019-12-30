import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GameOverPage extends JDialog {
	
	private final JPanel contentPanel = new JPanel();
	private boolean ok = false;
	private JLabel lblScoreValue;
	private JLabel lblHighScoreValue;
	public boolean isOk() {
		return ok;
	}

	public GameOverPage() {
		setBackground(Color.WHITE);
		setBounds(0, 0, 920, 920);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		contentPanel.setBackground(Color.WHITE);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(242, 17, 0, 0);
		contentPanel.add(lblNewLabel);
		
		JLabel lblGameOver = new JLabel("Game Over", SwingConstants.CENTER);
		lblGameOver.setForeground(Color.RED);
		lblGameOver.setBackground(Color.RED);
		lblGameOver.setFont(new Font("Dialog", Font.PLAIN, 70));
		lblGameOver.setBounds(6, 146, 914, 83);
		contentPanel.add(lblGameOver);
		JButton okButton = new JButton("Try Again");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		okButton.setBackground(Color.WHITE);
		okButton.setForeground(Color.GREEN);
		okButton.setFont(new Font("Dialog", Font.PLAIN, 40));
		okButton.setBounds(0, 466, 920, 67);
		contentPanel.add(okButton);
		okButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				okButton_mouseClicked(arg0);
			}
		});
		okButton.setActionCommand("OK");
		getRootPane().setDefaultButton(okButton);
		contentPanel.add(okButton);
		
		JLabel lblHighScore = new JLabel("High Score: ");
		lblHighScore.setForeground(Color.GREEN);
		lblHighScore.setFont(new Font("Dialog", Font.PLAIN, 35));
		lblHighScore.setBounds(19, 751, 230, 61);
		contentPanel.add(lblHighScore);
		
		lblHighScoreValue = new JLabel("0");
		lblHighScoreValue.setForeground(Color.GREEN);
		lblHighScoreValue.setFont(new Font("Dialog", Font.PLAIN, 35));
		lblHighScoreValue.setBounds(236, 753, 168, 56);
		contentPanel.add(lblHighScoreValue);
		
		JLabel lblNewLabel_3 = new JLabel("Score: ");
		lblNewLabel_3.setForeground(Color.RED);
		lblNewLabel_3.setFont(new Font("Dialog", Font.PLAIN, 35));
		lblNewLabel_3.setBounds(384, 270, 129, 59);
		contentPanel.add(lblNewLabel_3);
		
		lblScoreValue = new JLabel("0");
		lblScoreValue.setForeground(Color.RED);
		lblScoreValue.setFont(new Font("Dialog", Font.PLAIN, 35));
		lblScoreValue.setBounds(525, 266, 72, 63);
		contentPanel.add(lblScoreValue);
		initScores();
		writeHighScore(HolidayDashUniverse.getHighScore());
	}
	protected void okButton_mouseClicked(MouseEvent arg0) {
		this.ok = true;
		dispose();
		
		
	}
	private void writeHighScore(int highScore) {
		try {
			File highScoreFile = new File("src/HighScore");
			FileWriter fileWriter = new FileWriter(highScoreFile, false);
			fileWriter.write(String.valueOf(highScore));
			fileWriter.close();
		} catch (IOException ex) {
			System.out.println("ERROR: Could not write to File");
		}
	}
	private void initScores() {
		lblHighScoreValue.setText(String.valueOf(HolidayDashUniverse.getHighScore()));
		lblScoreValue.setText(String.valueOf(HolidayDashUniverse.getScore()));
	}
}
