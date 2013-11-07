/** Exhibit A - UIRF Open-source Based Public Software License.
* 
* The contents of this file are subject to the UIRF Open-source Based
* Public Software License(the "License"); you may not use this file except
* in compliance with the License. You may obtain a copy of the License at
* openelis.uhl.uiowa.edu
* 
* Software distributed under the License is distributed on an "AS IS"
* basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
* License for the specific language governing rights and limitations
* under the License.
* 
* The Original Code is OpenELIS code.
* 
* The Initial Developer of the Original Code is The University of Iowa.
* Portions created by The University of Iowa are Copyright 2006-2008. All
* Rights Reserved.
* 
* Contributor(s): ______________________________________.
* 
* Alternatively, the contents of this file marked
* "Separately-Licensed" may be used under the terms of a UIRF Software
* license ("UIRF Software License"), in which case the provisions of a
* UIRF Software License are applicable instead of those above. 
*/
package org.openelis.security.manager;

import java.io.Serializable;
import java.util.ArrayList;

import org.openelis.security.domain.DataObject;
import org.openelis.security.domain.SystemUserDO;
import org.openelis.security.domain.SystemUserModuleViewDO;
import org.openelis.security.domain.SystemUserSectionViewDO;

public class SystemUserManager implements Serializable {

    private static final long                         serialVersionUID = 1L;
    
    public enum Load {
        MODULES, SECTIONS  
    };

    protected SystemUserDO                            systemUser;
    protected ArrayList<SystemUserSectionViewDO>      sections;
    protected ArrayList<SystemUserModuleViewDO>       modules;
    protected ArrayList<DataObject>                   removed;
    
    transient public final UserSection                section = new UserSection();
    transient public final UserModule                 module  = new UserModule();
    
    /**
     * This is a protected constructor. See the three static methods for
     * allocation.
     */
    public SystemUserManager() {
        systemUser = new SystemUserDO();
    }

    public SystemUserDO getSystemUser() {
        return systemUser;
    }
    
    public class UserSection {
        
        public SystemUserSectionViewDO get(int i) {
            return sections.get(i);
        }
        
        public SystemUserSectionViewDO add() {
            SystemUserSectionViewDO data;
            
            data = new SystemUserSectionViewDO();
            add(data);
            
            return data;
        }
        
        public void add(SystemUserSectionViewDO section) {
            if(sections == null)
                sections = new ArrayList<SystemUserSectionViewDO>();
            sections.add(section);
            
        }
        
        public void remove(int i) {
            SystemUserSectionViewDO data;
            
            data = sections.remove(i);
            if(data.getId() != null && data.getId() > 0)
                removeDataObject(data);
        }
        
        public void remove(SystemUserSectionViewDO data) {
            if(sections.remove(data) && data.getId() != null && data.getId() > 0)
                removeDataObject(data);
        }
        
        public int count() {
            return sections != null ? sections.size() : 0;
        }
    }
    
    public class UserModule {
        
        public SystemUserModuleViewDO get(int i) {
            return modules.get(i);
        }
        
        public SystemUserModuleViewDO add() {
            SystemUserModuleViewDO data;
            
            data = new SystemUserModuleViewDO();
            add(data);
            
            return data;
        }
        
        public void add(SystemUserModuleViewDO module) {
            if(modules == null)
                modules = new ArrayList<SystemUserModuleViewDO>();
           
            modules.add(module);
        }
        
        public void remove(int i) {
            SystemUserModuleViewDO data;
            
            data = modules.remove(i);
            if(data.getId() != null && data.getId() > 0)
                removeDataObject(data);
        }
        
        public void remove(SystemUserModuleViewDO data) {
            if(modules.remove(data) && data.getId() != null && data.getId() > 0)
                removeDataObject(data);
        }
        
        public int count() {
            return modules != null ? modules.size() : 0;
        }
    }
    
    /**
     * Adds the specified data object to the list of objects that should be
     * removed from the database.
     */
    protected void removeDataObject(DataObject data) {
        removed.add(data);
    }
   
    
}
