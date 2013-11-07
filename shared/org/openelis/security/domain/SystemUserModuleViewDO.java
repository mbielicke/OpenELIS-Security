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
package org.openelis.security.domain;

import org.openelis.gwt.common.DataBaseUtil;

/**
 * The class extends system_user_module DO. The additional field is for read/display
 * only and do not get committed to the daatabase. Note: isChanged will reflect
 * any changes to read/display fields.
 */
public class SystemUserModuleViewDO extends SystemUserModuleDO {

    private static final long serialVersionUID = 1L;

    protected String          systemModuleApplicationName, systemModuleName, systemModuleDescription,
                              systemModuleHasSelectFlag, systemModuleHasUpdateFlag,
                              systemModuleHasAddFlag, systemModuleHasDeleteFlag;
        
    public SystemUserModuleViewDO(){        
    }
    
    public SystemUserModuleViewDO(Integer id, Integer systemUserId, Integer systemModuleId,
                                  String hasSelect, String hasAdd, String hasUpdate,
                                  String hasDelete,  String clause, String systemModuleApplicationName,
                                  String systemModuleName, String systemModuleDescription,
                                  String systemModuleHasSelectFlag, String systemModulehasUpdateFlag, 
                                  String systemModuleHasAddFlag, String systemModuleHasDeleteFlag){
        super(id, systemUserId, systemModuleId, hasSelect, hasAdd, hasUpdate,
              hasDelete, clause);
        setSystemModuleApplicationName(systemModuleApplicationName);
        setSystemModuleName(systemModuleName);
        setSystemModuleDescription(systemModuleDescription);
        setSystemModuleHasAddFlag(systemModuleHasAddFlag);
        setSystemModuleHasSelectFlag(systemModuleHasSelectFlag);
        setSystemModuleHasUpdateFlag(systemModulehasUpdateFlag);
        setSystemModuleHasDeleteFlag(systemModuleHasDeleteFlag);
    }

    public String getSystemModuleApplicationName() {
        return systemModuleApplicationName;
    }

    public void setSystemModuleApplicationName(String systemModuleApplicationName) {
        this.systemModuleApplicationName = DataBaseUtil.trim(systemModuleApplicationName);
    }    

    public String getSystemModuleName() {
        return systemModuleName;
    }

    public void setSystemModuleName(String systemModuleName) {
        this.systemModuleName = DataBaseUtil.trim(systemModuleName);
    }

    public String getSystemModuleDescription() {
        return systemModuleDescription;
    }

    public void setSystemModuleDescription(String systemModuleDescription) {
        this.systemModuleDescription = DataBaseUtil.trim(systemModuleDescription);
    }

    public String getSystemModuleHasAddFlag() {
        return systemModuleHasAddFlag;
    }

    public void setSystemModuleHasAddFlag(String systemModuleHasAddFlag) {
        this.systemModuleHasAddFlag = DataBaseUtil.trim(systemModuleHasAddFlag);
    }

    public String getSystemModuleHasSelectFlag() {
        return systemModuleHasSelectFlag;
    }

    public String getSystemModuleHasUpdateFlag() {
        return systemModuleHasUpdateFlag;
    }

    public void setSystemModuleHasUpdateFlag(String systemModuleHasUpdateFlag) {
        this.systemModuleHasUpdateFlag = DataBaseUtil.trim(systemModuleHasUpdateFlag);
    }

    public void setSystemModuleHasSelectFlag(String hasSelectFlag) {
        this.systemModuleHasSelectFlag = DataBaseUtil.trim(hasSelectFlag);
    }    

    public String getSystemModuleHasDeleteFlag() {
        return systemModuleHasDeleteFlag;
    }

    public void setSystemModuleHasDeleteFlag(String systemModuleHasDeleteFlag) {
        this.systemModuleHasDeleteFlag = DataBaseUtil.trim(systemModuleHasDeleteFlag);
    }
}
