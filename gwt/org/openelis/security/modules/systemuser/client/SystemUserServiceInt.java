package org.openelis.security.modules.systemuser.client;

import java.util.ArrayList;

import org.openelis.security.domain.IdNameVO;
import org.openelis.security.domain.SystemUserDO;
import org.openelis.security.manager.SystemUserManager;
import org.openelis.ui.common.data.Query;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("systemuser")
public interface SystemUserServiceInt extends RemoteService {

    SystemUserManager fetchById(Integer id, SystemUserManager.Load... elements) throws Exception;

    ArrayList<IdNameVO> query(Query query) throws Exception;

    ArrayList<SystemUserDO> fetchTemplateList() throws Exception;

    SystemUserManager add(SystemUserManager man) throws Exception;

    void update(SystemUserManager man) throws Exception;

    void delete(SystemUserManager man) throws Exception;

    SystemUserManager fetchForUpdate(Integer id) throws Exception;

    SystemUserManager abortUpdate(Integer id) throws Exception;

}