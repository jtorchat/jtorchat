package alpha;

public class ThreadManager {

	public static final int NORMAL = 0;
<<<<<<< HEAD
	// private static int threadCount;
	public static final int DAEMON = 1;

	/*
	 * Intend to do something with type param, but for now, useless 5/12/11 - No intention to use type param, too lazy to remove it
=======
//	private static int threadCount;
	public static final int DAEMON = 1;

	/*
	 * Intend to do something with type param, but for now, useless
	 * 5/12/11 - No intention to use type param, too lazy to remove it
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
	 */
	public static void registerWork(int type, final Runnable runnable, String s, String name) {
		if (s != null)
			Logger.log(Logger.INFO, "ThreadManager", s);
<<<<<<< HEAD
		// System.out.println("[" + ThreadManager.class.getCanonicalName() + "] " + s);
=======
//			System.out.println("[" + ThreadManager.class.getCanonicalName() + "] " + s);
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
		Runnable r = new Runnable() {

			@Override
			public void run() {
<<<<<<< HEAD
				// threadCount++;
				// System.out.println("threadCount++: " + threadCount);
=======
//				threadCount++;
//				System.out.println("threadCount++: " + threadCount);
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
				try {
					runnable.run();
				} catch (Exception e) {
					// one hopes this doesnt happen
					e.printStackTrace();
				}
<<<<<<< HEAD
				// threadCount--;
				// System.out.println("threadCount--: " + threadCount);
=======
//				threadCount--;
//				System.out.println("threadCount--: " + threadCount);
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
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
