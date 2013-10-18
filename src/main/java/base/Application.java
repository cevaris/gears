package base;

import static org.junit.Assert.assertTrue;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;

import template.Templaton;

import net.schmizz.sshj.connection.channel.direct.Session;
import network.SSHRequest;

abstract public class Application {
	
	protected String PACKAGE_NAME;
	protected Server server;
	
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
	
	public boolean restartService(String service) {
		return execute(String.format("service %s restart", service));
	}
	
	public boolean update() {
		return execute("apt-get update");
	}
	
	public boolean render(String source, String dest, VelocityContext context) {
		return execute(String.format("echo <<  %s", ));
	}
	
	public boolean execute(String command) {
		System.out.println(command);
		return false;
//		SSHRequest request = new SSHRequest(this.server);
//		return request.execute(command);
	}

	public Session getSession() {
		return this.server.getSession();
	}
	
	

}
