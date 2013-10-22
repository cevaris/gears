package gears.base;

import org.apache.commons.lang.StringUtils;

public class DebianInstaller implements Installer {
	
	Connection connection = null; 
	
	public DebianInstaller(Connection connection) {
		this.connection = connection;
	}
	
	public boolean install(String[] commands, String[] flags) {
		return install(
			StringUtils.join(commands, " "), 
			StringUtils.join(flags, " ")
		);
	}

	public boolean install(String service) {
		return install(service, "");
	}
	
	public boolean install(String service, String flags) {
		assert (this.connection != null) : "Connection is null";
		return this.connection.execute(String.format("apt-get %s install %s",flags, service));
	}
	
	public boolean restart(String service) {
		assert (this.connection != null) : "Connection is null";
		return this.connection.execute(String.format("service %s restart", service));
	}
	
	public boolean update() {
		assert (this.connection != null) : "Connection is null";
		return this.connection.execute("apt-get update");
	}

}
