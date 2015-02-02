package org.openelis.security.modules.main.client;

import java.util.Date;

import org.openelis.security.messages.Messages;
import org.openelis.ui.common.Datetime;
import org.openelis.ui.widget.Confirm;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.ClosingEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class SessionTimer {

    private Confirm             timeoutPopup;
    private static Timer        timeoutTimer, forceTimer;
    private static int          SESSION_TIMEOUT = 1000 * 60 * 30, // Time to allow before asking to extend/logout
                                FORCE_TIMEOUT = 1000 * 60,        // Time allowed to answer extend/logout before forcing logout
                                CHECK_TIMEOUT = 1000 * 60 * 5;    // How often to poll the server for last access
    
    private HandlerRegistration closeHandler;

    public static void start() {
        new SessionTimer();
    }

    private SessionTimer() {
        /*
         * add session timeout dialog box and timers
         */
        timeoutPopup = new Confirm(Confirm.Type.WARN,
                                   Messages.get().gen_timeoutHeader(),
                                   Messages.get().gen_timeoutWarning(),
                                   Messages.get().gen_timeoutExtendTime(),
                                   Messages.get().gen_timeoutLogout());
        timeoutPopup.addSelectionHandler(new SelectionHandler<Integer>() {
            public void onSelection(SelectionEvent<Integer> event) {
                if (event.getSelectedItem() == 0) {
                    forceTimer.cancel();
                    resetServerTimeout();
                    resetTimeout();
                } else {
                    logout();
                }

            }
        });

        /*
         * if they don't answer the dialog box, we are going to log them out
         * automatically
         */
        forceTimer = new Timer() {
            public void run() {
                logout();
            }
        };

        /*
         * timer that goes off regularly to ask the server for the last access
         * time
         */
        timeoutTimer = new Timer() {
            public void run() {
                checkLastAccess();
            }
        };

        /*
         * Handler that catches the user closing the browser or navigating away
         * and logs them out.
         */
        closeHandler = Window.addWindowClosingHandler(new Window.ClosingHandler() {
            public void onWindowClosing(ClosingEvent event) {
                logout();
            }
        });
        
        TimeoutService.get().keepAlive(new AsyncCallback<Void>() {

            @Override
            public void onFailure(Throwable caught) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void onSuccess(Void result) {
                // TODO Auto-generated method stub
                
            }
            
        });

        resetTimeout();
    }

    /**
     * Check the last time the server was accessed
     */
    private void checkLastAccess() {
        TimeoutService.get().getLastAccess(new AsyncCallback<Datetime>() {
            public void onSuccess(Datetime result) {
                Datetime check;

                check = Datetime.getInstance(Datetime.YEAR,
                                             Datetime.MINUTE,
                                             new Date(new Date().getTime() - SESSION_TIMEOUT));

                if (result.before(check)) {
                    forceTimer.schedule(FORCE_TIMEOUT);
                    timeoutPopup.show();
                } else
                    resetTimeout();
            }

            public void onFailure(Throwable caught) {
                Window.alert(Messages.get().gen_couldNotCall());
                //Application.logger().log(Level.SEVERE, caught.getMessage(), caught);
            }
        });
    }

    /**
     * ping the server so the session does not expire
     */
    private void resetServerTimeout() {
        TimeoutService.get().keepAlive(new AsyncCallback<Void>() {
            public void onSuccess(Void result) {

            }

            public void onFailure(Throwable caught) {
                Window.alert(Messages.get().gen_couldNotCall());
                //Application.logger().log(Level.SEVERE, caught.getMessage(), caught);
            }
        });
    }

    /**
     * resets the timeout timer to check the server
     */
    public void resetTimeout() {
        timeoutTimer.schedule(CHECK_TIMEOUT);
    }

    /**
     * logout the user
     */
    private void logout() {
        //
        // close the handler so we don't get called again
        //
        if (closeHandler == null)
            closeHandler.removeHandler();

        try {
            TimeoutService.get().logout();
        } catch (Exception e) {
            Window.alert(e.getMessage());
            //Application.logger().log(Level.SEVERE, e.getMessage(), e);
        }

        Window.open("/security/Security.html", "_self", null);
    }

}
