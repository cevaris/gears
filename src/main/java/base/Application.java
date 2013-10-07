package base;

import net.schmizz.sshj.connection.channel.direct.Session;

public class Application {
	
	protected String PACKAGE_NAME;
	protected Server server;
	
	public Application(Server server) {
		this.server = server;
	}
	
	public String getPackageName() {
		return PACKAGE_NAME;
	}
	
	
	protected boolean install() {
		
		return true;		
	}
	
	protected boolean uninstall() {
		
		return true;		
	}
	
	public boolean update() {
		// TODO Auto-generated method stub
		return false;
	}



	public Session getSession() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
