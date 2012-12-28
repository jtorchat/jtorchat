package core;

import gui.GuiKillTor;
import gui.GuiLog;
import gui.GuiTorLoading;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;

import util.ConfigWriter;



public class TorLoader {
	private static final String CLASS_NAME = TorLoader.class.getName();
	private static Process process;
	private static Object loadLock = new Object();

	public static void loadTor() {
		final GuiTorLoading tl = new GuiTorLoading();
		tl.getProgressBar1().setIndeterminate(true);
		tl.setVisible(true);
		ThreadManager.registerWork(ThreadManager.DAEMON, new Runnable() {

			@Override
			public void run() {

					if (Config.answer!=null) // if a language file NOT found
					{
						Logger.log(Logger.FATAL, CLASS_NAME, Config.answer);
						GuiLog.instance.setVisible(true);
						tl.getProgressBar1().setValue(0);
						tl.getProgressBar1().setIndeterminate(false);
						tl.gettextArea1().setText(Config.answer);
					}
					else {

						if (Config.offlineMod == 0)
						{
							Boolean ssfail = false;
							try {
								ServerSocket ss = new ServerSocket(Config.LOCAL_PORT);
								ss.close();
							} catch (IOException e) {
								ssfail = true;
							}
							
						if (ssfail)
						{
							Logger.log(Logger.FATAL, "TCServ", "Failed to test local server.");
							tl.getProgressBar1().setValue(0);
							tl.gettextArea1().setText("Can not bind a port for jtorchat, is another jtorchat instance activ?");
							tl.getProgressBar1().setIndeterminate(false);
						}
						else
						{
							
							
							if (Config.loadTor == 1) // only load portable tor if not testing
							{
								ProcessBuilder pb=null;
								pb = instantiateProcessBuilder();

								pb.directory(new File(Config.TOR_DIR).getAbsoluteFile());
								System.out.println(new File(Config.TOR_DIR).getAbsolutePath());
								pb.redirectErrorStream(true);

								try {
									process = pb.start();
									Scanner s = new Scanner(process.getInputStream());
									while (s.hasNextLine()) {
										String l = s.nextLine();
										Logger.log(Logger.INFO, "Tor", l);

										



										// New progress function (obfsproxy has more then three progress messages)
										if (l.contains("Bootstrapped ")) {
											String[] starting = l.split("%");
											if (starting.length == 2)
											{
												starting = starting[0].split(" ");
												if (starting.length == 6)
												{
													int starting3 = Integer.parseInt(starting[5]);
													if (starting3 <= 100 || starting3 > 0)
													{
														tl.getProgressBar1().setValue(Integer.parseInt(starting[5]));
														tl.gettextArea1().setText(language.langtext[51]);
														tl.getProgressBar1().setIndeterminate(false);
														if(starting3 == 100)
														{
															synchronized(loadLock) {
																
																Config.us = new Scanner(new FileInputStream(Config.TOR_DIR + "hidden_service/hostname")).nextLine().replace(".onion", "");
																Logger.log(Logger.NOTICE, CLASS_NAME, "Set 'us' to " + Config.us);
																loadLock.notifyAll();
																tl.dispose();
															}}}}}}


										if (l.contains("Failed to bind one of the listener")) {
											Logger.log(Logger.FATAL, CLASS_NAME, "The listener Port is in use.");
	
											if (GuiKillTor.newpb == 0)
											{
											tl.gettextArea1().setText("Wait for a answer!");
											GuiKillTor.listenerport();
	                                       	process.destroy();
	           								pb = instantiateProcessBuilder();
	           								pb.directory(new File(Config.TOR_DIR).getAbsoluteFile());
	           								System.out.println(new File(Config.TOR_DIR).getAbsolutePath());
	           								pb.redirectErrorStream(true);
	           								process = pb.start();
	           								s = new Scanner(process.getInputStream());
	           								GuiKillTor.newpb = 2;
	                                        
								
											}
											else
											{
											tl.getProgressBar1().setValue(0);
											tl.getProgressBar1().setIndeterminate(false);
											tl.gettextArea1().setText(language.langtext[48]);
											}
										}


										if (l.contains("Network is unreachable")) {
											Logger.log(Logger.FATAL, CLASS_NAME, "Network is unreachable.");
											tl.getProgressBar1().setValue(0);
											tl.getProgressBar1().setIndeterminate(false);
											tl.gettextArea1().setText("Network is unreachable.");
										}


										if (l.contains("broken state. Dying.")) {
											Logger.log(Logger.FATAL, CLASS_NAME, "Tor has died apparently, Ja.");
											GuiLog.instance.setVisible(true);
											tl.getProgressBar1().setValue(0);
											tl.getProgressBar1().setIndeterminate(false);
											tl.gettextArea1().setText(language.langtext[49]);
										}
									}
									s.close();
								} catch (IOException e) {
									Logger.log(Logger.SEVERE, CLASS_NAME, e.getLocalizedMessage());
									if (e.getLocalizedMessage().contains("Cannot"))
									{
										GuiLog.instance.setVisible(true);
										tl.getProgressBar1().setValue(0);
										tl.getProgressBar1().setIndeterminate(false);
										tl.gettextArea1().setText(language.langtext[50]);

									}
								}
							}
						}

				}
						else
						{
							tl.dispose();

						}
					}
				}





		}, "Starting Tor.", "Tor Monitor Thread");

		if (Config.offlineMod == 0 & Config.loadTor == 1)
		{
			try {
				synchronized(loadLock) {
					loadLock.wait();
				}
			} catch (InterruptedException e) {
			}
		}
	}

	public static void cleanUp() {
		synchronized(loadLock) {
			loadLock.notifyAll();
		}

		Config.firststart = 0;
	    ConfigWriter.saveall(2);
	
		
		if (process != null) {
			Logger.log(Logger.INFO, CLASS_NAME, "Cleaning up.");
			process.destroy();
		}
		
		
		
// fix: On Windows obfsproxy do not close when his linked tor close --> make better in future
		if (Config.os.contains("win") && Config.obfsproxy==1) {
			String command1="taskkill /F /IM jobfsproxy.exe";
			Runtime run = Runtime.getRuntime();
			try {
				Process pr1 = run.exec(command1);
				try {
					pr1.waitFor();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		
	}

	private static ProcessBuilder instantiateProcessBuilder() {
		ProcessBuilder pb;
		Logger.log(Logger.NOTICE, "TorLoader ", "Checking OS");
		// PROCESSBUILDER settings for WINDOWS
		if (Config.os.contains("win")) {

			// if so, then start tor.exe with torrc.txt
			Logger.log(Logger.NOTICE, CLASS_NAME, "Start portable tor in windows");
			pb = new ProcessBuilder(Config.TOR_DIR + Config.Torbinary, "-f", Config.TOR_DIR + Config.Tortorrc);

		} else if (Config.os.contains("nix") || Config.os.contains("nux")) {

			// PROCESSBUILDER settings for LINUX and UNIX
			Logger.log(Logger.NOTICE, CLASS_NAME, "Start portable tor in *nix or linux");
			
			pb = new ProcessBuilder(Config.TOR_DIR +Config.Torbinary,"-f",Config.TOR_DIR + Config.Tortorrc);
			pb.environment().put("LD_LIBRARY_PATH", Config.TOR_DIR+Config.TorLinlib);
			pb.environment().put("LDPATH", Config.TOR_DIR+"linux/lib/");

		}
		else {
			Logger.log(Logger.NOTICE, CLASS_NAME,"Can't detect OS type, using system tor (need system tor to work)");
			pb = new ProcessBuilder("tor", "-f", Config.TOR_DIR + Config.Tortorrc );
		}
		return pb;
	}

}

