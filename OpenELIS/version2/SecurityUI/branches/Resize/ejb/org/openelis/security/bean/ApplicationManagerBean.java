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

import java.util.Arrays;
import java.util.EnumSet;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.jboss.security.annotation.SecurityDomain;
import org.openelis.gwt.common.DataBaseUtil;
import org.openelis.gwt.common.ValidationErrorsList;
import org.openelis.gwt.common.ModulePermission.ModuleFlags;
import org.openelis.security.domain.ApplicationDO;
import org.openelis.security.domain.ReferenceTable;
import org.openelis.security.manager.ApplicationManager;

import static org.openelis.security.manager.ApplicationManagerAccessor.*;

@Stateless
@SecurityDomain("security")
@RolesAllowed("application-select")
public class ApplicationManagerBean {

    @EJB
    private LockBean       lock;
    
    @EJB
    private ApplicationBean application;

    @EJB
    private SectionBean section;
    
    @EJB
    private SystemModuleBean module;
    
    @EJB
    private UserCacheBean  userCache;

    public ApplicationManager fetchById(Integer id, ApplicationManager.Load... elements) throws Exception {
        ApplicationDO data;
        ApplicationManager m;
        
        data = application.fetchById(id);
        m = new ApplicationManager();
        
        setApplication(m,data);
        
        if(Arrays.asList(elements).contains(ApplicationManager.Load.MODULES)) 
            setModules(m,module.fetchByApplicationId(id));
        
        if(Arrays.asList(elements).contains(ApplicationManager.Load.SECTIONS))
            setSections(m,section.fetchByApplicationId(id));
        
        return m;
    }

    public ApplicationManager add(ApplicationManager man) throws Exception {
        Integer applicationId;
        checkSecurity(ModuleFlags.ADD);

        validate(man);

        application.add(man.getApplication());
        applicationId = man.getApplication().getId();
        
        for(int i = 0; i < man.module.count(); i++) {
            man.module.get(i).setApplicationId(applicationId);
            module.add(man.module.get(i));
        }
        
        for(int i = 0; i < man.section.count(); i++) {
            man.section.get(i).setApplicationId(applicationId);
            section.add(man.section.get(i));
        }

        return man;
    }

    public void update(ApplicationManager man) throws Exception {
    	Integer applicationId;
        checkSecurity(ModuleFlags.UPDATE);

        validate(man);

        lock.validateLock(ReferenceTable.APPLICATION, man.getApplication().getId());
        
        application.update(man.getApplication());
        applicationId = man.getApplication().getId();

        
        for(int i = 0; i < man.module.count(); i++) {
        	if(man.module.get(i).getId() != null)
        		module.update(man.module.get(i));
        	else {
        		man.module.get(i).setApplicationId(applicationId);
        		module.add(man.module.get(i));
        	}
        }

        for(int i = 0; i < man.section.count(); i++)  {
        	if(man.section.get(i).getId() != null)
        		section.update(man.section.get(i));
        	else {
        		man.section.get(i).setApplicationId(applicationId);
        		section.add(man.section.get(i));
        	}
        }
        
        lock.unlock(ReferenceTable.APPLICATION, man.getApplication().getId());

    }

    public ApplicationManager fetchForUpdate(Integer id) throws Exception {
       lock.lock(ReferenceTable.APPLICATION, id);
       return fetchById(id, ApplicationManager.Load.MODULES,ApplicationManager.Load.SECTIONS);
    }

    public ApplicationManager abortUpdate(Integer id) throws Exception {
        lock.unlock(ReferenceTable.APPLICATION, id);
        return fetchById(id, ApplicationManager.Load.MODULES,ApplicationManager.Load.SECTIONS);
    }

    private void checkSecurity(ModuleFlags flag) throws Exception {
        userCache.applyPermission("application", flag);
    }
    
    public void validate(ApplicationManager man) throws Exception {        
        ValidationErrorsList list;
        
        list = new ValidationErrorsList();
        try {
            application.validate(man.getApplication());
        } catch (Exception e) {
            DataBaseUtil.mergeException(list, e);
        }
        try {
            for(int i = 0; i < man.module.count(); i++)
                module.validate(man.module.get(i));
        } catch (Exception e) {
            DataBaseUtil.mergeException(list, e);
        }
        try {
            for(int i = 0; i < man.section.count(); i++)
                section.validate(man.section.get(i));
        } catch (Exception e) {
            DataBaseUtil.mergeException(list, e);
        }
        
        if (list.size() > 0)
            throw list;
    }
}
