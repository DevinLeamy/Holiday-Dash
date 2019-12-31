import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class HolidayDashUniverse extends Universe{
	private static int highScore = 0;
	private static int score = 0;
	private boolean gameStarted = false;
	public HolidayDashUniverse() {
		super();
		background = new MappedBackground();
		ArrayList<StaticSprite> barriers =  ((MappedBackground)background).getBarriers();
		ArrayList<EnemieSprite> enemies = ((MappedBackground)background).getEnemies();
		int tileSize = background.getTileSize();
		int trackLength = tileSize * background.getRowsAndCols()[0];
		player = new PlayerSprite(338, trackLength - 350, true);
		player.setHeight(MappedBackground.TILE_HEIGHT);
		player.setWidth(MappedBackground.TILE_WIDTH*0.8);
		activeSprites.add(player);
		staticSprites.addAll(barriers);
		enemieSprites.addAll(enemies);
		initHighScore();
		isPaused = false;
	}
	public boolean centerOnPlayer() {
		return true;
	}
	public boolean hasGameStarted() {
		return gameStarted;
	}
	public void setStarted(boolean hasStarted) {
		gameStarted = hasStarted;
	}
	public static int getHighScore() {
		return highScore;
	}
	public static void setHighScore(int newHighScore) {
		highScore = newHighScore;
	}
	public static int getScore() {
		return score;
	}
	public static void setScore(int add) {
		score = score + add;
	}
	public void update(KeyboardInput keyboard, long actual_delta_time) {
		if (keyboard.keyDownOnce(27)) {
			complete = true;
		}
		updateSprites(keyboard, actual_delta_time);
		disposeSprites();
	}
	private void initHighScore() {
		try {
			File highScoreFile = new File("src/HighScore");
			BufferedReader newBufferedReader = new BufferedReader(new FileReader(highScoreFile));
			highScore = Integer.parseInt(newBufferedReader.readLine());
		} catch (IOException ex) {}
	}
}
