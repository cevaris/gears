package networking;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.connection.channel.direct.Session.Command;
import net.schmizz.sshj.transport.TransportException;
import net.schmizz.sshj.userauth.UserAuthException;
import base.Application;
import base.Server;

public class SSHRequest {
	
	private Logger LOG = Logger.getLogger(SSHRequest.class.getClass());
	
	private Session session;
	private String  response;
	
	public SSHRequest(Server server) {
		this.session = server.getSession();
	}
	public SSHRequest(Application server) {
		this.session = server.getSession();
	}
	
	public String getResponse() {
		return response;
	}
	
	public boolean execute(String command){
		
		try {
			final Command cmd = session.exec(command);
            
        	InputStream channel = cmd.getInputStream();
        	
        	StringWriter writer = new StringWriter();
        	IOUtils.copy(channel, writer);
        	this.response = writer.toString();
        	
        	LOG.info(String.format("%s",this.response));
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
