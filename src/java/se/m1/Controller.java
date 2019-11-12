/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.m1;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static se.m1.Constants.*;

/**
 *
 * @author JAA
 */
public class Controller extends HttpServlet {

    DBActions dba;
    InputStream input;
    String dbUrl = "";
    String dbUser = "";
    String dbPwd = "";
    //So that we can access the user type in both welcome and details
    String userLogin = ""; 
    String userPwd = "";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param session
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.sql.SQLException
     */
    protected void processRequest(HttpServletRequest session, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        Properties prop = new Properties();
        input = getServletContext().getResourceAsStream(PROP_FILE_PATH);
        prop.load(input);
        
        dbUrl = prop.getProperty("dbUrl");
        dbUser = prop.getProperty("dbUser");
        dbPwd = prop.getProperty("dbPwd");
       

        if (session.getParameter("action") == null) {
            session.getRequestDispatcher(JSP_HOME_PAGE).forward(session, response);
        } else if ("Log In".equals(session.getParameter("action"))) {
            if("".equals(session.getParameter(FRM_LOGIN_FIELD)) || "".equals(session.getParameter(FRM_PWD_FIELD))) {
                session.setAttribute("errKey", ERR_MISSING_FIELD);
                session.getRequestDispatcher(JSP_HOME_PAGE).forward(session, response);
            }
            dba = new DBActions(dbUrl, dbUser, dbPwd);

            ServletContext ctx = this.getServletContext();
            ServletConfig conf = this.getServletConfig();
            String[] loginCtx = ctx.getInitParameter("login").split(","); //in the xml file, values are separated by a comma
            String[] pwdCtx = conf.getInitParameter("password").split(",");

            boolean ok = false;
            for(int i = 0; i<loginCtx.length; i++){
                if (loginCtx[i].equals(session.getParameter(FRM_LOGIN_FIELD)) && 
                    pwdCtx[i].equals(session.getParameter(FRM_PWD_FIELD))) {
                    ok = true;
                    userLogin = loginCtx[i];
                    userPwd = pwdCtx[i];
                    //In the welcome page, the user will be identified by its login and pwd, to display or no the modify and add button
                    session.setAttribute("user", dba.getUser(userLogin, userPwd));
                    //To display the list of employees
                    session.setAttribute("empList", dba.getEmployees());
                    session.getRequestDispatcher(JSP_WELCOME_PAGE).forward(session, response);
                }
            }
            
            if(ok == false) {
                session.setAttribute("errKey", ERR_CONNECTION_FAILED);
                session.getRequestDispatcher(JSP_HOME_PAGE).forward(session, response);
            }
        } else if ("Details".equals(session.getParameter("action"))) {
            dba = new DBActions(dbUrl, dbUser, dbPwd);
            session.setAttribute("user", dba.getUser(userLogin, userPwd));
            session.setAttribute("employee", dba.getEmployeeById((session.getParameter("employeeID"))));
            session.getRequestDispatcher(JSP_DETAILS_PAGE).forward(session, response);
        } else if ("Cancel".equals(session.getParameter("action"))) {
            session.setAttribute("user", dba.getUser(userLogin, userPwd));
            //To display the list of employees
            session.setAttribute("empList", dba.getEmployees());
            session.getRequestDispatcher(JSP_WELCOME_PAGE).forward(session, response);
        } else if ("Save".equals(session.getParameter("action"))) {
            dba = new DBActions(dbUrl, dbUser, dbPwd);
            if(null != session.getParameter("id")){
                dba.updateEmployee(session.getParameter("id"), 
                    session.getParameter("lastname"), 
                    session.getParameter("firstname"),
                    session.getParameter("telHome"),
                    session.getParameter("telMob"),
                    session.getParameter("telPro"),
                    session.getParameter("address"),
                    session.getParameter("postalcode"),
                    session.getParameter("city"),
                    session.getParameter("email"));
            } else {
                dba.addEmployee(session.getParameter("lastname"), 
                    session.getParameter("firstname"),
                    session.getParameter("telHome"),
                    session.getParameter("telMob"),
                    session.getParameter("telPro"),
                    session.getParameter("address"),
                    session.getParameter("postalcode"),
                    session.getParameter("city"),
                    session.getParameter("email"));
            }

            session.setAttribute("user", dba.getUser(userLogin, userPwd));
            //To display the list of employees
            session.setAttribute("empList", dba.getEmployees());
            session.getRequestDispatcher(JSP_WELCOME_PAGE).forward(session, response);
        } else if ("Add".equals(session.getParameter("action"))) {
            dba = new DBActions(dbUrl, dbUser, dbPwd);
            session.setAttribute("user", dba.getUser(userLogin, userPwd));
            session.getRequestDispatcher(JSP_ADD_PAGE).forward(session, response);
        }  else if ("Delete".equals(session.getParameter("action"))) {
            dba = new DBActions(dbUrl, dbUser, dbPwd);
            dba.deleteEmployee(session.getParameter("employeeID"));
            session.setAttribute("user", dba.getUser(userLogin, userPwd));
            //To display the list of employees
            session.setAttribute("empList", dba.getEmployees());
            session.getRequestDispatcher(JSP_WELCOME_PAGE).forward(session, response);
        } else if ("Logout".equals(session.getParameter("action"))) {
            session.logout();
            session.getRequestDispatcher(JSP_GOODBYE_PAGE).forward(session, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
