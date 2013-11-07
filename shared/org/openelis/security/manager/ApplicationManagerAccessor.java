package org.openelis.security.manager;

import java.util.ArrayList;

import org.openelis.security.domain.ApplicationDO;
import org.openelis.security.domain.SectionViewDO;
import org.openelis.security.domain.SystemModuleViewDO;

public class ApplicationManagerAccessor {
    
    public static void setApplication(ApplicationManager am, ApplicationDO app) {
        am.application = app;
    }
    
    public static void setModules(ApplicationManager am, ArrayList<SystemModuleViewDO> modules) {
        am.modules = modules;
    }
    
    public static void setSections(ApplicationManager am, ArrayList<SectionViewDO> sections) {
        am.sections = sections;
    }

}
