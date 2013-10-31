package gears.base;

import java.io.File;

import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;

import gears.base.connection.Connection;
import gears.base.pkmg.Installer;
import gears.base.template.Templaton;

public class Instance implements Pangaea {
	
	
	private static final Logger LOG = Logger.getLogger(Instance.class.getClass());
	
	protected Connection connection = null;
	protected Installer  installer  = null;
	
	String fqdn;
	String sshPermKeyPath;

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

	public boolean connect() {
		return this.connection.connect(this);
	}

	public boolean update()  { 
		return command(this.installer.update());
	}
	
	public boolean install(String flags, String commands) {
		LOG.info(this.installer == null);
//		return this.installer.install(flags, commands);		
		return command(this.installer.install(flags, commands));
	}
	
	public boolean restart(String service) {
		return command(this.installer.restart(service));
	}
	
	public boolean command(String commands) {
		return this.connection.command(commands);
	}
	
	public boolean render(String source, String dest, VelocityContext context) {
		Templaton templaton = Templaton.getInstance();
		
		File destFile = new File(dest);
		// Make sure parent directory exists for destination file
		command(String.format("mkdir -p %s", destFile.getParentFile()));
		
		String document = templaton.render(source, context).toString();
		command(String.format( "echo -e \"%s\" > %s", document.replace("\"", "\\\""), dest));
		
		return true;
	}

}
