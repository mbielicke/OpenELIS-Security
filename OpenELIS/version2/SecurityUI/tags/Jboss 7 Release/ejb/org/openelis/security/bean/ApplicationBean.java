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
import java.util.Locale;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jboss.security.annotation.SecurityDomain;
import org.openelis.security.domain.ApplicationDO;
import org.openelis.security.domain.IdNameVO;
import org.openelis.security.entity.Application;
import org.openelis.security.messages.SecurityMessages;
import org.openelis.security.meta.ApplicationMeta;
import org.openelis.ui.common.DataBaseUtil;
import org.openelis.ui.common.DatabaseException;
import org.openelis.ui.common.FieldErrorException;
import org.openelis.ui.common.LastPageException;
import org.openelis.ui.common.NotFoundException;
import org.openelis.ui.common.ValidationErrorsList;
import org.openelis.ui.common.data.QueryData;
import org.openelis.ui.messages.Messages;
import org.openelis.ui.util.QueryBuilderV2;

import com.teklabs.gwt.i18n.server.LocaleProvider;
import com.teklabs.gwt.i18n.server.LocaleProxy;

@Stateless
@SecurityDomain("security")
@RolesAllowed("application-select")
public class ApplicationBean {

    @PersistenceContext(unitName = "newsecurity")
    private EntityManager                manager;
    
    @EJB
    UserCacheBean user;

    private static final ApplicationMeta meta = new ApplicationMeta();

    public ApplicationDO fetchById(Integer id) throws Exception {
        Query query;
        ApplicationDO data;

        query = manager.createNamedQuery("Application.FetchById");
        query.setParameter("id", id);

        try {
            data = (ApplicationDO)query.getSingleResult();
        } catch (NoResultException e) {
            throw new NotFoundException();
        } catch (Exception e) {
            throw new DatabaseException(e);
        }
        return data;
    }

    public ArrayList<ApplicationDO> fetchList() throws Exception {
        Query query;
        List list;

        query = manager.createNamedQuery("Application.FetchList");

        list = query.getResultList();
        if (list.isEmpty())
            throw new NotFoundException();

        return DataBaseUtil.toArrayList(list);
    }

    public ApplicationDO fetchByName(String name) throws Exception {
        Query query;
        ApplicationDO data;

        query = manager.createNamedQuery("Application.FetchByName");
        query.setParameter("name", name);
        try {
            data = (ApplicationDO)query.getSingleResult();
        } catch (NoResultException e) {
            throw new NotFoundException();
        } catch (Exception e) {
            throw new DatabaseException(e);
        }
        return data;
    }

    public ArrayList<IdNameVO> query(ArrayList<QueryData> fields, int first, int max) throws Exception {
        Query query;
        QueryBuilderV2 builder;
        List list;

        builder = new QueryBuilderV2();
        builder.setMeta(meta);
        builder.setSelect("distinct new org.openelis.security.domain.IdNameVO(" +
                          ApplicationMeta.getId() + ", " + ApplicationMeta.getName() + ") ");
        builder.constructWhere(fields);
        builder.setOrderBy(ApplicationMeta.getName());

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

    public ApplicationDO add(ApplicationDO data) throws Exception {
        Application entity;

        manager.setFlushMode(FlushModeType.COMMIT);
        entity = new Application();
        entity.setName(data.getName());
        entity.setDescription(data.getDescription());

        manager.persist(entity);
        data.setId(entity.getId());

        return data;
    }

    public ApplicationDO update(ApplicationDO data) throws Exception {
        Application entity;

        if ( !data.isChanged())
            return data;

        entity = manager.find(Application.class, data.getId());
        entity.setName(data.getName());
        entity.setDescription(data.getDescription());

        return data;
    }

    public void validate(ApplicationDO data) throws Exception {
        ValidationErrorsList list;
        ApplicationDO qdata;

        LocaleProxy.setLocaleProvider(new LocaleProvider() {
            @Override
            public Locale getLocale() {
                try {
                    return new Locale(user.getLocale());
                }catch(Exception e) {
                    return new Locale("en");
                }
            }
        });
        
        LocaleProxy.initialize();
        
        list = new ValidationErrorsList();
        if (DataBaseUtil.isEmpty(data.getName())) {
            list.add(new FieldErrorException(Messages.get().exc_fieldRequired(),
                                             ApplicationMeta.getName()));
        } else {
            try {
                qdata = fetchByName(data.getName());
                if ( !qdata.getId().equals(data.getId()))
                    list.add(new FieldErrorException(Messages.get().exc_fieldRequired(),
                                                     ApplicationMeta.getName()));
            } catch (NotFoundException ignE) {
                // ignore
            }
        }
        if (list.size() > 0)
            throw list;
    }
}
