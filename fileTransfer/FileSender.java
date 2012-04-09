package fileTransfer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import alpha.Buddy;
import alpha.Logger;
import alpha.ThreadManager;
import alpha.language;

import util.Util;

<<<<<<< HEAD
=======

>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
public class FileSender implements Runnable, IFileTransfer {

	private Buddy buddy;
	private String fileName;
	private long fileSize = 0;
	private int blockSize = 8192;
<<<<<<< HEAD
	private int blocksWait = 16 * 2;
=======
	private int blocksWait = 16*2;
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
	private long startOk = -1;
	private long positionOk = -1;
	private long restartAt = 0;
	private boolean restartFlag = false;
	private boolean completed = false;
	private int timeoutCount = 0;
	private String id = generateId(); // this.id = str(random.getrandbits(32))

	private boolean running;
	private File file;
	private FileChannel fileHandle;
	private GUITransfer gui;

	public FileSender(Buddy b, String fileName) {
		this.buddy = b;
		this.fileName = fileName;
		ThreadManager.registerWork(ThreadManager.NORMAL, this, "File Send Thread [" + b + ", " + fileName + "]", "fs[" + b + ", " + fileName + "]");
	}

	private String generateId() {
		String s = "";
		String a = "abcdefghijklmnopqrstuvwxyz1234567890";
		Random r = new Random();
		for (int i = 0; i < 10; i++)
			s += a.charAt(r.nextInt(a.length()));
		return s;
	}

	@Override
	public void run() {
		this.running = true;
		try {
			this.file = new File(fileName);
			fileHandle = new FileInputStream(file).getChannel();
			this.fileName = file.getName(); // file name may have contained the path beforehand
			this.gui = new GUITransfer(this, buddy, fileName, true);
			gui.setVisible(true);
			// this.file_handle = open(this.file_name, mode="rb");
			// this.file_handle.seek(0, 2); //SEEK_END
			this.fileSize = file.length(); // this.file_handle.tell();
			this.gui.update(this.fileSize, 0);
			// filename_utf8 = this.file_name_short.encode("utf-8"); FIXME ?
			FileTransfer.senders.put(buddy.getAddress() + " " + this.id, this);

			// if not this.buddy.isFullyConnected():
			if (buddy.isFullyConnected()) {

<<<<<<< HEAD
				// Logger.oldOut.println("(2) file transfer waiting for connection");
=======
				//Logger.oldOut.println("(2) file transfer waiting for connection");
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
				this.gui.update(this.fileSize, 0, language.langtext[70]);
			}

			// this.running will be set to false when the user hits "cancel"
			// wait for connection to start file transfer
			while (running && !buddy.isFullyConnected())
				Thread.sleep(1000); // time.sleep(1)

			// user could have aborted while waiting in the loop above
			if (running) {
<<<<<<< HEAD
				// Logger.oldOut.println("(2) sending 'filename' message");
				this.gui.update(this.fileSize, 0, language.langtext[71]);
=======
				//Logger.oldOut.println("(2) sending 'filename' message");
				 this.gui.update(this.fileSize, 0, language.langtext[71]);
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
				synchronized (buddy.TSO_LOCK) {
					OutputStream os = buddy.theirSock.getOutputStream();
					String msg = "filename " + id + " " + fileSize + " " + blockSize + " " + fileName;
					Logger.log(Logger.NOTICE, this.getClass(), "Sending " + msg + " to " + buddy + ".");
					os.write((msg + "\n").getBytes());
					os.flush();
				}
				// msg = ProtocolMsg_filename(this.buddy.theirSock, (this.id, this.fileSize, this.blockSize, fileName)); //filename_utf8
				// msg.send();
			}

			// the outer loop (of the two sender loops)
			// runs forever until completed ore canceled
			while (running && !completed) {
<<<<<<< HEAD
				// Logger.oldOut.println("(2) FileSender now at start of retry loop");
=======
				//Logger.oldOut.println("(2) FileSender now at start of retry loop");
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
				this.restartFlag = false;

				// (re)start the inner loop
				this.sendBlocks(this.restartAt);

				// wait for *last* filedata_ok or restart flag
				while (running && !completed && !restartFlag) {
					Thread.sleep(100); // time.sleep(0.1)
					this.testTimeout(); // this can trigger the restart flag
				}
			}

<<<<<<< HEAD
			// if (running)
			// Logger.oldOut.println("(2) FileSender, retry loop ended because of success");
			// else
			// Logger.oldOut.println("(2) FileSender, retry loop ended because of cancel");
=======
			//if (running)
				//Logger.oldOut.println("(2) FileSender, retry loop ended because of success");
			//else
				//Logger.oldOut.println("(2) FileSender, retry loop ended because of cancel");
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95

			this.running = false;
			this.fileHandle.close();
		} catch (Exception e) {
			e.printStackTrace();
			// // haven't seen this happening yet
			// this.gui(this.file_size,
			// -1,
			// "error")
			// this.close()
			// tb()
		}
	}

	private static String getDigestFor(byte[] bs) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return asHex(md.digest(bs));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static final char[] HEX_CHARS = "0123456789abcdef".toCharArray();

	public static String asHex(byte[] buf) {
		char[] chars = new char[2 * buf.length];
		for (int i = 0; i < buf.length; ++i) {
			chars[2 * i] = HEX_CHARS[(buf[i] & 0xF0) >>> 4];
			chars[2 * i + 1] = HEX_CHARS[buf[i] & 0x0F];
		}
		return new String(chars);
	}

	public void sendBlocks(long first) {
		int blocks = (int) (this.fileSize / this.blockSize) + 1;

<<<<<<< HEAD
		// Logger.oldOut.println("(2) FileSender now entering inner loop, starting at block //" + first + ", last block in file //" + (blocks - 1));
=======
		//Logger.oldOut.println("(2) FileSender now entering inner loop, starting at block //" + first + ", last block in file //" + (blocks - 1));
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
		// the inner loop (of the two loops
		long start;
		long remaining;
		int size;
		// byte[] data;
		String hash;
		int i;
		try {
			for (i = 0; i < blocks; i++) {
				start = i * this.blockSize;
				// jump over already sent blocks
				if (start >= first) {
					remaining = this.fileSize - start;
					if (remaining > this.blockSize)
						size = this.blockSize;
					else
						size = (int) remaining;
<<<<<<< HEAD
					// Logger.oldOut.println("Block size: " + size);
=======
					//Logger.oldOut.println("Block size: " + size);
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
					ByteBuffer data = ByteBuffer.allocate(size);
					this.fileHandle.position(start);
					data.limit(size); // not sure if nessesary
					this.fileHandle.read(data);
					// data = this.file_handle.read(size);
					hash = getDigestFor(data.array());
					data = ByteBuffer.wrap(Util.escape(data.array()));

					// we can only send data if we are connected
					// while not this.buddy.isFullyConnected() and not this.restart_flag:
					while (!buddy.isFullyConnected()) {
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							// ignored
						}
						this.testTimeout();
					}

					// the message is sent over conn_in
					synchronized (buddy.TSO_LOCK) {
						OutputStream os = buddy.theirSock.getOutputStream();
						String msg = "filedata " + id + " " + start + " " + hash + " "; // + new String(data.array());
						Logger.log(Logger.NOTICE, this.getClass(), "Sending " + msg + " to " + buddy + ".");
						os.write(msg.getBytes());
						os.write(data.array());
						os.write("\n".getBytes());
						os.flush();
					}

					// wait for confirmations more than blocks_wait behind
					while (!canGoOn(start)) {
<<<<<<< HEAD
						// Logger.oldOut.println("bw");
=======
						//Logger.oldOut.println("bw");
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							// ignored
						}
						this.testTimeout(); // this can trigger the restart flag
					}

					if (restartFlag) {
						// the outer loop in run() will start us again
<<<<<<< HEAD
						// Logger.oldOut.println("(2) FileSender restart_flag, breaking innner loop");
=======
						//Logger.oldOut.println("(2) FileSender restart_flag, breaking innner loop");
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
						break;
					}

					if (!running) {
						// the outer loop in run() will also end
<<<<<<< HEAD
						// Logger.oldOut.println("(2) FileSender not running, breaking innner loop");
=======
						//Logger.oldOut.println("(2) FileSender not running, breaking innner loop");
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
						break;
					}
				}
			}
<<<<<<< HEAD
			// Logger.oldOut.println("(2) FileSender inner loop ended, last sent block: //" + i + ", last block in file //" + (blocks - 1));
=======
			//Logger.oldOut.println("(2) FileSender inner loop ended, last sent block: //" + i + ", last block in file //" + (blocks - 1));
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
		} catch (IOException ioe) {
			Logger.log(Logger.WARNING, this.getClass(), ioe.getLocalizedMessage());
		}

	}

	private boolean canGoOn(long start) {
		positionOk = this.startOk + this.blocksWait * this.blockSize;
		if (!this.running || this.restartFlag)
			return true;
		else
			return positionOk > start;
	}

	public void testTimeout() {
		// is waiting for confirmation messages. Either in the
		// sendBlocks() loop or when all blocks are sent in the
		// outer loop in run()
		// if a timeout is detected then the restart flag will be set
		if (buddy.isFullyConnected())
			// we only increase timeout if we are connected
			// otherwise other mechanisms are responsible and trying
			// to get us connected again and we just wait
			this.timeoutCount += 1;
		else
			this.timeoutCount = 0;

		if (this.timeoutCount == 6000) {
			// ten minutes without filedata_ok
			long new_start = this.startOk + this.blockSize;
			this.restart(new_start);
			// enforce a new connection
			try {
				buddy.disconnect();
			} catch (Exception e) {
				// ignored
			}
<<<<<<< HEAD
			// Logger.oldOut.println("(2) timeout file sender restart at " + new_start);
=======
			//Logger.oldOut.println("(2) timeout file sender restart at " + new_start);
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
		}
	}

	public void recievedOk(long start) {
		this.timeoutCount = 0; // we have received a sign of life
		long end = start + this.blockSize;
		if (end > this.fileSize)
			end = this.fileSize;

		try {
			this.gui.update(this.fileSize, end);
		} catch (Exception e) {
			// cannot update gui
			e.printStackTrace();
			this.close();
		}

		this.startOk = start;

		if (end == this.fileSize) {
			// the outer sender loop can now stop waiting for timeout
			this.gui.update(this.fileSize, end, language.langtext[72]);
			this.completed = true;
<<<<<<< HEAD

		}
	}

=======
			
		}
	}

	
	
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
	public void sendStopMessage() {
		try {
			buddy.sendRaw("file_stop_receiving " + this.id);
		} catch (IOException e) {
			try {
				buddy.disconnect();
			} catch (IOException e1) {
				// ignored
			}
			e.printStackTrace();
		}
	}

	@Override
	public void close() {
		if (this.running) {
			this.running = false;
			this.sendStopMessage();
			try {
				this.gui.update(this.fileSize, -1, "transfer aborted");
			} catch (Exception e) {
				// ignored
			}
		}
		// remove refference
		FileTransfer.senders.remove(buddy.getAddress() + " " + this.id);
		// del this.buddy.bl.file_sender[this.buddy.address, this.id]
	}
<<<<<<< HEAD

	// Functions must not needed here but must set.
	@Override
	public void startstop() {
	}

	@Override
	public void delete() {
	}

	@Override
	public void open() {
	}

	@Override
	public void opendir() {
	}

=======
	
// Functions must not needed here but must set.
	@Override
	public void startstop() {}
	@Override
	public void delete(){}
	@Override
	public void open(){}
	@Override
	public void opendir(){}
	
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
	public void restart(long start) {
		// trigger the reatart flag
		this.timeoutCount = 0;
		this.restartAt = start;
		this.restartFlag = true;
		// the inner loop will now immediately break and
		// the outer loop will start it again at position restart_at
	}
}
