package gui;

import java.awt.AWTException;
import java.awt.Font;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import alpha.Buddy;
import alpha.BuddyList;
import alpha.Config;
import alpha.TCPort;
import alpha.language;

<<<<<<< HEAD
public class Tray {
	static TrayIcon trayIcon = null;

	public static void init() {
		if (SystemTray.isSupported()) {
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
			// create a popup menu
			PopupMenu popup = new PopupMenu();
			// create menu item for the default action
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
			} catch (AWTException e) {
				System.err.println(e);
			}
		} else {

		}

	}

	public static void updateTray() {
		Image image;
		if (BuddyList.buds.get(Config.us) == null) {
			String status = TCPort.status; // i'm aware the below has some fails lol
			image = status.equalsIgnoreCase("offline") ? TCIconRenderer.offlineImage : status.equalsIgnoreCase("handshake") ? TCIconRenderer.handshakeImage : status.equalsIgnoreCase("available") ? TCIconRenderer.onlineImage : status.equalsIgnoreCase("away") ? TCIconRenderer.awayImage : status
					.equalsIgnoreCase("xa") ? TCIconRenderer.xaImage : null;
		} else {
			byte status = BuddyList.buds.get(Config.us).getStatus();
			image = status == Buddy.OFFLINE ? TCIconRenderer.offlineImage : status == Buddy.HANDSHAKE ? TCIconRenderer.handshakeImage : status == Buddy.ONLINE ? TCIconRenderer.onlineImage : status == Buddy.AWAY ? TCIconRenderer.awayImage : status == Buddy.XA ? TCIconRenderer.xaImage : null;
		}
		if (trayIcon != null) {
			trayIcon.setImage(image);
		}
	}
=======


public class Tray {
	static TrayIcon trayIcon= null;
	
	public static void init() {
		if (SystemTray.isSupported()) {
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
		    // create a popup menu
		    PopupMenu popup = new PopupMenu();
		    // create menu item for the default action
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
		    } catch (AWTException e) {
		        System.err.println(e);
		    }
		} else {
			
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
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
}
