package core;

import java.io.InputStream;

import fileTransfer.FileTransfer;

public class BuddyIncoming {
	public static void init(String in, Buddy b)	
	{
		
		// Fix filedata Problems and make it saver
		String save = in.split(" ")[0].replaceAll("[^a-zA-Z_]", "");
		
		
		if (save.equals("status")) {in_status(in, b);}
		else if (save.equals("ping")) {in_ping(in,b);} 
		else if (save.equals("pong")) {in_pong(in,b);} 
		else if (save.equals("profile_name")) {in_profile_name(in,b);} 
		else if (save.equals("client")) {in_client(in,b);} 
		else if (save.equals("version")) {in_version(in,b);} 
		else if (save.equals("profile_text")) {in_profile_text(in,b);} 
		else if (save.equals("add_me")) {in_add_me(in,b);} 
		else if (save.equals("remove_me")) {in_remove_me(in,b);} 
		else if (save.equals("message")) {in_message(in,b);} 
		else if (save.equals("disconnect")) {in_disconnect(in,b);} 
		else if (save.equals("not_implemented")) {in_not_implemented(in,b);} 
		else if (save.equals("filedata_ok")) {FileTransfer.in_filedata_ok(b,in);}
		else if (save.equals("filedata_error")) {FileTransfer.in_filedata_error(b,in);}
		else if (save.equals("file_stop_sending")) {FileTransfer.in_file_stop_sending(b,in);}
		else if (save.equals("file_stop_receiving")) {FileTransfer.in_file_stop_receiving(b,in);}
		else {in_nothing(in, b);}
	}
	
    // Why there are two incoming streams?
	public static void init_outin(String in, Buddy b, InputStream c)	
	{
		// Fix filedata Problems and make it saver
		String save = in.split(" ")[0].replaceAll("[^a-zA-Z_]", "");

		if (save.equals("filename")) {FileTransfer.in_filename(b,in,c);}
		else if (save.equals("filedata")) {FileTransfer.in_filedata(b,in,c);}

	}
	
	
	private static void in_status(String in, Buddy b)
	{
		b.lastStatusRecieved = System.currentTimeMillis();
		byte nstatus = in.split(" ")[1].equalsIgnoreCase("available") ? Buddy.ONLINE : in.split(" ")[1].equalsIgnoreCase("xa") ? Buddy.XA : in.split(" ")[1].equalsIgnoreCase("away") ? Buddy.AWAY : -1;
		b.setStatus(nstatus); // checks for change in method
	}
	private static void in_profile_name(String in, Buddy b)
	{
		String old = b.profile_name;
        b.profile_name = in.split(" ", 2)[1];
		APIManager.fireProfileNameChange(b, b.profile_name, old);
	}
	private static void in_client(String in, Buddy b)
	{
		b.client = in.split(" ", 2)[1];
	}
	private static void in_version(String in, Buddy b)
	{
		b.version = in.split(" ", 2)[1];
	}
	private static void in_profile_text(String in, Buddy b)
	{
		String old = b.profile_text;
		b.profile_text = in.split(" ", 2)[1];
		APIManager.fireProfileTextChange(b, b.profile_text, old);
	}
	private static void in_add_me(String in, Buddy b)
	{
		APIManager.fireAddMe(b);
	}
	private static void in_remove_me(String in, Buddy b)
	{
		APIManager.fireRemove(b);
	}
	private static void in_message(String in, Buddy b)
	{
		APIManager.fireMessage(b, in.split(" ", 2)[1]);
	}
	private static void in_not_implemented(String in, Buddy b)
	{
		Logger.log(Logger.NOTICE, b, "Recieved " + in.trim() + " from " + b.address);
	}
	private static void in_disconnect(String in, Buddy b)
	{
		Logger.log(Logger.NOTICE, b, "Recieved disconnect command from " + b.getAddress());
		b.disconnect();
	}
	private static void in_nothing(String in, Buddy b)
	{
		Logger.log(Logger.WARNING, b, "Recieved unknown from " + b.address + " " + in);
		b.sendRaw("not_implemented ");
	}
	private static void in_pong(String in, Buddy b)
	{
		if (in.split(" ")[1].equals(b.cookie)) {
			b.unansweredPings = 0;
			b.recievedPong = true;
			Logger.log(Logger.NOTICE, b, b.address + " sent pong");
			if (b.ourSock != null && b.ourSockOut != null && b.status > Buddy.OFFLINE)
			{b.onFullyConnected();}
			else {
				Logger.log(Logger.SEVERE, b, "[" + b.address + "] - :/ We should be connected here. Resetting connection!");
				b.disconnect();
				b.connect();
				return;
			}
		} else {
			Logger.log(Logger.SEVERE, b, "!!!!!!!!!! " + b.address + " !!!!!!!!!! sent us bad pong !!!!!!!!!!");
			Logger.log(Logger.SEVERE, b, "!!!!!!!!!! " + b.address + " !!!!!!!!!! ~ Disconnecting them");
			b.disconnect();
		}
	}
	

private static void in_ping(String in, Buddy b)
{
		if (b.ourSock == null){b.connect();}
		b.sendPong(in.split(" ")[2]);
}
	
	
	
}
