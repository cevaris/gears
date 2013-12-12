package org.gears;


import org.apache.log4j.Logger;


abstract public class Gear {
	
	static final Logger LOG = Logger.getLogger(Gear.class);
	
	protected Configuration config = Configuration.getInstance();
	private Instance instance = null;
	
	public boolean isSystem(System guess){
		if(this.instance != null){
			return this.instance.getSystem() == guess;
		}
		return false;
	}
	
	public System getSystem(){
		if(this.instance != null){
			return this.instance.getSystem();
		}
		return null;
	}
	
	
	public final void execute(Instance instance) {
		this.instance = instance;
		this.execute();
	}
	
	public Instance getInstance() {
		return instance;
	}
	
	protected void setInstance(Instance instance) {
		this.instance = instance;
	}
	
	public abstract void execute();
	
	
}
