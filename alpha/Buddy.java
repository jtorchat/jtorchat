package alpha;

import gui.Tray;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.net.SocketException;
import java.util.Random;
import java.util.Scanner;

public class Buddy {

	public static final byte OFFLINE = 0;
	public static final byte HANDSHAKE = 1;
	public static final byte ONLINE = 2;
	public static final byte AWAY = 3;
	public static final byte XA = 4;

	private String address;
	private String name; // wtf is this for?, custom name? todo use this somewhere -- used
	public volatile Socket ourSock = null; // Our socket to them - the output sock
	public volatile OutputStreamWriter ourSockOut = null;
	public volatile Socket theirSock = null; // Their socket to us - the input sock
	private String cookie = null;
	private String theirCookie = null;
	private byte status = OFFLINE;

	private int connectFailCount = 0;

	private Random random;
	public boolean recievedPong;
	private boolean sentPong;
	private String profile_name;
	private String client = "";

	private String version = "";
	private String profile_text;
	private Object connectLock = new Object();

	private long connectedAt;
	private long connectTime;
	protected long reconnectAt;
	public long lastPing = -1;
	public long lastStatus = -1;
	public long lastStatusRecieved;
	public int unansweredPings;

	public Buddy(String address, String name, Boolean now) {
		this.random = new Random();

		this.address = address;
		this.name = name;

		this.cookie = generateCookie();

		if (now) // Prevent flooding
		{
			BuddyList.addBuddy(this);
		}
	}

	private String generateCookie() {
		String s = "";
		String a = "abcdefghijklmnopqrstuvwxyz1234567890";
		for (int i = 0; i < 77; i++)
			s += a.charAt(random.nextInt(a.length()));
		return s;
	}

	public void connect() {
		if (!getBlack()) {
			if (ourSock != null) {
				reconnectAt = -1;
				Logger.log(Logger.WARNING, this, "Connect(V)V was called but ourSock isn't null!");
				Thread.dumpStack();
				return;
			}
			// maybe store sock connection in another variable then move it to
			// ourSock when connected - fixed with ourSockOut
			ThreadManager.registerWork(ThreadManager.NORMAL, new Runnable() {

				@Override
				public void run() {
					if (!getBlack()) {
						try {
							reconnectAt = -1;
							connectTime = System.currentTimeMillis();
							ourSock = new Socket(new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("127.0.0.1", Config.SOCKS_PORT)));
							ourSock.connect(InetSocketAddress.createUnresolved(address + ".onion", 11009));
							setStatus(HANDSHAKE);
							Logger.log(Logger.INFO, Buddy.this, "Connected to {" + address + ", " + name + "}");
							// ourSockOut = ourSock.getOutputStream();
							ourSockOut = new OutputStreamWriter(ourSock.getOutputStream(), "UTF-8");
							sendPing();
							if (theirCookie != null) {
								sendPong(theirCookie);
								Logger.log(Logger.DEBUG, Buddy.this, "Sent " + address + " a cached pong");
								theirCookie = null; // cbb to clear properly elsewhere
							}
							connectTime = -1;
							if (ourSock == null)
								Logger.log(Logger.SEVERE, Buddy.this, "Wtf?! ourSock is null, but we just connected");
							connectFailCount = 0;
							// System.err.println(ourSockOut);
						} catch (Exception e) {
							connectFailCount++;
							if (ourSock != null)
								try {
									ourSock.close();
								} catch (IOException e1) {
									// we should'nt have to worry about this
								}
							ourSock = null;
							ourSockOut = null;
							setStatus(OFFLINE);
							Logger.log(Logger.WARNING, Buddy.this, "Failed to connect to " + address + " : " + e.getMessage() + " | Retry in " + (reconnectAt - System.currentTimeMillis()));
							// e.printStackTrace();
							connectTime = -1;
						}

						synchronized (connectLock) {
							connectLock.notifyAll(); // incase something messed up we clear it out of the way so it should work next time
						}
						if (ourSockOut != null) {
							try {
								InputStream is = ourSock.getInputStream();
								byte b;
								String s = "";
								while ((b = (byte) is.read()) != -1) {
									if ((char) b == '\n') { // shouldnt happen
										Logger.log(Logger.SEVERE, Buddy.this.getClass(), "Recieved unknown '" + s + "' on ourSock from " + Buddy.this.toString(true));
										s = "";
										continue;
									}
									s += (char) b;
									if ((char) b == ' ' && !s.substring(0, s.length() - 1).contains(" ")) {
										if (APIManager.incomingCmdListeners.containsKey(s.trim())) {
											APIManager.incomingCmdListeners.get(s.trim()).onCommand(Buddy.this, s, is);
											s = "";
										}
									}
								}
								Logger.log(Logger.SEVERE, Buddy.this.getClass(), "BROKEN - " + address);
								// ourScanner = new Scanner(new InputStreamReader(ourSock.getInputStream(), "UTF-8"));
								// Logger.oldOut.println("erar");
								// ourScanner.useDelimiter("\n");
								// while (ourScanner.hasNext()) {
								// Logger.oldOut.println("ss");
								// String l = ourScanner.next();
								// Logger.oldOut.println("aaaaaaaaaaaaaaaaaaaaaaaaaaa");
								// if (APIManager.incomingCmdListeners.containsKey(l.split(" ")[0])) {
								// APIManager.incomingCmdListeners.get(l.split(" ")[0]).onCommand(Buddy.this, l);
								// } else
								// Logger.log(Logger.SEVERE, Buddy.this.getClass(), "Recieved unknown '" + l + "' on ourSock from " + Buddy.this.toString(true));
								// Logger.oldOut.println("esnd her");
								// }
								// Logger.oldOut.println("BROKEN");
							} catch (Exception e) {
								e.printStackTrace();
								try {
									disconnect();
								} catch (IOException ioe) {
									// ignored
								}
							}
						}
					}
				}
			}, "Connect to " + address, "Connection thread for " + address);
		}
	}

	public void setmyStatus(int zustand) {

		if (zustand > 0 & zustand < 4) {
			if (zustand == 1) {
				TCPort.status = "available";
			}
			if (zustand == 2) {
				TCPort.status = "away";
			}
			if (zustand == 3) {
				TCPort.status = "xa";
			}
			// try {
			// sendStatus();
			// } catch (IOException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			Logger.log(Logger.INFO, "Buddy", "Status set " + TCPort.status);
		}
	}

	protected void setStatus(byte nstatus) {
		if (nstatus == OFFLINE) {
			// if (address.equals("jutujsy2ufg33ckl"))
			// Thread.dumpStack();
			reconnectAt = connectFailCount < 4 ? (15) : connectFailCount < 15 ? (5 * 60) : (30 * 60);
			reconnectAt *= (7 + random.nextInt(13)) / 10d;
			reconnectAt *= 1000;
			reconnectAt += System.currentTimeMillis();
			sentPong = false;
			recievedPong = false;
			unansweredPings = 0;
			ourSockOut = null;
		}
		if (nstatus == HANDSHAKE && status == OFFLINE) {
			lastPing = System.currentTimeMillis(); // During handshake lastStatus is used as lastPing
		}
		if (nstatus >= ONLINE && status <= HANDSHAKE) { // if connection just finished
			connectedAt = System.currentTimeMillis();
		} else if (nstatus == OFFLINE && status >= ONLINE) {
			Logger.log(Logger.INFO, this, address + " connected for " + (connectedAt == -1 ? " Not Set" : ((System.currentTimeMillis() - connectedAt) / 1000)));
			connectedAt = -1;
		}
		if (this.status != nstatus) {
			APIManager.fireStatusChange(this, nstatus, status);
			status = nstatus;
			Tray.updateTray();
		}
	}

	public void sendPing() throws IOException {
		sendRaw("ping " + Config.us + " " + cookie);
		unansweredPings++;
		if (status == OFFLINE || status == HANDSHAKE)
			lastPing = System.currentTimeMillis();
	}

	public Object OSO_LOCK = new Object(); // OurSock Outputstream lock
	public Object TSO_LOCK = new Object(); // TheirSock Outputstream lock
	public Scanner ourScanner;
	private int npe1Count;

	public void sendRaw(String command) throws IOException {
		if (!getBlack()) {
			synchronized (OSO_LOCK) {
				try {
					Logger.log(Logger.DEBUG, this, "Send " + address + " " + command);
					ourSockOut.write((command + ((char) 10)));
					ourSockOut.flush();
				} catch (IOException e) {
					Logger.log(Logger.WARNING, this, "[" + address + "] ourSock = null; theirSock = null; " + e.getLocalizedMessage());
					disconnect();
					throw e;
				}
			}
		}
	}

	public String getAddress() {
		return address;
	}

	public Boolean getBlack() {
		if (BuddyList.black.containsKey(address)) {
			return true;
		} else {
			return false;
		}
	}

	public Boolean getHoly() {
		if (BuddyList.holy.containsKey(address)) {
			return true;
		} else {
			return false;
		}
	}

	public void sendPong(String pong) throws IOException {
		sendRaw("pong " + pong);
		sentPong = true;
	}

	public void sendClient() throws IOException {
		sendRaw("client " + Config.CLIENT);
	}

	public void sendVersion() throws IOException {
		sendRaw("version " + Config.VERSION);
	}

	public void sendProfileName() throws IOException {
		sendRaw("profile_name " + TCPort.profile_name);
	}

	public void sendProfileText() throws IOException {
		sendRaw("profile_text " + TCPort.profile_text);
	}

	public void sendAddMe() throws IOException {
		sendRaw("add_me");
	}

	public void sendStatus() throws IOException {

		if (Config.updateStatus > 0 & Config.updateStatus < 4) {
			setmyStatus(Config.updateStatus);
			Config.updateStatus = 0;
		}

		sendRaw("status " + TCPort.status);
		lastStatus = System.currentTimeMillis();

	}

	public void setTheirCookie(String theirCookie) {
		this.theirCookie = theirCookie;
	}

	public void onFullyConnected() throws IOException {
		sendClient();
		sendVersion();
		sendProfileName();
		sendProfileText();
		if (true) // check if already added? - one of those things that's always sent?
			sendAddMe();

		sendStatus();

		if (!BuddyList.buds.containsKey(this.address)) {
			BuddyList.addBuddy(this);
		}

	}

	public void attatch(Socket s, Scanner sc) throws IOException {

		if (!getBlack()) {
			if (theirSock != null) {
				disconnect();
				connect(); // TODO might need to do something about this entire block
				synchronized (connectLock) {
					try {
						Logger.log(Logger.NOTICE, this, "Waiting...");
						// connectLock.wait(45000); // 45sec wait for conenct
						connectLock.wait(); // wait for conenct
						// !NOTE! notify is called regardless of success or failure
					} catch (InterruptedException e) {
					}
				}
			}
			if (status == OFFLINE && connectTime != -1) {
				// connect() method is trying to connect atm

				// is severe so its printed on err
				Logger.log(Logger.SEVERE, this, "status == OFFLINE && connectTime != -1");
				// synchronized(connectLock) {
				// try {
				// // connectLock.wait(45000); // 45sec wait for conenct
				// Logger.log(Logger.NOTICE, this, "Waiting...");
				// connectLock.wait(); // wait for conenct
				// // !NOTE! notify is called regardless of success or failure
				// } catch (InterruptedException e) { }
				// }
			}
			// FIXME really need to fix replying to commands before we're connected
			this.theirSock = s;
			this.recievedPong = false;
			try {
				while (sc.hasNext()) {
					if (!getBlack()) {
						String l = sc.next();
						if (!sentPong && theirCookie != null) {
							try {
								sendPong(theirCookie);
							} catch (NullPointerException npe) {
								Logger.log(Logger.INFO, Buddy.this, "1Caught npe on " + address);
								if (npe1Count++ > 5) {
									disconnect();
									connect();
									return;
								}
							}
						}
						if (l.startsWith("pong ")) {
							if (l.split(" ")[1].equals(cookie)) {
								unansweredPings = 0;
								// also implies that oursock is fully connected aswell
								// but not nessesarily
								recievedPong = true;
								Logger.log(Logger.NOTICE, this, address + " sent pong");
								if (ourSock != null && ourSockOut != null && status > OFFLINE) {
									onFullyConnected();
								} else {
									Logger.log(Logger.SEVERE, this, "[" + address + "] - :/ We should be connected here. Resetting connection!");
									disconnect();
									connect();
									return;
								}
							} else {
								Logger.log(Logger.SEVERE, this, "!!!!!!!!!! " + address + " !!!!!!!!!! sent us bad pong !!!!!!!!!!");
								Logger.log(Logger.SEVERE, this, "!!!!!!!!!! " + address + " !!!!!!!!!! ~ Disconnecting them");
								disconnect();
							}
						} else if (l.startsWith("ping ")) {
							if (ourSock == null)
								connect();
							try {
								sendPong(l.split(" ")[2]);
							} catch (NullPointerException npe) {
								Logger.log(Logger.INFO, Buddy.this, "2Caught npe on " + address + ", DC.");
								disconnect();
							}
							theirCookie = null;
						} else if (!recievedPong) {
							Logger.log(Logger.WARNING, this, "Recieved before pong from " + address + " " + l);
							continue;
						} else

						if (l.startsWith("status ")) {
							lastStatusRecieved = System.currentTimeMillis();
							byte nstatus = l.split(" ")[1].equalsIgnoreCase("available") ? ONLINE : l.split(" ")[1].equalsIgnoreCase("xa") ? XA : l.split(" ")[1].equalsIgnoreCase("away") ? AWAY : -1;
							setStatus(nstatus); // checks for change in method
						} else if (l.startsWith("profile_name")) {
							String old = profile_name;
							if (l.split(" ").length > 1 && (profile_name == null || !profile_name.equals(l.split(" ", 2)[1])))
								profile_name = l.split(" ", 2)[1];
							else
								profile_name = "";
							APIManager.fireProfileNameChange(this, profile_name, old);
						} else if (l.startsWith("client")) {
							client = l.split(" ", 2)[1];
						} else if (l.startsWith("version")) {
							version = l.split(" ", 2)[1];
						} else if (l.startsWith("profile_text")) {
							String old = profile_text;
							if (l.split(" ").length > 1 && (profile_text == null || !profile_text.equals(l.split(" ", 2)[1])))
								profile_text = l.split(" ", 2)[1];
							else
								profile_text = "";
							APIManager.fireProfileTextChange(this, profile_text, old);
						} else if (l.startsWith("add_me")) {
							APIManager.fireAddMe(this);
						} else if (l.startsWith("remove_me")) {
							APIManager.fireRemove(this);
						} else if (l.startsWith("message ")) {
							APIManager.fireMessage(this, l.split(" ", 2)[1]);
						} else if (l.startsWith("not_implemented")) {
							Logger.log(Logger.NOTICE, this, "Recieved " + l.trim() + " from " + address);
						} else if (APIManager.cmdListeners.containsKey(l.split(" ")[0])) {
							APIManager.cmdListeners.get(l.split(" ")[0]).onCommand(this, l);
						} else if (l.startsWith("profile_avatar")) { // will match both profile_avatar_alpha and profile_avatar
							Logger.log(Logger.NOTICE, this, "Sorry, we have no avatar support. Coming soon.");
						} else {
							Logger.log(Logger.WARNING, this, "Recieved unknown from " + address + " " + l);
							sendRaw("not_implemented ");
						}
					}
				}
			} catch (SocketException se) {
				Logger.log(Logger.DEBUG, this, "[" + address + "] attatch() " + se.getLocalizedMessage() + " | " + se.getStackTrace()[0]);
				// SocketExceptions are quite common and generally nothing to worry about
			} catch (IOException e) {
				Logger.log(Logger.WARNING, this, "[" + address + "] theirSock = null; ourSock = null; " + e.getLocalizedMessage() + " | " + e.getStackTrace()[0]);
				disconnect();
				throw e;
			}
		}
	}

	public static boolean checkSock(Socket s) {
		return s != null && s.isConnected() && !s.isClosed();
	}

	public void sendMessage(String string) throws IOException {
		sendRaw("message " + string);
	}

	public static String getStatusName(byte b) {
		return b == OFFLINE ? "Offline" : b == HANDSHAKE ? "Handshake" : b == ONLINE ? "Online" : b == AWAY ? "Away" : b == XA ? "Extended Away" : "Idk.";
	}

	public long getTimeSinceLastStatus() {
		return System.currentTimeMillis() - lastStatus;
	}

	@Override
	public String toString() {
		return (name != null && name.length() > 0) ? name : (profile_name != null && profile_name.length() > 0) ? profile_name : "[" + address + "]"; // + " (" + address + ")";
	}

	public byte getStatus() {
		return status;
	}

	public String getProfile_name() {
		return profile_name;
	}

	public String getClient() {
		return client;
	}

	public String getProfile_text() {
		return profile_text;
	}

	public String getVersion() {
		return version;
	}

	public long getConnectTime() {
		return connectTime;
	}

	public void disconnect() throws IOException { // should be used with caution
		if (ourSock != null)
			ourSock.close();
		ourSock = null;
		if (theirSock != null)
			theirSock.close();
		theirSock = null;
		setStatus(OFFLINE);
		Logger.log(Logger.NOTICE, this, "Disconnect called on " + address + " | Retry in " + (reconnectAt - System.currentTimeMillis()));
	}

	public void remove() throws IOException {
		BuddyList.buds.remove(address);
		try {
			sendRaw("remove_me");
		} catch (IOException e) {
		}
		disconnect();
		APIManager.fireBuddyRemoved(this);
	}

	public String getName() {
		return name;
	}

	public String toString(boolean b) {
		return address.equals(Config.us) ? language.langtext[61] : (profile_name != null && profile_name.length() > 0) ? profile_name + " (" + address + ")" : (name != null && name.length() > 0) ? name + " (" + address + ")" : "[" + address + "]"; // + " (" + address + ")";
	}

	public boolean isFullyConnected() {
		return ourSockOut != null && ourSock != null && ourSock.isConnected() && !ourSock.isClosed() && theirSock != null && theirSock.isConnected() && !theirSock.isClosed();
	}

	public void setName(String text) {
		this.name = text;
	}

	public void setProfileName(String text) {
		this.profile_name = text;
	}

	public void setProfileText(String text) {
		this.profile_text = text;
	}

	public void setVersion(String text) {
		this.version = text;
	}

	public void setClient(String text) {
		this.client = text;
	}

}
