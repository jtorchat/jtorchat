package util;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Regex {
	
	private Regex() {} // No instances

	public static String getReg(String o, String regex, int i) {
		Matcher m = Pattern.compile(regex).matcher(o);
		return m.find() ? m.groupCount() > i-1 ? m.group(i) : null : null;
	}

	public static String[][] getRegAllArray(String o, String regex) {
		Matcher m = Pattern.compile(regex).matcher(o);
		ArrayList<String[]> tmpret = new ArrayList<String[]>();
		while (m.find()) {
			String[] tmp = new String[m.groupCount() + 1];
			tmpret.add(tmp);
			tmp[0] = m.group();
			for (int i = 0 ; i < m.groupCount() ; i++)
				tmp[i+1] = m.group(i+1);
			m.reset((o = o.replace(m.group(), "")));
		}
		String[][] ret = tmpret.toArray(new String[0][0]);
		return ret;
	}

	public static String[] getRegArray(String o, String regex) {
		Matcher m = Pattern.compile(regex).matcher(o);
		if (m.find()) {
			String[] tmp = new String[m.groupCount() + 1];
			tmp[0] = m.group();
			for (int i = 0 ; i < m.groupCount() ; i++)
				tmp[i+1] = m.group(i+1);
			return tmp;
		}
		return null;
	}

	public static String getReg(String o, String regex) {
		Matcher m = Pattern.compile(regex).matcher(o);
		return m.find() ? m.groupCount() > 0 ? m.group(1) : null : null;
	}
	
	public static String getRegAll(String o, String regex, String seperator) {
		Matcher m = Pattern.compile(regex).matcher(o);
		String ret = "";
		while (m.find()) {
			ret += m.group(1) + seperator;
			m.reset((o = o.replace(m.group(), "")));
		}
		if (ret.length() > seperator.length())
			ret = ret.substring(0, ret.length() - seperator.length());
		return ret.length() == 0 ? null : ret;
	}

	public static String getData(String u) throws IOException {
		Scanner s = new Scanner(new URL(u).openStream());
		String o = "";
		while (s.hasNextLine())
			o+=s.nextLine() + "\n";
		if (o.length() > 0)
			o = o.substring(0, o.length()-1);
		s.close();
		return o;
	}

	
	// Misc Methods.

	/**
	 * Note: only checks the first char.
	 * 
	 * @param string
	 * @return
	 */
	public static boolean toBoolean(String string) {
		return !string.equalsIgnoreCase("1") ? !string.equalsIgnoreCase("true") ? false : true : true;
	}
	
	public static char boolToChar(boolean b) {
		return b ? '1' : '0';
	}

	public static boolean match(String regex, String s) {
		return Pattern.compile(regex).matcher(s).matches();
	}
}
