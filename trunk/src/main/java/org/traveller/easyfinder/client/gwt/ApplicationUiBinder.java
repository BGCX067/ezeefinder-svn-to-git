package org.traveller.easyfinder.client.gwt;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ApplicationUiBinder extends Composite implements HasText 
{
	private static ApplicationUiBinderUiBinder uiBinder = GWT.create(ApplicationUiBinderUiBinder.class);

	interface ApplicationUiBinderUiBinder extends UiBinder<Widget, ApplicationUiBinder> 
	{
	}

	@UiField VerticalPanel myPanelContent;

	public ApplicationUiBinder() 
	{
		initWidget(uiBinder.createAndBindUi(this));
		 
        HTML html1 = new HTML();
        html1.setHTML("<a href='http://www.google.com'>Click me!</a>");
        myPanelContent.add(html1);
        HTML html2 = new HTML();
        html2.setHTML("This is my sample <b>content</b>!");
        myPanelContent.add(html2);
	}

	public String getText() 
	{
		return "";
	}

	public void setText(String text) 
	{
	}
}
