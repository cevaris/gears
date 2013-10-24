package gears.base.connection;

public interface Connection {
	
	
	public boolean isOpen();
	public boolean connect();
	public boolean execute(String command);
	public boolean disconnect();
	

}
