package gears.base;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;

import template.Templaton;


abstract public class Application {
	
	protected String PACKAGE_NAME;
	protected Server server;
	
	protected abstract void execute();
	
	public Application(Server server) {
		this.server = server;
	}
	
	public boolean install(String[] commands, String[] flags) {
		return install(
			StringUtils.join(commands, " "), 
			StringUtils.join(flags, " ")
		);
	}

	public boolean install(String service) {
		return install(service, "");
	}
	
	public boolean install(String service, String flags) {
		return execute(String.format("apt-get %s install %s",flags, service));
	}
	
	public boolean restart(String service) {
		return execute(String.format("service %s restart", service));
	}
	
	public boolean update() {
		return execute("apt-get update");
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
//		System.out.println(command);
//		return false;
		
//		SSHRequest request = new SSHRequest(this.server);
		return this.server.connection.execute(command);
//		return request.execute(command);
//		return request.execute("ls -l /");
	}

	
	

}
