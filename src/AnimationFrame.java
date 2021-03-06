import javax.print.attribute.standard.Media;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.Dialog.ModalityType;
import java.util.ArrayList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
//Press Space to Dash *Blinking*
public class AnimationFrame extends JFrame {

	final public static int FRAMES_PER_SECOND = 60;
	final public static int SCREEN_HEIGHT = 900;
	final public static int SCREEN_WIDTH = 900;

	private int xpCenter = SCREEN_WIDTH / 2;
	private int ypCenter = SCREEN_HEIGHT / 2;

	private double scale = 1;
	//point in universe on which the screen will center
	private double xCenter = 0;		
	private double yCenter = 0;

	private JPanel panel = null;
	private JButton btnPauseRun;
	private JLabel lblHighScore;
	private JLabel lblHighScoreValue;
	private JLabel lblScore;
	private JLabel lblScoreValue;
	public JLabel lblBegin;
	public static Clip gamePlayingClip;
	public static AudioInputStream gamePlayingInputStream;
	public static boolean gameSoundIsPlaying = false;

	private static Thread game;
	private static boolean stop = false;

	private long current_time = 0;								//MILLISECONDS
	private long next_refresh_time = 0;							//MILLISECONDS
	private long last_refresh_time = 0;
	private long minimum_delta_time = 1000 / FRAMES_PER_SECOND;	//MILLISECONDS
	private long actual_delta_time = 0;							//MILLISECONDS
	private long elapsed_time = 0;

	private KeyboardInput keyboard = new KeyboardInput();
	private HolidayDashUniverse universe = null;

	//local (and direct references to various objects in universe ... should reduce lag by avoiding dynamic lookup
	private ActiveSprite player1 = null;
	private ArrayList<ActiveSprite> activeSprites = null;
	private ArrayList<StaticSprite> staticSprites = null;
	private Background background = null;
	boolean centreOnPlayer = false;
	int universeLevel = 1;
	private boolean fadeOut = false;

	public AnimationFrame()
	{
		super("");
		this.setFocusable(true);
		this.setSize(SCREEN_WIDTH + 20, SCREEN_HEIGHT + 36);

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				this_windowClosing(e);
			}
		});

		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				keyboard.keyPressed(arg0);
			}
			@Override
			public void keyReleased(KeyEvent arg0) {
				keyboard.keyReleased(arg0);
			}
		});

		Container cp = getContentPane();
		cp.setBackground(Color.WHITE);
		cp.setLayout(null);

		panel = new DrawPanel();
		panel.setLayout(null);
		panel.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		getContentPane().add(panel, BorderLayout.CENTER);

		btnPauseRun = new JButton("||");
		btnPauseRun.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnPauseRun_mouseClicked(arg0);
			}
		});

		btnPauseRun.setFont(new Font("Dialog", Font.PLAIN, 30));
		btnPauseRun.setBounds(20, 20, 75, 75);
		btnPauseRun.setFocusable(false);
		getContentPane().add(btnPauseRun);
		getContentPane().setComponentZOrder(btnPauseRun, 0);

		lblHighScore = new JLabel("High Score ");
		lblHighScore.setForeground(Color.BLACK);
		lblHighScore.setFont(new Font("Dialog", Font.PLAIN, 30));
		lblHighScore.setBounds(120, 22, 240, 40);
		getContentPane().add(lblHighScore);
		getContentPane().setComponentZOrder(lblHighScore, 0);

		lblHighScoreValue = new JLabel("0");
		lblHighScoreValue.setForeground(Color.BLACK);
		lblHighScoreValue.setFont(new Font("Dialog", Font.PLAIN, 30));
		lblHighScoreValue.setBounds(300, 22, 148, 40);
		getContentPane().add(lblHighScoreValue);
		getContentPane().setComponentZOrder(lblHighScoreValue, 0);

		lblScore = new JLabel("Score: ");
		lblScore.setForeground(Color.BLACK);
		lblScore.setFont(new Font("Dailog", Font.PLAIN, 30));
		lblScore.setBounds(528, 22, 128, 40);
		getContentPane().add(lblScore);
		getContentPane().setComponentZOrder(lblScore, 0);

		lblScoreValue = new JLabel("0");
		lblScoreValue.setForeground(Color.BLACK);
		lblScoreValue.setFont(new Font("Dialog", Font.PLAIN, 30));
		lblScoreValue.setBounds(672, 22, 108, 40);
		getContentPane().add(lblScoreValue);
		getContentPane().setComponentZOrder(lblScoreValue, 0);
		
		lblBegin = new JLabel("Press Space to Dash", SwingConstants.CENTER);
		lblBegin.setForeground(Color.RED);
		lblBegin.setFont(new Font("Dialog", Font.PLAIN, 30));
		lblBegin.setBounds(0, 500, 900, 550);
		getContentPane().add(lblBegin);
		getContentPane().setComponentZOrder(lblBegin, 0);

		try {
			File clipFile = new File("res/Sound/gamePlayingSound.wav");
			gamePlayingInputStream =  
	                AudioSystem.getAudioInputStream(clipFile.getAbsoluteFile()); 
			gamePlayingClip = AudioSystem.getClip();
		} catch (Exception e) {
		}
		
	}

	public void animationLoop() {

		universe = Animation.getNextUniverse();

		while (stop == false && universe != null) {

			activeSprites = universe.getActiveSprites();
			staticSprites = universe.getStaticSprites();
			player1 = universe.getPlayer1();
			background = universe.getBackground();
			universeLevel = Animation.getUniverseCount();
			centreOnPlayer = universe.centerOnPlayer();
			this.scale = universe.getScale();
			this.xCenter = universe.getXCenter();
			this.yCenter = universe.getYCenter();
			if (universe instanceof HolidayDashUniverse) {
				lblHighScoreValue.setText(String.valueOf(HolidayDashUniverse.getHighScore()));
			}

			// main game loop
			while (stop == false && universe.isComplete() == false) {

				//adapted from http://www.java-gaming.org/index.php?topic=24220.0
				last_refresh_time = System.currentTimeMillis();
				next_refresh_time = current_time + minimum_delta_time;

				//sleep until the next refresh time
				while (current_time < next_refresh_time)
				{
					//allow other threads (i.e. the Swing thread) to do its work
					Thread.yield();

					try {
						Thread.sleep(1);
					}
					catch(Exception e) {    					
					} 

					//track current time
					current_time = System.currentTimeMillis();
				}

				//read input
				keyboard.poll();
				handleKeyboardInput();

				//UPDATE STATE
				updateTime();
				universe.update(keyboard, actual_delta_time);
				updatePlayerVelocity();
				updateScore();
				updateBeginOpacity();

				//REFRESH
				this.repaint();   
				System.out.println("------------------------------------------");
				System.out.println("Universe Complete: " + universe.isComplete());
				System.out.println("Universe Paused: " + universe.isPaused);
				System.out.println("Music Playing: " + gameSoundIsPlaying);
			}
			GameOverPage gameOverPage;
			try {
				gamePlayingClip.stop();
				gameSoundIsPlaying = false;
				File clipFile = new File("res/Sound/crashSound.wav");
				AudioInputStream crashInputStream =  
		                AudioSystem.getAudioInputStream(clipFile.getAbsoluteFile()); 
				Clip crashClip = AudioSystem.getClip();
				crashClip.open(crashInputStream);
				crashClip.start();
				try {
					Thread.sleep(1500);
				} catch (Exception e) {
					e.printStackTrace();
				}
				crashClip.stop();
				gameOverPage = new GameOverPage(universe.player.isGreen, universe.player.collidedWithBlock(), universe.getNewHighScore());
				gameOverPage.setModalityType(ModalityType.APPLICATION_MODAL);
				gameOverPage.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				gameOverPage.setVisible(true);
				if (gameOverPage.isOk()) {
					universe = Animation.getNextUniverse();
					HolidayDashUniverse.setScore(-HolidayDashUniverse.getScore());
				} else {
					universe = null;
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		System.out.println("animation complete");
		AudioPlayer.setStopAll(true);
		dispose();	
	}
	private void updateScore() {
		int score = HolidayDashUniverse.getScore();
		int highScore = HolidayDashUniverse.getHighScore();
		lblScoreValue.setText(String.valueOf(score));
		if (score > highScore) {
			HolidayDashUniverse.setHighScore(score);
			lblHighScoreValue.setText(String.valueOf(score));
			universe.gotNewHighScore();
		}
	}
	private void updateTime() {
		if (universe instanceof HolidayDashUniverse) {
			current_time = System.currentTimeMillis();
			actual_delta_time = (universe.isPaused ? 0 : current_time - last_refresh_time);
			last_refresh_time = current_time;
			elapsed_time += actual_delta_time;
		}

	}
	private void updateBeginOpacity() {
		//
		Color color = lblBegin.getForeground();
		int deltaAlpha = 3;
		int alpha = color.getAlpha();
		if (alpha == 0 && universe.hasGameStarted()) {return;}
		if (universe.hasGameStarted()) {
			lblBegin.setForeground(new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha - 1));
		} else {
			if (alpha < 3) {
				fadeOut = false;
			} else if (alpha > 252) {
				fadeOut = true;
			}
			if (fadeOut) {
				lblBegin.setForeground(new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha - deltaAlpha));
			} else {
				lblBegin.setForeground(new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha + deltaAlpha));
			}
		}
	}
	protected void btnPauseRun_mouseClicked(MouseEvent arg0) {
		if (!universe.hasGameStarted()) {return;}
		if (universe.isPaused) {
			this.btnPauseRun.setText("||");
			universe.isPaused = false;
	        gamePlayingClip.start(); 
	        gameSoundIsPlaying = true;
		}
		else {
			this.btnPauseRun.setText(">");
			universe.isPaused = true;
			gamePlayingClip.stop();
			gameSoundIsPlaying = false;
			
		}
	}
	private void updatePlayerVelocity() {
		MappedBackground newMappedBackground = new MappedBackground();
		int totalTrackLength = MappedBackground.TILE_HEIGHT * newMappedBackground.getRowsAndCols()[0];
		player1.setSpeed(4.0 + ((totalTrackLength - player1.getCenterY()) / 3000)); 
	}
	private void handleKeyboardInput() {
		if (keyboard.keyDown(80) && ! universe.isPaused) {
			btnPauseRun_mouseClicked(null);	
		}
		if (keyboard.keyDown(79) && universe.isPaused ) {
			btnPauseRun_mouseClicked(null);
		}
		if (keyboard.keyDown(112)) {
			scale *= 1.01;
		}
		if (keyboard.keyDown(113)) {
			scale /= 1.01;
		}
		
	}

	class DrawPanel extends JPanel {

		public void paintComponent(Graphics g)
		{	
			
//			System.out.println("paintComponent");
			if (universe == null) {
				return;
			}

			if (player1 != null && centreOnPlayer) {
				xCenter = player1.getCenterX();
				yCenter = player1.getCenterY();     
			}

			paintBackground(g, background);

			for (StaticSprite staticSprite : staticSprites) {
				if (staticSprite.getShowImage()) {
					if (staticSprite.getImage() != null) {
						g.drawImage(staticSprite.getImage(), translateX(staticSprite.getMinX()), translateY(staticSprite.getMinY()), scaleX(staticSprite.getWidth()), scaleY(staticSprite.getHeight()), null);
					}
					else {
						g.setColor(Color.RED);
						g.fillRect(translateX(staticSprite.getMinX()), translateY(staticSprite.getMinY()),scaleX(staticSprite.getWidth()), scaleY(staticSprite.getHeight()));					
					}
				}
			}

			for (ActiveSprite activeSprite : activeSprites) {
				if (activeSprite.getImage() != null) {
					g.drawImage(activeSprite.getImage(), translateX(activeSprite.getMinX()), translateY(activeSprite.getMinY()), scaleX(activeSprite.getWidth()), scaleY(activeSprite.getHeight()), null);
				}
				else {
					g.setColor(Color.BLUE);
					g.fillRect(translateX(scale * (activeSprite.getMinX())), translateY(activeSprite.getMinY()), scaleX(activeSprite.getWidth()), scaleY(activeSprite.getHeight()));					
				}
			}

		}
		
		private int translateX(double x) {
			return xpCenter + scaleX(x - xCenter);
		}
		
		private int scaleX(double x) {
			return (int) Math.round(scale * x);
		}
		private int translateY(double y) {
			return ypCenter + scaleY(y - yCenter);
		}		
		private int scaleY(double y) {
			return (int) Math.round(scale * y);
		}

		private void paintBackground(Graphics g, Background background) {

			if ((g == null) || (background == null)) {
				return;
			}
			
			//what tile covers the top-left corner?
			double xTopLeft = ( xCenter - (xpCenter / scale));
			double yTopLeft =  (yCenter - (ypCenter / scale)) ;
			
			int row = background.getRow((int)yTopLeft);
			int col = background.getCol((int)xTopLeft);
			Tile tile = null;
			boolean rowDrawn = false;
			boolean screenDrawn = false;
			while (screenDrawn == false) {
				while (rowDrawn == false) {
					tile = background.getTile(col, row);
					if (tile.getWidth() <= 0 || tile.getHeight() <= 0) {
						//no increase in width; will cause an infinite loop, so consider this screen to be done
						g.setColor(Color.GRAY);
						g.fillRect(0,0, SCREEN_WIDTH, SCREEN_HEIGHT);					
						rowDrawn = true;
						screenDrawn = true;						
					}
					else {
						Tile nextTile = background.getTile(col+1, row+1);
						int width = translateX(nextTile.getMinX()) - translateX(tile.getMinX());
						int height = translateY(nextTile.getMinY()) - translateY(tile.getMinY());
						g.drawImage(tile.getImage(), translateX(tile.getMinX()), translateY(tile.getMinY()), width, height, null);
						//System.out.println(String.format("x = %d, y = %d, width = %d, height = %d" , translateX(tile.getMinX()), translateY(tile.getMinY()), width, height));
					}					
					//does the RHE of this tile extend past the RHE of the visible area?
					if (translateX(tile.getMinX() + tile.getWidth()) > SCREEN_WIDTH || tile.isOutOfBounds()) {
						rowDrawn = true;
					}
					else {
						col++;
					}
				}
				//does the bottom edge of this tile extend past the bottom edge of the visible area?
				if (translateY(tile.getMinY() + tile.getHeight()) > SCREEN_HEIGHT || tile.isOutOfBounds()) {
					screenDrawn = true;
				}
				else {
					//TODO - should be passing in a double, as this represents a universe coordinate
					col = background.getCol((int)xTopLeft);
					row++;
					rowDrawn = false;
				}
			}
		}				
	}
	protected void this_windowClosing(WindowEvent e) {
		System.out.println("windowClosing()");
		stop = true;
		dispose();	
	}
}

