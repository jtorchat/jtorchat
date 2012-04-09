package util;

import java.io.FileOutputStream;
import java.io.IOException;

<<<<<<< HEAD
public class ConfigWriter {
	private FileOutputStream fos;

	public ConfigWriter(FileOutputStream fos) {
		this.fos = fos;
	}

=======

public class ConfigWriter {
	private FileOutputStream fos;
	
	public ConfigWriter(FileOutputStream fos) {
		this.fos = fos;
	}
	
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
	public void write(String h) throws IOException {
		fos.write((h + "\n").getBytes());
		fos.flush();
	}
<<<<<<< HEAD

=======
	
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
	public void close() throws IOException {
		fos.close();
	}

}
