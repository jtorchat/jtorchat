package core;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import util.ConfigWriter;





public class language {
	
static String language;
static String native_language;
public static String langtext[] = new String[78];


public static String loadlang()
{

Properties prop = new Properties();
try {
	prop.load(new InputStreamReader(new FileInputStream(Config.LANG_DIR + Config.lang + ".ini"), "UTF-16"));
} catch (FileNotFoundException e) {

	prop = new Properties();
	try {
		prop.load(new InputStreamReader(new FileInputStream(Config.LANG_DIR + Config.dlang + ".ini"), "UTF-16"));
	} catch (FileNotFoundException e1) {
return "I can not load a language file, please check it!";	
	} catch (IOException e1) {}
} catch (IOException e) {}

if (ConfigWriter.assign("version", null, prop).equals("0.1"))
{
language = ConfigWriter.assign("language", null, prop);
native_language = ConfigWriter.assign("native_language", null, prop);
int i = 0;

while(i<41)
{
String count = Integer.toString(i);
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

langtext[i] = ConfigWriter.assign("lang"+count, "lang"+count, prop);
i++;
} 



try {
	prop.load(new InputStreamReader(new FileInputStream(Config.LANG_DIR + Config.dlang + ".ini"), "UTF-16"));
} catch (FileNotFoundException e) {

return "The Language file is not the newest and i can not load the second file "+Config.dlang + ".ini !";	

} catch (IOException e) {}

language = ConfigWriter.assign("language", null, prop);
native_language = ConfigWriter.assign("native_language", null, prop);

if (ConfigWriter.assign("version", null, prop).equals("0.2"))
{
i = 41;
while(i<78)
{
String count = Integer.toString(i);
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

langtext[i] = ConfigWriter.assign("lang"+count, "lang"+count, prop);
i++;
} 
}
else
{
return "The second language is not new enough!";	
}
}
else if (ConfigWriter.assign("version", null, prop).equals("0.2"))
{
	language = ConfigWriter.assign("language", null, prop);
	native_language = ConfigWriter.assign("native_language", null, prop);
	int i = 0;

	while(i<78)
	{
	String count = Integer.toString(i);
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

	langtext[i] = ConfigWriter.assign("lang"+count, "lang"+count, prop);
	i++;
	} 	
}
	
return null;
}
	
	
public static String[] getinfo(String out)
{
	String[] info = new String[3];
	String info_lang;
	String info_n_lang;
	String version;
	
	
Properties prop = new Properties();
try {
	prop.load(new InputStreamReader(new FileInputStream(Config.LANG_DIR + out + ".ini"), "UTF-16"));
} catch (FileNotFoundException e) {
info[0] = "Sorry but this language not exist!";
info[1] = "empty";
info[2] = "empty";
return info;
} catch (IOException e) {}


info_lang = ConfigWriter.assign("language", null, prop);
info_n_lang = ConfigWriter.assign("native_language", null, prop);
version = ConfigWriter.assign("version", null, prop);


info[0] = "The file has the language is " + info_lang;
info[1] = "In the language it means " + info_n_lang;
info[2] = "Version of the language file " + version;

return info;
}


}


