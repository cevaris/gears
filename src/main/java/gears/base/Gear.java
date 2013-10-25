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
	
	private Installer installer   = null;
	private Connection connection = null;
	
	public abstract void execute();
	
	/**
	 * Internal execution
	 * @param conn
	 * @param installer
	 */
	private void execute(Connection conn, Installer installer){
		this.setConnection(conn);
		this.setInstaller(installer);
		this.execute();
	}
	
	private void setConnection(Connection connection) {
		this.connection = connection;
	}
	
	private void setInstaller(Installer installer) {
		this.installer = installer;
	}
	
	public void setConfig(Configuration config) {
		this.config = config;
	}
	
	protected boolean install(String group, Gear app) {
		boolean result = true;
		for(Instance instance : this.config.getInstances(group)){
			app.execute(instance.connection, instance.installer);
		}
		return result;
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
	
	public boolean render(String gearGroup, String source, String dest, VelocityContext context) {
		Templaton templaton = Templaton.getInstance();
		File destFile = new File(dest);
		
		for(Instance instance : this.config.getInstances(gearGroup)){
			this.setConnection(instance.connection);
			
			// Make sure parent directory exists for destination file
			command(String.format("mkdir -p %s", destFile.getParentFile()));
			
			String document = templaton.render(source, context).toString();
			command(String.format( "echo -e \"%s\" > %s", document.replace("\"", "\\\""), dest));
		}
		
		return true;
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
