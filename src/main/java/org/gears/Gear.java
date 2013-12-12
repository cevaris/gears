package org.gears;

import java.util.List;


import org.apache.log4j.Logger;


abstract public class Gear extends Application {
	
	private static final Logger LOG = Logger.getLogger(Gear.class);
	
//	private List<Instance> getInstances() {
//		return (this.gearGroup == null) ? config.getInstances() : config.getInstances(this.gearGroup);
//	}
//	
//	private void setGearGroup(String gearGroup) {
//		this.gearGroup = gearGroup;
//	}
	
//	private void setInstance(Instance instance) {
//		this.instance = instance;
//	}
	
	
	public void install(String group, Application application) {
		for(Instance instance : config.getInstances(group)){
			application.execute(instance);
		}
	}
	
	public void install(Application application) {
		for(Instance instance : config.getInstances()){
			application.execute(instance);
		}
	}
	
//	public void install(String gearGroup, String commands) {
//	}
	
		
	
	public void update(String group) {
		for(Instance instance : config.getInstances(group)){
			instance.update();
		}
	}
	
	public void update() {
		for(Instance instance : config.getInstances()){
			instance.update();
		}
	}
	
	
	
	public void install(String commands) {
	}
	
	
	
	public void openPort(String value) {
	}

	
	public void start(Gear gear) {
	}
	public void start(String service) {
	}
	
	
//	public void restart(String gearGroup, CompositeApplication service) {
//	}
	
	public void restart(Gear service) {
	}
	
	public void restart(String service) {
	}
	
	
	
	public void command(String commands) {
	}
	
	
	
//	public void render(String gearGroup, String source, String dest) {
//	}
//	
//	public void render(String source, String dest) {
//	}
	
	public void render(Instance instance, String source, String dest) {
		
	}
	

}
