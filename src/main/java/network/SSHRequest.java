package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.connection.channel.direct.Session.Command;
import net.schmizz.sshj.transport.TransportException;
import net.schmizz.sshj.userauth.UserAuthException;
import base.Server;

public class SSHRequest {
	
	private Logger LOG = Logger.getLogger(SSHRequest.class.getClass());
	
	private SSHClient client;
	private String  response;
	
	public SSHRequest(Server server) {
		this.client = server.getClient();
	}
	
	public String getResponse() {
		return response;
	}
	
	public boolean execute(String command){
		
		try {
			Session session = this.client.startSession();
			final Command cmd = session.exec(command);
        	InputStream channel = cmd.getInputStream();
        	
//        	/* Writes full response to string */ 
//        	StringWriter writer = new StringWriter();
//        	IOUtils.copy(channel, writer);
//        	this.response = writer.toString();
        	
        	BufferedReader in = new BufferedReader(new InputStreamReader(channel));
        	StringBuilder log = new StringBuilder();
        	
            String line;        	
            while ((line = in.readLine()) != null) {
            	LOG.info(String.format("%s",line));
            	log.append(line);
            }
            
            this.response = log.toString();
        	
            in.close();
            channel.close();
            cmd.close();
            
        	session.close();
        	System.out.println("Closed Session");
        	
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
