package gears.base.pkmg;

import gears.base.connection.Connection;

public interface Installer {
	
	
	public String update();
	
	public String restart(String service);
	
	public String remove(String flags, String service);
	public String install(String flags, String commands);
	
}
