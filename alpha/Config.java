package alpha;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import util.Regex;
import util.Util;

public class Config {
	public static final String VERSIONA = "0";
	public static final String VERSIONB = "7";
	public static final String VERSIONC = "7";
	public static String comment = "";
	public static final String BUILD = VERSIONA+ "."+ VERSIONB + "." + VERSIONC;
	public static final String VERSION = BUILD +" "+ comment; 
	public static String BASE_DIR = "";
	public static String CONFIG_DIR = "";
	public static String DOWNLOAD_DIR = "";
	public static String LOG_DIR = "";
	public static String MESSAGE_DIR = "";
	public static String PAGE_DIR = "";
	public static String TOR_DIR = "";
	public static String LANG_DIR = "";
	public static String DATA_DIR = "";
	public static String ICON_DIR = "";
	public static int SOCKS_PORT;
	public static int LOCAL_PORT;
	public static int alert;
	public static boolean loadTor;
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
	public static String sync;
	public static String update;
	public static String us;
	public static String nowstart = "";
	public static String nowstartupdate = "";
	public static String LastCheck;
	public static final Properties prop;
	public static String lang;
	public static String dlang = "en";
	public static String TorLin = "tor.sh";
	public static String TorLinobf = "torobf.sh";
	public static String TorWin = "tor.exe";
	public static String TorWinobf = "torobf.exe";
	public static String Torbinary;
	public static String controlfile = "controlfile";
	public static String answer = "";

	// Linux only
	public static String Torclose = "torclose.sh";
	public static String torpid = Integer.toString(Util.myRandom(1000, 9999));
	// Only Windows
	public static String TorWintorrc = "torrc.txt";
	public static String TorWintorrcobf = "torrcobf.txt";
	public static String Tortorrc;

	public static int image_size;
	public static int icon_size;
	public static int icon_space;
	public static String icon_folder;

	static {

		if (TCPort.base_pwd != null) {
			BASE_DIR = TCPort.base_pwd;
		} else {
			String x = System.getProperty("java.class.path");
			for (String s : x.split(";"))
				// prioritize finding the jar
				if (Regex.match(".*?[/\\\\]{0,1}jtorchat.*?\\.jar", s.toLowerCase())) {
					BASE_DIR = new File(s).isDirectory() ? new File(s).getPath() : new File(s).getParent();
					break;
				}
			if (BASE_DIR == null || BASE_DIR.length() == 0)
				for (String s : x.split(";"))
					if (!Regex.match("bin(?!.)", s.toLowerCase())) { // dodgy ~
						BASE_DIR = new File(s).isDirectory() ? new File(s).getParent() : new File(s).getParent();
						break;
					} else if (!Regex.match("build(?!.)", s.toLowerCase())) { // dodgy ~
						BASE_DIR = new File(s).isDirectory() ? new File(s).getParent() : new File(s).getParent();
						break;
					}
			if (BASE_DIR == null)
				BASE_DIR = "";
			if (BASE_DIR.length() > 0) {
				if (!BASE_DIR.endsWith("/") && !BASE_DIR.endsWith("\\"))
					BASE_DIR += "/";
			}
		}

		Logger.log(Logger.NOTICE, "Config", "Using " + BASE_DIR + " as BASE_DIR");
		String os = System.getProperty("os.name").toLowerCase(); // Operating System details as a CASE INSENSTIVE string

		DATA_DIR = "data/";
		CONFIG_DIR = Config.BASE_DIR + Config.DATA_DIR + "config/";
		DOWNLOAD_DIR = Config.BASE_DIR + Config.DATA_DIR + "downloads/";
		LOG_DIR = Config.BASE_DIR + Config.DATA_DIR + "log/";
		MESSAGE_DIR = Config.BASE_DIR + Config.DATA_DIR + "offlinemsgs/";
		PAGE_DIR = Config.BASE_DIR + Config.DATA_DIR + "page/";
		TOR_DIR = Config.BASE_DIR + Config.DATA_DIR + "Tor/";
		LANG_DIR = Config.BASE_DIR + Config.DATA_DIR + "lang/";
		// Create all important dir
		new File(CONFIG_DIR).mkdirs();
		new File(DOWNLOAD_DIR).mkdirs();
		new File(LOG_DIR).mkdirs();
		new File(MESSAGE_DIR).mkdirs();
		new File(PAGE_DIR).mkdirs();
		new File(TOR_DIR).mkdirs();
		new File(LANG_DIR).mkdirs();
		new File(ICON_DIR).mkdirs();
		Logger.log(Logger.NOTICE, "Config", "Using " + CONFIG_DIR + " as CONFIG_DIR");
		Logger.log(Logger.NOTICE, "Config", "Using " + DOWNLOAD_DIR + " as DOWNLOAD_DIR");
		Logger.log(Logger.NOTICE, "Config", "Using " + LOG_DIR + " as LOG_DIR");
		Logger.log(Logger.NOTICE, "Config", "Using " + MESSAGE_DIR + " as MESSAGE_DIR");
		Logger.log(Logger.NOTICE, "Config", "Using " + PAGE_DIR + " as PAGE_DIR");
		Logger.log(Logger.NOTICE, "Config", "Using " + TOR_DIR + " as TOR_DIR");
		Logger.log(Logger.NOTICE, "Config", "Using " + LANG_DIR + " as LANG_DIR");

		prop = new Properties();
		File settingsFile = new File(CONFIG_DIR + "settings.ini");
		if (settingsFile.exists() && settingsFile.isFile() && settingsFile.canRead()) {
			try {
				prop.load(new FileInputStream(settingsFile));
			} catch (IOException e) {
				// Do nothing
			}
		}

		us = assign("ourId", null, prop);

		SOCKS_PORT = assignInt("SOCKS_PORT", 11160, prop);
		LOCAL_PORT = assignInt("LOCAL_PORT", 8978, prop);

		TCPort.profile_name = assign("profile_name", null, prop);
		TCPort.profile_text = assign("profile_text", null, prop);
		Config.lang = assign("lang", dlang, prop);
		Config.sync = assign("sync", null, prop);
		Config.update = assign("update", null, prop);
		Config.alert = assignInt("alert", 1, prop);
		Config.loadTor = assignInt("loadPortableTor", 1, prop) == 1 ? true : false;
		Config.buddyStart = assignInt("OnStartBuddySync", 0, prop);
		Config.updateStart = assignInt("OnStartUpdateCheck", 0, prop);
		Config.firststart = assignInt("firststart", 0, prop);
		Config.visiblelog = assignInt("OnStartLoggerDisplay", 1, prop);
		Config.fulllog = assignInt("EnableFullLoggerMode", 1, prop);
		Config.pageactive = assignInt("pageactive", 0, prop);
		Config.transferonstart = assignInt("transferonstart", 0, prop);
		Config.obfsproxy = assignInt("obfsproxy", 0, prop);
		Config.ClickableLinks = assignInt("ClickableLinks", 0, prop);
		Config.offlineMod = assignInt("offlineMod", 0, prop);
		Config.image_size = assignInt("image_size", 16, prop);
		Config.icon_size = assignInt("icon_size", 16, prop);
		Config.icon_space = assignInt("icon_space", 2, prop);
		Config.icon_folder = assign("ICON", "orginal", prop);

		Config.ICON_DIR = Config.BASE_DIR + Config.DATA_DIR + "icon/" + icon_folder;
		if (!new File(ICON_DIR).exists()) {
			Config.ICON_DIR = Config.BASE_DIR + Config.DATA_DIR + "icon/" + "orginal";
		}
		Logger.log(Logger.NOTICE, "Config", "Using " + ICON_DIR + " as ICON_DIR");

		if (Config.buddyStart == 1 & Config.offlineMod == 0) {
			nowstart = sync;
		}

		if (Config.updateStart == 1 & Config.offlineMod == 0) {
			nowstartupdate = update;
		}

		if (os.indexOf("win") >= 0) {
			if (obfsproxy == 0) {
				Torbinary = TorWin;
				Tortorrc = TorWintorrc;
			} else {
				Torbinary = TorWinobf;
				Tortorrc = TorWintorrcobf;
			}
		} else if (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0) {
			if (obfsproxy == 0) {
				Torbinary = TorLin;
			} else {
				Torbinary = TorLinobf;
			}
		}
		answer = language.loadlang();
		Logger.log(Logger.INFO, "Config", "Using " + SOCKS_PORT + " as socks port and " + LOCAL_PORT + " as local port.");
	}

	public static void reloadSettings() { // reloads almost all settings

		new File(CONFIG_DIR).mkdirs();
		prop.clear();
		try {
			prop.load(new FileInputStream(CONFIG_DIR + "settings.ini"));
		} catch (FileNotFoundException e) {
			System.err.println(e.getLocalizedMessage());
		} catch (IOException e) {
			System.err.println(e.getLocalizedMessage());
		}

		SOCKS_PORT = assignInt("SOCKS_PORT", 11160, prop);
		LOCAL_PORT = assignInt("LOCAL_PORT", 8978, prop);
		us = assign("ourId", null, prop);

		TCPort.profile_name = assign("profile_name", null, prop);
		TCPort.profile_text = assign("profile_text", null, prop);
		Config.lang = assign("lang", dlang, prop);
		Config.sync = assign("sync", null, prop);
		Config.update = assign("update", null, prop);
		Config.alert = assignInt("alert", 1, prop);
		Config.loadTor = assignInt("loadPortableTor", 1, prop) == 1 ? true : false;
		Config.buddyStart = assignInt("OnStartBuddySync", 0, prop);
		Config.updateStart = assignInt("OnStartUpdateCheck", 0, prop);
		Config.firststart = assignInt("firststart", 0, prop);
		Config.visiblelog = assignInt("OnStartLoggerDisplay", 1, prop);
		Config.pageactive = assignInt("pageactive", 0, prop);
		Config.transferonstart = assignInt("transferonstart", 0, prop);
		Config.fulllog = assignInt("EnableFullLoggerMode", 1, prop);
		Config.obfsproxy = assignInt("obfsproxy", 0, prop);
		Config.ClickableLinks = assignInt("ClickableLinks", 0, prop);
		Config.offlineMod = assignInt("offlineMod", 0, prop);
		Config.image_size = assignInt("image_size", 20, prop);
		Config.icon_size = assignInt("icon_size", 20, prop);
		Config.icon_space = assignInt("icon_space", 2, prop);
		Config.icon_folder = assign("ICON", "orginal", prop);

		if (Config.buddyStart == 1) {
			nowstart = sync;
		}

		if (Config.updateStart == 1) {
			nowstartupdate = update;
		}

		Logger.log(Logger.INFO, "Config", "Using " + SOCKS_PORT + " as socks port and " + LOCAL_PORT + " as local port.");
	}

	public static final int DEAD_CONNECTION_TIMEOUT = 240;
	public static final int KEEPALIVE_INTERVAL = (int) (Math.random() * 120); // 120;
	public static final int MAX_UNANSWERED_PINGS = 4;
	public static final int CONNECT_TIMEOUT = 70;

	public static final String CLIENT = "JTC [T2]";

	private static int assignInt(String string, int def, Properties prop) {
		String x = (String) prop.get(string);
		int i = def;
		if (x != null) {
			try {
				i = Integer.parseInt(x);
			} catch (NumberFormatException nfe) {
				System.err.println(nfe.getLocalizedMessage());
			}
		}
		if (i == def) {
			Logger.log(Logger.NOTICE, "Config", string + " not defined using " + def);
		}
		return i;
	}

	public static String assign(String string, String s, Properties prop) {
		String x = (String) prop.get(string);
		String ret = s;
		if (x != null) {
			ret = x;
		}
		if (ret == null && s == null || s != null && ret != null && ret.equals(s)) {
			Logger.log(Logger.NOTICE, "Config", string + " not defined using " + s);
		}
		return ret;
	}

}
