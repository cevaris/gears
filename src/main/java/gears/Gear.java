package gears;

import static org.junit.Assert.assertTrue;
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
		for(Instance instance : this.config.getInstances()){
			instance.update();
		}
		return true;
	}
	
	public boolean install(String flags, String commands) {
		assert this.gearGroup != null : "Gear group not defined";
		for(Instance instance : this.config.getInstances()){
			instance.install(flags, commands);
		}
		return true;
	}
	
	public boolean openPort(String value) {
		assert this.gearGroup != null : "Gear group not defined";
		for(Instance instance : this.config.getInstances()){
			instance.openPort(value);
		}
		return true;
	}

	public boolean restart(String service) {
		assert this.gearGroup != null : "Gear group not defined";
		for(Instance instance : this.config.getInstances()){
			instance.restart(service);
		}
		return true;
	}

	public boolean command(String commands) {
		assert this.gearGroup != null : "Gear group not defined";
		boolean result = true;
		for(Instance instance : this.config.getInstances()){
			instance.command(commands);
		}
		return result;
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
	
	
	public boolean render(String source, String dest) {
		assert this.gearGroup != null : "Gear group not defined";
		Context context = Templaton.getContext(this);
		
		for(Instance instance : this.config.getInstances()){
			instance.render(source, dest, context);
		}
		return true;
	}
	
	public boolean render(String gearGroup, String source, String dest) {
		Context context = Templaton.getContext(this);
		for(Instance instance : this.config.getInstances(gearGroup)){
			instance.render(source, dest, context);
		}
		return true;
	}
	
	

}
