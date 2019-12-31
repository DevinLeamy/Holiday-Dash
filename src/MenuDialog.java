import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.awt.Color;
import javax.swing.SwingConstants;

public class MenuDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private static Thread game;
	private JLabel lblHighScoreValue;
	private JLabel lblSideViewSleigh;
	private JLabel lblRedBlock;
	private JLabel lblGreenBlock;
	private JLabel lblSideViewTree;
	public static void main(String[] args) {
		MenuDialog dialog = new MenuDialog();
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
		dialog.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent evt) {
				char character = evt.getKeyChar();
		        if (character == ' ') {
		        	//Start the game
		        	System.out.println("SPACE BAR WAS PRESSED");
		        	AnimationFrame animationFrame = new AnimationFrame();
		        	animationFrame.setVisible(true);
		        	dialog.setVisible(false);
		    		game = new Thread(){
		    			public void run(){
		    				animationFrame.animationLoop();
		    				System.out.println("run() complete");
		    			}
		    		};

		    		game.start();
		    		System.out.println("main() complete");
		        }
	            System.out.println("The SPACEBAR WAS PRESSED");
	        }
			public void keyTyped(KeyEvent evt) {
				char character = evt.getKeyChar();
		        if (character == ' ') {
		        	System.out.println("SPACE BAR WAS PRESSED");
		        	AnimationFrame animationFrame = new AnimationFrame();
		        	animationFrame.setVisible(true);
		        	dialog.setVisible(false);
		    		game = new Thread(){
		    			public void run(){
		    				animationFrame.animationLoop();
		    				System.out.println("run() complete");
		    			}
		    		};

		    		game.start();
		    		System.out.println("main() complete");
		        }
				System.out.println("The SPACEBAR WAS PRESSED");
			}
		});
	}
	public MenuDialog() {
		
		setResizable(false);
		setBounds(0, 0, 920, 920);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblHolidayDash = new JLabel("Holiday");
		lblHolidayDash.setForeground(Color.RED);
		lblHolidayDash.setBackground(Color.RED);
		lblHolidayDash.setFont(new Font("Dialog", Font.PLAIN, 70));
		lblHolidayDash.setBounds(225, 153, 254, 113);
		contentPanel.add(lblHolidayDash);
		
		//currentMax = Integer.max(currentMax, nextLength);
		
		JLabel lblDash = new JLabel("Dash");
		lblDash.setForeground(Color.GREEN);
		lblDash.setFont(new Font("Dialog", Font.PLAIN, 70));
		lblDash.setBackground(Color.RED);
		lblDash.setBounds(507, 153, 260, 113);
		contentPanel.add(lblDash);
		
		JLabel lblPressPTo = new JLabel("Press Space to Play", SwingConstants.CENTER);
		lblPressPTo.setFont(new Font("Lucida Grande", Font.PLAIN, 40));
		lblPressPTo.setBounds(6, 307, 920, 113);
		contentPanel.add(lblPressPTo);
		
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
		
		ImageIcon greenBlockIcon = new ImageIcon("res/Images/greenBlock.png");
		ImageIcon redBlockIcon = new ImageIcon("res/Images/enemie.png");
		ImageIcon sleighSideViewIcon = new ImageIcon("res/Images/sleighOne.png");
		lblSideViewSleigh = new JLabel();
		lblSideViewTree = new JLabel();
		lblGreenBlock = new JLabel();
		lblRedBlock = new JLabel();
		
		lblSideViewSleigh.setBounds(65, 471, 256, 256);
		contentPanel.add(lblSideViewSleigh);
		
		
		lblRedBlock.setBounds(654, 394, 113, 113);
		contentPanel.add(lblRedBlock);
		
		lblGreenBlock.setBounds(654, 733, 113, 113);
		contentPanel.add(lblGreenBlock);
		
		lblSideViewTree.setBounds(613, 511, 200, 200);
		contentPanel.add(lblSideViewTree);
		ImageIcon treeSideViewIcon = new ImageIcon();
		try {
			Image treeSideView = ImageIO.read(new File("res/Images/treeSideViewTwo.png"));
			Image treeSideViewResized = treeSideView.getScaledInstance(lblSideViewTree.getWidth(), lblSideViewTree.getHeight(), Image.SCALE_SMOOTH);
			treeSideViewIcon = new ImageIcon(treeSideViewResized);
		} catch (IOException e) {}
	
		lblSideViewSleigh.setIcon(sleighSideViewIcon);
		lblSideViewTree.setIcon(treeSideViewIcon);
		lblGreenBlock.setIcon(greenBlockIcon);
		lblRedBlock.setIcon(redBlockIcon);
		
	
		initHighScore();
	}
	private void initHighScore() {
		try {
			File highScoreFile = new File("src/HighScore");
			BufferedReader newBufferedReader = new BufferedReader(new FileReader(highScoreFile));
			int highScore = Integer.parseInt(newBufferedReader.readLine());
			lblHighScoreValue.setText(String.valueOf(highScore));
		} catch (IOException ex) {}
	}
}
