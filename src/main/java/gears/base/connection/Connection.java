package gears.base.connection;

import java.util.List;

import gears.base.Instance;

public interface Connection {
	
	
	public boolean isOpen();
	public boolean connect(Instance instance);
	public boolean command(String command);
	public boolean disconnect();
	

}
