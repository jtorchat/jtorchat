package alpha;
<<<<<<< HEAD

=======
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
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

<<<<<<< HEAD
=======



>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
public class BuddyList {
	public static HashMap<String, Buddy> buds = new HashMap<String, Buddy>();
	public static HashMap<String, Buddy> black = new HashMap<String, Buddy>();
	public static HashMap<String, Buddy> holy = new HashMap<String, Buddy>();
<<<<<<< HEAD

=======
	
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
	public static void addBuddy(Buddy b) {
		BuddyList.buds.put(b.getAddress(), b);
		APIManager.fireNewBuddy(b);
	}
<<<<<<< HEAD

=======
	
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
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
<<<<<<< HEAD

=======
	
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
	public static void addHoly(Buddy b) {
		BuddyList.holy.put(b.getAddress(), b);
		Gui.holylist(b);
	}
<<<<<<< HEAD

	public static void loadBuddies() throws FileNotFoundException {
		// Logger.log(Logger.INFO, "BuddyList loader", Config.CONFIG_DIR + "bl.txt");
=======
	
	public static void loadBuddies() throws FileNotFoundException {
//		Logger.log(Logger.INFO, "BuddyList loader", Config.CONFIG_DIR + "bl.txt");
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
		Scanner s = new Scanner(new FileInputStream(Config.CONFIG_DIR + "bl.txt"));
		Random r = new Random();
		while (s.hasNextLine()) {
			String l = s.nextLine();
			// from 0 to 16 is address, 17 onwards is name
			if (l.length() > 15) {
<<<<<<< HEAD
				if (buds.containsKey(l.substring(0, 16)))
					try {
						buds.remove(l.substring(0, 16)).disconnect();
					} catch (IOException e) {
						System.err.println("Error disconnecting buddy: " + e.getLocalizedMessage());
					}
				if (l.length() > 16) {
					Buddy b = new Buddy(l.substring(0, 16), l.substring(17)); // .connect();
					b.reconnectAt = System.currentTimeMillis() + 15000 + r.nextInt(30000);
					if (l.toCharArray()[16] == '!')
						b.overrideName = true;
				} else
					new Buddy(l.substring(0, 16), null).reconnectAt = System.currentTimeMillis() + 15000 + r.nextInt(30000); // .connect();
			}
		}
		loadBlack();
		loadHoly();
	}

	public static void loadBlack() throws FileNotFoundException {
		Scanner s = new Scanner(new FileInputStream(Config.CONFIG_DIR + "blacklist.txt"));
=======
			if (buds.containsKey(l.substring(0, 16)))
				try {
					buds.remove(l.substring(0, 16)).disconnect();
				} catch (IOException e) {
					System.err.println("Error disconnecting buddy: " + e.getLocalizedMessage());
				}
			if (l.length() > 16) {
				Buddy b = new Buddy(l.substring(0, 16), l.substring(17)); //.connect();
				b.reconnectAt = System.currentTimeMillis() + 15000 + r.nextInt(30000);
				if (l.toCharArray()[16] == '!')
					b.overrideName = true;
			} else
				new Buddy(l.substring(0, 16), null).reconnectAt = System.currentTimeMillis() + 15000 + r.nextInt(30000); //.connect();
			}
			}
		loadBlack();
		loadHoly();
	}
	
	public static void loadBlack() throws FileNotFoundException {
       Scanner s = new Scanner(new FileInputStream(Config.CONFIG_DIR + "blacklist.txt"));
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95

		while (s.hasNextLine()) {
			String l = s.nextLine();
			// from 0 to 16 is address, 17 onwards is name
			if (l.length() > 15) {
<<<<<<< HEAD

=======
				
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
			}
			if (black.containsKey(l.substring(0, 16)))
				try {
					black.remove(l.substring(0, 16)).disconnect();
				} catch (IOException e) {
					System.err.println("Error disconnecting buddy: " + e.getLocalizedMessage());
				}
<<<<<<< HEAD
			if (buds.get(l.substring(0, 16)).getAddress().equals(l.substring(0, 16))) {
				BuddyList.black.put(l.substring(0, 16), buds.get(l.substring(0, 16)));
				Gui.blacklist(buds.get(l.substring(0, 16)));
			}
		}
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
			if (buds.get(l.substring(0, 16)).getAddress().equals(l.substring(0, 16))) {
				BuddyList.holy.put(l.substring(0, 16), buds.get(l.substring(0, 16)));
				Gui.holylist(buds.get(l.substring(0, 16)));
			}
		}
	}

=======
			if (buds.get(l.substring(0, 16)).getAddress().equals(l.substring(0, 16)))
			{
			BuddyList.black.put(l.substring(0, 16), buds.get(l.substring(0, 16)));
			Gui.blacklist(buds.get(l.substring(0, 16)));
			}
			}
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
		}
	
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
	public static void saveBuddies() throws IOException {
		FileOutputStream fos = new FileOutputStream(Config.CONFIG_DIR + "bl.txt");
		for (Buddy b : buds.values())
			fos.write((b.getAddress() + (b.overrideName ? "!" : " ") + ((b.getProfile_name() != null && b.getProfile_name().length() > 0) ? b.getProfile_name() : (b.getName() != null && b.getName().length() > 0) ? b.getName() : "") + "\n").getBytes());
		fos.close();
<<<<<<< HEAD
		saveBlack();
		saveHoly();
	}

=======
	saveBlack();
	saveHoly();
	}
	
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
	public static void saveBlack() throws IOException {
		FileOutputStream fos = new FileOutputStream(Config.CONFIG_DIR + "blacklist.txt");
		for (Buddy b : black.values())
			fos.write((b.getAddress() + (b.overrideName ? "!" : " ") + ((b.getProfile_name() != null && b.getProfile_name().length() > 0) ? b.getProfile_name() : (b.getName() != null && b.getName().length() > 0) ? b.getName() : "") + "\n").getBytes());
		fos.close();
	}
<<<<<<< HEAD

=======
	
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
	public static void saveHoly() throws IOException {
		FileOutputStream fos = new FileOutputStream(Config.CONFIG_DIR + "holylist.txt");
		for (Buddy b : holy.values())
			fos.write((b.getAddress() + (b.overrideName ? "!" : " ") + ((b.getProfile_name() != null && b.getProfile_name().length() > 0) ? b.getProfile_name() : (b.getName() != null && b.getName().length() > 0) ? b.getName() : "") + "\n").getBytes());
		fos.close();
	}

<<<<<<< HEAD
	/*
	 * Experimental listener for buddy updater Added "runStaticInit("buddyList");" to TCPort.java
=======

	/*
	 * Experimental listener for buddy updater Added
	 * "runStaticInit("buddyList");" to TCPort.java
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
	 */
	/*
	 * REMOTE BUDDY LOAD VIA TOR
	 */
	public static void loadBuddiesRemote(String remote_bl_URL) {
		// Don't load if no url was specified
<<<<<<< HEAD
		if ((remote_bl_URL == null) || (remote_bl_URL == "")) {
			Logger.log(Logger.INFO, "BuddyList loadBuddiesRemote", "No remote buddylist specified. Skipping remote load.");
			Logger.log(Logger.INFO, "BuddyList loadBuddiesRemote", "Tip: sync = example.com/bl.txt ; in settings.ini");
			return;
		} else {
			Logger.log(Logger.INFO, "BuddyList loadBuddiesRemote", "Loading buddies from remote url... ");
=======
		if ((remote_bl_URL == null)||(remote_bl_URL == "")) {
			Logger.log(Logger.INFO, "BuddyList loadBuddiesRemote",
					"No remote buddylist specified. Skipping remote load.");
			Logger.log(Logger.INFO, "BuddyList loadBuddiesRemote",
					"Tip: sync = example.com/bl.txt ; in settings.ini");
			return;
		} else {
			Logger.log(Logger.INFO, "BuddyList loadBuddiesRemote",
					"Loading buddies from remote url... ");
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
		}

		/*
		 * SOCKET RETREIVE REMOTE FILE VIA PROXY TO SCANNER OBJECT
		 */
<<<<<<< HEAD
		Logger.log(Logger.INFO, "BuddyList loadBuddiesRemote", "REMOTE BUDDYLISTURL LOCATION: " + remote_bl_URL);

		try {
			// assume https:// if lacking protocol
			if (!remote_bl_URL.matches(".+://.+")) {
				remote_bl_URL = "https://" + remote_bl_URL;
=======
		Logger.log(Logger.INFO, "BuddyList loadBuddiesRemote",
				"REMOTE BUDDYLISTURL LOCATION: " + remote_bl_URL);

		try {
			//assume https:// if lacking protocol
			if(!remote_bl_URL.matches(".+://.+")){
				remote_bl_URL = "https://"+remote_bl_URL;
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
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
<<<<<<< HEAD
			Logger.log(Logger.INFO, "BuddyList loadBuddiesRemote", "Configering secure proxy tunnel to buddylist; Port: " + Config.SOCKS_PORT + " host: 127.0.0.1");
			Socket ourSock = new Socket(new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("127.0.0.1", Config.SOCKS_PORT)));
			Logger.log(Logger.INFO, "BuddyList loadBuddiesRemote", "Starting secure remote proxy tunnel to buddylist");
			ourSock.connect(InetSocketAddress.createUnresolved(host, port));

			// INPUT/OUTPUT STREAM
			Logger.log(Logger.INFO, "BuddyList loadBuddiesRemote", "opening in/out stream");
=======
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
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
			InputStream is = ourSock.getInputStream();
			OutputStream os = ourSock.getOutputStream();

			// read incoming data
<<<<<<< HEAD
			Logger.log(Logger.INFO, "BuddyList loadBuddiesRemote", "start inputstream scanner");
=======
			Logger.log(Logger.INFO, "BuddyList loadBuddiesRemote",
					"start inputstream scanner");
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
			Scanner s = new Scanner(new InputStreamReader(is)); // create
																// scanner obj

			// Send Request Header to output stream
<<<<<<< HEAD
			String sendString = "GET " + path + " HTTP/1.0 \r\n" + "Host: " + host + "\r\n" + "\r\n";
=======
			String sendString = "GET " + path + " HTTP/1.0 \r\n" + "Host: "
					+ host + "\r\n" + "\r\n";
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
			Logger.log(Logger.INFO, "BuddyList loadBuddiesRemote", sendString);
			os.write(sendString.getBytes());

			/*
			 * PROCESS FILE AND MERGE BUDDY
			 */
<<<<<<< HEAD
			Logger.log(Logger.INFO, "BuddyList loadBuddiesRemote", "Processing remote buddies");
=======
			Logger.log(Logger.INFO, "BuddyList loadBuddiesRemote",
					"Processing remote buddies");
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
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
							if (l.length() > 16) {
<<<<<<< HEAD
								Buddy b = new Buddy(l.substring(0, 16), l.substring(17)); // .connect();
								b.reconnectAt = System.currentTimeMillis() + 15000 + r.nextInt(30000);
								if (l.toCharArray()[16] == '!')
									b.overrideName = true;
							} else {
								new Buddy(l.substring(0, 16), null).reconnectAt = System.currentTimeMillis() + 15000 + r.nextInt(30000); // .connect();
=======
								Buddy b = new Buddy(l.substring(0, 16),
										l.substring(17)); // .connect();
								b.reconnectAt = System.currentTimeMillis()
										+ 15000 + r.nextInt(30000);
								if (l.toCharArray()[16] == '!')
									b.overrideName = true;
							} else {
								new Buddy(l.substring(0, 16), null).reconnectAt = System
										.currentTimeMillis()
										+ 15000
										+ r.nextInt(30000); // .connect();
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
							}
						}
					}
				}
			}

<<<<<<< HEAD
			Logger.log(Logger.INFO, "BuddyList loadBuddiesRemote", "Processing Done");
=======
			Logger.log(Logger.INFO, "BuddyList loadBuddiesRemote",
					"Processing Done");
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
			// Close input/output stream
			os.close();
			is.close();
			// Close socket
			ourSock.close();
		} catch (IOException e1) {
<<<<<<< HEAD
			Logger.log(Logger.INFO, "BuddyList loadBuddiesRemote", "ERROR: Cannot remote buddies list - Skipping: " + e1);
=======
			Logger.log(Logger.INFO, "BuddyList loadBuddiesRemote",
					"ERROR: Cannot remote buddies list - Skipping: " + e1);
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
			return;
		}

	}

}
