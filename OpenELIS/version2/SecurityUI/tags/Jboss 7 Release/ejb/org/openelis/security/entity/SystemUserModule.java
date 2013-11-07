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
import javax.persistence.Table;

import org.openelis.ui.common.DataBaseUtil;

@NamedQueries({
    @NamedQuery( name = "SystemUserModule.FetchById",
                query = "select	new org.openelis.security.domain.SystemUserModuleDO(id,systemUserId,systemModuleId,hasSelect,hasAdd,hasUpdate,hasDelete,clause)"
                      + " from SystemUserModule where id = :id"),
    @NamedQuery( name = "SystemUserModule.FetchBySystemUserId",
                query = "select new org.openelis.security.domain.SystemUserModuleViewDO(um.id, um.systemUserId, um.systemModuleId," +
                		"um.hasSelect, um.hasAdd, um.hasUpdate, um.hasDelete,  um.clause, a.name, sm.name," +
                		"sm.description, sm.hasSelectFlag, sm.hasUpdateFlag, sm.hasAddFlag, sm.hasDeleteFlag)"
                      + " from SystemUserModule um inner join um.systemModule sm inner join sm.application a"
                      + " where um.systemUserId = :systemUserId  order by a.name, sm.name"),
    @NamedQuery( name = "SystemUserModule.FetchBySystemModuleId",
                query = "select new org.openelis.security.domain.SystemUserModuleDO(id,systemUserId,systemModuleId,hasSelect,hasAdd,hasUpdate,hasDelete,clause)"
                    +	" from SystemUserModule where systemModuleId = :systemModuleId"),
    @NamedQuery( name = "SystemUserModule.FetchBySystemUserIdAndSystemModuleId",
                query = "select new org.openelis.security.domain.SystemUserModuleDO(id,systemUserId,systemModuleId,hasSelect,hasAdd,hasUpdate,hasDelete,clause)"
                      + " from SystemUserModule where systemUserId = :systemUserId and systemModuleId = :systemModuleId"),    
    @NamedQuery( name = "SystemUserModule.FetchByUserIdAndApplication", 
                query = "select new org.openelis.ui.common.ModulePermission(sysMod.name,um.hasSelect,um.hasAdd,um.hasUpdate,um.hasDelete,um.clause) "
                      + " from SystemUserModule um inner join um.systemModule sysMod inner join sysMod.application app"
                      +	" where um.systemUserId = :systemUserId and app.name = :application")})
@Entity
@Table(name = "system_user_module")
public class SystemUserModule {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer          id;

    @Column(name = "system_user_id")
    private Integer          systemUserId;

    @Column(name = "system_module_id")
    private Integer          systemModuleId;

    @Column(name = "has_select")
    private String           hasSelect;

    @Column(name = "has_add")
    private String           hasAdd;

    @Column(name = "has_update")
    private String           hasUpdate;

    @Column(name = "has_delete")
    private String           hasDelete;

    @Column(name = "clause")
    private String           clause;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "system_module_id", insertable = false, updatable = false)
    private SystemModule     systemModule;

    public Integer getId() {
        return id;
    }

    protected void setId(Integer id) {
        if (DataBaseUtil.isDifferent(id, this.id))
            this.id = id;
    }

    public Integer getSystemUserId() {
        return systemUserId;
    }

    public void setSystemUserId(Integer systemUserId) {
        if (DataBaseUtil.isDifferent(systemUserId, this.systemUserId))
            this.systemUserId = systemUserId;
    }

    public Integer getSystemModuleId() {
        return systemModuleId;
    }

    public void setSystemModuleId(Integer systemModuleId) {
        if (DataBaseUtil.isDifferent(systemModuleId, this.systemModuleId))
            this.systemModuleId = systemModuleId;
    }

    public SystemModule getSystemModule() {
        return systemModule;
    }

    public void setSystemModule(SystemModule systemModule) {
        if (DataBaseUtil.isDifferent(systemModule, this.systemModule))
            this.systemModule = systemModule;
    }

    public String getHasSelect() {
        return hasSelect;
    }

    public void setHasSelect(String hasSelect) {
        if (DataBaseUtil.isDifferent(hasSelect, this.hasSelect))
            this.hasSelect = hasSelect;
    }

    public String getHasAdd() {
        return hasAdd;
    }

    public void setHasAdd(String hasAdd) {
        if (DataBaseUtil.isDifferent(hasAdd, this.hasAdd))
            this.hasAdd = hasAdd;
    }

    public String getHasUpdate() {
        return hasUpdate;
    }

    public void setHasUpdate(String hasUpdate) {
        if (DataBaseUtil.isDifferent(hasUpdate, this.hasUpdate))
            this.hasUpdate = hasUpdate;
    }

    public String getHasDelete() {
        return hasDelete;
    }

    public void setHasDelete(String hasDelete) {
        if (DataBaseUtil.isDifferent(hasDelete, this.hasDelete))
        this.hasDelete = hasDelete;
    }
    
    public String getClause() {
        return clause;
    }

    public void setClause(String clause) {
        if (DataBaseUtil.isDifferent(clause, this.clause))
            this.clause = clause;
    }
}
