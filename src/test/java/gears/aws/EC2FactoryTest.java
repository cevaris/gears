package gears.aws;

import static org.junit.Assert.*;

import org.junit.Test;

import com.amazonaws.services.ec2.model.Instance;

public class EC2FactoryTest {

	@Test
	public void testGetInstance() {
		
		EC2Factory ec2Factory = EC2Factory.getInstance();
		
		Instance value = ec2Factory.getEC2Instance();
		assertNotNull(value);
		assertNotNull(value.getInstanceId());	
		
		ec2Factory.terminate(value.getInstanceId());
		
		
		

	}

}
