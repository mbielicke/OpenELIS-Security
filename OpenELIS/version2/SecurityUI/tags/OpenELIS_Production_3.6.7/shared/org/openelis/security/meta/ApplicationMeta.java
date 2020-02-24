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


public class ApplicationMeta implements Meta, MetaMap {   
    
    private static final String ID = "_application.id",
                               NAME = "_application.name",
                               DESCRIPTION = "_application.description",   
                               
                               SECT_ID = "_section.id",
                               SECT_APPLICATION_ID = "_section.applicationId",
                               SECT_NAME = "_section.name",
                               SECT_DESCRIPTION = "_section.description",
                               
                               SYS_MOD_ID = "_systemModule.id",
                               SYS_MOD_APPLICATION_ID = "_systemModule.applicationId",
                               SYS_MOD_NAME = "_systemModule.name",
                               SYS_MOD_DESCRIPTION = "_systemModule.description",
                               SYS_MOD_HAS_SELECT_FLAG = "_systemModule.hasSelectFlag", 
                               SYS_MOD_HAS_ADD_FLAG = "_systemModule.hasAddFlag",   
                               SYS_MOD_HAS_UPDATE_FLAG = "_systemModule.hasUpdateFlag", 
                               SYS_MOD_HAS_DELETE_FLAG = "_systemModule.hasDeleteFlag",
                               SYS_MOD_CLAUSE = "_systemModule.clause";
    
    private static HashSet<String> names;
    
    static {
        names = new HashSet<String>(Arrays.asList(ID, NAME, DESCRIPTION, SECT_ID, SECT_APPLICATION_ID,
                                                  SECT_NAME, SECT_DESCRIPTION, SYS_MOD_ID, 
                                                  SYS_MOD_APPLICATION_ID, SYS_MOD_NAME, SYS_MOD_DESCRIPTION,
                                                  SYS_MOD_HAS_SELECT_FLAG, SYS_MOD_HAS_ADD_FLAG, 
                                                  SYS_MOD_HAS_UPDATE_FLAG, SYS_MOD_HAS_DELETE_FLAG,SYS_MOD_CLAUSE));
    }    
    
    public static String getId() {
        return ID;
    }
    
    public static String getName() {
        return NAME;
    }
    
    public static String getDescription() {
        return DESCRIPTION;
    }
    
    public static String getSectionId() {
       return SECT_ID;
    }
    
    public static String getSectionApplicationId() {
       return SECT_APPLICATION_ID;       
    }
    
    public static String getSectionName() {
       return SECT_NAME;
    }
    
    public static String getSectionDescription() {
        return SECT_DESCRIPTION;
    }
    
    public static String getSystemModuleId (){
       return SYS_MOD_ID; 
    }
    
    public static String getSystemModuleApplicationId() {
       return SYS_MOD_APPLICATION_ID;
    }
    
    public static String getSystemModuleName() {
       return SYS_MOD_NAME;
    } 
    
    public static String getSystemModuleDescription() {
       return SYS_MOD_DESCRIPTION;
    }
    
    public static String getSystemModuleHasSelectFlag() {        
        return SYS_MOD_HAS_SELECT_FLAG;
    } 
    
    public static String getSystemModuleHasAddFlag() {
        return SYS_MOD_HAS_ADD_FLAG; 
    }
    
    public static String getSystemModuleHasUpdateFlag() {
        return SYS_MOD_HAS_UPDATE_FLAG;
    }
    
    public static String getSystemModuleHasDeleteFlag() {
        return SYS_MOD_HAS_DELETE_FLAG;
    }
    
    public static String getSystemModuleClause() {
        return SYS_MOD_CLAUSE;
    }
    
    public boolean hasColumn(String columnName) {
        return names.contains(columnName);
    }
    
    public String buildFrom(String where) {
        String from;
        
        from = "Application _application ";
        if (where.indexOf("section.") > -1)
            from += ",IN (_application.section) _section ";
        if (where.indexOf("systemModule.") > -1)
            from += ",IN (_application.systemModule) _systemModule ";

        return from;
    }        
}
