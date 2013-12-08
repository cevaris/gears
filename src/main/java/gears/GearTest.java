package gears;

import static org.junit.Assert.*;

import java.io.File;

import gears.template.Templaton;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.context.Context;
import org.junit.Test;

public class GearTest {
	
	public static String TEST_RESOURCES = "src/test/java/resources/";
	public static String CONFIG_NGINX = TEST_RESOURCES + "configNginx.vm";
	
	
	class NginxGear extends Gear {
		
		protected static final String PORT    = "8888";
		protected static final String WEB_NAME = "blogger.com";
		protected static final String PROJECT_PATH = "/var/www/test";
		
		@Override
		public void execute() {
			System.out.println(this.getClass().getName());
		}

		    
	}
	
	@Test
	public void testGetFields() {
		Gear nginx = new NginxGear();
		
		Templaton templaton = Templaton.getInstance();
		Context context = Templaton.getContext(nginx);
		String document = templaton.render(CONFIG_NGINX, context).toString();
		
		System.out.println(document);
	}

}
