package org.gears;


import org.apache.log4j.Logger;
import org.gears.template.Templaton;


abstract public class GearApplication extends Application {
	
	static final Logger LOG = Logger.getLogger(GearApplication.class);
	
	
	public void render(String source, String destination) {
		getInstance().render(source, destination, Templaton.getContext(this));
	}
		
	
	public void service(String serviceName, Service state) {
		getInstance().service(serviceName, state);
	}
	
	public void install(String commands) {
		getInstance().install(commands);
	}
	
	
	public void openPort(String value) {
		getInstance().openPort(value);
	}

	
	public void command(String commands) {
		getInstance().command(commands);
	}
	
	

}
