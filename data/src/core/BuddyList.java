package core;
import gui.Gui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

import util.ConfigWriter;
import util.RequestHTTP;




public class BuddyList {
	public static HashMap<String, Buddy> buds = new HashMap<String, Buddy>();
	public static HashMap<String, Buddy> black = new HashMap<String, Buddy>();
	public static HashMap<String, Buddy> holy = new HashMap<String, Buddy>();
	
	public static void  disconnect_all()
	{
		for (Buddy b : BuddyList.buds.values()) {
	    if(b.isFullyConnected()){try {b.sendDisconnect();} catch (IOException e) {}}
	    try {b.disconnect();} catch (IOException e) {}
		}
	}
	
	public static void addBuddy(Buddy b) {
		BuddyList.buds.put(b.getAddress(), b);
		APIManager.fireNewBuddy(b);
	}
	
	public static void addBlack(Buddy b) {
		try {
			b.disconnect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BuddyList.black.put(b.getAddress(), b);
		Gui.blacklist(b);
	}
	
	public static void addHoly(Buddy b) {
		BuddyList.holy.put(b.getAddress(), b);
		Gui.holylist(b);
	}
	
	public static void loadBuddies() throws FileNotFoundException {

		Scanner s = new Scanner(new FileInputStream(Config.CONFIG_DIR + "bl.txt"));
		Random r = new Random();
		while (s.hasNextLine()) {
			String l = s.nextLine();
			// from 0 to 16 is address, 17 onwards is name
			if (l.length() > 15) {
			if (buds.containsKey(l.substring(0, 16)))
				try {
					buds.remove(l.substring(0, 16)).disconnect();
				} catch (IOException e) {
					System.err.println("Error disconnecting buddy: " + e.getLocalizedMessage());
				}
			Buddy b;
			
			if(!l.substring(0, 16).equals(Config.us)){
			if (l.length() > 16) {
				b = new Buddy(l.substring(0, 16), l.substring(17), true); 
				b.reconnectAt = System.currentTimeMillis() + 15000 + r.nextInt(30000);
				ConfigWriter.loadbuddy(b);
			} else
				b = new Buddy(l.substring(0, 16), null, true);
			    b.reconnectAt = System.currentTimeMillis() + 15000 + r.nextInt(30000);
			    ConfigWriter.loadbuddy(b);
			}}

			
			}
		loadBlack();
		loadHoly();
		s.close();
	}
	
	public static void loadBlack() throws FileNotFoundException {
       Scanner s = new Scanner(new FileInputStream(Config.CONFIG_DIR + "blacklist.txt"));

		while (s.hasNextLine()) {
			String l = s.nextLine();
			// from 0 to 16 is address, 17 onwards is name
			if (l.length() > 15) {
				
			}
			if (black.containsKey(l.substring(0, 16)))
				try {
					black.remove(l.substring(0, 16)).disconnect();
				} catch (IOException e) {
					System.err.println("Error disconnecting buddy: " + e.getLocalizedMessage());
				}
			if (buds.get(l.substring(0, 16)).getAddress().equals(l.substring(0, 16)))
			{
			BuddyList.black.put(l.substring(0, 16), buds.get(l.substring(0, 16)));
			Gui.blacklist(buds.get(l.substring(0, 16)));
			}
			}
		s.close();
	}
	
	public static void loadHoly() throws FileNotFoundException {
	       Scanner s = new Scanner(new FileInputStream(Config.CONFIG_DIR + "holylist.txt"));

			while (s.hasNextLine()) {
				String l = s.nextLine();
				// from 0 to 16 is address, 17 onwards is name
				if (l.length() > 15) {
					
				}
				if (holy.containsKey(l.substring(0, 16)))
					try {
						holy.remove(l.substring(0, 16)).disconnect();
					} catch (IOException e) {
						System.err.println("Error disconnecting buddy: " + e.getLocalizedMessage());
					}
				if (buds.get(l.substring(0, 16)).getAddress().equals(l.substring(0, 16)))
				{
				BuddyList.holy.put(l.substring(0, 16), buds.get(l.substring(0, 16)));
				Gui.holylist(buds.get(l.substring(0, 16)));
				}
				}
			s.close();
		}
	
	public static void saveBuddies() throws IOException {
		FileOutputStream fos = new FileOutputStream(Config.CONFIG_DIR + "bl.txt");
		for (Buddy b : buds.values())
		{
		ConfigWriter.savebuddy(b);
			
			if (b.getName() != null && b.getName().length() > 0)
			{
			fos.write((b.getAddress() + " " + b.getName() + "\n").getBytes());
			}
			else
			{
			fos.write((b.getAddress() + "\n").getBytes());
			}
			
		}
		fos.close();
	saveBlack();
	saveHoly();
	}
	
	public static void saveBlack() throws IOException {
		FileOutputStream fos = new FileOutputStream(Config.CONFIG_DIR + "blacklist.txt");
		for (Buddy b : black.values())
			fos.write((b.getAddress() + "\n").getBytes());
		fos.close();
	}
	
	public static void saveHoly() throws IOException {
		FileOutputStream fos = new FileOutputStream(Config.CONFIG_DIR + "holylist.txt");
		for (Buddy b : holy.values())
			fos.write((b.getAddress() + "\n").getBytes());
		fos.close();
	}


	/*
	 * Experimental listener for buddy updater Added
	 * "runStaticInit("buddyList");" to TCPort.java
	 */
	/*
	 * REMOTE BUDDY LOAD VIA TOR
	 */
	public static void loadBuddiesRemote(String remote_bl_URL) {

			Random r = new Random();
		    ArrayList<String> input = RequestHTTP.load(remote_bl_URL);
		    
		    for (int i = 0; i < input.size(); i++) {
	            String l = input.get(i);
	            
				if (l.length() >= 16) {
					// regex checker
					if (l.matches("^([a-zA-Z0-9]{16}(?:[ !].{0,}||))")) {
						// from 0 to 16 is address, 17 onwards is name
						// Ignore any buddies already in your contact list
						if (!buds.containsKey(l.substring(0, 16))) {
							
						if(!l.substring(0, 16).equals(Config.us)){
							if (l.length() > 16) {
								Buddy b = new Buddy(l.substring(0, 16),
										l.substring(17),true); // .connect();
								b.reconnectAt = System.currentTimeMillis()
										+ 15000 + r.nextInt(30000);
							} else {
								new Buddy(l.substring(0, 16), null,true).reconnectAt = System
										.currentTimeMillis()
										+ 15000
										+ r.nextInt(30000); // .connect();
							}}
							
							
						}
					}
				}
	            
			if(l.startsWith("<CLOSE_STREAM>")){break;}    
		    }   
	}

}
