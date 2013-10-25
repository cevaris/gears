package gears.base.pkmg;

import org.apache.log4j.Logger;

public class DebianInstaller implements Installer {
	
	private static final Logger LOG = Logger.getLogger(DebianInstaller.class.getClass());
	
	public String install(String flags, String service) {
		return String.format("apt-get %s install %s",flags, service);
	}

	//TODO: Installer "remove" not tested
	public String remove(String flags, String services) {
		return String.format("apt-get %s --purge remove %s ",flags, services);
	}
	
	public String restart(String service) {
		return String.format("service %s restart", service);
	}
	
	public String update() {
		return "apt-get update";
	}

}
