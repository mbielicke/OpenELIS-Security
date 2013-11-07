package org.openelis.security.modules.main.cache;

import java.util.ArrayList;
import java.util.HashMap;

import org.openelis.ui.common.SystemUserPermission;
import org.openelis.ui.common.SystemUserVO;

import com.google.gwt.user.client.Window;

/**
 * Class provides cache service handling for front end GWT classes. Cache
 * objects in GWT instance are cached for the duration of the session and are
 * not updated -- if objects in the back-end are updated, the user will need to
 * restart the session to get updated objects.
 */
public class UserCache {

    protected static SystemUserPermission           perm;
    protected static HashMap<Integer, SystemUserVO> users;

    static {
        users = new HashMap<Integer, SystemUserVO>();
    }

    public static Integer getId() throws Exception {
        getPermission();
        return perm.getSystemUserId();
    }

    public static String getName() throws Exception {
        getPermission();
        return perm.getLoginName();
    }

    public static SystemUserPermission getPermission() {
        try {
            if (perm == null)
                perm = UserCacheService.get().getPermission();
        } catch (Exception e) {
            Window.alert("Failed to get Permissions - " + e.getMessage());
            perm = new SystemUserPermission();
        }
        return perm;
    }
}
