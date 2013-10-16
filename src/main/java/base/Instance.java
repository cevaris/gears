package base;

public class Instance {
	
	String fqdn;
	String sshPermKeyPath;
	
	public void setFQDN(String fqdn) {
		this.fqdn = fqdn;
	}
	public void setSSHPermKeyPath(String sshPermKeyPath) {
		this.sshPermKeyPath = sshPermKeyPath;
	}
	
	public String getFQDN() {
		return fqdn;
	}
	public String getSSHPermKeyPath() {
		return sshPermKeyPath;
	}
}
