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

import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jboss.security.annotation.SecurityDomain;
import org.openelis.gwt.common.DataBaseUtil;
import org.openelis.gwt.common.FieldErrorException;
import org.openelis.gwt.common.NotFoundException;
import org.openelis.gwt.common.ValidationErrorsList;
import org.openelis.security.domain.SystemUserModuleDO;
import org.openelis.security.domain.SystemUserModuleViewDO;
import org.openelis.security.entity.SystemUserModule;
import org.openelis.security.messages.SecurityMessages;
import org.openelis.security.meta.SystemUserMeta;

@Stateless
@SecurityDomain("security")
@RolesAllowed({"user-select", "templates-select"})
public class SystemUserModuleBean {

    @PersistenceContext(unitName = "newsecurity")
    private EntityManager manager;
    
    @EJB
    private UserCacheBean               userCache;
    
    private SecurityMessages consts;
    
    @PostConstruct
    protected void init() {
        consts = userCache.getConstants(SecurityMessages.class);   
    }

    public ArrayList<SystemUserModuleViewDO> fetchBySystemUserId(Integer id) throws Exception {
        Query query;
        List list;

        query = manager.createNamedQuery("SystemUserModule.FetchBySystemUserId");
        query.setParameter("systemUserId", id);

        list = query.getResultList();
        if (list.isEmpty())
            throw new NotFoundException();

        return DataBaseUtil.toArrayList(list);
    }

    public ArrayList<SystemUserModuleDO> fetchBySystemModuleId(Integer id) throws Exception {
        Query query;
        List list;

        query = manager.createNamedQuery("SystemUserModule.FetchBySystemModuleId");
        query.setParameter("systemModuleId", id);

        list = query.getResultList();
        if (list.isEmpty())
            throw new NotFoundException();

        return DataBaseUtil.toArrayList(list);
    }

    public SystemUserModuleViewDO add(SystemUserModuleViewDO data) throws Exception {
        SystemUserModule entity;

        manager.setFlushMode(FlushModeType.COMMIT);
        entity = new SystemUserModule();
        entity.setSystemUserId(data.getSystemUserId());
        entity.setSystemModuleId(data.getSystemModuleId());
        entity.setHasSelect(data.getHasSelect());
        entity.setHasAdd(data.getHasAdd());
        entity.setHasUpdate(data.getHasUpdate());
        entity.setHasDelete(data.getHasDelete());
        entity.setClause(data.getClause());

        manager.persist(entity);
        data.setId(entity.getId());

        return data;
    }

    public SystemUserModuleViewDO update(SystemUserModuleViewDO data) throws Exception {
        SystemUserModule entity;

        if ( !data.isChanged())
            return data;

        manager.setFlushMode(FlushModeType.COMMIT);
        entity = manager.find(SystemUserModule.class, data.getId());
        entity.setSystemUserId(data.getSystemUserId());
        entity.setSystemModuleId(data.getSystemModuleId());
        entity.setHasSelect(data.getHasSelect());
        entity.setHasAdd(data.getHasAdd());
        entity.setHasUpdate(data.getHasUpdate());
        entity.setHasDelete(data.getHasDelete());
        entity.setClause(data.getClause());

        return data;
    }

    public void delete(SystemUserModuleViewDO data) throws Exception {
        SystemUserModule entity;

        manager.setFlushMode(FlushModeType.COMMIT);
        entity = manager.find(SystemUserModule.class, data.getId());
        if (entity != null)
            manager.remove(entity);
    }

    public void validate(SystemUserModuleViewDO data) throws Exception {
        ValidationErrorsList list;

        list = new ValidationErrorsList();

        if ( !"Y".equals(data.getHasSelect()))
            list.add(new FieldErrorException(consts.selectPermRequired(),
                                             SystemUserMeta.getModuleHasSelect()));

        if (list.size() > 0)
            throw list;
    }
}
