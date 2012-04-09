package util;

import java.io.FileOutputStream;
import java.io.IOException;

public class ConfigWriter {
	private FileOutputStream fos;

	public ConfigWriter(FileOutputStream fos) {
		this.fos = fos;
	}

	public void write(String h) throws IOException {
		fos.write((h + "\n").getBytes());
		fos.flush();
	}

	public void close() throws IOException {
		fos.close();
	}

}
