package org.gears.connection;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.Security;

import org.apache.log4j.Logger;
import org.gears.Constants;
import org.gears.Instance;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.connection.channel.direct.Session.Command;
import net.schmizz.sshj.transport.TransportException;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;
import net.schmizz.sshj.userauth.UserAuthException;
import net.schmizz.sshj.userauth.keyprovider.PKCS8KeyFile;

public class SSHConnection implements Connection {
	
	Logger LOG = Logger.getLogger(SSHConnection.class);
	
	private SSHClient client = null;
	private boolean isOpen = false;
	
	public boolean disconnect(Instance instance) {
		try{
			this.client.disconnect();
		} catch(IOException e){
			e.printStackTrace();
		}
		return true;
	}

	public boolean isOpen() {
		return this.isOpen;
	}
	
	
	public boolean command(String command) {
		
		LOG.info(String.format("[%s] - %s", this.client.getRemoteAddress(), command));
		
		if(Constants.DEBUG) return true;
		
		try {
			Session session = this.client.startSession();
			final Command cmd = session.exec(command);
        	InputStream channel = cmd.getInputStream();
        	InputStream errorChannel = cmd.getErrorStream();
        	
        	BufferedReader in = new BufferedReader(new InputStreamReader(channel));
        	StringBuilder log = new StringBuilder();
        	
            String line;        	
            while ((line = in.readLine()) != null) {
            	LOG.info(String.format("%s",line));
            	log.append(line);
            }
            
            Integer exitStatus = cmd.getExitStatus();
            if(exitStatus == null || exitStatus != 0) {
            	LOG.error(String.format("Irregular Exit Status (%d) from the following command %s", exitStatus, command));
            	BufferedReader error = new BufferedReader(new InputStreamReader(errorChannel));
            	while ((line = error.readLine()) != null) {
                	LOG.info(String.format("%s",line));
                	log.append(line);
                }
            	errorChannel.close();
            	error.close();   
            } else {
            	LOG.info(String.format("Success Exit Status (%d) from the following command %s", exitStatus, command));
            }
            
            in.close();
            channel.close();
            cmd.close();
            
        	session.close();
        	LOG.info("Closed Session");
        	
        	return true;
        	
		} catch (TransportException e){
			LOG.error(e);
		} catch (UserAuthException e){
			LOG.error(e);
		} catch (IOException e){
			LOG.error(e);
			
		} 
		
		return false;

	}
	
	public boolean connect(Instance instance) {
		
		assert(instance != null) : "Instance is null";
		
		try {
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			this.client = new SSHClient();
			this.client.addHostKeyVerifier(new PromiscuousVerifier());
			this.client.connect(instance.getFQDN());
			
			PKCS8KeyFile keyFile = new PKCS8KeyFile();
			keyFile.init(new File(instance.getSSHPermKeyPath()));
			this.client.authPublickey(Constants.SSH_USER,keyFile);
			
			return true;
		} catch (TransportException e){
			LOG.error(e);
		} catch (UserAuthException e){
			LOG.error(e);
		} catch (IOException e){
			LOG.error(e);
		}
		
		return false;
		
	}

	


}
