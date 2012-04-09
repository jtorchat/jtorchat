package broadcast;

import gui.Gui;
import gui.GuiListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import alpha.Buddy;
import alpha.Config;
import alpha.language;

<<<<<<< HEAD
public class BroadcastGui {
	static Listener lis;

=======

public class BroadcastGui {
	static Listener lis;
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
	static void init() { // Check for Gui class should've already been done
		if (lis != null) {
			// wth
			return;
		}

		Gui.getInstance().cmdListeners.put("broadcast", lis = new Listener());
		Gui.getInstance().cmdListeners.put("bcast", lis);

		JMenuItem jmiBuddyReq = new JMenuItem(language.langtext[6]);
<<<<<<< HEAD

=======
		
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
		jmiBuddyReq.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Broadcast.broadcast("$buddyRequest", Config.us, null, true, true);
			}
		});
		Gui.getInstance().getFileMenu().add(new JSeparator());
		Gui.getInstance().getFileMenu().add(jmiBuddyReq);

<<<<<<< HEAD
	}

=======
	
	}
	
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
	private static class Listener implements GuiListener {

		@Override
		public String onCommand(Buddy buddy, String s) {
			System.out.println(s);
			if ((s.startsWith("/bcast") || s.startsWith("/broadcast")) && s.split(" ").length >= 3 && s.split(" ")[1].startsWith("#")) {
				Broadcast.broadcast(s.split(" ")[1], Config.us, s.split(" ", 3)[2], Broadcast.forwardAll, true);
			}
			return "";
		}
<<<<<<< HEAD

=======
		
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
	}

}
