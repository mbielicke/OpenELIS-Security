package org.openelis.security.messages;

import com.google.gwt.core.shared.GWT;

public class Messages  {
        
    public static SecurityMessages get() {
        return GWT.create(SecurityMessages.class);
    }

    private Messages() {
    }

}
