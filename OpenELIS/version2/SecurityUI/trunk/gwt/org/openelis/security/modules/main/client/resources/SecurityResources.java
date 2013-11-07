package org.openelis.security.modules.main.client.resources;

import org.openelis.ui.resources.UIResources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.DataResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.ImageResource.ImageOptions;
import com.google.gwt.resources.client.ImageResource.RepeatStyle;

public interface SecurityResources extends UIResources {
    public static final SecurityResources INSTANCE = GWT.create(SecurityResources.class);

    @Source("css/style.css")
    StyleCSS style();
    
    @Source({"css/icon.css","org/openelis/ui/resources/css/icon.css"})
    IconCSS icon();

    @Source("images/app_background.png")
    @ImageOptions(repeatStyle=RepeatStyle.Both)
    ImageResource AppBackground();
    
    @Source("images/logoutbuttonimage.gif")
    ImageResource logout();

    @Source("images/removerowbuttonimage.gif")
    ImageResource removeRow();

    @Source("images/removerowbuttonimagedisabled.gif")
    ImageResource removeRowDisabled();

    @Source("images/addrowbuttonimage.gif")
    ImageResource addRow();

    @Source("images/addrowbuttonimagedisabled.gif")
    ImageResource addRowDisabled();

    @Source("images/buttonbarbg.gif")
    @ImageOptions(repeatStyle = RepeatStyle.Horizontal)
    ImageResource buttonBar();


}
