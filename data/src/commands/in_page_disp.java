package commands;


import util.ChatWindow;
import gui.GuiChatWindow;
import core.Buddy;


public class in_page_disp {
	public static void command(Buddy buddy, String s, GuiChatWindow w) {

		
		if (s.length() < 12) // When nothing is choosen use index
        {
        s="/page_disp index";
        }

		s="/page_disp "+s.substring(11).replaceAll("[^a-zA-Z]",""); // Replace all special letters

		String msg = util_page.read(s.substring(11));
		if(msg=="")
		{
		ChatWindow.update_window(7, w,"The page '" + s.substring(11) + "' do not exist","","",false);
		}
		else
		{
		ChatWindow.update_window(7, w,"Get " + s.substring(11) + msg,"","",false);
		}
		
	}
}
