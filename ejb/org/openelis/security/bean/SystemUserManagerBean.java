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

import static org.openelis.security.manager.SystemUserManagerAccessor.setSystemUser;
import static org.openelis.security.manager.SystemUserManagerAccessor.setSystemUserModules;
import static org.openelis.security.manager.SystemUserManagerAccessor.setSystemUserSections;

import java.util.Arrays;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.jboss.security.annotation.SecurityDomain;
import org.openelis.security.domain.DataObject;
import org.openelis.security.domain.ReferenceTable;
import org.openelis.security.domain.SystemUserModuleViewDO;
import org.openelis.security.domain.SystemUserSectionViewDO;
import org.openelis.security.manager.SystemUserManager;
import org.openelis.ui.common.DataBaseUtil;
import org.openelis.ui.common.NotFoundException;
import org.openelis.ui.common.ValidationErrorsList;
import org.openelis.ui.common.ModulePermission.ModuleFlags;

@Stateless
@SecurityDomain("security")
@RolesAllowed({"user-select", "templates-select"})
public class SystemUserManagerBean {

    @EJB
    private LockBean              lock;

    @EJB
    private SystemUserBean        user;

    @EJB
    private SystemUserSectionBean section;

    @EJB
    private SystemUserModuleBean  module;

    @EJB
    private UserCacheBean         userCache;

    public SystemUserManager fetchById(Integer id, SystemUserManager.Load... elements) throws Exception {
        SystemUserManager manager;

        manager = new SystemUserManager();
        setSystemUser(manager, user.fetchById(id));
        
        
        if (Arrays.asList(elements).contains(SystemUserManager.Load.SECTIONS))  { 
            try {
                setSystemUserSections(manager, section.fetchBySystemUserId(id));
            }catch(NotFoundException e) {
                // do nothing
            }
        }
        
        if (Arrays.asList(elements).contains(SystemUserManager.Load.MODULES)) {
            try {
                setSystemUserModules(manager, module.fetchBySystemUserId(id));
            }catch(NotFoundException e) {
                // do nothing
            }
        }

        return manager;
    }

    public SystemUserManager add(SystemUserManager man) throws Exception {
        Integer id;

        checkSecurity(ModuleFlags.ADD);

        validate(man);

        user.add(man.getSystemUser());
        id = man.getSystemUser().getId();

        for (int i = 0; i < man.module.count(); i++ ) {
        	man.module.get(i).setSystemUserId(id);
            module.add(man.module.get(i));
                    }

        for (int i = 0; i < man.section.count(); i++ ) {
        	man.section.get(i).setSystemUserId(id);
            section.add(man.section.get(i));
            
        }

        return man;

    }

    public void update(SystemUserManager man) throws Exception {
    	Integer id; 
    	
        checkSecurity(ModuleFlags.UPDATE);

        validate(man);

        lock.validateLock(ReferenceTable.SYSTEM_USER, man.getSystemUser().getId());

        user.update(man.getSystemUser());
        id = man.getSystemUser().getId();


        for (int i = 0; i < man.module.count(); i++) {	
        	if(man.module.get(i).getId() != null)
        		module.update(man.module.get(i));
        	else {
            	man.module.get(i).setSystemUserId(id);
        		module.add(man.module.get(i));
        	}
        }

        for (int i = 0; i < man.section.count(); i++ ) {
        	if(man.section.get(i).getId() != null)
        		section.update(man.section.get(i));
        	else {
        		man.section.get(i).setSystemUserId(id);
        		section.add(man.section.get(i));
        	}
        }
        
        if(man.removed != null && !man.removed.isEmpty()) {
            for(DataObject vo : man.removed) {
                if(vo instanceof SystemUserSectionViewDO)
                    section.delete((SystemUserSectionViewDO)vo);
                else
                    module.delete((SystemUserModuleViewDO)vo);
            }
        }

        lock.unlock(ReferenceTable.SYSTEM_USER, man.getSystemUser().getId());

    }

    public void delete(SystemUserManager man) throws Exception {

        checkSecurity(ModuleFlags.DELETE);

        lock.validateLock(ReferenceTable.SYSTEM_USER, man.getSystemUser().getId());

        user.delete(man.getSystemUser());

        for (int i = 0; i < man.module.count(); i++ )
            module.delete(man.module.get(i));

        for (int i = 0; i < man.section.count(); i++ )
            section.delete(man.section.get(i));

        lock.unlock(ReferenceTable.SYSTEM_USER, man.getSystemUser().getId());

    }

    public SystemUserManager fetchForUpdate(Integer id) throws Exception {

        lock.lock(ReferenceTable.SYSTEM_USER, id);

        return fetchById(id,SystemUserManager.Load.MODULES, SystemUserManager.Load.SECTIONS);

    }

    public SystemUserManager abortUpdate(Integer id) throws Exception {
        lock.unlock(ReferenceTable.SYSTEM_USER, id);
        return fetchById(id,SystemUserManager.Load.MODULES, SystemUserManager.Load.SECTIONS);
    }

    private void validate(SystemUserManager man) throws Exception {
        ValidationErrorsList list;

        list = new ValidationErrorsList();
        try {
            user.validate(man.getSystemUser());
        } catch (Exception e) {
            DataBaseUtil.mergeException(list, e);
        }
        try {
            for (int i = 0; i < man.module.count(); i++ )
                module.validate(man.module.get(i));
        } catch (Exception e) {
            DataBaseUtil.mergeException(list, e);
        }
        try {
            for (int i = 0; i < man.section.count(); i++ )
                section.validate(man.section.get(i));
        } catch (Exception e) {
            DataBaseUtil.mergeException(list, e);
        }

        if (list.size() > 0)
            throw list;
    }

    private void checkSecurity(ModuleFlags flag) throws Exception {
        userCache.applyPermission("user", flag);
    }
}
