package gears.base;

public interface Installer {
	
	
	public boolean update();
	
	public boolean restart(String service);
	
	public boolean remove(String flags, String service);
	public boolean install(String flags, String commands);
	
}
