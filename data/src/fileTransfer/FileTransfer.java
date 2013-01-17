package fileTransfer;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import core.Buddy;
import core.Logger;


import util.Util;


public class FileTransfer {

	static HashMap<String, FileSender> senders = new HashMap<String, FileSender>();
	static HashMap<String, FileReceiver> receivers = new HashMap<String, FileReceiver>();

		private static final String COMMAND_SEPARATOR = " ";
		
		
		public static void in_filename(Buddy buddy, String command, InputStream is) {
			
             	String[] spl;
				try {
					spl = Util.readStringTillChar(is, '\n').split(COMMAND_SEPARATOR, 4);
				} catch (IOException e) {				
                 e.printStackTrace();
                return;	
				}
				
				String id = spl[0];
				long fileSize = Long.valueOf(spl[1]);
				int blockSize = Integer.valueOf(spl[2]);
				// remove all / and \ to be safe
				String fileName = spl[3].replaceAll((char) 0 + "", "").replaceAll("/", "").replaceAll("\\\\", "");
				String ext = fileName.split("\\.")[fileName.split("\\.").length - 1];
				String base = fileName.substring(0, fileName.length() - ext.length());
				if (base.trim().length() == 0)
					fileName = base + fileName;
				

				Logger.log(Logger.NOTICE, "FileTransfer", "filename recieved from " + buddy + " | " + id + ", " + fileSize + ", " + blockSize + ", " + fileName + " | " + base + ", " + ext);
				new FileReceiver(buddy, id, blockSize, fileSize, fileName);
				
		         }
				
			
			
			
		public static void in_filedata(Buddy buddy, String command, InputStream is) {
			String id;
			long start;
			String hash;
			byte[] data;
				try {
				id = Util.readStringTillChar(is, ' ');
			    start = Long.valueOf(Util.readStringTillChar(is, ' '));
			    hash = Util.readStringTillChar(is, ' ');
				data = Util.unescape(Util.readBytesTillChar(is, '\n'));

				
				} catch (IOException e1) {					
                e1.printStackTrace();
                return;
				}
				
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




		public static void in_filedata_ok(Buddy buddy, String s) {


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
				
		}
		public static void in_filedata_error(Buddy buddy, String s) {
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
				} 
			 
			 
			 
		public static void in_file_stop_sending(Buddy buddy, String s) {
			 
					String id = s.split(COMMAND_SEPARATOR, 2)[1]; // NOTE - in pytorchat this is done by self.id = self.blob doesnt make sense
					FileSender sender = senders.get(buddy.getAddress() + COMMAND_SEPARATOR + id);
					if (sender != null)
						sender.close();
					
			 }
					
					
		public static void in_file_stop_receiving(Buddy buddy, String s) {
	
					
					String id = s.split(COMMAND_SEPARATOR, 2)[1]; // NOTE - in pytorchat this is done by self.id = self.blob doesnt make sense
					FileReceiver receiver = receivers.get(buddy.getAddress() + COMMAND_SEPARATOR + id);
					if (receiver != null)
						receiver.close();
				
	      }

	public static HashMap<String, FileSender> getSenders() {
		return senders;
	}

	public static HashMap<String, FileReceiver> getReceivers() {
		return receivers;
	}

}
