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
package org.openelis.security.bean;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.openelis.security.domain.SystemUserDO;
import org.openelis.security.local.SystemUserPermissionLocal;
import org.openelis.security.remote.SystemUserPermissionRemote;
import org.openelis.ui.common.ModulePermission;
import org.openelis.ui.common.SectionPermission;
import org.openelis.ui.common.SystemUserPermission;
import org.openelis.ui.common.SystemUserVO;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class SystemUserPermissionBean implements SystemUserPermissionLocal, SystemUserPermissionRemote {

    @PersistenceContext(unitName = "newsecurity")
    private EntityManager manager;

    public SystemUserVO fetchById(Integer id) throws Exception {
        Query query;
        SystemUserDO data;

        query = manager.createNamedQuery("SystemUser.FetchById");
        query.setParameter("id", id);

        data = (SystemUserDO)query.getSingleResult();
        return new SystemUserVO(data.getId(),
                                data.getExternalId(),
                                data.getLoginName(),
                                data.getLastName(),
                                data.getFirstName(),
                                data.getInitials(),
                                data.getIsEmployee(),
                                data.getIsActive(),
                                data.getIsTemplate());
    }

    public ArrayList<SystemUserVO> fetchByLoginName(String loginName, int max) throws Exception {
        Query query;
        ArrayList<SystemUserVO> vos;

        query = manager.createNamedQuery("SystemUser.FetchActiveByLoginName");
        query.setParameter("loginName", loginName);
        query.setMaxResults(max);

        vos = new ArrayList<SystemUserVO>();
        for (SystemUserDO data : (List<SystemUserDO>)query.getResultList())
            vos.add(new SystemUserVO(data.getId(),
                                     data.getExternalId(),
                                     data.getLoginName(),
                                     data.getLastName(),
                                     data.getFirstName(),
                                     data.getInitials(),
                                     data.getIsEmployee(),
                                     data.getIsActive(),
                                     data.getIsTemplate()));

        return vos;
    }

    public ArrayList<SystemUserVO> fetchEmployeeByLoginName(String loginName, int max) throws Exception {
        Query query;
        ArrayList<SystemUserVO> vos;

        query = manager.createNamedQuery("SystemUser.FetchActiveEmployeeByLoginName");
        query.setParameter("loginName", loginName);
        query.setMaxResults(max);

        vos = new ArrayList<SystemUserVO>();
        for (SystemUserDO data : (List<SystemUserDO>)query.getResultList())
            vos.add(new SystemUserVO(data.getId(),
                                     data.getExternalId(),
                                     data.getLoginName(),
                                     data.getLastName(),
                                     data.getFirstName(),
                                     data.getInitials(),
                                     data.getIsEmployee(),
                                     data.getIsActive(),
                                     data.getIsTemplate()));

        return vos;
    }

    public SystemUserPermission fetchByApplicationAndLoginName(String application, String loginName) throws Exception {
        Query query;
        ArrayList<SystemUserVO> user;
        SystemUserPermission perm;

        user = fetchByLoginName(loginName, 1);
        if (user.size() == 0)
            return null;

        perm = new SystemUserPermission();
        perm.setUser(user.get(0));

        query = manager.createNamedQuery("SystemUserModule.FetchByUserIdAndApplication");
        query.setParameter("systemUserId", perm.getSystemUserId());
        query.setParameter("application", application);
        for (ModulePermission module : (List<ModulePermission>)query.getResultList())
            perm.add(module);

        query = manager.createNamedQuery("SystemUserSection.FetchByUserIdAndApplication");
        query.setParameter("systemUserId", perm.getSystemUserId());
        query.setParameter("application", application);
        for (SectionPermission section : (List<SectionPermission>)query.getResultList())
            perm.add(section);

        return perm;
    }
}
