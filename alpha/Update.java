package alpha;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.net.URL;
import java.util.Scanner;

public class Update {
	

	public static String its(int i) {
	    return Integer.toString(i);
	}

	public static String loadUpdate(String remote_bl_URL) {
		// Don't load if no url was specified
		if ((remote_bl_URL == null)||(remote_bl_URL.length() == 0)) {
			Logger.log(Logger.INFO, "loadUpdate",
					"No remote buddylist specified. Skipping remote load.");
			Logger.log(Logger.INFO, "loadUpdate",
					"Tip: update = example.com/update.txt ; in settings.ini");
			return "false";
		} else {
			Logger.log(Logger.INFO, "loadUpdate",
					"Loading update from url... ");
		}

		/*
		 * SOCKET RETREIVE REMOTE FILE VIA PROXY TO SCANNER OBJECT
		 */
		Logger.log(Logger.INFO, "loadUpdate",
				"UPDATEURL LOCATION: " + remote_bl_URL);

		try {
			//assume https:// if lacking protocol
			if(!remote_bl_URL.matches(".+://.+")){
				remote_bl_URL = "https://"+remote_bl_URL;
			}

			// Parse the URL for socket usage via URL. Can't use URL directly
			// due to DNS leaks
			URL aURL = new URL(remote_bl_URL);
			// Placed parsed URL into vars for general usage
			// http://example.com:80/docs/books/tutorial/index.html?name=networking#DOWNLOADING
			String host = aURL.getHost(); // host = example.com
			int port = aURL.getPort(); // port = 80
			String path = aURL.getPath(); // path = /docs/books/tutorial/index.html
			// Sometimes portnumber is not declared (shows as port = -1), assume
			// port 80 in that case
			if (port < 0) {
				port = 80;
			}

			// Declare a new proxyed socket and connect to it (No DNS leak via
			// createUnresolved)
			Logger.log(Logger.INFO, "loadUpdate",
					"Configering secure proxy tunnel to updatefile; Port: "
							+ Config.SOCKS_PORT + " host: 127.0.0.1");
            Socket ourSock = new Socket(new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("127.0.0.1", Config.SOCKS_PORT)));
			Logger.log(Logger.INFO, "loadUpdate",
					"Starting secure remote proxy tunnel to updatefile");
			ourSock.connect(InetSocketAddress.createUnresolved(host, port));

			// INPUT/OUTPUT STREAM
			Logger.log(Logger.INFO, "loadUpdate",
					"opening in/out stream");
			InputStream is = ourSock.getInputStream();
			OutputStream os = ourSock.getOutputStream();

			// read incoming data
			Logger.log(Logger.INFO, "loadUpdate",
					"start inputstream scanner");
			Scanner s = new Scanner(new InputStreamReader(is)); // create
																// scanner obj

			// Send Request Header to output stream
			String sendString = "GET " + path + " HTTP/1.0 \r\n" + "Host: "
					+ host + "\r\n" + "\r\n";
			Logger.log(Logger.INFO, "loadUpdate", sendString);
			os.write(sendString.getBytes());

			/*
			 * PROCESS FILE AND MERGE BUDDY
			 */
			Logger.log(Logger.INFO, "loadUpdate",
					"Processing updatefile");

			String outversion = null;


            String[] b;
			while (s.hasNextLine()) {
				String l = s.nextLine();
			
				if(l.startsWith("<version!"))
				{
				b=l.split("!");
				if (b.length == 5)
				{

				outversion = b[1] +"."+ b[2] +"."+ b[3];    	


				}}
					
				}
			Logger.log(Logger.INFO, "loadUpdate",
					"Processing Done");
			// Close input/output stream
			os.close();
			is.close();
				
			// Close socket
			ourSock.close();

			if(outversion != null)
			{
			Logger.log(Logger.INFO, "loadUpdate","compare: " + Config.BUILD +" with "+ outversion);
			
			if(isOutdated(Config.BUILD, outversion))

			{
				return language.langtext[52];
	
			}else
			{
				
				if(Config.allcheckupdate == 1)
				{
				Config.allcheckupdate = 0;
				return language.langtext[53];
				}
				else
				{
				return"close"; // not open a window at every start
				}
				
				
			}
			}else{return language.langtext[54];}
		} catch (IOException e1) {
			Logger.log(Logger.INFO, "loadUpdate",
					"ERROR: Cannot get file for update - Skipping: " + e1);
			return"Error by loading the file";
		}
		

	}


	private static boolean isOutdated(String buildVersion, String outVersion) {
		boolean isOutdated = false;
		String[] buildVersionComponents = buildVersion.split("[.]");
		String[] outVersionComponents = outVersion.split("[.]");

		int buildVersionIndex = 0;
		int outVersionIndex = 0;


		while (buildVersionIndex < buildVersionComponents.length || outVersionIndex < outVersionComponents.length) {
			final int currBuildVersionComponent = (buildVersionIndex < buildVersionComponents.length) ? 
				Integer.parseInt(buildVersionComponents[buildVersionIndex]) : 0;

			final int outBuildVersionComponent = (outVersionIndex < outVersionComponents.length) ?
				Integer.parseInt(outVersionComponents[outVersionIndex]) : 0;
			
			if (currBuildVersionComponent < outBuildVersionComponent) {
				isOutdated = true;
				break;
			} else if (currBuildVersionComponent > outBuildVersionComponent) {
				// result is false
				break;
			}
			
			buildVersionIndex++;
			outVersionIndex++;
		}
		

		return isOutdated;
	}
}

