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
              @NamedQuery( name = "SystemUserSection.FetchBySystemUserId",
                          query = "select new org.openelis.security.domain.SystemUserSectionViewDO(sus.id, sus.systemUserId, sus.sectionId," +
                          	      "sus.hasView,sus.hasAssign, sus.hasComplete, sus.hasRelease, sus.hasCancel, a.name, sec.name, sec.description)" 
                                + " from SystemUserSection sus left join sus.section sec left join sec.application a"
                                + " where sus.systemUserId = :systemUserId  order by a.name, sec.name"),
              @NamedQuery( name = "SystemUserSection.FetchBySectionId",
                          query = "select new org.openelis.security.domain.SystemUserSectionDO(sus.id, sus.systemUserId, sus.sectionId," +
                                  "sus.hasView,sus.hasAssign, sus.hasComplete, sus.hasRelease, sus.hasCancel)"
                                + " from SystemUserSection sus where sectionId = :sectionId"),
              @NamedQuery( name = "SystemUserSection.FetchById",
                          query = "select new org.openelis.security.domain.SystemUserSectionDO(sus.id, sus.systemUserId, sus.sectionId," +
                                  "sus.hasView,sus.hasAssign, sus.hasComplete, sus.hasRelease, sus.hasCancel)"
                                + " from SystemUserSection sus where id = :id"),              
              @NamedQuery( name = "SystemUserSection.FetchByUserIdAndApplication",
                          query = "select new org.openelis.ui.common.SectionPermission(sec.name,sus.hasView,sus.hasAssign,sus.hasComplete,sus.hasRelease,sus.hasCancel)"
                                + " from SystemUserSection sus left join sus.section sec left join sec.application app where sus.systemUserId = :systemUserId and app.name = :application")})
              
@Entity
@Table(name = "system_user_section")
public class SystemUserSection {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer           id;

    @Column(name = "system_user_id")
    private Integer           systemUserId;

    @Column(name = "section_id")
    private Integer           sectionId;

    @Column(name = "has_view")
    private String            hasView;

    @Column(name = "has_assign")
    private String            hasAssign;

    @Column(name = "has_complete")
    private String            hasComplete;

    @Column(name = "has_release")
    private String            hasRelease;

    @Column(name = "has_cancel")
    private String            hasCancel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id", insertable = false, updatable = false)
    private Section           section;

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

    public Integer getSectionId() {
        return sectionId;
    }

    public void setSectionId(Integer sectionId) {
        if (DataBaseUtil.isDifferent(sectionId, this.sectionId))
            this.sectionId = sectionId;
    }
    
    public String getHasView() {
        return hasView;
    }

    public void setHasView(String hasView) {
        if (DataBaseUtil.isDifferent(hasView, this.hasView))
            this.hasView = hasView;
    }

    public String getHasAssign() {
        return hasAssign;
    }

    public void setHasAssign(String hasAssign) {
        if (DataBaseUtil.isDifferent(hasAssign, this.hasAssign))
            this.hasAssign = hasAssign;
    }

    public String getHasComplete() {
        return hasComplete;
    }

    public void setHasComplete(String hasComplete) {
        if (DataBaseUtil.isDifferent(hasComplete, this.hasComplete))
            this.hasComplete = hasComplete;
    }

    public String getHasRelease() {
        return hasRelease;
    }
    
    public void setHasRelease(String hasRelease) {
        if (DataBaseUtil.isDifferent(hasRelease, this.hasRelease))
            this.hasRelease = hasRelease;
    }
    
    public String getHasCancel() {
        return hasCancel;
    }

    public void setHasCancel(String hasCancel) {
        if (DataBaseUtil.isDifferent(hasCancel, this.hasCancel))
            this.hasCancel = hasCancel;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }
}
