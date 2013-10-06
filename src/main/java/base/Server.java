package base;

import gears.Constant;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.RunInstancesRequest;
import com.amazonaws.services.ec2.model.RunInstancesResult;



public class Server {
	
	private String AWS_ACCESS_KEY;
	private String AWS_SECRET_KEY;
	
	public Server() {
		loadCredentials();
	}
	
	private void loadCredentials() {
		Yaml yaml = new Yaml();
		try{
			Object configInput = yaml.load(new FileInputStream(new File(Constant.CONFIG_PATH)));
			Map<String, Object> config = (Map<String, Object>)configInput;
			Map<String, String> credentials = (Map<String, String>)config.get("AWS");
			AWS_ACCESS_KEY = credentials.get("AWS_ACCESS_KEY");
			AWS_SECRET_KEY = credentials.get("AWS_SECRET_KEY");
			System.out.println(String.format("Loaded AWS credentials - %s::%s", AWS_ACCESS_KEY, AWS_SECRET_KEY));
		} catch (Exception e){
			System.err.println(e);
		}
	}

	protected boolean init(){
		RunInstancesRequest runInstancesRequest = new RunInstancesRequest();
		runInstancesRequest.withImageId("ami-4b814f22")
		                   .withInstanceType("m1.small")
		                   .withMinCount(1)
		                   .withMaxCount(1)
		                   .withKeyName("gears-test")
		                   .withSecurityGroups("YourSecurityGroupName");
		
		AWSCredentials credentials = new BasicAWSCredentials("access-key","secret-access-key");
        AmazonEC2Client ec2 = new AmazonEC2Client(credentials);
		RunInstancesResult runInstances = ec2.runInstances(runInstancesRequest);
		
		return (runInstances != null) ? true : false;
		  
	}

}
