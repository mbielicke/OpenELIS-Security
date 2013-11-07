package org.openelis.security.modules.systemuser.client;

import java.util.ArrayList;

import org.openelis.security.domain.IdNameVO;
import org.openelis.security.domain.SystemUserDO;
import org.openelis.security.manager.SystemUserManager;
import org.openelis.ui.common.data.Query;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SystemUserServiceIntAsync {

    void abortUpdate(Integer id, AsyncCallback<SystemUserManager> callback);

    void add(SystemUserManager man, AsyncCallback<SystemUserManager> callback);

    void delete(SystemUserManager man, AsyncCallback<Void> callback);

    void fetchById(Integer id, SystemUserManager.Load[] elements, AsyncCallback<SystemUserManager> callback);

    void fetchForUpdate(Integer id, AsyncCallback<SystemUserManager> callback);

    void fetchTemplateList(AsyncCallback<ArrayList<SystemUserDO>> callback);

    void query(Query query, AsyncCallback<ArrayList<IdNameVO>> callback);

    void update(SystemUserManager man, AsyncCallback<Void> callback);

}
