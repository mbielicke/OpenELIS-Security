package org.openelis.security.modules.main.client;

import org.openelis.ui.messages.UIMessages;

public interface Prompts extends UIMessages {
    String system();
    String logout();
    String logoutDescription();
    String permissions();
    String systemUser();
    String utilities();
    String templates();
    String application();

}
