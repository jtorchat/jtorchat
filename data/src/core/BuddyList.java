package core;
import gui.Gui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

import util.ConfigWriter;




public class BuddyList {
	public static HashMap<String, Buddy> buds = new HashMap<String, Buddy>();
	public static HashMap<String, Buddy> black = new HashMap<String, Buddy>();
	public static HashMap<String, Buddy> holy = new HashMap<String, Buddy>();
	
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
		// Don't load if no url was specified
		if ((remote_bl_URL == null)||(remote_bl_URL == "")) {
			Logger.log(Logger.INFO, "BuddyList loadBuddiesRemote",
					"No remote buddylist specified. Skipping remote load.");
			Logger.log(Logger.INFO, "BuddyList loadBuddiesRemote",
					"Tip: sync = example.com/bl.txt ; in settings.ini");
			return;
		} else {
			Logger.log(Logger.INFO, "BuddyList loadBuddiesRemote",
					"Loading buddies from remote url... ");
		}

		/*
		 * SOCKET RETREIVE REMOTE FILE VIA PROXY TO SCANNER OBJECT
		 */
		Logger.log(Logger.INFO, "BuddyList loadBuddiesRemote",
				"REMOTE BUDDYLISTURL LOCATION: " + remote_bl_URL);

		try {
			//assume https:// if lacking protocol
			if(!remote_bl_URL.matches(".+://.+")){
				remote_bl_URL = "https://"+remote_bl_URL;
			}

			// Parse the URL for socket usage via URL. Can't use URL directly
			// due to DNS leaks
			URL aURL = new URL(remote_bl_URL);
			// Placed parsed URL into vars for general usage
			// http://example.com:80/docs/books/tutorial/index.html?name=networking#DOWNLOADING
			String host = aURL.getHost(); // host = example.com
			int port = aURL.getPort(); // port = 80
			String path = aURL.getPath(); // path = /docs/books/tutorial/index.html
			// Sometimes portnumber is not declared (shows as port = -1), assume
			// port 80 in that case
			if (port < 0) {
				port = 80;
			}

			// Declare a new proxyed socket and connect to it (No DNS leak via
			// createUnresolved)
			Logger.log(Logger.INFO, "BuddyList loadBuddiesRemote",
					"Configering secure proxy tunnel to buddylist; Port: "
							+ Config.SOCKS_PORT + " host: 127.0.0.1");
            Socket ourSock = new Socket(new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("127.0.0.1", Config.SOCKS_PORT)));
			Logger.log(Logger.INFO, "BuddyList loadBuddiesRemote",
					"Starting secure remote proxy tunnel to buddylist");
			ourSock.connect(InetSocketAddress.createUnresolved(host, port));

			// INPUT/OUTPUT STREAM
			Logger.log(Logger.INFO, "BuddyList loadBuddiesRemote",
					"opening in/out stream");
			InputStream is = ourSock.getInputStream();
			OutputStream os = ourSock.getOutputStream();

			// read incoming data
			Logger.log(Logger.INFO, "BuddyList loadBuddiesRemote",
					"start inputstream scanner");
			Scanner s = new Scanner(new InputStreamReader(is)); // create
																// scanner obj

			// Send Request Header to output stream
			String sendString = "GET " + path + " HTTP/1.0 \r\n" + "Host: "
					+ host + "\r\n" + "\r\n";
			Logger.log(Logger.INFO, "BuddyList loadBuddiesRemote", sendString);
			os.write(sendString.getBytes());

			/*
			 * PROCESS FILE AND MERGE BUDDY
			 */
			Logger.log(Logger.INFO, "BuddyList loadBuddiesRemote",
					"Processing remote buddies");
			Random r = new Random();

			while (s.hasNextLine()) {
				String l = s.nextLine();
				// Hunt for matching address in a new line

				// Skip anything less than 16 char, as substring cannot handle
				// it (it will error out)
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
			}
			s.close();
			Logger.log(Logger.INFO, "BuddyList loadBuddiesRemote",
					"Processing Done");
			// Close input/output stream
			os.close();
			is.close();
			// Close socket
			ourSock.close();
		} catch (IOException e1) {
			Logger.log(Logger.INFO, "BuddyList loadBuddiesRemote",
					"ERROR: Cannot remote buddies list - Skipping: " + e1);
			return;
		}
		
	}

}
