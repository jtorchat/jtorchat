package listeners;

import alpha.Buddy;

public interface APIListener {
	public void onStatusChange(Buddy buddy, byte newStatus, byte oldStatus);
<<<<<<< HEAD

	public void onProfileNameChange(Buddy buddy, String newName, String oldName);

	public void onProfileTextChange(Buddy buddy, String newText, String oldText);

	public void onAddMe(Buddy buddy);

	public void onMessage(Buddy buddy, String s);

	public void onNewBuddy(Buddy buddy);

=======
	public void onProfileNameChange(Buddy buddy, String newName, String oldName);
	public void onProfileTextChange(Buddy buddy, String newText, String oldText);
	public void onAddMe(Buddy buddy);
	public void onMessage(Buddy buddy, String s);
	public void onNewBuddy(Buddy buddy);
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
	public void onBuddyRemoved(Buddy buddy);
}
