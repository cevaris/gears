package gears.base.pkmg;

import org.apache.log4j.Logger;

import gears.base.Gear;
import gears.base.connection.Connection;

public class DebianInstaller implements Installer {
	
	private static final Logger LOG = Logger.getLogger(DebianInstaller.class.getClass());
	
	Connection connection = null; 
	
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	
	public boolean install(String flags, String service) {
		assert (this.connection != null) : "Connection is null";
		return execute(String.format("apt-get %s install %s",flags, service));
	}

	//TODO: Installer "remove" not tested
	public boolean remove(String flags, String services) {
		assert (this.connection != null) : "Connection is null";
		return execute(String.format("apt-get %s --purge remove %s ",flags, services));
	}
	
	public boolean restart(String service) {
		assert (this.connection != null) : "Connection is null";
		return execute(String.format("service %s restart", service));
	}
	
	public boolean update() {
		assert (this.connection != null) : "Connection is null";
		return execute("apt-get update");
	}

	public boolean execute(String commands) {
		assert (this.connection != null) : "Connection is null";
//		LOG.info(commands);
		return this.connection.execute(commands);
//		return true;
//		return this.connection.execute(commands);
	}

	

}
