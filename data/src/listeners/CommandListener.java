package listeners;

import core.Buddy;

public interface CommandListener {
	public void onCommand(Buddy buddy, String s);
}
