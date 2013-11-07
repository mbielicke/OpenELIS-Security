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
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jboss.security.annotation.SecurityDomain;
import org.openelis.gwt.common.DataBaseUtil;
import org.openelis.gwt.common.DatabaseException;
import org.openelis.gwt.common.FieldErrorException;
import org.openelis.gwt.common.LastPageException;
import org.openelis.gwt.common.NotFoundException;
import org.openelis.gwt.common.ValidationErrorsList;
import org.openelis.gwt.common.data.QueryData;
import org.openelis.security.constants.SecurityConstants;
import org.openelis.security.domain.IdNameVO;
import org.openelis.security.domain.SystemUserDO;
import org.openelis.security.entity.SystemUser;
import org.openelis.security.meta.SystemUserMeta;
import org.openelis.util.QueryBuilderV2;

import com.google.gwt.i18n.client.LocalizableResource.Key;

@Stateless
@SecurityDomain("security")
@RolesAllowed({"user-select", "templates-select"})
public class SystemUserBean {

    @PersistenceContext(unitName = "newsecurity")
    private EntityManager               manager;

    private static final SystemUserMeta meta = new SystemUserMeta();
    
    @EJB
    private UserCacheBean               userCache;
    
    private SecurityConstants consts;
    
    @PostConstruct
    protected void init() {
        consts = userCache.getConstants(SecurityConstants.class);
    }

    public SystemUserDO fetchById(Integer id) throws Exception {
        Query query;
        SystemUserDO data;

        query = manager.createNamedQuery("SystemUser.FetchById");
        query.setParameter("id", id);
        try {
            data = (SystemUserDO)query.getSingleResult();
        } catch (NoResultException e) {
            throw new NotFoundException();
        } catch (Exception e) {
            throw new DatabaseException(e);
        }
        return data;
    }

    public SystemUserDO fetchByLoginName(String loginName) throws Exception {
        Query query;
        SystemUserDO data;

        query = manager.createNamedQuery("SystemUser.FetchByLoginName");
        query.setParameter("loginName", loginName);
        try {
            data = (SystemUserDO)query.getSingleResult();
        } catch (NoResultException e) {
            throw new NotFoundException();
        } catch (Exception e) {
            throw new DatabaseException(e);
        }
        return data;
    }

    public ArrayList<SystemUserDO> fetchTemplateList() throws Exception {
        Query query;
        List list;

        query = manager.createNamedQuery("SystemUser.FetchTemplateList");

        list = query.getResultList();
        if (list.isEmpty())
            throw new NotFoundException();

        return DataBaseUtil.toArrayList(list);
    }

    public ArrayList<IdNameVO> query(ArrayList<QueryData> fields, int first, int max) throws Exception {
        Query query;
        QueryBuilderV2 builder;
        List list;

        builder = new QueryBuilderV2();
        builder.setMeta(meta);
        builder.setSelect("distinct new org.openelis.security.domain.IdNameVO(" +
                          SystemUserMeta.getId() + ", " + SystemUserMeta.getLoginName() + ") ");
        builder.constructWhere(fields);
        builder.setOrderBy(SystemUserMeta.getLoginName());

        query = manager.createQuery(builder.getEJBQL());
        query.setMaxResults(first + max);
        builder.setQueryParams(query, fields);

        list = query.getResultList();
        if (list.isEmpty())
            throw new NotFoundException();
        list = (ArrayList<IdNameVO>)DataBaseUtil.subList(list, first, max);
        if (list == null)
            throw new LastPageException();

        return (ArrayList<IdNameVO>)list;
    }

    public SystemUserDO add(SystemUserDO data) throws Exception {
        SystemUser entity;

        manager.setFlushMode(FlushModeType.COMMIT);
        entity = new SystemUser();
        entity.setExternalId(data.getExternalId());
        entity.setLoginName(data.getLoginName());
        entity.setLastName(data.getLastName());
        entity.setFirstName(data.getFirstName());
        entity.setInitials(data.getInitials());
        entity.setIsEmployee(data.getIsEmployee());
        entity.setIsActive(data.getIsActive());
        entity.setIsTemplate(data.getIsTemplate());

        manager.persist(entity);
        data.setId(entity.getId());

        return data;
    }

    public SystemUserDO update(SystemUserDO data) throws Exception {
        SystemUser entity;

        if ( !data.isChanged())
            return data;

        manager.setFlushMode(FlushModeType.COMMIT);
        entity = manager.find(SystemUser.class, data.getId());
        entity.setExternalId(data.getExternalId());
        entity.setLoginName(data.getLoginName());
        entity.setLastName(data.getLastName());
        entity.setFirstName(data.getFirstName());
        entity.setInitials(data.getInitials());
        entity.setIsEmployee(data.getIsEmployee());
        entity.setIsActive(data.getIsActive());
        entity.setIsTemplate(data.getIsTemplate());

        return data;
    }

    public void delete(SystemUserDO data) throws Exception {
        SystemUser entity;

        manager.setFlushMode(FlushModeType.COMMIT);

        entity = manager.find(SystemUser.class, data.getId());
        if (entity != null)
            manager.remove(entity);
    }

    public void validate(SystemUserDO data) throws Exception {
        ValidationErrorsList list;
        SystemUserDO qdata;

        list = new ValidationErrorsList();
        if (DataBaseUtil.isEmpty(data.getLoginName())) {
            list.add(new FieldErrorException(consts.fieldRequired(),
                                             SystemUserMeta.getLoginName()));
        } else {
            try {
                qdata = fetchByLoginName(data.getLoginName());
                if ( !qdata.getId().equals(data.getId()))
                    list.add(new FieldErrorException(consts.fieldUnique(),
                                                     SystemUserMeta.getLoginName()));
            } catch (NotFoundException ignE) {
                // ignore
            }
        }
        if (list.size() > 0)
            throw list;
    }
}
