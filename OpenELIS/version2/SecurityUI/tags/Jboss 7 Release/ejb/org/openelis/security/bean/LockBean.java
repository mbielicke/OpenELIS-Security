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
package org.openelis.security.bean;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.openelis.security.entity.Lock;
import org.openelis.security.entity.Lock.PK;
import org.openelis.security.messages.Messages;
import org.openelis.security.messages.SecurityMessages;
import org.openelis.ui.common.EntityLockedException;
import org.openelis.ui.common.SystemUserVO;

import com.teklabs.gwt.i18n.server.LocaleProvider;
import com.teklabs.gwt.i18n.server.LocaleProxy;

@Stateless
public class LockBean {

    @PersistenceContext
    private EntityManager manager;
    
    @EJB
    UserCacheBean userCache;

    private static int    DEFAULT_LOCK_TIME = 15 * 60 * 1000, // 15 M * 60 S * 1000 Millis
                          GRACE_LOCK_TIME = 2 * 60 * 1000;
    
    @PostConstruct
    protected void init() {
        LocaleProxy.setLocaleProvider(new LocaleProvider() {
            @Override
            public Locale getLocale() {
                try {
                    return new Locale(userCache.getLocale());
                }catch(Exception e) {
                    return new Locale("en");
                }
            }
        });
        
        LocaleProxy.initialize();
    }
    
    /**
     * Method creates a new lock entry for the specified table reference and id.
     * If a valid lock currently exist, a EntityLockedException is thrown.
     */
    public void lock(int referenceTableId, int referenceId) throws Exception {
        lock(referenceTableId, referenceId, DEFAULT_LOCK_TIME);
    }

    /**
     * Method creates a new lock entry for the specified table reference and id
     * for the specified time on milliseconds. If a valid lock currently exist,
     * a EntityLockedException is thrown.
     */
    public void lock(int referenceTableId, int referenceId, long lockTimeMillis) throws Exception {
        PK pk;
        Lock lock;
        long timeMillis;
        Integer userId;
        SystemUserVO user;
              
        userId = userCache.getId();
        timeMillis = System.currentTimeMillis();

        pk = new Lock.PK(referenceTableId, referenceId);
        try {
            lock = manager.find(Lock.class, pk);
            if (lock == null) {
                lock = new Lock();
                lock.setReferenceTableId(referenceTableId);
                lock.setReferenceId(referenceId);
                lock.setSystemUserId(userId);
                lock.setExpires(lockTimeMillis + timeMillis);
                lock.setSessionId(userCache.getSessionId());
                manager.persist(lock);
            } else if (lock.getExpires() < timeMillis) {
                //
                // if the lock has expired, then we can take it over
                //
                lock.setSystemUserId(userId);
                lock.setExpires(lockTimeMillis + timeMillis);
                lock.setSessionId(userCache.getSessionId());
            } else {
                user = userCache.getSystemUser(lock.getSystemUserId());
                throw new EntityLockedException(Messages.get().exc_entityLock(user.getLoginName(), new Date(lock.getExpires())));
            }
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * This method removes the lock entry for the specified reference table and
     * id. The lock record must be owned by the user before the lock is removed.
     */
    public void unlock(int referenceTableId, int referenceId) {
        PK pk;
        Lock lock;
        Integer userId;
        String sessionId;
        
        manager.setFlushMode(FlushModeType.COMMIT);

        pk = new Lock.PK(referenceTableId, referenceId);
        try {
            userId = userCache.getId();
            sessionId = userCache.getSessionId();
            lock = manager.find(Lock.class, pk);
            if (lock != null && lock.getSystemUserId().equals(userId) &&
                lock.getSessionId().equals(sessionId))
                manager.remove(lock);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method will search for an existing lock for the specified reference
     * table and id. If a lock is not found or the lock does not belong to the
     * calling user, the method throws EntityLockException specifying that the
     * lock is not valid. Note that expired locks are valid (because no one else
     * has requested for the same resource to be locked) and this method resets
     * the expiration time of expired or nearly expire locks by a constant grace
     * time.
     */
    public void validateLock(int referenceTableId, int referenceId) throws Exception {
        PK pk;
        Lock lock;
        long timeMillis;
        Integer userId;
        String sessionId;
        
        pk = new Lock.PK(referenceTableId, referenceId);
        try {
            lock = manager.find(Lock.class, pk);
            userId = userCache.getId();
            sessionId = userCache.getSessionId();
        } catch (Exception e) {
            lock = null;
            userId = null;
            sessionId = null;
        }

        if (lock == null || !lock.getSystemUserId().equals(userId) ||
            !lock.getSessionId().equals(sessionId))
            throw new EntityLockedException(Messages.get().exc_expiredLock());
        //
        // if the lock has expired, we are going to refresh its expiration time
        //
        timeMillis = System.currentTimeMillis();
        if (lock.getExpires() < timeMillis - GRACE_LOCK_TIME)
            lock.setExpires(timeMillis + GRACE_LOCK_TIME);
    }

    /**
     * Removes all the locks for a user's session. This action is often called
     * from logout.
     */
    public void removeLocks() {
        String sessionId;
        
        try {
            sessionId = userCache.getSessionId();
            removeLocks(sessionId);
        } catch (Exception e) {
            // ignore
        }
    }

    public void removeLocks(String sessionId) {
        Query query;
        List<Lock> locks;

        if (sessionId == null || sessionId.trim().length() < 1)
            return;
        manager.setFlushMode(FlushModeType.COMMIT);

        query = manager.createNamedQuery("Lock.FetchBySessionId");
        query.setParameter("id", sessionId);

        locks = query.getResultList();
        for (Lock lock : locks)
            manager.remove(lock);
    }
}