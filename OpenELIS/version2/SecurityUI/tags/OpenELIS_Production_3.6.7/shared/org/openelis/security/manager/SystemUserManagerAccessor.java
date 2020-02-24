package org.openelis.security.manager;

import java.util.ArrayList;

import org.openelis.security.domain.SystemUserDO;
import org.openelis.security.domain.SystemUserModuleViewDO;
import org.openelis.security.domain.SystemUserSectionViewDO;

public class SystemUserManagerAccessor {
    
    public static SystemUserDO getSystemUser(SystemUserManager sum) {
        return sum.systemUser;
    }
    
    public static void setSystemUser(SystemUserManager sum, SystemUserDO user) {
        sum.systemUser = user;
    }
    
    public static void setSystemUserSections(SystemUserManager sum, ArrayList<SystemUserSectionViewDO> sections) {
        sum.sections = sections;
    }
    
    public static void setSystemUserModules(SystemUserManager sum, ArrayList<SystemUserModuleViewDO> modules) {
        sum.modules = modules;
    }

}
