package alpha;

import gui.ChatWindow;
import gui.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

<<<<<<< HEAD
=======

>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
public class Logger {
	public static final PrintStream oldOut;
	public static final int FATAL = 0;
	public static final int SEVERE = 1;
	public static final int WARNING = 2;
	public static final int NOTICE = 3;
	public static final int INFO = 4;
	public static final int DEBUG = 5;

	private static int logLevel = DEBUG;
	private static boolean usingLog;
	private static Object LOCK = new Object();
<<<<<<< HEAD
	private static boolean override = true;

=======
	private static boolean override = true; 
	
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
	static {
		oldOut = System.out;
		boolean hasGUI = hasGUI();
		if (hasGUI) {
			System.setOut(new PrintStream(new OutputStream() {
<<<<<<< HEAD

=======
	
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
				@Override
				public void write(int b) throws IOException {
					Log.updateOut(String.valueOf((char) b));
				}
<<<<<<< HEAD

			}));
			System.setErr(new PrintStream(new OutputStream() {

				@Override
				public void write(int b) throws IOException {
					Log.updateErr(String.valueOf((char) b));
					// oldOut.print(String.valueOf((char) b));
				}

=======
				
			}));
			System.setErr(new PrintStream(new OutputStream() {
	
				@Override
				public void write(int b) throws IOException {
					Log.updateErr(String.valueOf((char) b));
//					oldOut.print(String.valueOf((char) b));
				}
				
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
			}));
			usingLog = true; // reffering to Log class
		}
	}

	public static void log(int i, String s, String string) {
<<<<<<< HEAD
		synchronized (LOCK) {
=======
		synchronized(LOCK) {
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
			if (i == FATAL) {
				System.setErr(oldOut);
				System.setOut(oldOut);
				usingLog = false;
			}
			if (logLevel >= i) {
				if (usingLog) {
					try {
						Log.append("[" + ChatWindow.getTime() + " - ", "Time Stamp");
						Log.append(s + "] ", "Class-t");
						Log.append(string + "\n", null);
					} catch (Exception e) {
						System.setErr(oldOut);
						System.setOut(oldOut);
						usingLog = false;
					}
<<<<<<< HEAD
				}
=======
				} 
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
				if (!usingLog || override) {
					if (logLevel <= WARNING)
						oldOut.println("Log: !{" + s + "}! " + string);
					else
						oldOut.println("Log: {" + s + "} " + string);
					if (i <= SEVERE) // dump stack if SEVERE or greater (actually lower, but you get the point)
						Thread.dumpStack();
				}
			}
		}
	}
<<<<<<< HEAD

=======
	
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
	private static boolean hasGUI() {
		try {
			Class.forName("gui.Gui");
			return true;
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
		return false;
	}
<<<<<<< HEAD

	public static void log(int i, Object o, String string) {
		synchronized (LOCK) {
=======
	
	public static void log(int i, Object o, String string) {
		synchronized(LOCK) {
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
			if (logLevel >= i) {
				if (i == FATAL) {
					System.setErr(oldOut);
					System.setOut(oldOut);
					usingLog = false;
				}
				if (usingLog) {
					try {
						Log.append("[" + ChatWindow.getTime() + " - ", "Time Stamp");
						Log.append(o.getClass().getName() + "] ", "Class-c");
						Log.append(string + "\n", null);
					} catch (Exception e) {
						System.setErr(oldOut);
						System.setOut(oldOut);
						usingLog = false;
					}
<<<<<<< HEAD
				}
=======
				} 
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
				if (!usingLog || override) {
					if (logLevel <= WARNING)
						oldOut.println("Log: ![" + o.getClass().getCanonicalName() + "]! " + string);
					else
						oldOut.println("Log: [" + o.getClass().getCanonicalName() + "] " + string);
					if (i <= SEVERE) // dump stack if SEVERE or greater (actually lower, but you get the point)
						Thread.dumpStack();
				}
			}
		}
	}

	public static void setOverride(boolean o) {
		override = o;
	}

	public static void stopGLog() {
		usingLog = false;
	}

}
