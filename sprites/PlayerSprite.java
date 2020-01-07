import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.swing.JLabel;

public class PlayerSprite extends ActiveSprite{
	private final double VELOCITY_SHIFT = 75;
	private final double VELOCITY_UP = 300;
	public double VELOCITY_FORWARD = 4.0;
	public boolean isGreen = false;
	private boolean changeColorEventually = false;
	private ArrayList<int[]> snowCovered = new ArrayList<>();
	private boolean collidedWithBlock = false;

	private Image greenSleigh;
	private Image redSleigh;
	private Image originalSleigh;
	

	public PlayerSprite(double centerX, double centerY, boolean isGreen) {
		super();
		this.isGreen = false;
		this.setCenterX(centerX);
		this.setCenterY(centerY);

		if (greenSleigh == null) {
			try {
				greenSleigh = ImageIO.read(new File("res/Images/greenSleigh.png"));
				redSleigh = ImageIO.read(new File("res/Images/redSleigh.png"));
				originalSleigh = ImageIO.read(new File("res/Images/GenericSleigh.png"));
				this.setHeight(this.greenSleigh.getHeight(null) / 2);
				this.setWidth(this.greenSleigh.getWidth(null) / 2);
			}
			catch (IOException e) {
				System.out.println(e.toString());
			}		
		}		
	}

	public Image getImage() {
//		System.out.println(String.format("PlayerSprite: isGreen=%b", isGreen));
		if (isGreen) {
			return greenSleigh;
		}
		else {
			return redSleigh;			
		}
	}
	@Override
	public void update(Universe universe, KeyboardInput keyboard, long actual_delta_time) {
		if (universe.isPaused) {
			return;
		}
		double velocityY = 0;
		double deltaX = 0;
		if (keyboard.keyDownOnce(37) && universe.hasGameStarted()) {
			deltaX = -VELOCITY_SHIFT;
		}
		if (keyboard.keyDownOnce(39) && universe.hasGameStarted()) {
			deltaX = VELOCITY_SHIFT;
		}
		if (keyboard.keyDownOnce(32)) {
			if (!universe.hasGameStarted()) {
				universe.setStarted(true);
				try {
					File clipFile = new File("res/Sound/gamePlayingSound.wav");
					AnimationFrame.gamePlayingInputStream =  
			                AudioSystem.getAudioInputStream(clipFile.getAbsoluteFile()); 
					AnimationFrame.gamePlayingClip = AudioSystem.getClip();
					AnimationFrame.gamePlayingClip.open(AnimationFrame.gamePlayingInputStream);
					AnimationFrame.gamePlayingClip.loop(AnimationFrame.gamePlayingClip.LOOP_CONTINUOUSLY);
					AnimationFrame.gamePlayingClip.start();
					AnimationFrame.gameSoundIsPlaying = true;
				} catch(Exception e) {e.printStackTrace();}
			}
			velocityY += -VELOCITY_UP;
			double deltaY = 0.15 * velocityY;
			HolidayDashUniverse.setScore(1);
			if (checkCollisionWithBarrier(universe, 0, deltaY) == false) {
				this.addCenterY(deltaY);
			}
		}
		if (checkCollisionWithBarrier(universe, deltaX, 0) == false) {
			this.addCenterX(deltaX);
		}
		
		double deltaY = (universe.hasGameStarted())? -VELOCITY_FORWARD: 0;
		if (checkCollisionWithBarrier(universe, 0, deltaY) == false) {
			this.addCenterY(deltaY);
		}
	}
	public double getSpeed() {
		return VELOCITY_FORWARD;
	}
	public void setSpeed(double newSpeed) {
		VELOCITY_FORWARD = newSpeed;
	}
	public boolean collidedWithBlock() {
		return collidedWithBlock;
	}
	private boolean checkCollisionWithBarrier(Universe universe, double deltaX, double deltaY) {
		boolean colliding = false;
		boolean changeColor = false;
		ArrayList<int[]> newSnowCovered = new ArrayList<>();
		for (StaticSprite staticSprite : universe.getStaticSprites()) {
			if (staticSprite instanceof BarrierSprite) {
				if (CollisionDetection.overlaps(this.getMinX() + deltaX, this.getMinY() + deltaY, 
						this.getMaxX()  + deltaX, this.getMaxY() + deltaY, 
						staticSprite.getMinX(),staticSprite.getMinY(), 
						staticSprite.getMaxX(), staticSprite.getMaxY())) {
//					System.out.println("COLLIDING WITH WALL");
					colliding = true;
					universe.setComplete(true);
					break;					
				}
			}  else if (staticSprite instanceof PathSprite) {
				if (CollisionDetection.overlaps(this.getMinX() + deltaX, this.getMinY() + deltaY, 
						this.getMaxX()  + deltaX, this.getMaxY() + deltaY, 
						staticSprite.getMinX(),staticSprite.getMinY(), 
						staticSprite.getMaxX(), staticSprite.getMaxY())) {
//					universe.background.changeTile(staticSprite.row, staticSprite.col, 7);				
					newSnowCovered.add(new int[] {staticSprite.row, staticSprite.col, 7});
				}
			}
		}	
		for (EnemieSprite enemieSprite : universe.getEnemieSprites()) {
			if (CollisionDetection.overlaps(this.getMinX() + deltaX, this.getMinY() + deltaY, 
				this.getMaxX()  + deltaX, this.getMaxY() + deltaY, 
				enemieSprite.getMinX(),enemieSprite.getMinY(), 
				enemieSprite.getMaxX(), enemieSprite.getMaxY())) {
//				System.out.println("COLLIDING WITH WOLF");
				if (enemieSprite.isGreen == isGreen) {
					colliding = true;
					universe.setComplete(true);
					collidedWithBlock = true;
					break;					
				} else {
//					universe.background.changeTile(enemieSprite.row,  enemieSprite.col, (enemieSprite.isGreen)? 5 : 6);
					newSnowCovered.add(new int[] {enemieSprite.row, enemieSprite.col, (enemieSprite.isGreen)? 5 : 6});
					changeColor = true;
				}
			} 
		}
		if (snowCovered.size() != 0) {
			for (int[] coords : snowCovered) {
				boolean found = false;
				for (int[] otherCoords : newSnowCovered) {
					if (coords[0] == otherCoords[0] && coords[1] == otherCoords[1]) {
						found = true;
					}
				}
				if (!found) {
					universe.background.changeTile(coords[0], coords[1], coords[2]);
				}
			}
		}
		snowCovered = newSnowCovered;
		
		if (!changeColor && changeColorEventually && !colliding) {
			isGreen = !isGreen;
			changeColorEventually = false;
			HolidayDashUniverse.setScore(10);
		} else if (changeColor) {
			changeColorEventually = true;
		} else {
			changeColorEventually = false;
		}
//		System.out.println("IS COLLIDING = " + colliding);
		return colliding;		
	}

}
