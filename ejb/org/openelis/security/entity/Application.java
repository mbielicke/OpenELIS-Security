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
               @NamedQuery(name = "Application.FetchById", query = "select	new org.openelis.security.domain.ApplicationDO(id,name,description)"
                                                                   + " from Application where id = :id"),
               @NamedQuery(name = "Application.FetchList", query = "select new org.openelis.security.domain.ApplicationDO(id,name,description)"
                                                                   + " from Application order by name"),
               @NamedQuery(name = "Application.FetchByName", query = "select new org.openelis.security.domain.ApplicationDO(id,name,description)"
                                                                     + " from Application where name = :name")})
@Entity
@Table(name = "application")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer                  id;

    @Column(name = "name")
    private String                   name;

    @Column(name = "description")
    private String                   description;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id")
    private Collection<Section>      section;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id")
    private Collection<SystemModule> systemModule;

    public Integer getId() {
        return id;
    }

    protected void setId(Integer id) {
        if (DataBaseUtil.isDifferent(id, this.id))
            this.id = id;
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

    public Collection<Section> getSection() {
        return section;
    }

    public void setSection(Collection<Section> section) {
        this.section = section;
    }

    public Collection<SystemModule> getSystemModule() {
        return systemModule;
    }

    public void setSystemModule(Collection<SystemModule> systemModule) {
        this.systemModule = systemModule;
    }
}
