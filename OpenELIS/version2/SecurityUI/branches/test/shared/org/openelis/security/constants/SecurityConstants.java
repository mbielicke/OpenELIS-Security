package org.openelis.security.constants;

import org.openelis.gwt.constants.LibraryConstants;

public interface SecurityConstants extends LibraryConstants {

    @Key("gen.clause")
    String clause();

    @Key("systemUser.moduleAddedToUser")
    String moduleAddedToUser();

    @Key("systemUser.sectionAddedToUser")
    String sectionAddedToUser();

    @Key("systemUser.deleteUserMessage")
    String deleteUserMessage();

    @Key("systemUser.userDeleteInvalid")
    String userDeleteInvalid();

    @Key("systemUser.userDuplicateInvalid")
    String userDuplicateInvalid();

    @Key("template.moduleAddedToTemplate")
    String moduleAddedToTemplate();

    @Key("template.sectionAddedToTemplate")
    String sectionAddedToTemplate();

    @Key("gen.name")
    String name();

    @Key("gen.description")
    String description();

    @Key("gen.selectPerm")
    String selectPerm();

    @Key("gen.addPerm")
    String addPerm();

    @Key("gen.updatePerm")
    String updatePerm();

    @Key("gen.deletePerm")
    String deletePerm();

    @Key("gen.addRow")
    String addRow();

    @Key("gen.removeRow")
    String removeRow();

    @Key("gen.showClause")
    String showClause();

    @Key("btn.ok")
    String ok();

    @Key("btn.cancel")
    String cancel();

    @Key("systemUser.users")
    String users();

    @Key("systemUser.loginName")
    String loginName();

    @Key("gen.modules")
    String modules();

    @Key("gen.selectApplication")
    String selectApplication();

    @Key("gen.sections")
    String sections();

    @Key("gen.templates")
    String templates();

    @Key("btn.options")
    String options();

    @Key("btn.duplicateRecord")
    String duplicate();

    @Key("systemUser.employee")
    String employee();

    @Key("systemUser.lastName")
    String lastName();

    @Key("systemUser.firstName")
    String firstName();

    @Key("gen.active")
    String active();

    @Key("systemUser.initials")
    String initials();

    @Key("systemUser.externalId")
    String externalId();

    @Key("gen.application")
    String application();

    @Key("gen.viewPerm")
    String viewPerm();

    @Key("gen.assignPerm")
    String assignPerm();

    @Key("gen.completePerm")
    String completePerm();

    @Key("gen.cancelPerm")
    String cancelPerm();

    @Key("gen.releasePerm")
    String releasePerm();

    @Key("gen.id")
    String id();

    @Key("menu.system")
    String system();

    @Key("menu.logout")
    String logout();

    @Key("menu.logoutDescription")
    String logoutDescription();

    @Key("gen.systemUser")
    String systemUser();

    @Key("menu.utilities")
    String utilities();

    @Key("menu.permissions")
    String permissions();
    
    @Key("application.sectionAssignedToUserException")
    String sectionAssignedToUser(String section);
    
    @Key("application.moduleAssignedToUserException")
    String moduleAssignedToUser(String module);
}
