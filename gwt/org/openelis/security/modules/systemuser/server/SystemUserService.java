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
package org.openelis.security.modules.systemuser.server;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;

import org.openelis.security.bean.SystemUserBean;
import org.openelis.security.bean.SystemUserManagerBean;
import org.openelis.security.domain.IdNameVO;
import org.openelis.security.domain.SystemUserDO;
import org.openelis.security.manager.SystemUserManager;
import org.openelis.security.modules.systemuser.client.SystemUserServiceInt;
import org.openelis.ui.common.data.Query;
import org.openelis.ui.server.RemoteServlet;

/*
 * This class provides service for SystemUserManager, SystemUserSectionManager
 * and SystemUserModuleManager.
 */

@WebServlet("/security/systemuser")
public class SystemUserService extends RemoteServlet implements SystemUserServiceInt {

    private static final long serialVersionUID = 1L;

    private static final int      rowPP = 24;

    @EJB
    private SystemUserManagerBean systemUserManager;

    @EJB
    private SystemUserBean        systemUser;

    public SystemUserManager fetchById(Integer id, SystemUserManager.Load... elements) throws Exception {
        try {
            return systemUserManager.fetchById(id,elements);
        } catch (Exception anyE) {
            throw serializeForGWT(anyE);
        }
    }

    public ArrayList<IdNameVO> query(Query query) throws Exception {
        try {
            return systemUser.query(query.getFields(), query.getPage() * rowPP, rowPP);
        } catch (Exception anyE) {
            throw serializeForGWT(anyE);
        }
    }

    public ArrayList<SystemUserDO> fetchTemplateList() throws Exception {
        try {
            return systemUser.fetchTemplateList();
        } catch (Exception anyE) {
            throw serializeForGWT(anyE);
        }
    }

    public SystemUserManager add(SystemUserManager man) throws Exception {
        try {
            return systemUserManager.add(man);
        } catch (Exception anyE) {
            throw serializeForGWT(anyE);
        }
    }

    public SystemUserManager update(SystemUserManager man) throws Exception {
        try {
            return systemUserManager.update(man);
        } catch (Exception anyE) {
            throw serializeForGWT(anyE);
        }
    }

    public SystemUserManager delete(SystemUserManager man) throws Exception {
        try {
            return systemUserManager.delete(man);
        } catch (Exception anyE) {
            throw serializeForGWT(anyE);
        }
    }

    public SystemUserManager fetchForUpdate(Integer id) throws Exception {
        try {
            return systemUserManager.fetchForUpdate(id);
        } catch (Exception anyE) {
            throw serializeForGWT(anyE);
        }
    }

    public SystemUserManager abortUpdate(Integer id) throws Exception {
        try {
            return systemUserManager.abortUpdate(id);
        } catch (Exception anyE) {
            throw serializeForGWT(anyE);
        }
    }
}
