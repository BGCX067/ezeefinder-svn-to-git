package org.traveller.easyfinder.client.gwt.view.location;

import org.traveller.easyfinder.client.gwt.view.Display;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.CssResource.NotStrict;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class LocationDetailsView extends Composite implements Display
{
	@UiTemplate( "FindLocationView.ui.xml" )
	interface LocationDetailsViewUiBinder extends UiBinder<Widget, LocationDetailsView> 
	{
	}
	
	interface GlobalResources extends ClientBundle 
	{
		@NotStrict
	    @Source("org/traveller/easyfinder/client/gwt/css/stylesheet.css")
	    CssResource css();
	}

	private static LocationDetailsViewUiBinder uiBinder = GWT.create( LocationDetailsViewUiBinder.class );

	@UiField
	DockLayoutPanel mainLayoutPanel;
	
	@UiField
	SimplePanel workAreaPanel;

	public LocationDetailsView() 
	{
		// Inject global styles.
	    GWT.<GlobalResources>create( GlobalResources.class ).css().ensureInjected();

		initWidget( uiBinder.createAndBindUi( this ) );
		GWT.log( "Called LocationDetailsView --> Constructor" );
		
		workAreaPanel.add( new Label( "Search Completed ..." ) );
	}

	public Widget asWidget() 
	{
		return this;
	}
}
