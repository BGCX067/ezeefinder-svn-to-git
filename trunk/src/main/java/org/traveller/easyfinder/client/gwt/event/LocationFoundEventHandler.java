package org.traveller.easyfinder.client.gwt.event;

import com.google.gwt.event.shared.EventHandler;

public interface LocationFoundEventHandler extends EventHandler 
{
	void onLocationFound( LocationFoundEvent event );
}
