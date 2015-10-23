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

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jboss.security.annotation.SecurityDomain;
import org.openelis.security.domain.SystemModuleViewDO;
import org.openelis.security.entity.SystemModule;
import org.openelis.security.meta.ApplicationMeta;
import org.openelis.ui.common.DataBaseUtil;
import org.openelis.ui.common.FieldErrorException;
import org.openelis.ui.common.NotFoundException;
import org.openelis.ui.common.ValidationErrorsList;

@Stateless
@SecurityDomain("security")
@RolesAllowed("application-select")
public class SystemModuleBean {

    @PersistenceContext(unitName = "security")
    private EntityManager        manager;

    @EJB
    private SystemUserModuleBean systemUserModule;

    @EJB
    private UserCacheBean        userCache;
    
    public ArrayList<SystemModuleViewDO> fetchByApplicationId(Integer id) throws Exception {
        Query query;
        List list;

        query = manager.createNamedQuery("SystemModule.FetchByApplicationId");
        query.setParameter("applicationId", id);

        list = query.getResultList();
        if (list.isEmpty())
            throw new NotFoundException();

        return DataBaseUtil.toArrayList(list);
    }

    public SystemModuleViewDO add(SystemModuleViewDO data) throws Exception {
        SystemModule entity;

        manager.setFlushMode(FlushModeType.COMMIT);

        entity = new SystemModule();
        entity.setApplicationId(data.getApplicationId());
        entity.setName(data.getName());
        entity.setDescription(data.getDescription());
        entity.setHasSelectFlag(data.getHasSelectFlag());
        entity.setHasAddFlag(data.getHasAddFlag());
        entity.setHasUpdateFlag(data.getHasUpdateFlag());
        entity.setHasDeleteFlag(data.getHasDeleteFlag());
        entity.setClause(data.getClause());

        manager.persist(entity);
        data.setId(entity.getId());

        return data;
    }

    public SystemModuleViewDO update(SystemModuleViewDO data) throws Exception {
        SystemModule entity;

        if ( !data.isChanged())
            return data;

        manager.setFlushMode(FlushModeType.COMMIT);
        entity = manager.find(SystemModule.class, data.getId());
        entity.setApplicationId(data.getApplicationId());
        entity.setName(data.getName());
        entity.setDescription(data.getDescription());
        entity.setHasSelectFlag(data.getHasSelectFlag());
        entity.setHasAddFlag(data.getHasAddFlag());
        entity.setHasUpdateFlag(data.getHasUpdateFlag());
        entity.setHasDeleteFlag(data.getHasDeleteFlag());
        entity.setClause(data.getClause());

        return data;
    }

    public void delete(SystemModuleViewDO data) throws Exception {
        SystemModule entity;

        manager.setFlushMode(FlushModeType.COMMIT);
        entity = manager.find(SystemModule.class, data.getId());
        if (entity != null)
            manager.remove(entity);
    }

    public void validate(SystemModuleViewDO data) throws Exception {
        String name;
        ValidationErrorsList list;

        list = new ValidationErrorsList();
        name = data.getName();

        if (DataBaseUtil.isEmpty(name))
            list.add(new FieldErrorException(userCache.getMessages().exc_fieldRequired(),
                                             ApplicationMeta.getSystemModuleName()));

        if ( !"Y".equals(data.getHasSelectFlag()))
            list.add(new FieldErrorException(userCache.getMessages().exc_fieldRequired(),
                                             ApplicationMeta.getSystemModuleHasSelectFlag()));

        if (list.size() > 0)
            throw list;
    }

    public void validateForDelete(SystemModuleViewDO data) throws Exception {
        ValidationErrorsList list;

        list = new ValidationErrorsList();
        try {
            systemUserModule.fetchBySystemModuleId(data.getId());
            list.add(new FieldErrorException(userCache.getMessages().application_moduleAssignedToUserException(""),data.getName()));
        } catch (NotFoundException ignE) {
            // ignore
        }

        if (list.size() > 0)
            throw list;
    }
}
