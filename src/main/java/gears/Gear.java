package gears;

import java.util.List;

import gears.template.Templaton;

import org.apache.log4j.Logger;
import org.apache.velocity.context.Context;


abstract public class Gear {
	
	private static final Logger LOG = Logger.getLogger(Gear.class);
	
	protected Configuration config = Configuration.getInstance();
	
	private String gearGroup = null;
	
	public abstract void execute(); 
	
	private List<Instance> getInstances() {
		return (this.gearGroup == null) ? config.getInstances() : config.getInstances(this.gearGroup);
	}
	
	private void setGearGroup(String gearGroup) {
		this.gearGroup = gearGroup;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	public void install(String gearGroup, Gear gear) {
		this.setGearGroup(gearGroup);
		gear.execute();
		this.setGearGroup(null);
	}
	
	public boolean render(String gearGroup, String source, String dest) {
		this.setGearGroup(gearGroup);
		Context context = Templaton.getContext(this);
		for(Instance instance : getInstances()){
			instance.render(source, dest, context);
		}
		this.setGearGroup(null);
		return true;
	}
	
	
	
	
	
	
	
	
	
	
	
	public boolean update() {
		for(Instance instance : getInstances()){
			instance.update();
		}
		return true;
	}
	
	
	public void install(Gear gear) {
		this.setGearGroup(null);
		gear.execute();
	}
	
	
	public boolean install(String commands) {
		for(Instance instance : getInstances()){
			instance.install(commands);
		}
		return true;
	}
	
	public boolean install(String flags, String commands) {
		for(Instance instance : getInstances()){
			instance.install(flags, commands);
		}
		return true;
	}
	
	public boolean openPort(String value) {
		for(Instance instance : getInstances()){
			instance.openPort(value);
		}
		return true;
	}

	public boolean restart(String service) {
		for(Instance instance : getInstances()){
			instance.restart(service);
		}
		return true;
	}

	public boolean command(String commands) {
		boolean result = true;
		for(Instance instance : getInstances()){
			instance.command(commands);
		}
		return result;
	}
	
	public boolean render(String source, String dest) {
		Context context = Templaton.getContext(this);
		
		for(Instance instance : getInstances()){
			instance.render(source, dest, context);
		}
		return true;
	}
	

}
