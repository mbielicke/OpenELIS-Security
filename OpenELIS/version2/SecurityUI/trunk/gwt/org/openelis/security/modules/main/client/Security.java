/**
 * Exhibit A - UIRF Open-source Based Public Software License.
 * 
 * The contents of this file are subject to the UIRF Open-source Based Public
 * Software License(the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * openelis.uhl.uiowa.edu
 * 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License for
 * the specific language governing rights and limitations under the License.
 * 
 * The Original Code is OpenELIS code.
 * 
 * The Initial Developer of the Original Code is The University of Iowa.
 * Portions created by The University of Iowa are Copyright 2006-2008. All
 * Rights Reserved.
 * 
 * Contributor(s): ______________________________________.
 * 
 * Alternatively, the contents of this file marked "Separately-Licensed" may be
 * used under the terms of a UIRF Software license ("UIRF Software License"), in
 * which case the provisions of a UIRF Software License are applicable instead
 * of those above.
 */
package org.openelis.security.modules.main.client;

import org.openelis.security.modules.application.client.ApplicationScreen;
import org.openelis.security.modules.systemuser.client.SystemUserScreen;
import org.openelis.security.modules.template.client.TemplateScreen;
import org.openelis.ui.widget.Browser;
import org.openelis.ui.widget.Confirm;
import org.openelis.ui.widget.MenuItem;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.SyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.Widget;

public class Security extends ResizeComposite {

    @UiTemplate("Security.ui.xml")
    interface SecurityUiBinder extends UiBinder<Widget, Security> {
    };

    public static final SecurityUiBinder uiBinder        = GWT.create(SecurityUiBinder.class);

    @UiField
    protected static Browser             browser;

    @UiField
    protected MenuItem                   systemUser, template, application, logout;

    public Security() throws Exception {
        initWidget(uiBinder.createAndBindUi(this));
        initialize();
    }

    protected void initialize() {

        logout.addCommand(new Command() {
            public void execute() {
                try {
                    logout();
                    Window.open("/security/Security.html", "_self", null);
                } catch (Throwable e) {
                    e.printStackTrace();
                    Window.alert(e.getMessage());
                }
            }
        });

        /*
         * these items could be null if the user doesn't have the permission to
         * access the screen that they open
         */
        if (systemUser != null) {
            systemUser.addCommand(new Command() {
                public void execute() {
                    try {
                        final org.openelis.ui.widget.Window win = new org.openelis.ui.widget.Window(true);
                        win.setSize("967px", "754px");
                        new SystemUserScreen(win);
                        win.setName("System User");
                        browser.addWindow(win, "systemuser");
                    } catch (Throwable e) {
                        e.printStackTrace();
                        Window.alert(e.getMessage());
                    }

                }
            });
        }

        if (template != null) {
            template.addCommand(new Command() {
                public void execute() {
                    try {
                        final org.openelis.ui.widget.Window win = new org.openelis.ui.widget.Window(true);
                        win.setSize("967px", "698px");
                        new TemplateScreen(win);
                        win.setName("Templates");
                        browser.addWindow(win, "template");
                    } catch (Throwable e) {
                        e.printStackTrace();
                        Window.alert(e.getMessage());
                    }

                }
            });
        }

        if (application != null) {
            application.addCommand(new Command() {
                public void execute() {
                    try {
                        final org.openelis.ui.widget.Window win = new org.openelis.ui.widget.Window(true);
                        win.setSize("967px","686px");
                        new ApplicationScreen(win);
                        win.setName("Application");
                        browser.addWindow(win, "application");
                    } catch (Throwable e) {
                        e.printStackTrace();
                        Window.alert(e.getMessage());
                    }

                }
            });
        }
    }

    /**
     * Returns the browser associated with this application.
     */
    public static Browser getBrowser() {
        return browser;
    }

    private void logout() {

        TimeoutService.get().logout(new SyncCallback<Void>() {
            public void onSuccess(Void result) {
            }

            public void onFailure(Throwable caught) {
            }
        });
        Window.open("/security/Security.html", "_self", null);
    }
}
