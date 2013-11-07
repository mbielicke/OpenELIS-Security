package org.openelis.security.modules.main.cache;

import org.openelis.ui.common.SystemUserPermission;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("userCache")
public interface UserCacheServiceInt extends RemoteService {

    public SystemUserPermission getPermission() throws Exception;
}