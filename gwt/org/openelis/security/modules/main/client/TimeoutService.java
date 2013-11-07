package org.openelis.security.modules.main.client;

import org.openelis.ui.common.Datetime;
import org.openelis.ui.screen.Callback;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class TimeoutService implements TimeoutServiceInt, TimeoutServiceIntAsync {

    private TimeoutServiceIntAsync service;

    private static TimeoutService  instance;

    public static TimeoutService get() {
        if (instance == null)
            instance = new TimeoutService();

        return instance;
    }

    private TimeoutService() {
        service = (TimeoutServiceIntAsync)GWT.create(TimeoutServiceInt.class);
    }

    @Override
    public void getLastAccess(AsyncCallback<Datetime> callback) {
        service.getLastAccess(callback);
    }

    @Override
    public Datetime getLastAccess() throws Exception {
        Callback<Datetime> callback;

        callback = new Callback<Datetime>();
        service.getLastAccess(callback);
        return callback.getResult();
    }

    @Override
    public void keepAlive(AsyncCallback<Void> callback) {
        service.keepAlive(callback);
    }

    @Override
    public void logout(AsyncCallback<Void> callback) {
        service.logout(callback);
    }

    @Override
    public void keepAlive() throws Exception {
        Callback<Void> callback;

        callback = new Callback<Void>();
        service.keepAlive(callback);
    }

    @Override
    public void logout() throws Exception {
        Callback<Void> callback;

        callback = new Callback<Void>();
        service.logout(callback);
    }

}
