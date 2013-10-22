package gears.base;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;

import template.Templaton;


abstract public class Application implements Installer {
	
	protected Gear deployment;
	
	protected abstract void execute();
	
	public Application(Gear deployment) {
		this.deployment = deployment;
	}
	
	public void render(String source, String dest, VelocityContext context) {
		Templaton templaton = Templaton.getInstance();
		File destFile = new File(dest);
		execute(String.format("mkdir -p %s", destFile.getParentFile()));
		
		String document = templaton.render(source, context).toString();
		
//		execute(String.format( "echo -e \"%s\" > %s", document.replace("\"", "\\\""), dest));
		execute(String.format( "echo -e \"%s\" > %s", document.replace("\"", "\\\""), dest));
	}
	
	public boolean execute(String command) {
		System.out.println(command);
		return false;
		
//		SSHRequest request = new SSHRequest(this.server);
//		return this.server.connection.execute(command);
//		return request.execute(command);
//		return request.execute("ls -l /");
	}

	
	

}
