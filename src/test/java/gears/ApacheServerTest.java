package gears;

import networking.SSHRequest;

import org.junit.*;

import base.Application;
import base.Server;
import junit.framework.TestCase;


public class ApacheServerTest extends TestCase {
	
	class ApacheApp extends Application {
		
		public ApacheApp(Server server) {
			super(server);
		}

		String PACKAGE_NAME = "apache2";

	}
	
	class ApacheServer extends Server {
		
		public ApacheServer() {
			loadCredentials();
			connect();
		}

		
		

	}
	
	@Test
	public void testApacheServerSession(){
		Server apache = new ApacheServer();
		assertNotNull(apache.getSession());
	}
	
//	@Test
	public void testApacheServerSSHRequest(){
		Server apache = new ApacheServer();
		SSHRequest request = new SSHRequest(apache);
		
		boolean success = request.execute("ls -l /");
		assertTrue(success);
		
		String response = request.getResponse();
		assertNotNull(response);
		System.out.println(response);
	}
	
	@Test
	public void testApache(){
		Server apache = new ApacheServer();
		assertNotNull(apache.getSession());
	}
	
	

}
