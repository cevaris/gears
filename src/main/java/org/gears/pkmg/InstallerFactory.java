package org.gears.pkmg;

public class InstallerFactory {

	
	private static InstallerFactory instance = null;
	
	public static InstallerFactory getInstance() {
		if(instance == null) instance = new InstallerFactory();
		return instance;
	}
	
	public Installer getDebianInstaller(){
		return new DebianInstaller();
	}
	public Installer getRedHatInstaller(){
		return new RedHatInstaller();
	}
	
}
