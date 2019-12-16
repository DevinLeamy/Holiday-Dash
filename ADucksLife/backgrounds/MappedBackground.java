import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class MappedBackground extends Background{
	protected static int TILE_WIDTH = 100;
	protected static int TILE_HEIGHT = 100;
	
	private Image wall; //1
	private Image greenBarrier; //2
	private Image redBlock; //3
	private Image grass; //4
	private Image epicScene; //0
	private int maxCols = 0;
	private int maxRows = 0;
	
	private int[][] map = {
			{0, 0, 1, 1, 1, 1, 1, 0, 0},
			{0, 0, 1, 4, 4, 4, 1, 0, 0},
			{0, 0, 1, 4, 4, 4, 1, 0, 0},
			{0, 0, 1, 4, 3, 4, 1, 0, 0},
			{0, 0, 1, 4, 4, 4, 1, 0, 0},
			{0, 0, 1, 3, 4, 4, 1, 0, 0},
			{0, 0, 1, 4, 4, 4, 1, 0, 0},
			{0, 0, 1, 4, 3, 4, 1, 0, 0},
			{0, 0, 1, 4, 4, 4, 1, 0, 0},
			{0, 0, 1, 4, 4, 3, 1, 0, 0},
			{0, 0, 1, 4, 4, 4, 1, 0, 0},
			{0, 0, 1, 4, 3, 4, 1, 0, 0},
			{0, 0, 1, 4, 4, 4, 1, 0, 0},
			{0, 0, 1, 4, 4, 4, 1, 0, 0},
			{0, 0, 1, 4, 2, 4, 1, 0, 0},
			{0, 0, 1, 4, 3, 4, 1, 0, 0},
			{0, 0, 1, 4, 4, 4, 1, 0, 0},
			{0, 0, 1, 4, 2, 4, 1, 0, 0},
			{0, 0, 1, 3, 4, 4, 1, 0, 0},
			{0, 0, 1, 4, 4, 3, 1, 0, 0},
			{0, 0, 1, 4, 4, 4, 1, 0, 0},
			{0, 0, 1, 4, 4, 4, 1, 0, 0},
			{0, 0, 1, 3, 4, 4, 1, 0, 0},
			{0, 0, 1, 4, 4, 4, 1, 0, 0},
			{0, 0, 1, 4, 4, 4, 1, 0, 0},
			{0, 0, 1, 4, 4, 4, 1, 0, 0},
			{0, 0, 1, 4, 4, 4, 1, 0, 0},
			{0, 0, 1, 4, 3, 4, 1, 0, 0},
			{0, 0, 1, 4, 4, 4, 1, 0, 0},
			{0, 0, 1, 3, 4, 4, 1, 0, 0},
			{0, 0, 1, 4, 4, 4, 1, 0, 0},
			{0, 0, 1, 4, 3, 4, 1, 0, 0},
			{0, 0, 1, 4, 4, 4, 1, 0, 0},
			{0, 0, 1, 4, 4, 3, 1, 0, 0},
			{0, 0, 1, 4, 4, 4, 1, 0, 0},
			{0, 0, 1, 2, 3, 4, 1, 0, 0},
			{0, 0, 1, 4, 4, 4, 1, 0, 0},
			{0, 0, 1, 4, 4, 4, 1, 0, 0},
			{0, 0, 1, 4, 4, 2, 1, 0, 0},
			{0, 0, 1, 4, 3, 4, 1, 0, 0},
			{0, 0, 1, 4, 4, 4, 1, 0, 0},
			{0, 0, 1, 4, 4, 4, 1, 0, 0},
			{0, 0, 1, 3, 4, 4, 1, 0, 0},
			{0, 0, 1, 4, 4, 3, 1, 0, 0},
			{0, 0, 1, 4, 4, 4, 1, 0, 0},
			{0, 0, 1, 4, 4, 4, 1, 0, 0},
			{0, 0, 1, 3, 4, 4, 1, 0, 0},
			{0, 0, 1, 4, 4, 4, 1, 0, 0},
			{0, 0, 1, 4, 4, 4, 1, 0, 0},
			{0, 0, 1, 4, 4, 4, 1, 0, 0},
			{0, 0, 1, 4, 4, 4, 1, 0, 0},
			{0, 0, 1, 4, 3, 4, 1, 0, 0},
			{0, 0, 1, 4, 4, 4, 1, 0, 0},
			{0, 0, 1, 3, 4, 4, 1, 0, 0},
			{0, 0, 1, 4, 4, 4, 1, 0, 0},
			{0, 0, 1, 4, 3, 4, 1, 0, 0},
			{0, 0, 1, 4, 4, 4, 1, 0, 0},
			{0, 0, 1, 4, 4, 3, 1, 0, 0},
			{0, 0, 1, 4, 4, 4, 1, 0, 0},
			{0, 0, 1, 4, 3, 4, 1, 0, 0},
			{0, 0, 1, 4, 4, 4, 1, 0, 0},
			{0, 0, 1, 4, 4, 4, 1, 0, 0},
			{0, 0, 1, 4, 4, 4, 1, 0, 0},
			{0, 0, 1, 4, 3, 4, 1, 0, 0},
			{0, 0, 1, 4, 4, 4, 1, 0, 0},
			{0, 0, 1, 4, 4, 4, 1, 0, 0},
			{0, 0, 1, 3, 4, 4, 1, 0, 0},
			{0, 0, 1, 4, 4, 3, 1, 0, 0},
			{0, 0, 1, 4, 4, 4, 1, 0, 0},
			{0, 0, 1, 4, 4, 4, 1, 0, 0},
			{0, 0, 1, 3, 4, 4, 1, 0, 0},
			{0, 0, 1, 4, 4, 4, 1, 0, 0},
			{0, 0, 1, 4, 4, 4, 1, 0, 0},
			{0, 0, 1, 4, 4, 4, 1, 0, 0},
			{0, 0, 1, 4, 4, 4, 1, 0, 0},
			{0, 0, 1, 4, 3, 4, 1, 0, 0},
			{0, 0, 1, 4, 4, 2, 1, 0, 0},
			{0, 0, 1, 3, 4, 4, 1, 0, 0},
			{0, 0, 1, 4, 4, 4, 1, 0, 0},
			{0, 0, 1, 4, 3, 4, 1, 0, 0},
			{0, 0, 1, 4, 4, 4, 1, 0, 0},
			{0, 0, 1, 4, 4, 3, 1, 0, 0},
			{0, 0, 1, 4, 4, 4, 1, 0, 0},
			{0, 0, 1, 4, 3, 4, 1, 0, 0},
			{0, 0, 1, 4, 4, 4, 1, 0, 0},
			{0, 0, 1, 4, 4, 4, 1, 0, 0},
			{0, 0, 1, 4, 4, 4, 1, 0, 0},
			{0, 0, 1, 4, 3, 4, 1, 0, 0},
			{0, 0, 1, 4, 4, 4, 1, 0, 0},
			{0, 0, 1, 4, 4, 4, 1, 0, 0},
			{0, 0, 1, 3, 4, 4, 1, 0, 0},
			{0, 0, 1, 4, 4, 3, 1, 0, 0},
			{0, 0, 1, 4, 4, 4, 1, 0, 0},
			{0, 0, 1, 4, 4, 4, 1, 0, 0},
			{0, 0, 1, 3, 4, 4, 1, 0, 0},
			{0, 0, 1, 4, 4, 4, 1, 0, 0},
			{0, 0, 1, 4, 4, 4, 1, 0, 0},
			
	};
	
	public MappedBackground() {
		try {
			this.wall = ImageIO.read(new File("res/Images/snowflake.jpg"));
			this.greenBarrier = ImageIO.read(new File("res/Images/greenBlock.png"));
			this.grass = ImageIO.read(new File("res/Images/path.png"));
			this.redBlock = ImageIO.read(new File("res/Images/enemie.png"));
			this.epicScene = ImageIO.read(new File("res/Images/snowflake.jpg"));
		} catch (IOException e) {
			System.err.println(e.toString());
		}
		maxRows = map.length;
		maxCols = map[0].length;
	}
	public Tile getTile(int col, int row) {
		Image image = null;
		if (row < 0 || row >= maxRows || col < 0 || col >= maxCols) {
			image = null;
		} else {
			switch(map[row][col]) {
				case 0:
					image = epicScene;
					break;
				case 1:
					image = wall;
					break;
				case 2:
					image = greenBarrier;
					break;
				case 3:
					image = redBlock;
					break;
				case 4:
					image = grass;
					break;
				default:
					image = null;
			}
		}
		int x = (col * TILE_WIDTH);
		int y = (row * TILE_HEIGHT);
		Tile newTile = new Tile(image, x, y, TILE_WIDTH, TILE_HEIGHT, false);
		return newTile;
	}
	
	public int getRow(int y) {
		int row = 0;
		if (TILE_HEIGHT != 0) {
			row = (y / TILE_HEIGHT);
			if (y < 0) {
				return row-1;
			} else {
				return row;
			}
		} else {
			return 0;
		}
	}
	
	public int getCol(int x) {
		int col = 0;
		if (TILE_WIDTH != 0) {
			col = (x / TILE_WIDTH);
			if (x < 0) {
				return col-1;
			} else {
				return col;
			}
		} else {
			return 0;
		}
	}
	public ArrayList<StaticSprite> getBarriers() {
		ArrayList<StaticSprite> barriers = new ArrayList<>();
		for (int row = 0; row < maxRows; row++) {
			for (int col = 0; col < maxCols; col++) {
				if (map[row][col] == 1) {
					System.out.println("ADDED WALL");
					barriers.add(new BarrierSprite(col * TILE_WIDTH, row * TILE_HEIGHT, (col + 1) * TILE_WIDTH, (row + 1) * TILE_HEIGHT, false, map[row][col]));
				} 
			}
		}
		return barriers;
	}
	public ArrayList<EnemieSprite> getEnemies() {
		ArrayList<EnemieSprite> enemies = new ArrayList<>();
		for (int i = 0; i < maxRows; i++) {
			for (int j = 0; j < maxCols; j++) {
				if (map[i][j] == 3) {
					System.out.println("ADDED redBlock");
					enemies.add(new EnemieSprite(j * TILE_WIDTH, i * TILE_HEIGHT, (j + 1) * TILE_WIDTH, (i + 1) * TILE_HEIGHT, false, map[i][j], false));
				} else if (map[i][j] == 2) {
					System.out.println("ADDED WATER");
					enemies.add(new EnemieSprite(j * TILE_WIDTH, i * TILE_HEIGHT, (j + 1) * TILE_WIDTH, (i + 1) * TILE_HEIGHT, false, map[i][j], true));
				}
			}
		}
		return enemies;
	}
	
}
