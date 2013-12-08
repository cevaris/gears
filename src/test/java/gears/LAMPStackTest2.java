package gears;

import static org.junit.Assert.*;
import gears.template.Template;
import gears.template.Templaton;

import org.apache.velocity.VelocityContext;
import org.junit.Test;


public class LAMPStackTest2 {

	public static String TEST_RESOURCES = "src/test/java/resources/";
	
	public static String INFO = TEST_RESOURCES + "info.php.vm";
	public static String MY_CNF = TEST_RESOURCES + "my.cnf.vm";
	
	
	class PHPApp extends Gear {
		
		@Override
		public void execute() {
			// Update application repository
//			update();

			// Install misc apps
			install("-y", "php5");
		}
		    
	}
	
	class ApacheApp extends Gear {
		
		@Override
		public void execute() {
			// Update application repository
//			update();
			
			// Install misc apps
			install( "-y", "apache2 libapache2-mod-php5 php5-mcrypt" );
	
			// Restart Apache service, equals to "service apache2 restart"
			restart("apache2");
		}

	}
	
	class MySQLApp extends Gear {
		
		/**
		 * For MySQL config file
		 * http://stackoverflow.com/questions/1167056/optimal-mysql-configuration-my-cnf
		 */
		public static final String MYSQL_PASS = "mypass";
		public static final String MYSQL_USER = "root";
		
		public static final String PORT = "3306";
		public static final String IP_ADDRESS = "192.168.2.101";

		@Override
		public void execute() {
//			update();
			
			// Hack for automating Mysql install
			command(String.format("echo mysql-server-5.5 mysql-server/root_password password %s | debconf-set-selections", MYSQL_PASS));
			command(String.format("echo mysql-server-5.5 mysql-server/root_password_again password %s | debconf-set-selections", MYSQL_PASS));
			
			// Install misc apps
			install( "-y", "mysql-server php5-mysql php5 php5-mcrypt" );
			
			renderConfig();
			
			// Grant Remote access
			command("mysql -u root --password='mypass' -e \"GRANT ALL PRIVILEGES ON *.* TO 'root'@'192.168.1.%' IDENTIFIED BY 'mypass' WITH GRANT OPTION; FLUSH PRIVILEGES;\"");
			command("mysql -u root --password='mypass' -e \"GRANT ALL PRIVILEGES ON *.* TO 'root'@'nod%' IDENTIFIED BY 'mypass' WITH GRANT OPTION; FLUSH PRIVILEGES;\"");
			
			openPort("3306");
			
			restart("mysql");
			
		}


		private void renderConfig(){
			render(MY_CNF, "/etc/mysql/my.cnf");
		}

	}
	
	
	class ProductionLAMP {
		
		private final static String SSH_KEY = "/Users/cevaris/Documents/workspace/gears/gears/keys/id_rsa";
		
		private Configuration config = Configuration.getInstance();
		
		public ProductionLAMP() {
			Instance instance1 = new Instance("192.168.2.100", SSH_KEY);
			config.addInstance("web", instance1);
			
			Instance instance2 = new Instance("192.168.2.101", SSH_KEY);
			config.addInstance("db", instance2);
		}
	}
	
	
	class LAMPStackServer extends Gear {
		
		Gear mysql  = new MySQLApp();
		Gear php    = new PHPApp();
		Gear apache = new ApacheApp();
		
		public LAMPStackServer() {
			new ProductionLAMP();
		}

		@Override
		public void execute() {
//			install("web", php);
//			install("web", apache);
			
			install("db", mysql);
			
			renderInfo();
		}
		
		private void renderInfo(){
        	render("web", INFO, "/var/www/info.php");
		}
		
	}
	
	@Test
	public void testLAMPServer(){
		Gear server = new LAMPStackServer();
		server.execute();
	}

	

}
