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
package org.openelis.security.modules.systemuser.client;

import static org.openelis.gwt.screen.State.ADD;
import static org.openelis.gwt.screen.State.DEFAULT;
import static org.openelis.gwt.screen.State.DELETE;
import static org.openelis.gwt.screen.State.DISPLAY;
import static org.openelis.gwt.screen.State.QUERY;
import static org.openelis.gwt.screen.State.UPDATE;
import static org.openelis.gwt.screen.Screen.ShortKeys.CTRL;

import java.util.ArrayList;

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
import org.openelis.gwt.widget.CheckBox;
import org.openelis.gwt.widget.DragItem;
import org.openelis.gwt.widget.Dropdown;
import org.openelis.gwt.widget.Item;
import org.openelis.gwt.widget.Label;
import org.openelis.gwt.widget.MenuItem;
import org.openelis.gwt.widget.Queryable;
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
import org.openelis.security.constants.Constants;
import org.openelis.security.domain.ApplicationDO;
import org.openelis.security.domain.IdNameVO;
import org.openelis.security.domain.SectionViewDO;
import org.openelis.security.domain.SystemModuleViewDO;
import org.openelis.security.domain.SystemUserDO;
import org.openelis.security.domain.SystemUserModuleViewDO;
import org.openelis.security.domain.SystemUserSectionViewDO;
import org.openelis.security.manager.SystemUserManager;
import org.openelis.security.meta.SystemUserMeta;
import org.openelis.security.modules.application.client.ApplicationService;
import org.openelis.security.modules.clausepopout.client.ClausePopoutScreen;
import org.openelis.security.modules.clausepopout.client.ClausePopoutScreen.Action;
import org.openelis.security.modules.main.cache.UserCache;

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
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;

public class SystemUserScreen extends Screen {
    @UiTemplate("SystemUser.ui.xml")
    interface SystemUserUiBinder extends UiBinder<Widget, SystemUserScreen> {
    };

    public static final SystemUserUiBinder uiBinder = GWT.create(SystemUserUiBinder.class);

    private SystemUserManager              manager;
    private ModulePermission               userPermission;

    @UiField
    protected AtoZButtons                  atozButtons;
    private ScreenNavigator<IdNameVO>      nav;
    @UiField
    protected Button                       query, previous, next, add, atozNext, atozPrev, update,
                                           delete, commit, abort, removeModuleButton, showClause, removeSectionButton;
    @UiField
    protected MenuItem                     duplicateRecord;
    @UiField
    protected TextBox<Integer>             id;    
    @UiField
    protected TextBox<String>              loginName, lastName, firstName, initials, externalId;
                                           
    @UiField
    protected Dropdown<Integer>            modAppDropDown, secAppDropDown;
    @UiField
    protected CheckBox                     isEmployee, isActive;
    @UiField
    protected Table                        appModuleTable, appSectionTable, templateTable,
                                           userModuleTable, userSectionTable, atozTable;

    private Integer                        prevAppId;

    private ClausePopoutScreen             clausePopoutScreen;

    public SystemUserScreen(WindowInt window) throws Exception {
        setWindow(window);

        userPermission = UserCache.getPermission().getModule("user");
        if (userPermission == null)
            throw new PermissionException(Constants.get().screenPerm("System User Screen"));

        initWidget(uiBinder.createAndBindUi(this));
        window.setContent(this);

        SystemUserDO data;
        manager = new SystemUserManager();

        data = manager.getSystemUser();
        data.setIsActive("N");
        data.setIsEmployee("N");
        data.setIsTemplate("N");

        initialize();
        setState(DEFAULT);
        initializeDropdowns();
        DataChangeEvent.fire(this);
    }

    /**
     * Setup state and data change handles for every widget on the screen
     */
    private void initialize() {
        //
        // button panel buttons
        //
        addStateChangeHandler(new StateChangeHandler() {
            public void onStateChange(StateChangeEvent event) {
                query.setEnabled(isState(DEFAULT,DISPLAY) &&
                                 userPermission.hasSelectPermission());
                if (isState(QUERY)) {
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
                duplicateRecord.setEnabled(isState(State.DISPLAY));
            }
        });

        duplicateRecord.addCommand(new Command() {
            public void execute() {
                duplicate();
            }
        });

        addStateChangeHandler(new StateChangeHandler() {
            public void onStateChange(StateChangeEvent event) {
                modAppDropDown.setEnabled(true);
                modAppDropDown.setQueryMode(false);
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
                secAppDropDown.setQueryMode(false);
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

        templateTable.enableDrag();
        templateTable.getDragController()
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
                                 row = templateTable.getRowAt(event.getDragObject().getIndex());
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

        addScreenHandler(id, SystemUserMeta.getId(), new ScreenHandler<Integer>() {
            public void onDataChange(DataChangeEvent event) {
                id.setValue(manager.getSystemUser().getId());
            }

            public void onValueChange(ValueChangeEvent<Integer> event) {
                manager.getSystemUser().setId(event.getValue());
            }

            public void onStateChange(StateChangeEvent event) {
                id.setEnabled(isState(QUERY));
                id.setQueryMode(isState(State.QUERY));
            }
            
            public Widget onTab(boolean forward) {
            	return forward ? loginName : userSectionTable;
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
            	return forward ? isEmployee : id;
            }
        });

        addScreenHandler(isEmployee, SystemUserMeta.getIsEmployee(), new ScreenHandler<String>() {
            public void onDataChange(DataChangeEvent event) {
                isEmployee.setValue(manager.getSystemUser().getIsEmployee());
            }

            public void onValueChange(ValueChangeEvent<String> event) {
                manager.getSystemUser().setIsEmployee(event.getValue());
            }

            public void onStateChange(StateChangeEvent event) {
                isEmployee.setEnabled(isState(QUERY, ADD, UPDATE));
                isEmployee.setQueryMode(isState(QUERY));
            }
            
            public Widget onTab(boolean forward) {
            	return forward ? lastName : loginName;
            }
        });

        addScreenHandler(lastName, SystemUserMeta.getLastName(), new ScreenHandler<String>() {
            public void onDataChange(DataChangeEvent event) {
                lastName.setValue(manager.getSystemUser().getLastName());
            }

            public void onValueChange(ValueChangeEvent<String> event) {
                manager.getSystemUser().setLastName(event.getValue());
            }

            public void onStateChange(StateChangeEvent event) {
                lastName.setEnabled(isState(QUERY,ADD,UPDATE));
                lastName.setQueryMode(isState(State.QUERY));
            }
            
            public Widget onTab(boolean forward) {
            	return forward ? firstName : isEmployee;
            }
        });

        addScreenHandler(firstName, SystemUserMeta.getFirstName(), new ScreenHandler<String>() {
            public void onDataChange(DataChangeEvent event) {
                firstName.setValue(manager.getSystemUser().getFirstName());
            }

            public void onValueChange(ValueChangeEvent<String> event) {
                manager.getSystemUser().setFirstName(event.getValue());
            }

            public void onStateChange(StateChangeEvent event) {
                firstName.setEnabled(isState(QUERY,ADD,UPDATE));
                firstName.setQueryMode(isState(State.QUERY));
            }
            
            public Widget onTab(boolean forward) {
            	return forward ? isActive : lastName;
            }
        });

        addScreenHandler(isActive, SystemUserMeta.getIsActive(), new ScreenHandler<String>() {
            public void onDataChange(DataChangeEvent event) {
                isActive.setValue(manager.getSystemUser().getIsActive());
            }

            public void onValueChange(ValueChangeEvent<String> event) {
                manager.getSystemUser().setIsActive(event.getValue());
            }

            public void onStateChange(StateChangeEvent event) {
                isActive.setEnabled(isState(QUERY,ADD,UPDATE));
                isActive.setQueryMode(isState(QUERY));
            }
            
            public Widget onTab(boolean forward) {
            	return forward ? initials : firstName;
            }
        });

        addScreenHandler(initials, SystemUserMeta.getInitials(), new ScreenHandler<String>() {
            public void onDataChange(DataChangeEvent event) {
                initials.setValue(manager.getSystemUser().getInitials());
            }

            public void onValueChange(ValueChangeEvent<String> event) {
                manager.getSystemUser().setInitials(event.getValue());
            }

            public void onStateChange(StateChangeEvent event) {
                initials.setEnabled(isState(QUERY,ADD,UPDATE));
                initials.setQueryMode(isState(QUERY));
            }
            
            public Widget onTab(boolean forward) {
            	return forward ? externalId : isActive;
            }
        });

        addScreenHandler(externalId, SystemUserMeta.getExternalId(), new ScreenHandler<String>() {
            public void onDataChange(DataChangeEvent event) {
                externalId.setValue(manager.getSystemUser().getExternalId());
            }

            public void onValueChange(ValueChangeEvent<String> event) {
                manager.getSystemUser().setExternalId(event.getValue());
            }

            public void onStateChange(StateChangeEvent event) {
                externalId.setEnabled(isState(QUERY,ADD,UPDATE));
                externalId.setQueryMode(isState(QUERY));
            }
            
            public Widget onTab(boolean forward) {
            	return forward ? userModuleTable : initials; 
            }
        });

        addScreenHandler(userModuleTable, "userModuletTable", new ScreenHandler<ArrayList<Row>>() {
            public void onDataChange(DataChangeEvent event) {
                if (!isState(State.QUERY))
                    userModuleTable.setModel(getModuleModel());
                showClause.setEnabled(false);
            }

            public void onStateChange(StateChangeEvent event) {
                userModuleTable.setEnabled(true);
                userModuleTable.setQueryMode(isState(QUERY));
            }

            public Object getQuery() {
                ArrayList<QueryData> qds = new ArrayList<QueryData>();
                QueryData qd;

                for (int i = 0; i < 8; i++ ) {
                    qd = (QueryData) ((Queryable)userModuleTable.getColumnWidget(i)).getQuery();
                    if (qd != null) {
                        switch (i) {
                            case 0:
                                qd.setKey(SystemUserMeta.getModuleSystemModuleApplicationName());
                                break;
                            case 1:
                                qd.setKey(SystemUserMeta.getModuleSystemModuleName());
                                break;
                            case 2:
                                qd.setKey(SystemUserMeta.getModuleSystemModuleDescription());
                                break;
                            case 3:
                                qd.setKey(SystemUserMeta.getModuleHasSelect());
                                break;
                            case 4:
                                qd.setKey(SystemUserMeta.getModuleHasAdd());
                                break;
                            case 5:
                                qd.setKey(SystemUserMeta.getModuleHasUpdate());
                                break;
                            case 6:
                                qd.setKey(SystemUserMeta.getModuleHasDelete());
                                break;
                            case 7:
                                qd.setKey(SystemUserMeta.getModuleClause());
                        }
                        qds.add(qd);
                    }
                }

                return qds.toArray(new QueryData[] {});
            }
            
            public Widget onTab(boolean forward) {
            	return forward ? userSectionTable : externalId;
            }
        });

        userModuleTable.addSelectionHandler(new SelectionHandler<Integer>() {
            public void onSelection(SelectionEvent<Integer> event) {
                showClause.setEnabled(true);
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

                data = manager.module.get(r);

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

                data = manager.module.get(r);

                switch (c) {
                    case 3:
                        data.setHasSelect((String)val);
                        /*
                         * this cell could get errors added to it because of the
                         * validation done in EJB; those errors are cleared if
                         * the value in the cell changes, so that the data in it
                         * could be committed
                         */
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
                Row row;

                r = event.getIndex();
                row = userModuleTable.getRowAt(r);
                manager.module.add((SystemUserModuleViewDO)row.getData());

            }
        });

        userModuleTable.addRowDeletedHandler(new RowDeletedHandler() {
            public void onRowDeleted(RowDeletedEvent event) {
                manager.module.remove(event.getIndex());
            }
        });

        userModuleTable.enableDrop();
        appModuleTable.addDropTarget(userModuleTable.getDropController());
        templateTable.addDropTarget(userModuleTable.getDropController());

        userModuleTable.getDropController().addDropHandler(new DropHandler<DragItem>() {
            public void onDrop(DropEvent<DragItem> event) {
                int drg, drp;
                Row row, drow;
                SystemModuleViewDO mod;
                SystemUserDO user;
                SystemUserManager man;
                SystemUserModuleViewDO data;
                DragItem item;

                item = event.getDragObject();
                drg = item.getIndex();

                /*
                 * the SystemUserSectionViewDO(s) and SystemUserModuleViewDO(s)
                 * are created here and set as the data for the rows that get
                 * added to the table; we have to do this here as opposed to the
                 * normal practice of creating the DO(s) in the handler for
                 * RowAddedEvent because it would be difficult to determine
                 * there as to whether it was a template or a system module from
                 * an application that was dropped on the table and to be able
                 * to set all the permissions, as appropriate, from the dropped
                 * record in the new DO
                 */
                if (item.getSource() == appModuleTable) {
                    drow = appModuleTable.getRowAt(drg);
                    mod = (SystemModuleViewDO)drow.getData();

                    if (moduleAddedtoUser(mod.getId())) {
                        Window.alert(Constants.get().moduleAddedToUser());
                        return;
                    }

                    data = getSystemUserModule(mod);
                    row = new Row(8);
                    row.setData(data);
                    userModuleTable.addRow(row);
                    drp = userModuleTable.getRowCount() - 1;
                    setValuesInModuleTable(drp, data, false);
                } else if (item.getSource() == templateTable) {
                    drow = templateTable.getRowAt(drg);
                    user = (SystemUserDO)drow.getData();

                    try {
                        man = SystemUserService.get().fetchById(user.getId(),SystemUserManager.Load.MODULES, SystemUserManager.Load.SECTIONS);
                        /*
                         * we add all the modules and sections from the template
                         * that are not already added to the user
                         */
                        addUserModulesFromTemplate(man);
                        addUserSectionsFromTemplate(man);
                    } catch (Exception e) {
                        Window.alert(e.getMessage());
                    }
                }
            }
        });

        addStateChangeHandler(new StateChangeHandler() {
            public void onStateChange(StateChangeEvent event) {
                removeModuleButton.setEnabled(isState(ADD,UPDATE));
            }
        });

        addStateChangeHandler(new StateChangeHandler() {
            public void onStateChange(StateChangeEvent event) {
                showClause.setEnabled(false);
            }
        });

        addScreenHandler(userSectionTable, "userSectionTable", new ScreenHandler<ArrayList<Row>>() {
            public void onDataChange(DataChangeEvent event) {
                if (!isState(QUERY))
                    userSectionTable.setModel(getSectionModel());
            }

            public void onStateChange(StateChangeEvent event) {
                userSectionTable.setEnabled(true);
                userSectionTable.setQueryMode(isState(State.QUERY));
            }

            public Object getQuery() {
                ArrayList<QueryData> qds = new ArrayList<QueryData>();
                QueryData qd;

                for (int i = 0; i < 8; i++ ) {
                    qd = (QueryData) ((Queryable)userSectionTable.getColumnWidget(i)).getQuery();
                    if (qd != null) {
                        switch (i) {
                            case 0:
                                qd.setKey(SystemUserMeta.getSectionApplicationName());
                                break;
                            case 1:
                                qd.setKey(SystemUserMeta.getSectionSectionName());
                                break;
                            case 2:
                                qd.setKey(SystemUserMeta.getSectionSectionDescription());
                                break;
                            case 3:
                                qd.setKey(SystemUserMeta.getSectionHasView());
                                break;
                            case 4:
                                qd.setKey(SystemUserMeta.getSectionHasAssign());
                                break;
                            case 5:
                                qd.setKey(SystemUserMeta.getSectionHasComplete());
                                break;
                            case 6:
                                qd.setKey(SystemUserMeta.getSectionHasRelease());
                                break;
                            case 7:
                                qd.setKey(SystemUserMeta.getSectionHasCancel());
                        }
                        qds.add(qd);
                    }
                }

                return qds.toArray(new QueryData[] {});
            }
            
            public Widget onTab(boolean forward) {
            	return forward ? id : userModuleTable;
            }
        });

        userSectionTable.addBeforeCellEditedHandler(new BeforeCellEditedHandler() {
            public void onBeforeCellEdited(BeforeCellEditedEvent event) {
                if (event.getCol() < 3 || (!isState(ADD,UPDATE,QUERY)))
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

                data = manager.section.get(r);

                switch (c) {
                    case 3:
                        data.setHasView((String)val);
                        /*
                         * this cell could get errors added to it because of the
                         * validation done in EJB; those errors are cleared if
                         * the value in the cell changes, so that the data in it
                         * could be committed
                         */
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
                Row row;

                row = userSectionTable.getRowAt(event.getIndex());
                data = (SystemUserSectionViewDO)row.getData();

                manager.section.add(data);

            }
        });

        userSectionTable.addRowDeletedHandler(new RowDeletedHandler() {
            public void onRowDeleted(RowDeletedEvent event) {
                manager.section.remove(event.getIndex());
            }
        });

        userSectionTable.enableDrop();
        appSectionTable.addDropTarget(userSectionTable.getDropController());
        templateTable.addDropTarget(userSectionTable.getDropController());

        userSectionTable.getDropController().addDropHandler(new DropHandler<DragItem>() {
            public void onDrop(DropEvent<DragItem> event) {
                int drg, drp;
                Row row, drow;
                SectionViewDO sec;
                SystemUserDO user;
                SystemUserManager man;
                SystemUserSectionViewDO data;
                DragItem item;

                item = event.getDragObject();
                drg = item.getIndex();

                /*
                 * the SystemUserSectionViewDO(s) and SystemUserModuleViewDO(s)
                 * are created here and set as the data for the rows that get
                 * added to the table; we have to do this here as opposed to the
                 * normal practice of creating the DO(s) in the handler for
                 * RowAddedEvent because it would be difficult to determine
                 * there as to whether it was a template or a section from an
                 * application that was dropped on the table and to be able to
                 * set all the permissions, as appropriate, from the dropped
                 * record in the new DO
                 */
                if (item.getSource() == appSectionTable) {
                    drow = appSectionTable.getRowAt(drg);
                    sec = (SectionViewDO)drow.getData();

                    if (sectionAddedtoUser(sec.getId())) {
                        Window.alert(Constants.get().sectionAddedToUser());
                        return;
                    }

                    data = getSystemUserSection(sec);
                    row = new Row(8);
                    row.setData(data);
                    userSectionTable.addRow(row);
                    drp = userSectionTable.getRowCount() - 1;
                    setValuesInSectionTable(drp, data, false);
                } else if (item.getSource() == templateTable) {
                    drow = templateTable.getRowAt(drg);
                    user = (SystemUserDO)drow.getData();

                    try {
                        man = SystemUserService.get().fetchById(user.getId(),SystemUserManager.Load.MODULES,SystemUserManager.Load.SECTIONS);
                        /*
                         * we add all the modules and sections from the template
                         * that are not already added to the user
                         */
                        addUserModulesFromTemplate(man);
                        addUserSectionsFromTemplate(man);
                    } catch (Exception e) {
                        Window.alert(e.getMessage());
                    }
                }
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
                setBusy(Constants.get().querying());

                SystemUserService.get().query(query, new AsyncCallback<ArrayList<IdNameVO>>() {
                    public void onSuccess(ArrayList<IdNameVO> result) {
                        setQueryResult(result);
                    }

                    public void onFailure(Throwable error) {
                        setQueryResult(null);
                        if (error instanceof NotFoundException) {
                            setDone(Constants.get().noRecordsFound());
                            setState(DEFAULT);
                        } else if (error instanceof LastPageException) {
                            setError(Constants.get().noMoreRecordInDir());
                            removeBusy();
                        } else {
                            Window.alert("Error: System User call query failed; " +
                                         error.getMessage());
                            setError(Constants.get().queryFailed());
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
                enable = isState(DEFAULT,DISPLAY) &&
                         userPermission.hasSelectPermission();
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
                field.setQuery("N");
                field.setType(QueryData.Type.STRING);

                query.setFields(field);

                nav.setQuery(query);
            }
        });

        window.addBeforeClosedHandler(new BeforeCloseHandler<WindowInt>() {
            public void onBeforeClosed(BeforeCloseEvent<WindowInt> event) {
                if (isState(ADD,UPDATE)) {
                    event.cancel();
                    setError(Constants.get().mustCommitOrAbort());
                }
            }
        });
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
            templateTable.setModel(rmodel);
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

        id.setFocus(true);
        window.setDone(Constants.get().enterFieldsToQuery());
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
        data.setIsTemplate("N");

        setState(ADD);
        DataChangeEvent.fire(this);
        loginName.setFocus(true);
        window.setDone(Constants.get().enterInformationPressCommit());
    }

    @UiHandler("update")
    protected void update(ClickEvent event) {
        setBusy(Constants.get().lockForUpdate());

        try {
            manager = SystemUserService.get().fetchForUpdate(manager.getSystemUser().getId());
            setState(UPDATE);
            DataChangeEvent.fire(this);
            loginName.setFocus(true);
        } catch (Exception e) {
            Window.alert(e.getMessage());
        }
        clearStatus();
        removeBusy();
    }

    @UiHandler("delete")
    protected void delete(ClickEvent event) {
        boolean ok;
        ok = Window.confirm(Constants.get().deleteUserMessage());
        if ( !ok)
            return;

        setBusy(Constants.get().lockForUpdate());

        try {
            manager = SystemUserService.get().fetchForUpdate(manager.getSystemUser().getId());
            if (manager.module.count() == 0 && manager.section.count() == 0 &&
                "N".equals(manager.getSystemUser().getIsActive())) {
                setState(UPDATE);
                abort(event);
                DataChangeEvent.fire(this);
                setError(Constants.get().userDeleteInvalid());
                removeBusy();
                return;
            }

            manager.getSystemUser().setIsActive("N");

            setState(DELETE);
            DataChangeEvent.fire(this);
            deletePermissions();
        } catch (Exception e) {
            Window.alert(e.getMessage());
        }
        clearStatus();
        removeBusy();
    }

    @UiHandler("commit")
    protected void commit(ClickEvent event) {
        Query query;

        finishEditing();

        if ( !validate()) {
            setError(Constants.get().correctErrors());
            return;
        }

        switch(state) {
            case QUERY : 
                query = new Query();
                query.setFields(getQueryFields());
                nav.setQuery(query);
                break;
            case ADD : 
                setBusy(Constants.get().adding());
                try {
                    manager = SystemUserService.get().add(manager);

                    setState(DISPLAY);
                    DataChangeEvent.fire(this);
                    setDone(Constants.get().addingComplete());
                } catch (ValidationErrorsList e) {
                    showErrors(e);
                    removeBusy();
                } catch (Exception e) {
                    Window.alert("commitAdd(): " + e.getMessage());
                    clearStatus();
                    removeBusy();
                }
                break;
            case UPDATE :
                setBusy(Constants.get().updating());
                try {
                    SystemUserService.get().update(manager);

                    setState(DISPLAY);
                    DataChangeEvent.fire(this);
                    setDone(Constants.get().updatingComplete());
                } catch (ValidationErrorsList e) {
                    showErrors(e);
                    removeBusy();
                } catch (Exception e) {
                    Window.alert("commitUpdate(): " + e.getMessage());
                    clearStatus();
                    removeBusy();
                }
                break;
            case DELETE :
                setBusy(Constants.get().updating());
                try {
                    SystemUserService.get().update(manager);

                    setState(DISPLAY);
                    DataChangeEvent.fire(this);
                    setDone(Constants.get().updatingComplete());
                } catch (ValidationErrorsList e) {
                    showErrors(e);
                    removeBusy();
                } catch (Exception e) {
                    Window.alert("commitUpdate(): " + e.getMessage());
                    clearStatus();
                    removeBusy();
                }
                break;
        }
    }

    @UiHandler("abort")
    protected void abort(ClickEvent event) {
        finishEditing();
        clearErrors();
        setBusy(Constants.get().cancelChanges());

        switch(state) {
            case QUERY :
                fetchById(null);
                setDone(Constants.get().queryAborted());
                break;
            case ADD :
                fetchById(null);
                setDone(Constants.get().addAborted());
                break;
            case UPDATE :
                try {
                    manager = SystemUserService.get().abortUpdate(manager.getSystemUser().getId());
                    setState(DISPLAY);
                    DataChangeEvent.fire(this);
                } catch (Exception e) {
                    Window.alert(e.getMessage());
                    fetchById(null);
                }
                setDone(Constants.get().updateAborted());
            case DELETE :
                try {
                    manager = SystemUserService.get().abortUpdate(manager.getSystemUser().getId());
                    setState(DISPLAY);
                    DataChangeEvent.fire(this);
                } catch (Exception e) {
                    Window.alert(e.getMessage());
                    fetchById(null);
                }
                setDone(Constants.get().deleteAborted());
                break;
            default :
                clearStatus();
                removeBusy();
                break;
        }
    }

    protected void duplicate() {
        try {
            manager = SystemUserService.get().fetchById(manager.getSystemUser().getId(),SystemUserManager.Load.MODULES, SystemUserManager.Load.SECTIONS);

            if (manager.module.count() == 0 && manager.section.count() == 0) {
                DataChangeEvent.fire(this);
                setError(Constants.get().userDuplicateInvalid());
                removeBusy();
                return;
            }
            clearKeys();

            setState(ADD);
            DataChangeEvent.fire(this);

            loginName.setFocus(true);
            setDone(Constants.get().enterInformationPressCommit());
        } catch (Exception e) {
            Window.alert(e.getMessage());
            removeBusy();
            return;
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
            setBusy(Constants.get().fetching());
            try {
                manager = SystemUserService.get().fetchById(id,SystemUserManager.Load.MODULES,SystemUserManager.Load.SECTIONS);

                setState(DISPLAY);
            } catch (NotFoundException e) {
                fetchById(null);
                setDone(Constants.get().noRecordsFound());
                return false;
            } catch (Exception e) {
                removeBusy();
                fetchById(null);
                e.printStackTrace();
                Window.alert(Constants.get().fetchFailed() + e.getMessage());
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

        fields = getQueryFields();

        field = new QueryData();
        field.setKey(SystemUserMeta.getIsTemplate());
        field.setQuery("N");
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
            Window.alert(e.getMessage());
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
            Window.alert(e.getMessage());
            e.printStackTrace();
        }
        return model;
    }

    private ArrayList<Row> getModulesFromApplication(Integer id) {
        ArrayList<SystemModuleViewDO> modules;
        ArrayList<Row> model;
        Row row;

        model = new ArrayList<Row>();
        setBusy(Constants.get().fetching());
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
            setDone(Constants.get().noRecordsFound());
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
            setBusy(Constants.get().fetching());
            sections = ApplicationService.get().fetchSectionsByAppId(id);
            for (SectionViewDO data : sections) {
                row = new Row(data.getName());
                row.setData(data);
                model.add(row);
            }
            clearStatus();
            removeBusy();
        } catch (NotFoundException ign) {
            setDone(Constants.get().noRecordsFound());
        } catch (Exception e) {
            Window.alert(e.getMessage());
            clearStatus();
            removeBusy();
        }
        return model;
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

    @UiHandler("showClause")
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

                    if (event.getAction() == ClausePopoutScreen.Action.OK && (isState(ADD,UPDATE))) {
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

        modal = new org.openelis.gwt.widget.ModalWindow();
        modal.setName(Constants.get().clause());
        modal.setContent(clausePopoutScreen);
        clausePopoutScreen.setState(state);
        clausePopoutScreen.setWindow(modal);
        clausePopoutScreen.setClause(data.getClause());
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

    private void addUserModulesFromTemplate(SystemUserManager man) {
        int ind;
        SystemUserModuleViewDO data, smod;
        Row row;

        for (int i = 0; i < manager.module.count(); i++ ) {
            smod = man.module.get(i);
            if (moduleAddedtoUser(smod.getSystemModuleId())) {
                Window.alert(Constants.get().moduleAddedToUser());
                continue;
            }

            data = getSystemUserModule(smod);
            row = new Row(8);
            row.setData(data);
            userModuleTable.addRow(row);
            ind = userModuleTable.getRowCount() - 1;
            setValuesInModuleTable(ind, data, true);
        }
    }

    private void addUserSectionsFromTemplate(SystemUserManager man) {
        int ind;
        SystemUserSectionViewDO data, ssec;
        Row row;

        try {
            for (int i = 0; i < manager.section.count(); i++ ) {
                ssec = man.section.get(i);
                if (sectionAddedtoUser(ssec.getSectionId())) {
                    Window.alert(Constants.get().sectionAddedToUser());
                    continue;
                }

                data = getSystemUserSection(ssec);
                row = new Row(8);
                row.setData(data);
                userSectionTable.addRow(row);
                ind = userSectionTable.getRowCount() - 1;
                setValuesInSectionTable(ind, data, true);
            }
        } catch (Exception e) {
            Window.alert(e.getMessage());
        }

    }

    private SystemUserModuleViewDO getSystemUserModule(SystemModuleViewDO mod) {
        SystemUserModuleViewDO data;

        data = new SystemUserModuleViewDO();
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

        return data;
    }

    private SystemUserModuleViewDO getSystemUserModule(SystemUserModuleViewDO smod) {
        SystemUserModuleViewDO data;

        data = new SystemUserModuleViewDO();
        data.setSystemUserId(manager.getSystemUser().getId());
        data.setSystemModuleId(smod.getSystemModuleId());
        data.setHasSelect(smod.getSystemModuleHasSelectFlag());
        data.setHasAdd(smod.getHasAdd());
        data.setHasUpdate(smod.getHasUpdate());
        data.setHasDelete(smod.getHasDelete());

        data.setSystemModuleApplicationName(smod.getSystemModuleApplicationName());
        data.setSystemModuleName(smod.getSystemModuleName());
        data.setSystemModuleDescription(smod.getSystemModuleDescription());
        data.setSystemModuleHasSelectFlag(smod.getSystemModuleHasSelectFlag());
        data.setSystemModuleHasAddFlag(smod.getSystemModuleHasAddFlag());
        data.setSystemModuleHasUpdateFlag(smod.getSystemModuleHasUpdateFlag());
        data.setSystemModuleHasDeleteFlag(smod.getSystemModuleHasDeleteFlag());

        return data;
    }

    private SystemUserSectionViewDO getSystemUserSection(SectionViewDO sec) {
        SystemUserSectionViewDO data;

        data = new SystemUserSectionViewDO();
        data.setSectionId(sec.getId());
        data.setHasView("Y");
        data.setHasAssign("N");
        data.setHasComplete("N");
        data.setHasRelease("N");
        data.setHasCancel("N");

        data.setSectionApplicationName(sec.getApplicationName());
        data.setSectionName(sec.getName());
        data.setSectionDescription(sec.getDescription());

        return data;
    }

    private SystemUserSectionViewDO getSystemUserSection(SystemUserSectionViewDO sec) {
        SystemUserSectionViewDO data;

        data = new SystemUserSectionViewDO();
        data.setSectionApplicationName(sec.getSectionApplicationName());
        data.setSectionId(sec.getSectionId());
        data.setSectionName(sec.getSectionName());
        data.setSectionDescription(sec.getSectionDescription());
        data.setHasView(sec.getHasView());
        data.setHasAssign(sec.getHasAssign());
        data.setHasComplete(sec.getHasComplete());
        data.setHasRelease(sec.getHasRelease());
        data.setHasCancel(sec.getHasCancel());

        return data;
    }

    private void setValuesInModuleTable(int row, SystemUserModuleViewDO mod, boolean fromTemplate) {
        userModuleTable.setValueAt(row, 0, mod.getSystemModuleApplicationName());
        userModuleTable.setValueAt(row, 1, mod.getSystemModuleName());
        userModuleTable.setValueAt(row, 2, mod.getSystemModuleDescription());
        userModuleTable.setValueAt(row, 3, mod.getSystemModuleHasSelectFlag());
        if ( !fromTemplate) {
            userModuleTable.setValueAt(row, 4, "N");
            userModuleTable.setValueAt(row, 5, "N");
            userModuleTable.setValueAt(row, 6, "N");
        } else {
            userModuleTable.setValueAt(row, 4, mod.getHasAdd());
            userModuleTable.setValueAt(row, 5, mod.getHasUpdate());
            userModuleTable.setValueAt(row, 6, mod.getHasDelete());
        }
    }

    private void setValuesInSectionTable(int row, SystemUserSectionViewDO sec, boolean fromTemplate) {
        userSectionTable.setValueAt(row, 0, sec.getSectionApplicationName());
        userSectionTable.setValueAt(row, 1, sec.getSectionName());
        userSectionTable.setValueAt(row, 2, sec.getSectionDescription());
        if ( !fromTemplate) {
            userSectionTable.setValueAt(row, 3, "Y");
            userSectionTable.setValueAt(row, 4, "N");
            userSectionTable.setValueAt(row, 5, "N");
            userSectionTable.setValueAt(row, 6, "N");
            userSectionTable.setValueAt(row, 7, "N");
        } else {
            userSectionTable.setValueAt(row, 3, sec.getHasView());
            userSectionTable.setValueAt(row, 4, sec.getHasAssign());
            userSectionTable.setValueAt(row, 5, sec.getHasComplete());
            userSectionTable.setValueAt(row, 6, sec.getHasRelease());
            userSectionTable.setValueAt(row, 7, sec.getHasCancel());
        }
    }

    private void deletePermissions() {
        while (userModuleTable.getRowCount() > 0)
            userModuleTable.removeRowAt(0);

        while (userSectionTable.getRowCount() > 0)
            userSectionTable.removeRowAt(0);
    }

    private void clearKeys() {
        int i;
        SystemUserDO data;
        SystemUserModuleViewDO umod;
        SystemUserSectionViewDO usec;

        data = manager.getSystemUser();
        data.setId(null);
        data.setExternalId(null);
        data.setLoginName(null);
        data.setLastName(null);
        data.setFirstName(null);
        data.setInitials(null);
        data.setIsEmployee("N");
        data.setIsActive("N");
        data.setIsTemplate("N");

        try {
            for (i = 0; i < manager.module.count(); i++ ) {
                umod = manager.module.get(i);
                umod.setId(null);
                umod.setSystemUserId(null);
            }

            for (i = 0; i < manager.section.count(); i++ ) {
                usec = manager.section.get(i);
                usec.setId(null);
                usec.setSystemUserId(null);
            }

        } catch (Exception e) {
            Window.alert(e.getMessage());
        }
    }

}