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
public class ChangePasswordController extends HttpServlet {

@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // This method remains empty, no actions needed for GET request
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        User loggedInUser = (User) session.getAttribute("u"); // Get the logged-in user from the session

        if (loggedInUser == null) {
            // If user is not logged in, redirect to login page
            request.setAttribute("mess", "You must be logged in to edit your profile.");
            request.getRequestDispatcher("Login.jsp").forward(request, response);
            return;
        }

        int userID = loggedInUser.getUserID(); // Get the userID from the session
        String oldPassword = request.getParameter("oldpassword");
        String newPassword = request.getParameter("newpassword");
        String confirmPassword = request.getParameter("confirmnewpassword");

        // Compare old password with the stored hashed password using bcrypt
        if (!BCrypt.checkpw(oldPassword, loggedInUser.getPassword())) {
            // If the old password does not match the stored hash, show an error
            request.setAttribute("mess", "Mật khẩu cũ không đúng");
            request.getRequestDispatcher("ChangePassword.jsp").forward(request, response);
            return; // Stop further processing
        }

        // Check if the new password matches the confirmation password
        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("mess", "Mật khẩu không trùng khớp");
            request.getRequestDispatcher("ChangePassword.jsp").forward(request, response);
            return; // Stop further processing
        }

        // Hash the new password using bcrypt before storing it
        String hashedNewPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());

        // Update the password in the database
        UserDAO userDAO = new UserDAO();
        userDAO.updatePassword(loggedInUser.getEmail(), hashedNewPassword);

        // Notify the user that the password change was successful
        request.setAttribute("mess", "Thay đổi mật khẩu thành công.");
        request.getRequestDispatcher("ChangePassword.jsp").forward(request, response);
    }

}
