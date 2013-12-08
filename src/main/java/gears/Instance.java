package gears;

import java.io.File;

import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.context.Context;

import gears.connection.Connection;
import gears.connection.ConnectionFactory;
import gears.pkmg.Installer;
import gears.pkmg.InstallerFactory;
import gears.template.Templaton;

public class Instance {
	
	private ConnectionFactory connFactory    = ConnectionFactory.getInstance();
	private InstallerFactory  installFactory = InstallerFactory.getInstance();
	
	private static final Logger LOG = Logger.getLogger(Instance.class.getClass());
	
	private Connection connection  = null;
	protected Installer  installer = null;
	
	String fqdn;
	String sshPermKeyPath;

	public Instance(String fqdn, String sshPermKeyPath, Connection connection, Installer installer) {
		this.sshPermKeyPath = sshPermKeyPath;
		this.fqdn = fqdn;
		
		this.connection = connection;
		this.installer = installer;
	}
	
	public Instance(String fqdn, String sshPermKeyPath) {
		this.sshPermKeyPath = sshPermKeyPath;
		this.fqdn = fqdn;
		
		this.connection = this.connFactory.getSSHConnection();
		this.installer  = this.installFactory.getDebianInstaller();
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
	
	public boolean disconnect() {
		return this.connection.disconnect(this);
	}

	public boolean update()  { 
		return command(this.installer.update());
	}
	
	public boolean install(String commands) {
		LOG.info(this.installer == null);
		return command(this.installer.install(commands));
	}
	
	public boolean install(String flags, String commands) {
		LOG.info(this.installer == null);
//		return this.installer.install(flags, commands);		
		return command(this.installer.install(flags, commands));
	}
	
	public boolean openPort(String value) {
		LOG.info(this.installer == null);
//		return this.installer.install(flags, commands);		
		return command(this.installer.openPort(value));
	}
	
	public boolean restart(String service) {
		return command(this.installer.restart(service));
	}
	
	public boolean command(String commands) {
		return this.connection.command(commands);
	}
	
	public boolean render(String source, String dest, Context context) {
		Templaton templaton = Templaton.getInstance();
		
		File destFile = new File(dest);
		// Make sure parent directory exists for destination file
		command(String.format("mkdir -p %s", destFile.getParentFile()));
		
		String document = templaton.render(source, context).toString();
		command(String.format( "echo -e \"%s\" > %s", document.replace("\"", "\\\""), dest));
		
		return true;
	}

	public void execute(Gear gear) {
		gear.execute();
	}

}
