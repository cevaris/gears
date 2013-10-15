package com.template;

import static org.junit.Assert.*;

import org.junit.Test;

import template.Templaton;

public class TemplateTest {
	
	public static String TEST_RESOURCES = "src/test/java/resources/";
	public static String CONFIG_NGINX = TEST_RESOURCES + "configNginx.vm";

	
	
	@Test
	public void testTemplatonNginx() {
		
		Templaton template = new Templaton(CONFIG_NGINX);
		
        try {
        	template.put("port",  "8888");
        	template.put("webName", "example.com" );
        	template.put("projectPath", "/var/www/my_awesome_rails_app/public");
            assertTrue(template.render().toString().length() > 0);
            System.out.println(template.render());
        } catch( Exception e ) {
          System.err.println("Exception caught: " + e.getMessage());
        }

	}

}
