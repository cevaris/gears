package org.gears;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.log4j.Logger;
import org.gears.template.Templaton;


abstract public class Gear extends Application {
	
	private static final Logger LOG = Logger.getLogger(Gear.class);
	
	
	
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
	
	
	public void service(Application application, Service state) {
		for(Instance instance : config.getInstances()){
			// TODO: Find a way to get red of Application.setInstance(Instance);
			application.setInstance(instance);
			instance.service(application.toString(), state);
		}
	}
	
	public void service(String group, Application application, Service state) {
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
	

}
