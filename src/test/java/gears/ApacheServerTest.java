package gears;
import networking.SSHRequest;

import org.junit.*;

import base.Application;
import base.Server;
import junit.framework.TestCase;


public class ApacheServerTest extends TestCase {
	
	class ApacheApp extends Application {
		String PACKAGE_NAME = "apache2";
	}

	class ApacheServer extends Server {
		
		public ApacheServer() {
			loadCredentials();
			connect();
		}

		@Override
		protected boolean install() {
			
			return false;
		}

		@Override
		protected boolean update() {
			return false;
		}
		
	}
	
	@Test
	public void testApacheServerSession(){
		Server apache = new ApacheServer();
		assertNotNull(apache.getSession());
	}
	
	@Test
	public void testApacheServerSSHRequest(){
		Server apache = new ApacheServer();
		SSHRequest request = new SSHRequest(apache);
		
		boolean success = request.execute("ls -l /");
		assertTrue(success);
		
		String response = request.getResponse();
		assertNotNull(response);
		System.out.println(response);
		
	}
	
	

}
