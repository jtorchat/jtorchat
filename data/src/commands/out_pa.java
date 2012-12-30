package commands;

import util.ChatWindow;
import gui.GuiChatWindow;
import core.Buddy;

public class out_pa {
	public static void command(Buddy buddy, String s, GuiChatWindow w, boolean with_delay) {
	    ChatWindow.update_window(6, w,s.substring(4),"","",with_delay);
	}
}
