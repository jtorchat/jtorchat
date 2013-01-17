package util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import core.Config;
import core.Logger;


public class RequestHTTP {
	
private static final String CLASS_NAME = RequestHTTP.class.getName();


public static  ArrayList<String> load(String remote_URL) {
ArrayList<String> file = new ArrayList<String>();


		if ((remote_URL == null)||(remote_URL == "")) {
			Logger.log(Logger.INFO, CLASS_NAME,"No URL defined!!!.");
			return file;
		} 
		Logger.log(Logger.INFO, CLASS_NAME,"URL LOCATION: " + remote_URL);
		
		// Fix URL
		if(!(remote_URL.startsWith("http://")||remote_URL.startsWith("https://")))
		{remote_URL = "http://"+remote_URL;}

		URL aURL;
		try {
			aURL = new URL(remote_URL);
		} catch (MalformedURLException e) {
			Logger.log(Logger.INFO, CLASS_NAME,"Problems with the URL.");
			return file;
		}
		
		String host = aURL.getHost();
		int port = aURL.getPort();
		String path = aURL.getPath(); 
		
		
		// Fix Port
		if (port < 0) {
			if(remote_URL.startsWith("http://"))
			{port = 80;}
			
			if(remote_URL.startsWith("https://"))
			{port = 443;}
		}

		// Declare a new proxyed socket and connect to it (No DNS leak via createUnresolved)
		Logger.log(Logger.INFO, CLASS_NAME,"Connect to proxy with port: "+ Config.SOCKS_PORT + " and host: 127.0.0.1");
		Socket ourSock = new Socket(new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("127.0.0.1", Config.SOCKS_PORT)));
		Logger.log(Logger.INFO, CLASS_NAME,"Starting secure remote proxy tunnel to "+host+" with port "+port);
		try {
			ourSock.connect(InetSocketAddress.createUnresolved(host, port));
		} catch (IOException e) {
			Logger.log(Logger.INFO, CLASS_NAME,"Problems with create a Unresolved InetSocketAddress.");
			return file;
		}
					
		InputStream is;
		OutputStream os;
					
		if(remote_URL.startsWith("https://")){
		SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
		SSLSocket socket;
		try {
		socket = (SSLSocket) factory.createSocket(ourSock,"127.0.0.1",Config.SOCKS_PORT,true);
		is = socket.getInputStream();
		os = socket.getOutputStream();
		} catch (IOException e) {
			Logger.log(Logger.INFO, CLASS_NAME,"Problems with connect to the URL over SSL.");
			return file;
		}
		}
		else
		{
		try {
			is = ourSock.getInputStream();
			os = ourSock.getOutputStream();	
		} catch (IOException e) {
			Logger.log(Logger.INFO, CLASS_NAME,"Problems with Input or Output Stream.");
            return file;
		}
		}
					
		Scanner s = new Scanner(new InputStreamReader(is));
		
		String sendString = "GET " + path + " HTTP/1.1 \r\n" +    // Connect to URL
		                    "Host: " + host + "\r\n" +            // From this Host
				            "Connection: close" +                 // Close connection after Respond
		                    "\r\n" + "\r\n";                      // End of Request
		
		Logger.log(Logger.INFO, CLASS_NAME, sendString);
		try {
			os.write(sendString.getBytes());
		} catch (IOException e) {
			Logger.log(Logger.INFO, CLASS_NAME,"Problems with sending the Request.");
			return file;
		}
		
		Logger.log(Logger.INFO, CLASS_NAME,"Processing Respond");
		

		while (s.hasNextLine()) {
			String l = s.nextLine();
            file.add(l);
 			Logger.log(Logger.INFO, CLASS_NAME,l);

			// Closing the Stream with this Tag is easier as waiting.
			if(l.startsWith("<CLOSE_STREAM>")){break;}
		}
		s.close();
		
		Logger.log(Logger.INFO, CLASS_NAME,"Processing Done");
		
		try {
			os.close();
			is.close();
			ourSock.close();
		} catch (IOException e) {}
		
return file;
}
	
	
	
}
