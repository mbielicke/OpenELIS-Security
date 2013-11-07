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
package org.openelis.security.entity;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.openelis.gwt.common.DataBaseUtil;

@NamedQueries({
               @NamedQuery(name = "SystemUser.FetchById", query = "select new org.openelis.security.domain.SystemUserDO(id,externalId,loginName,lastName,firstName,initials,isEmployee,isActive,isTemplate)"
                                                                  + " from SystemUser where id = :id"),
               @NamedQuery(name = "SystemUser.FetchByLoginName", query = "select new org.openelis.security.domain.SystemUserDO(id,externalId,loginName,lastName,firstName,initials,isEmployee,isActive,isTemplate)"
                                                                         + " from SystemUser where loginName = :loginName"),
               @NamedQuery(name = "SystemUser.FetchTemplateList", query = "select new org.openelis.security.domain.SystemUserDO(id,externalId,loginName,lastName,firstName,initials,isEmployee,isActive,isTemplate)"
                                                                          + " from SystemUser su where su.isTemplate = 'Y' order by su.loginName"),
               @NamedQuery(name = "SystemUser.FetchActiveByLoginName", query = "select distinct new org.openelis.security.domain.SystemUserDO(id,externalId,loginName,lastName,firstName,initials,isEmployee,isActive,isTemplate) "
                                                                               + " from SystemUser where isActive = 'Y' and isTemplate = 'N' and loginName like :loginName order by loginName"),
               @NamedQuery(name = "SystemUser.FetchActiveEmployeeByLoginName", query = "select distinct new org.openelis.security.domain.SystemUserDO(id,externalId,loginName,lastName,firstName,initials,isEmployee,isActive,isTemplate) "
                                                                                       + " from SystemUser where isActive = 'Y' and isTemplate = 'N' and isEmployee = 'Y' and loginName like :loginName order by loginName")})
@Entity
@Table(name = "system_user")
public class SystemUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer                       id;

    @Column(name = "external_id")
    private String                        externalId;

    @Column(name = "login_name")
    private String                        loginName;

    @Column(name = "last_name")
    private String                        lastName;

    @Column(name = "first_name")
    private String                        firstName;

    @Column(name = "initials")
    private String                        initials;

    @Column(name = "is_employee")
    private String                        isEmployee;

    @Column(name = "is_active")
    private String                        isActive;

    @Column(name = "is_template")
    private String                        isTemplate;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "system_user_id", insertable = false, updatable = false)
    private Collection<SystemUserSection> systemUserSection;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "system_user_id", insertable = false, updatable = false)
    private Collection<SystemUserModule>  systemUserModule;

    public Integer getId() {
        return id;
    }

    protected void setId(Integer id) {
        if (DataBaseUtil.isDifferent(id, this.id))
            this.id = id;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        if (DataBaseUtil.isDifferent(externalId, this.externalId))
            this.externalId = externalId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        if (DataBaseUtil.isDifferent(loginName, this.loginName))
            this.loginName = loginName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (DataBaseUtil.isDifferent(lastName, this.lastName))
            this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (DataBaseUtil.isDifferent(firstName, this.firstName))
            this.firstName = firstName;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        if (DataBaseUtil.isDifferent(initials, this.initials))
            this.initials = initials;
    }

    public String getIsEmployee() {
        return isEmployee;
    }

    public void setIsEmployee(String isEmployee) {
        if (DataBaseUtil.isDifferent(isEmployee, this.isEmployee))
            this.isEmployee = isEmployee;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        if (DataBaseUtil.isDifferent(isActive, this.isActive))
            this.isActive = isActive;
    }

    public String getIsTemplate() {
        return isTemplate;
    }

    public void setIsTemplate(String isTemplate) {
        if (DataBaseUtil.isDifferent(isTemplate, this.isTemplate))
            this.isTemplate = isTemplate;
    }

    public Collection<SystemUserModule> getSystemUserModule() {
        return systemUserModule;
    }

    public void setSystemUserModule(Collection<SystemUserModule> systemUserModule) {
        this.systemUserModule = systemUserModule;
    }

    public Collection<SystemUserSection> getSystemUserSection() {
        return systemUserSection;
    }

    public void setSystemUserSection(Collection<SystemUserSection> systemUserSection) {
        this.systemUserSection = systemUserSection;
    }
}
