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

import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jboss.security.annotation.SecurityDomain;
import org.openelis.security.domain.SystemUserSectionDO;
import org.openelis.security.domain.SystemUserSectionViewDO;
import org.openelis.security.entity.SystemUserSection;
import org.openelis.security.messages.Messages;
import org.openelis.security.messages.SecurityMessages;
import org.openelis.security.meta.SystemUserMeta;
import org.openelis.ui.common.DataBaseUtil;
import org.openelis.ui.common.FieldErrorException;
import org.openelis.ui.common.NotFoundException;
import org.openelis.ui.common.ValidationErrorsList;

import com.teklabs.gwt.i18n.server.LocaleProvider;
import com.teklabs.gwt.i18n.server.LocaleProxy;

@Stateless
@SecurityDomain("security")
@RolesAllowed({"user-select", "templates-select"})
public class SystemUserSectionBean {

    @PersistenceContext(unitName = "newsecurity")
    private EntityManager     manager;

    @EJB
    private UserCacheBean     userCache;

    @PostConstruct
    protected void init() {
        LocaleProxy.setLocaleProvider(new LocaleProvider() {
            @Override
            public Locale getLocale() {
                try {
                    return new Locale(userCache.getLocale());
                }catch(Exception e) {
                    return new Locale("en");
                }
            }
        });
        
        LocaleProxy.initialize();
    }

    public ArrayList<SystemUserSectionViewDO> fetchBySystemUserId(Integer id) throws Exception {
        Query query;
        List list;

        query = manager.createNamedQuery("SystemUserSection.FetchBySystemUserId");
        query.setParameter("systemUserId", id);

        list = query.getResultList();
        if (list.isEmpty())
            throw new NotFoundException();

        return DataBaseUtil.toArrayList(list);
    }

    public ArrayList<SystemUserSectionDO> fetchBySectionId(Integer id) throws Exception {
        Query query;
        List list;

        query = manager.createNamedQuery("SystemUserSection.FetchBySectionId");
        query.setParameter("sectionId", id);

        list = query.getResultList();
        if (list.isEmpty())
            throw new NotFoundException();

        return DataBaseUtil.toArrayList(list);
    }

    public SystemUserSectionViewDO add(SystemUserSectionViewDO data) throws Exception {
        SystemUserSection entity;

        manager.setFlushMode(FlushModeType.COMMIT);
        entity = new SystemUserSection();
        entity.setSystemUserId(data.getSystemUserId());
        entity.setSectionId(data.getSectionId());
        entity.setHasView(data.getHasView());
        entity.setHasAssign(data.getHasAssign());
        entity.setHasComplete(data.getHasComplete());
        entity.setHasRelease(data.getHasRelease());
        entity.setHasCancel(data.getHasCancel());

        manager.persist(entity);
        data.setId(entity.getId());

        return data;
    }

    public SystemUserSectionViewDO update(SystemUserSectionViewDO data) throws Exception {
        SystemUserSection entity;

        if ( !data.isChanged())
            return data;

        manager.setFlushMode(FlushModeType.COMMIT);
        entity = manager.find(SystemUserSection.class, data.getId());
        entity.setSystemUserId(data.getSystemUserId());
        entity.setSectionId(data.getSectionId());
        entity.setHasView(data.getHasView());
        entity.setHasAssign(data.getHasAssign());
        entity.setHasComplete(data.getHasComplete());
        entity.setHasRelease(data.getHasRelease());
        entity.setHasCancel(data.getHasCancel());

        return data;
    }

    public void delete(SystemUserSectionViewDO data) throws Exception {
        SystemUserSection entity;

        manager.setFlushMode(FlushModeType.COMMIT);
        entity = manager.find(SystemUserSection.class, data.getId());
        if (entity != null)
            manager.remove(entity);
    }

    public void validate(SystemUserSectionViewDO data) throws Exception {
        ValidationErrorsList list;

        list = new ValidationErrorsList();

        if ( !"Y".equals(data.getHasView()))
            list.add(new FieldErrorException(Messages.get().exc_viewPermRequired(),
                                             SystemUserMeta.getSectionHasView()));

        if (list.size() > 0)
            throw list;
    }
}
