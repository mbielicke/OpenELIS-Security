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

import org.openelis.ui.common.DataBaseUtil;

/**
 * Class represents the fields in database table system_user_section.
 */
public class SystemUserSectionDO extends DataObject {

    private static final long serialVersionUID = 1L;
    protected Integer         id, systemUserId, sectionId;
    protected String          hasView, hasAssign, hasComplete, hasRelease, hasCancel;

    public SystemUserSectionDO() {
    }

    public SystemUserSectionDO(Integer id, Integer systemUserId, Integer sectionId, String hasView,
                               String hasAssign, String hasComplete, String hasRelease,
                               String hasCancel) {
        setId(id);
        setSystemUserId(systemUserId);
        setSectionId(sectionId);
        setHasView(hasView);
        setHasAssign(hasAssign);
        setHasComplete(hasComplete);
        setHasRelease(hasRelease);
        setHasCancel(hasCancel);
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
        _changed = true;
    }
    
    public Integer getSectionId() {
        return sectionId;
    }

    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
        _changed = true;
    } 

    public String getHasView() {
        return hasView;
    }

    public void setHasView(String hasView) {
        this.hasView = DataBaseUtil.trim(hasView);
        _changed = true;
    }

    public String getHasAssign() {
        return hasAssign;
    }

    public void setHasAssign(String hasAssign) {
        this.hasAssign = DataBaseUtil.trim(hasAssign);
        _changed = true;
    }

    public String getHasComplete() {
        return hasComplete;
    }

    public void setHasComplete(String hasComplete) {
        this.hasComplete = DataBaseUtil.trim(hasComplete);
        _changed = true;
    }

    public String getHasRelease() {
        return hasRelease;
    }

    public void setHasRelease(String hasRelease) {
        this.hasRelease = DataBaseUtil.trim(hasRelease);
        _changed = true;
    }

    public String getHasCancel() {
        return hasCancel;
    }

    public void setHasCancel(String hasCancel) {
        this.hasCancel = DataBaseUtil.trim(hasCancel);
        _changed = true;
    }
}
