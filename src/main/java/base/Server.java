package base;

import gears.Constant;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.Security;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.transport.TransportException;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;
import net.schmizz.sshj.userauth.UserAuthException;
import net.schmizz.sshj.userauth.keyprovider.PKCS8KeyFile;

import org.apache.log4j.Logger;
import org.yaml.snakeyaml.Yaml;



abstract public class Server {
	
	Logger LOG = Logger.getLogger(Server.class.getClass());
	
	private List<?> nodes;
	private String sshKey;
	
	private String hostsPath;
	
	protected SSHClient client;
	protected Session session;
	protected boolean isSSHConnected;
	
	protected List<Application> applications = new ArrayList<Application>();
	protected ServerConfiguration config;
	
	protected boolean notifySubscribers() {
		for( Application app : this.applications ){
			app.update(); //TODO: install app if not installed
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

	public Session getSession() {
		if(this.session == null) connect();
		return this.session;
	}
	
	protected boolean connect() {
		
		assert(this.hostsPath != null) : "Hosts path is not set";
		if((this.session != null) && this.session.isOpen()) return false;
		
		loadCredentials(this.hostsPath);
		
		try {
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			this.client = new SSHClient();
			client.addHostKeyVerifier(new PromiscuousVerifier());
			client.connect("ec2-54-224-80-192.compute-1.amazonaws.com");
			
			
			PKCS8KeyFile keyFile = new PKCS8KeyFile();
			keyFile.init(new File(AWS_SSH_KEY));
			client.authPublickey("ubuntu",keyFile);
			
			this.session = client.startSession();
			
//			final Command cmd = session.exec("sudo apt-get update");
//            
//        	InputStream channel = cmd.getInputStream();
//        	
//        	StringWriter writer = new StringWriter();
//        	IOUtils.copy(channel, writer);
//        	String theString = writer.toString();
//        	
//        	LOG.info(String.format("%s",theString));
//            
//			this.client.close();
//			this.session.close();
            
            
		} catch (TransportException e){
			LOG.error(e);
		} catch (UserAuthException e){
			LOG.error(e);
		} catch (IOException e){
			LOG.error(e);
		} finally {
			
		}
		
		return true;
		
	}

	private void loadCredentials(String configPath) {
		Yaml yaml = null;
		Object configDocument = null;
		Map<?, ?> config = null;
		
		try{
			yaml = new Yaml();
			configDocument = yaml.load(new FileInputStream(new File(configPath)));
			config = (Map<?, ?>) configDocument;
		} catch (Exception e){
			LOG.error("Syntax error in configuration file", e);
		}
		
		try{
			this.nodes = (List<?>)config.get("NODES");
		} catch (ClassCastException e){
			LOG.error("Invalid node configuration. Define list of IP Address or hostnames, e.g, NODES: [191.168.1.101, 191.168.1.101]", e);
		}
		
		try{
			this.sshKey = (String)config.get("SSH_KEY");
		} catch (ClassCastException e){
			LOG.error("Invalid SSH Key configuration. Define path of SSH private key.", e);
		}
		
		System.out.println(String.format("Loaded Node Credentials - %s::%s", this.nodes, this.sshKey));
	}

	

}
