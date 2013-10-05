package gears;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Driver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println(String.format("Gots it working, %s",System.getProperty("java.version")));
		
		Properties prop = new Properties();
		try {
    		//set the properties value
    		prop.setProperty("database", "localhost");
    		prop.setProperty("dbuser", "mkyong");
    		prop.setProperty("dbpassword", "newPassword");
 
    		//save properties to project root folder
    		
    		prop.store(new FileOutputStream(Constant.CONFIG_PATH), null);
    		System.out.println("Stored properties");
 
    	} catch (IOException ex) {
    		ex.printStackTrace();
        }
		
		
	}

}
	