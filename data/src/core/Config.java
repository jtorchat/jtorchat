package core;


import java.io.File;
import java.util.Properties;

import util.ConfigWriter;
import util.DataControl;
import util.Util;

public class Config {

	// What Client and Version
	public static final String CLIENT = "JTC [T2]";
	public static final String VERSIONA = "0";
	public static final String VERSIONB = "7";
	public static final String VERSIONC = "11";
	public static String comment = "";
	
	// Test for connections
	public static final int DEAD_CONNECTION_TIMEOUT = 240;
	public static final int KEEPALIVE_INTERVAL = (int) (Math.random()*120); //120;
	public static final int MAX_UNANSWERED_PINGS = 4;
	public static final int CONNECT_TIMEOUT = 70;

	// Compile with 
	public static final String BUILD = VERSIONA+ "."+ VERSIONB + "." + VERSIONC;
	public static final String VERSION = BUILD +" "+ comment;

	// Important Folders
	public static String BASE_DIR = "";
	public static String BUDDY_DIR = "";
	public static String CONFIG_DIR = "";
	public static String DOWNLOAD_DIR = "";
	public static String LOG_DIR = "";
	public static String MESSAGE_DIR = ""; 
	public static String PAGE_DIR = ""; 
	public static String TOR_DIR = "";
	public static String LANG_DIR = "";
	public static String DATA_DIR = "";
	public static String ICON_DIR = "";
	public static String ICON_DIR_MAIN = "";
	
	
    // Config or Control Variables
	public static final Properties prop;
	public static int loadTor;
	public static int SOCKS_PORT; 
	public static int LOCAL_PORT; 
	public static int alert; 
	public static int visiblelog;
	public static int fulllog;
	public static int buddyStart;
	public static int updateStart;
	public static int updateStatus = 0;
	public static int firststart;
	public static int pageactive;
	public static int transferonstart;
	public static int allcheckupdate;
	public static int obfsproxy;
	public static int ClickableLinks;
	public static int offlineMod;
	public static int image_size;
	public static int icon_size;
	public static int icon_space;
	public static String icon_folder;
	public static String sync; 
	public static String update; 
	public static String us;
	public static String nowstart = "";
	public static String nowstartupdate = "";
	public static String LastCheck;
	public static String lang;
	public static String dlang = "en";
	public static String answer;
	public static String os;

	
	// Paths to all Imortant Files
	public static String TorLin="linux/jtor.lin";
	public static String TorLinobf="linux/jtorobf.lin";
	public static String TorLinlib="linux/";
	public static String TorWin="windows/jtor.exe";
	public static String TorWinobf="windows/jtorobf.exe";
	public static String Torbinary;
	public static String controlfile = "controlfile";
	
	// Linux only
	public static String Torclose="linux/torclose.sh";
	public static String torpid=Integer.toString(Util.myRandom(1000, 9999));

	public static String TorWINLINtorrc="torrc.txt";
	public static String TorWINLINtorrcobf="torrcobf.txt";
	public static String Tortorrc;



	static {
		os = System.getProperty("os.name").toLowerCase();
		DataControl.init();
		prop = new Properties();
		ConfigWriter.loadall();


		// Choose the right Folder with Icons
		Config.ICON_DIR = Config.ICON_DIR_MAIN + icon_folder;
		if(!new File(ICON_DIR).exists()){Config.ICON_DIR = Config.ICON_DIR_MAIN + "juan.icon";}
		Logger.log(Logger.NOTICE, "Config", "Using " + ICON_DIR + " as ICON_DIR");
		if (Config.buddyStart == 1 & Config.offlineMod == 0){nowstart=sync;}
		if (Config.updateStart == 1 & Config.offlineMod == 0){nowstartupdate=update;}

		// Choose the right binary for every system
		if (os.indexOf("win") >= 0) {
			if(obfsproxy == 0){Torbinary=TorWin;Tortorrc=TorWINLINtorrc;}
			else{Torbinary=TorWinobf;Tortorrc=TorWINLINtorrcobf;}
		} else if (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0) {
			if(obfsproxy == 0){Torbinary=TorLin;Tortorrc=TorWINLINtorrc;}
			else{Torbinary=TorLinobf;Tortorrc=TorWINLINtorrcobf;}
		}
		
		answer = language.loadlang();
		Logger.log(Logger.INFO, "Config", "Using " + SOCKS_PORT + " as socks port.");
		Logger.log(Logger.INFO, "Config", "Using " + LOCAL_PORT + " as local port.");
	}

}
