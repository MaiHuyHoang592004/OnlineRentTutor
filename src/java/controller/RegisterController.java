package controller;

import dal.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import model.User;
import org.mindrot.jbcrypt.BCrypt; // Thêm thư viện jBCrypt

public class RegisterController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String fullname = request.getParameter("fullname");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        String address = request.getParameter("address");
        String confirmPassword = request.getParameter("confirm-password");
        String sexParam = request.getParameter("sex"); 
        boolean sex = "male".equalsIgnoreCase(sexParam); 

        String dobParam = request.getParameter("dob"); 
        java.sql.Date dob = null;
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        String phoneRegex = "^(\\+\\d{1,3}[- ]?)?\\d{10}$";
        String passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$"; // Mật khẩu tối thiểu 8 ký tự và chứa ít nhất một chữ cái và một số

        // Kiểm tra định dạng ngày tháng
        if (dobParam != null && !dobParam.isEmpty()) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                sdf.setLenient(false);
                Date parsedDate = sdf.parse(dobParam);
                dob = new java.sql.Date(parsedDate.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
                request.setAttribute("dobError", "Sai định dạng ngày. Hãy dùng định dạng yyyy-MM-dd.");
                request.getRequestDispatcher("Register.jsp").forward(request, response);
                return;
            }
        }

        // Kiểm tra mật khẩu xác nhận
        if (!confirmPassword.equalsIgnoreCase(password)) {
            request.setAttribute("errorMessage", "Mật khẩu không trùng khớp");
            request.getRequestDispatcher("Register.jsp").forward(request, response);
            return;
        }

        // Kiểm tra email
        if (!email.matches(emailRegex)) {
            request.setAttribute("errorMessage", "Email sai định dạng");
            request.getRequestDispatcher("Register.jsp").forward(request, response);
            return;
        }

        // Kiểm tra số điện thoại
        if (!phoneNumber.matches(phoneRegex)) {
            request.setAttribute("errorMessage", "Sai định dạng Số điện thoại");
            request.getRequestDispatcher("Register.jsp").forward(request, response);
            return;
        }

        // Kiểm tra mật khẩu
        if (!password.matches(passwordRegex)) {
            request.setAttribute("errorMessage", "Mật khẩu phải có ít nhất 8 ký tự và chứa ít nhất một chữ cái và một số.");
            request.getRequestDispatcher("Register.jsp").forward(request, response);
            return;
        }

        // Kiểm tra trùng username
        List<User> users = (new UserDAO()).getAllUsers();
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                request.setAttribute("errorMessage", "Username đã tồn tại");
                request.getRequestDispatcher("Register.jsp").forward(request, response);
                return;
            }
        }

        // Mã hóa mật khẩu trước khi lưu vào cơ sở dữ liệu
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        // Thêm người dùng mới vào cơ sở dữ liệu
        UserDAO userDao = new UserDAO();
        User user = new User(1, sex, true, username, hashedPassword, dob, email, phoneNumber, 0, address, 4, true, " ", fullname);
        userDao.insertUser(user);

        // Hiển thị thông báo đăng ký thành công
        request.setAttribute("registerSuccess", "Đăng kí thành công!");
        request.getRequestDispatcher("Login.jsp").forward(request, response);
    }
}
