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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.openelis.ui.common.DataBaseUtil;

@NamedQueries({
               @NamedQuery(name = "SystemModule.FetchById", query = "select	new org.openelis.security.domain.SystemModuleDO(id,applicationId,name,description,hasSelectFlag,hasAddFlag,hasUpdateFlag,hasDeleteFlag,clause)"
                                                                    + " from SystemModule where id = :id"),
               @NamedQuery(name = "SystemModule.FetchByApplicationId", query = "select new org.openelis.security.domain.SystemModuleViewDO(sm.id,sm.applicationId,sm.name,sm.description,sm.hasSelectFlag,sm.hasAddFlag,sm.hasUpdateFlag,sm.hasDeleteFlag,sm.clause,sm.application.name)"
                                                                               + " from SystemModule sm where sm.applicationId = :applicationId order by sm.name")})
@Entity
@Table(name = "system_module")
public class SystemModule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer                      id;

    @Column(name = "application_id")
    private Integer                      applicationId;

    @Column(name = "name")
    private String                       name;

    @Column(name = "description")
    private String                       description;

    @Column(name = "has_select_flag")
    private String                       hasSelectFlag;

    @Column(name = "has_add_flag")
    private String                       hasAddFlag;

    @Column(name = "has_update_flag")
    private String                       hasUpdateFlag;

    @Column(name = "has_delete_flag")
    private String                       hasDeleteFlag;

    @Column(name = "clause")
    private String                       clause;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id", insertable = false, updatable = false)
    private Application                  application;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "system_module_id", insertable = false, updatable = false)
    private Collection<SystemUserModule> systemUserModule;

    public Integer getId() {
        return id;
    }

    protected void setId(Integer id) {
        if (DataBaseUtil.isDifferent(id, this.id))
            this.id = id;
    }

    public Integer getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Integer applicationId) {
        if (DataBaseUtil.isDifferent(applicationId, this.applicationId))
            this.applicationId = applicationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (DataBaseUtil.isDifferent(name, this.name))
            this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (DataBaseUtil.isDifferent(description, this.description))
            this.description = description;
    }

    public String getHasAddFlag() {
        return hasAddFlag;
    }

    public void setHasAddFlag(String hasAddFlag) {
        if (DataBaseUtil.isDifferent(hasAddFlag, this.hasAddFlag))
            this.hasAddFlag = hasAddFlag;
    }

    public String getHasDeleteFlag() {
        return hasDeleteFlag;
    }

    public void setHasDeleteFlag(String hasDeleteFlag) {
        if (DataBaseUtil.isDifferent(hasDeleteFlag, this.hasDeleteFlag))
            this.hasDeleteFlag = hasDeleteFlag;
    }

    public String getHasSelectFlag() {
        return hasSelectFlag;
    }

    public void setHasSelectFlag(String hasSelectFlag) {
        if (DataBaseUtil.isDifferent(hasSelectFlag, this.hasSelectFlag))
            this.hasSelectFlag = hasSelectFlag;
    }

    public String getHasUpdateFlag() {
        return hasUpdateFlag;
    }

    public void setHasUpdateFlag(String hasUpdateFlag) {
        if (DataBaseUtil.isDifferent(hasUpdateFlag, this.hasUpdateFlag))
            this.hasUpdateFlag = hasUpdateFlag;
    }

    public String getClause() {
        return clause;
    }

    public void setClause(String clause) {
        if (DataBaseUtil.isDifferent(clause, this.clause))
            this.clause = clause;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public Collection<SystemUserModule> getSystemUserModule() {
        return systemUserModule;
    }

    public void setSystemUserModule(Collection<SystemUserModule> systemUserModule) {
        this.systemUserModule = systemUserModule;
    }
}
