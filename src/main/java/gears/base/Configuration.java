package gears.base;


import gears.base.connection.Connection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class Configuration {
	
	
	private static final Logger LOG = Logger.getLogger(Configuration.class.getClass());
	
	private static Configuration configuration;
	
	protected List<Instance> instancesList;
	protected Map<String,List<Instance>> instancesMap;
	
	public Configuration() {
		this.instancesMap  = new HashMap<String, List<Instance>>();
		this.instancesList = new ArrayList<Instance>();
	}
	
	public List<Instance> getInstances(String group) {
		return this.instancesMap.get(group);
	}
	
	public boolean addInstance(String group, Instance instance){
		// Add instance list to newly encountered groups
		if(this.instancesMap.get(group) == null) 
			this.instancesMap.put(group, new ArrayList<Instance>());
		
//		instance.setGearGroup(group);
		this.instancesMap.get(group).add(instance);
		this.instancesList.add(instance);
		return instance.connect();
	}
	
	public List<Instance> getInstances(){
		return this.instancesList;
	}
	
}
