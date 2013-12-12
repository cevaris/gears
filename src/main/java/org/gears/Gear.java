package org.gears;

import java.util.List;


import org.apache.log4j.Logger;
import org.gears.template.Templaton;


abstract public class Gear extends Application {
	
	private static final Logger LOG = Logger.getLogger(Gear.class);
	
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
	
	
	public void openPort(String value) {
	}

	
	public void command(String commands) {
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
