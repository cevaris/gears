package org.gears;

import java.util.List;


import org.apache.log4j.Logger;
import org.apache.velocity.context.Context;
import org.gears.template.Templaton;


abstract public class Gear {
	
	private static final Logger LOG = Logger.getLogger(Gear.class);
	
	protected Configuration config = Configuration.getInstance();
	protected String gearGroup = null;
	protected Instance instance = null;
	
	public abstract void execute();
	
	public boolean isSystem(System guess){
		if(this.instance != null){
			return this.instance.getSystem() == guess;
		}
		return false;
	}
	
	
	
	private List<Instance> getInstances() {
		return (this.gearGroup == null) ? config.getInstances() : config.getInstances(this.gearGroup);
	}
	
	private void setGearGroup(String gearGroup) {
		this.gearGroup = gearGroup;
	}
	
	private void setInstance(Instance instance) {
		this.instance = instance;
	}

	
	
	
	public void install(String group, Gear gear) {
		for(Instance instance : config.getInstances(group)){
			gear.setInstance(instance);
			gear.execute();
			gear.setInstance(null);
		}
	}
	
	public void install(Gear gear) {
		install(null, gear);
	}
	
	
		
	
	
	
	
	public boolean update() {
		for(Instance instance : getInstances()){
			setInstance(instance);
			instance.update();
			setInstance(null);
		}
		return true;
	}
	
	public boolean install(String gearGroup, String flags, String commands) {
		this.setGearGroup(gearGroup);
		install(flags, commands);
		this.setGearGroup(null);
		return true;
	}
	
	public boolean install(String commands) {
		return install("", commands);
	}
	
	public boolean install(String flags, String commands) {
		for(Instance instance : getInstances()){
			setInstance(instance);
			instance.install(flags, commands);
			setInstance(null);
		}
		return true;
	}
	
	public boolean openPort(String value) {
		for(Instance instance : getInstances()){
			setInstance(instance);
			instance.openPort(value);
			setInstance(null);
		}
		return true;
	}

	
	public boolean start(Gear gear) {
		return start(gear.toString());
	}
	public boolean start(String service) {
		for(Instance instance : getInstances()){
			setInstance(instance);
			instance.start(service);
			setInstance(null);
		}
		return true;
	}
	
	
	public boolean restart(String gearGroup, Gear service) {
		setGearGroup(gearGroup);
		boolean result = restart(service.toString());
		setGearGroup(null);
		return result;
	}
	
	public boolean restart(Gear service) {
		return restart(service.toString());
	}
	
	public boolean restart(String service) {
		for(Instance instance : getInstances()){
			setInstance(instance);
			instance.restart(service);
			setInstance(null);
		}
		return true;
	}
	
	public boolean command(String commands) {
		boolean result = true;
		for(Instance instance : getInstances()){
			setInstance(instance);
			instance.command(commands);
			setInstance(null);
		}
		return result;
	}
	
	
	
	public boolean render(String gearGroup, String source, String dest) {
		this.setGearGroup(gearGroup);
		render(source, dest);
		this.setGearGroup(null);
		return true;
	}
	
	public boolean render(String source, String dest) {
		Context context = Templaton.getContext(this);
		for(Instance instance : getInstances()){
			setInstance(instance);
			instance.render(source, dest, context);
			setInstance(null);
		}
		return true;
	}
	

}
