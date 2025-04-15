/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.TransactionDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;

@WebServlet(name = "TransactionController", urlPatterns = {"/transaction"})
public class TransactionController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        User u = (User) request.getSession().getAttribute("u");

        // Lấy các tham số lọc từ request
        String type = request.getParameter("transactionType");  // Kiểu giao dịch
        String status = request.getParameter("statusFilter");   // Trạng thái
        String amountOrder = request.getParameter("amountFilter"); // Sắp xếp theo số tiền
        String time = request.getParameter("timeFilter");
        String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");

        try {
            // Gọi phương thức DAO để lọc các giao dịch theo các tiêu chí
            request.setAttribute("transactions", TransactionDAO.getFilteredTransactions(u.getUserID(), type, status, amountOrder, time, fromDate, toDate));
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher("transaction.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "TransactionController";
    }
}
