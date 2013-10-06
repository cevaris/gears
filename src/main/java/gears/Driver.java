package gears;

import java.io.File;
import java.io.FileInputStream;

import org.yaml.snakeyaml.Yaml;


public class Driver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println(String.format("Gots it working, %s",System.getProperty("java.version")));
		
		
		Yaml yaml = new Yaml();
		try{
			Object output = yaml.load(new FileInputStream(new File(Constant.CONFIG_PATH)));
			System.out.println(output);
		} catch (Exception e){
			System.err.println(e);
			
		}
		
		
		
	}

}
	