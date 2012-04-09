package broadcast;
import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Scanner;

import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;

import alpha.APIManager;
import alpha.Buddy;
import alpha.BuddyList;
import alpha.Config;
import alpha.Logger;

import util.ConfigWriter;
import util.Regex;
import util.Util;

import listeners.APIListener;
import listeners.CommandListener;

public class Broadcast {
	private static Listener lis;
	private static ArrayList<String> recievedHashes = new ArrayList<String>();
	private static HashMap<String, String> budTags = new HashMap<String, String>();
	private static FileOutputStream bcastLogOut;
	private static String myTags = null;
	private static boolean addBuddyViaReq;
	private static final Random rnd = new Random();
	
	private static final String TAG = "(#.*?)[ =\t*?]([0-1])([0-1])([0-1])";
	private static final String ABVR = "addBudsViaReq[ =](1|0|true|false)";
	// NOTE minilog does nothing if Gui disabled
	public static final HashMap<String, boolean[]> tagMap = new HashMap<String, boolean[]>();
	public static boolean forwardAll = true;
	
	public static HashMap<String, DefaultStyledDocument> minilog = hasGUI() ? new HashMap<String, DefaultStyledDocument>() : null;

	public static void init() {
		if (lis != null) {
			// wth
			return;
		}
		try {
			loadSettings();
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		}
		Logger.log(Logger.NOTICE, "Broadcast", "Loaded: " + tagMap.keySet());
//		myTags = Config.assign("tags", "", prop);
		
		try {
			bcastLogOut = new FileOutputStream("bcastLog.txt", true);
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		}
		
		try {
			Class.forName("gui.Gui"); // Throws ClassNotFoundException if no Gui class
			BroadcastGui.init();
		} catch (Exception e) {
			// ignored
		}
		
		APIManager.addEventListener(lis = new Listener());
		APIManager.cmdListeners.put("broadcast", lis);
		APIManager.cmdListeners.put("mytags", lis);
	}
	
	public static void loadSettings() throws FileNotFoundException {
		Scanner s = new Scanner(new FileInputStream(Config.CONFIG_DIR + "broadcast.ini"));
		tagMap.clear();
		while (s.hasNextLine()) {
			String l = s.nextLine().trim();
			if (l.startsWith("//") || l.length() == 0)
				continue; // ignore
			String[] data;
			String x;
			if ((data = Regex.getRegArray(l, TAG)) != null) {
				boolean[] res = new boolean[] { Regex.toBoolean(data[2]), Regex.toBoolean(data[3]), Regex.toBoolean(data[4]) };
				tagMap.put(data[1].trim(), res);
//				System.out.println(data[1] + " " + res[0] + " " + res[1] + " " + res[2]);
			} else if ((x = Regex.getReg(l, ABVR)) != null) {
				System.out.println(l);
				if (x.equalsIgnoreCase("1") || x.equalsIgnoreCase("true"))
					addBuddyViaReq = true;
				else
					addBuddyViaReq = false;
			}
		}
		myTags = "";
		for (String st : tagMap.keySet())
			myTags += st + " ";
		if (myTags.length() > 0)
			myTags.substring(0, myTags.length() - 1);
	}
	
	public static void saveSettings() throws IOException {
		ConfigWriter c = new ConfigWriter(new FileOutputStream(Config.CONFIG_DIR + "broadcast.ini"));
		c.write("// This file is machine generated, any comments/unknown settings will be removed");
		c.write("// Def: [tag] [alert][minilog][mainlog]");
		for (Entry<String, boolean[]> e : tagMap.entrySet())
			c.write(e.getKey() + " " + e.getValue()[0] + e.getValue()[1] + e.getValue()[2]);
		c.close();
	}
	
	public static boolean hasGUI() {
		try {
			Class.forName("gui.Gui");
			return true;
		} catch (ClassNotFoundException e) {
			// ignored
		}
		return false;
	}
	
	public static void broadcast(String tag, String sender, String msg, boolean ignoreTheirTags, boolean dontAdd) {
		int sent = 0;
		int r = rnd.nextInt();
		String hash = getDigestFor(tag + "|" + sender + "|" + r + "|" + msg);
		if (!dontAdd && !recievedHashes.contains(hash))
			recievedHashes.add(hash);
		for (Buddy bud : BuddyList.buds.values())
			if (bud.getStatus() >= Buddy.ONLINE && (budTags.containsKey(bud.getAddress()) && budTags.get(bud.getAddress()).contains(tag) || ignoreTheirTags)) {
				try {
					bud.sendRaw("broadcast " + tag + " " + sender + " " + r + " " + msg);
					if (!bud.getAddress().equals(Config.us))
						sent++;
				} catch (IOException e) { // Would be strange, but not really a problem - scratch that
					Logger.log(Logger.WARNING, "Broadcast", "Error relaying broadcast to " + bud.getAddress() + " | " + e.getLocalizedMessage());
					try {
						bud.disconnect();
					} catch (IOException e1) {
						// ignored
					}
				}
			}
		if (BuddyList.buds.get(Config.us).getStatus() < Buddy.ONLINE) { // might throw npe, but npe wouuld mean that other code fcked up
			lis.onCommand(BuddyList.buds.get(Config.us), "broadcast " + tag + " " + sender + " " + r + " " + msg);
		}
		if (sent == 0)
			Logger.log(Logger.NOTICE, "Broadcast", "Couldnt send broadcast {" + tag + ", " + sender + ", " + msg + "} to anyone.");
	}

	private static String getDigestFor(String string) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return new String(md.digest(string.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static class Listener implements APIListener, CommandListener {
		
		@Override
		public void onCommand(Buddy b, String s) {
			if (s.startsWith("broadcast ")) {
				String tag = s.split(" ")[1];
				String sender = s.split(" ")[2];
				String rand = s.split(" ")[3];
	//			 broadcast #tag sender msg
				if (tag.startsWith("#")) {
					String msg = s.split(" ", 5)[4];
					String hash = getDigestFor(tag + "|" + sender + "|" + rand + "|" + msg);
					if (myTags.contains(tag) && !recievedHashes.contains(hash)) {
						if (tagMap.containsKey(tag) && tagMap.get(tag)[0]) { // alert
							try {
								Class<?> c = Class.forName("gui.Alert");
								Object o = c.getConstructor(String.class).newInstance(tag + ": " + msg);
								c.getMethod("start").invoke(o);
//								new Alert(tag + ": " + msg).start();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						if (tagMap.containsKey(tag) && tagMap.get(tag)[1] && hasGUI()) { // minilog
							System.out.println("append");
							append("All", sender, msg, tag);
							append(tag, sender, msg, null);
						}
						if (tagMap.containsKey(tag) && tagMap.get(tag)[2]) { // mainlog
							try {
								bcastLogOut.write((b.getAddress() + (char)01 + tag + (char)01 + sender + (char)01 + msg + "\n").getBytes());
								bcastLogOut.flush();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
					Logger.log(Logger.INFO, this, "Broadcast relayed via " + b.getAddress() + " | " + tag + " by " + sender + " with text " + msg + " | " + (myTags.contains(tag) ? "Want" : "Don't want") + " | " + hash + " | Old hash? " + recievedHashes.contains(hash));
					if (!recievedHashes.contains(hash)) {
						recievedHashes.add(hash);
		//				 forward to others
						if (!b.getAddress().equals(Config.us))
							for (Buddy bud : BuddyList.buds.values())
								if (bud.getStatus() >= Buddy.ONLINE && (forwardAll || budTags.containsKey(bud.getAddress())
										&& (budTags.get(bud.getAddress()).contains(tag)))) {
									try {
										if (!bud.getAddress().equals(Config.us) && !bud.getAddress().equals(b.getAddress()))
											bud.sendRaw("broadcast " + tag + " " + sender + " " + rand + " " + msg);
									} catch (IOException e) {
										Logger.log(Logger.WARNING, this, "Error relaying broadcast to " + bud.getAddress() + " | " + e.getLocalizedMessage());
										try {
											bud.disconnect();
										} catch (IOException e1) {
											e1.printStackTrace();
										}
									}
								}
					}
				} /* /tag.startsWith("#")/ */ else if (tag.startsWith("$")) {
					String hash = getDigestFor(tag + "|" + sender + "|" + rand + "|" + null);
					if (tag.equals("$buddyRequest") && !recievedHashes.contains(hash)) {
						recievedHashes.add(hash);
						Logger.log(Logger.NOTICE, this.getClass(), "Recived request from " + sender + " through " +  b.getAddress());
						if (sender.equals(Config.us))
							return; // ignore it
						System.out.println(addBuddyViaReq + " | " + BuddyList.buds.containsKey(sender));
						if (addBuddyViaReq && !BuddyList.buds.containsKey(sender)) {
							Buddy bu = null;
							try {
								Logger.log(Logger.NOTICE, this.getClass(), "Recived request from " + sender + " through " +  b.getAddress() + " adding them.");
								bu = new Buddy(sender, null, false);
								bu.connect();
							} catch (Exception e) {
								try {
									if (bu != null)
										bu.disconnect(); 
									// if anything at all goes wrong, kill the buddy
									// this really shouldnt ever happen, at all, whatsoever
								} catch (IOException e1) {
									// ignored
								} 
							}
						}
						for (Buddy bud : BuddyList.buds.values()) {
							if (bud.getStatus() >= Buddy.ONLINE && !bud.getAddress().equals(Config.us)
									&& !bud.getAddress().equals(sender) && !bud.getAddress().equals(b.getAddress())) {
								try {
	//								broadcast("$buddyRequest", sender, null, true, false);
									bud.sendRaw("broadcast $buddyRequest " + sender + " " + null);// possibly include the name
								} catch (IOException e) {
									Logger.log(Logger.WARNING, "Broadcast", "Error relaying buddy request to " + bud.getAddress() + " | " + e.getLocalizedMessage());
									try {
										bud.disconnect();
									} catch (IOException e1) {
										// ignored
									}
								}
							}
						}
					}
				}
				
	//					BuddyList.buds.get("jutujsy2ufg33ckl").sendRaw("message 'tag' [hopsleft] 'crc' 'sender' 'content'");
			} else if (s.startsWith("mytags ")) {
				Logger.log(Logger.INFO, this, "Got " + b.getAddress() + "'s tags " + s.split(" ", 2)[1]);
				budTags.put(b.getAddress(), s.split(" ", 2)[1]);
			}
		}
		
		@Override
		public void onStatusChange(Buddy buddy, byte newStatus, byte oldStatus) {
			if (myTags == null)
				return;
			if (oldStatus <= Buddy.HANDSHAKE && newStatus >= Buddy.ONLINE) { // just coming online
				try {
					buddy.sendRaw("mytags " + myTags.replace("\n", "")); // also clean input
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	
		@Override
		public void onProfileNameChange(Buddy buddy, String newName, String oldName) {}
		@Override
		public void onProfileTextChange(Buddy buddy, String newText, String oldText) {}
		@Override
		public void onAddMe(Buddy buddy) {}
		@Override
		public void onMessage(Buddy buddy, String s) {}
		@Override
		public void onNewBuddy(Buddy buddy) {}
		@Override
		public void onBuddyRemoved(Buddy buddy) {}
	}

	public static void sendMyTags() {
		for (Buddy b : BuddyList.buds.values()) {
			if (b.getStatus() >= Buddy.ONLINE) {
				try {
					b.sendRaw("mytags " + myTags .replace("\n", "")); // also clean input
				} catch (IOException ioe) {
					try {
						ioe.printStackTrace();
						b.disconnect(); // something is iffy if we error out
					} catch (IOException e) {
						// ignored
					}
				}
			}
		}
	}

	public static void append(String tag, String sender, String msg, String dispTag) {
		DefaultStyledDocument d = minilog.get(tag);
		if (d == null) {
			d = new DefaultStyledDocument();
			minilog.put(tag, d);
			Style timestampStyle = d.addStyle("Time Stamp", null);
		    StyleConstants.setForeground(timestampStyle, Color.gray.darker());
//		    Style myNameStyle = d.addStyle("Sender", null);
//		    StyleConstants.setForeground(myNameStyle, Color.blue.darker());
		    Style theirNameStyle = d.addStyle("Tag", null);
		    StyleConstants.setForeground(theirNameStyle, Color.red.darker());
		}
		try {
			try {
				d.insertString(d.getLength(), "(" + Util.staticMethodInvoke("gui.ChatWindow", "getTime") + ") ", d.getStyle("Time Stamp"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (dispTag != null)
				d.insertString(d.getLength(), "[" + dispTag + "] ", d.getStyle("Tag"));
//			d.insertString(d.getLength(), sender + ": ", d.getStyle("Sender"));
			d.insertString(d.getLength(), msg + "\n", d.getStyle("Plain"));
			BGui.instance.getTextPane2().setCaretPosition(BGui.instance.getTextPane2().getDocument().getLength());
		} catch (BadLocationException ble) {
			ble.printStackTrace();
		}
	}

	
	public static Listener getLis() {
		return lis;
	}
}
