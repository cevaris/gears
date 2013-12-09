package org.gears.aws;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


import org.apache.log4j.Logger;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.RunInstancesRequest;
import com.amazonaws.services.ec2.model.RunInstancesResult;
import com.amazonaws.services.ec2.model.TerminateInstancesRequest;
import com.amazonaws.services.ec2.model.TerminateInstancesResult;

public class EC2Factory {
	
	
	private static final Logger LOG = Logger.getLogger(EC2Factory.class.getClass());
	
	
	private static InputStream _credentialsAsStream;
	private static AWSCredentials _credentials;
	private static AmazonEC2 _ec2;
	
	private static EC2Factory instance = null;
	
	
	
	public static EC2Factory getInstance() {
		if(instance == null) {
			instance = new EC2Factory();
			authenticate();
		}
		
		return instance;
	}
	
	private static void authenticate() {
		
		try {
			_credentialsAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("aws.properties");
			_credentials = new PropertiesCredentials(_credentialsAsStream);
			
			_ec2 = new AmazonEC2Client(_credentials);
			_ec2.setEndpoint("ec2.eu-west-1.amazonaws.com");
			
			LOG.info(String.format("Loaded format %s %s",
					_credentials.getAWSAccessKeyId(), _credentials.getAWSSecretKey()));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public TerminateInstancesResult terminate(String instanceId) {
		TerminateInstancesRequest rq = new TerminateInstancesRequest();
		rq.getInstanceIds().add(instanceId);
		return _ec2.terminateInstances(rq);
	}
	
	
	public static Instance getEC2Instance(){
		
		
		
		RunInstancesRequest runInstancesRequest = new RunInstancesRequest()
	    .withInstanceType("t1.micro")
	    .withImageId("ami-967edcff")
	    .withMinCount(2)
	    .withMaxCount(2)
	    .withSecurityGroupIds("default")
	    .withKeyName("ec2_key");
//	    .withUserData(Base64.encodeBase64String(myUserData.getBytes()));
	
		RunInstancesResult runInstances = _ec2.runInstances(runInstancesRequest);
		
		// TAG EC2 INSTANCES
		List<Instance> instances = runInstances.getReservation().getInstances();
		
		return instances.get(0);
//		int idx = 1;
//		
//		for (Instance instance : instances) {
//		  CreateTagsRequest createTagsRequest = new CreateTagsRequest();
//		  createTagsRequest.withResources(instance.getInstanceId()) //
//		      .withTags(new Tag("Name", "travel-ecommerce-" + idx));
//		  ec2.createTags(createTagsRequest);
//		
//		  idx++;
//		}
//		
//		return ec2;
			
		
	}
	
	
	

//InputStream credentialsAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("AwsCredentials.properties");
//Preconditions.checkNotNull(credentialsAsStream, "File 'AwsCredentials.properties' NOT found in the classpath");
//AWSCredentials credentials = new PropertiesCredentials(credentialsAsStream);
//
//AmazonEC2 ec2 = new AmazonEC2Client(credentials);
//ec2.setEndpoint("ec2.eu-west-1.amazonaws.com");
//
//// CREATE EC2 INSTANCES
//RunInstancesRequest runInstancesRequest = new RunInstancesRequest()
//    .withInstanceType("t1.micro")
//    .withImageId("ami-62201116")
//    .withMinCount(2)
//    .withMaxCount(2)
//    .withSecurityGroupIds("tomcat")
//    .withKeyName("xebia-france")
//    .withUserData(Base64.encodeBase64String(myUserData.getBytes()))
//;
//
//RunInstancesResult runInstances = ec2.runInstances(runInstancesRequest);
//
//// TAG EC2 INSTANCES
//List<Instance> instances = runInstances.getReservation().getInstances();
//int idx = 1;
//for (Instance instance : instances) {
//  CreateTagsRequest createTagsRequest = new CreateTagsRequest();
//  createTagsRequest.withResources(instance.getInstanceId()) //
//      .withTags(new Tag("Name", "travel-ecommerce-" + idx));
//  ec2.createTags(createTagsRequest);
//
//  idx++;
//}

}
