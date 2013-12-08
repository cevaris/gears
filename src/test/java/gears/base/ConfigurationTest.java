package gears.base;

import static org.junit.Assert.*;
import gears.Configuration;
import gears.Instance;
import gears.connection.SSHConnection;
import gears.pkmg.DebianInstaller;

import org.junit.Test;

public class ConfigurationTest {
	
	private final static String SSH_KEY = "/Users/cevaris/Documents/workspace/gears/gears/keys/id_rsa";
	
	@Test
	public void testAddInstance() {
		
		Configuration config = Configuration.getInstance();
		Instance instance1 = new Instance("192.168.2.100", SSH_KEY, new SSHConnection(), new DebianInstaller());
		config.addInstance("web", instance1);
		
		Instance instance2 = new Instance("192.168.2.101", SSH_KEY, new SSHConnection(), new DebianInstaller());
		config.addInstance("web", instance2);
		
		for(Instance instance : config.getInstances("web")){
			assertNotNull(instance.getFQDN());
			assertNotNull(instance.getSSHPermKeyPath());
			assertTrue(instance.connect());
		}
		
		for(Instance instance : config.getInstances()){
			assertNotNull(instance.getFQDN());
			assertNotNull(instance.getSSHPermKeyPath());
			assertTrue(instance.connect());
		}
		
		config = Configuration.getInstance();
		
		assertTrue(config.getInstances().size() == 2);
	}

}
