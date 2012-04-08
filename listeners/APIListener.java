package listeners;

import alpha.Buddy;

public interface APIListener {
	public void onStatusChange(Buddy buddy, byte newStatus, byte oldStatus);

	public void onProfileNameChange(Buddy buddy, String newName, String oldName);

	public void onProfileTextChange(Buddy buddy, String newText, String oldText);

	public void onAddMe(Buddy buddy);

	public void onMessage(Buddy buddy, String s);

	public void onNewBuddy(Buddy buddy);

	public void onBuddyRemoved(Buddy buddy);
}
