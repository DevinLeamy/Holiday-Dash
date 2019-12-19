import java.util.ArrayList;

public class MenuUniverse extends Universe{
	
	
	public MenuUniverse() {
		super();
		setXCenter(0);
		setYCenter(0);
		background = new WhiteBackground();
		TreeSprite treeLeft = new TreeSprite(100, 100, 200, 200, true);
		TreeSprite treeRight = new TreeSprite(100, 200, 200, 200, true);
		staticSprites.add(treeLeft);
		staticSprites.add(treeRight);	
	} 
	
	@Override
	public boolean centerOnPlayer() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void update(KeyboardInput keyboard, long actual_delta_time) {
		if (keyboard.keyDownOnce(27)) {
			complete = true;
		} else if (keyboard.keyDownOnce(80)) {
			complete = true;
		}
		
	}

}
