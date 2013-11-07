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
package org.openelis.security.domain;

import org.openelis.gwt.common.DataBaseUtil;

/**
 * Class represents the fields in database table system_user_module.
 */

public class SystemUserModuleDO extends DataObject {

    private static final long serialVersionUID = 1L;
    protected Integer         id, systemUserId, systemModuleId;
    protected String          hasSelect, hasAdd, hasUpdate, hasDelete, clause;

    public SystemUserModuleDO() {
    }

    public SystemUserModuleDO(Integer id, Integer systemUserId, Integer systemModuleId,
                              String hasSelect, String hasAdd, String hasUpdate,
                              String hasDelete,  String clause) {
        setId(id);
        setSystemUserId(systemUserId);
        setSystemModuleId(systemModuleId);
        setHasSelect(hasSelect);
        setHasAdd(hasAdd);
        setHasUpdate(hasUpdate);
        setHasDelete(hasDelete);
        setClause(clause);
        _changed = false;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
        _changed = true;
    }    

    public Integer getSystemUserId() {
        return systemUserId;
    }

    public void setSystemUserId(Integer systemUserId) {
        this.systemUserId = systemUserId;
    }
    
    public Integer getSystemModuleId() {
        return systemModuleId;        
    }

    public void setSystemModuleId(Integer systemModuleId) {
        this.systemModuleId = systemModuleId;
        _changed = true;
    }

    public String getHasSelect() {
        return hasSelect;
    }

    public void setHasSelect(String hasSelect) {
        this.hasSelect = DataBaseUtil.trim(hasSelect);
        _changed = true;
    }

    public String getHasAdd() {
        return hasAdd;
    }

    public void setHasAdd(String hasAdd) {
        this.hasAdd = DataBaseUtil.trim(hasAdd);
        _changed = true;
    }

    public String getHasUpdate() {
        return hasUpdate;
    }

    public void setHasUpdate(String hasUpdate) {
        this.hasUpdate = DataBaseUtil.trim(hasUpdate);
        _changed = true;
    }

    public String getHasDelete() {
        return hasDelete;
    }

    public void setHasDelete(String hasDelete) {
        this.hasDelete = DataBaseUtil.trim(hasDelete);
        _changed = true;
    }

    public String getClause() {
        return clause;
    }

    public void setClause(String clause) {
        this.clause = DataBaseUtil.trim(clause);
        _changed = true;
    }
}
