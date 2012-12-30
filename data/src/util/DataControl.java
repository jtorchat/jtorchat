package util;

import gui.GuiLog;
import gui.GuiTorLoading;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;

import core.Config;
import core.Logger;
import core.TCPort;
import core.TorLoader;



public class DataControl {
private static final String CLASS_NAME = TorLoader.class.getName();

public static void init()
{
	Config.BASE_DIR = get_base_pwd();
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
	
	checkData();
	
	Logger.log(Logger.NOTICE, CLASS_NAME, "Using " + Config.CONFIG_DIR + " as CONFIG_DIR");
	Logger.log(Logger.NOTICE, CLASS_NAME, "Using " + Config.DOWNLOAD_DIR + " as DOWNLOAD_DIR");
	Logger.log(Logger.NOTICE, CLASS_NAME, "Using " + Config.LOG_DIR + " as LOG_DIR");
	Logger.log(Logger.NOTICE, CLASS_NAME, "Using " + Config.MESSAGE_DIR + " as MESSAGE_DIR");
	Logger.log(Logger.NOTICE, CLASS_NAME, "Using " + Config.PAGE_DIR + " as PAGE_DIR");
	Logger.log(Logger.NOTICE, CLASS_NAME, "Using " + Config.TOR_DIR + " as TOR_DIR");
	Logger.log(Logger.NOTICE, CLASS_NAME, "Using " + Config.LANG_DIR + " as LANG_DIR");
	Logger.log(Logger.NOTICE, CLASS_NAME, "Using " + Config.BUDDY_DIR + " as BUDDY_DIR");
}

public static String get_base_pwd() {
	String os = System.getProperty("os.name").toLowerCase();
    String BASE_DIR = null;
	
	try {
		if (TCPort.extern_source_path.length > 0)
		{
			BASE_DIR = TCPort.extern_source_path[0];
			BASE_DIR = new File(Config.BASE_DIR).getCanonicalPath() + "/";
		}
		else
		{
		String path = TCPort.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		BASE_DIR = URLDecoder.decode(path, "UTF-8"); 
		Logger.log(Logger.NOTICE, "ConfigWriter", BASE_DIR);

		if (os.indexOf("win") >= 0) { BASE_DIR=BASE_DIR.split("/", 2)[1]; }
		
		File f1 = new File(BASE_DIR);
		
		// is the destination a file then delete this part from the string
	    if(f1.isFile())
	    {
	    BASE_DIR=Util.reverse(BASE_DIR);
	    BASE_DIR=BASE_DIR.split("/", 2)[1]; 
	    BASE_DIR=Util.reverse(BASE_DIR);
	    BASE_DIR=BASE_DIR+"/";
		}
	    // else it is a directory in the output folder and run direct over eclipse
	    else
	    {
	    BASE_DIR=BASE_DIR+"../../../";
	    }
		}
    } catch (IOException e) {
	e.printStackTrace();
    }

	return BASE_DIR;
}


//Check consistent of the Data Folder --> not complete
public static void checkData()
{
final String controlFilePath = Config.BASE_DIR+Config.DATA_DIR+Config.controlfile;
boolean exists = (new File(controlFilePath)).exists(); // check base dir
if (!exists) {
	Logger.log(Logger.WARNING, CLASS_NAME, "Wrong base dir, the controlfile is not found: " + controlFilePath);
	final GuiTorLoading tl = new GuiTorLoading();
	tl.setVisible(true);
	tl.getProgressBar1().setValue(0);
	tl.getProgressBar1().setIndeterminate(false);
	tl.gettextArea1().setText("Wrong base dir, the controlfile is not found!");
	GuiLog.instance.setVisible(true);
while(true)
{
try {
	Thread.sleep(10000);
} catch (InterruptedException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}	
}
}} 


}
