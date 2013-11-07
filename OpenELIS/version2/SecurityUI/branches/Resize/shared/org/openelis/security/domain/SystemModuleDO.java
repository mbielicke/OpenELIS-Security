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
 * Class represents the fields in database table system_module.
 */
public class SystemModuleDO extends DataObject {

    private static final long serialVersionUID = 1L;

    protected Integer         id, applicationId;
    protected String          name, description;
    protected String          hasSelectFlag, hasAddFlag, hasUpdateFlag,
                              hasDeleteFlag, clause;
    
    public SystemModuleDO() {        
    }

    public SystemModuleDO(Integer id, Integer applicationId, String name, String description,
                          String hasSelectFlag, String hasAddFlag, String hasUpdateFlag,
                          String hasDeleteFlag, String clause) {
        setId(id);
        setApplicationId(applicationId);
        setName(name);
        setDescription(description);
        setHasSelectFlag(hasSelectFlag);
        setHasAddFlag(hasAddFlag);
        setHasUpdateFlag(hasUpdateFlag);
        setHasDeleteFlag(hasDeleteFlag);
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

    public Integer getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Integer applicationId) {
        this.applicationId = applicationId;
        _changed = true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = DataBaseUtil.trim(name);
        _changed = true;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = DataBaseUtil.trim(description);
        _changed = true;
    }

    public String getHasSelectFlag() {
        return hasSelectFlag;
    }

    public void setHasSelectFlag(String hasSelectFlag) {
        this.hasSelectFlag = DataBaseUtil.trim(hasSelectFlag);
        _changed = true;
    }

    public String getHasAddFlag() {
        return hasAddFlag;
    }

    public void setHasAddFlag(String hasAddFlag) {
        this.hasAddFlag = DataBaseUtil.trim(hasAddFlag);
        _changed = true;
    }

    public String getHasUpdateFlag() {
        return hasUpdateFlag;
    }

    public void setHasUpdateFlag(String hasUpdateFlag) {
        this.hasUpdateFlag = DataBaseUtil.trim(hasUpdateFlag);
        _changed = true;
    }

    public String getHasDeleteFlag() {
        return hasDeleteFlag;
    }

    public void setHasDeleteFlag(String hasDeleteFlag) {
        this.hasDeleteFlag = DataBaseUtil.trim(hasDeleteFlag);
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
