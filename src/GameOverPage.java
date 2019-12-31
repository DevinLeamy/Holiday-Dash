import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;

import javax.imageio.ImageIO;
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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;

public class GameOverPage extends JDialog{
	
	private final JPanel contentPanel = new JPanel();
	private boolean ok = false;
	private JLabel lblScoreValue;
	private JLabel lblHighScoreValue;
	private JLabel lblSleigh;
	private JLabel lblExplosion;
	private JLabel lblEnemieBlock;
	private ImageIcon enemieIcon;
	private ImageIcon sleighIcon;
	private ImageIcon explosionIcon;
	public boolean isOk() {
		return ok;
	}

	public GameOverPage(boolean isGreen) {
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
				ok = true;
				dispose();
			}
		});
		okButton.setBackground(Color.WHITE);
		okButton.setForeground(Color.GREEN);
		okButton.setFont(new Font("Dialog", Font.PLAIN, 40));
		okButton.setBounds(0, 629, 920, 67);
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
		lblHighScore.setBounds(18, 809, 230, 61);
		contentPanel.add(lblHighScore);
		
		lblHighScoreValue = new JLabel("0");
		lblHighScoreValue.setForeground(Color.GREEN);
		lblHighScoreValue.setFont(new Font("Dialog", Font.PLAIN, 35));
		lblHighScoreValue.setBounds(228, 811, 168, 56);
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
		
		lblSleigh = new JLabel("This is a sleigh");
		lblSleigh.setBounds(274, 383, 256, 256);
		
		
		lblExplosion = new JLabel("This is an explosion");
		lblExplosion.setBounds(370, 366, 230, 175);
		
		
		lblEnemieBlock = new JLabel("This is an enemie block");
		lblEnemieBlock.setBounds(520, 455, 113, 113);
		
		try {
			if (isGreen) {
				enemieIcon = new ImageIcon(ImageIO.read(new File("res/Images/greenBlock.png")));
				sleighIcon = new ImageIcon(ImageIO.read(new File("res/Images/sleighOneGreen.png")));
			} else {
				enemieIcon = new ImageIcon(ImageIO.read(new File("res/Images/enemie.png")));
				sleighIcon = new ImageIcon(ImageIO.read(new File("res/Images/sleighOne.png")));
			}
			Image explosion = ImageIO.read(new File("res/Images/explosion.png"));
			Image explosionScaled = explosion.getScaledInstance(lblExplosion.getWidth(), lblExplosion.getHeight(), Image.SCALE_SMOOTH);
			explosionIcon = new ImageIcon(explosionScaled);
		} catch(IOException e) {
			enemieIcon = new ImageIcon();
			sleighIcon = new ImageIcon();
			explosionIcon = new ImageIcon();
		}
		
		lblSleigh.setIcon(sleighIcon);
		lblEnemieBlock.setIcon(enemieIcon);
		lblExplosion.setIcon(explosionIcon);
	
		contentPanel.add(lblSleigh);
		contentPanel.add(lblEnemieBlock);
		contentPanel.add(lblExplosion);
		
		
		
		initScores();
		writeHighScore(HolidayDashUniverse.getHighScore());
		this.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				char key = e.getKeyChar();
				if (key == ' ') {
					ok = true;
					dispose();
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
				char key = e.getKeyChar();
				if (key == ' ') {
					ok = true;
					dispose();
				}
			}
			@Override
			public void keyTyped(KeyEvent e) {
				char key = e.getKeyChar();
				if (key == ' ') {
					ok = true;
					dispose();
				}
			}
		});
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
