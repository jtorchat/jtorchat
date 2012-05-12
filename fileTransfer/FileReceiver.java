package fileTransfer;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import alpha.Buddy;
import alpha.Config;
import alpha.Logger;
import alpha.language;

// Pretty much direct translation from the python torchat source. 
// Original comments mainly preserved with occasional original 
// code commented next to the translated code

public class FileReceiver implements IFileTransfer {

	public long fileSize;
	private Buddy buddy;
	private String id;
	private boolean closed;
	public int blockSize;
	private String fileName;
	public String fileNameSave;
	private long nextStart;
	private int wrongBlockNumberCount;
	private String fileNameTmp;
	private File fileTmp;
	private FileChannel fileHandleTmp;
	private FileChannel fileHandleSave;
	private GUITransfer gui;
	private int starter;
	private boolean createfile;
	private File fileopen;

	// ths will be instantiated automatically on an incoming file transfer.
	// it will then notify the GUI which will open a window and give us a callback to interact
	public FileReceiver(Buddy b, String id, int blockSize, long fileSize, String fileName) {
		this.buddy = b;
		this.id = id;
		this.closed = false;
		this.blockSize = blockSize;
		this.fileName = System.currentTimeMillis() / 1000 + "-" + fileName;
		this.starter = 0;
		this.createfile = false;
		this.fileNameSave = "";
		this.fileNameTmp = Config.DOWNLOAD_DIR + this.fileName;

		this.fileSize = fileSize;
		this.nextStart = 0;
		this.wrongBlockNumberCount = 0;

		if (this.fileSize == 0) {
			Logger.log(Logger.WARNING, this.getClass(), "The file size is 0");
		} else {
			FileTransfer.getReceivers().put(buddy.getAddress() + " " + this.id, this);
			this.gui = new GUITransfer(this, buddy, fileName, false);
			gui.setVisible(true);
			this.gui.update(this.fileSize, 0);

			if (Config.transferonstart == 1) {
				this.gui.startfirst();
			} else {
				Logger.log(Logger.WARNING, this.getClass(), "answer with error until you press start");
			}
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

	public void data(long start, String hash, byte[] data) {
		if (this.closed) {
			// ignore still incoming data blocks
			// for already aborted transfers
			Logger.log(Logger.WARNING, this.getClass(), "ignoring incoming file data block for canceled receiver");
			return;
		}

		if (this.starter == 0) {

			// this.buddy.sendRaw("filedata_error " + this.id + " " + this.nextStart);
			// Logger.log(Logger.WARNING, this.getClass(), "answer with error until you press start");

			if (!createfile) {
				this.gui.update(this.fileSize, 0);
			}

			return;
		}

		try {
			if (start > this.nextStart) {
				if (this.wrongBlockNumberCount == 0) {
					// not on every single out-of-order block in a row
					// we must send an error message...
					this.buddy.sendRaw("filedata_error " + this.id + " " + this.nextStart);
					this.wrongBlockNumberCount += 1;
					// ...only every 16
					// FIXME: This must be solved more elegantly
					if (this.wrongBlockNumberCount == 16)
						this.wrongBlockNumberCount = 0;
				}
				return;
			}
			// Logger.oldOut.println(data);
			this.wrongBlockNumberCount = 0;
			String hash2 = getDigestFor(data);
			if (hash.equals(hash2)) {
				this.fileHandleTmp.position(start);
				this.fileHandleTmp.write(ByteBuffer.wrap(data));
				// Logger.oldOut.println("written: " + written);
				this.nextStart = start + data.length;
				// Logger.oldOut.println("Got data " + data);
				this.buddy.sendRaw("filedata_ok " + this.id + " " + start);
				// Logger.oldOut.println("sending filedata_ok " + this.id + " " + start);
				this.gui.update(this.fileSize, start + data.length);
				// Logger.oldOut.println("sending filedata_ok " + this.fileSize + " " + (start + data.length));
				// TODO GUI - with == if (this.fileSize == (start + data.length())) {
				// Logger.oldOut.println("close");
				// close(); // completed;
				// }
			} else {
				Logger.log(Logger.WARNING, this.getClass(), "(3) receiver wrong hash " + start + " len: " + data.length);
				// Logger.oldOut.println("(3) receiver wrong hash " + start + " len: " + data.length + " | " + hash + ", " + hash2);
				this.buddy.sendRaw("filedata_error " + this.id + " " + start);
				// we try to avoid unnecessary wrong-block-number errors
				// the next block sure will be out of order, but we have sent
				// an error already because of the wrong hash
				this.wrongBlockNumberCount = 1;
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
			try {
				buddy.disconnect();
			} catch (IOException e) {
				// ignored
			}
		}
	}

	public void setFileNameSave(String fileNameSave) {
		this.fileNameSave = fileNameSave;
		try {
			this.fileHandleSave = new FileOutputStream(fileNameSave).getChannel();
			Logger.log(Logger.INFO, this.getClass(), "(2) created and opened placeholder file " + this.fileNameSave);
		} catch (IOException ioe) {
			this.fileHandleSave = null;
			this.fileNameSave = null;
			Logger.log(Logger.NOTICE, this.getClass(), "(2) " + fileNameSave + " could not be created: " + ioe.getLocalizedMessage());
			try {
				this.fileHandleSave.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void sendStopMessage() {
		try {
			this.buddy.sendRaw("file_stop_sending " + this.id);
		} catch (IOException ioe) {
			ioe.printStackTrace();
			try {
				buddy.disconnect();
			} catch (IOException e) {
				// ignored
			}
		}
	}

	@Override
	public void delete() {
		Logger.log(Logger.INFO, this.getClass(), "deleting file " + this.fileNameTmp);
		this.closethis();
		this.fileTmp.delete();
		this.closeSave();

	}

	@Override
	public void open() {
		this.fileopen = new File(this.fileNameTmp);
		try {
			Desktop.getDesktop().open(this.fileopen);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void opendir() {
		this.fileopen = new File(Config.DOWNLOAD_DIR);
		try {
			Desktop.getDesktop().open(this.fileopen);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void startstop() {
		if (this.starter == 0) {
			if (!createfile) {
				this.fileTmp = new File(fileNameTmp);
				this.fileTmp.getParentFile().mkdirs();
				try {
					this.fileHandleTmp = new FileOutputStream(fileTmp).getChannel();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				System.out.println("(2) FileReceiver: created file: " + this.fileNameTmp);
				System.out.println("(2) FileReceiver: init done for file " + fileName);
				try {
					this.buddy.sendRaw("filedata_error " + this.id + " " + 0);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				createfile = true;
			}

			this.starter = 1;

		} else {
			this.starter = 0;
		}

	}

	@Override
	public void close() {
		closethis();
		closeSave();
	}

	public void closethis() { // in pytorchat the equivelant is closeForced

		if (!createfile) {
			this.fileName = "thisisaworkaroundforabadbug";
			this.fileNameTmp = Config.DOWNLOAD_DIR + this.fileName;
			this.fileTmp = new File(fileNameTmp);
			this.fileTmp.getParentFile().mkdirs();
			try {
				this.fileHandleTmp = new FileOutputStream(this.fileTmp).getChannel();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		this.gui.update(this.fileSize, -1, language.langtext[69]);
		this.sendStopMessage();
		if (this.fileNameSave != null && this.fileNameSave.length() != 0) {
			try {
				this.fileHandleSave.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Logger.log(Logger.INFO, this.getClass(), "deleting empty placeholder file " + this.fileNameSave);
			new File(fileNameSave).delete();
			this.fileNameSave = "";
		}
		try {
			this.fileHandleTmp.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (!createfile) {
			Logger.log(Logger.INFO, this.getClass(), "deleting bugfix file " + this.fileNameTmp);
			this.fileTmp.delete();
		} else {
			if (this.nextStart < this.fileSize) {
				this.fileTmp.delete();
				Logger.log(Logger.INFO, this.getClass(), "deleting file " + this.fileNameTmp);
			}
		}

	}

	public void closeSave() { // in pytorchat the equivelant is close
		// this is called from the GUI (or its replacement)
		// therefore this FileReceiver object cannot work without
		// a GUI attached to it (or some other piece of code) that
		// properly provides and reacts to the callback function
		// and closes this obect after it is done
		// (user clicked save or whatever this GUI or GUI-replacement does)

		if (this.closed)
			return;
		try {
			this.closed = true;
			this.fileHandleTmp.close();
			// if (this.fileNameSave != null && this.fileNameSave.length() != 0) {
			// new File(this.fileNameTmp).renameTo(new File(this.fileNameSave));
			// Logger.oldOut.println("Renamed " + this.fileNameTmp + " to " + this.fileNameSave);
			// }

			FileTransfer.getReceivers().remove(buddy.getAddress() + " " + this.id);

		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

}
