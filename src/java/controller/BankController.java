package controller;

import dal.BankDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Bank;
import model.User;

@WebServlet(name = "BankController", urlPatterns = {"/bank"})
public class BankController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

 
        User u = (User) request.getSession().getAttribute("u");

        if (request.getMethod().equalsIgnoreCase("post")) {
            String bankName = request.getParameter("bankAccountName");
            String bankNo = request.getParameter("bankAccountNumber");
            String bankType = request.getParameter("bankName");

            Bank bank = new Bank(u.getUserID(), bankName, bankNo, bankType);
            
            try {
                BankDAO.updateBank(bank);
                request.setAttribute("mess", "Set ngân hàng thành công!");
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("mess", "Có lỗi xảy ra, vui lòng thử lại sau.");
            }
        }

        try {
            Bank userBank = BankDAO.getBank(u.getUserID());
            request.setAttribute("Bank", userBank);
        } catch (Exception e) {
            e.printStackTrace();
            // Xử lý ngoại lệ khi lấy thông tin ngân hàng thất bại
        }

        request.getRequestDispatcher("bank.jsp").forward(request, response);
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
        return "Bank Controller for managing user bank details";
    }
}
