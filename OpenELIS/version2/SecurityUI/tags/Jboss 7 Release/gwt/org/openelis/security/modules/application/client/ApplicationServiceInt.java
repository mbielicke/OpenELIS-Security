package org.openelis.security.modules.application.client;

import java.util.ArrayList;
import java.util.EnumSet;

import org.openelis.security.domain.ApplicationDO;
import org.openelis.security.domain.IdNameVO;
import org.openelis.security.domain.SectionViewDO;
import org.openelis.security.domain.SystemModuleViewDO;
import org.openelis.security.manager.ApplicationManager;
import org.openelis.ui.common.data.Query;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("application")
public interface ApplicationServiceInt extends RemoteService {

    public ApplicationManager fetchById(Integer id, ApplicationManager.Load... elements) throws Exception;

    public ArrayList<ApplicationDO> fetchList() throws Exception;

    public ArrayList<IdNameVO> query(Query query) throws Exception;

    public ApplicationManager add(ApplicationManager man) throws Exception;

    public void update(ApplicationManager man) throws Exception;

    public ApplicationManager fetchForUpdate(Integer id) throws Exception;

    public ApplicationManager abortUpdate(Integer id) throws Exception;
    
    public ArrayList<SystemModuleViewDO> fetchModulesByAppId(Integer id) throws Exception;
    
    public ArrayList<SectionViewDO> fetchSectionsByAppId(Integer id) throws Exception;

}