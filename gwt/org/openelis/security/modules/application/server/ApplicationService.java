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
package org.openelis.security.modules.application.server;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;

import org.openelis.security.bean.ApplicationBean;
import org.openelis.security.bean.ApplicationManagerBean;
import org.openelis.security.bean.SectionBean;
import org.openelis.security.bean.SystemModuleBean;
import org.openelis.security.domain.ApplicationDO;
import org.openelis.security.domain.IdNameVO;
import org.openelis.security.domain.SectionViewDO;
import org.openelis.security.domain.SystemModuleViewDO;
import org.openelis.security.manager.ApplicationManager;
import org.openelis.security.modules.application.client.ApplicationServiceInt;
import org.openelis.ui.common.data.Query;
import org.openelis.ui.server.RemoteServlet;

/*
 * This class provides service for ApplicationManager, SectionManager and
 * SystemModuleManager.
 */
@WebServlet("/security/application")
public class ApplicationService extends RemoteServlet implements ApplicationServiceInt {

    private static final long      serialVersionUID = 1L;

    private static final int       rowPP            = 23;

    @EJB
    private ApplicationManagerBean appManager;

    @EJB
    private ApplicationBean        application;
    
    @EJB
    private SystemModuleBean       module;
    
    @EJB
    private SectionBean            section;

    public ApplicationManager fetchById(Integer id, ApplicationManager.Load... elements) throws Exception {
        try {
            return appManager.fetchById(id,elements);
        } catch (Exception anyE) {
            throw serializeForGWT(anyE);
        }
    }

    public ArrayList<ApplicationDO> fetchList() throws Exception {
        try {
            return application.fetchList();
        } catch (Exception anyE) {
            throw serializeForGWT(anyE);
        }
    }

    public ArrayList<IdNameVO> query(Query query) throws Exception {
        try {
            return application.query(query.getFields(), query.getPage() * rowPP, rowPP);
        } catch (Exception anyE) {
            throw serializeForGWT(anyE);
        }
    }

    public ApplicationManager add(ApplicationManager man) throws Exception {
        try {
            return appManager.add(man);
        } catch (Exception anyE) {
            throw serializeForGWT(anyE);
        }
    }

    public ApplicationManager update(ApplicationManager man) throws Exception {
        try {
            return appManager.update(man);
        } catch (Exception anyE) {
            throw serializeForGWT(anyE);
        }
    }

    public ApplicationManager fetchForUpdate(Integer id) throws Exception {
        try {
            return appManager.fetchForUpdate(id);
        } catch (Exception anyE) {
            throw serializeForGWT(anyE);
        }
    }

    public ApplicationManager abortUpdate(Integer id) throws Exception {
        try {
            return appManager.abortUpdate(id);
        } catch (Exception anyE) {
            throw serializeForGWT(anyE);
        }
    }

    @Override
    public ArrayList<SystemModuleViewDO> fetchModulesByAppId(Integer id) throws Exception {
        try {
            return module.fetchByApplicationId(id);
        } catch (Exception anyE) {
            throw serializeForGWT(anyE);
        }
    }

    @Override
    public ArrayList<SectionViewDO> fetchSectionsByAppId(Integer id) throws Exception {
        try {
            return section.fetchByApplicationId(id);
        } catch (Exception anyE) {
            throw serializeForGWT(anyE);
        }
    }
}
