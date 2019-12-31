
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class EnemieSprite extends StaticSprite {

	private static Image image = null;
	public boolean isGreen;
	public static int id;
	public int row;
	public int col;
	public EnemieSprite(double minX, double minY, double maxX, double maxY, boolean showImage, int id, boolean isGreen, int row, int col) {
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
		this.row = row;
		this.col = col;
	}
	
	@Override
	public Image getImage() {
		return image;
	}
}
