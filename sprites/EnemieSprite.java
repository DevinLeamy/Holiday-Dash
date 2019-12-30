
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class EnemieSprite extends StaticSprite {

	private static Image image = null;
	public boolean isGreen;
	public static int id;
	
	public EnemieSprite(double minX, double minY, double maxX, double maxY, boolean showImage, int id, boolean isGreen) {
		this.isGreen = isGreen;
		if (image == null && showImage) {
			try {
				image = ImageIO.read(new File("res/Images/enemie.png"));
			}
			catch (IOException e) {
				e.printStackTrace();
			}		
		}
		
		this.minX = minX;
		this.minY = minY;
		this.maxX = maxX;
		this.maxY = maxY;
		this.showImage = showImage;
		this.id = id;
		handleCollision(id);
	}
	
	@Override
	public Image getImage() {
		return image;
	}
	public static void handleCollision(int id) {
		//I want this to be triggered when the duckSprite comes in contact with the wolf sprite
		if (id == 3) {
			//Trigger game over and restart menu
		} 
	}
}
