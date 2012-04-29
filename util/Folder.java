package util;

import java.io.File;

import alpha.Config;
import alpha.Logger;
import alpha.TCPort;

public class Folder {

public static void getBaseDir()
{
	if (TCPort.base_pwd != null )
	{
		Config.BASE_DIR = TCPort.base_pwd;
	}
	else
	{
		String x = System.getProperty("java.class.path");
		for (String s : x.split(";")) // prioritize finding the jar
			if (Regex.match(".*?[/\\\\]{0,1}jtorchat.*?\\.jar", s.toLowerCase())) {
				Config.BASE_DIR = new File(s).isDirectory() ? new File(s).getPath() : new File(s).getParent();
				break;
			}
		if (Config.BASE_DIR == null || Config.BASE_DIR.length() == 0)
			for (String s : x.split(";"))
				if (!Regex.match("bin(?!.)", s.toLowerCase())) { // dodgy ~
					Config.BASE_DIR = new File(s).isDirectory() ? new File(s).getParent() : new File(s).getParent();
					break;
				} else if (!Regex.match("build(?!.)", s.toLowerCase())) { // dodgy ~
					Config.BASE_DIR = new File(s).isDirectory() ? new File(s).getParent() : new File(s).getParent();
					break;
				}
		if (Config.BASE_DIR == null)
			Config.BASE_DIR = "";
		if (Config.BASE_DIR.length() > 0) {
			if (!Config.BASE_DIR.endsWith("/") && !Config.BASE_DIR.endsWith("\\"))
				Config.BASE_DIR += "/";
		}
	}

	Logger.log(Logger.NOTICE, "ConfigWriter", "Using " + Config.BASE_DIR + " as BASE_DIR");	
}


public static void getPaths()
{
	Config.DATA_DIR = "data/";
	Config.CONFIG_DIR = Config.BASE_DIR + Config.DATA_DIR + "config/";
	Config.DOWNLOAD_DIR =  Config.BASE_DIR + Config.DATA_DIR + "downloads/";
	Config.LOG_DIR =  Config.BASE_DIR + Config.DATA_DIR + "log/";
	Config.MESSAGE_DIR =  Config.BASE_DIR + Config.DATA_DIR + "offlinemsgs/";
	Config.PAGE_DIR =  Config.BASE_DIR + Config.DATA_DIR + "page/";
	Config.TOR_DIR = Config.BASE_DIR + Config.DATA_DIR + "Tor/";
	Config.LANG_DIR = Config.BASE_DIR + Config.DATA_DIR + "lang/";
	Config.ICON_DIR_MAIN = Config.BASE_DIR + Config.DATA_DIR + "icon/";	
	Config.BUDDY_DIR = Config.BASE_DIR + Config.DATA_DIR + "buddy/";	
	
	Logger.log(Logger.NOTICE, "ConfigWriter", "Using " + Config.CONFIG_DIR + " as CONFIG_DIR");
	Logger.log(Logger.NOTICE, "ConfigWriter", "Using " + Config.DOWNLOAD_DIR + " as DOWNLOAD_DIR");
	Logger.log(Logger.NOTICE, "ConfigWriter", "Using " + Config.LOG_DIR + " as LOG_DIR");
	Logger.log(Logger.NOTICE, "ConfigWriter", "Using " + Config.MESSAGE_DIR + " as MESSAGE_DIR");
	Logger.log(Logger.NOTICE, "ConfigWriter", "Using " + Config.PAGE_DIR + " as PAGE_DIR");
	Logger.log(Logger.NOTICE, "ConfigWriter", "Using " + Config.TOR_DIR + " as TOR_DIR");
	Logger.log(Logger.NOTICE, "ConfigWriter", "Using " + Config.LANG_DIR + " as LANG_DIR");
	Logger.log(Logger.NOTICE, "ConfigWriter", "Using " + Config.BUDDY_DIR + " as BUDDY_DIR");
}

public static void createFolder()
{
	new File(Config.CONFIG_DIR).mkdirs();
	new File(Config.DOWNLOAD_DIR).mkdirs();
	new File(Config.LOG_DIR).mkdirs();
	new File(Config.MESSAGE_DIR).mkdirs();
	new File(Config.PAGE_DIR).mkdirs();
	new File(Config.TOR_DIR).mkdirs();
	new File(Config.LANG_DIR).mkdirs();
	new File(Config.ICON_DIR_MAIN).mkdirs();	
}
	
}
