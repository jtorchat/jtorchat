package alpha;
<<<<<<< HEAD

=======
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
import java.util.ArrayList;
import java.util.HashMap;

import listeners.APIListener;
import listeners.CommandListener;
import listeners.IncomingCommandListener;

<<<<<<< HEAD
=======

>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
public class APIManager {
	private static ArrayList<APIListener> listeners = new ArrayList<APIListener>();
	public static HashMap<String, CommandListener> cmdListeners = new HashMap<String, CommandListener>();
	public static HashMap<String, IncomingCommandListener> incomingCmdListeners = new HashMap<String, IncomingCommandListener>();
<<<<<<< HEAD

=======
	
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
	public static void addEventListener(APIListener apil) {
		listeners.add(apil);
	}

	public static void fireStatusChange(Buddy buddy, byte newStatus, byte oldStatus) {
<<<<<<< HEAD
		// System.out.println("[API] " + buddy.getAddress() + " status change from " + Buddy.getStatusName(oldStatus) + " to " + Buddy.getStatusName(newStatus));
=======
//		System.out.println("[API] " + buddy.getAddress() + " status change from " + Buddy.getStatusName(oldStatus) + " to " + Buddy.getStatusName(newStatus));
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
		Logger.log(Logger.DEBUG, "API", buddy.getAddress() + " status change from " + Buddy.getStatusName(oldStatus) + " to " + Buddy.getStatusName(newStatus));
		for (APIListener apil : listeners)
			apil.onStatusChange(buddy, newStatus, oldStatus);
	}

	public static void fireProfileNameChange(Buddy buddy, String newName, String oldName) {
<<<<<<< HEAD
		// System.out.println("[API] " + buddy.getAddress() + " name changed from " + oldName + " to " + newName);
=======
//		System.out.println("[API] " + buddy.getAddress() + " name changed from " + oldName + " to " + newName);
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
		Logger.log(Logger.DEBUG, "API", buddy.getAddress() + " name changed from " + oldName + " to " + newName);
		for (APIListener apil : listeners)
			apil.onProfileNameChange(buddy, newName, oldName);
	}

	public static void fireProfileTextChange(Buddy buddy, String newText, String oldText) {
<<<<<<< HEAD
		// System.out.println("[API] " + buddy.getAddress() + " profileText changed from " + oldText + " to " + newText);
=======
//		System.out.println("[API] " + buddy.getAddress() + " profileText changed from " + oldText + " to " + newText);
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
		Logger.log(Logger.DEBUG, "API", buddy.getAddress() + " profileText changed from " + oldText + " to " + newText);
		for (APIListener apil : listeners)
			apil.onProfileTextChange(buddy, newText, oldText);
	}

	public static void fireAddMe(Buddy buddy) {
<<<<<<< HEAD
		// System.out.println("[API] " + buddy.getAddress() + " requested us to add them");
=======
//		System.out.println("[API] " + buddy.getAddress() + " requested us to add them");
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
		Logger.log(Logger.DEBUG, "API", buddy.getAddress() + " requested us to add them");
		for (APIListener apil : listeners)
			apil.onAddMe(buddy);
	}

	public static void fireMessage(Buddy buddy, String s) {
<<<<<<< HEAD
		// System.out.println("[API] " + buddy.getAddress() + " sent " + s);
=======
//		System.out.println("[API] " + buddy.getAddress() + " sent " + s);
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
		Logger.log(Logger.DEBUG, "API", buddy.getAddress() + " sent " + s);
		for (APIListener apil : listeners)
			apil.onMessage(buddy, s);
	}
<<<<<<< HEAD

=======
	
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
	public static void fireBuddyRemoved(Buddy buddy) {
		Logger.log(Logger.DEBUG, "API", "Buddy removed: " + buddy.getAddress());
		for (APIListener apil : listeners)
			apil.onBuddyRemoved(buddy);
	}
<<<<<<< HEAD

	public static void fireNewBuddy(Buddy buddy) {
		// System.out.println("[API] new Buddy: " + buddy.getAddress());
=======
	
	public static void fireNewBuddy(Buddy buddy) {
//		System.out.println("[API] new Buddy: " + buddy.getAddress());
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
		Logger.log(Logger.DEBUG, "API", "New buddy: " + buddy.getAddress());
		for (APIListener apil : listeners)
			apil.onNewBuddy(buddy);
	}

}
