package gears;

import java.io.File;
import java.io.FileInputStream;

import org.yaml.snakeyaml.Yaml;

import base.Server;


public class Driver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		System.out.println(String.format("Gots it working, %s",System.getProperty("java.version")));
		Server server = new Server();


	}


	
	

}
