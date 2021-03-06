package org.gears;

import java.util.HashMap;


import org.apache.log4j.Logger;
import org.gears.template.Templaton;


abstract public class GearApplication extends Gear {
	
	static final Logger LOG = Logger.getLogger(GearApplication.class);
	
	
	public void install(String commands) {
		for(Instance instance : config.getInstances()){
			instance.install(commands);
		}
	}
	
	public void install(String group, String commands) {
		for(Instance instance : config.getInstances(group)){
			instance.install(commands);
		}
	}
	
	public void install(String group, Gear application) {
		for(Instance instance : config.getInstances(group)){
			application.execute(instance);
		}
	}
	
	public void install(Gear application) {
		for(Instance instance : config.getInstances()){
			application.execute(instance);
		}
	}
		
	
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
	

	
	public void install(String group, HashMap<System, Object> context) {
		for(Instance instance : config.getInstances(group)){
			instance.install((String)context.get(instance.getSystem()));
		}
	}
	
	
	public void service(Gear application, Service state) {
		for(Instance instance : config.getInstances()){
			// TODO: Find a way to get red of Application.setInstance(Instance);
			application.setInstance(instance);
			instance.service(application.toString(), state);
		}
	}
	
	public void service(String group, Gear application, Service state) {
		for(Instance instance : config.getInstances(group)){
			// TODO: Find a way to get red of Application.setInstance(Instance);
			application.setInstance(instance);
			instance.service(application.toString(), state);
		}
	}
	
	
	
	
	public void render(String source, String dest) {
		for(Instance instance : config.getInstances()){
			instance.render(source, dest, Templaton.getContext(this));
		}
	}
	public void render(String group, String source, String dest) {
		for(Instance instance : config.getInstances(group)){
			instance.render(source, dest, Templaton.getContext(this));
		}
	}
	public void render(String group, String source, HashMap<System, Object> context) {
		for(Instance instance : config.getInstances(group)){
			instance.render(source, (String) context.get(instance.getSystem()), Templaton.getContext(this));
		}
	}
	

}
