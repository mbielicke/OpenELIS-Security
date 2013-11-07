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

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Singleton;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.jboss.security.annotation.SecurityDomain;
import org.openelis.security.messages.Messages;
import org.openelis.security.messages.SecurityMessages;
import org.openelis.security.remote.SystemUserPermissionRemote;
import org.openelis.ui.common.ModulePermission.ModuleFlags;
import org.openelis.ui.common.PermissionException;
import org.openelis.ui.common.SectionPermission.SectionFlags;
import org.openelis.ui.common.SystemUserPermission;
import org.openelis.ui.common.SystemUserVO;

import com.teklabs.gwt.i18n.client.LocaleFactory;
import com.teklabs.gwt.i18n.server.LocaleProxy;

/**
 * This class provides application level cache handling for user and permission
 */

@SecurityDomain("security")
@Singleton
public class UserCacheBean {

    @Resource
    private SessionContext   ctx;

    private Cache            cache, permCache;

    @EJB
    SystemUserPermissionRemote security;

    @EJB
    LockBean                 lock;

    @PostConstruct
    public void init() {
        CacheManager cm;

        cm = CacheManager.getInstance();
        cache = cm.getCache("user");
        permCache = cm.getCache("userperm");
    }

    /**
     * Returns the system user id associated with this context.
     */
    public Integer getId() throws Exception {
        SystemUserVO data;

        data = getSystemUser();
        if (data != null)
            return data.getId();

        return null;
    }

    /**
     * Returns the system user's login name associated with this context. Please
     * note that we concat username, sessionId, and locale on initial login and
     * you will need a special login class for JBOSS to parse the username.
     */
    public String getName() throws Exception {
        String parts[];

        parts = ctx.getCallerPrincipal().getName().split(";", 3);
        if (parts.length == 3)
            return parts[0];

        return null;
    }

    /**
     * Returns the system user's session id associated with this context. Please
     * note that we concat username, sessionId, and locale on initial login and
     * you will need a special login class for JBOSS to parse the username.
     */
    public String getSessionId() throws Exception {
        String parts[];

        parts = ctx.getCallerPrincipal().getName().split(";", 3);
        if (parts.length == 3)
            return parts[1];

        return "";
    }

    /**
     * Returns the system user's locale associated with this context. Please
     * note that we concat username, sessionId, and locale on initial login and
     * you will need a special login class for JBOSS to parse the username.
     */
    public String getLocale() throws Exception {
        String parts[];

        parts = ctx.getCallerPrincipal().getName().split(";", 3);
        if (parts.length == 3)
            return parts[2];

        return "";
    }

    /**
     * Returns the system user data associated with this context.
     */
    public SystemUserVO getSystemUser() throws Exception {
        Element e;
        String name;

        name = getName();
        e = cache.get(name);
        if (e != null)
            return (SystemUserVO)e.getValue();

        return getSystemUser(name);
    }

    /**
     * Returns the system user data for the specified id.
     */
    public SystemUserVO getSystemUser(Integer id) throws Exception {
        Element e;
        SystemUserVO data;

        e = cache.get(id);
        if (e != null)
            return (SystemUserVO)e.getValue();

        try {
            data = security.fetchById(id);
            cache.put(new Element(data.getId(), data));
            cache.put(new Element(data.getLoginName(), data));
        } catch (Exception e1) {
            data = null;
        }

        return data;
    }

    /**
     * Returns the system user data for the specified name.
     */
    public SystemUserVO getSystemUser(String name) throws Exception {
        Element e;
        SystemUserVO data;
        ArrayList<SystemUserVO> list;

        e = cache.get(name);
        if (e != null)
            return (SystemUserVO)e.getValue();

        data = null;
        try {
            list = getSystemUsers(name, 1);
            if (list != null && list.size() > 0) {
                data = list.get(0);
                cache.put(new Element(data.getId(), data));
                cache.put(new Element(data.getLoginName(), data));
            }
        } catch (Exception e1) {
            data = null;
        }

        return data;
    }

    /**
     * Throws permission exception if the user does not have permission to the
     * module for the specified operation.
     */
    public void applyPermission(String module, ModuleFlags flag) throws Exception {
        if ( !getPermission().has(module, flag))
            throw new PermissionException(Messages.get().exc_modulePerm(flag.name(), module));
    }

    /**
     * Throws permission exception if the user does not have permission to the
     * section for the specified operation.
     */
    public void applyPermission(String section, SectionFlags flag) throws Exception {
        if ( !getPermission().has(section, flag))
            throw new PermissionException(Messages.get().exc_sectionPerm(flag.name(), section));
    }

    /**
     * Returns the user permission for initial application login
     */
    public SystemUserPermission login() {
        try {
            return getPermission();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Removes the permission and various user caches for current user. This
     * method should be called on user logout.
     */
    public void logout() {
        String name;
        Element e;
        SystemUserPermission data;

        try {
            name = getName();
            e = permCache.get(name);
            if (e != null) {
                data = (SystemUserPermission)e.getValue();
                permCache.remove(name);
                cache.remove(data.getLoginName());
                cache.remove(data.getSystemUserId());
            }
            // we can't use the injected Lock because of circular reference; do
            // it the hard way
            lock.removeLocks();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    /**
     * Returns the system user data for matching name.
     */
    public ArrayList<SystemUserVO> getSystemUsers(String name, int max) throws Exception {
        ArrayList<SystemUserVO> list;

        try {
            list = security.fetchByLoginName(name, max);
        } catch (Exception e1) {
            list = null;
        }

        return list;
    }

    /**
     * Returns the user permission for current user.
     */
    public SystemUserPermission getPermission() throws Exception {
        Element e;
        String name, appName;
        SystemUserPermission data;

        name = getName();
        e = permCache.get(name);
        if (e != null)
            return (SystemUserPermission)e.getValue();

        data = null;
        try {
            appName = System.getProperty("org.openelis.security.system.security.application");
            data = security.fetchByApplicationAndLoginName(appName, name);
            permCache.put(new Element(name, data));
            cache.put(new Element(data.getLoginName(), data.getUser()));
            cache.put(new Element(data.getSystemUserId(), data.getUser()));
        } catch (Exception e1) {
            e1.printStackTrace();
            data = null;
        }

        return data;
    }
    
    
    public SecurityMessages getMessages() {
        try {
            LocaleProxy.initialize();
            return LocaleFactory.get(SecurityMessages.class,getLocale());
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    
}