package org.openelis.security.modules.main.cache;

import org.openelis.gwt.common.SystemUserPermission;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UserCacheServiceIntAsync {

    void getPermission(AsyncCallback<SystemUserPermission> callback);

}
