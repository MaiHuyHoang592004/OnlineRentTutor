package controller.manager;

import dal.TransactionDAO;
import dal.UserDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Transaction;
import model.TransactionWithUser;
import model.User;

/**
 * Controller for managing transactions in the admin panel
 */
public class ManagerTransaction extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     * Retrieves all transactions and forwards to the transaction management page
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
            // Get search parameters
            String search = request.getParameter("search");
            String status = request.getParameter("status");
            String sortBy = request.getParameter("sort");
            
            // Get pagination parameters
            int page = 1;
            int pageSize = 10; // Number of records per page
            
            try {
                String pageParam = request.getParameter("page");
                if (pageParam != null && !pageParam.isEmpty()) {
                    page = Integer.parseInt(pageParam);
                    if (page < 1) page = 1;
                }
            } catch (NumberFormatException e) {
                // If page parameter is invalid, default to page 1
                page = 1;
            }
            
            // Get total count for pagination
            int totalTransactions = TransactionDAO.countTransactions(search, status);
            int totalPages = (int) Math.ceil((double) totalTransactions / pageSize);
            
            // Ensure page is within valid range
            if (page > totalPages && totalPages > 0) {
                page = totalPages;
            }
            
            // Get transactions with pagination, search and sorting
            List<Transaction> transactions = TransactionDAO.getTransactionsWithPaging(
                    search, status, sortBy, page - 1, pageSize);
            
            // Create a list to hold transactions with user information
            List<TransactionWithUser> transactionsWithUser = new ArrayList<>();
            
            // Get user information for each transaction
            UserDAO userDAO = new UserDAO();
            for (Transaction transaction : transactions) {
                User user = userDAO.getUserById(transaction.getUid());
                TransactionWithUser transactionWithUser = new TransactionWithUser(
                        transaction.getId(),
                        transaction.getUid(),
                        user != null ? user.getFullname() : "Unknown User",
                        transaction.getBalance(),
                        transaction.getType(),
                        transaction.getFee(),
                        transaction.getContent(),
                        transaction.getTime(),
                        transaction.getStatus()
                );
                transactionsWithUser.add(transactionWithUser);
            }
            
            // Set attributes for the view
            request.setAttribute("transactions", transactionsWithUser);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("totalRecords", totalTransactions);
            request.setAttribute("search", search);
            request.setAttribute("status", status);
            request.setAttribute("sort", sortBy);
            
            // Forward to the transaction management page
            request.getRequestDispatcher("/manager/TransactionManagement.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("Dashboard.jsp");
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * Currently not implemented as the page is read-only
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Currently not implemented as the page is read-only
        doGet(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Transaction Management Controller";
    }
}