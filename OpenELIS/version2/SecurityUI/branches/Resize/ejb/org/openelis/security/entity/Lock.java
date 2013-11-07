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

/**
 * Lock Entity POJO for database
 */

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.openelis.gwt.common.DataBaseUtil;

@NamedQueries({@NamedQuery(name = "Lock.FetchBySessionId", query = "select l "
                                                                   + " from Lock l where l.sessionId = :id")})
@Entity
@Table(name = "lock")
public class Lock {

    @EmbeddedId
    private PK      pk;

    @Column(name = "expires")
    private Long    expires;

    @Column(name = "system_user_id")
    private Integer systemUserId;

    @Column(name = "session_id")
    private String  sessionId;

    public Lock() {
        pk = new PK();
    }

    public Integer getReferenceTableId() {
        return pk.referenceTableId;
    }

    public void setReferenceTableId(Integer referenceTableId) {
        pk.referenceTableId = referenceTableId;
    }

    public Integer getReferenceId() {
        return pk.referenceId;
    }

    public void setReferenceId(Integer referenceId) {
        pk.referenceId = referenceId;
    }

    public Long getExpires() {
        return expires;
    }

    public void setExpires(Long expires) {
        this.expires = expires;
    }

    public Integer getSystemUserId() {
        return systemUserId;
    }

    public void setSystemUserId(Integer systemUserId) {
        this.systemUserId = systemUserId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    /**
     * Implements the primary key for lock table
     */
    @Embeddable
    public static class PK implements Serializable {

        @Column(name = "reference_table_id")
        private Integer           referenceTableId;

        @Column(name = "reference_id")
        private Integer           referenceId;

        private static final long serialVersionUID = 1L;

        public PK() {
        }

        public PK(Integer referenceTableId, Integer referenceId) {
            this.referenceTableId = referenceTableId;
            this.referenceId = referenceId;
        }

        public Integer getReferenceTableId() {
            return referenceTableId;
        }

        public void setReferenceTableId(Integer referenceTableId) {
            this.referenceTableId = referenceTableId;
        }

        public Integer getReferenceId() {
            return referenceId;
        }

        public void setReferenceId(Integer referenceId) {
            this.referenceId = referenceId;
        }

        public int hashCode() {
            return referenceTableId.hashCode() * 200 + referenceId.hashCode();
        }

        public boolean equals(Object arg) {
            return (arg instanceof PK &&
                    !DataBaseUtil.isDifferent( ((PK)arg).referenceTableId, referenceTableId) && !DataBaseUtil.isDifferent( ((PK)arg).referenceId,
                                                                                                                          referenceId));
        }
    }
}
