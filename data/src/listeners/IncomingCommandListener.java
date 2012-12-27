package listeners;

import java.io.InputStream;

import core.Buddy;



public interface IncomingCommandListener {
	public void onCommand(Buddy buddy, String command, InputStream is);
}
