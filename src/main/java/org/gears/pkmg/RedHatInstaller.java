package org.gears.pkmg;

import org.apache.log4j.Logger;

public class RedHatInstaller implements Installer {
	
	private static final Logger LOG = Logger.getLogger(RedHatInstaller.class);
	
	public String install(String service) {
		return install("-y",service);
	}
	
	public String install(String flags, String service) {
		return String.format("yum %s --disableplugin=fastestmirror install %s",flags, service);
	}

	//TODO: Installer "remove" not tested
	public String remove(String flags, String services) {
		return String.format("yum %s --purge remove %s ",flags, services);
	}
	
	public String openPort(String port){
		String command = String.format("-A RH-Firewall-1-INPUT -m state --state NEW -m tcp -p tcp --dport %s -j ACCEPT", port);
		return String.format(
				"echo -e \"%s\" >> %s", command.replace("\"", "\\\""), "/etc/sysconfig/iptables");
//		return String.format("/sbin/iptables -A INPUT -i eth0 -p tcp --destination-port %s -j ACCEPT",port);
	}
	
	public String start(String service) {
		return String.format("service %s start", service);
	}
	
	public String restart(String service) {
		return String.format("service %s restart", service);
	}
	
	public String update() {
		return "yum --disableplugin=fastestmirror update";
	}

}
