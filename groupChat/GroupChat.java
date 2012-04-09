package groupChat;

import java.security.Key;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;

import alpha.APIManager;
import alpha.Buddy;

import listeners.CommandListener;

public class GroupChat {

	private static HashMap<String, Group> groups = new HashMap<String, Group>();
	private static Listener lis;

	public static void init() {
		if (lis != null)
			return;
		APIManager.cmdListeners.put("groupmessage", lis = new Listener());
//		APIManager.cmdListeners.put("groupmessage", lis);
	}

	private static class Group {

		@SuppressWarnings("unused")
		private String id;
		@SuppressWarnings("unused")
		private List<String> _part;
		@SuppressWarnings("unused")
		private HashMap<String, Key> participants = new HashMap<String, Key>();
		@SuppressWarnings("unused")
		private KeyPair kp; // my key

		private Group(String id) throws NoSuchAlgorithmException {
			this.id = id;
			kp = RSAHelper.generateKeyPair();
//			Collections.sort(participants);
		}
		
	//	public void addParticipant(String address) {
			
	//	}

		public void getMessage(String sender, String content) {
			
		}

	}

	private static class Listener implements CommandListener {

		@Override
		public void onCommand(Buddy b, String s) {
			// groupmessage id sender content
			if (s.startsWith("groupmessage ")) {
				String[] spl = s.split(" ", 4);
				String id = spl[1];
				String sender = spl[2]; // not trusted
				String content = spl[3];
				groups.get(id).getMessage(sender, content);
			}
		}
	}
}
