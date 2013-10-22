package gears;

import static org.junit.Assert.*;
import gears.LAMPStackTest.ApacheApp;
import gears.LAMPStackTest.ApacheServer;
import gears.LAMPStackTest.ApacheServer.ProductionApache;
import gears.base.Application;
import gears.base.Connection;
import gears.base.Installer;
import gears.base.InstallerFactory;
import gears.base.Instance;
import gears.base.Gear;
import gears.base.Configuration;
import gears.base.connection.ConnectionFactory;

import org.apache.velocity.VelocityContext;
import org.junit.Test;

import template.Templaton;

public class LAMPStackTest2 {

	public static String TEST_RESOURCES = "src/test/java/resources/";
	public static String HOSTS = TEST_RESOURCES + "hosts.yaml";
	public static String INFO = TEST_RESOURCES + "info.php.vm";
	
	
	class PHPApp extends Application {
		
		public PHPApp(Gear server) {
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
		
		public ApacheApp(Gear server) {
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
		
		public MySQLApp(Gear server) {
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
	
	class ProductionLAMP extends Configuration {
		public ProductionLAMP() {
			Instance server1 = new Instance("192.168.1.101", "/Users/cevaris/.ssh/id_rsa");
			addInstance(server1);
			
			Instance server2 = new Instance("192.168.1.102", "/Users/cevaris/.ssh/id_rsa");
			addInstance(server2);
		}
	}
	
	class LAMPStackServer extends Gear {
		
		Application mysql = null;
		Application php = null;
		Application apache = null;
		
		public LAMPStackServer() {
			
			Configuration config = new ProductionLAMP();
			setConfig(config);
			
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
		Gear server   = new LAMPStackServer();
//		Application app = new MySQLApp(server);
//		assertNotNull(apache.getSession());
	}

}
