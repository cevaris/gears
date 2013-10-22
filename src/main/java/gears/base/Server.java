package gears.base;

import gears.Constant;
import gears.base.connection.ConnectionFactory;
import gears.base.connection.SSHConnection;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.security.Security;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.schmizz.sshj.Config;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.connection.channel.direct.Session.Command;
import net.schmizz.sshj.transport.TransportException;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;
import net.schmizz.sshj.userauth.UserAuthException;
import net.schmizz.sshj.userauth.keyprovider.PKCS8KeyFile;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;



abstract public class Server {
	
	Logger LOG = Logger.getLogger(Server.class.getClass());
	
	Connection connection = null;
	Installer  installer  = null;
	
	protected List<Application> applications = new ArrayList<Application>();
	
	protected ServerConfiguration config;
	
	protected boolean notifySubscribers() {
		
		assert(this.connection != null) : "Connection is not defined";
		if(!this.connection.isOpen()) this.connection.connect();
		
		System.out.println("Is sever open:" + this.connection.isOpen());
		
		for( Application app : this.applications ){
			app.execute(); //TODO: install app if not installed
		}
		return true;
	}
	protected boolean subscribe(Application app) {
		this.applications.add(app);
		return true;
	}

	protected boolean unsubscribe(Application app) {
		this.applications.remove(app);
		return true;
	}
	
	protected void setInstaller(Installer installer) {
		this.installer = installer;		
	}

	protected void setConnection(Connection connection) {
		this.connection = connection;
	}

	protected void setConfig(ServerConfiguration config) {
		this.config = config;
	}


//	private void loadCredentials(String configPath) {
//		Yaml yaml = null;
//		Object configDocument = null;
//		Map<?, ?> config = null;
//		
//		try{
//			yaml = new Yaml();
//			configDocument = yaml.load(new FileInputStream(new File(configPath)));
//			config = (Map<?, ?>) configDocument;
//		} catch (Exception e){
//			LOG.error("Syntax error in configuration file", e);
//		}
//		
//		try{
//			this.nodes = (List<?>)config.get("NODES");
//		} catch (ClassCastException e){
//			LOG.error("Invalid node configuration. Define list of IP Address or hostnames, e.g, NODES: [191.168.1.101, 191.168.1.101]", e);
//		}
//		
//		try{
//			this.sshKey = (String)config.get("SSH_KEY");
//		} catch (ClassCastException e){
//			LOG.error("Invalid SSH Key configuration. Define path of SSH private key.", e);
//		}
//		
//		System.out.println(String.format("Loaded Node Credentials - %s::%s", this.nodes, this.sshKey));
//	}
	
	
}
