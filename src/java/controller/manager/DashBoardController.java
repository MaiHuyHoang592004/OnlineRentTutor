/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.manager;

import dal.RequestDAO;
import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

/**
 *
 * @author huyho
 */
@WebServlet("/manager/Dashboard")
public class DashBoardController extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        // Check if user is logged in
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("u");
        
        if (user == null) {
            // User not logged in, redirect to login page
            response.sendRedirect(request.getContextPath() + "/Login.jsp");
            return;
        }
        
        // Check if user has role ID 4 (mentee) or 2 (manager)
        if (user.getRoleID() != 4 && user.getRoleID() != 2) {
            // User doesn't have appropriate role, redirect to home page
            response.sendRedirect(request.getContextPath() + "/Home.jsp");
            return;
        }
        
        try {
            // Get dashboard data
            UserDAO userDao = new UserDAO();
            RequestDAO requestDao = new RequestDAO();
            
            // Count total mentees (role ID 4)
            int totalMentees = userDao.countUsersByRole(4);
            request.setAttribute("totalMentees", totalMentees);
            
            // Count active mentors (role ID 3)
            int activeMentors = userDao.countUsersByRole(3);
            request.setAttribute("activeMentors", activeMentors);
            
            // Count pending requests
            int pendingRequests = requestDao.countPendingRequests();
            request.setAttribute("pendingRequests", pendingRequests);
            
            // Forward to dashboard page
            request.getRequestDispatcher("/manager/Dashboard.jsp").forward(request, response);
        } catch (Exception e) {
            // Log the error
            e.printStackTrace();
            
            // Forward to error page or show error message
            request.setAttribute("errorMessage", "An error occurred while loading dashboard data.");
            request.getRequestDispatcher("/manager/Dashboard.jsp").forward(request, response);
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
