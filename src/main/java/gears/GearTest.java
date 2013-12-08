package gears;

import static org.junit.Assert.*;
import gears.template.Templaton;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.context.Context;
import org.junit.Test;

public class GearTest {
	
	public static String TEST_RESOURCES = "src/test/java/resources/";
	public static String CONFIG_NGINX = TEST_RESOURCES + "configNginx.vm";
	
	
	class NginxGear extends Gear {
		
		public static final String PORT   = "8888";
		public static final String WEBNAME   = "blogger.com";
		
		@Override
		public void execute() {	}

		    
	}

	@Test
	public void testGetFields() {
		NginxGear nginx = new NginxGear();
		Context context = Templaton.getContext(nginx.getClass());
	}

}
