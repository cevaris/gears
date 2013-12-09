package org.gears.connection;

import org.gears.Instance;

public interface Connection {
	
	
	public boolean isOpen();
	public boolean connect(Instance instance);
	public boolean disconnect(Instance instance);
	public boolean command(String command);
	

}
