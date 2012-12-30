package util;

import gui.Gui;

import java.awt.AWTException;
import java.awt.Font;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import core.Buddy;
import core.BuddyList;
import core.Config;
import core.Logger;
import core.TCPort;
import core.TorLoader;
import core.language;





public class Tray {
	static TrayIcon trayIcon= null;
	private static final String CLASS_NAME = TorLoader.class.getName();

	public static void init() {
		if (SystemTray.isSupported() && is_not_gnome3()) {
		    SystemTray tray = SystemTray.getSystemTray();
		    Image image = TCIconRenderer.offlineImage;
	
		    ActionListener togListener = new ActionListener() {
		        @Override
				public void actionPerformed(ActionEvent e) {
		        	Gui.getInstance().setVisible(!Gui.getInstance().isVisible());
		        }
		    };
		    ActionListener exitListener = new ActionListener() {
		        @Override
				public void actionPerformed(ActionEvent e) {
		        	System.exit(0);
		        }
		    };
		    PopupMenu popup = new PopupMenu();
		    
		    MenuItem defaultItem = new MenuItem("Restore/Hide");
		    defaultItem.setFont(new Font("sansserif", Font.BOLD, 12));
		    defaultItem.addActionListener(togListener);
		    popup.add(defaultItem);
		    popup.addSeparator();
		    MenuItem exitItem = new MenuItem(language.langtext[5]);
		    exitItem.addActionListener(exitListener);
		    popup.add(exitItem);
		    trayIcon = new TrayIcon(image, "JTorchat - " + Config.us, popup);
		    trayIcon.addActionListener(togListener);
		    try {
		        tray.add(trayIcon);
			    Gui.f.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		    } catch (AWTException e) {
		        System.err.println(e);
		    }
		} else {	   
        Gui.f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Logger.log(Logger.WARNING, CLASS_NAME, "Tray Icons not supported for this system.");
		}

	}
// Java Tray Icons not work in Gnome3 :(
private static boolean is_not_gnome3() {
	String os = System.getProperty("os.name").toLowerCase();
if (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0){
	try {
	String command= "gnome-shell --version"; 
	Runtime rt = Runtime.getRuntime(); 	
	Process pr = rt.exec(command);
	Reader r = new InputStreamReader(pr.getInputStream());
    BufferedReader in = new BufferedReader(r);
    String line;
	while((line = in.readLine()) != null){
	if(line.startsWith("GNOME Shell 3")){return false;}
	}
	in.close();
	return true;
	} catch (IOException e) {
		return true;
	}
    }
    else
    {
    return true;
    }
    }
public static void updateTray()
{
	Image image;
    if (BuddyList.buds.get(Config.us) == null) {
	    String status = TCPort.status; // i'm aware the below has some fails lol
    	image = status.equalsIgnoreCase("offline") ? TCIconRenderer.offlineImage : status.equalsIgnoreCase("handshake") ? TCIconRenderer.handshakeImage : status.equalsIgnoreCase("available") ? TCIconRenderer.onlineImage : status.equalsIgnoreCase("away") ? TCIconRenderer.awayImage : status.equalsIgnoreCase("xa") ? TCIconRenderer.xaImage : null;
    } else {
    	byte status = BuddyList.buds.get(Config.us).getStatus();
    	image = status == Buddy.OFFLINE ? TCIconRenderer.offlineImage : status == Buddy.HANDSHAKE ? TCIconRenderer.handshakeImage : status == Buddy.ONLINE ? TCIconRenderer.onlineImage : status == Buddy.AWAY ? TCIconRenderer.awayImage : status == Buddy.XA ? TCIconRenderer.xaImage : null;
    }
	if (trayIcon != null) {
    trayIcon.setImage(image);
    }
}
}
