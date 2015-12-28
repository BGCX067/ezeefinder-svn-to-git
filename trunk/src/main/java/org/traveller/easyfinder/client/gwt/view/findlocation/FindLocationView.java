package org.traveller.easyfinder.client.gwt.view.findlocation;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.CssResource.NotStrict;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class FindLocationView extends Composite implements FindLocationDisplay
{
	@UiTemplate( "FindLocationView.ui.xml" )
	interface FindLocationViewUiBinder extends UiBinder<Widget, FindLocationView> 
	{
	}
	
	interface GlobalResources extends ClientBundle 
	{
		@NotStrict
	    @Source("org/traveller/easyfinder/client/gwt/css/stylesheet.css")
	    CssResource css();
	}

	private static FindLocationViewUiBinder uiBinder = GWT.create( FindLocationViewUiBinder.class );
	
	@UiField
	DockLayoutPanel mainLayoutPanel;
	
	@UiField
	SimplePanel workAreaPanel;

	final VerticalPanel 	verticalPanel   = new VerticalPanel();
	final FormPanel 		formPanel 		= new FormPanel();
	final PopupPanel 		popupPanel 		= new PopupPanel();
	final TextBox 			searchTextbox	= new TextBox();
	final Button 			submitButton  	= new Button();
	
	final VerticalPanel popupVerticalPanel = new VerticalPanel(); 
	
	public FindLocationView() 
	{
		// Inject global styles.
	    GWT.<GlobalResources>create( GlobalResources.class ).css().ensureInjected();

		initWidget( uiBinder.createAndBindUi( this ) );
		GWT.log( "In Constructor" );
		
		//verticalPanel.add( formPanel );
		
		//Setting Popup properties : START
		popupPanel.setModal( true );
		popupPanel.addStyleName( "searchResultsPopup" );
        
        /*
        popupVerticalPanel.add(  new Label( "Search returned multiple results" )  );
        
        Button button = new Button( "Close", new ClickHandler() 
												{
													public void onClick(ClickEvent event) 
													{
														popupPanel.hide();
													}
												} 
        						  );
        popupVerticalPanel.add( button );
        */
		
        popupPanel.setWidget( popupVerticalPanel );

		//Setting Popup properties : END
		
		workAreaPanel.addStyleName( "searchWorkArea" );
		
		formPanel.setAction("/searchLocation.do");

		formPanel.setMethod(FormPanel.METHOD_POST);

	    HorizontalPanel panel = new HorizontalPanel();
	    //panel.addStyleName( "searchPanel" );
	    verticalPanel.addStyleName( "searchPanel" );
	    verticalPanel.add( panel );
	    
	    formPanel.setWidget( verticalPanel );

	    searchTextbox.setName("searchString");
	    searchTextbox.addStyleName( "seachTextBox" );
	    panel.add( searchTextbox );

	    submitButton.addStyleName( "seachButton" );
	    
	    panel.add( submitButton );
	    
	    //workAreaPanel.setWidget( popupPanel );
	    
	    workAreaPanel.setWidget( formPanel );
	}
	
	public FormPanel getFormPanel() 
	{
		return formPanel;
	}

	public TextBox getSearchTextbox() 
	{
		return searchTextbox;
	}

	public Button getSubmitButton() 
	{
		return submitButton;
	}
	
	public PopupPanel getPopupPanel() 
	{
		return popupPanel;
	}
	
	public VerticalPanel getVerticalPanel() 
	{
		return verticalPanel;
	}

	
	public VerticalPanel getPopupVerticalPanel() 
	{
		return popupVerticalPanel;
	}

	public Widget asWidget() 
	{
		return this;
	}
}
