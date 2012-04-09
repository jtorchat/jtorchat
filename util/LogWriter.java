package util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import alpha.Config;
import alpha.Logger;


public class LogWriter {
	
	public static String LogWrite(String what, int typ, String free){
	    // File anlegen
		  FileWriter writer;
		  File file;
		  
		  String filename = "error";
	if (typ == 0) {	 filename = System.currentTimeMillis() / 1000 + "." + Util.myRandom(100, 1000) +  "-" + "main.log"; };
	if (typ == 1) {	 filename = System.currentTimeMillis() / 1000 + "-" + free + ".log"; };
	file = new File(Config.LOG_DIR + filename);
	     try {

	       writer = new FileWriter(file ,true);

	       writer.write(what);
	     
	    //   writer.write(System.getProperty("line.separator"));
	       writer.flush();
	       writer.close();
		Logger.log(Logger.INFO, "Log", "Log is write in " + filename);

	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	     return filename;
	  }
	
}

