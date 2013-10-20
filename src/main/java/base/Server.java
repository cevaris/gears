package base;

import gears.Constant;

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
import org.yaml.snakeyaml.Yaml;



abstract public class Server {
	
	Logger LOG = Logger.getLogger(Server.class.getClass());
	
	protected SSHClient client;
	protected Session session;
	
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

//	public Session getSession() {
//		if(this.session == null) connect();
//		return this.session;
//	}
	public SSHClient getClient() {
		if(this.client == null) connect();
		return this.client;
	}
	
	protected boolean connect() {
		boolean status = true;
		for(Instance instance : config.getInstances()){
			status = status && connect(instance);
		}
		return status;
	}
	
	private boolean connect(Instance instance) {
		
		assert(instance != null) : "Instance is null";
		
		try {
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			this.client = new SSHClient();
			this.client.addHostKeyVerifier(new PromiscuousVerifier());
			this.client.connect(instance.getFQDN());
			
			PKCS8KeyFile keyFile = new PKCS8KeyFile();
			keyFile.init(new File(instance.getSSHPermKeyPath()));
			this.client.authPublickey("root",keyFile);
			
			return true;
			
//			this.session = this.client.startSession();
			
//			return this.session.isOpen();
			
//			final Command cmd = this.session.exec("ls -l /");
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
		}
		
		return false;
		
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
	
	
	public void execute() {
		connect();
	}

	

}
