package org.gears;

import java.util.List;


import org.apache.log4j.Logger;


abstract public class GearApplication extends Application {
	
	private static final Logger LOG = Logger.getLogger(GearApplication.class);
	
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
	
	
	
//	public void install(String gearGroup, String commands) {
//	}
	
		
	
	public void service(String serviceName, Service state) {
		this.instance.service(serviceName, state);
	}
	
	public void install(String commands) {
		this.instance.install(commands);
	}
	
	
	
	public void openPort(String value) {
	}

	
	public void start(GearApplication gear) {
	}
	public void start(String service) {
	}
	
	
//	public void restart(String gearGroup, CompositeApplication service) {
//	}
	
	public void restart(GearApplication service) {
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
