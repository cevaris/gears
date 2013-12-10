package org.gears.utils;

public class ResourceUtil {
	
	public static String getResourcePath(String path){
		return String.format("src/main/resources/%s",path);
	}
	
	public static String getTestResourcePath(String path){
		return String.format("src/main/resources/%s",path);
	}

}
