package gears;

import static org.junit.Assert.*;
import gears.LAMPStackTest.ApacheApp;
import gears.LAMPStackTest.ApacheServer;
import gears.LAMPStackTest.ApacheServer.ProductionApache;

import org.apache.velocity.VelocityContext;
import org.junit.Test;

import template.Templaton;
import base.Application;
import base.Instance;
import base.Server;
import base.ServerConfiguration;

public class LAMPStackTest2 {

	public static String TEST_RESOURCES = "src/test/java/resources/";
	public static String HOSTS = TEST_RESOURCES + "hosts.yaml";
	public static String INFO = TEST_RESOURCES + "info.php.vm";
	
	
	class PHPApp extends Application {
		
		public PHPApp(Server server) {
			super(server);
		}

		@Override
		protected void execute() {
			// Update application repository
			update();

			// Install misc apps
			install(new String[]{"php5"}, new String[]{"-q","-y"});
		}
		    
	}
	
	class ApacheApp extends Application {
		
		
		
		public ApacheApp(Server server) {
			super(server);
		}
		
		@Override
		protected void execute() {
			// Update application repository
			update();
			
			// Install misc apps
			install(new String[]{"apache2", "libapache2-mod-php5", "php5-mcrypt"}, 
					new String[]{"-q","-y"});
	
			// Restart Apache service, equals to "service apache2 restart"
			restart("apache2");
		}
		    
	}
	
	class MySQLApp extends Application {
		
		/**
		 * For MySQL config file
		 * http://stackoverflow.com/questions/1167056/optimal-mysql-configuration-my-cnf
		 */
		public static final String MYSQL_PASS   = "mypass";
		public static final String MYSQL_USER   = "root";
		
		public static final String INNODB_BUFFER_POOL_SIZE = "512M";
		public static final String INNODB_LOG_FILE_SIZE = "128M";
		
		public MySQLApp(Server server) {
			super(server);
		}
		
		@Override
		protected void execute() {
			update();
			
			// Hack for automating Mysql install
			execute(String.format("echo mysql-server-5.5 mysql-server/root_password password %s | debconf-set-selections", MYSQL_PASS));
			execute(String.format("echo mysql-server-5.5 mysql-server/root_password_again password %s | debconf-set-selections", MYSQL_PASS));
			
			// Install misc apps
			install(new String[]{"mysql-server", "php5-mysql", "php5", "php5-mcrypt"}, 
					new String[]{"-q","-y"});
			
		}
		
//		private void renderConfig(){
//			VelocityContext context = Templaton.getContext();
//        	context.put("MYSQL_PASS", MYSQL_PASS);
//        	context.put("MYSQL_USER", MYSQL_USER );
//			render(INFO, "/var/www/info.php", context);
//		}
		    
	}
	
	class LAMPStackServer extends Server {
		

		Application mysql = null;
		Application php = null;
		Application apache = null;
		
		class ProductionLAMP extends ServerConfiguration {
			public ProductionLAMP() {
				Instance server = new Instance();
				server.setFQDN("192.168.1.103");
				server.setSSHPermKeyPath("/Users/cevaris/.ssh/id_rsa");
				
				addInstance(server);
			}
		}
		
		public LAMPStackServer() {
			this.config = new ProductionLAMP();
			
			subscribe(php    = new PHPApp(this));
			subscribe(mysql  = new MySQLApp(this));
			subscribe(apache = new ApacheApp(this));
			
//			renderInfo();
			
			notifySubscribers();
		}
		
//		private void renderInfo(){
//			VelocityContext context = Templaton.getContext();
//        	context.put("MYSQL_PASS", MySQLApp.MYSQL_PASS);
//        	context.put("MYSQL_USER", MySQLApp.MYSQL_USER );
//			render(INFO, "/var/www/info.php", context);
//		}

	}
	
//	@Test
//	public void testApacheServerSession(){
//		Server apache = new ApacheServer();
//		apache.execute();
////		assertNotNull(apache.getSession());
//	}
	
	@Test
	public void testLAMPServer(){
		Server server   = new LAMPStackServer();
//		Application app = new MySQLApp(server);
//		assertNotNull(apache.getSession());
	}

}