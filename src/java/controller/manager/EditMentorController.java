/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.manager;

import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import model.User;

/**
 *
 * @author kttan
 */
@WebServlet(name = "EditMentorController", urlPatterns = {"/manager/EditMentor"})
public class EditMentorController extends HttpServlet {

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
        String idParam = request.getParameter("id");
        if (idParam != null && !idParam.isEmpty()) {
            try {
                int mentorId = Integer.parseInt(idParam);
                UserDAO dao = new UserDAO();
                User mentor = dao.getUserById(mentorId);
                
                if (mentor != null && mentor.getRoleID() == 3) { // Check if user is a mentor
                    request.setAttribute("mentor", mentor);
                    request.getRequestDispatcher("/manager/EditMentor.jsp").forward(request, response);
                    return;
                }
            } catch (NumberFormatException e) {
                // Invalid ID format
                response.sendRedirect("manageMentor");
                return;
            }
        }
        // No ID provided or mentor not found
        response.sendRedirect("manageMentor");
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
        String idParam = request.getParameter("id");
        
        if (idParam != null && !idParam.isEmpty()) {
            try {
                int mentorId = Integer.parseInt(idParam);
                UserDAO dao = new UserDAO();
                User mentor = dao.getUserById(mentorId);
                
                if (mentor != null && mentor.getRoleID() == 3) { // Check if user is a mentor
                    // Get form data
                    String fullname = request.getParameter("fullname");
                    String email = request.getParameter("email");
                    String phoneNumber = request.getParameter("phoneNumber");
                    String gender = request.getParameter("gender");
                    String dobString = request.getParameter("dob");
                    String address = request.getParameter("address");
                    
                    // Validate data
                    if (fullname == null || fullname.trim().isEmpty() ||
                        email == null || email.trim().isEmpty() ||
                        phoneNumber == null || phoneNumber.trim().isEmpty() ||
                        gender == null || gender.trim().isEmpty() ||
                        dobString == null || dobString.trim().isEmpty() ||
                        address == null || address.trim().isEmpty()) {
                        
                        request.setAttribute("errorMessage", "Please fill in all required fields");
                        request.setAttribute("mentor", mentor);
                        request.getRequestDispatcher("/manager/EditMentor.jsp").forward(request, response);
                        return;
                    }
                    
                    // Parse and validate date
                    Date dob;
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        sdf.setLenient(false);
                        dob = sdf.parse(dobString);
                    } catch (ParseException e) {
                        request.setAttribute("errorMessage", "Invalid date format");
                        request.setAttribute("mentor", mentor);
                        request.getRequestDispatcher("/manager/EditMentor.jsp").forward(request, response);
                        return;
                    }
                    
                    // Update mentor object
                    mentor.setFullname(fullname);
                    mentor.setEmail(email);
                    mentor.setPhoneNumber(phoneNumber);
                    mentor.setSex(gender.equals("male"));
                    mentor.setDob(dob);
                    mentor.setAddress(address);
                    
                    // Update in database
 try {
                        dao.updateUser(mentor);
                        response.sendRedirect("MentorDetail?id=" + mentorId);
                        return;
                    } catch (Exception e) {
                        request.setAttribute("errorMessage", "Failed to update mentor information");
                        request.setAttribute("mentor", mentor);
                        request.getRequestDispatcher("/manager/EditMentor.jsp").forward(request, response);
                        return;
                    }
                }
            } catch (NumberFormatException e) {
                response.sendRedirect("manageMentor");
                return;
            }
        }
        response.sendRedirect("manageMentor");
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Handles mentor profile editing";
    }
}