package gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

import alpha.Buddy;
import alpha.BuddyList;
import alpha.Config;
import alpha.TCPort;
import alpha.language;

import util.LogWriter;


public class Commands {
private static String answer;
private static String command;


	public static String run(Buddy buddy, String s, String logtext)
	{
		command = s.split(" ")[0];
		answer = "0" + "Your command " + command + " is not exist! Please try /help"; 
		if (command.equals("/nick")){answer = nick(buddy, s);}
		if (command.equals("/profile")){answer = profile(buddy, s);}
		if (command.equals("/sync")){answer = sync(buddy, s);}
		if (command.equals("/quit")){answer = quit(buddy, s);}
		if (command.equals("/dice")){answer = dice(buddy, s);}
		if (command.equals("/time")){answer = time(buddy, s);}
		if (command.equals("/status")){answer = status(buddy, s);}
		if (command.equals("/addbuddy")){answer = addbuddy(buddy, s);}
		if (command.equals("/delbuddy")){answer = delbuddy(buddy, s);}
		if (command.equals("/log")){answer = log(buddy, s, logtext);}
		if (command.equals("/help")){answer = help(buddy, s);}
		if (command.equals("/me")){answer = meout(buddy, s);}
		if (command.equals("/sendpage")){answer = mypage(buddy, s, 3);}
		if (command.equals("/page")){answer = page(buddy, s);}
		return answer;
	}
	
	public static String runin(Buddy buddy, String s)
	{
		command = s.split(" ")[0];
		answer = "0" + "He send " + command + " but i do not understand this!"; 
		if (command.equals("/me")){answer = mein(buddy, s);}
		if (command.equals("/pa")){answer = pain(buddy, s);}
		if (command.equals("/page")){answer = getpage(buddy, s);}
		if (command.equals("/help")){answer = helpout(buddy, s);}
		return answer;
	}
	
	public static String help(Buddy buddy, String s) {
		
	String help;
	
	help  = "\n"+language.langtext[57]+"\n";
	help += "/nick name\n";
	help += "/profile text\n";
	help += "/sync link\n";
	help += "/dice\n";
	help += "/time\n";
	help += "/sendpage site\n";
	help += "/page site\n";
	help += "/status on/away/xa\n";
	help += "/addbuddy torid name\n";
	help += "/delbuddy torid\n";
	help += "/log\n";
	help += "/me\n";
	help += "/quit\n";
	return "0"+help;
	
	}
	
	public static String helpout(Buddy buddy, String s) {
	String help;
	help  = "\nList of extern commands for old versions without own commands!\n";
	help += "/page site\n";
	help += "/me\n";
	boolean flag = buddy.isFullyConnected();
	try {
	if (flag)
	{
	buddy.sendMessage(help);
	}
	else {
		new File(Config.MESSAGE_DIR).mkdirs();
		FileOutputStream fos = new FileOutputStream(Config.MESSAGE_DIR + buddy.getAddress() + ".txt", true);
		fos.write((help+"\n").getBytes());
		fos.close();
	}
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return "0"+"he retrieve a list of extern commands for old versions";
	}
	
	
	public static String getpage(Buddy buddy, String s) {

		if(Config.pageactive == 0) {
			
			boolean flag = buddy.isFullyConnected();
			try {
			if (flag)
			{
			buddy.sendMessage("/pa " + "this command is deactivate from the user");
			}
			else {
				new File(Config.MESSAGE_DIR).mkdirs();
				FileOutputStream fos = new FileOutputStream(Config.MESSAGE_DIR + buddy.getAddress() + ".txt", true);
				fos.write(("/pa " + "this command is deactivate from the user" + "\n").getBytes());
				fos.close();
			}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return "2"+"try the page " + s.substring(6) + " but this function is off";
			
		}
		else
		{
		return mypage(buddy,"/sendpage " + s.substring(6),2);
		}
	}
	
	public static String mein(Buddy buddy, String s) {
		if (s.length() < 5)
		{
		return "1" + s + " is the false syntax!"; 
		}
		return "1"+s.substring(4);
	}
	
	public static String page(Buddy buddy, String s) {
		boolean flag = buddy.isFullyConnected();
		if (s.length() < 7)
		{
		s = "/page index";
		}
		try {
		if (flag)
		{
		buddy.sendMessage("/page " + s.substring(6));
		}
		else {
			new File(Config.MESSAGE_DIR).mkdirs();
			FileOutputStream fos = new FileOutputStream(Config.MESSAGE_DIR + buddy.getAddress() + ".txt", true);
			fos.write(("/page " + s.substring(5) + "\n").getBytes());
			fos.close();
		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "3"+"try to get " + s.substring(6);
	}
	
	
	public static String pain(Buddy buddy, String s) {
		return "2"+s.substring(4);
	}
	
	public static String meout(Buddy buddy, String s) {
		if (s.length() < 5)
		{
		return "0"+"parameter: /me msg";
		}
		boolean flag = buddy.isFullyConnected();
		try {
		if (flag)
		{
		buddy.sendMessage("/me " + s.substring(4));
		}
		else {
			new File(Config.MESSAGE_DIR).mkdirs();
			FileOutputStream fos = new FileOutputStream(Config.MESSAGE_DIR + buddy.getAddress() + ".txt", true);
			fos.write(("/me " + "[Delayed] " + s.substring(4) + "\n").getBytes());
			fos.close();
		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "2"+s.substring(4);
	}

		public static String nick(Buddy buddy, String s) {

				if (s.length() < 6)
				{
				return "0"+"parameter: /nick name";
				}
				TCPort.profile_name = s.substring(6);
				TCPort.sendMyInfo();
				return "0"+"change your Nick to " + s.substring(6);
		}

	


		public static String profile(Buddy buddy, String s) {
			
				if (s.length() < 9)
				{
				return "0"+"parameter: /profile text";
				}
				TCPort.profile_text = s.substring(9);
				TCPort.sendMyInfo();
				return "0"+"change your profile to " + s.substring(9);
		}




		public static String sync(Buddy buddy, String s) {
	
				
			if (s.length() < 6)
			{
			return "0"+"parameter: /sync link";
			}
				Config.nowstart = s.substring(6);
				return "0"+"start sync Buddys with " + s.substring(6); 
			
		}

	


		public static String quit(Buddy buddy, String s) {

				System.exit(0);

              return "";
		}




		public static String dice(Buddy buddy, String s) {
	
				Random rand = new Random();
				   rand.nextInt(100);
				  
			return "1"+"dice: " + (new Integer(rand.nextInt(100))).toString();
			}

	


		public static String time(Buddy buddy, String s) {

				SimpleDateFormat formatter = new SimpleDateFormat ("yyyy.MM.dd 'at' HH:mm:ss ");
				Date currentTime = new Date();
				return "1"+"time: " + formatter.format(currentTime);		
		}

	

		
		public static String status(Buddy buddy, String s)
		{
			if(s.length() < 8 ||  (!s.substring(8).equals("on") &  !s.substring(8).equals("away") &  !s.substring(8).equals("xa")))
			{
		    return "0"+"parameter: /status on/away/xa";
			}
				
		if(s.substring(8).equals("on")){
			Config.updateStatus = 1;
			return "0"+"Set status on";
		}
		else if(s.substring(8).equals("away")){
			Config.updateStatus = 2;
			return "0"+"Set status away";
		}
		else
		{
			Config.updateStatus = 3;	
			return "0"+"Set status xa"; 
		}
		}
		

		public static String addbuddy(Buddy buddy, String s) {

			String[] array = s.split(" ");
			String dispName;
			
	
			if (array.length>3 || array.length<2) {
			return "0"+"parameter: /addbuddy torid name";
			}
			
			String addr = array[1];
			if (addr.length() != 16) {
			return "0"+"The buddy id has 16 characters";
			}
			
			
			if(array.length == 3) { dispName = array[2]; } else  { dispName = ""; }
			
			if (BuddyList.buds.containsKey(addr))
				try {
					BuddyList.buds.get(addr).remove();
				} catch (IOException ioe) {
					System.err.println("Error disconnecting buddy: " + ioe.getLocalizedMessage());
				}
			new Buddy(addr, dispName).connect();
			
			if(array.length == 3) 
			{ 
			return "0"+"the buddy " + dispName + " is now on the list!";
			} else  
			{ 
			return "0"+"the buddy " + addr + " is now on the list!";
			}	
	
	}
	
		public static String delbuddy(Buddy buddy, String s) {

			String[] array = s.split(" ");
	
			if (array.length!=2) {
			return "0"+"parameter: /dellbuddy torid";
			}
			
			String addr = array[1];
			if (addr.length() != 16) {
			return "0"+"The buddy id has 16 characters";
			}
			
			
			
			if(BuddyList.buds.get(array[1]) == null)
			{
				return "0"+"the buddy " + array[1] + " is not in the list!";
			}
			
			try {
				BuddyList.buds.get(array[1]).remove();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
	  
	
			return "0"+"the buddy " + array[1] + " is delete from list!";

	}
		
		
		public static String log(Buddy buddy, String s, String text) {
			
			String filename;
			String buddyname = buddy.getName();
			if(buddyname.equals("")){buddyname = buddy.getAddress();}
			filename = LogWriter.LogWrite(text,1,buddyname);
			  
		return "0"+"this chatbox log here " + filename;
		}

		

        private static String mypage(Buddy buddy, String s, int error){
        	boolean flag = buddy.isFullyConnected();
        	String pagename;
            if(s.length() < 11){ pagename = "index"; }else{ pagename = s.substring(10);} // defaults to page
            // Try to locate the file
            try {                                   
                    return read(pagename,pagename,buddy,error);
            } catch (FileNotFoundException e) {
                    try {                                   
                            return read(pagename+".nfo",pagename,buddy,error);
                    } catch (FileNotFoundException e1) {
                            try {                                   
                                    return error+read(pagename+".txt",pagename,buddy,error);
                            } catch (FileNotFoundException e2) {
                                    
                            		try {
                                		if (flag)
                                		{
                                		buddy.sendMessage("/pa " + "page not found");
                                		}
                            			else {
                            				new File(Config.MESSAGE_DIR).mkdirs();
                            				FileOutputStream fos = new FileOutputStream(Config.MESSAGE_DIR + buddy.getAddress() + ".txt", true);
                            				fos.write(("/pa " + "[Delayed] " + "page not found" + "\n").getBytes());
                            				fos.close();
                            			}
                            			} catch (IOException e111) {
                            				// TODO Auto-generated catch block
                            				e111.printStackTrace();
                            			}
                                    
                                    return error+"page not found";
              }
                            
                    
                    }}}
    

    private static String read(String pagename, String two, Buddy buddy, int error) throws FileNotFoundException{
            String result = "[Page:"+two+"]"; // show the filename
            boolean flag = buddy.isFullyConnected();
            if(two.indexOf('.')!=-1 || two.indexOf('/')!=-1) {
        		try {
            		if (flag)
            		{
            		buddy.sendMessage("/pa " + "page not found");
            		}
        			else {
        				new File(Config.MESSAGE_DIR).mkdirs();
        				FileOutputStream fos = new FileOutputStream(Config.MESSAGE_DIR + buddy.getAddress() + ".txt", true);
        				fos.write(("/pa " + "[Delayed] " + "page not found" + "\n").getBytes());
        				fos.close();
        			}
        			} catch (IOException e111) {
        				// TODO Auto-generated catch block
        				e111.printStackTrace();
        			}
                
                return error+"page not found";
            } 
            Scanner scannerObj = new Scanner(new FileInputStream(Config.PAGE_DIR + pagename));                      
            while (scannerObj.hasNextLine()) {
                    result += "\n"+scannerObj.nextLine();
            }         
			String msg = result.trim().replaceAll("\n", "\\\\n").replaceAll("\n", "\\\\n").replaceAll("\r", "");

            msg = msg.replaceAll("\n", "\\n").trim();
            
			if (msg.trim().endsWith("\\\\n")) {
				msg.substring(0, msg.length() - 6);}
    		try {
    		if (flag)
    		{
    		buddy.sendMessage("/pa " + msg);
    		}
			else {
				new File(Config.MESSAGE_DIR).mkdirs();
				FileOutputStream fos = new FileOutputStream(Config.MESSAGE_DIR + buddy.getAddress() + ".txt", true);
				fos.write(("/pa " + "[Delayed] " + msg + "\n").getBytes());
				fos.close();
			}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            return result;
    }

	
}
