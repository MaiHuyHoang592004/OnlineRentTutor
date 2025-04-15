/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dal.TokenForgetDAO;
import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import model.ResetService;
import model.TokenForgetPassword;

/**
 *
 * @author ACER
 */
public class RequestPassword extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        request.getRequestDispatcher("ForgotPassword.jsp").forward(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String email = request.getParameter("email");
        UserDAO ud = new UserDAO();
        User user = ud.getUserByEmail(email);
        if (user == null) {
            request.setAttribute("mess", "Email không tồn tại");
            request.getRequestDispatcher("ForgotPassword.jsp").forward(request, response);
            return;
        }
        ResetService service = new ResetService();
        String token = service.generateToken();
        //send link to email
        String linkReset = "http://localhost:9999/OnlineRentTutor/resetPassword?token="+token;
        
        TokenForgetPassword newTokenForget = new TokenForgetPassword(user.getUserID(), false, token, service.expireDateTime());
        TokenForgetDAO tokenDao = new TokenForgetDAO();
        boolean isInsert = tokenDao.insertTokenForget(newTokenForget);
        if (!isInsert) {
            request.setAttribute("mess", "Server Error");
            request.getRequestDispatcher("ForgotPassword.jsp").forward(request, response);
            return;            
        }
        boolean isSend = service.sendEmail(email, linkReset, user.getUsername());
        if (!isSend) {
            request.setAttribute("mess", "Cannot send request");
            request.getRequestDispatcher("ForgotPassword.jsp").forward(request, response);
            return;
        }
        request.setAttribute("mess", "Send request successful");
        request.getRequestDispatcher("ForgotPassword.jsp").forward(request, response);
    }
}
