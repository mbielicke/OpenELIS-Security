package org.openelis.security.messages;

import com.teklabs.gwt.i18n.client.LocaleFactory;

public class Messages  {
        
    public static SecurityMessages get() {
        return LocaleFactory.get(SecurityMessages.class);
    }

    private Messages() {
    }

}
