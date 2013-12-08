package gears;

import gears.template.Templaton;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.context.Context;


abstract public class Gear {
	
	private static final Logger LOG = Logger.getLogger(Gear.class.getClass());
	
	protected Configuration config = Configuration.getInstance();
	
	private String gearGroup = null;
	
	public abstract void execute(); 
	
	public void install(String gearGroup, Gear gear) {
		gear.setGearGroup(gearGroup);
		gear.execute();
	}
	
	public void setGearGroup(String gearGroup) {
		this.gearGroup = gearGroup;
	}
	
	public boolean update() {
		assert this.gearGroup != null : "Gear group not defined";
		LOG.info(this.gearGroup);
		LOG.info(this.config.getInstances());
		for(Instance instance : this.config.getInstances(this.gearGroup)){
			instance.update();
		}
		return true;
	}
	
	public boolean install(String flags, String commands) {
		assert this.gearGroup != null : "Gear group not defined";
		for(Instance instance : this.config.getInstances(this.gearGroup)){
			instance.install(flags, commands);
		}
		return true;
	}

	public boolean restart(String service) {
		assert this.gearGroup != null : "Gear group not defined";
		for(Instance instance : this.config.getInstances(this.gearGroup)){
			instance.restart(service);
		}
		return true;
	}

	public boolean command(String commands) {
		assert this.gearGroup != null : "Gear group not defined";
		for(Instance instance : this.config.getInstances(this.gearGroup)){
			instance.command(commands);
		}
		return true;
	}

	
	public boolean render(String source, String dest) {
		assert this.gearGroup != null : "Gear group not defined";
		Context context = Templaton.getContext(this.getClass());
		
		for(Instance instance : this.config.getInstances(this.gearGroup)){
			instance.render(source, dest, context);
		}
		return true;
	}
	
	public boolean render(String source, String dest, Context context) {
		assert this.gearGroup != null : "Gear group not defined";
		for(Instance instance : this.config.getInstances(this.gearGroup)){
			instance.render(source, dest, context);
		}
		return true;
	}
	
	public boolean render(String gearGroup, String source, String dest, Context context) {
		for(Instance instance : this.config.getInstances(gearGroup)){
			instance.render(source, dest, context);
		}
		return true;
	}
	
	

}
