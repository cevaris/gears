package gears.base;

public class Instance {
	
	String fqdn;
	String sshPermKeyPath;
	Configuration config;

	
	public Instance(String fqdn, String sshPermKeyPath, Configuration config) {
		this.fqdn = fqdn;
		this.sshPermKeyPath = sshPermKeyPath;
		this.config = config;
	}
	
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
