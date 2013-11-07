package org.openelis.security.modules.main.client;

import org.openelis.gwt.common.Datetime;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("timeout")
public interface TimeoutServiceInt extends RemoteService {

    public Datetime getLastAccess() throws Exception;

    public void keepAlive() throws Exception;

    public void logout() throws Exception;
}
