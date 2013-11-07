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

import org.openelis.gwt.common.DataBaseUtil;

@NamedQueries({
               @NamedQuery(name = "Section.FetchById", query = "select new org.openelis.security.domain.SectionDO(id,applicationId,name,description)"
                                                               + " from Section where id = :id"),
               @NamedQuery(name = "Section.FetchByApplicationName", query = "select distinct new org.openelis.security.domain.IdNameVO(s.id, s.name)"
                                                                            + " from Section s, Application a where s.applicationId = a.id and a.name = :name"
                                                                            + " order by s.name"),
               @NamedQuery(name = "Section.FetchByApplicationId", query = "select new org.openelis.security.domain.SectionViewDO(id,applicationId,name,description, s.application.name)"
                                                                          + " from Section s where applicationId = :applicationId order by s.name")})
@Entity
@Table(name = "section")
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer                       id;

    @Column(name = "application_id")
    private Integer                       applicationId;

    @Column(name = "name")
    private String                        name;

    @Column(name = "description")
    private String                        description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id", insertable = false, updatable = false)
    private Application                   application;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "section", insertable = false, updatable = false)
    private Collection<SystemUserSection> systemUserSection;

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

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public Collection<SystemUserSection> getSystemUserSection() {
        return systemUserSection;
    }

    public void setSystemUserSection(Collection<SystemUserSection> systemUserSection) {
        this.systemUserSection = systemUserSection;
    }
}
