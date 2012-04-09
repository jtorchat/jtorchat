package alpha;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class TCServ {
	private static ServerSocket ss;
	private static String error = null;
	protected static boolean running = true;

	public static String init() {
		final Object o = new Object();
		ThreadManager.registerWork(ThreadManager.DAEMON, new Runnable() {
			@Override
			public void run() {
				try {
					try {
						ss = new ServerSocket(Config.LOCAL_PORT);
					} catch (IOException e) {
						Logger.log(Logger.FATAL, "TCServ", "Failed to start local server: " + e.getLocalizedMessage());
//						System.err.println("[" + TCServ.class.getCanonicalName() + "] Failed to start local server: " + e.getLocalizedMessage());
						e.printStackTrace();
						Logger.log(Logger.FATAL, "TCServ", e.getLocalizedMessage());
						TCPort.halt(new RuntimeException("Failed to start local server: " + e.getLocalizedMessage()));
						error="Failed to start local server!";
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
									
//									if (BuddyList.buds)
//									BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
//									Scanner sc = new Scanner(s.getInputStream());
									Scanner sc = new Scanner(new InputStreamReader(s.getInputStream(), "UTF8"));
									sc.useDelimiter("\\n");
//									String l = s.readLine();
									String l = sc.next();
									if (l == null) {
										Logger.log(Logger.SEVERE, "TCServ", "wtf");
										s.close();
										return;
									}
									if (!l.startsWith("ping ")) {
										Logger.log(Logger.SEVERE, "TCServ", l + " doesnt start with ping?!");
										s.close();
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
											try {
											b.sendPong(l.split(" ")[2]); // TODO FIXME URGENT check if not connected!
											} catch (IOException ioe) {
												ioe.printStackTrace();
											}
										b.attatch(s, sc);
									} else {
										
										if (l.split(" ")[1].length() == 16)  // first defend for flooding ping
										{
										Buddy b = new Buddy(l.split(" ")[1], null, false);
										Logger.log(Logger.INFO, "TCServ", "Got ping from unknown address " + l.split(" ")[1] + " with cookie " + l.split(" ")[2]);
										b.setTheirCookie(l.split(" ")[2]);
										if (b.ourSock == null)
											b.connect();
										else
											b.sendPong(l.split(" ")[2]); // TODO FIXME URGENT check if not connected!
										b.attatch(s, sc);
										}
										
										
										return;
									}
								} catch (Exception e) {
									e.printStackTrace();
									try {
										s.close();
									} catch (IOException e1) {
										e1.printStackTrace();
									}
								}
							}
						}, null, null);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, "Starting local server on " + Config.LOCAL_PORT + ".", "Server thread");
		try {
			synchronized(o) {
				o.wait();
			}
		} catch (InterruptedException e) {
			// ignored
		}
		return error;
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
			e.printStackTrace();
		}
	}

}
