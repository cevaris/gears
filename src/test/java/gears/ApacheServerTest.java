package gears;

import org.junit.*;

import base.Application;
import base.Instance;
import base.Server;
import base.ServerConfiguration;
import junit.framework.TestCase;


public class ApacheServerTest extends TestCase {
	
	public static String TEST_RESOURCES = "src/test/java/resources/";
	public static String HOSTS = TEST_RESOURCES + "hosts.yaml";
	
	class ApacheApp extends Application {
		String PACKAGE_NAME = "apache2";
		
		public ApacheApp(Server server) {
			super(server);
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
