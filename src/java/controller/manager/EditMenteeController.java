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
@WebServlet(name = "EditMenteeController", urlPatterns = {"/manager/EditMentee"})
public class EditMenteeController extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet EditMenteeController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EditMenteeController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        UserDAO dao = new UserDAO();
        
        // Get mentee detail by ID
        String idParam = request.getParameter("id");
        if (idParam != null && !idParam.isEmpty()) {
            try {
                int menteeId = Integer.parseInt(idParam);
                User mentee = dao.getUserById(menteeId);
                
                if (mentee != null) {
                    request.setAttribute("mentee", mentee);
                    request.getRequestDispatcher("/manager/EditMentee.jsp").forward(request, response);
                    return;
                } else {
                    // Mentee not found
                    request.setAttribute("errorMessage", "Mentee with ID " + menteeId + " not found.");
                    response.sendRedirect("MenteeManagement");
                    return;
                }
            } catch (NumberFormatException e) {
                // Invalid ID format, redirect to mentee list
                response.sendRedirect("MenteeManagement");
                return;
            }
        } else {
            // No ID provided, redirect to mentee list
            response.sendRedirect("MenteeManagement");
            return;
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
        UserDAO dao = new UserDAO();
        
        // Get form parameters
        String idParam = request.getParameter("id");
        String fullname = request.getParameter("fullname");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        String address = request.getParameter("address");
        String genderParam = request.getParameter("gender");
        String dobParam = request.getParameter("dob");
        
        // Validate required fields
        if (idParam == null || fullname == null || email == null || phoneNumber == null || 
            address == null || genderParam == null || dobParam == null) {
            request.setAttribute("errorMessage", "All fields are required");
            doGet(request, response);
            return;
        }
        
        try {
            // Parse and validate ID
            int menteeId = Integer.parseInt(idParam);
            User existingMentee = dao.getUserById(menteeId);
            
            if (existingMentee == null) {
                request.setAttribute("errorMessage", "Mentee not found");
                response.sendRedirect("MenteeManagement");
                return;
            }
            
            // Parse gender
            boolean gender = "male".equalsIgnoreCase(genderParam);
            
            // Parse date of birth
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date dob;
            try {
                dob = dateFormat.parse(dobParam);
            } catch (ParseException e) {
                request.setAttribute("errorMessage", "Invalid date format");
                doGet(request, response);
                return;
            }
            
            // Create updated user object
            User updatedMentee = new User(
                menteeId,
                gender,
                dob,
                email,
                phoneNumber,
                address,
                fullname
            );
            
            // Update user in database
            dao.updateUser(updatedMentee);
            
            // Redirect to mentee detail page with success message
            request.setAttribute("successMessage", "Mentee information updated successfully");
            response.sendRedirect("MenteeDetail?id=" + menteeId);
            
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid ID format");
            doGet(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error updating mentee: " + e.getMessage());
            doGet(request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Handles mentee profile editing";
    }// </editor-fold>

}