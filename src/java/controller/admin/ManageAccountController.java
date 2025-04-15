/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.util.List;
import model.User;
import org.json.JSONObject;

/**
 *
 * @author dungnv
 */
@WebServlet(name = "ManageAccountController", urlPatterns = {"/admin/manageAccount"})
public class ManageAccountController extends HttpServlet {

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
            out.println("<title>Servlet ManageAccountController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ManageAccountController at " + request.getContextPath() + "</h1>");
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

        String page_raw = request.getParameter("page");
        int itemsPerPage = 3;
        int page = 1;
        if (page_raw != null) {
            if (!page_raw.isEmpty()) {
                page = Integer.parseInt(page_raw);
            }
        }

        UserDAO dao = new UserDAO();
        List<User> users = dao.getAllUserByPage(page, itemsPerPage);

        int total = dao.getTotalAccount();
        int end_page = total / itemsPerPage;
        if (total % itemsPerPage != 0) {
            end_page++;
        }

        request.setAttribute("accounts", users);
        request.setAttribute("current_page", page);
        request.setAttribute("end_page", end_page);
        request.getRequestDispatcher("/admin/ManageAccount.jsp").forward(request, response);
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
        StringBuilder jsonBody = new StringBuilder();
        String line;
        BufferedReader reader = request.getReader();
        while ((line = reader.readLine()) != null) {
            jsonBody.append(line);
        }

        try {
            // Parse JSON
            JSONObject json = new JSONObject(jsonBody.toString());

            int accountId = json.optInt("id", -1);
            boolean enable = json.optBoolean("enable", false);

            UserDAO dao = new UserDAO();
            dao.updateStatusUserById(accountId, enable);

        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JSON format");
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
