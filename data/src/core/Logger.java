package core;

import gui.GuiChatWindow;
import gui.GuiLog;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;


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
	private static boolean override = true; 
	
	static {
		oldOut = System.out;
		boolean hasGUI = hasGUI();
		if (hasGUI) {
			System.setOut(new PrintStream(new OutputStream() {
	
				@Override
				public void write(int b) throws IOException {
					GuiLog.updateOut(String.valueOf((char) b));
				}
				
			}));
			System.setErr(new PrintStream(new OutputStream() {
	
				@Override
				public void write(int b) throws IOException {
					GuiLog.updateErr(String.valueOf((char) b));
//					oldOut.print(String.valueOf((char) b));
				}
				
			}));
			usingLog = true; // reffering to Log class
		}
	}

	public static void log(int i, String s, String string) {
		synchronized(LOCK) {
			if (i == FATAL) {
				System.setErr(oldOut);
				System.setOut(oldOut);
				usingLog = false;
			}
			if (logLevel >= i) {
				if (usingLog) {
					try {
						GuiLog.append("[" + GuiChatWindow.getTime() + " - ", "Time Stamp");
						GuiLog.append(s + "] ", "Class-t");
						GuiLog.append(string + "\n", null);
					} catch (Exception e) {
						System.setErr(oldOut);
						System.setOut(oldOut);
						usingLog = false;
					}
				} 
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
	
	private static boolean hasGUI() {
		try {
			Class.forName("gui.Gui");
			return true;
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
		return false;
	}
	
	public static void log(int i, Object o, String string) {
		synchronized(LOCK) {
			if (logLevel >= i) {
				if (i == FATAL) {
					System.setErr(oldOut);
					System.setOut(oldOut);
					usingLog = false;
				}
				if (usingLog) {
					try {
						GuiLog.append("[" + GuiChatWindow.getTime() + " - ", "Time Stamp");
						GuiLog.append(o.getClass().getName() + "] ", "Class-c");
						GuiLog.append(string + "\n", null);
					} catch (Exception e) {
						System.setErr(oldOut);
						System.setOut(oldOut);
						usingLog = false;
					}
				} 
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
