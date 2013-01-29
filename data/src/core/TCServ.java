package core;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;


public class TCServ {
	private static ServerSocket ss;
	protected static boolean running = true;

	public static void init() {
		final Object o = new Object();
		ThreadManager.registerWork(ThreadManager.DAEMON, new Runnable() {
			@Override
			public void run() {
				try {
					try {
						ss = new ServerSocket(Config.LOCAL_PORT);
					} catch (IOException e) {
						Logger.log(Logger.FATAL, "TCServ", "Failed to start local server: " + e.getLocalizedMessage());
						return;
					}
					synchronized(o) {
						o.notifyAll();
					}
					while (ss.isBound() && !ss.isClosed() && running) {

						final Socket s = ss.accept();
						if (!running) {
							s.close();
							return;
						}
						ThreadManager.registerWork(ThreadManager.NORMAL, new Runnable() {
							@Override
							public void run() {
								try {
									

									Scanner sc = new Scanner(new InputStreamReader(s.getInputStream(), "UTF8"));
									sc.useDelimiter("\\n");
									String l = sc.next();
									if (l == null) {
										Logger.log(Logger.SEVERE, "TCServ", "wtf");
										s.close();
										sc.close();
										return;
									}
									if (!l.startsWith("ping ")) {
										Logger.log(Logger.SEVERE, "TCServ", l + " doesnt start with ping?!");
										s.close();
										sc.close();
										return;
									}
									if (BuddyList.buds.containsKey(l.split(" ")[1])) { // TODO add check to see if its different cookie from last time
										Logger.log(Logger.INFO, "TCServ", "Got ping from " + l.split(" ")[1] + " with cookie " + l.split(" ")[2]);
										Buddy b = BuddyList.buds.get(l.split(" ")[1]);
										Logger.log(Logger.INFO, "TCServ", "Match " +  l.split(" ")[1] + " to " + b.getAddress());
										b.setTheirCookie(l.split(" ")[2]);
										if (b.ourSock == null)
											b.connect();
										else if (b.ourSockOut != null)
											b.sendPong(l.split(" ")[2]); // TODO FIXME URGENT check if not connected!
										b.attatch(s, sc);
									} else {
										
										if (l.split(" ")[1].length() == 16)  // first defend for flooding ping
										{
										if (!l.split(" ")[1].equals(Config.us)){
										Buddy b = new Buddy(l.split(" ")[1], null, false);
										
										Logger.log(Logger.INFO, "TCServ", "Got ping from unknown address " + l.split(" ")[1] + " with cookie " + l.split(" ")[2]);
										b.setTheirCookie(l.split(" ")[2]);
										if (b.ourSock == null)
											b.connect();
										else
											b.sendPong(l.split(" ")[2]); // TODO FIXME URGENT check if not connected!
										b.attatch(s, sc);
										}
										}
										
										
										return;
									}
								} catch (SocketException se) {
									// ignored
									try {
										s.close();
									} catch (IOException e1) {
									}
								} catch (Exception e) {
									try {
										s.close();
									} catch (IOException e1) {
									}
								}
							}
						}, null, null);
					}
				} catch (Exception e) {
				}
			}
		}, "Starting local server on " + Config.LOCAL_PORT + ".", "Server thread");

		try {
			synchronized(o) {
				o.wait();
			}
		} catch (InterruptedException e) {
		
		}
		return;
	}

	public static void halt() {
		running = false;
		try {
			if (ss != null) {
				ss.close();
				Logger.log(Logger.NOTICE, "TCServ", "Terminated.");
			} else
				Logger.log(Logger.SEVERE, "TCServ", "ss == null!!");
		} catch (IOException e) {

		}
	}

}
