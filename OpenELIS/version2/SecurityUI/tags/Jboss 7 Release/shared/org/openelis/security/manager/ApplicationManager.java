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

import org.openelis.security.domain.ApplicationDO;
import org.openelis.security.domain.DataObject;
import org.openelis.security.domain.SectionViewDO;
import org.openelis.security.domain.SystemModuleViewDO;

public class ApplicationManager implements Serializable {

    private static final long     serialVersionUID = 1L;
    
    public enum Load {
        MODULES, SECTIONS  
    };

    protected ApplicationDO                            application;
    protected ArrayList<SectionViewDO>                 sections;
    protected ArrayList<SystemModuleViewDO>            modules;
    public    ArrayList<DataObject>                    removed;
    
    transient public final Section                     section = new Section();
    transient public final Module                      module = new Module();
    
    /**
     * This is a protected constructor. See the three static methods for
     * allocation.
     */
    public ApplicationManager() {
        application = new ApplicationDO();
        sections = null;
        modules = null;
    }
    
    public ApplicationDO getApplication() {
        return application;
    }
    
    public class Section {
        public SectionViewDO get(int i) {
            return sections.get(i);
        }
        
        public SectionViewDO add() {
            SectionViewDO data;
            
            data = new SectionViewDO();
            add(data);
            
            return data;
        }
        
        public void add(SectionViewDO section) {
            if(sections == null)
                sections = new ArrayList<SectionViewDO>();
            sections.add(section);
            
        }
        
        public void remove(int i) {
            SectionViewDO data;
            
            data = sections.remove(i);
            if(data.getId() != null && data.getId() > 0)
                removeDataObject(data);
        }
        
        public void remove(SectionViewDO data) {
            if(sections.remove(data) && data.getId() != null && data.getId() > 0)
                removeDataObject(data);
        }
        
        public int count() {
            return sections != null ? sections.size() : 0;
        }
    }
    
    public class Module {
        
        public SystemModuleViewDO get(int i) {
            return modules.get(i);
        }
        
        public SystemModuleViewDO add() {
            SystemModuleViewDO data;
            
            data = new SystemModuleViewDO();
            add(data);
            
            return data;
        }
        
        public void add(SystemModuleViewDO module) {
            if(modules == null)
                modules = new ArrayList<SystemModuleViewDO>();
           
            modules.add(module);
        }
        
        public void remove(int i) {
            SystemModuleViewDO data;
            
            data = modules.remove(i);
            if(data.getId() != null && data.getId() > 0)
                removeDataObject(data);
        }
        
        public void remove(SystemModuleViewDO data) {
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
