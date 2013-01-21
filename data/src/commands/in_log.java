package commands;

import gui.GuiChatWindow;
import util.ChatWindow;
import util.LogWriter;
import core.Buddy;

public class in_log {
	public static void command(Buddy buddy, String s, GuiChatWindow w) {
		
		String filename;
		String buddyname = buddy.getName();
		if(buddyname==null || buddyname.equals("")){buddyname = buddy.getAddress();}
		filename = LogWriter.LogWrite(w.get_textPane1().getText(),1,buddyname);
	    ChatWindow.update_window(7, w,"This Chat log here " + filename + " in the Log Folder.","","",false);
	}
}
