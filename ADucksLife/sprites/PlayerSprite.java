import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.imageio.ImageIO;

public class PlayerSprite extends ActiveSprite{
	private final double VELOCITY_SHIFT = 2000;
	private final double VELOCITY_UP = 125;
	private boolean isGreen = true;
	private boolean changeColorEventually = false;

	public Image imageA;
	public Image imageB;

	public PlayerSprite(double centerX, double centerY, boolean isGreen) {
		super();
		this.isGreen = true;
		this.setCenterX(centerX);
		this.setCenterY(centerY);

		if (imageA == null) {
			try {
				imageA = ImageIO.read(new File("res/Images/greenBlock.png"));
				imageB = ImageIO.read(new File("res/Images/enemie.png"));
				this.setHeight(this.imageA.getHeight(null) / 2);
				this.setWidth(this.imageA.getWidth(null) / 2);
			}
			catch (IOException e) {
				System.out.println(e.toString());
			}		
		}		
	}

	public Image getImage() {
		System.out.println(String.format("PlayerSprite: isGreen=%b", isGreen));
		if (isGreen) {
			return imageA;
		}
		else {
			return imageB;			
		}
	}

	@Override
	public void update(Universe universe, KeyboardInput keyboard, long actual_delta_time) {

 		double velocityX = 0;
		double velocityY = 0;
		
		if (keyboard.keyDownOnce(37)) {
			velocityX = -6115;
		}
		if (keyboard.keyDownOnce(39)) {
			velocityX += 6115;
		}
		if (keyboard.keyDownOnce(32)) {
			velocityY += -1000;
			double deltaY = 0.15 * velocityY;
			if (checkCollisionWithBarrier(universe, 0, deltaY) == false) {
				this.addCenterY(deltaY);
			}
		}

		double deltaX = actual_delta_time * 0.001 * velocityX;
		if (checkCollisionWithBarrier(universe, deltaX, 0) == false) {
			this.addCenterX(deltaX);
		}
		
		double deltaY = -1;
		System.out.println(deltaY);
		if (checkCollisionWithBarrier(universe, 0, deltaY) == false) {
			this.addCenterY(deltaY);
		}
	}

	private boolean checkCollisionWithBarrier(Universe universe, double deltaX, double deltaY) {
		boolean colliding = false;
		boolean changeColor = false;
		
		for (StaticSprite staticSprite : universe.getStaticSprites()) {
			if (staticSprite instanceof BarrierSprite) {
				if (CollisionDetection.overlaps(this.getMinX() + deltaX, this.getMinY() + deltaY, 
						this.getMaxX()  + deltaX, this.getMaxY() + deltaY, 
						staticSprite.getMinX(),staticSprite.getMinY(), 
						staticSprite.getMaxX(), staticSprite.getMaxY())) {
					System.out.println("COLLIDING WITH WALL");
					colliding = true;
					break;					
				}
			} 
		}	
		for (EnemieSprite enemieSprite : universe.getEnemieSprites()) {
			if (CollisionDetection.overlaps(this.getMinX() + deltaX, this.getMinY() + deltaY, 
				this.getMaxX()  + deltaX, this.getMaxY() + deltaY, 
				enemieSprite.getMinX(),enemieSprite.getMinY(), 
				enemieSprite.getMaxX(), enemieSprite.getMaxY())) {
				System.out.println("COLLIDING WITH WOLF");
				if (enemieSprite.isGreen == isGreen) {
					colliding = true;
					universe.setComplete(true);
					break;					
				} else {
					changeColor = true;
				}
			} 
		}
		
		if (!changeColor && changeColorEventually) {
			isGreen = !isGreen;
			changeColorEventually = false;
		} else if (changeColor) {
			changeColorEventually = true;
		} else {
			changeColorEventually = false;
		}
		System.out.println("IS COLLIDING = " + colliding);
		return colliding;		
	}

}
