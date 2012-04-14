package alpha;

import java.util.ArrayList;
import java.util.HashMap;

import listeners.APIListener;
import listeners.CommandListener;
import listeners.IncomingCommandListener;

public class APIManager {
	private static ArrayList<APIListener> listeners = new ArrayList<APIListener>();
	public static HashMap<String, CommandListener> cmdListeners = new HashMap<String, CommandListener>();
	public static HashMap<String, IncomingCommandListener> incomingCmdListeners = new HashMap<String, IncomingCommandListener>();

	public static void addEventListener(APIListener apil) {
		listeners.add(apil);
	}

	public static void fireStatusChange(Buddy buddy, byte newStatus, byte oldStatus) {
		try {
			Logger.log(Logger.DEBUG, "API", buddy.getAddress() + " status change from " + Buddy.getStatusName(oldStatus) + " to " + Buddy.getStatusName(newStatus));
			for (APIListener apil : listeners)
				apil.onStatusChange(buddy, newStatus, oldStatus);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void fireProfileNameChange(Buddy buddy, String newName, String oldName) {
		try {
			Logger.log(Logger.DEBUG, "API", buddy.getAddress() + " name changed from " + oldName + " to " + newName);
			for (APIListener apil : listeners)
				apil.onProfileNameChange(buddy, newName, oldName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void fireProfileTextChange(Buddy buddy, String newText, String oldText) {
		try {
			Logger.log(Logger.DEBUG, "API", buddy.getAddress() + " profileText changed from " + oldText + " to " + newText);
			for (APIListener apil : listeners)
				apil.onProfileTextChange(buddy, newText, oldText);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void fireAddMe(Buddy buddy) {
		try {
			Logger.log(Logger.DEBUG, "API", buddy.getAddress() + " requested us to add them");
			for (APIListener apil : listeners)
				apil.onAddMe(buddy);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void fireMessage(Buddy buddy, String s) {
		try {
			Logger.log(Logger.DEBUG, "API", buddy.getAddress() + " sent " + s);
			for (APIListener apil : listeners)
				apil.onMessage(buddy, s);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void fireBuddyRemoved(Buddy buddy) {
		try {
			Logger.log(Logger.DEBUG, "API", "Buddy removed: " + buddy.getAddress());
			for (APIListener apil : listeners)
				apil.onBuddyRemoved(buddy);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void fireNewBuddy(Buddy buddy) {
		try {
			Logger.log(Logger.DEBUG, "API", "New buddy: " + buddy.getAddress());
			for (APIListener apil : listeners)
				apil.onNewBuddy(buddy);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
