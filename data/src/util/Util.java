package util;



import java.io.ByteArrayOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Random;

public class Util {
	public static Random random = new Random();
	
    public static int myRandom(int low, int high) {  
        return (int) (Math.random() * (high - low) + low);  
    }  

	public static byte[] escape(byte[] b1) {
		for (int i = 0; i < b1.length; i++) {
			if (b1[i] == '\\') {
				b1[i] = '\\';
				byte[] t = Arrays.copyOf(b1, b1.length + 1);
				for (int ix = b1.length; ix > i + 1; ix--)
					t[ix] = t[ix - 1];
				t[i + 1] = '/';
				b1 = t;
			} else if (b1[i] == '\n') {
				b1[i] = '\\';
				byte[] t = Arrays.copyOf(b1, b1.length + 1);
				for (int ix = b1.length; ix > i + 1; ix--)
					t[ix] = t[ix - 1];
				t[i + 1] = 'n';
				b1 = t;
			}
		}
		return b1;
	}

	public static String readStringTillChar(InputStream is, char ch) throws IOException {
		String s = "";
		char c;
		while ((c = (char) is.read()) != -1 && c != ch)
			s += c;
		return s;
	}

	public static byte[] readBytesTillChar(InputStream is, char ch) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int b;
		while ((b = is.read()) != -1 && (char) b != ch)
			baos.write(b);
		return baos.toByteArray();
	}

	public static byte[] unescape(byte[] b1) {
		for (int i = 0; i < b1.length; i++) {
			if (b1[i] == '\\' && b1[i + 1] == 'n') {
				b1[i] = '\n';
				byte[] t = Arrays.copyOf(b1, b1.length - 1);
				for (int ix = i + 1; ix < t.length; ix++)
					t[ix] = b1[ix + 1];
				b1 = t;
			} else if (b1[i] == '\\' && b1[i + 1] == '/') {
				b1[i] = '\\';
				byte[] t = Arrays.copyOf(b1, b1.length - 1);
				for (int ix = i + 1; ix < t.length; ix++)
					t[ix] = b1[ix + 1];
				b1 = t;
			}
		}
		return b1;
	}

	public static String asString(byte[] a) {
		String ret = "[";
		for (byte b : a)
			if (ret.length() == 1)
				ret += (char) b;
			else
				ret += ", " + (char) b;
		return ret + "]";
	}

	public static Object staticMethodInvoke(String clazz, String method, Object... ob) throws ClassNotFoundException, IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Class<?>[] cz = null;
		if (ob != null) {
			cz = new Class<?>[ob.length];
			for (int i = 0; i < ob.length; i++)
				cz[i] = ob[i].getClass();
		}
		return Class.forName(clazz).getDeclaredMethod(method, cz).invoke(null, ob);
	}
	
	 public static String reverse( String str )
	  {
	    String umgekehrt = new String();

	    for ( int j = str.length()-1; j >= 0; j-- )
	      umgekehrt += str.charAt(j);

	    return umgekehrt;
	  }

}
