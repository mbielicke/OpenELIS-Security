package org.openelis.security.messages;

import com.google.gwt.core.client.GWT;

public class Messages {
    
    private static SecurityMessages consts = GWT.create(SecurityMessages.class); 
    
    public static SecurityMessages get() {
        return consts;
    }

    private Messages() {
    }

}
