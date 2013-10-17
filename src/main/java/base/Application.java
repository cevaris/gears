package base;

import org.apache.commons.lang.StringUtils;

import net.schmizz.sshj.connection.channel.direct.Session;
import network.SSHRequest;

abstract public class Application {
	
	protected String PACKAGE_NAME;
	protected Server server;
	
	public Application(Server server) {
		this.server = server;
	}
	
	public String getPackageName() {
		return PACKAGE_NAME;
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
	
	public boolean update() {
		return execute("apt-get update");
	}
	
	private boolean execute(String command) {
		SSHRequest request = new SSHRequest(this.server);
		return request.execute(command);
	}

	public Session getSession() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
