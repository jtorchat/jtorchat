package alpha;

public class ThreadManager {

	public static final int NORMAL = 0;
//	private static int threadCount;
	public static final int DAEMON = 1;

	/*
	 * Intend to do something with type param, but for now, useless
	 * 5/12/11 - No intention to use type param, too lazy to remove it
	 */
	public static void registerWork(int type, final Runnable runnable, String s, String name) {
		if (s != null)
			Logger.log(Logger.INFO, "ThreadManager", s);
//			System.out.println("[" + ThreadManager.class.getCanonicalName() + "] " + s);
		Runnable r = new Runnable() {

			@Override
			public void run() {
//				threadCount++;
//				System.out.println("threadCount++: " + threadCount);
				try {
					runnable.run();
				} catch (Exception e) {
					// one hopes this doesnt happen
					e.printStackTrace();
				}
//				threadCount--;
//				System.out.println("threadCount--: " + threadCount);
			}
		};
		Thread t;
		if (name != null)
			t = new Thread(r, name);
		else
			t = new Thread(r);
		t.setDaemon(type == DAEMON);
		t.start();
	}

}
