package fileTransfer;


import gui.ChatWindow;
import gui.Gui;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import alpha.APIManager;
import alpha.Buddy;
import alpha.Logger;

import util.Util;

import listeners.CommandListener;
import listeners.IncomingCommandListener;

public class FileTransfer {

	static HashMap<String, FileSender> senders = new HashMap<String, FileSender>();
	static HashMap<String, FileReceiver> receivers = new HashMap<String, FileReceiver>();

	private static Listener lis;

	public static void init() {
		lis = new Listener();
		APIManager.incomingCmdListeners.put("filename", lis);
		APIManager.incomingCmdListeners.put("filedata", lis);
		APIManager.cmdListeners.put("filedata_ok", lis);
		APIManager.cmdListeners.put("filedata_error", lis);
		APIManager.cmdListeners.put("file_stop_sending", lis);
		APIManager.cmdListeners.put("file_stop_receiving", lis);
	}

	private static class Listener implements IncomingCommandListener, CommandListener {

		private static final String COMMAND_SEPARATOR = " ";

		// Util.readTillLinebreak(is)
		@Override
		public void onCommand(Buddy buddy, String command, InputStream is) {
			

			//Logger.oldOut.println("ft: " + command);
			try {
			if (command.startsWith("filename")) {
				String[] spl = Util.readStringTillChar(is, '\n').split(COMMAND_SEPARATOR, 4);
				String id = spl[0];
				long fileSize = Long.valueOf(spl[1]);
				int blockSize = Integer.valueOf(spl[2]);
				// remove all / and \ to be safe
				String fileName = spl[3].replaceAll((char) 0 + "", "").replaceAll("/", "").replaceAll("\\\\", "");
				String ext = fileName.split("\\.")[fileName.split("\\.").length - 1];
				String base = fileName.substring(0, fileName.length() - ext.length());
				if (base.trim().length() == 0)
					fileName = base + fileName;
				//Logger.oldOut.println("filename recieved from " + buddy + " | " + id + ", " + fileSize + ", " + blockSize + ", " + fileName + " | " + base + ", " + ext);
				
				if (buddy.getHoly())
				{
				Logger.log(Logger.NOTICE, "FileTransfer", "filename recieved from " + buddy + " | " + id + ", " + fileSize + ", " + blockSize + ", " + fileName + " | " + base + ", " + ext);
				new FileReceiver(buddy, id, blockSize, fileSize, fileName);
				}
				else
				{
				
				boolean flag = buddy.isFullyConnected();
				
				if (command.startsWith("filename")) {
				Gui.getChatWindow(buddy, true, true).append("Time Stamp", "(" + ChatWindow.getTime() + ") ");
				Gui.getChatWindow(buddy, true, true).append("Them", " --> " + "Try to start File-Transfer but I'm not Holy!!!" + "\n");

				
				if (flag)
				{
					try {
						buddy.sendMessage("/pa " + "You are not on the Holy list");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}}
				}
				
			} else if (command.startsWith("filedata")) {
				String id = Util.readStringTillChar(is, ' ');
				long start = Long.valueOf(Util.readStringTillChar(is, ' '));
				String hash = Util.readStringTillChar(is, ' ');
				byte[] data = Util.unescape(Util.readBytesTillChar(is, '\n'));

				FileReceiver receiver = receivers.get(buddy.getAddress() + COMMAND_SEPARATOR + id);
				if (receiver != null) {
					//int amountExpected = (int) ((receiver.fileSize - start) > receiver.blockSize ? receiver.blockSize : receiver.fileSize - start);
					// ByteBuffer dataBuf = ByteBuffer.allocate(amountExpected);
					//Logger.oldOut.println("amountExpected: " + amountExpected);
					//int amountLeft = amountExpected - data.length;
					//Logger.oldOut.println("amountLeft: " + amountLeft + " | " + (amountLeft == 1));
					// dataBuf.limit(amountExpected);
					// dataBuf.position(data.length()); // after the end of current data
					// System.out.println(dataBuf.position() + " | " + data.length() + " | " + dataBuf.array().length);

					//Logger.oldOut.println("ft b");
					receiver.data(start, hash, data);
					//Logger.oldOut.println("ft c");
				} else
					try {
						buddy.sendRaw("file_stop_sending " + id);
					} catch (IOException e) {
						e.printStackTrace();
						try {
							buddy.disconnect();
						} catch (IOException ioe) {
							// ignored
						}
					}
			}
			} catch (IOException ioe) {
				Logger.log(Logger.NOTICE, "FileTransfer", "IOException on " + buddy.toString(true) + COMMAND_SEPARATOR + ioe.getLocalizedMessage());
				try {
					buddy.disconnect();
				} catch (IOException e) {
					// ignored
				}
			}
			//Logger.oldOut.println("end onc");
		

				

			
		}

		@Override
		public void onCommand(Buddy buddy, String s) {

			 if (s.startsWith("filedata_ok ")) {
					String[] spl = s.split(COMMAND_SEPARATOR, 3);
					String id = spl[1];
					long start = Long.valueOf(spl[2]);

					FileSender sender = senders.get(buddy.getAddress() + COMMAND_SEPARATOR + id);
					if (sender != null)
						sender.recievedOk(start);
					else
						try {
							buddy.sendRaw("file_stop_receiving " + id);
						} catch (IOException e) {
							e.printStackTrace();
							try {
								buddy.disconnect();
							} catch (IOException ioe) {
								// ignored
							}
						}
				} else if (s.startsWith("filedata_error ")) {
					String[] spl = s.split(COMMAND_SEPARATOR, 3);
					String id = spl[1];
					long start = Long.valueOf(spl[2]);

					FileSender sender = senders.get(buddy.getAddress() + COMMAND_SEPARATOR + id);
					if (sender != null)
						sender.restart(start);
					else
						try {
							buddy.sendRaw("file_stop_receiving " + id);
						} catch (IOException e) {
							e.printStackTrace();
							try {
								buddy.disconnect();
							} catch (IOException ioe) {
								// ignored
							}
						}
				} else if (s.startsWith("file_stop_sending ")) {
					String id = s.split(COMMAND_SEPARATOR, 2)[1]; // NOTE - in pytorchat this is done by self.id = self.blob doesnt make sense

					FileSender sender = senders.get(buddy.getAddress() + COMMAND_SEPARATOR + id);
					if (sender != null)
						sender.close();
				} else if (s.startsWith("file_stop_receiving ")) {
					String id = s.split(COMMAND_SEPARATOR, 2)[1]; // NOTE - in pytorchat this is done by self.id = self.blob doesnt make sense
					FileReceiver receiver = receivers.get(buddy.getAddress() + COMMAND_SEPARATOR + id);
					if (receiver != null)
						receiver.close();
				}

	}}

	public static HashMap<String, FileSender> getSenders() {
		return senders;
	}

	public static HashMap<String, FileReceiver> getReceivers() {
		return receivers;
	}

}
