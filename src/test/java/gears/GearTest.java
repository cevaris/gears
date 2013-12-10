package gears;

import static org.junit.Assert.*;

import java.io.File;


import org.apache.velocity.VelocityContext;
import org.apache.velocity.context.Context;
import org.gears.Configuration;
import org.gears.Gear;
import org.gears.Instance;
import org.gears.template.Templaton;
import org.junit.Test;

public class GearTest {
	
	public static String TEST_RESOURCES = "src/test/java/resources/";
	public static String CONFIG_NGINX = TEST_RESOURCES + "configNginx.vm";
	
	
	class NginxGear extends Gear {
		
		protected static final String PORT    = "8888";
		protected static final String WEB_NAME = "blogger.com";
		protected static final String PROJECT_PATH = "/var/www/test";
		
		public NginxGear() {
			new ProductionConfig();
		}
		
		@Override
		public void execute() {
			render(CONFIG_NGINX, "/tmp/nginx.conf");
		}
		    
	}
	
	class ProductionConfig {
		
		private final static String SSH_KEY = "/Users/cevaris/Documents/workspace/gears/gears/keys/id_rsa";
		private Configuration config = Configuration.getInstance();
		
		public ProductionConfig() {
			Instance instance1 = new Instance("192.168.2.100", SSH_KEY);
			config.addInstance("web", instance1);
			
//			Instance instance2 = new Instance("192.168.2.101", SSH_KEY);
//			config.addInstance("web", instance2);
		}
	}
	

	@Test
	public void testDynamicContext() {
		Gear nginx = new NginxGear();
		Templaton templaton = Templaton.getInstance();
		Context context = Templaton.getContext("nginx",nginx);
		String document = templaton.render(CONFIG_NGINX, context).toString();
		assertTrue(document != null);
		assertTrue(document.length() > 0);
	}
	
	@Test
	public void testDynamciRender() {
		Gear nginx = new NginxGear();
		nginx.execute();
		boolean result = nginx.command("cat /tmp/nginx.conf");
		assertTrue(result);
	}

}
