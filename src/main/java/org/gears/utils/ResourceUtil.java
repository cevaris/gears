package org.gears.utils;

public class ResourceUtil {
	
	public static String getHomeDirectoryPath(String path){
		return String.format("%s/%s",System.getProperty("user.home"),path);
	}
	
	public static String getResourcePath(String path){
		return String.format("src/main/resources/%s",path);
	}
	
	public static String getTestResourcePath(String path){
		return String.format("src/main/resources/%s",path);
	}

}
