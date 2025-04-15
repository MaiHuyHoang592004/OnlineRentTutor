/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.User;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author ACER
 */
public class DeleteAccountController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        User loggedInUser = (User) session.getAttribute("u"); // Lấy đối tượng User từ session
        if (loggedInUser == null) {
            // Nếu không tìm thấy thông tin người dùng trong session (người dùng chưa đăng nhập)
            request.setAttribute("mess", "Bạn phải đăng nhập");
            request.getRequestDispatcher("Login.jsp").forward(request, response);
            return;
        }
        int userID = loggedInUser.getUserID(); // Lấy UserID từ đối tượng User trong session
        String password1 = request.getParameter("password1");
        boolean isPasswordCorrect = BCrypt.checkpw(password1, loggedInUser.getPassword());
        if (!isPasswordCorrect) {
            request.setAttribute("mess", "Mật khẩu không đúng , xin nhập lại!");
            request.getRequestDispatcher("DeleteAccount.jsp").forward(request, response);
            return;
        }

        UserDAO ud = new UserDAO();
        ud.deleteUser(loggedInUser.getUserID());
        request.getRequestDispatcher("Home.jsp").forward(request, response);

    }

}
