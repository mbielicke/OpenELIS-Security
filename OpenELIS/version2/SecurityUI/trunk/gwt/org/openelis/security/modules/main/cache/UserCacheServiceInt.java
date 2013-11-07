package org.openelis.security.modules.main.cache;

import org.openelis.ui.common.SystemUserPermission;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.client.rpc.XsrfProtectedService;

@RemoteServiceRelativePath("userCache")
public interface UserCacheServiceInt extends XsrfProtectedService {

    public SystemUserPermission getPermission() throws Exception;
}