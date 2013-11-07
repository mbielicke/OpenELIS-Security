package org.openelis.security.modules.main.client;

import org.openelis.gwt.common.Datetime;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface TimeoutServiceIntAsync {

    void getLastAccess(AsyncCallback<Datetime> callback);

    void keepAlive(AsyncCallback<Void> callback);

    void logout(AsyncCallback<Void> callback);

}
