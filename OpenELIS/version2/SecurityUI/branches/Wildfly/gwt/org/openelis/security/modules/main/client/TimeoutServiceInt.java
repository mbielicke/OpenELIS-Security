package org.openelis.security.modules.main.client;

import org.openelis.ui.common.Datetime;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.client.rpc.XsrfProtectedService;

@RemoteServiceRelativePath("timeout")
public interface TimeoutServiceInt extends XsrfProtectedService {

    public Datetime getLastAccess() throws Exception;

    public void keepAlive() throws Exception;

    public void logout() throws Exception;
}
