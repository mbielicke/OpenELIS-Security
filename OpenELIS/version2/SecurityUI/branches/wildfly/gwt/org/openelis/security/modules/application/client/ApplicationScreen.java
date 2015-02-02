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
package org.openelis.security.modules.application.client;

import static org.openelis.ui.screen.Screen.ShortKeys.CTRL;
import static org.openelis.ui.screen.State.ADD;
import static org.openelis.ui.screen.State.DEFAULT;
import static org.openelis.ui.screen.State.DISPLAY;
import static org.openelis.ui.screen.State.QUERY;
import static org.openelis.ui.screen.State.UPDATE;
import static org.openelis.ui.screen.Screen.Validation.Status.VALID;

import java.util.ArrayList;

import org.openelis.security.domain.IdNameVO;
import org.openelis.security.domain.SectionViewDO;
import org.openelis.security.domain.SystemModuleViewDO;
import org.openelis.security.manager.ApplicationManager;
import org.openelis.security.messages.Messages;
import org.openelis.security.meta.ApplicationMeta;
import org.openelis.security.modules.clausepopout.client.ClausePopoutScreen;
import org.openelis.security.modules.clausepopout.client.ClausePopoutScreen.Action;
import org.openelis.security.modules.main.cache.UserCache;
import org.openelis.ui.common.DataBaseUtil;
import org.openelis.ui.common.LastPageException;
import org.openelis.ui.common.ModulePermission;
import org.openelis.ui.common.NotFoundException;
import org.openelis.ui.common.PermissionException;
import org.openelis.ui.common.ValidationErrorsList;
import org.openelis.ui.common.data.Query;
import org.openelis.ui.common.data.QueryData;
import org.openelis.ui.event.ActionEvent;
import org.openelis.ui.event.ActionHandler;
import org.openelis.ui.event.BeforeCloseEvent;
import org.openelis.ui.event.BeforeCloseHandler;
import org.openelis.ui.event.DataChangeEvent;
import org.openelis.ui.event.StateChangeEvent;
import org.openelis.ui.event.StateChangeEvent.Handler;
import org.openelis.ui.screen.Screen;
import org.openelis.ui.screen.ScreenHandler;
import org.openelis.ui.screen.ScreenNavigator;
import org.openelis.ui.widget.AtoZButtons;
import org.openelis.ui.widget.Button;
import org.openelis.ui.widget.Item;
import org.openelis.ui.widget.ModalWindow;
import org.openelis.ui.widget.Queryable;
import org.openelis.ui.widget.TextBox;
import org.openelis.ui.widget.WindowInt;
import org.openelis.ui.widget.table.Row;
import org.openelis.ui.widget.table.Table;
import org.openelis.ui.widget.table.event.BeforeCellEditedEvent;
import org.openelis.ui.widget.table.event.BeforeCellEditedHandler;
import org.openelis.ui.widget.table.event.CellEditedEvent;
import org.openelis.ui.widget.table.event.CellEditedHandler;
import org.openelis.ui.widget.table.event.RowAddedEvent;
import org.openelis.ui.widget.table.event.RowAddedHandler;
import org.openelis.ui.widget.table.event.RowDeletedEvent;
import org.openelis.ui.widget.table.event.RowDeletedHandler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

public class ApplicationScreen extends Screen {
    @UiTemplate("Application.ui.xml")
    interface ApplicationUiBinder extends UiBinder<Widget, ApplicationScreen> {};

    public static final ApplicationUiBinder uiBinder = GWT.create(ApplicationUiBinder.class);

    private ApplicationManager              manager;
    private ModulePermission                userPermission;

    @UiField
    protected AtoZButtons                   atozButtons;

    @UiField
    protected TextBox<String>               name, description;

    @UiField
    protected Button                        query, previous, next, add, atozNext, atozPrev, update,
                                            commit, abort, addModuleButton, removeModuleButton, showClauseButton,
                                            addSectionButton, removeSectionButton;
    @UiField
    protected Table                         moduleTable, sectionTable, atozTable;
    
    @UiField
    protected SplitLayoutPanel              layout;
    
    @UiField
    protected LayoutPanel                   sidePanel;
   

    private ScreenNavigator<IdNameVO>       nav;
    private ClausePopoutScreen              clausePopoutScreen;

    public ApplicationScreen(WindowInt window) throws Exception {
        setWindow(window);

        userPermission = UserCache.getPermission().getModule("application");
        if (userPermission == null)
            throw new PermissionException(Messages.get().exc_screenPerm("Application Screen"));

        initWidget(uiBinder.createAndBindUi(this));
        window.setContent(this);

        manager = new ApplicationManager();

        initialize();
        setState(DEFAULT);
        fireDataChange();
    }

    /**
     * Setup state and data change handles for every widget on the screen
     */
    private void initialize() {
        
        layout.setWidgetToggleDisplayAllowed(sidePanel, true);
        
        //
        // button panel buttons
        //
        addStateChangeHandler(new StateChangeEvent.Handler() {
            public void onStateChange(StateChangeEvent event) {
                query.setEnabled(isState(DEFAULT, DISPLAY) &&
                                 userPermission.hasSelectPermission());
                if (event.getState() == QUERY) {
                    query.lock();
                    query.setPressed(true);
                } else
                    query.setPressed(false);
            }
        });

        addShortcut(query, 'q', CTRL);

        addStateChangeHandler(new StateChangeEvent.Handler() {
            public void onStateChange(StateChangeEvent event) {
                previous.setEnabled(isState(DISPLAY));
            }
        });

        addShortcut(previous, 'p', CTRL);

        addStateChangeHandler(new StateChangeEvent.Handler() {
            public void onStateChange(StateChangeEvent event) {
                next.setEnabled(isState(DISPLAY));
            }
        });

        addShortcut(next, 'n', CTRL);

        addStateChangeHandler(new StateChangeEvent.Handler() {
            public void onStateChange(StateChangeEvent event) {
                add.setEnabled(isState(DEFAULT,DISPLAY) &&
                               userPermission.hasAddPermission());
                if (event.getState() == ADD) {
                    add.lock();
                    add.setPressed(true);
                } else
                    add.setPressed(false);
            }
        });

        addShortcut(add, 'a', CTRL);

        addStateChangeHandler(new StateChangeEvent.Handler() {
            public void onStateChange(StateChangeEvent event) {
                update.setEnabled(isState(DISPLAY) &&
                                  userPermission.hasUpdatePermission());
                if (event.getState() == UPDATE) {
                    update.lock();
                    update.setPressed(true);
                } else
                    update.setPressed(false);
            }
        });

        addStateChangeHandler(new StateChangeEvent.Handler() {
            public void onStateChange(StateChangeEvent event) {
                commit.setEnabled(isState(QUERY,ADD,UPDATE));
            }
        });

        addShortcut(commit, 'm', CTRL);

        addStateChangeHandler(new StateChangeEvent.Handler() {
            public void onStateChange(StateChangeEvent event) {
                abort.setEnabled(isState(QUERY,ADD,UPDATE));
            }
        });

        addShortcut(abort, 'o', CTRL);

        addScreenHandler(name, ApplicationMeta.getName(), new ScreenHandler<String>() {
            public void onDataChange(DataChangeEvent event) {
                name.setValue(manager.getApplication().getName());
            }

            public void onValueChange(ValueChangeEvent<String> event) {
                String val;

                val = event.getValue();
                manager.getApplication().setName(val);
                if ( !DataBaseUtil.isEmpty(val))
                    name.clearEndUserExceptions();
            }

            public void onStateChange(StateChangeEvent event) {
                name.setEnabled(isState(QUERY,ADD,UPDATE));
                name.setQueryMode(event.getState() == QUERY);
            }

            public Widget onTab(boolean forward) {
                return forward ? description : sectionTable;
            }
        });

        addScreenHandler(description,
                         ApplicationMeta.getDescription(),
                         new ScreenHandler<String>() {
                             public void onDataChange(DataChangeEvent event) {
                                 description.setValue(manager.getApplication().getDescription());
                             }

                             public void onValueChange(ValueChangeEvent<String> event) {
                                 manager.getApplication().setDescription(event.getValue());
                             }

                             public void onStateChange(StateChangeEvent event) {
                                 description.setEnabled(isState(QUERY,ADD,UPDATE));
                                 description.setQueryMode(event.getState() == QUERY);
                             }

                             public Widget onTab(boolean forward) {
                                 return forward ? moduleTable : name;
                             }
                         });

        addScreenHandler(moduleTable, "moduleTable", new ScreenHandler<ArrayList<Row>>() {
            public void onDataChange(DataChangeEvent event) {
                if (state != QUERY)
                    moduleTable.setModel(getModuleModel());
                showClauseButton.setEnabled(false);
            }

            public void onStateChange(StateChangeEvent event) {
                moduleTable.setEnabled(true);
                moduleTable.setQueryMode(event.getState() == QUERY);
            }
            
            public Object getQuery() {
                ArrayList<QueryData> qds = new ArrayList<QueryData>();
                QueryData qd;
                
                for(int i = 0; i < 7; i++) {
                    qd = (QueryData)((Queryable)moduleTable.getColumnWidget(i)).getQuery();
                    if(qd != null) {
                        switch(i) {
                            case 0 :
                                qd.setKey(ApplicationMeta.getSystemModuleName());
                                break;
                            case 1 :
                                qd.setKey(ApplicationMeta.getSystemModuleDescription());
                                break;
                            case 2 :
                                qd.setKey(ApplicationMeta.getSystemModuleHasSelectFlag());
                                break;
                            case 3 :
                                qd.setKey(ApplicationMeta.getSystemModuleHasAddFlag());
                                break;
                            case 4 :
                                qd.setKey(ApplicationMeta.getSystemModuleHasUpdateFlag());
                                break;
                            case 5 :
                                qd.setKey(ApplicationMeta.getSystemModuleHasDeleteFlag());
                                break;
                            case 6 :
                                qd.setKey(ApplicationMeta.getSystemModuleClause());
                                break;
                        }
                        qds.add(qd);
                    }  
                }
               
                return qds.toArray(new QueryData[]{});
            }
            
            public Widget onTab(boolean forward) {
            	return forward ? sectionTable : description;
            }
            
        });

        moduleTable.addSelectionHandler(new SelectionHandler<Integer>() {
            public void onSelection(SelectionEvent<Integer> event) {
                showClauseButton.setEnabled(true);
                if (isState(ADD,UPDATE))
                    removeModuleButton.setEnabled(true);
            }
        });

        moduleTable.addBeforeCellEditedHandler(new BeforeCellEditedHandler() {
            public void onBeforeCellEdited(BeforeCellEditedEvent event) {
                if (!isState(ADD,UPDATE,QUERY))
                    event.cancel();
            }
        });

        moduleTable.addCellEditedHandler(new CellEditedHandler() {
            public void onCellUpdated(CellEditedEvent event) {
                int r, c;
                Object val;
                SystemModuleViewDO data;

                r = event.getRow();
                c = event.getCol();
                val = moduleTable.getValueAt(r, c);

                data = manager.module.get(r);

                switch (c) {
                    case 0:
                        data.setName((String)val);
                        if ( !DataBaseUtil.isEmpty(val))
                            moduleTable.clearEndUserExceptions(r, c);
                        break;
                    case 1:
                        data.setDescription((String)val);
                        break;
                    case 2:
                        data.setHasSelectFlag((String)val);
                        if ("Y".equals(val))
                            moduleTable.clearEndUserExceptions(r, c);
                        break;
                    case 3:
                        data.setHasAddFlag((String)val);
                        break;
                    case 4:
                        data.setHasUpdateFlag((String)val);
                        break;
                    case 5:
                        data.setHasDeleteFlag((String)val);
                        break;
                    case 6:
                        data.setClause((String)val);
                        break;
                }
            }
        });

        moduleTable.addRowAddedHandler(new RowAddedHandler() {
            public void onRowAdded(RowAddedEvent event) {
                SystemModuleViewDO data;

                data = manager.module.add();
                data.setHasSelectFlag("Y");
                data.setHasAddFlag("N");
                data.setHasUpdateFlag("N");
                data.setHasDeleteFlag("N");
            }
        });

        moduleTable.addRowDeletedHandler(new RowDeletedHandler() {
            public void onRowDeleted(RowDeletedEvent event) {
                try {
                    manager.module.remove(event.getIndex());
                } catch (Exception e) {
                    Window.alert(e.getMessage());
                }
            }
        });
        
        /*
        addScreenHandler(moduleTable.getColumnWidget(0),ApplicationMeta.getSystemModuleName(),new ScreenHandler<String>() {});
        addScreenHandler(moduleTable.getColumnWidget(1),ApplicationMeta.getSystemModuleDescription(),new ScreenHandler<String>() {});
        addScreenHandler(moduleTable.getColumnWidget(2),ApplicationMeta.getSystemModuleHasSelectFlag(),new ScreenHandler<String>() {});
        addScreenHandler(moduleTable.getColumnWidget(3),ApplicationMeta.getSystemModuleHasAddFlag(),new ScreenHandler<String>(){});
        addScreenHandler(moduleTable.getColumnWidget(4),ApplicationMeta.getSystemModuleHasUpdateFlag(),new ScreenHandler<String>(){});
        addScreenHandler(moduleTable.getColumnWidget(5),ApplicationMeta.getSystemModuleHasDeleteFlag(),new ScreenHandler<String>(){});
        addScreenHandler(moduleTable.getColumnWidget(6),ApplicationMeta.getSystemModuleClause(),new ScreenHandler<String>(){});
        */
        
        addStateChangeHandler(new StateChangeEvent.Handler() {
            public void onStateChange(StateChangeEvent event) {
                addModuleButton.setEnabled(isState(ADD,UPDATE));
            }
        });

        addStateChangeHandler(new StateChangeEvent.Handler() {
            public void onStateChange(StateChangeEvent event) {
                removeModuleButton.setEnabled(false);
            }
        });

        addStateChangeHandler(new StateChangeEvent.Handler() {
            public void onStateChange(StateChangeEvent event) {
                showClauseButton.setEnabled(false);
            }
        });

        addScreenHandler(sectionTable, "sectionTable", new ScreenHandler<ArrayList<Row>>() {
            public void onDataChange(DataChangeEvent event) {
                if (state != QUERY)
                    sectionTable.setModel(getSectionModel());
            }

            public void onStateChange(StateChangeEvent event) {
                sectionTable.setEnabled(true);
                sectionTable.setQueryMode(event.getState() == QUERY);
            }
            
            public Object getQuery() {
                ArrayList<QueryData> qds = new ArrayList<QueryData>();
                QueryData qd;
                
                for(int i = 0; i < 2; i++) {
                    qd = (QueryData)((Queryable)sectionTable.getColumnWidget(i)).getQuery();
                    if(qd != null) {
                        switch(i) {
                            case 0 :
                                qd.setKey(ApplicationMeta.getSectionName());
                                break;
                            case 1 :
                                qd.setKey(ApplicationMeta.getSectionDescription());
                                break;
                        }
                        qds.add(qd);
                    }
                }
                
                return qds.toArray(new QueryData[]{});
            }
            
            public Widget onTab(boolean forward) {
            	return forward ? name : moduleTable;
            }
        });

        sectionTable.addSelectionHandler(new SelectionHandler<Integer>() {
            public void onSelection(SelectionEvent<Integer> event) {
                if (isState(ADD,UPDATE))
                    removeSectionButton.setEnabled(true);
            }
        });

        sectionTable.addBeforeCellEditedHandler(new BeforeCellEditedHandler() {
            public void onBeforeCellEdited(BeforeCellEditedEvent event) {
                if (!isState(ADD,UPDATE,QUERY))
                    event.cancel();
            }
        });

        sectionTable.addCellEditedHandler(new CellEditedHandler() {
            public void onCellUpdated(CellEditedEvent event) {
                int r, c;
                Object val;
                SectionViewDO data;

                r = event.getRow();
                c = event.getCol();
                val = sectionTable.getValueAt(r, c);

                try {
                    data = manager.section.get(r);
                } catch (Exception e) {
                    com.google.gwt.user.client.Window.alert(e.getMessage());
                    return;
                }

                switch (c) {
                    case 0:
                        data.setName((String)val);
                        if ( !DataBaseUtil.isEmpty(val))
                            sectionTable.clearEndUserExceptions(r, c);
                        break;
                    case 1:
                        data.setDescription((String)val);
                        break;
                }
            }
        });

        sectionTable.addRowAddedHandler(new RowAddedHandler() {
            public void onRowAdded(RowAddedEvent event) {
                try {
                    manager.section.add();
                } catch (Exception e) {
                    Window.alert(e.getMessage());
                }
            }
        });

        sectionTable.addRowDeletedHandler(new RowDeletedHandler() {
            public void onRowDeleted(RowDeletedEvent event) {
                try {
                    manager.section.remove(event.getIndex());
                } catch (Exception e) {
                    e.printStackTrace();
                    Window.alert(e.getMessage());
                }
            }
        });
        
        /*
        addScreenHandler(sectionTable.getColumnWidget(0),ApplicationMeta.getSectionName(),new ScreenHandler<String>(){});
        addScreenHandler(sectionTable.getColumnWidget(1),ApplicationMeta.getSectionDescription(),new ScreenHandler<String>(){});
        */

        addStateChangeHandler(new StateChangeEvent.Handler() {
            public void onStateChange(StateChangeEvent event) {
                addSectionButton.setEnabled(isState(ADD,UPDATE));
            }
        });

        addStateChangeHandler(new StateChangeEvent.Handler() {
            public void onStateChange(StateChangeEvent event) {
                removeSectionButton.setEnabled(false);
            }
        });

        //
        // left hand navigation panel
        //
        nav = new ScreenNavigator<IdNameVO>(atozTable, atozNext, atozPrev) {
            public void executeQuery(final Query query) {
                setBusy(Messages.get().msg_querying());

                query.setRowsPerPage(29);
                ApplicationService.get().query(query, new AsyncCallback<ArrayList<IdNameVO>>() {
                    public void onSuccess(ArrayList<IdNameVO> result) {
                        setQueryResult(result);
                    }

                    public void onFailure(Throwable error) {
                        setQueryResult(null);
                        if (error instanceof NotFoundException) {
                            setDone(Messages.get().msg_noRecordsFound());
                            setState(DEFAULT);
                        } else if (error instanceof LastPageException) {
                            setError(Messages.get().msg_noMoreRecordInDir());
                            removeBusy();
                        } else {
                            Window.alert("Error: Application call query failed; " +
                                         error.getMessage());
                            setError(Messages.get().msg_queryFailed());
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

        addStateChangeHandler(new StateChangeEvent.Handler() {
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

                field = new QueryData();
                field.setKey(ApplicationMeta.getName());
                field.setQuery( ((Button)event.getSource()).getAction().toLowerCase());
                field.setType(QueryData.Type.STRING);

                query = new Query();
                query.setFields(field);
                nav.setQuery(query);
            }
        });

        window.addBeforeClosedHandler(new BeforeCloseHandler<WindowInt>() {
            public void onBeforeClosed(BeforeCloseEvent<WindowInt> event) {
                if (isState(ADD,UPDATE)) {
                    event.cancel();
                    setError(Messages.get().msg_mustCommitOrAbort());
                }
            }
        });
    }

    @UiHandler("addModuleButton")
    public void adddModule(ClickEvent event) {
        final int n;

        moduleTable.addRow(new Row(null, null, "Y", "N", "N", "N", null));
        n = moduleTable.getRowCount() - 1;
        moduleTable.scrollToVisible(n);

        Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
            public void execute() {
                moduleTable.startEditing(n, 0);
            }
        });
    }

    @UiHandler("removeModuleButton")
    public void removeModule(ClickEvent event) {
        int r;

        r = moduleTable.getSelectedRow();
        if (r > -1 && moduleTable.getRowCount() > 0)
            moduleTable.removeRowAt(r);
    }

    @UiHandler("addSectionButton")
    public void addSection(ClickEvent event) {
        final int n;

        sectionTable.addRow();
        n = sectionTable.getRowCount() - 1;
        sectionTable.scrollToVisible(n);

        Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
            public void execute() {
                sectionTable.startEditing(n, 0);
            }
        });
    }

    @UiHandler("removeSectionButton")
    public void removeSection(ClickEvent event) {
        int r;

        r = sectionTable.getSelectedRow();
        if (r > -1 && sectionTable.getRowCount() > 0)
            sectionTable.removeRowAt(r);
    }

    @UiHandler("query")
    protected void query(ClickEvent event) {
        manager = new ApplicationManager();

        setState(QUERY);
        fireDataChange();

        name.setFocus(true);
        setDone(Messages.get().msg_enterFieldsToQuery());
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
        manager = new ApplicationManager();

        setState(ADD);
        fireDataChange();

        name.setFocus(true);
        setDone(Messages.get().msg_enterInformationPressCommit());
    }

    @UiHandler("update")
    protected void update(ClickEvent event) {
        setBusy(Messages.get().msg_lockForUpdate());

        try {
            manager = ApplicationService.get().fetchForUpdate(manager.getApplication().getId());

            setState(UPDATE);
            fireDataChange();
            name.setFocus(true);
        } catch (Exception e) {
            com.google.gwt.user.client.Window.alert(e.getMessage());
        }
        clearStatus();
        removeBusy();
    }

    @UiHandler("commit")
    protected void commit(ClickEvent event) {
        Query query;
        Validation validation;

        finishEditing();

        validation = validate();
        
        if (validation.getStatus() != VALID) {
            setError(Messages.get().msg_correctErrors());
            return;
        }

        if (state == QUERY) {
            query = new Query();
            query.setFields(getQueryFields());
            nav.setQuery(query);
        } else if (state == ADD) {
            setBusy(Messages.get().msg_adding());
            try {
                manager = ApplicationService.get().add(manager);

                setState(DISPLAY);
                fireDataChange();
                setDone(Messages.get().msg_addingComplete());
            } catch (ValidationErrorsList e) {
                showErrors(e);
                removeBusy();
            } catch (Exception e) {
                com.google.gwt.user.client.Window.alert("commitAdd(): " + e.getMessage());
                clearStatus();
                removeBusy();
            }
        } else if (state == UPDATE) {
            setBusy(Messages.get().msg_updating());
            try {
                ApplicationService.get().update(manager);

                setState(DISPLAY);
                setDone(Messages.get().msg_updatingComplete());
            } catch (ValidationErrorsList e) {
                showErrors(e);
                removeBusy();
            } catch (Exception e) {
                com.google.gwt.user.client.Window.alert("commitUpdate(): " + e.getMessage());
                clearStatus();
                removeBusy();
            }
        }
    }

    @UiHandler("abort")
    protected void abort(ClickEvent event) {
        finishEditing();
        clearErrors();
        setBusy(Messages.get().msg_cancelChanges());

        if (state == QUERY) {
            fetchById(null);
            setDone(Messages.get().msg_queryAborted());
        } else if (state == ADD) {
            fetchById(null);
            setDone(Messages.get().msg_addAborted());
        } else if (state == UPDATE) {
            try {
                manager = ApplicationService.get().abortUpdate(manager.getApplication().getId());
                setState(DISPLAY);
                fireDataChange();
            } catch (Exception e) {
                com.google.gwt.user.client.Window.alert(e.getMessage());
                fetchById(null);
            }
            setDone(Messages.get().msg_updateAborted());
        } else {
            clearStatus();
            removeBusy();
        }
    }

    protected boolean fetchById(Integer id) {
        if (id == null) {
            manager = new ApplicationManager();
            setState(DEFAULT);
        } else {
            setBusy(Messages.get().msg_fetching());
            try {
                manager = ApplicationService.get().fetchById(id,ApplicationManager.Load.MODULES,ApplicationManager.Load.SECTIONS);

                setState(DISPLAY);
            } catch (NotFoundException e) {
                fetchById(null);
                setDone(Messages.get().msg_noRecordsFound());
                return false;
            } catch (Exception e) {
                removeBusy();
                fetchById(null);
                e.printStackTrace();
                com.google.gwt.user.client.Window.alert(Messages.get().msg_fetchFailed() +
                                                        e.getMessage());
                return false;
            }
        }
        fireDataChange();
        clearStatus();
        removeBusy();

        return true;
    }

    private ArrayList<Row> getModuleModel() {
        Row row;
        SystemModuleViewDO data;
        ArrayList<Row> model;

        model = new ArrayList<Row>();
        if (manager == null)
            return model;
        try {
            for (int i = 0; i < manager.module.count(); i++ ) {
                data = manager.module.get(i);
                row = new Row(7);
                row.setCell(0, data.getName());
                row.setCell(1, data.getDescription());
                row.setCell(2, data.getHasSelectFlag());
                row.setCell(3, data.getHasAddFlag());
                row.setCell(4, data.getHasUpdateFlag());
                row.setCell(5, data.getHasDeleteFlag());
                row.setCell(6, data.getClause());
                model.add(row);
            }
        } catch (Exception e) {
            com.google.gwt.user.client.Window.alert(e.getMessage());
            e.printStackTrace();
        }
        return model;
    }

    @UiHandler("showClauseButton")
    protected void showClause(ClickEvent event) {
        WindowInt modal;
        SystemModuleViewDO data;

        if (isState(ADD,UPDATE))
            moduleTable.finishEditing();
        try {
            data = manager.module.get(moduleTable.getSelectedRow());
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
                    SystemModuleViewDO data;

                    if (event.getAction() == ClausePopoutScreen.Action.OK && isState(ADD,UPDATE)) {
                        clause = (String)event.getData();
                        try {
                            r = moduleTable.getSelectedRow();
                            data = manager.module.get(r);
                            data.setClause(clause);
                            moduleTable.setValueAt(r, 6, clause);
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
        modal.setName(Messages.get().gen_clause());
        modal.setContent(clausePopoutScreen);
        clausePopoutScreen.setState(state);
        clausePopoutScreen.setWindow(modal);
        clausePopoutScreen.setClause(data.getClause());
    }

    private ArrayList<Row> getSectionModel() {
        Row row;
        SectionViewDO data;
        ArrayList<Row> model;

        model = new ArrayList<Row>();
        if (manager == null)
            return model;
        try {
            for (int i = 0; i < manager.section.count(); i++ ) {
                data = manager.section.get(i);
                row = new Row(2);
                row.setCell(0, data.getName());
                row.setCell(1, data.getDescription());
                model.add(row);
            }
        } catch (Exception e) {
            com.google.gwt.user.client.Window.alert(e.getMessage());
            e.printStackTrace();
        }
        return model;
    }

}
