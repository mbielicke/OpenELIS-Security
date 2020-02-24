package org.openelis.security.server;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;

import org.openelis.security.bean.UserCacheBean;
import org.openelis.security.modules.main.client.TimeoutServiceInt;
import org.openelis.ui.common.Datetime;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.gwt.user.server.rpc.XsrfProtectedServiceServlet;


@WebServlet("/security/timeout")
public class TimeoutServlet extends XsrfProtectedServiceServlet implements TimeoutServiceInt {

    private static final long serialVersionUID = 1L;

    @EJB
    UserCacheBean             userCache;

    public Datetime getLastAccess() {
        return (Datetime)getThreadLocalRequest().getSession().getAttribute("last_access");
    }

    public void keepAlive() {
        getThreadLocalRequest().getSession().setAttribute("last_access",
                                                          Datetime.getInstance(Datetime.YEAR,
                                                                               Datetime.MINUTE));
    }

    public void logout() {
        HttpSession session;

        try {
            userCache.logout();
            session = getThreadLocalRequest().getSession();
            if (session != null) {
                session.invalidate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
