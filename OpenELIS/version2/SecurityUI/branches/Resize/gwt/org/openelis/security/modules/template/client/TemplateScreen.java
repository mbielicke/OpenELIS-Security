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
package org.openelis.security.modules.template.client;

import static org.openelis.gwt.screen.State.ADD;
import static org.openelis.gwt.screen.State.DEFAULT;
import static org.openelis.gwt.screen.State.DELETE;
import static org.openelis.gwt.screen.State.DISPLAY;
import static org.openelis.gwt.screen.State.QUERY;
import static org.openelis.gwt.screen.State.UPDATE;
import static org.openelis.gwt.screen.Screen.ShortKeys.CTRL;

import java.util.ArrayList;
import java.util.EnumSet;

import org.openelis.gwt.common.DataBaseUtil;
import org.openelis.gwt.common.LastPageException;
import org.openelis.gwt.common.ModulePermission;
import org.openelis.gwt.common.NotFoundException;
import org.openelis.gwt.common.PermissionException;
import org.openelis.gwt.common.ValidationErrorsList;
import org.openelis.gwt.common.data.Query;
import org.openelis.gwt.common.data.QueryData;
import org.openelis.gwt.event.ActionEvent;
import org.openelis.gwt.event.ActionHandler;
import org.openelis.gwt.event.BeforeCloseEvent;
import org.openelis.gwt.event.BeforeCloseHandler;
import org.openelis.gwt.event.BeforeDragStartEvent;
import org.openelis.gwt.event.BeforeDragStartHandler;
import org.openelis.gwt.event.DataChangeEvent;
import org.openelis.gwt.event.DropEvent;
import org.openelis.gwt.event.DropHandler;
import org.openelis.gwt.event.StateChangeEvent;
import org.openelis.gwt.event.StateChangeHandler;
import org.openelis.gwt.screen.Screen;
import org.openelis.gwt.screen.ScreenHandler;
import org.openelis.gwt.screen.ScreenNavigator;
import org.openelis.gwt.screen.State;
import org.openelis.gwt.widget.AtoZButtons;
import org.openelis.gwt.widget.Button;
import org.openelis.gwt.widget.DragItem;
import org.openelis.gwt.widget.Dropdown;
import org.openelis.gwt.widget.Item;
import org.openelis.gwt.widget.Label;
import org.openelis.gwt.widget.ModalWindow;
import org.openelis.gwt.widget.Queryable;
import org.openelis.gwt.widget.TabLayoutPanel;
import org.openelis.gwt.widget.TextBox;
import org.openelis.gwt.widget.WindowInt;
import org.openelis.gwt.widget.table.Row;
import org.openelis.gwt.widget.table.Table;
import org.openelis.gwt.widget.table.event.BeforeCellEditedEvent;
import org.openelis.gwt.widget.table.event.BeforeCellEditedHandler;
import org.openelis.gwt.widget.table.event.CellEditedEvent;
import org.openelis.gwt.widget.table.event.CellEditedHandler;
import org.openelis.gwt.widget.table.event.RowAddedEvent;
import org.openelis.gwt.widget.table.event.RowAddedHandler;
import org.openelis.gwt.widget.table.event.RowDeletedEvent;
import org.openelis.gwt.widget.table.event.RowDeletedHandler;
import org.openelis.security.domain.ApplicationDO;
import org.openelis.security.domain.IdNameVO;
import org.openelis.security.domain.SectionViewDO;
import org.openelis.security.domain.SystemModuleViewDO;
import org.openelis.security.domain.SystemUserDO;
import org.openelis.security.domain.SystemUserModuleViewDO;
import org.openelis.security.domain.SystemUserSectionViewDO;
import org.openelis.security.manager.SystemUserManager;
import org.openelis.security.messages.Messages;
import org.openelis.security.meta.SystemUserMeta;
import org.openelis.security.modules.application.client.ApplicationService;
import org.openelis.security.modules.clausepopout.client.ClausePopoutScreen;
import org.openelis.security.modules.clausepopout.client.ClausePopoutScreen.Action;
import org.openelis.security.modules.main.cache.UserCache;
import org.openelis.security.modules.main.client.Security;
import org.openelis.security.modules.systemuser.client.SystemUserService;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

public class TemplateScreen extends Screen {
    @UiTemplate("Template.ui.xml")
    interface TemplateUiBinder extends UiBinder<Widget, TemplateScreen> {};

    public static final TemplateUiBinder uiBinder = GWT.create(TemplateUiBinder.class);

    private SystemUserManager            manager;
    private ModulePermission             userPermission;

    @UiField
    protected AtoZButtons                atozButtons;
    private ScreenNavigator<IdNameVO>    nav;

    @UiField
    protected Button                     query, previous, next, add, update, delete, commit, abort,
                                         atozNext, atozPrev, removeModuleButton, showClauseButton, removeSectionButton;

    @UiField
    protected TextBox<String>            loginName;

    @UiField
    protected Dropdown<Integer>          modAppDropDown, secAppDropDown;

    @UiField
    protected Table                      appModuleTable, appSectionTable, atozTable,
                                         userModuleTable, userSectionTable;

    @UiField
    protected SplitLayoutPanel           layout;
    
    @UiField
    protected TabLayoutPanel             tabPanel;
    
    private Integer                      prevAppId;

    private ClausePopoutScreen           clausePopoutScreen;

    public TemplateScreen(WindowInt window) throws Exception {
        setWindow(window);

        userPermission = UserCache.getPermission().getModule("templates");
        if (userPermission == null)
            throw new PermissionException(Messages.get().screenPerm("Template Screen"));

        initWidget(uiBinder.createAndBindUi(this));
        window.setContent(this);

        manager = new SystemUserManager();

        initialize();
        setState(State.DEFAULT);
        initializeDropdowns();
        DataChangeEvent.fire(this);
    }

    /**
     * Setup state and data change handles for every widget on the screen
     */
    private void initialize() {
        
        layout.setWidgetToggleDisplayAllowed(tabPanel,true);
        
        //
        // button panel buttons
        //
        addStateChangeHandler(new StateChangeHandler() {
            public void onStateChange(StateChangeEvent event) {
                query.setEnabled(isState(DEFAULT,DISPLAY) &&
                                 userPermission.hasSelectPermission());
                if (isState(State.QUERY)) {
                    query.lock();
                    query.setPressed(true);
                } else
                    query.setPressed(false);
            }
        });

        addShortcut(query, 'q', CTRL);

        addStateChangeHandler(new StateChangeHandler() {
            public void onStateChange(StateChangeEvent event) {
                previous.setEnabled(isState(DISPLAY));
            }
        });

        addShortcut(previous, 'p', CTRL);

        addStateChangeHandler(new StateChangeHandler() {
            public void onStateChange(StateChangeEvent event) {
                next.setEnabled(isState(DISPLAY));
            }
        });

        addShortcut(next, 'n', CTRL);

        addStateChangeHandler(new StateChangeHandler() {
            public void onStateChange(StateChangeEvent event) {
                add.setEnabled(isState(DEFAULT,DISPLAY) &&
                               userPermission.hasAddPermission());
                if (isState(ADD)) {
                    add.lock();
                    add.setPressed(true);
                } else
                    add.setPressed(false);
            }
        });

        addShortcut(add, 'a', CTRL);

        addStateChangeHandler(new StateChangeHandler() {
            public void onStateChange(StateChangeEvent event) {
                update.setEnabled(isState(DISPLAY) &&
                                  userPermission.hasUpdatePermission());
                if (isState(UPDATE)) {
                    update.lock();
                    update.setPressed(true);
                } else
                    update.setPressed(false);
            }
        });

        addShortcut(update, 'u', CTRL);

        addStateChangeHandler(new StateChangeHandler() {
            public void onStateChange(StateChangeEvent event) {
                delete.setEnabled(isState(DISPLAY) &&
                                  userPermission.hasDeletePermission());
                if (isState(DELETE)) {
                    delete.lock();
                    delete.setPressed(true);
                } else
                    delete.setPressed(false);
            }
        });

        addShortcut(delete, 'd', CTRL);

        addStateChangeHandler(new StateChangeHandler() {
            public void onStateChange(StateChangeEvent event) {
                commit.setEnabled(isState(QUERY,ADD,UPDATE,DELETE));
            }
        });

        addShortcut(commit, 'm', CTRL);

        addStateChangeHandler(new StateChangeHandler() {
            public void onStateChange(StateChangeEvent event) {
                abort.setEnabled(isState(QUERY,ADD,UPDATE,DELETE));
            }
        });

        addShortcut(abort, 'o', CTRL);

        addStateChangeHandler(new StateChangeHandler() {
            public void onStateChange(StateChangeEvent event) {
                modAppDropDown.setEnabled(true);
            }
        });

        modAppDropDown.addValueChangeHandler(new ValueChangeHandler<Integer>() {
            public void onValueChange(ValueChangeEvent<Integer> event) {
                Integer sel;

                sel = modAppDropDown.getSelectedItem().getKey();
                if (sel != null && !sel.equals(prevAppId)) {
                    prevAppId = sel;
                    appModuleTable.setModel(getModulesFromApplication(sel));
                    appSectionTable.setModel(getSectionsFromApplication(sel));
                    secAppDropDown.setValue(sel);
                }
            }
        });

        addStateChangeHandler(new StateChangeHandler() {
            public void onStateChange(StateChangeEvent event) {
                appModuleTable.setEnabled(isState(ADD,UPDATE));
            }
        });

        appModuleTable.enableDrag();

        appModuleTable.getDragController()
                      .addBeforeDragStartHandler(new BeforeDragStartHandler<DragItem>() {
                          public void onBeforeDragStart(BeforeDragStartEvent<DragItem> event) {
                              Row row;
                              Label<String> label;
                              String value;

                              if (!isState(ADD,UPDATE)) {
                                  event.cancel();
                                  return;
                              }
                              try {
                                  row = appModuleTable.getRowAt(event.getDragObject().getIndex());
                                  value = (String)row.getCell(0);
                                  if (value == null)
                                      value = "";
                                  label = new Label<String>(value);
                                  label.setStyleName("ScreenLabel");
                                  label.setWordWrap(false);
                                  event.setProxy(label);
                              } catch (Exception e) {
                                  Window.alert("table beforeDragStart: " + e.getMessage());
                              }
                          }
                      });

        addStateChangeHandler(new StateChangeHandler() {
            public void onStateChange(StateChangeEvent event) {
                secAppDropDown.setEnabled(true);
            }
        });

        secAppDropDown.addValueChangeHandler(new ValueChangeHandler<Integer>() {
            public void onValueChange(ValueChangeEvent<Integer> event) {
                Integer sel;

                sel = secAppDropDown.getSelectedItem().getKey();
                if (sel != null && !sel.equals(prevAppId)) {
                    prevAppId = sel;
                    appModuleTable.setModel(getModulesFromApplication(sel));
                    appSectionTable.setModel(getSectionsFromApplication(sel));
                    modAppDropDown.setValue(sel);
                }
            }
        });

        addStateChangeHandler(new StateChangeHandler() {
            public void onStateChange(StateChangeEvent event) {
                appSectionTable.setEnabled(isState(ADD,UPDATE));
            }
        });

        appSectionTable.enableDrag();

        appSectionTable.getDragController()
                       .addBeforeDragStartHandler(new BeforeDragStartHandler<DragItem>() {
                           public void onBeforeDragStart(BeforeDragStartEvent<DragItem> event) {
                               Row row;
                               Label<String> label;
                               String value;

                               if (!isState(ADD,UPDATE)) {
                                   event.cancel();
                                   return;
                               }

                               try {
                                   row = appSectionTable.getRowAt(event.getDragObject().getIndex());
                                   value = (String)row.getCell(0);
                                   if (value == null)
                                       value = "";
                                   label = new Label<String>(value);
                                   label.setStyleName("ScreenLabel");
                                   label.setWordWrap(false);
                                   event.setProxy(label);
                               } catch (Exception e) {
                                   Window.alert("table beforeDragStart: " + e.getMessage());
                               }
                           }
                       });

        addScreenHandler(loginName, SystemUserMeta.getLoginName(), new ScreenHandler<String>() {
            public void onDataChange(DataChangeEvent event) {
                loginName.setValue(manager.getSystemUser().getLoginName());
            }

            public void onValueChange(ValueChangeEvent<String> event) {
                String val;

                val = event.getValue();
                manager.getSystemUser().setLoginName(val);
                if ( !DataBaseUtil.isEmpty(val))
                    loginName.clearEndUserExceptions();
            }

            public void onStateChange(StateChangeEvent event) {
                loginName.setEnabled(isState(QUERY,ADD,UPDATE));
                loginName.setQueryMode(isState(QUERY));
            }
            
            public Widget onTab(boolean forward) {
            	return forward ? userModuleTable : userSectionTable;
            }
        });

        addScreenHandler(userModuleTable, "userModuleTable", new ScreenHandler<ArrayList<Row>>() {
            public void onDataChange(DataChangeEvent event) {
                if (!isState(State.QUERY))
                    userModuleTable.setModel(getModuleModel());
                showClauseButton.setEnabled(false);
            }

            public void onStateChange(StateChangeEvent event) {
                userModuleTable.setEnabled(true);
                userModuleTable.setQueryMode(isState(QUERY));
            }
           
            public Object getQuery() {
                ArrayList<QueryData> qds = new ArrayList<QueryData>();
                QueryData qd;
                
                for(int i = 0; i < 8; i++) {
                    qd = (QueryData)((Queryable)userModuleTable.getColumnWidget(i)).getQuery();
                    if(qd != null){
                        switch(i) {
                            case 0 :
                                qd.setKey(SystemUserMeta.getModuleSystemModuleApplicationName());
                                break;
                            case 1 :
                                qd.setKey(SystemUserMeta.getModuleSystemModuleName());
                                break;
                            case 2 :
                                qd.setKey(SystemUserMeta.getModuleSystemModuleDescription());
                                break;
                            case 3 :
                                qd.setKey(SystemUserMeta.getModuleHasSelect());
                                break;
                            case 4 :
                                qd.setKey(SystemUserMeta.getModuleHasAdd());
                                break;
                            case 5 :
                                qd.setKey(SystemUserMeta.getModuleHasUpdate());
                                break;
                            case 6 :
                                qd.setKey(SystemUserMeta.getModuleHasDelete());
                                break;
                            case 7 :
                                qd.setKey(SystemUserMeta.getModuleClause());
                        }
                        qds.add(qd);
                    }
                }
                
                return qds.toArray(new QueryData[]{});
            }
            
            public Widget onTab(boolean forward) {
            	return forward ? userSectionTable : loginName;
            }
        });

        userModuleTable.addSelectionHandler(new SelectionHandler<Integer>() {
            public void onSelection(SelectionEvent<Integer> event) {
                showClauseButton.setEnabled(true);
            }
        });

        userModuleTable.addBeforeCellEditedHandler(new BeforeCellEditedHandler() {
            public void onBeforeCellEdited(BeforeCellEditedEvent event) {
                int r, c;
                SystemUserModuleViewDO data;

                r = event.getRow();
                c = event.getCol();

                if (c < 3 || (!isState(ADD,UPDATE,QUERY))) {
                    event.cancel();
                    return;
                }

                try {
                    data = manager.module.get(r);
                } catch (Exception e) {
                    com.google.gwt.user.client.Window.alert(e.getMessage());
                    return;
                }

                switch (c) {
                    case 3:
                        if ("N".equals(data.getSystemModuleHasSelectFlag()))
                            event.cancel();
                        break;
                    case 4:
                        if ("N".equals(data.getSystemModuleHasAddFlag()))
                            event.cancel();
                        break;
                    case 5:
                        if ("N".equals(data.getSystemModuleHasUpdateFlag()))
                            event.cancel();
                        break;
                    case 6:
                        if ("N".equals(data.getSystemModuleHasDeleteFlag()))
                            event.cancel();
                        break;
                }
            }
        });

        userModuleTable.addCellEditedHandler(new CellEditedHandler() {
            public void onCellUpdated(CellEditedEvent event) {
                int r, c;
                Object val;
                SystemUserModuleViewDO data;

                r = event.getRow();
                c = event.getCol();
                val = userModuleTable.getValueAt(r, c);

                try {
                    data = manager.module.get(r);
                } catch (Exception e) {
                    com.google.gwt.user.client.Window.alert(e.getMessage());
                    return;
                }

                switch (c) {
                    case 3:
                        data.setHasSelect((String)val);
                        if ("Y".equals(val))
                            userModuleTable.clearEndUserExceptions(r, c);
                        break;
                    case 4:
                        data.setHasAdd((String)val);
                        break;
                    case 5:
                        data.setHasUpdate((String)val);
                        break;
                    case 6:
                        data.setHasDelete((String)val);
                        break;
                    case 7:
                        data.setClause((String)val);
                        break;
                }
            }
        });

        userModuleTable.addRowAddedHandler(new RowAddedHandler() {
            public void onRowAdded(RowAddedEvent event) {
                int r;
                SystemUserModuleViewDO data;
                SystemModuleViewDO mod;
                Row row;

                r = event.getIndex();
                row = userModuleTable.getRowAt(r);

                mod = (SystemModuleViewDO)row.getData();
                data = manager.module.add();
                data.setSystemUserId(manager.getSystemUser().getId());
                data.setSystemModuleId(mod.getId());
                data.setHasSelect(mod.getHasSelectFlag());
                data.setHasAdd("N");
                data.setHasUpdate("N");
                data.setHasDelete("N");

                data.setSystemModuleApplicationName(mod.getApplicationName());
                data.setSystemModuleName(mod.getName());
                data.setSystemModuleDescription(mod.getDescription());
                data.setSystemModuleHasSelectFlag(mod.getHasSelectFlag());
                data.setSystemModuleHasAddFlag(mod.getHasAddFlag());
                data.setSystemModuleHasUpdateFlag(mod.getHasUpdateFlag());
                data.setSystemModuleHasDeleteFlag(mod.getHasDeleteFlag());
            }
        });

        userModuleTable.addRowDeletedHandler(new RowDeletedHandler() {
            public void onRowDeleted(RowDeletedEvent event) {
                try {
                    manager.module.remove(event.getIndex());
                } catch (Exception e) {
                    Window.alert(e.getMessage());
                }
            }
        });

        userModuleTable.enableDrop();
        appModuleTable.addDropTarget(userModuleTable.getDropController());

        userModuleTable.getDropController().addDropHandler(new DropHandler<DragItem>() {
            public void onDrop(DropEvent<DragItem> event) {
                int drg, drp;
                Row row, drow;
                SystemModuleViewDO data;

                drg = event.getDragObject().getIndex();

                drow = appModuleTable.getRowAt(drg);
                data = (SystemModuleViewDO)drow.getData();

                if (moduleAddedtoUser(data.getId())) {
                    Window.alert(Messages.get().moduleAddedToTemplate());
                    return;
                }
                row = new Row(8);
                row.setData(data);

                userModuleTable.addRow(row);
                drp = userModuleTable.getRowCount() - 1;
                userModuleTable.setValueAt(drp, 0, data.getApplicationName());
                userModuleTable.setValueAt(drp, 1, data.getName());
                userModuleTable.setValueAt(drp, 2, data.getDescription());
                userModuleTable.setValueAt(drp, 3, data.getHasSelectFlag());
                userModuleTable.setValueAt(drp, 4, "N");
                userModuleTable.setValueAt(drp, 5, "N");
                userModuleTable.setValueAt(drp, 6, "N");
            }
        });

        addStateChangeHandler(new StateChangeHandler() {
            public void onStateChange(StateChangeEvent event) {
                removeModuleButton.setEnabled(!isState(ADD,UPDATE));
            }
        });

        addStateChangeHandler(new StateChangeHandler() {
            public void onStateChange(StateChangeEvent event) {
                showClauseButton.setEnabled(false);
            }
        });

        addScreenHandler(userSectionTable, "userSectionTable", new ScreenHandler<ArrayList<Row>>() {
            public void onDataChange(DataChangeEvent event) {
                if (!isState(QUERY))
                    userSectionTable.setModel(getSectionModel());
            }

            public void onStateChange(StateChangeEvent event) {
                userSectionTable.setEnabled(true);
                userSectionTable.setQueryMode(isState(QUERY));
            }
            
            public Object getQuery() {
                ArrayList<QueryData> qds = new ArrayList<QueryData>();
                QueryData qd;
                
                for(int i = 0; i < 8; i++) {
                    qd = (QueryData)((Queryable)userSectionTable.getColumnWidget(i)).getQuery();
                    if(qd != null){
                        switch(i) {
                            case 0 :
                                qd.setKey(SystemUserMeta.getSectionApplicationName());
                                break;
                            case 1 :
                                qd.setKey(SystemUserMeta.getSectionSectionName());
                                break;
                            case 2 :
                                qd.setKey(SystemUserMeta.getSectionSectionDescription());
                                break;
                            case 3 :
                                qd.setKey(SystemUserMeta.getSectionHasView());
                                break;
                            case 4 :
                                qd.setKey(SystemUserMeta.getSectionHasAssign());
                                break;
                            case 5 :
                                qd.setKey(SystemUserMeta.getSectionHasComplete());
                                break;
                            case 6 :
                                qd.setKey(SystemUserMeta.getSectionHasRelease());
                                break;
                            case 7 :
                                qd.setKey(SystemUserMeta.getSectionHasCancel());
                        }
                        qds.add(qd);
                    }
                }
                
                return qds.toArray(new QueryData[]{});
            }
            
            public Widget onTab(boolean forward) {
            	return forward ? loginName : userModuleTable;
            }
        });

        userSectionTable.addBeforeCellEditedHandler(new BeforeCellEditedHandler() {
            public void onBeforeCellEdited(BeforeCellEditedEvent event) {
                if (event.getCol() < 3 || (!isState(ADD,UPDATE,State.QUERY)))
                    event.cancel();
            }
        });

        userSectionTable.addCellEditedHandler(new CellEditedHandler() {
            public void onCellUpdated(CellEditedEvent event) {
                int r, c;
                Object val;
                SystemUserSectionViewDO data;

                r = event.getRow();
                c = event.getCol();
                val = userSectionTable.getValueAt(r, c);

                try {
                    data = manager.section.get(r);
                } catch (Exception e) {
                    com.google.gwt.user.client.Window.alert(e.getMessage());
                    return;
                }

                switch (c) {
                    case 3:
                        data.setHasView((String)val);
                        if ("Y".equals(val))
                            userSectionTable.clearEndUserExceptions(r, c);
                        break;
                    case 4:
                        data.setHasAssign((String)val);
                        break;
                    case 5:
                        data.setHasComplete((String)val);
                        break;
                    case 6:
                        data.setHasRelease((String)val);
                        break;
                    case 7:
                        data.setHasCancel((String)val);
                        break;
                }
            }
        });

        userSectionTable.addRowAddedHandler(new RowAddedHandler() {
            public void onRowAdded(RowAddedEvent event) {
                SystemUserSectionViewDO data;
                SectionViewDO sec;
                Row row;

                row = userSectionTable.getRowAt(event.getIndex());
                sec = (SectionViewDO)row.getData();

                data = manager.section.add();
                data.setSectionApplicationName(sec.getApplicationName());
                data.setSectionId(sec.getId());
                data.setSectionName(sec.getName());
                data.setSectionDescription(sec.getDescription());
                data.setHasView("Y");
                data.setHasAssign("N");
                data.setHasComplete("N");
                data.setHasRelease("N");
                data.setHasCancel("N");
            }
        });

        userSectionTable.addRowDeletedHandler(new RowDeletedHandler() {
            public void onRowDeleted(RowDeletedEvent event) {
                try {
                    manager.section.remove(event.getIndex());
                } catch (Exception e) {
                    Window.alert(e.getMessage());
                }
            }
        });

        userSectionTable.enableDrop();
        appSectionTable.addDropTarget(userSectionTable.getDropController());

        userSectionTable.getDropController().addDropHandler(new DropHandler<DragItem>() {
            public void onDrop(DropEvent<DragItem> event) {
                int drg, drp;
                Row row, drow;
                SectionViewDO data;

                drg = event.getDragObject().getIndex();
                drow = appSectionTable.getRowAt(drg);
                data = (SectionViewDO)drow.getData();

                if (sectionAddedtoUser(data.getId())) {
                    Window.alert(Messages.get().sectionAddedToTemplate());
                    return;
                }

                row = new Row(8);
                row.setData(data);
                userSectionTable.addRow(row);
                drp = userSectionTable.getRowCount() - 1;
                userSectionTable.setValueAt(drp, 0, data.getApplicationName());
                userSectionTable.setValueAt(drp, 1, data.getName());
                userSectionTable.setValueAt(drp, 2, data.getDescription());
                userSectionTable.setValueAt(drp, 3, "Y");
                userSectionTable.setValueAt(drp, 4, "N");
                userSectionTable.setValueAt(drp, 5, "N");
                userSectionTable.setValueAt(drp, 6, "N");
                userSectionTable.setValueAt(drp, 7, "N");
            }
        });

        addStateChangeHandler(new StateChangeHandler() {
            public void onStateChange(StateChangeEvent event) {
                removeSectionButton.setEnabled(isState(ADD,UPDATE));
            }
        });

        //
        // left hand navigation panel
        //
        nav = new ScreenNavigator<IdNameVO>(atozTable, atozNext, atozPrev) {
            public void executeQuery(final Query query) {
                setBusy(Messages.get().querying());

                SystemUserService.get().query(query, new AsyncCallback<ArrayList<IdNameVO>>() {
                    public void onSuccess(ArrayList<IdNameVO> result) {
                        setQueryResult(result);
                        removeBusy();
                    }

                    public void onFailure(Throwable error) {
                        setQueryResult(null);
                        if (error instanceof NotFoundException) {
                            setDone(Messages.get().noRecordsFound());
                            setState(DEFAULT);
                        } else if (error instanceof LastPageException) {
                            setError(Messages.get().noMoreRecordInDir());
                            removeBusy();
                        } else {
                            Window.alert("Error: System User call query failed; " +
                                         error.getMessage());
                            setError(Messages.get().queryFailed());
                            removeBusy();
                        }
                    }
                });
            }

            public boolean fetch(IdNameVO entry) {
                return fetchById( (entry == null) ? null : entry.getId());
            }

            public ArrayList<Item<Integer>> getModel() {
                ArrayList<IdNameVO> result;
                ArrayList<Item<Integer>> model;

                model = null;
                result = nav.getQueryResult();
                if (result != null) {
                    model = new ArrayList<Item<Integer>>();
                    for (IdNameVO entry : result)
                        model.add(new Item<Integer>(entry.getId(), entry.getName()));
                }
                return model;
            }
        };

        addStateChangeHandler(new StateChangeHandler() {
            public void onStateChange(StateChangeEvent event) {
                boolean enable;
                enable = isState(DEFAULT,DISPLAY) && userPermission.hasSelectPermission();
                atozButtons.setEnabled(enable);
                nav.enable(enable);
            }
        });

        atozButtons.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                Query query;
                QueryData field;

                query = new Query();

                field = new QueryData();
                field.setKey(SystemUserMeta.getLoginName());
                field.setQuery( ((Button)event.getSource()).getAction().toLowerCase());
                field.setType(QueryData.Type.STRING);
                query.setFields(field);

                field = new QueryData();
                field.setKey(SystemUserMeta.getIsTemplate());
                field.setQuery("Y");
                field.setType(QueryData.Type.STRING);

                query.setFields(field);

                nav.setQuery(query);
            }
        });

        window.addBeforeClosedHandler(new BeforeCloseHandler<WindowInt>() {
            public void onBeforeClosed(BeforeCloseEvent<WindowInt> event) {
                if (EnumSet.of(State.ADD, State.UPDATE).contains(state)) {
                    event.cancel();
                    setError(Messages.get().mustCommitOrAbort());
                }
            }
        });
        
        tabPanel.setPopoutBrowser(Security.getBrowser());
       
    }

    private void initializeDropdowns() {
        ArrayList<Item<Integer>> imodel;
        ArrayList<Row> rmodel;
        ArrayList<ApplicationDO> apps;
        ArrayList<SystemUserDO> temps;
        Item<Integer> item;
        Row row;

        try {
            imodel = new ArrayList<Item<Integer>>();
            apps = ApplicationService.get().fetchList();
            for (ApplicationDO data : apps) {
                item = new Item<Integer>(data.getId(), data.getName());
                item.setData(data);
                imodel.add(item);
            }
            modAppDropDown.setModel(imodel);
            secAppDropDown.setModel(imodel);

            rmodel = new ArrayList<Row>();
            temps = SystemUserService.get().fetchTemplateList();
            for (SystemUserDO data : temps) {
                row = new Row(data.getLoginName());
                row.setData(data);
                rmodel.add(row);
            }
        } catch (NotFoundException ignE) {
            // ignore
        } catch (Exception e) {
            e.printStackTrace();
            Window.alert(e.getMessage());
        }
    }

    @UiHandler("removeModuleButton")
    protected void removeModule(ClickEvent event) {
        int r;

        r = userModuleTable.getSelectedRow();
        if (r > -1 && userModuleTable.getRowCount() > 0)
            userModuleTable.removeRowAt(r);
    }

    @UiHandler("removeSectionButton")
    protected void removeSection(ClickEvent event) {
        int r;

        r = userSectionTable.getSelectedRow();
        if (r > -1 && userSectionTable.getRowCount() > 0)
            userSectionTable.removeRowAt(r);
    }

    @UiHandler("query")
    protected void query(ClickEvent event) {
        manager = new SystemUserManager();
        setState(QUERY);
        DataChangeEvent.fire(this);

        loginName.setFocus(true);
        setDone(Messages.get().enterFieldsToQuery());
    }

    @UiHandler("previous")
    protected void previous(ClickEvent event) {
        nav.previous();
    }

    @UiHandler("next")
    protected void next(ClickEvent event) {
        nav.next();
    }

    @UiHandler("add")
    protected void add(ClickEvent event) {
        SystemUserDO data;

        manager = new SystemUserManager();
        data = manager.getSystemUser();
        data.setIsActive("Y");
        data.setIsEmployee("N");
        data.setIsTemplate("Y");

        setState(ADD);
        DataChangeEvent.fire(this);
        loginName.setFocus(true);
        setDone(Messages.get().enterInformationPressCommit());
    }

    @UiHandler("update")
    protected void update(ClickEvent event) {
        setBusy(Messages.get().lockForUpdate());

        try {
            manager = SystemUserService.get().fetchForUpdate(manager.getSystemUser().getId());

            setState(UPDATE);
            DataChangeEvent.fire(this);
            loginName.setFocus(true);
        } catch (Exception e) {
            com.google.gwt.user.client.Window.alert(e.getMessage());
        }
        clearStatus();
        removeBusy();
    }

    @UiHandler("delete")
    protected void delete(ClickEvent event) {
        setBusy(Messages.get().lockForUpdate());

        try {
            manager = SystemUserService.get().fetchForUpdate(manager.getSystemUser().getId());

            setState(DELETE);
            DataChangeEvent.fire(this);
        } catch (Exception e) {
            com.google.gwt.user.client.Window.alert(e.getMessage());
        }
        clearStatus();
        removeBusy();
    }

    @UiHandler("commit")
    protected void commit(ClickEvent event) {
        Query query;

        finishEditing();

        if ( !validate()) {
            window.setError(Messages.get().correctErrors());
            return;
        }
        
        switch(state) {
            case QUERY :
                query = new Query();
                query.setFields(getQueryFields());
                nav.setQuery(query);
                break;
            case ADD :
                setBusy(Messages.get().adding());
                try {
                    manager = SystemUserService.get().add(manager);
                
                    setState(DISPLAY);
                    DataChangeEvent.fire(this);
                    setDone(Messages.get().addingComplete());
                } catch (ValidationErrorsList e) {
                    showErrors(e);
                    removeBusy();
                } catch (Exception e) {
                    com.google.gwt.user.client.Window.alert("commitAdd(): " + e.getMessage());
                    clearStatus();
                    removeBusy();
                }
                break;
            case UPDATE :
                setBusy(Messages.get().updating());
                try {
                    SystemUserService.get().update(manager);

                    setState(DISPLAY);
                    setDone(Messages.get().updatingComplete());
                } catch (ValidationErrorsList e) {
                    showErrors(e);
                    removeBusy();
                } catch (Exception e) {
                    com.google.gwt.user.client.Window.alert("commitUpdate(): " + e.getMessage());
                    clearStatus();
                    removeBusy();
                }
                break;
            case DELETE :
                setBusy(Messages.get().deleting());
                try {
                    SystemUserService.get().delete(manager);

                    fetchById(null);
                    setDone(Messages.get().deleteComplete());
                } catch (ValidationErrorsList e) {
                    showErrors(e);
                    removeBusy();
                } catch (Exception e) {
                    Window.alert("commitDelete(): " + e.getMessage());
                    clearStatus();
                    removeBusy();
                }
        }
    }

    @UiHandler("abort")
    protected void abort(ClickEvent event) {
        finishEditing();
        clearErrors();
        setBusy(Messages.get().cancelChanges());
        
        switch(state) {
            case QUERY :
                fetchById(null);
                setDone(Messages.get().queryAborted());
                break;
            case ADD :
                fetchById(null);
                setDone(Messages.get().addAborted());
                break;
            case UPDATE :
                try {
                    manager = SystemUserService.get().abortUpdate(manager.getSystemUser().getId());
                    setState(DISPLAY);
                    DataChangeEvent.fire(this);
                } catch (Exception e) {
                    com.google.gwt.user.client.Window.alert(e.getMessage());
                    fetchById(null);
                }
                setDone(Messages.get().updateAborted());
                break;
            case DELETE :
                try {
                    manager = SystemUserService.get().abortUpdate(manager.getSystemUser().getId());
                    setState(DISPLAY);
                    DataChangeEvent.fire(this);
                } catch (Exception e) {
                    com.google.gwt.user.client.Window.alert(e.getMessage());
                    fetchById(null);
                }
                setDone(Messages.get().deleteAborted());
                break;
            default :
                clearStatus();
                removeBusy();
        }
    }

    protected boolean fetchById(Integer id) {
        SystemUserDO data;
        if (id == null) {
            manager = new SystemUserManager();
            data = manager.getSystemUser();
            data.setIsActive("N");
            data.setIsEmployee("N");
            data.setIsTemplate("N");
            setState(DEFAULT);
        } else {
            setBusy(Messages.get().fetching());
            try {
                manager = SystemUserService.get().fetchById(id,SystemUserManager.Load.MODULES,SystemUserManager.Load.SECTIONS);

                setState(DISPLAY);
            } catch (NotFoundException e) {
                fetchById(null);
                setDone(Messages.get().noRecordsFound());
                return false;
            } catch (Exception e) {
                removeBusy();
                fetchById(null);
                e.printStackTrace();
                com.google.gwt.user.client.Window.alert(Messages.get().fetchFailed() +
                                                        e.getMessage());
                return false;
            }
        }
        DataChangeEvent.fire(this);
        clearStatus();
        removeBusy();

        return true;
    }

    public ArrayList<QueryData> getQueryFields() {
        ArrayList<QueryData> fields;
        QueryData field;

        fields = super.getQueryFields();

        field = new QueryData();
        field.setKey(SystemUserMeta.getIsTemplate());
        field.setQuery("Y");
        field.setType(QueryData.Type.STRING);
        fields.add(field);

        return fields;
    }

    private ArrayList<Row> getModuleModel() {
        Row row;
        SystemUserModuleViewDO data;
        ArrayList<Row> model;

        model = new ArrayList<Row>();
        if (manager == null)
            return model;

        try {
            for (int i = 0; i < manager.module.count(); i++ ) {
                data = manager.module.get(i);
                row = new Row(8);
                row.setCell(0, data.getSystemModuleApplicationName());
                row.setCell(1, data.getSystemModuleName());
                row.setCell(2, data.getSystemModuleDescription());
                row.setCell(3, data.getHasSelect());
                row.setCell(4, data.getHasAdd());
                row.setCell(5, data.getHasUpdate());
                row.setCell(6, data.getHasDelete());
                row.setCell(7, data.getClause());
                model.add(row);
            }
        } catch (Exception e) {
            com.google.gwt.user.client.Window.alert(e.getMessage());
            e.printStackTrace();
        }
        return model;
    }

    private ArrayList<Row> getSectionModel() {
        Row row;
        SystemUserSectionViewDO data;
        ArrayList<Row> model;

        model = new ArrayList<Row>();
        if (manager == null)
            return model;

        try {
            for (int i = 0; i < manager.section.count(); i++ ) {
                data = manager.section.get(i);
                row = new Row(8);
                row.setCell(0, data.getSectionApplicationName());
                row.setCell(1, data.getSectionName());
                row.setCell(2, data.getSectionDescription());
                row.setCell(3, data.getHasView());
                row.setCell(4, data.getHasAssign());
                row.setCell(5, data.getHasComplete());
                row.setCell(6, data.getHasRelease());
                row.setCell(7, data.getHasCancel());
                model.add(row);
            }
        } catch (Exception e) {
            com.google.gwt.user.client.Window.alert(e.getMessage());
            e.printStackTrace();
        }
        return model;
    }

    private ArrayList<Row> getModulesFromApplication(Integer id) {
        ArrayList<SystemModuleViewDO> modules;
        ArrayList<Row> model;
        Row row;

        model = new ArrayList<Row>();
        setBusy(Messages.get().fetching());
        try {
            modules = ApplicationService.get().fetchModulesByAppId(id);
            for (SystemModuleViewDO data : modules) {
                row = new Row(data.getName());
                row.setData(data);
                model.add(row);
            }
            clearStatus();
            removeBusy();
        } catch (NotFoundException ign) {
            setDone(Messages.get().noRecordsFound());
        } catch (Exception e) {
            Window.alert(e.getMessage());
            clearStatus();
            removeBusy();
        }
        return model;
    }

    private ArrayList<Row> getSectionsFromApplication(Integer id) {
        ArrayList<SectionViewDO> sections;
        ArrayList<Row> model;
        Row row;

        model = new ArrayList<Row>();
        try {
            setBusy(Messages.get().fetching());
            sections = ApplicationService.get().fetchSectionsByAppId(id);
            for (SectionViewDO data : sections) {
                row = new Row(data.getName());
                row.setData(data);
                model.add(row);
            }
            clearStatus();
            removeBusy();
        } catch (NotFoundException ign) {
            setDone(Messages.get().noRecordsFound());
        } catch (Exception e) {
            Window.alert(e.getMessage());
            clearStatus();
            removeBusy();
        }
        return model;
    }

    private boolean sectionAddedtoUser(Integer id) {
        try {
            for (int i = 0; i < manager.section.count(); i++ ) {
                if (manager.section.get(i).getSectionId().equals(id))
                    return true;
            }
        } catch (Exception e) {
            Window.alert(e.getMessage());
        }

        return false;
    }

    private boolean moduleAddedtoUser(Integer id) {
        try {
            for (int i = 0; i < manager.module.count(); i++ ) {
                if (manager.module.get(i).getSystemModuleId().equals(id))
                    return true;
            }
        } catch (Exception e) {
            Window.alert(e.getMessage());
        }

        return false;
    }

    @UiHandler("showClauseButton")
    protected void showClause(ClickEvent event) {
        WindowInt modal;
        SystemUserModuleViewDO data;

        if (isState(ADD,UPDATE))
            userModuleTable.finishEditing();
        try {
            data = manager.module.get(userModuleTable.getSelectedRow());
        } catch (Exception e) {
            Window.alert(e.getMessage());
            e.printStackTrace();
            return;
        }

        if (clausePopoutScreen == null) {
            try {
                clausePopoutScreen = new ClausePopoutScreen();
            } catch (Exception e) {
                e.printStackTrace();
                Window.alert("ClausePopoutScreen error: " + e.getMessage());
                return;
            }

            clausePopoutScreen.addActionHandler(new ActionHandler<ClausePopoutScreen.Action>() {
                public void onAction(ActionEvent<Action> event) {
                    int r;
                    String clause;
                    SystemUserModuleViewDO data;

                    if (event.getAction() == ClausePopoutScreen.Action.OK && isState(ADD,UPDATE)) {
                        clause = (String)event.getData();
                        try {
                            r = userModuleTable.getSelectedRow();
                            data = manager.module.get(r);
                            data.setClause(clause);
                            userModuleTable.setValueAt(r, 7, clause);
                        } catch (Exception e) {
                            Window.alert(e.getMessage());
                            e.printStackTrace();
                        }
                    }
                }
            });
        }

        modal = new ModalWindow();
        ((ModalWindow)modal).setSize("400px","400px");
        modal.setName(Messages.get().clause());
        modal.setContent(clausePopoutScreen);
        clausePopoutScreen.setState(state);
        clausePopoutScreen.setWindow(modal);
        clausePopoutScreen.setClause(data.getClause());
    }

}