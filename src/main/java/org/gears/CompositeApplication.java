package org.gears;

import java.util.List;


import org.apache.log4j.Logger;


abstract public class CompositeApplication extends Application {
	
	private static final Logger LOG = Logger.getLogger(CompositeApplication.class);
	
	
	
	private List<Instance> getInstances() {
		return (this.gearGroup == null) ? config.getInstances() : config.getInstances(this.gearGroup);
	}
	
	private void setGearGroup(String gearGroup) {
		this.gearGroup = gearGroup;
	}
	
	private void setInstance(Instance instance) {
		this.instance = instance;
	}

	
	
	public void install(String group, CompositeApplication gear) {
	}
	
	public void install(CompositeApplication gear) {
	}
	
	public void install(String gearGroup, String commands) {
	}
	
		
	public void update() {
	}
	
	
	
	public void install(String commands) {
	}
	
	
	
	public void openPort(String value) {
	}

	
	public void start(CompositeApplication gear) {
	}
	public void start(String service) {
	}
	
	
	public void restart(String gearGroup, CompositeApplication service) {
	}
	
	public void restart(CompositeApplication service) {
	}
	
	public void restart(String service) {
	}
	
	
	
	public void command(String commands) {
	}
	
	
	
	public void render(String gearGroup, String source, String dest) {
	}
	
	public void render(String source, String dest) {
	}
	

}
