package base;

import gears.Constant;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.Security;
import java.util.Map;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.common.SSHException;
import net.schmizz.sshj.transport.TransportException;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;
import net.schmizz.sshj.userauth.UserAuthException;
import net.schmizz.sshj.userauth.keyprovider.PKCS8KeyFile;

import org.apache.log4j.Logger;
import org.yaml.snakeyaml.Yaml;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.InstanceType;
import com.amazonaws.services.ec2.model.RunInstancesRequest;
import com.amazonaws.services.ec2.model.RunInstancesResult;



public class Server {
	
	Logger LOG = Logger.getLogger(Server.class.getClass());
	
	private String AWS_ACCESS_KEY;
	private String AWS_SECRET_KEY;
	private String AWS_SSH_KEY;
	
	public Server() {
		loadCredentials();
		Boolean isStarted = init();
		LOG.info(String.format("Got EC2 running: %s", isStarted));
//		connect();
	}
	
	private boolean connect() {
		
		try {
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			SSHClient client = new SSHClient();
			client.addHostKeyVerifier(new PromiscuousVerifier());
			client.connect("ec2-XXX-XXX-XXX-XXX.compute-1.amazonaws.com");
			
			PKCS8KeyFile keyFile = new PKCS8KeyFile();
			keyFile.init(new File("/dev/ec2/key/mykey.pem"));
			client.authPublickey("ubuntu",keyFile);
		} catch (TransportException e){
			
		} catch (UserAuthException e){
			
		} catch (IOException e){
			
		}
		
		return true;
		
	}

	private void loadCredentials() {
		Yaml yaml = new Yaml();
		try{
			Object configInput = yaml.load(new FileInputStream(new File(Constant.CONFIG_PATH)));
			Map<String, Object> config = (Map<String, Object>)configInput;
			Map<String, String> credentials = (Map<String, String>)config.get("AWS");
			AWS_ACCESS_KEY = credentials.get("AWS_ACCESS_KEY");
			AWS_SECRET_KEY = credentials.get("AWS_SECRET_KEY");
			AWS_SSH_KEY    = credentials.get("AWS_SSH_KEY");
			System.out.println(String.format("Loaded AWS credentials - %s::%s::", AWS_ACCESS_KEY, AWS_SECRET_KEY, AWS_SSH_KEY));
		} catch (Exception e){
			System.err.println(e);
		}
	}

	
	/**
	 * Connecting with EC2
	 * http://stackoverflow.com/questions/9283556/sshj-keypair-login-to-ec2-instance/15800383#15800383
	 * @return
	 */
	protected boolean init(){
		
		assert(AWS_ACCESS_KEY != null || AWS_SECRET_KEY != null || AWS_SSH_KEY != null) : "AWS Credentials not set";
		
		RunInstancesRequest runInstancesRequest = new RunInstancesRequest();
		runInstancesRequest.withImageId("ami-d0f89fb9")
		                   .withInstanceType(InstanceType.T1Micro)
		                   .withMinCount(1)
		                   .withMaxCount(1)
		                   .withKeyName("ec2-key")
		                   .withSecurityGroups("default");
		
		AWSCredentials credentials = new BasicAWSCredentials(AWS_ACCESS_KEY,AWS_SECRET_KEY);
        AmazonEC2Client ec2 = new AmazonEC2Client(credentials);
        ec2.setRegion(Region.getRegion(Regions.US_EAST_1));
		RunInstancesResult runInstances = ec2.runInstances(runInstancesRequest);
		
		return (runInstances != null) ? true : false;
//		return false;
	}

}
