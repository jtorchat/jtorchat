package commands;

import gui.GuiChatWindow;
import core.Buddy;

public class list_of_commands {

public static boolean in_command(Buddy buddy, String s, GuiChatWindow w)
{
			String command = s.split(" ")[0];
			if (command.equals("/log")){in_log.command(buddy,s,w);return false;}
			else if(command.equals("/help")){in_help.command(buddy,s,w);return false;}
			else if(command.equals("/me")){in_me.command(buddy,s,w);return false;}
		    else if(command.equals("/page_disp")){in_page_disp.command(buddy,s,w);return false;}
		    else if(command.equals("/page_send")){in_page_send.command(buddy,s,w);return false;}
			return true;
}

public static boolean out_command(Buddy buddy, String s, GuiChatWindow w, boolean with_delay)
{
	String command = s.split(" ")[0];
	if (command.equals("/me")){out_me.command(buddy,s,w,with_delay);return false;}
	if (command.equals("/pa")){out_pa.command(buddy, s,w,with_delay);return false;}
	if (command.equals("/page")){out_page.command(buddy, s,w,with_delay);return false;}
	
//When the command is not exist then it can be not jtorchat because you can not send commands yourself
return true;
}
		
}
