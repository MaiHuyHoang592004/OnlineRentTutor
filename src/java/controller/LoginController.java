package controller;

import dal.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import model.User;
import org.mindrot.jbcrypt.BCrypt;

public class LoginController extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.sendRedirect("CustomerHome.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UserDAO userDao = new UserDAO();
        // Gọi phương thức getOne với username và mật khẩu
        User user = userDao.getOne(username, password); // Phương thức đã sửa ở trên

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("u", user);
            session.setAttribute("user", user);
            //role 4 : mentee
            if (user.getRoleID() == 4) {
                session.setAttribute("fullname", user.getFullname());
                session.setAttribute("mail", user.getEmail());
                session.setAttribute("phone", user.getPhoneNumber());
                session.setAttribute("address", user.getAddress());
                session.setAttribute("gender", user.getSex() ? "Female" : "Male");
                session.setAttribute("birthdate", user.getDob());
                session.setAttribute("wallet", user.getWallet());
                response.sendRedirect("CustomerHome.jsp");
            } else if (user.getRoleID() == 2) {
                //role 2 : manager
                session.setAttribute("fullname", user.getFullname());
                session.setAttribute("mail", user.getEmail());
                response.sendRedirect("manager/Dashboard");
            } else if (user.getRoleID() == 1) {
                // role 1 : admin
                session.setAttribute("fullname", user.getFullname());
                session.setAttribute("mail", user.getEmail());
                response.sendRedirect("admin/dashboard");
            } else if (user.getRoleID() == 3) {
                // role 3 : mentor
                session.setAttribute("fullname", user.getFullname());
                session.setAttribute("mail", user.getEmail());
                session.setAttribute("phone", user.getPhoneNumber());
                session.setAttribute("address", user.getAddress());
                session.setAttribute("gender", user.getSex() ? "Female" : "Male");
                session.setAttribute("birthdate", user.getDob());
                session.setAttribute("wallet", user.getWallet());                
                response.sendRedirect("CustomerHome.jsp");
            }
        } else {
            request.setAttribute("errorMessage", "Sai tài khoản hoặc mật khẩu!");
            request.getRequestDispatcher("Login.jsp").forward(request, response);
        }
    }
}
