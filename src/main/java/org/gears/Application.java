package org.gears;

import java.util.List;


import org.apache.log4j.Logger;
import org.apache.velocity.context.Context;
import org.gears.template.Templaton;


abstract public class Application {
	
	private static final Logger LOG = Logger.getLogger(Application.class);
	
	protected Configuration config = Configuration.getInstance();
	protected Instance instance = null;
	
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
	
	public abstract void execute();
	
	
}
