package org.openelis.security.messages;

/**
 * Interface to represent the messages contained in resource bundle:
 * 	/home/tschmidt/juno/workspace/Security/shared/org/openelis/security/messages/SecurityMessages.properties'.
 */
public interface SecurityMessages extends org.openelis.ui.messages.UIMessages {
  
  /**
   * Translated "Description".
   * 
   * @return translated "Description"
   */
  @DefaultMessage("Description")
  @Key("application.description")
  String application_description();

  /**
   * Translated "The system module {0} cannot be deleted as it has been added to one or more users".
   * 
   * @return translated "The system module {0} cannot be deleted as it has been added to one or more users"
   */
  @DefaultMessage("The system module {0} cannot be deleted as it has been added to one or more users")
  @Key("application.moduleAssignedToUserException")
  String application_moduleAssignedToUserException(String arg0);

  /**
   * Translated "Name".
   * 
   * @return translated "Name"
   */
  @DefaultMessage("Name")
  @Key("application.name")
  String application_name();

  /**
   * Translated "The section {0} cannot be deleted as it has been added to one or more users    ".
   * 
   * @return translated "The section {0} cannot be deleted as it has been added to one or more users    "
   */
  @DefaultMessage("The section {0} cannot be deleted as it has been added to one or more users    ")
  @Key("application.sectionAssignedToUserException")
  String application_sectionAssignedToUserException(String arg0);

  /**
   * Translated "Abort".
   * 
   * @return translated "Abort"
   */
  @DefaultMessage("Abort")
  @Key("btn.abort")
  String btn_abort();

  /**
   * Translated "Add".
   * 
   * @return translated "Add"
   */
  @DefaultMessage("Add")
  @Key("btn.add")
  String btn_add();

  /**
   * Translated "Cancel".
   * 
   * @return translated "Cancel"
   */
  @DefaultMessage("Cancel")
  @Key("btn.cancel")
  String btn_cancel();

  /**
   * Translated "Commit".
   * 
   * @return translated "Commit"
   */
  @DefaultMessage("Commit")
  @Key("btn.commit")
  String btn_commit();

  /**
   * Translated "Delete".
   * 
   * @return translated "Delete"
   */
  @DefaultMessage("Delete")
  @Key("btn.delete")
  String btn_delete();

  /**
   * Translated "Duplicate".
   * 
   * @return translated "Duplicate"
   */
  @DefaultMessage("Duplicate")
  @Key("btn.duplicateRecord")
  String btn_duplicateRecord();

  /**
   * Translated "Duplicate the current record".
   * 
   * @return translated "Duplicate the current record"
   */
  @DefaultMessage("Duplicate the current record")
  @Key("btn.duplicateRecordDescription")
  String btn_duplicateRecordDescription();

  /**
   * Translated "Next".
   * 
   * @return translated "Next"
   */
  @DefaultMessage("Next")
  @Key("btn.next")
  String btn_next();

  /**
   * Translated "OK".
   * 
   * @return translated "OK"
   */
  @DefaultMessage("OK")
  @Key("btn.ok")
  String btn_ok();

  /**
   * Translated "Options".
   * 
   * @return translated "Options"
   */
  @DefaultMessage("Options")
  @Key("btn.options")
  String btn_options();

  /**
   * Translated "Previous".
   * 
   * @return translated "Previous"
   */
  @DefaultMessage("Previous")
  @Key("btn.previous")
  String btn_previous();

  /**
   * Translated "Query".
   * 
   * @return translated "Query"
   */
  @DefaultMessage("Query")
  @Key("btn.query")
  String btn_query();

  /**
   * Translated "Update".
   * 
   * @return translated "Update"
   */
  @DefaultMessage("Update")
  @Key("btn.update")
  String btn_update();

  /**
   * Translated "Applications".
   * 
   * @return translated "Applications"
   */
  @DefaultMessage("Applications")
  @Key("ge.applications")
  String ge_applications();

  /**
   * Translated "Active".
   * 
   * @return translated "Active"
   */
  @DefaultMessage("Active")
  @Key("gen.active")
  String gen_active();

  /**
   * Translated "Add aborted".
   * 
   * @return translated "Add aborted"
   */
  @DefaultMessage("Add aborted")
  @Key("gen.addAborted")
  String gen_addAborted();

  /**
   * Translated "Add".
   * 
   * @return translated "Add"
   */
  @DefaultMessage("Add")
  @Key("gen.addPerm")
  String gen_addPerm();

  /**
   * Translated "Add Row".
   * 
   * @return translated "Add Row"
   */
  @DefaultMessage("Add Row")
  @Key("gen.addRow")
  String gen_addRow();

  /**
   * Translated "Adding...".
   * 
   * @return translated "Adding..."
   */
  @DefaultMessage("Adding...")
  @Key("gen.adding")
  String gen_adding();

  /**
   * Translated "Adding...Complete".
   * 
   * @return translated "Adding...Complete"
   */
  @DefaultMessage("Adding...Complete")
  @Key("gen.addingComplete")
  String gen_addingComplete();

  /**
   * Translated "Application".
   * 
   * @return translated "Application"
   */
  @DefaultMessage("Application")
  @Key("gen.application")
  String gen_application();

  /**
   * Translated "Asn".
   * 
   * @return translated "Asn"
   */
  @DefaultMessage("Asn")
  @Key("gen.assignPerm")
  String gen_assignPerm();

  /**
   * Translated "Authentication Failure".
   * 
   * @return translated "Authentication Failure"
   */
  @DefaultMessage("Authentication Failure")
  @Key("gen.authFailure")
  String gen_authFailure();

  /**
   * Translated "Canceling changes ...".
   * 
   * @return translated "Canceling changes ..."
   */
  @DefaultMessage("Canceling changes ...")
  @Key("gen.cancelChanges")
  String gen_cancelChanges();

  /**
   * Translated "Cncl".
   * 
   * @return translated "Cncl"
   */
  @DefaultMessage("Cncl")
  @Key("gen.cancelPerm")
  String gen_cancelPerm();

  /**
   * Translated "Clause".
   * 
   * @return translated "Clause"
   */
  @DefaultMessage("Clause")
  @Key("gen.clause")
  String gen_clause();

  /**
   * Translated "Cmp".
   * 
   * @return translated "Cmp"
   */
  @DefaultMessage("Cmp")
  @Key("gen.completePerm")
  String gen_completePerm();

  /**
   * Translated "Please correct the errors indicated, then press Commit".
   * 
   * @return translated "Please correct the errors indicated, then press Commit"
   */
  @DefaultMessage("Please correct the errors indicated, then press Commit")
  @Key("gen.correctErrors")
  String gen_correctErrors();

  /**
   * Translated "Delete Aborted".
   * 
   * @return translated "Delete Aborted"
   */
  @DefaultMessage("Delete Aborted")
  @Key("gen.deleteAborted")
  String gen_deleteAborted();

  /**
   * Translated "Delete...Complete".
   * 
   * @return translated "Delete...Complete"
   */
  @DefaultMessage("Delete...Complete")
  @Key("gen.deleteComplete")
  String gen_deleteComplete();

  /**
   * Translated "Pressing commit will delete the current record from the database".
   * 
   * @return translated "Pressing commit will delete the current record from the database"
   */
  @DefaultMessage("Pressing commit will delete the current record from the database")
  @Key("gen.deleteMessage")
  String gen_deleteMessage();

  /**
   * Translated "Del".
   * 
   * @return translated "Del"
   */
  @DefaultMessage("Del")
  @Key("gen.deletePerm")
  String gen_deletePerm();

  /**
   * Translated "Deleting...".
   * 
   * @return translated "Deleting..."
   */
  @DefaultMessage("Deleting...")
  @Key("gen.deleting")
  String gen_deleting();

  /**
   * Translated "Description".
   * 
   * @return translated "Description"
   */
  @DefaultMessage("Description")
  @Key("gen.description")
  String gen_description();

  /**
   * Translated "Enter fields to query by then press Commit".
   * 
   * @return translated "Enter fields to query by then press Commit"
   */
  @DefaultMessage("Enter fields to query by then press Commit")
  @Key("gen.enterFieldsToQuery")
  String gen_enterFieldsToQuery();

  /**
   * Translated "Enter information in the fields, then press Commit.".
   * 
   * @return translated "Enter information in the fields, then press Commit."
   */
  @DefaultMessage("Enter information in the fields, then press Commit.")
  @Key("gen.enterInformationPressCommit")
  String gen_enterInformationPressCommit();

  /**
   * Translated "This record is locked by {0} until {1}".
   * 
   * @return translated "This record is locked by {0} until {1}"
   */
  @DefaultMessage("This record is locked by {0} until {1}")
  @Key("gen.entityLockException")
  String gen_entityLockException(String arg0,  String arg1);

  /**
   * Translated "Your Lock on this record has expired; Please abort and try again".
   * 
   * @return translated "Your Lock on this record has expired; Please abort and try again"
   */
  @DefaultMessage("Your Lock on this record has expired; Please abort and try again")
  @Key("gen.expiredLockException")
  String gen_expiredLockException();

  /**
   * Translated "Error: Could not retrieve the record".
   * 
   * @return translated "Error: Could not retrieve the record"
   */
  @DefaultMessage("Error: Could not retrieve the record")
  @Key("gen.fetchFailed")
  String gen_fetchFailed();

  /**
   * Translated "Fetching ...".
   * 
   * @return translated "Fetching ..."
   */
  @DefaultMessage("Fetching ...")
  @Key("gen.fetching")
  String gen_fetching();

  /**
   * Translated "Field is required".
   * 
   * @return translated "Field is required"
   */
  @DefaultMessage("Field is required")
  @Key("gen.fieldRequiredException")
  String gen_fieldRequiredException();

  /**
   * Translated "A record with this value already exists. Please enter a unique value for this field".
   * 
   * @return translated "A record with this value already exists. Please enter a unique value for this field"
   */
  @DefaultMessage("A record with this value already exists. Please enter a unique value for this field")
  @Key("gen.fieldUniqueException")
  String gen_fieldUniqueException();

  /**
   * Translated "Please enter a unique value for this field".
   * 
   * @return translated "Please enter a unique value for this field"
   */
  @DefaultMessage("Please enter a unique value for this field")
  @Key("gen.fieldUniqueOnlyException")
  String gen_fieldUniqueOnlyException();

  /**
   * Translated "Id:".
   * 
   * @return translated "Id:"
   */
  @DefaultMessage("Id:")
  @Key("gen.id")
  String gen_id();

  /**
   * Translated "Locking record for Update...".
   * 
   * @return translated "Locking record for Update..."
   */
  @DefaultMessage("Locking record for Update...")
  @Key("gen.lockForUpdate")
  String gen_lockForUpdate();

  /**
   * Translated "Login Name".
   * 
   * @return translated "Login Name"
   */
  @DefaultMessage("Login Name")
  @Key("gen.loginName")
  String gen_loginName();

  /**
   * Translated "You do not have {0} permission in {1,number}".
   * 
   * @return translated "You do not have {0} permission in {1,number}"
   */
  @DefaultMessage("You do not have {0} permission in {1,number}")
  @Key("gen.modulePermException")
  String gen_modulePermException(String arg0,  Integer arg1);

  /**
   * Translated "Modules".
   * 
   * @return translated "Modules"
   */
  @DefaultMessage("Modules")
  @Key("gen.modules")
  String gen_modules();

  /**
   * Translated "You must Commit or Abort changes first".
   * 
   * @return translated "You must Commit or Abort changes first"
   */
  @DefaultMessage("You must Commit or Abort changes first")
  @Key("gen.mustCommitOrAbort")
  String gen_mustCommitOrAbort();

  /**
   * Translated "Name".
   * 
   * @return translated "Name"
   */
  @DefaultMessage("Name")
  @Key("gen.name")
  String gen_name();

  /**
   * Translated "No more records in this direction".
   * 
   * @return translated "No more records in this direction"
   */
  @DefaultMessage("No more records in this direction")
  @Key("gen.noMoreRecordInDir")
  String gen_noMoreRecordInDir();

  /**
   * Translated "No records found".
   * 
   * @return translated "No records found"
   */
  @DefaultMessage("No records found")
  @Key("gen.noRecordsFound")
  String gen_noRecordsFound();

  /**
   * Translated "Password".
   * 
   * @return translated "Password"
   */
  @DefaultMessage("Password")
  @Key("gen.password")
  String gen_password();

  /**
   * Translated "Query aborted".
   * 
   * @return translated "Query aborted"
   */
  @DefaultMessage("Query aborted")
  @Key("gen.queryAborted")
  String gen_queryAborted();

  /**
   * Translated "Querying....".
   * 
   * @return translated "Querying...."
   */
  @DefaultMessage("Querying....")
  @Key("gen.querying")
  String gen_querying();

  /**
   * Translated "Querying...Complete".
   * 
   * @return translated "Querying...Complete"
   */
  @DefaultMessage("Querying...Complete")
  @Key("gen.queryingComplete")
  String gen_queryingComplete();

  /**
   * Translated "Rls".
   * 
   * @return translated "Rls"
   */
  @DefaultMessage("Rls")
  @Key("gen.releasePerm")
  String gen_releasePerm();

  /**
   * Translated "Remove Row".
   * 
   * @return translated "Remove Row"
   */
  @DefaultMessage("Remove Row")
  @Key("gen.removeRow")
  String gen_removeRow();

  /**
   * Translated "Section".
   * 
   * @return translated "Section"
   */
  @DefaultMessage("Section")
  @Key("gen.section")
  String gen_section();

  /**
   * Translated "You do not have permission to {0} for section {1}".
   * 
   * @return translated "You do not have permission to {0} for section {1}"
   */
  @DefaultMessage("You do not have permission to {0} for section {1}")
  @Key("gen.sectionPermException")
  String gen_sectionPermException(String arg0,  String arg1);

  /**
   * Translated "Sections".
   * 
   * @return translated "Sections"
   */
  @DefaultMessage("Sections")
  @Key("gen.sections")
  String gen_sections();

  /**
   * Translated "Select Application".
   * 
   * @return translated "Select Application"
   */
  @DefaultMessage("Select Application")
  @Key("gen.selectApplication")
  String gen_selectApplication();

  /**
   * Translated "Sel".
   * 
   * @return translated "Sel"
   */
  @DefaultMessage("Sel")
  @Key("gen.selectPerm")
  String gen_selectPerm();

  /**
   * Translated "\"Select\" permission must not be unchecked for a module".
   * 
   * @return translated "\"Select\" permission must not be unchecked for a module"
   */
  @DefaultMessage("\"Select\" permission must not be unchecked for a module")
  @Key("gen.selectPermRequiredException")
  String gen_selectPermRequiredException();

  /**
   * Translated "Show Clause".
   * 
   * @return translated "Show Clause"
   */
  @DefaultMessage("Show Clause")
  @Key("gen.showClause")
  String gen_showClause();

  /**
   * Translated "Sign In".
   * 
   * @return translated "Sign In"
   */
  @DefaultMessage("Sign In")
  @Key("gen.signin")
  String gen_signin();

  /**
   * Translated "System User".
   * 
   * @return translated "System User"
   */
  @DefaultMessage("System User")
  @Key("gen.systemUser")
  String gen_systemUser();

  /**
   * Translated "Template".
   * 
   * @return translated "Template"
   */
  @DefaultMessage("Template")
  @Key("gen.template")
  String gen_template();

  /**
   * Translated "Templates".
   * 
   * @return translated "Templates"
   */
  @DefaultMessage("Templates")
  @Key("gen.templates")
  String gen_templates();

  /**
   * Translated "Extend Time".
   * 
   * @return translated "Extend Time"
   */
  @DefaultMessage("Extend Time")
  @Key("gen.timeoutExtendTime")
  String gen_timeoutExtendTime();

  /**
   * Translated "Timeout Warning".
   * 
   * @return translated "Timeout Warning"
   */
  @DefaultMessage("Timeout Warning")
  @Key("gen.timeoutHeader")
  String gen_timeoutHeader();

  /**
   * Translated "Logout".
   * 
   * @return translated "Logout"
   */
  @DefaultMessage("Logout")
  @Key("gen.timeoutLogout")
  String gen_timeoutLogout();

  /**
   * Translated "Your session is about to expire, do you want to\nlogout or extend your session".
   * 
   * @return translated "Your session is about to expire, do you want to\nlogout or extend your session"
   */
  @DefaultMessage("Your session is about to expire, do you want to\nlogout or extend your session")
  @Key("gen.timeoutWarning")
  String gen_timeoutWarning();

  /**
   * Translated "Update aborted".
   * 
   * @return translated "Update aborted"
   */
  @DefaultMessage("Update aborted")
  @Key("gen.updateAborted")
  String gen_updateAborted();

  /**
   * Translated "Upd".
   * 
   * @return translated "Upd"
   */
  @DefaultMessage("Upd")
  @Key("gen.updatePerm")
  String gen_updatePerm();

  /**
   * Translated "Updating...".
   * 
   * @return translated "Updating..."
   */
  @DefaultMessage("Updating...")
  @Key("gen.updating")
  String gen_updating();

  /**
   * Translated "Updating...Complete".
   * 
   * @return translated "Updating...Complete"
   */
  @DefaultMessage("Updating...Complete")
  @Key("gen.updatingComplete")
  String gen_updatingComplete();

  /**
   * Translated "Username".
   * 
   * @return translated "Username"
   */
  @DefaultMessage("Username")
  @Key("gen.username")
  String gen_username();

  /**
   * Translated "Users".
   * 
   * @return translated "Users"
   */
  @DefaultMessage("Users")
  @Key("gen.users")
  String gen_users();

  /**
   * Translated "View".
   * 
   * @return translated "View"
   */
  @DefaultMessage("View")
  @Key("gen.viewPerm")
  String gen_viewPerm();

  /**
   * Translated "\"View\" permission must not be unchecked for a section".
   * 
   * @return translated "\"View\" permission must not be unchecked for a section"
   */
  @DefaultMessage("\"View\" permission must not be unchecked for a section")
  @Key("gen.viewPermRequiredException")
  String gen_viewPermRequiredException();

  /**
   * Translated "Logout".
   * 
   * @return translated "Logout"
   */
  @DefaultMessage("Logout")
  @Key("menu.logout")
  String menu_logout();

  /**
   * Translated "Exit the application.".
   * 
   * @return translated "Exit the application."
   */
  @DefaultMessage("Exit the application.")
  @Key("menu.logoutDescription")
  String menu_logoutDescription();

  /**
   * Translated "Permissions".
   * 
   * @return translated "Permissions"
   */
  @DefaultMessage("Permissions")
  @Key("menu.permissions")
  String menu_permissions();

  /**
   * Translated "System".
   * 
   * @return translated "System"
   */
  @DefaultMessage("System")
  @Key("menu.system")
  String menu_system();

  /**
   * Translated "Utilities".
   * 
   * @return translated "Utilities"
   */
  @DefaultMessage("Utilities")
  @Key("menu.utilities")
  String menu_utilities();

  /**
   * Translated "Active".
   * 
   * @return translated "Active"
   */
  @DefaultMessage("Active")
  @Key("systemUser.active")
  String systemUser_active();

  /**
   * Translated "This action will remove all the permissions for this user and make it inactive. The user itself will not be removed.".
   * 
   * @return translated "This action will remove all the permissions for this user and make it inactive. The user itself will not be removed."
   */
  @DefaultMessage("This action will remove all the permissions for this user and make it inactive. The user itself will not be removed.")
  @Key("systemUser.deleteUserMessage")
  String systemUser_deleteUserMessage();

  /**
   * Translated "Employee".
   * 
   * @return translated "Employee"
   */
  @DefaultMessage("Employee")
  @Key("systemUser.employee")
  String systemUser_employee();

  /**
   * Translated "External ID".
   * 
   * @return translated "External ID"
   */
  @DefaultMessage("External ID")
  @Key("systemUser.externalId")
  String systemUser_externalId();

  /**
   * Translated "First Name".
   * 
   * @return translated "First Name"
   */
  @DefaultMessage("First Name")
  @Key("systemUser.firstName")
  String systemUser_firstName();

  /**
   * Translated "Initials".
   * 
   * @return translated "Initials"
   */
  @DefaultMessage("Initials")
  @Key("systemUser.initials")
  String systemUser_initials();

  /**
   * Translated "Last Name".
   * 
   * @return translated "Last Name"
   */
  @DefaultMessage("Last Name")
  @Key("systemUser.lastName")
  String systemUser_lastName();

  /**
   * Translated "System Module already added to the user".
   * 
   * @return translated "System Module already added to the user"
   */
  @DefaultMessage("System Module already added to the user")
  @Key("systemUser.moduleAddedToUser")
  String systemUser_moduleAddedToUser();

  /**
   * Translated "Section already added to the user".
   * 
   * @return translated "Section already added to the user"
   */
  @DefaultMessage("Section already added to the user")
  @Key("systemUser.sectionAddedToUser")
  String systemUser_sectionAddedToUser();

  /**
   * Translated "Since this user has no permissions and is inactive, deletion is invalid".
   * 
   * @return translated "Since this user has no permissions and is inactive, deletion is invalid"
   */
  @DefaultMessage("Since this user has no permissions and is inactive, deletion is invalid")
  @Key("systemUser.userDeleteInvalid")
  String systemUser_userDeleteInvalid();

  /**
   * Translated "Since this user has no permissions, duplication is invalid".
   * 
   * @return translated "Since this user has no permissions, duplication is invalid"
   */
  @DefaultMessage("Since this user has no permissions, duplication is invalid")
  @Key("systemUser.userDuplicateInvalid")
  String systemUser_userDuplicateInvalid();

  /**
   * Translated "System Module already added to the template".
   * 
   * @return translated "System Module already added to the template"
   */
  @DefaultMessage("System Module already added to the template")
  @Key("template.moduleAddedToTemplate")
  String template_moduleAddedToTemplate();

  /**
   * Translated "Template Name".
   * 
   * @return translated "Template Name"
   */
  @DefaultMessage("Template Name")
  @Key("template.name")
  String template_name();

  /**
   * Translated "Section already added to the template".
   * 
   * @return translated "Section already added to the template"
   */
  @DefaultMessage("Section already added to the template")
  @Key("template.sectionAddedToTemplate")
  String template_sectionAddedToTemplate();
}
