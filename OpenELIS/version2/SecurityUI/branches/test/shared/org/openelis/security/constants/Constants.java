package org.openelis.security.constants;

import com.google.gwt.core.client.GWT;

public class Constants {
    
    private static SecurityConstants consts = GWT.create(SecurityConstants.class); 
    
    public static SecurityConstants get() {
        return consts;
    }

    private Constants() {
    }

}
