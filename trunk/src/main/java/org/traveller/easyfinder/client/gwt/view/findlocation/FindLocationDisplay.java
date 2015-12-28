package org.traveller.easyfinder.client.gwt.view.findlocation;

import org.traveller.easyfinder.client.gwt.view.Display;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public interface FindLocationDisplay extends Display 
{
	public FormPanel getFormPanel(); 
	
	public TextBox getSearchTextbox();
	
	public Button getSubmitButton(); 
	
	public PopupPanel getPopupPanel();
	
	public VerticalPanel getVerticalPanel();
	
	public VerticalPanel getPopupVerticalPanel();
}
