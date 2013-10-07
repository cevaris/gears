package base;

import net.schmizz.sshj.connection.channel.direct.Session;

public class Application {
	
	protected String PACKAGE_NAME;
	
	public String getPackageName() {
		return PACKAGE_NAME;
	}
	
	
	protected boolean install() {
		
		return true;		
	}
	
	protected boolean uninstall() {
		
		return true;		
	}


	public Session getSession() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
