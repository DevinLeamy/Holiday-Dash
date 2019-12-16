import java.util.ArrayList;

public class ADucksLifeUniverse extends Universe{
	public ADucksLifeUniverse() {
		super();
		
		background = new MappedBackground();
		ArrayList<StaticSprite> barriers =  ((MappedBackground)background).getBarriers();
		ArrayList<EnemieSprite> enemies = ((MappedBackground)background).getEnemies();
		duck = new PlayerSprite(350, 4000, true);
		duck.setHeight(MappedBackground.TILE_HEIGHT * 0.8);
		duck.setWidth(MappedBackground.TILE_WIDTH * 0.8);
		activeSprites.add(duck);
		staticSprites.addAll(barriers);
		enemieSprites.addAll(enemies);
	}
	public boolean centerOnPlayer() {
		return true;
	}
	public void update(KeyboardInput keyboard, long actual_delta_time) {

		if (keyboard.keyDownOnce(27)) {
			complete = true;
		}
		
		updateSprites(keyboard, actual_delta_time);
		disposeSprites();
	}
}