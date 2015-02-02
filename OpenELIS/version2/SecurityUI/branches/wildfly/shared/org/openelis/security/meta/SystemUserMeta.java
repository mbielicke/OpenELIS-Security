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
package org.openelis.security.meta;

import java.util.Arrays;
import java.util.HashSet;

import org.openelis.ui.common.Meta;
import org.openelis.ui.common.MetaMap;

public class SystemUserMeta implements Meta, MetaMap {   
    
    private static final String ID = "_systemUser.id",
                               EXTERNAL_ID = "_systemUser.externalId",
                               LOGIN_NAME = "_systemUser.loginName",
                               LAST_NAME = "_systemUser.lastName",
                               FIRST_NAME = "_systemUser.firstName",
                               INITIALS = "_systemUser.initials",     
                               IS_EMPLOYEE = "_systemUser.isEmployee",
                               IS_ACTIVE = "_systemUser.isActive",
                               IS_TEMPLATE = "_systemUser.isTemplate",
    
                               MOD_ID = "_systemUserModule.id",
                               MOD_SYSTEM_USER_ID = "_systemUserModule.systemUserId",
                               MOD_SYSTEM_MODULE_ID = "_systemUserModule.systemModuleId",
                               MOD_HAS_SELECT = "_systemUserModule.hasSelect", 
                               MOD_HAS_ADD = "_systemUserModule.hasAdd",   
                               MOD_HAS_UPDATE = "_systemUserModule.hasUpdate", 
                               MOD_HAS_DELETE = "_systemUserModule.hasDelete",
                               MOD_CLAUSE = "_systemUserModule.clause",
                               
                               SECT_ID = "_systemUserSection.id",       
                               SECT_SYSTEM_USER_ID = "_systemUserSection.systemUserId",
                               SECT_SECTION_ID = "_systemUserSection.sectionId",
                               SECT_HAS_VIEW = "_systemUserSection.hasView", 
                               SECT_HAS_ASSIGN = "_systemUserSection.hasAssign",   
                               SECT_HAS_COMPLETE = "_systemUserSection.hasComplete", 
                               SECT_HAS_RELEASE = "_systemUserSection.hasRelease",
                               SECT_HAS_CANCEL = "_systemUserSection.hasCancel",
    
                               MOD_SYSTEM_MODULE_APPLICATION_NAME = "_systemModule.application.name",
                               MOD_SYSTEM_MODULE_NAME = "_systemModule.name",
                               MOD_SYSTEM_MODULE_DESCRIPTION = "_systemModule.description",
                               
                               SECT_SECTION_APPLICATION_NAME = "_section.application.name",
                               SECT_SECTION_NAME = "_section.name",
                               SECT_SECTION_DESCRIPTION = "_section.description";
          
    private static HashSet<String> names;
    
    static {
        names = new HashSet<String>(Arrays.asList(ID, EXTERNAL_ID, LOGIN_NAME, LAST_NAME, FIRST_NAME,
                                                  INITIALS, IS_EMPLOYEE, IS_ACTIVE, IS_TEMPLATE, SECT_ID,       
                                                  SECT_SYSTEM_USER_ID, SECT_SECTION_ID, SECT_HAS_VIEW, 
                                                  SECT_HAS_ASSIGN, SECT_HAS_COMPLETE, SECT_HAS_RELEASE,
                                                  SECT_HAS_CANCEL, MOD_ID, MOD_SYSTEM_USER_ID,
                                                  MOD_SYSTEM_MODULE_ID, MOD_HAS_SELECT, MOD_HAS_ADD,
                                                  MOD_HAS_UPDATE, MOD_HAS_DELETE, MOD_CLAUSE, 
                                                  MOD_SYSTEM_MODULE_APPLICATION_NAME, MOD_SYSTEM_MODULE_NAME,
                                                  MOD_SYSTEM_MODULE_DESCRIPTION, SECT_SECTION_APPLICATION_NAME, 
                                                  SECT_SECTION_NAME, SECT_SECTION_DESCRIPTION));
      
    
    }
    
    public static String getId(){
        return ID;
    }
    
    public static String getExternalId(){
        return EXTERNAL_ID;
    }
    
    public static String getLoginName(){
        return LOGIN_NAME;
    }
    
    public static String getLastName(){
        return LAST_NAME;
    }
    
    public static String getFirstName(){
        return FIRST_NAME;
    }
    
    public static String getInitials(){
        return INITIALS;
    }
    
    public static String getIsEmployee(){
        return IS_EMPLOYEE;
    }
    
    public static String getIsActive(){
        return IS_ACTIVE;
    }
    
    public static String getIsTemplate(){
        return IS_TEMPLATE;
    }   
    
    public static String getModuleId() {
        return MOD_ID;
    }
    
    public static String getModuleSystemUserId() {
        return MOD_SYSTEM_USER_ID;
    }
    
    public static String getModuleSystemModuleId() {
        return MOD_SYSTEM_MODULE_ID;
    }
    
    public static String getModuleHasSelect() {
        return MOD_HAS_SELECT;
    }
    
    public static String getModuleHasAdd() {
        return MOD_HAS_ADD;
    }
    
    public static String getModuleHasUpdate() {
        return MOD_HAS_UPDATE;
    }
    
    public static String getModuleHasDelete() {
        return MOD_HAS_DELETE;
    }
    
    public static String getModuleClause() {
        return MOD_CLAUSE;
    }
        
    public static String getSectionId() {
        return SECT_ID;       
    }
    
    public static String getSectionSystemUserId() {
        return SECT_SYSTEM_USER_ID;
    }
    
    public static String getSectionSectionId() {
        return SECT_SECTION_ID;
    }
    
    public static String getSectionHasView() {
        return SECT_HAS_VIEW;
    }
    
    public static String getSectionHasAssign() {
        return SECT_HAS_ASSIGN;
    }
    
    public static String getSectionHasComplete() {
        return SECT_HAS_COMPLETE;
    }
    
    public static String getSectionHasRelease() {
        return SECT_HAS_RELEASE; 
    }

    public static String getSectionHasCancel() {        
        return SECT_HAS_CANCEL;
    }
    
    public static String getModuleSystemModuleApplicationName() {
        return MOD_SYSTEM_MODULE_APPLICATION_NAME;
    }
    
    public static String getModuleSystemModuleName() {
        return MOD_SYSTEM_MODULE_NAME;
    } 
    
    public static String getModuleSystemModuleDescription() {
        return MOD_SYSTEM_MODULE_DESCRIPTION;
    }
    
    public static String getSectionApplicationName() {
        return SECT_SECTION_APPLICATION_NAME; 
    }
    
    public static String getSectionSectionName() {
        return SECT_SECTION_NAME;
    }
    
    public static String getSectionSectionDescription() {
        return SECT_SECTION_DESCRIPTION;
    }
    
    public boolean hasColumn(String columnName) {
        return names.contains(columnName);
    }

    public String buildFrom(String where) {
        String from;
        
        from = "SystemUser _systemUser ";
        if (where.indexOf("systemUserSection.") > -1)
            from += ",IN (_systemUser.systemUserSection) _systemUserSection ";
        if (where.indexOf("systemUserModule.") > -1)
            from += ",IN (_systemUser.systemUserModule) _systemUserModule ";
        if (where.indexOf("systemModule.") > -1) {
            if (where.indexOf("systemUserModule.") == -1) 
                from += ",IN (_systemUser.systemUserModule) _systemUserModule ";
            from += ",IN (_systemUserModule.systemModule) _systemModule ";
        }
        if (where.indexOf("section.") > -1) {
            if (where.indexOf("systemUserSection.") == -1)
                from += ",IN (_systemUser.systemUserSection) _systemUserSection ";
            from += ",IN (_systemUserSection.section) _section ";
        }

        return from;    
    }
}
