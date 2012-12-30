package commands;

import gui.GuiChatWindow;
import util.ChatWindow;
import core.Buddy;

public class in_me {
	public static void command(Buddy buddy, String s, GuiChatWindow w) {
		
		if (s.length() < 5)
		{
	    ChatWindow.update_window(7, w,"Parameter /me msg","","",false);
		}
		else
		{
		ChatWindow.update_window(3, w,s.substring(4),"","/me " + s.substring(4),!buddy.isFullyConnected());
		}
	}
}
