package core;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import util.ConfigWriter;



public class language {
	
public static int until_max = 87;
static String language;
static String native_language;
public static String langtext[] = new String[until_max];

public static String loadlang()
{
Properties prop = new Properties();

prop = load_lang_prop(Config.lang);
if(prop==null){Config.lang=Config.dlang; prop = load_lang_prop(Config.LANG_DIR + Config.lang + ".ini");}
if(prop==null){return "Can not load any language file.";}

language = ConfigWriter.assign("language", null, prop);
native_language = ConfigWriter.assign("native_language", null, prop);

int until = Integer.parseInt(ConfigWriter.assign("until", "0", prop));
if (until>until_max){until=until_max;}

load_from_until(0,until,prop);

if(until<until_max){

prop = load_lang_prop(Config.dlang);
int until2 = Integer.parseInt(ConfigWriter.assign("until", "0", prop));

if(until2<until_max){return "The second Language file is not new only " + until2;}
load_from_until(until,until_max,prop);
}

prop.clear();
return null;
}

public static Properties load_lang_prop(String file)
{
	Properties prop = new Properties();

		try {
			prop.load(new InputStreamReader(new FileInputStream(Config.LANG_DIR + file + ".ini"), "UTF-16"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	return prop;
}

	
public static void load_from_until(int min, int max, Properties prop)
{
	while(min<max)
	{
	String count = Integer.toString(min);
	if (count.length() == 1)
	{
	count = "00000"+count;
	}
	if (count.length() == 2)
	{
	count = "0000"+count;
	}
	if (count.length() == 3)
	{
	count = "000"+count;
	}
	if (count.length() == 4)
	{
	count = "00"+count;
	}
	if (count.length() == 5)
	{
	count = "0"+count;
	}

	langtext[min] = ConfigWriter.assign("lang"+count, "lang"+count, prop);
	min++;
	}
}


public static String[] get_info_from_file(String file)
{
	String[] info = new String[3];
	String info_lang;
	String info_n_lang;
	String until;
	
	
Properties prop = new Properties();
try {
	prop.load(new InputStreamReader(new FileInputStream(Config.LANG_DIR + file + ".ini"), "UTF-16"));
} catch (FileNotFoundException e) {
info[0] = "Sorry but this language not exist!";
info[1] = "empty";
info[2] = "empty";
return info;
} catch (IOException e) {}


info_lang = ConfigWriter.assign("language", null, prop);
info_n_lang = ConfigWriter.assign("native_language", null, prop);
until = ConfigWriter.assign("until", null, prop);


info[0] = "The file has the language is " + info_lang;
info[1] = "In the language it means " + info_n_lang;
info[2] = "This language file have " + until + "/" + until_max + " Entrys.";

return info;
}

}


