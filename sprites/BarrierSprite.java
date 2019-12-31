
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BarrierSprite extends StaticSprite {

	private static Image image = null;
	public static int id;
	public int row;
	public int col;
	
	public BarrierSprite(double minX, double minY, double maxX, double maxY, boolean showImage, int id, int row, int col) {
		
		if (image == null && showImage) {
			try {
				image = ImageIO.read(new File("res/Images/wall.jpg"));
			}
			catch (IOException e) {
				e.printStackTrace();
			}		
		}
		this.row = row;
		this.col = col;
		this.minX = minX;
		this.minY = minY;
		this.maxX = maxX;
		this.maxY = maxY;
		this.showImage = showImage;
		this.id = id;
	}
	
	@Override
	public Image getImage() {
		return image;
	}
}
