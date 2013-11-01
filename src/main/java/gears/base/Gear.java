package gears.base;

import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;

abstract public class Gear {
	
	private static final Logger LOG = Logger.getLogger(Gear.class.getClass());
	
	protected Configuration config = null;
	
	public abstract void execute(); 
	
	public void setConfig(Configuration config) {
		this.config = config;
	}
	
	protected boolean install(String group, Gear gear) {
		
		boolean result = true;
		for(Instance instance : this.config.getInstances(group)){
			LOG.info("Installing gear: "+gear.getClass().getCanonicalName());
		}
		return result;
	}
	
	public boolean render(String gearGroup, String source, String dest, VelocityContext context) {
		for(Instance instance : this.config.getInstances(gearGroup)){
			instance.render(source, dest, context);
		}
		return true;
	}
	
	public boolean update() {
		for(Instance instance : this.config.getInstances()){
			instance.update();
		}
		return true;
	}
	
	public boolean install(String flags, String commands) {
		for(Instance instance : this.config.getInstances()){
			instance.install(flags, commands);
		}
		return true;
	}

	public boolean restart(String service) {
		for(Instance instance : this.config.getInstances()){
			instance.restart(service);
		}
		return true;
	}

	public boolean command(String commands) {
		for(Instance instance : this.config.getInstances()){
			instance.command(commands);
		}
		return true;
	}

	
	public boolean render(String source, String dest, VelocityContext context) {
		for(Instance instance : this.config.getInstances()){
			instance.render(source, dest, context);
		}
		return true;
	}

}
