
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PathSprite extends StaticSprite {

	private static Image image = null;
	public static int id;
	public PathSprite(double minX, double minY, double maxX, double maxY, boolean showImage, int id, int row, int col) {
		
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
		this.row = row;
		this.col = col;
	}
	
	@Override
	public Image getImage() {
		return image;
	}
}