package com.template;

import static org.junit.Assert.*;

import java.io.StringWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.junit.Test;

public class TemplateTest {
	
	public static String TEST_RESOURCES = "src/test/java/resources/";
	public static String CONFIG_NGINX = TEST_RESOURCES + "configNginx.vm";

	@Test
	public void testConfigNginx() {
		
		VelocityContext context = new VelocityContext();
		Template template = null;
		
		System.out.println("Working Directory = " +
	              System.getProperty("user.dir"));
		
        try {
            context.put("port",  "80");
            context.put("webName", "example.com" );
            context.put("projectPath", "/var/www/my_awesome_rails_app/public");
            template = Velocity.getTemplate(CONFIG_NGINX);
            assertNotNull(template.getData());
            
            StringWriter writer = new StringWriter();
            template.merge(context, writer);
            System.out.println(writer);
        } catch( Exception e ) {
          System.err.println("Exception caught: " + e.getMessage());
        }

	}

}
