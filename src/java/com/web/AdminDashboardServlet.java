/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web;

import com.model.JDBCWrapper;
import com.model.User;
import com.model.XYZWebApplicationDB;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Harri Renney
 */
public class AdminDashboardServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String page = "/Theme.jsp";
        String button = request.getParameter("button");
        
        HttpSession session = request.getSession();
        session.setAttribute("currentpage", "Admin/adminPage.jsp");

        switch (button) {
            case "listAllMembers":
                session.setAttribute("currentpage", "Admin/adminListAllMembers.jsp");
                break;
            case "listAllOutstanding":
                session.setAttribute("currentpage", "Admin/adminListAllOutstanding.jsp");
                break;
            case "listAllClaims":
                session.setAttribute("currentpage", "Admin/adminListAllClaims.jsp");
                break;
            case "listApplications":
                session.setAttribute("currentpage", "Admin/adminListApplications.jsp");
                break;
            case "processClaim":
                session.setAttribute("currentpage", "Admin/adminProcessClaim.jsp");
                break;
            case "processApplication":
                session.setAttribute("currentpage", "Admin/adminProcessApplication.jsp");
                break;
            case "suspendResumeMember":
                session.setAttribute("currentpage", "Admin/adminSuspendResumeMember.jsp");
                break;
            case "annualReport":
                session.setAttribute("currentpage", "Admin/adminAnnualReport.jsp");
                break;
            case "backPage":
                session.setAttribute("currentpage", "Admin/adminPage.jsp");
                break;
            case "approve":
                User temp = new User(request.getParameter("memberSelected"), "", "");
                new XYZWebApplicationDB((JDBCWrapper) getServletContext().getAttribute("database")).approveMemberApplication(temp);
                break;
        }
        
        request.getRequestDispatcher(page).forward(request, response);
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
        processRequest(request, response);
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
        processRequest(request, response);
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
