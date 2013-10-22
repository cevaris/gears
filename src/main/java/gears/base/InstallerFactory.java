package gears.base;

public class InstallerFactory {

	
	private static InstallerFactory instance = null;
	
	public static InstallerFactory getInstance() {
		if(instance == null) instance = new InstallerFactory();
		return instance;
	}
	
	public Installer getDebianInstaller(Connection connection){
		return new DebianInstaller(connection);
	}
	
}
