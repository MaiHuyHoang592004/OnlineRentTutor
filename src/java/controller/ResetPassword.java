/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.TokenForgetDAO;
import dal.UserDAO;
import model.TokenForgetPassword;
import model.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;
import model.ResetService;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author ACER
 */
public class ResetPassword extends HttpServlet {

    UserDAO userDao = new UserDAO();
    TokenForgetDAO tokenDao = new TokenForgetDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String token = request.getParameter("token");
        HttpSession session = request.getSession();
        if (token != null) {
            TokenForgetPassword tokenForgetPassword = tokenDao.getTokenPassword(token);
            ResetService service = new ResetService();
            if (tokenForgetPassword == null) {
                request.setAttribute("mess", "token invalid");
                request.getRequestDispatcher("ForgotPassword.jsp").forward(request, response);
                return;
            }
            if (tokenForgetPassword.isIsUsed()) {
                request.setAttribute("mess", "token is used");
                request.getRequestDispatcher("ForgotPassword.jsp").forward(request, response);
                return;
            }
            if (service.isExpireTime(tokenForgetPassword.getExpiryTime())) {
                request.setAttribute("mess", "token is expiry time");
                request.getRequestDispatcher("ForgotPassword.jsp").forward(request, response);
                return;
            }
            User user = userDao.getUserById(tokenForgetPassword.getUserId());
            request.setAttribute("email", user.getEmail());
            session.setAttribute("token", tokenForgetPassword.getToken());
            request.getRequestDispatcher("ResetPassword.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("ForgotPassword.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirm_password");

        // Validate password...
        if (!password.equals(confirmPassword)) {
            request.setAttribute("mess", "Confirm password must be the same as the password");
            request.setAttribute("email", email);
            request.getRequestDispatcher("ResetPassword.jsp").forward(request, response);
            return;
        }

        HttpSession session = request.getSession();
        String tokenStr = (String) session.getAttribute("token");
        TokenForgetPassword tokenForgetPassword = tokenDao.getTokenPassword(tokenStr);

        // Check token is valid, of time, of use
        ResetService service = new ResetService();
        if (tokenForgetPassword == null) {
            request.setAttribute("mess", "Token invalid");
            request.getRequestDispatcher("ForgotPassword.jsp").forward(request, response);
            return;
        }
        if (tokenForgetPassword.isIsUsed()) {
            request.setAttribute("mess", "Token is used");
            request.getRequestDispatcher("ForgotPassword.jsp").forward(request, response);
            return;
        }
        if (service.isExpireTime(tokenForgetPassword.getExpiryTime())) {
            request.setAttribute("mess", "Token is expired");
            request.getRequestDispatcher("ForgotPassword.jsp").forward(request, response);
            return;
        }

        // Mã hóa mật khẩu bằng bcrypt
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());  // Mã hóa mật khẩu

        // Update status of token and update user password
        tokenForgetPassword.setToken(tokenStr);
        tokenForgetPassword.setIsUsed(true);

        // Cập nhật mật khẩu đã mã hóa vào cơ sở dữ liệu
        userDao.updatePassword(email, hashedPassword);
        tokenDao.updateStatus(tokenForgetPassword);

        // Lưu user vào session và chuyển hướng về trang chủ
        request.getRequestDispatcher("Login.jsp").forward(request, response);
    }

}
