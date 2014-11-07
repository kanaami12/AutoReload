package github.plugin.kanaami12.autoreload;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.bukkit.plugin.java.JavaPlugin;

public class Message extends JavaPlugin{

    static String[] str;

  //message1=broadcastMessage
    public static boolean message1 = false;
  //message2=opMessage
    public static boolean message2 = false;

    /**
     * create a file
     * @param file filePath
     */
	public static void onCreate(File file){
		//already exists
		if (checkBeforeReadfile(file)){
			readFile(file);
			return;
		}else{
		//create file
		try{
		    file.createNewFile();
		}catch(IOException e){
		}

		//write
		writeSettings(file);
		}
	}

	/**
	 * write Setting file
	 * @param file
	 */
	public static void writeSettings(File file){
		try{
	          FileWriter filewriter = new FileWriter(file);

	          final String BR = System.getProperty("line.separator");
	          String s = "#true or false" + BR
	        		   + "broadcast message：" + BR
	        		   + "false" + BR
	        		   + "op message：" + BR
	        		   + "false" ;
	          filewriter.write(s);
	          filewriter.close();

	          readFile(file);
		}catch(IOException e){
	    }

	}

	/**
	 * reading file
	 */
	public static void readFile(File file){
    try{
          BufferedReader br = new BufferedReader(new FileReader(file));

          //str[i]
          int i = 0;
          String str2;
          while((str2 = br.readLine()) != null){
        	  if(i == 2 || i == 4)
        	  truefalse(str2, i);
        	  i++;
          }
  		if(i < 5){
			file.delete();
			writeSettings(file);
		}

          br.close();
      }catch(FileNotFoundException e){
        System.out.println(e);
      }catch(IOException e){
        System.out.println(e);
      }
	}

	/**
	 * read to settings
	 * @param str
	 * @param i
	 */
	public static void truefalse(String str, int i){
		if(i == 2){
			if(str.equalsIgnoreCase("true"))
				message1 = true;
		}
		if(i == 4){
			if(str.equalsIgnoreCase("true"))
				message2 = true;
		}

	}

	/**
	 * exists
	 * @param file
	 * @return exists file
	 */
	  private static boolean checkBeforeReadfile(File file){
		    if (file.exists()){
		    	if (file.isFile() && file.canRead()){
		    		return true;
		    	}
		    }
		    return false;
	  }
}
