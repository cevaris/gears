package gears.base;

import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;


 public abstract class Gear implements Pangaea {
	
	protected String gearGroup = null;
	
	private static final Logger LOG = Logger.getLogger(Gear.class.getClass());
	
	protected Configuration config = null;
	
//	private Installer installer   = null;
//	private Connection connection = null;
	
	public abstract void execute();
	
	/**
	 * Internal execution
	 * @param conn
	 * @param installer
	 */
	private void execute(Instance instance){
		this.execute();
	}
	
//	private void setConnection(Connection connection) {
//		this.connection = connection;
//	}
//	
//	private void setInstaller(Installer installer) {
//		this.installer = installer;
//	}
	
	public void setConfig(Configuration config) {
		this.config = config;
	}
	
	public void setGearGroup(String gearGroup) {
		this.gearGroup = gearGroup;
	}
	
	protected boolean install(String group, Gear app) {
		boolean result = true;
		for(Instance instance : this.config.getInstances(group)){
			app.execute(instance);
		}
		return result;
	}
	
	
	public boolean update() {
		assert this.gearGroup != null : "Gear Group not defined";
		for(Instance instance : this.config.getInstances(this.gearGroup)){
			instance.update();
		}
		return true;
	}
	
	
	public boolean install(String flags, String commands) {
		assert this.gearGroup != null : "Gear Group not defined";
		for(Instance instance : this.config.getInstances(this.gearGroup)){
			instance.install(flags, commands);
		}
		return true;
	}
	

	public boolean restart(String service) {
		assert this.gearGroup != null : "Gear Group not defined";
		for(Instance instance : this.config.getInstances(this.gearGroup)){
			instance.restart(service);
		}
		return true;
	}

	public boolean command(String commands) {
		assert this.gearGroup != null : "Gear Group not defined";
		for(Instance instance : this.config.getInstances(this.gearGroup)){
			instance.command(commands);
		}
		return true;
	}

	public boolean render(String source, String dest, VelocityContext context) {
		assert this.gearGroup != null : "Gear Group not defined";
		for(Instance instance : this.config.getInstances(this.gearGroup)){
			instance.render(source, dest, context);
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
////	
//
//	public boolean install(String service) {
//		return install(service, "");
//	}
//	
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
