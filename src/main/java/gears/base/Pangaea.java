package gears.base;

import gears.base.template.Templaton;

import java.io.File;

import org.apache.velocity.VelocityContext;

public interface Pangaea {
	
	public boolean update(); 
	
	public boolean install(String flags, String commands);
	
	public boolean restart(String service);
	
	public boolean command(String commands);
	
	public boolean render(String source, String dest, VelocityContext context);
}
