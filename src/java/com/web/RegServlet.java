/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web;

import com.model.Address;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.model.JDBCWrapper;
import com.model.Member;
import com.model.User;
import com.model.XYZWebApplicationDB;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Fraser
 */
@WebServlet(name = "RegServlet", urlPatterns = {"/Registration"})
public class RegServlet extends HttpServlet {

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
        String button = request.getParameter("button");

        switch (button) {
            case "Register":
                // Get parameters
                String firstName = request.getParameter("firstName");
                String lastName = request.getParameter("lastName");
                String houseNumber = request.getParameter("houseNumber");
                houseNumber = houseNumber.trim();
                String streetName = request.getParameter("streetName");
                String city = request.getParameter("city");
                String county = request.getParameter("county");
                String postCode = request.getParameter("postCode");
                postCode = postCode.toUpperCase();
                String userID = makeUserID(firstName,lastName);

                try {
                    Date dob = makeDate(request.getParameter("DOB"));
                    // Error check
                    if (isEmpty(firstName, lastName, houseNumber, streetName, city, county, postCode)) {
                        request.setAttribute("errorMessage", "1 or more field has been left blank");
                        RequestDispatcher rd = request.getRequestDispatcher("registrationPage.jsp");
                        rd.forward(request, response);
                    } else if (!isValidPostcode(postCode)) {
                        request.setAttribute("errorMessage2", "Invalid PostCode");
                        RequestDispatcher rd = request.getRequestDispatcher("registrationPage.jsp");
                        rd.forward(request, response);
                    } else {
                        Date dor = new Date();

                        Member m = new Member(userID, firstName + " " + lastName, new Address(Integer.parseInt(houseNumber), streetName, city, county, postCode), dob, dor, "APPLIED", 0);

                        User u = new User(userID, User.createPassword(), "APPLIED");

                        //Inserting members with data provided above^^
                        JDBCWrapper wrapper = (JDBCWrapper) getServletContext().getAttribute("database");
                        new XYZWebApplicationDB(wrapper).insertMember(m);
                        new XYZWebApplicationDB(wrapper).insertUser(u);

                        request.setAttribute("username", u.getId());
                        request.setAttribute("password", u.getPassword());

                        RequestDispatcher view = request.getRequestDispatcher("RegistrationSuccess.jsp");
                        view.forward(request, response);
                    }
                } catch (NumberFormatException ex) {
                    // Catch if the house number is a String
                    request.setAttribute("errorMessage3", "House number must be a number");
                    RequestDispatcher rd = request.getRequestDispatcher("registrationPage.jsp");
                    rd.forward(request, response);
                } catch (ParseException ex) {
                    // Catch if invalid date
                    request.setAttribute("errorMessage4", "Invalid Date");
                    RequestDispatcher rd = request.getRequestDispatcher("registrationPage.jsp");
                    rd.forward(request, response);
                }
                break;

            case "login":
                RequestDispatcher view = request.getRequestDispatcher("login.jsp");
                view.forward(request, response);
                break;
            case "backPage":
                RequestDispatcher view2 = request.getRequestDispatcher("login.jsp");
                view2.forward(request, response);
                break;

            default:
                break;
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

    public String makeUserID(String firstName, String lastName) {
        char initial = firstName.toLowerCase().charAt(0);
        lastName = lastName.toLowerCase();
        JDBCWrapper wrapper = (JDBCWrapper) getServletContext().getAttribute("database");
        int count = 0;

        wrapper.createStatement();
        if (wrapper.findRecord("users", "id", initial + "-" + lastName)) {
            count++;
        }

        if (count == 0) {
            return initial + "-" + lastName;
        } else {
            return initial + "-" + lastName + count;
        }
    }

    public boolean isValidPostcode(String postcode) {
        String regex = "^[A-Z]{1,2}[0-9R][0-9A-Z]? [0-9][ABD-HJLNP-UW-Z]{2}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(postcode);

        return matcher.matches();
    }

    public Date makeDate(String dateParam) throws ParseException {
        Date dob = new Date();
        DateFormat df = new SimpleDateFormat("dd/MM/yy");
        dob = df.parse(dateParam);
        return dob;
    }

    public boolean isEmpty(String userID, String fullName, String houseNumber, String streetName, String city, String county, String postCode) {
        return userID.trim().isEmpty() || fullName.trim().isEmpty() || houseNumber.trim().isEmpty()
                || streetName.trim().isEmpty() || city.trim().isEmpty() || county.trim().isEmpty() || postCode.trim().isEmpty();
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
