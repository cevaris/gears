package gears.base.pkmg;

import gears.base.connection.Connection;

public interface Installer {
	
	
	public void setConnection(Connection connection);
	
	public boolean update();
	
	public boolean restart(String service);
	
//	public boolean execute(String commands);
	
	public boolean remove(String flags, String service);
	public boolean install(String flags, String commands);
	
}
