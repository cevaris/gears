package org.gears.pkmg;

import org.gears.Service;

public interface Installer {
	
	
	public String update();
	
	public String remove(String flags, String service);
	public String install(String flags, String commands);
	public String install(String commands);

	public String openPort(String value);

	public String service(String serviceName, Service state);
	
}
