package util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import core.Buddy;
import core.Config;
import core.Logger;
import core.TCPort;
import gui.GuiChatWindow;

public class ChatWindow {

//	update_window(type, w,new_entry,new_textarea,send,add_delay) {

	
	public static void update_window(int type, GuiChatWindow w,String new_entry, String new_textarea, String send, boolean add_delay) {
		String delay="";
		
		if(add_delay)
		{delay = "[Delayed] ";}
		
		
		if(send!=""){	
			
		send = send.trim().replaceAll("\n", "\\\\n").replaceAll("\r", "");
	

		if (!add_delay){
		if(!w.b.sendMessage(send)){
		delay = "[Delayed] "; // Because it is disconnect now
		write_delay(w.b,send);
		}}
		else 
		{
	    write_delay(w.b,send);
		}}
		

		// Not in use but useful
		if(type==0)
		{
		}
		// Send or receive a normal Message
		else if(type==1)
		{
		w.append("Time Stamp", "(" + GuiChatWindow.getTime() + ") ");
		w.append("Me", delay+"Me: ");
		w.addUrlText("Plain",new_entry + "\n");
		}
		else if(type==2)
		{
		w.append("Time Stamp", "(" + GuiChatWindow.getTime() + ") ");
		w.append("Them", delay+"Them: ");
		w.addUrlText("Plain",new_entry + "\n");
		}
		// Send or receive what you or the other do
		else if(type==3)
		{
		w.append("Time Stamp", "(" + GuiChatWindow.getTime() + ") ");
		
		if(TCPort.profile_name!=""){w.append("Me", delay+"* " + TCPort.profile_name+" ");}else
		                           {w.append("Me", delay+"* " + Config.us+" ");}
		w.addUrlText("Plain",new_entry + "\n");
		}
		else if(type==4)
		{
		w.append("Time Stamp", "(" + GuiChatWindow.getTime() + ") ");
		w.append("Them", delay+"* " + w.b.toString()+" ");
		w.addUrlText("Plain",new_entry + "\n");
		}
		// Send or receive a page
		else if(type==5)
		{
		w.append("Time Stamp", "(" + GuiChatWindow.getTime() + ") ");
		w.append("Me", delay+"Me --> ");
		w.addUrlText("Plain",new_entry.trim() + "\n");
		}
		else if(type==6)
		{
		w.append("Time Stamp", "(" + GuiChatWindow.getTime() + ") ");
		w.append("Them", delay+"Them --> ");
		w.addUrlText("Plain",new_entry + "\n");
		}
		// Private
		else if(type==7)
		{
		w.append("Time Stamp", "(" + GuiChatWindow.getTime() + ") ");
		w.append("Me", delay+"Private: ");
		w.addUrlText("Plain",new_entry + "\n");
		}
		
		w.get_textPane1().setCaretPosition(w.get_textPane1().getDocument().getLength());
		w.get_textPane1().requestFocusInWindow();
		if(new_textarea!=null){
		w.get_textArea4().setText(new_textarea);
		}
		w.get_textArea4().requestFocusInWindow();
		 
	}
		
	
	public static void write_delay(Buddy b, String send)
	{
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(Config.MESSAGE_DIR + b.getAddress() + ".txt", true);
		    fos.write(("[Delayed] "+send + "\n").getBytes());
		    fos.close();
		} catch (FileNotFoundException e) {
			Logger.log(Logger.SEVERE, "ChatWindow", "Something went wrong bye write the Delay file.");
		} catch (IOException e) {
			Logger.log(Logger.SEVERE, "ChatWindow", "Something went wrong bye write the Delay file.");
		}
	}

}
