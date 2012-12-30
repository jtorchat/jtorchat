package commands;

import util.ChatWindow;
import gui.GuiChatWindow;
import core.Buddy;
import core.language;

public class in_help {
	public static void command(Buddy buddy, String s, GuiChatWindow w) {
		
	String help;
	
	help  = "\n"+language.langtext[57]+"\n";
	help += "/page_disp site\n";
	help += "/page_send site\n";
	help += "/page site\n";
	help += "/log\n";
		
	ChatWindow.update_window(7, w,help,"","",false);

	}
}
