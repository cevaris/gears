package gears.base;

import org.apache.commons.lang.StringUtils;

public class DebianInstaller implements Installer {
	
	Connection connection = null; 
	
	public DebianInstaller(Connection connection) {
		this.connection = connection;
	}
	
	public boolean install(String flags, String service) {
		assert (this.connection != null) : "Connection is null";
		return this.connection.execute(String.format("apt-get %s install %s",flags, service));
	}

	//TODO: Installer "remove" not tested
	public boolean remove(String[] commands, String[] flags) {
		assert (this.connection != null) : "Connection is null";
		return this.connection.execute(String.format("apt-get %s --purge remove %s ",flags, commands));
	}
	
	public boolean restart(String service) {
		assert (this.connection != null) : "Connection is null";
		return this.connection.execute(String.format("service %s restart", service));
	}
	
	public boolean update() {
		assert (this.connection != null) : "Connection is null";
		return this.connection.execute("apt-get update");
	}

	public boolean remove(String flags, String service) {
		// TODO Auto-generated method stub
		return false;
	}

}
