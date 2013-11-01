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
			instance.execute(gear);
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
		instance.update();
		return true;
	}
	
	
	public boolean install(String flags, String commands) {
		for(Instance instance : this.config.getInstances(this.gearGroup)){
			instance.install(flags, commands);
		}
		return true;
	}
	

	public boolean restart(String service) {
		assert this.gearGroup != null : "Gear Group not defined";
		for(Instance instance : this.config.getInstances(this.gearGroup)){
			instance.restart(service);
		}
		return true;
	}

	public boolean command(String commands) {
		assert this.gearGroup != null : "Gear Group not defined";
		for(Instance instance : this.config.getInstances(this.gearGroup)){
			instance.command(commands);
		}
		return true;
	}

	
	public boolean render(String source, String dest, VelocityContext context) {
		assert this.gearGroup != null : "Gear Group not defined";
		for(Instance instance : this.config.getInstances(this.gearGroup)){
			instance.render(source, dest, context);
		}
		return true;
	}

}
