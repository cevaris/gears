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

public class MySQLTest {

	public static String TEST_RESOURCES = "src/test/java/resources/";
	public static String HOSTS = TEST_RESOURCES + "hosts.yaml";
	public static String INFO = TEST_RESOURCES + "info.php.vm";
	
	class MySQLApp extends Application {
		
		String MYSQL_PASS   = "mypass";
		String MYSQL_USER   = "root";
		String INNODB_BUFFER_POOL_SIZE = "512M";
		String INNODB_LOG_FILE_SIZE = "128M";
		
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
	
	class DBServer extends Server {
		
		class ProductionMySQL extends ServerConfiguration {
			public ProductionMySQL() {
				Instance dbServer = new Instance();
				dbServer.setFQDN("192.168.1.101");
				dbServer.setSSHPermKeyPath("/Users/cevaris/.ssh/id_rsa");
				addInstance(dbServer);
			}
		}
		
		public DBServer() {
			this.config = new ProductionMySQL();
			
			this.applications.add(new MySQLApp(this));
			this.execute();
		}

	}
	
//	@Test
//	public void testApacheServerSession(){
//		Server apache = new ApacheServer();
//		apache.execute();
////		assertNotNull(apache.getSession());
//	}
	
	@Test
	public void testDBServer(){
		Server server   = new DBServer();
//		Application app = new MySQLApp(server);
//		assertNotNull(apache.getSession());
	}

}
