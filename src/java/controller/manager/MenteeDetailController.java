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
import model.User;

/**
 *
 * @author kttan
 */
@WebServlet(name = "MenteeDetailController", urlPatterns = {"/manager/MenteeDetail"})
public class MenteeDetailController extends HttpServlet {

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
            out.println("<title>Servlet MenteeDetailController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MenteeDetailController at " + request.getContextPath() + "</h1>");
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
                    // Check if this is a status toggle action
                    String action = request.getParameter("action");
                    if (action != null && action.equals("toggleStatus")) {
                        String statusParam = request.getParameter("status");
                        boolean newStatus = Boolean.parseBoolean(statusParam);
                        dao.updateStatusUserById(menteeId, newStatus);
                        
                        // Refresh mentee data after update
                        mentee = dao.getUserById(menteeId);
                    }
                    
                    request.setAttribute("mentee", mentee);
                    request.getRequestDispatcher("/manager/MenteeDetail.jsp").forward(request, response);
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
        // Currently not handling POST requests, but could be used for form submissions
        // like editing mentee details in the future
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Handles mentee detail view and status management";
    }// </editor-fold>

}