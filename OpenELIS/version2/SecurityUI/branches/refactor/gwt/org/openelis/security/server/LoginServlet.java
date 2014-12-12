/**
 * Exhibit A - UIRF Open-source Based Public Software License.
 * 
 * The contents of this file are subject to the UIRF Open-source Based Public
 * Software License(the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * openelis.uhl.uiowa.edu
 * 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License for
 * the specific language governing rights and limitations under the License.
 * 
 * The Original Code is OpenELIS code.
 * 
 * The Initial Developer of the Original Code is The University of Iowa.
 * Portions created by The University of Iowa are Copyright 2006-2008. All
 * Rights Reserved.
 * 
 * Contributor(s): ______________________________________.
 * 
 * Alternatively, the contents of this file marked "Separately-Licensed" may be
 * used under the terms of a UIRF Software license ("UIRF Software License"), in
 * which case the provisions of a UIRF Software License are applicable instead
 * of those above.
 */
package org.openelis.security.server;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openelis.ui.server.ServiceUtils;
import org.openelis.ui.util.XMLUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Elis controller is the entry point for Elis Web Application. This object
 * controls the workflow for processing request and response.
 * 
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static Logger     log          = Logger.getLogger("security");


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
                    IOException {
        doGet(req,resp);
    }
    
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException,IOException {
        String error = null,locale = "en";
        HttpServletRequest hreq = (HttpServletRequest)req;

        //
        // used for language binding
        //
        if (hreq.getParameter("locale") != null) {
            locale = req.getParameter("locale");
            hreq.getSession().setAttribute("locale", locale);
        }

        //
        // ask them to authenticate
        //
        try {
            Document doc;
            Element session,localeEl,ip;

            doc = XMLUtil.createNew("login");
            
            session = doc.createElement("session");
            session.appendChild(doc.createTextNode(String.valueOf(req.getSession().getId())));
            doc.getDocumentElement().appendChild(session);
            
            localeEl = doc.createElement("locale");
            localeEl.appendChild(doc.createTextNode(locale));
            doc.getDocumentElement().appendChild(localeEl);
            
            ip = doc.createElement("ip");
            ip.appendChild(doc.createTextNode(req.getRemoteAddr()));
            doc.getDocumentElement().appendChild(ip);
            
            if (req.getParameter("error") != null) {
                Element errorEL = doc.createElement("error");
                errorEL.appendChild(doc.createTextNode("Failed to login"));
                doc.getDocumentElement().appendChild(errorEL);
            }
            
            ((HttpServletResponse)response).setContentType("text/html");
            ((HttpServletResponse)response).setCharacterEncoding("UTF-8");
            
            response.getWriter().write(ServiceUtils.getXML(getServletContext().getRealPath("") +
                                                       "/jbosslogin.xsl", doc,(String)hreq.getSession().getAttribute("locale")));
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage(), e);
            e.printStackTrace();
        }
    }
}