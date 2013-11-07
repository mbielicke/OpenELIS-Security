package org.openelis.security.modules.application.client;

import java.util.ArrayList;
import java.util.EnumSet;

import org.openelis.security.domain.ApplicationDO;
import org.openelis.security.domain.IdNameVO;
import org.openelis.security.domain.SectionViewDO;
import org.openelis.security.domain.SystemModuleViewDO;
import org.openelis.security.manager.ApplicationManager;
import org.openelis.ui.common.data.Query;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ApplicationServiceIntAsync {

    void abortUpdate(Integer id, AsyncCallback<ApplicationManager> callback);

    void add(ApplicationManager man, AsyncCallback<ApplicationManager> callback);

    void fetchById(Integer id, ApplicationManager.Load[] elements, AsyncCallback<ApplicationManager> callback);

    void fetchForUpdate(Integer id, AsyncCallback<ApplicationManager> callback);

    void fetchList(AsyncCallback<ArrayList<ApplicationDO>> callback);

    void query(Query query, AsyncCallback<ArrayList<IdNameVO>> callback);

    void update(ApplicationManager man, AsyncCallback<Void> callback);

    void fetchModulesByAppId(Integer id, AsyncCallback<ArrayList<SystemModuleViewDO>> callback);

    void fetchSectionsByAppId(Integer id, AsyncCallback<ArrayList<SectionViewDO>> callback);
   
    
}
