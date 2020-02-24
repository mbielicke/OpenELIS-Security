package org.openelis.security.modules.main.cache;

import org.openelis.ui.common.SystemUserPermission;
import org.openelis.ui.screen.Callback;
import org.openelis.ui.services.TokenService;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.HasRpcToken;

public class UserCacheService implements UserCacheServiceInt, UserCacheServiceIntAsync {

    private UserCacheServiceIntAsync        service;

    private static UserCacheService         instance;

    public static UserCacheService get() {
        if (instance == null)
            instance = new UserCacheService();

        return instance;
    }

    private UserCacheService() {
        service = (UserCacheServiceIntAsync)GWT.create(UserCacheServiceInt.class);
        ((HasRpcToken)service).setRpcToken(TokenService.getToken());
    }

    @Override
    public void getPermission(AsyncCallback<SystemUserPermission> callback) {
        service.getPermission(callback);
    }

    @Override
    public SystemUserPermission getPermission() throws Exception {
        Callback<SystemUserPermission> callback;

        callback = new Callback<SystemUserPermission>();
        service.getPermission(callback);
        return callback.getResult();

    }

}
