package gears.base;

import org.apache.log4j.Logger;

import gears.base.connection.Connection;
import gears.base.connection.ConnectionFactory;
import gears.base.pkmg.Installer;
import gears.base.pkmg.InstallerFactory;

public class Instance {
	
	
	private static final Logger LOG = Logger.getLogger(Instance.class.getClass());
	
	protected Connection connection = null;
	protected Installer  installer  = null;
	
	protected ConnectionFactory connFactory    = ConnectionFactory.getInstance();
	protected InstallerFactory  installFactory = InstallerFactory.getInstance();
	
	String fqdn;
	String sshPermKeyPath;
	Configuration config;

	public Instance(String fqdn, String sshPermKeyPath) {
		this.fqdn = fqdn;
		this.sshPermKeyPath = sshPermKeyPath;
	}
	
	public Instance(String fqdn, String sshPermKeyPath, Connection connection, Installer installer) {
		this.sshPermKeyPath = sshPermKeyPath;
		this.fqdn = fqdn;
		
		this.connection = connection;
		this.installer = installer;
	}

	public void setInstaller(Installer installer) {
		this.installer = installer;		
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	
	public void setFQDN(String fqdn) {
		this.fqdn = fqdn;
	}
	public void setSSHPermKeyPath(String sshPermKeyPath) {
		this.sshPermKeyPath = sshPermKeyPath;
	}
	
	public String getFQDN() {
		return fqdn;
	}
	public String getSSHPermKeyPath() {
		return sshPermKeyPath;
	}

}
