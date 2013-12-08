package gears.pkmg;

import org.apache.log4j.Logger;

public class DebianInstaller implements Installer {
	
	private static final Logger LOG = Logger.getLogger(DebianInstaller.class.getClass());
	
	
	public String install(String service) {
		return install("-y",service);
	}
	
	public String install(String flags, String service) {
		return String.format("apt-get %s install %s",flags, service);
	}

	//TODO: Installer "remove" not tested
	public String remove(String flags, String services) {
		return String.format("apt-get %s --purge remove %s ",flags, services);
	}
	
	public String openPort(String port){
		return String.format("/sbin/iptables -A INPUT -i eth0 -p tcp --destination-port %s -j ACCEPT",port);
	}
	
	public String restart(String service) {
		return String.format("service %s restart", service);
	}
	
	public String update() {
		return "apt-get update";
	}

}
