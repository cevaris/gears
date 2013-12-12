package org.gears;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class Configuration {
	
	private static final Logger LOG = Logger.getLogger(Configuration.class.getClass());
	
	private static Configuration instance = null;
	protected List<Instance> instancesList;
	protected Map<String,List<Instance>> instancesMap;

	public static Configuration getInstance() {
		if(instance == null) {
			instance = new Configuration();
		}
		return instance;
	}
		
	private Configuration() {
		this.instancesMap  = new HashMap<String, List<Instance>>();
		this.instancesList = new ArrayList<Instance>();
		disconnectInstances();
	}
	
	public List<Instance> getInstances(String group) {
		if(this.instancesMap.containsKey(group))
			return this.instancesMap.get(group);
		else
			return new ArrayList<Instance>();
	}
	
	public List<Instance> getInstances() {
		return this.instancesList;
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
	
	public static void reset(){
		instance = new Configuration();		
	}
	
	private void disconnectInstances() {
		Runtime.getRuntime().addShutdownHook(new Thread() {
		    public void run() { 
		    	LOG.info("Shutting down...");
		    	if(instancesList != null){
		    		for(Instance node: instancesList)
		    			node.disconnect();
		    	}
		    	LOG.info("Done!");
		    }
		});
	}

}
