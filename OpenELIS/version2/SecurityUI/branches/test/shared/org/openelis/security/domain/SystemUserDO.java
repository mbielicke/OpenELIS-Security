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
 * Class represents the fields in database table system_user.
 */
public class SystemUserDO extends DataObject {

    private static final long serialVersionUID = 1L;
    protected Integer         id;
    protected String          externalId, loginName, lastName, firstName,
                              initials, isEmployee, isActive, isTemplate;

    public SystemUserDO() {
    }

    public SystemUserDO(Integer id, String externalId, String loginName, 
                        String lastName,  String firstName, String initials,
                        String isEmployee, String isActive, String isTemplate) {
        setId(id);
        setExternalId(externalId);
        setLoginName(loginName);
        setLastName(lastName);
        setFirstName(firstName);
        setInitials(initials);
        setIsEmployee(isEmployee);
        setIsActive(isActive);
        setIsTemplate(isTemplate);
        _changed = false;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
        _changed = true;
    }
    
    public String getExternalId() {
        return externalId;
    }
    
    public void setExternalId(String externalId) {
        this.externalId = DataBaseUtil.trim(externalId);
        _changed = true;
    }
    
    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = DataBaseUtil.trim(loginName);
        _changed = true;
    }    

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = DataBaseUtil.trim(lastName);
        _changed = true;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = DataBaseUtil.trim(firstName);
        _changed = true;
    }
    
    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = DataBaseUtil.trim(initials);
        _changed = true;
    }

    public String getIsEmployee() {
        return isEmployee;
    }

    public void setIsEmployee(String isEmployee) {
        this.isEmployee = DataBaseUtil.trim(isEmployee);
        _changed = true;
    }
    
    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = DataBaseUtil.trim(isActive);
        _changed = true;
    }

    public String getIsTemplate() {
        return isTemplate;
    }

    public void setIsTemplate(String isTemplate) {
        this.isTemplate = DataBaseUtil.trim(isTemplate);
        _changed = true;
    }
}
