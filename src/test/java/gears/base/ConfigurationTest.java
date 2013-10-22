package gears.base;

import static org.junit.Assert.*;

import org.junit.Test;

public class ConfigurationTest {
	
	@Test
	public void testAddInstance() {
		
		Configuration config = new Configuration();
		Instance instance1 = new Instance("192.168.1.101", "/Users/cevaris/.ssh/id_rsa");
		config.addInstance("web", instance1);
		
		assertNotNull(config.getInstances("web"));
		assertTrue(config.getInstances("web").size() == 1);
		
		Instance instance2 = new Instance("192.168.1.102", "/Users/cevaris/.ssh/id_rsa");
		config.addInstance("web", instance2);
		
		assertNotNull(config.getInstances("web"));
		assertTrue(config.getInstances("web").size() == 2);
		
	}
	
	@Test
	public void testMultipleConnect() {
		
		Configuration config = new Configuration();
		Instance instance1 = new Instance("192.168.1.101", "/Users/cevaris/.ssh/id_rsa");
		config.addInstance("web", instance1);
		
		
		Instance instance2 = new Instance("192.168.1.102", "/Users/cevaris/.ssh/id_rsa");
		config.addInstance("web", instance2);
		
		// Connect to all instances
		config.connection.connect();
		// Test if connection was successfull
		assertTrue(config.connection.isOpen());
		
	}
	

}
