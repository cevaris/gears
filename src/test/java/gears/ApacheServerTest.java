package gears;

import static org.junit.Assert.assertTrue;

import org.apache.velocity.VelocityContext;
import org.junit.*;

import template.Templaton;

import base.Application;
import base.Instance;
import base.Server;
import base.ServerConfiguration;
import junit.framework.TestCase;


public class ApacheServerTest extends TestCase {
	
	public static String TEST_RESOURCES = "src/test/java/resources/";
	public static String HOSTS = TEST_RESOURCES + "hosts.yaml";
	public static String INFO = TEST_RESOURCES + "info.php.vm";
	
	class ApacheApp extends Application {
		String PACKAGE_NAME = "apache2";
		
		String MYSQL_PASS   = "mypass";
		String MYSQL_USER   = "root";
		
		public ApacheApp(Server server) {
			super(server);
//			init();
			renderInfo();
		}

		private void init() {
			// Update apt-get
			update();

			// Silent terminal
			execute("export DEBIAN_FRONTEND=noninteractive");

			// Install misc apps
			install(new String[]{PACKAGE_NAME, "mysql-server","php5-mysql", "php5", "libapache2-mod-php5", "php5-mcrypt"}, 
					new String[]{"-q","-y"});

			// Define Mysql password
			execute(String.format("mysqladmin -u root password %s", MYSQL_PASS));
			
			renderInfo();
			
			// Restart Apache service, equals to "service apache2 restart"
			restartService(PACKAGE_NAME);
		}
		
		
		private void renderInfo(){
			VelocityContext context = Templaton.getContext();
        	context.put("MYSQL_PASS", MYSQL_PASS);
        	context.put("MYSQL_USER", MYSQL_USER );
			render(INFO, "/var/www/info.php", context);
		}
		
		    
	}
	
	class ApacheServer extends Server {
		
		class ProductionApache extends ServerConfiguration {
			public ProductionApache() {
				Instance apacheWeb = new Instance();
				apacheWeb.setFQDN("192.168.1.101");
				apacheWeb.setSSHPermKeyPath("/Users/cevaris/.ssh/id_rsa");
				addInstance(apacheWeb);
			}
		}
		
		public ApacheServer() {
			this.config = new ProductionApache();
		}

	}
	
	@Test
	public void testApacheServerSession(){
		Server apache = new ApacheServer();
		apache.execute();
//		assertNotNull(apache.getSession());
	}
	
	@Test
	public void testApacheApp(){
		Server server   = new ApacheServer();
		Application app = new ApacheApp(server);
//		assertNotNull(apache.getSession());
	}
//	
////	@Test
//	public void testApacheServerSSHRequest(){
//		Server apache = new ApacheServer();
//		SSHRequest request = new SSHRequest(apache);
//		
//		boolean success = request.execute("ls -l /");
//		assertTrue(success);
//		
//		String response = request.getResponse();
//		assertNotNull(response);
//		System.out.println(response);
//	}
//	
//	@Test
//	public void testApache(){
//		Server apache = new ApacheServer();
//		assertNotNull(apache.getSession());
//	}
	
	

}
