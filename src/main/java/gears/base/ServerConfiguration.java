package gears.base;

import java.util.ArrayList;
import java.util.List;

public class ServerConfiguration {
	
	protected List<Instance> instances;
	
	public ServerConfiguration() {
		instances = new ArrayList<Instance>();
	}
	
	public List<Instance> getInstances() {
		return instances;
	}
	public boolean addInstance(Instance instance){
		return this.instances.add(instance);
	}
	
	

}
