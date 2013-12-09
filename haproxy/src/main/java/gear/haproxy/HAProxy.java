package gear.haproxy;

import gears.Gear;

class HAProxy extends Gear {
	
	
	
	
	@Override
	public void execute() {
		// Update application repository
		update();
		
		// Install misc apps
		install( "-y", "haproxy" );

		// Restart Apache service, equals to "service apache2 restart"
		restart("apache2");
	}

}