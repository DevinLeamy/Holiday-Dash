
public class Animation {

	private static int universeCount = 0;
	
	public static int getUniverseCount() {
		return universeCount;
	}

	public static void setUniverseCount(int count) {
		Animation.universeCount = count;
	}

	public static Universe getNextUniverse() {
//		if (universeCount == 0) {
//			return new MenuUniverse();
//		}
		universeCount++;
		return new HolidayDashUniverse();

	}
	
}
