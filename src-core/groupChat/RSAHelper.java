package groupChat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;

public class RSAHelper {

	private static int keySize = 128;

	public static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		kpg.initialize(keySize);
		KeyPair kp = kpg.generateKeyPair();
		System.out.println(kp.getPrivate());
		System.out.println(kp.getPublic());
		return kp;
	}

	public void updateFlushEveryRead(InputStream is, OutputStream os, int bufSize, Key key, int mode) {
		try {
			Cipher cipher = Cipher.getInstance("RSA", "SunJCE");
			cipher.init(mode, key);
			byte[] buf = new byte[bufSize];
			int bytesRead = 0;
			while ((bytesRead = is.read(buf)) > 0) {
				os.write(cipher.doFinal(buf, 0, bytesRead));
			}
			os.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (GeneralSecurityException e) {
			throw new RuntimeException(e);
		}
	}

	public int getKeySize() {
		return keySize;
	}
}
