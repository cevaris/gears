package gears.base;

import gears.base.connection.ConnectionFactory;

import java.util.ArrayList;
import java.util.List;

public class Configuration {
	
	
	protected ConnectionFactory connFactory    = ConnectionFactory.getInstance();
	protected InstallerFactory  installFactory = InstallerFactory.getInstance();
	
	protected Connection connection = null;
	protected Installer  installer  = null;
	
	protected List<Instance> instances;
	
	public Configuration() {
		this.instances = new ArrayList<Instance>();
		this.connection = this.connFactory.getSSHConnection(this);
		this.installer 	= this.installFactory.getDebianInstaller(this.connection);
	}
	
	public List<Instance> getInstances() {
		return instances;
	}
	public boolean addInstance(Instance instance){
		return this.instances.add(instance);
	}
	
	protected void setInstaller(Installer installer) {
		this.installer = installer;		
	}

	protected void setConnection(Connection connection) {
		this.connection = connection;
	}
	
	

}
