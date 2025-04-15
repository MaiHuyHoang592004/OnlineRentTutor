package controller.manager;

import dal.BankDAO;
import dal.TransactionDAO;
import dal.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Bank;
import model.Transaction;
import model.TransactionWithUser;
import model.User;

/**
 * Controller for displaying transaction details
 */
@WebServlet(name = "TransactionDetailController", urlPatterns = {"/manager/TransactionDetail"})
public class TransactionDetailController extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     * Retrieves transaction details by ID and forwards to the detail page
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
            // Get transaction ID from request parameter
            String idParam = request.getParameter("id");
            
            if (idParam == null || idParam.isEmpty()) {
                // If no ID provided, redirect to transaction list
                response.sendRedirect("TransactionManagement");
                return;
            }
            
            int transactionId = Integer.parseInt(idParam);
            
            // Get all transactions to find the one with matching ID
            List<Transaction> transactions = TransactionDAO.getAllTransactions();
            Transaction transaction = null;
            
            // Find the transaction with the specified ID
            for (Transaction t : transactions) {
                if (t.getId() == transactionId) {
                    transaction = t;
                    break;
                }
            }
            
            if (transaction == null) {
                // If transaction not found, redirect to transaction list
                response.sendRedirect("TransactionManagement");
                return;
            }
            
            // Get user information for the transaction
            UserDAO userDAO = new UserDAO();
            User user = userDAO.getUserById(transaction.getUid());
            
            // Create TransactionWithUser object with combined information
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
            
            // Get bank information for the user
            try {
                Bank bank = BankDAO.getBank(transaction.getUid());
                if (bank != null) {
                    request.setAttribute("bank", bank);
                }
            } catch (Exception e) {
                // Log the error but continue processing
                e.printStackTrace();
            }
            
            // Set transaction as request attribute
            request.setAttribute("transaction", transactionWithUser);
            
            // Forward to transaction detail page
            request.getRequestDispatcher("/manager/TransactionDetail.jsp").forward(request, response);
            
        } catch (NumberFormatException e) {
            // If ID is not a valid number, redirect to transaction list
            response.sendRedirect("TransactionManagement");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("TransactionManagement");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    /**
 * Handles the HTTP <code>POST</code> method.
 * Processes withdrawal approval or rejection
 *
 * @param request servlet request
 * @param response servlet response
 * @throws ServletException if a servlet-specific error occurs
 * @throws IOException if an I/O error occurs
 */
@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    try {
        // Get transaction ID and action from request parameters
        String idParam = request.getParameter("id");
        String action = request.getParameter("action");
        
        if (idParam == null || idParam.isEmpty() || action == null || action.isEmpty()) {
            response.sendRedirect("TransactionManagement");
            return;
        }
        
        int transactionId = Integer.parseInt(idParam);
        
        // Get the transaction
        List<Transaction> transactions = TransactionDAO.getAllTransactions();
        Transaction transaction = null;
        
        // Find the transaction with the specified ID
        for (Transaction t : transactions) {
            if (t.getId() == transactionId) {
                transaction = t;
                break;
            }
        }
        
        if (transaction == null || !transaction.getContent().equals("Rút tiền") || !transaction.getStatus().equals("Pending")) {
            // If transaction not found or not a pending withdrawal, redirect to transaction list
            response.sendRedirect("TransactionManagement");
            return;
        }
        
        // Process the action
        String message = "";
        String messageType = "";
        
        if ("accept".equals(action)) {
            // Accept the withdrawal
            TransactionDAO.acceptWithdraw(transactionId);
            message = "Withdrawal has been accepted successfully.";
            messageType = "success";
        } else if ("reject".equals(action)) {
            // Reject the withdrawal and refund the money
            TransactionDAO.rejectWithdraw(transactionId);
            message = "Withdrawal has been rejected and the amount has been refunded to the user's wallet.";
            messageType = "success";
        } else {
            message = "Invalid action specified.";
            messageType = "danger";
        }
        
        // Set attributes for the view
        request.setAttribute("message", message);
        request.setAttribute("messageType", messageType);
        
        // Forward back to the same page to show the updated transaction
        doGet(request, response);
        
    } catch (NumberFormatException e) {
        // If ID is not a valid number, redirect to transaction list
        response.sendRedirect("TransactionManagement");
    } catch (Exception e) {
        e.printStackTrace();
        response.sendRedirect("TransactionManagement");
    }
}
    @Override
    public String getServletInfo() {
        return "Transaction Detail Controller";
    }
}