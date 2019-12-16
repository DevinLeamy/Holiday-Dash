
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BarrierSprite extends StaticSprite {

	private static Image image = null;
	public static int id;
	
	public BarrierSprite(double minX, double minY, double maxX, double maxY, boolean showImage, int id) {
		
		if (image == null && showImage) {
			try {
				image = ImageIO.read(new File("res/Images/wall.jpg"));
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
		if (id == 3) {
			//Trigger game over and restart menu
		} 
	}
}
