package gears.base;

import gears.base.connection.Connection;
import gears.base.pkmg.Installer;
import gears.base.template.Templaton;

import java.io.File;
import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;


abstract public class Gear  {
	
	protected String packageName = null;
	
	private static final Logger LOG = Logger.getLogger(Gear.class.getClass());
	
	protected Configuration config = null;
	
	private Installer installer = null;
	
	public abstract void execute();
	
	protected void setConfig(Configuration config) {
		this.config = config;
	}
	
	protected boolean install(String group, Gear app) {
		boolean result = true;
		for(Instance instance : this.config.getInstances(group)){
			app.setup(instance.connection, instance.installer);
			app.execute();
		}
		return result;
	}
	
	public void setup(Connection conn, Installer installer) {
		this.installer = installer;
		this.installer.setConnection(conn);
	}
	
	public boolean update()  { 
		return this.installer.update(); 
	}
	
	public boolean install(String flags, String commands) {
		LOG.info(this.installer == null);
		return this.installer.install(flags, commands);		
	}
	
	public boolean restart(String service) {
		return this.installer.restart(service);
	}
	
	public boolean render(String group, String source, String dest, VelocityContext context) {
		Templaton templaton = Templaton.getInstance();
		File destFile = new File(dest);
		
		for(Instance instance : this.config.getInstances(group)){
			setup(instance.connection, instance.installer);
			
			// Make sure parent directory exists for destination file
			this.installer.execute(String.format("mkdir -p %s", destFile.getParentFile()));
			
			String document = templaton.render(source, context).toString();
			this.installer.execute(String.format( "echo -e \"%s\" > %s", document.replace("\"", "\\\""), dest));
		}
		
		return true;
	}
	
//	public boolean render(String source, String dest, VelocityContext context) {
//		Templaton templaton = Templaton.getInstance();
//		File destFile = new File(dest);
//		
//		
//		LOG.info("Before mkdir: " + String.format("mkdir -p %s", destFile.getParentFile()));
//		LOG.info(this.installer == null);
//		this.installer.execute(String.format("mkdir -p %s", destFile.getParentFile()));
//		LOG.info("After mkdir: " + String.format("mkdir -p %s", destFile.getParentFile()));
//		
//		LOG.info("Before render");
//		String document = templaton.render(source, context).toString();
//		LOG.info("After render");
//		
//		return this.installer.execute(String.format( "echo -e \"%s\" > %s", document.replace("\"", "\\\""), dest));
//	}
	
	public boolean execute(String commands) {
		return this.installer.execute(commands);
	}
	
//	public boolean install(String[] commands, String[] flags) {
//		return this.gear.config.installer.install(
//			StringUtils.join(commands, " "), 
//			StringUtils.join(flags, " ")
//		);
//	}
//	
//	public boolean restart() {
//		return this.gear.config.installer.restart(this.packageName);
//	}
//	
//	public boolean update() {
//		return this.gear.config.installer.update();
//	}
//	

//	public boolean install(String service) {
//		return install(service, "");
//	}
	
//	public boolean remove() {
//		return this.gear.config.installer.remove(
//				new String[]{},
//				new String[]{this.packageName}
//		);
//	}
	

	
//	public void render(String source, String dest, VelocityContext context) {
//		Templaton templaton = Templaton.getInstance();
//		File destFile = new File(dest);
//		execute(String.format("mkdir -p %s", destFile.getParentFile()));
//		
//		String document = templaton.render(source, context).toString();
//		
////		execute(String.format( "echo -e \"%s\" > %s", document.replace("\"", "\\\""), dest));
//		execute(String.format( "echo -e \"%s\" > %s", document.replace("\"", "\\\""), dest));
//	}
	
//	public boolean execute(String command) {
//		System.out.println(command);
//		return false;
		
//		SSHRequest request = new SSHRequest(this.server);
//		return this.server.connection.execute(command);
//		return request.execute(command);
//		return request.execute("ls -l /");
//	}

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
