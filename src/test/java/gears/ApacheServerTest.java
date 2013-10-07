package gears;
import java.util.ArrayList;
import java.util.List;

import networking.SSHRequest;

import org.junit.*;

import base.Application;
import base.Server;
import junit.framework.TestCase;


public class ApacheServerTest extends TestCase {
	
	class ApacheApp extends Application {
		String PACKAGE_NAME = "apache2";
		
		@Override
		protected boolean update() {
			// TODO Auto-generated method stub
			return false;
		}

	}
	
	// Solidify User model
	// Encapsulate tweet import into custom rake command
	// Draft out needed tweet attributes and create Tweet Mongoid model using Embedded Documents
	// 

	class ApacheServer extends Server {
		
		List<Application> applications = new ArrayList<Application>();
		
		public ApacheServer() {
			loadCredentials();
			connect();
		}

		@Override
		protected boolean subscribe(Application app) {
			this.applications.add(app);
			return true;
		}

		@Override
		protected boolean unsubscribe(Application app) {
			this.applications.remove(app);
			return true;
		}

		@Override
		protected boolean notifySubscribers() {
			for( Application app : this.applications ){
				app.update();
			}
			return true;
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
