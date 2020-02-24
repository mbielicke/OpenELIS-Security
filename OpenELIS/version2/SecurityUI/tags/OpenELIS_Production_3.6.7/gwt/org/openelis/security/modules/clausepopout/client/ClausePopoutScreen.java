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
package org.openelis.security.modules.clausepopout.client;

import static org.openelis.ui.screen.State.ADD;
import static org.openelis.ui.screen.State.UPDATE;

import org.openelis.ui.event.ActionEvent;
import org.openelis.ui.event.ActionHandler;
import org.openelis.ui.event.DataChangeEvent;
import org.openelis.ui.event.HasActionHandlers;
import org.openelis.ui.event.StateChangeEvent;
import org.openelis.ui.screen.Screen;
import org.openelis.ui.screen.ScreenHandler;
import org.openelis.ui.screen.State;
import org.openelis.ui.widget.Button;
import org.openelis.ui.widget.TextArea;
import org.openelis.ui.widget.WindowInt;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Widget;

public class ClausePopoutScreen extends Screen implements
                                                   HasActionHandlers<ClausePopoutScreen.Action> {
    @UiTemplate("ClausePopout.ui.xml")
    interface ClausePopoutUiBinder extends UiBinder<Widget, ClausePopoutScreen> {
    };

    public static final ClausePopoutUiBinder uiBinder = GWT.create(ClausePopoutUiBinder.class);

    @UiField
    protected TextArea                       clauseText;
    @UiField
    protected Button                         ok, cancel;

    private String                           clause;

    public enum Action {
        OK, CANCEL
    };

    public ClausePopoutScreen() throws Exception {

        initWidget(uiBinder.createAndBindUi(this));

        // Setup link between Screen and widget Handlers
        initialize();
        setState(State.DEFAULT);
    }

    /**
     * Setup state and data change handles for every widget on the screen
     */
    private void initialize() {

        addScreenHandler(clauseText, "clauseText", new ScreenHandler<String>() {
            public void onDataChange(DataChangeEvent event) {
                clauseText.setValue(clause);
            }

            public void onValueChange(ValueChangeEvent<String> event) {
                clause = event.getValue();
            }

            public void onStateChange(StateChangeEvent event) {
                clauseText.setEnabled(isState(ADD,UPDATE));
            }

            public Widget onTab(boolean forward) {
                return forward ? ok : cancel;
            }
        });

        addScreenHandler(ok,"ok",new ScreenHandler<Object>() {
            public void onStateChange(StateChangeEvent event) {
                ok.setEnabled(true);
            }
            public Widget onTab(boolean forward) {
                return forward ? cancel : clauseText;
            }
        });

        addScreenHandler(cancel,"cancel",new ScreenHandler<Object>() {
            public void onStateChange(StateChangeEvent event) {
                cancel.setEnabled(true);
            }
            public Widget onTab(boolean forward) {
                return forward ? clauseText : ok;
            }
        });
    }

    public void setClause(String clause) {
        this.clause = clause;
        clauseText.setFocus(true);
        fireDataChange();
    }

    public HandlerRegistration addActionHandler(ActionHandler<Action> handler) {
        return addHandler(handler, ActionEvent.getType());
    }

    @UiHandler("ok")
    protected void ok(ClickEvent event) {
        finishEditing();
        ActionEvent.fire(this, Action.OK, clause);
        window.close();
    }

    @UiHandler("cancel")
    protected void cancel(ClickEvent event) {
        ActionEvent.fire(this, Action.CANCEL, null);
        window.close();
    }
    
    public void setState(State state) {
        super.setState(state);
    }
    
    public void setWindow(WindowInt window) {
        super.setWindow(window);
    }

}