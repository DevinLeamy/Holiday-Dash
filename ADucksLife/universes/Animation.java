
public class Animation {

	private static int universeCount = 0;
	
	public static int getUniverseCount() {
		return universeCount;
	}

	public static void setUniverseCount(int count) {
		Animation.universeCount = count;
	}

	public static Universe getNextUniverse() {
		universeCount++;
		
		return new ADucksLifeUniverse();

	}
	
}
