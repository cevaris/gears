package gears.base.pkmg;

import gears.base.connection.Connection;

public class DebianInstaller implements Installer {
	
	Connection connection = null; 
	
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	
	public boolean install(String flags, String service) {
		assert (this.connection != null) : "Connection is null";
		return this.connection.execute(String.format("apt-get %s install %s",flags, service));
	}

	//TODO: Installer "remove" not tested
	public boolean remove(String flags, String services) {
		assert (this.connection != null) : "Connection is null";
		return this.connection.execute(String.format("apt-get %s --purge remove %s ",flags, services));
	}
	
	public boolean restart(String service) {
		assert (this.connection != null) : "Connection is null";
		return this.connection.execute(String.format("service %s restart", service));
	}
	
	public boolean update() {
		assert (this.connection != null) : "Connection is null";
		return this.connection.execute("apt-get update");
	}

	public boolean execute(String commands) {
		assert (this.connection != null) : "Connection is null";
		return this.connection.execute(commands);
	}

	

}
