package gears.base;

import gears.base.connection.ConnectionFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Configuration {
	
	
	protected ConnectionFactory connFactory    = ConnectionFactory.getInstance();
	protected InstallerFactory  installFactory = InstallerFactory.getInstance();
	
	protected Connection connection = null;
	protected Installer  installer  = null;
	
	protected List<Instance> instancesList;
	protected Map<String,List<Instance>> instancesMap;
	
	public Configuration() {
		this.instancesMap  = new HashMap<String, List<Instance>>();
		this.instancesList = new ArrayList<Instance>();
		this.connection = this.connFactory.getSSHConnection(this);
		this.installer 	= this.installFactory.getDebianInstaller(this.connection);
	}
	
	public List<Instance> getInstances(String group) {
		return this.instancesMap.get(group);
	}
	
	public boolean addInstance(String group, Instance instance){
		// Add instance list to newly encountered groups
		if(this.instancesMap.get(group) == null) 
			this.instancesMap.put(group, new ArrayList<Instance>());
		
		return this.instancesMap.get(group).add(instance) && this.instancesList.add(instance);
	}
	
	public List<Instance> getInstances(){
		return this.instancesList;
	}
	
	protected void setInstaller(Installer installer) {
		this.installer = installer;		
	}

	protected void setConnection(Connection connection) {
		this.connection = connection;
	}
	
	

}
