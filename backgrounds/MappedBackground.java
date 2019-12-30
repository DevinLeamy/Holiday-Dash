import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

public class MappedBackground extends Background{
	protected static int TILE_WIDTH = 75;
	protected static int TILE_HEIGHT = 75;
	
	private Image treeWall; //1
	private Image greenBarrier; //2
	private Image redBarrier; //3
	private Image path; //4
	private Image snowflake; //0
	private int maxCols = 10;
	private int maxRows = 300;
	
	private int[][] map;
	
	public MappedBackground() {
		try {
			this.treeWall = ImageIO.read(new File("res/Images/treeImage.jpg"));
			this.greenBarrier = ImageIO.read(new File("res/Images/greenBlock.png"));
			this.path = ImageIO.read(new File("res/Images/path.png"));
			this.redBarrier = ImageIO.read(new File("res/Images/enemie.png"));
			this.snowflake = ImageIO.read(new File("res/Images/snowflake.jpg"));
		} catch (IOException e) {
			System.err.println(e.toString());
		}
		map = generateMap(maxRows, maxCols);
	}
	public Tile getTile(int col, int row) {
		Image image = null;
		if (row < 0 || row >= maxRows || col < 0 || col >= maxCols) {
			image = snowflake;
		} else {
			switch(map[row][col]) {
				case 0:
					image = snowflake;
					break;
				case 1:
					image = treeWall;
					break;
				case 2:
					image = greenBarrier;
					break;
				case 3:
					image = redBarrier;
					break;
				case 4:
					image = path;
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
	private int[][] generateMap(int rows, int cols) {
		int[][] newMap = new int[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 1; j < cols-2; j++) {
				if (j == 1 || j == cols-3) {
					newMap[i][j] = 1;
				} else if (i % 4 == 0 && i < maxRows-10) {
					newMap[i][j] = getRandomInteger(1, 4);
				} else {
					newMap[i][j] = 4;
				}
			}
		}
		for (int i = 0; i < rows; i += 4) {
			boolean[] valid = {false, false, false}; //Has path, has red, has green
			for (int j = 2; j < cols-2; j++) {
				if (newMap[i][j] == 2) {valid[2] = true;}
				else if (newMap[i][j] == 3) {valid[1] = true;}
				else if (newMap[i][j] == 4) {valid[0] = true;}
			}
			if (valid[0] || (valid[1] && valid[2])) {continue;}
			else {
				int numOne = getRandomInteger(2, maxCols-4);
				newMap[i][numOne] = 3;
				int numTwo;
				while (true) {
					numTwo = getRandomInteger(2, maxCols-4);
					if (numTwo != numOne) {break;}
				}
				newMap[i][numTwo] = 2;
			}
		}
		
		return newMap;
	}
	private int getRandomInteger(int bottomBound, int topBound) {
		Random random = new Random();
		return random.nextInt((topBound - bottomBound) + 1) + bottomBound;
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
	public int[] getRowsAndCols() {
		return new int[] {maxRows, maxCols};
	}
	public ArrayList<StaticSprite> getBarriers() {
		ArrayList<StaticSprite> barriers = new ArrayList<>();
		for (int row = 0; row < maxRows; row++) {
			for (int col = 0; col < maxCols; col++) {
				if (map[row][col] == 1) {
//					System.out.println("ADDED WALL");
					barriers.add(new BarrierSprite(col * TILE_WIDTH, row * TILE_HEIGHT, (col + 1) * TILE_WIDTH, (row + 1) * TILE_HEIGHT, false, map[row][col]));
				} 
			}
		}
		return barriers;
	}
	public int getTileSize() {
		return TILE_WIDTH;
	}
	public ArrayList<EnemieSprite> getEnemies() {
		ArrayList<EnemieSprite> enemies = new ArrayList<>();
		for (int i = 0; i < maxRows; i++) {
			for (int j = 0; j < maxCols; j++) {
				if (map[i][j] == 3) {
//					System.out.println("ADDED redBlock");
					enemies.add(new EnemieSprite(j * TILE_WIDTH, i * TILE_HEIGHT, (j + 1) * TILE_WIDTH, (i + 1) * TILE_HEIGHT, false, map[i][j], false));
				} else if (map[i][j] == 2) {
//					System.out.println("ADDED WATER");
					enemies.add(new EnemieSprite(j * TILE_WIDTH, i * TILE_HEIGHT, (j + 1) * TILE_WIDTH, (i + 1) * TILE_HEIGHT, false, map[i][j], true));
				}
			}
		}
		return enemies;
	}
	
}
