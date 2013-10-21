package gears.base.connection;

import gears.base.Connection;
import gears.base.ServerConfiguration;

public class ConnectionFactory {

	private static ConnectionFactory instance = null;
	
	public static ConnectionFactory getInstance() {
		if(instance == null) instance = new ConnectionFactory();
		return instance;
	}
	
	public Connection getSSHConnection(ServerConfiguration  config){
		return new SSHConnection(config);
	}
	
	

}
