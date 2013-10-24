package gears.base;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class Configuration {
	
	private static final Logger LOG = Logger.getLogger(Configuration.class.getClass());
	
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
		
		this.instancesMap.get(group).add(instance);
		this.instancesList.add(instance);
		
		System.out.println(this.instancesMap.get(group));
		System.out.println(this.instancesList);
		System.out.println(instance.connection);
		System.out.println("--");
		
		return instance.connect();
	}
	
	public List<Instance> getInstances(){
		return this.instancesList;
	}
	
}
