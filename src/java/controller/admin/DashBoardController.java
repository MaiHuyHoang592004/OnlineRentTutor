/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

import dal.ReportDAO;
import dal.SkillDAO;
import dal.TransactionDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.Currency;
import java.util.Locale;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Report;
import model.Transaction;

/**
 *
 * @author dungnv
 */
@WebServlet(name = "AdminController", urlPatterns = {"/admin/dashboard"})
public class DashBoardController extends HttpServlet {

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
            out.println("<title>Servlet AdminController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AdminController at " + request.getContextPath() + "</h1>");
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
        TransactionDAO dao = new TransactionDAO();
        ReportDAO reportDao = new ReportDAO();
        SkillDAO skillDao = new SkillDAO();
        Map<String, Integer> skillCount =  skillDao.getSkillCounts();
        int earnAll = 0;
        try {
            earnAll = dao.getAllEarning();
        } catch (SQLException ex) {
            Logger.getLogger(DashBoardController.class.getName()).log(Level.SEVERE, null, ex);
        }
        int earnMonth = 0;
        try {
            earnMonth = dao.getEarningOfMonth();
        } catch (SQLException ex) {
            Logger.getLogger(DashBoardController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Currency vnd = Currency.getInstance("VND");
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat vndFormatter = NumberFormat.getCurrencyInstance(localeVN);
        vndFormatter.setCurrency(vnd);
        
        ArrayList<Transaction> transactions = null;
        try {
            transactions = dao.getAllTransaction();
        } catch (SQLException ex) {
            Logger.getLogger(DashBoardController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ArrayList<Report> reports = reportDao.getAllReport();
        
        request.setAttribute("skillCount", skillCount);
        request.setAttribute("transactions", transactions);
        request.setAttribute("reports", reports);
        request.setAttribute("earnAll", vndFormatter.format(earnAll));
        request.setAttribute("earnMonth", vndFormatter.format(earnMonth));
        request.getRequestDispatcher("/admin/DashBoard.jsp").forward(request, response);
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
        request.getRequestDispatcher("/admin/Admin.jsp").forward(request, response);
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
