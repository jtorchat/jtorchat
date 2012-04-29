package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import alpha.Buddy;
import alpha.Config;
import alpha.Logger;
import alpha.TCPort;


public class ConfigWriter {
	private FileOutputStream fos;
	
	public ConfigWriter(FileOutputStream fos) {
		this.fos = fos;
	}
	
	public void write(String h) throws IOException {
		fos.write((h + "\n").getBytes());
		fos.flush();
	}
	
	public void close() throws IOException {
		fos.close();
	}
	
	
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
	
	
public static void saveall(int really)
{
	Config.prop.clear();
if (really == 0 || really == 2)
{
	Config.prop.put("SOCKS_PORT", Config.SOCKS_PORT + "");
	Config.prop.put("LOCAL_PORT", Config.LOCAL_PORT + "");
	Config.prop.put("ourId", Config.us == null ? "" : Config.us+ "");
	Config.prop.put("sync", Config.sync+ "");
	Config.prop.put("update", Config.update+ "");
	Config.prop.put("lang", Config.lang+ "");
	Config.prop.put("alert", Config.alert+ "");
	Config.prop.put("loadPortableTor", Config.loadTor + "");
    Config.prop.put("OnStartBuddySync", Config.buddyStart+ "");
    Config.prop.put("OnStartUpdateCheck", Config.updateStart+ "");
    Config.prop.put("transferonstart", Config.transferonstart+ "");
    Config.prop.put("pageactive", Config.pageactive+ "");
    Config.prop.put("OnStartLoggerDisplay", Config.visiblelog+ "");
    Config.prop.put("EnableFullLoggerMode", Config.fulllog+ "");
    Config.prop.put("obfsproxy", Config.obfsproxy+ "");
    Config.prop.put("ClickableLinks", Config.ClickableLinks+ "");
    Config.prop.put("offlineMod", Config.offlineMod+ "");
}
if (really == 1 || really == 2)
{
	Config.prop.put("icon_size",Config.icon_size+ "");
	Config.prop.put("icon_space", Config.icon_space+ "");
	Config.prop.put("ICON", Config.icon_folder+ "");
	Config.prop.put("image_size", Config.image_size+ "");	
}
if (really == 3 || really == 2)
{
	Config.prop.put("profile_name", TCPort.profile_name+ "");
	Config.prop.put("profile_text", TCPort.profile_text+ "");	
}
    
	try {
		Config.prop.store(new FileOutputStream(Config.CONFIG_DIR + "settings.ini"), null);
	} catch (FileNotFoundException fnfe) {
		fnfe.printStackTrace();
	} catch (IOException ioe) {
		ioe.printStackTrace();
	}
}


public static void loadall()
{
File settingsFile;
boolean save = false;

	boolean exists = (new File(Config.CONFIG_DIR + "settings.ini")).exists();
	if (exists) {
	settingsFile = new File(Config.CONFIG_DIR + "settings.ini");
	Logger.log(Logger.NOTICE, "ConfigWriter", "Load from settings.ini");
	} else {
    settingsFile = new File(Config.CONFIG_DIR + "backup/settings.ini");
    Logger.log(Logger.NOTICE, "ConfigWriter", "Load from backup");
    save=true;
	}

	Config.prop.clear();
	if (settingsFile.exists() && settingsFile.isFile() && settingsFile.canRead()) {
		try {
			Config.prop.load(new FileInputStream(settingsFile));
		} catch (IOException e) {
			
		}
	}

	Config.us = assign("ourId", null, Config.prop);
	Config.SOCKS_PORT = assignInt("SOCKS_PORT", 11160, Config.prop);
	Config.LOCAL_PORT = assignInt("LOCAL_PORT", 8978, Config.prop);
	TCPort.profile_name = assign("profile_name", "", Config.prop);
	TCPort.profile_text = assign("profile_text", "", Config.prop);
	Config.lang = assign("lang", Config.dlang, Config.prop);
	Config.sync = assign("sync", "", Config.prop);
	Config.update = assign("update", "", Config.prop);
	Config.alert = assignInt("alert", 1, Config.prop);
	Config.loadTor = assignInt("loadPortableTor", 1, Config.prop);
	Config.buddyStart = assignInt("OnStartBuddySync", 0, Config.prop);
	Config.updateStart = assignInt("OnStartUpdateCheck", 0, Config.prop);
	Config.firststart = assignInt("firststart", 0, Config.prop);
	Config.visiblelog = assignInt("OnStartLoggerDisplay", 1, Config.prop);
	Config.fulllog = assignInt("EnableFullLoggerMode", 1, Config.prop);
	Config.pageactive = assignInt("pageactive", 0, Config.prop);
	Config.transferonstart = assignInt("transferonstart", 0, Config.prop);
	Config.obfsproxy = assignInt("obfsproxy", 0, Config.prop);
	Config.ClickableLinks = assignInt("ClickableLinks", 0, Config.prop);
	Config.offlineMod = assignInt("offlineMod", 0, Config.prop);
	Config.image_size  = assignInt("image_size", 16, Config.prop);
	Config.icon_size = assignInt("icon_size", 16, Config.prop);
	Config.icon_space = assignInt("icon_space", 2, Config.prop);
	Config.icon_folder = assign("ICON", "juan.icon", Config.prop);
	
	if (save){saveall(2);}
}

public static void savebuddy(Buddy b)
{
	Config.prop.clear();
	
	if (b.getProfile_name() != null && b.getProfile_name().length() > 0) {Config.prop.put("profile_name", b.getProfile_name()+ "");};
	if (b.getProfile_text() != null && b.getProfile_text().length() > 0) {Config.prop.put("profile_text", b.getProfile_text()+ "");};
	if (b.getClient() != null && b.getClient().length() > 0) {Config.prop.put("client", b.getClient()+ "");};
	if (b.getVersion() != null && b.getVersion().length() > 0) {Config.prop.put("version", b.getVersion()+ "");};

	
	try {
		Config.prop.store(new FileOutputStream(Config.BUDDY_DIR + b.getAddress()), null);
	} catch (FileNotFoundException fnfe) {
		fnfe.printStackTrace();
	} catch (IOException ioe) {
		ioe.printStackTrace();
	}
}

public static void loadbuddy(Buddy b)
{
	
	Config.prop.clear();
	File settingsFile = new File(Config.BUDDY_DIR + b.getAddress());
	if (settingsFile.exists() && settingsFile.isFile() && settingsFile.canRead()) {
		try {
			Config.prop.load(new FileInputStream(settingsFile));
		} catch (IOException e) {
			
		}
	}
	
	b.setProfileName(assign("profile_name", "", Config.prop));
	b.setProfileText(assign("profile_text", "", Config.prop));
	b.setClient(assign("client", "", Config.prop));
	b.setVersion(assign("version", "", Config.prop));



}

}


