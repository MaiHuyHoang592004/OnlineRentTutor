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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import model.User;

/**
 *
 * @author ACER
 */
public class EditProfileController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.getRequestDispatcher("EditProfile.jsp").forward(request, response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
     HttpSession session = request.getSession();
        User loggedInUser = (User) session.getAttribute("u"); // Lấy đối tượng User từ session
    if (loggedInUser == null) {
        // Nếu không tìm thấy thông tin người dùng trong session (người dùng chưa đăng nhập)
        request.setAttribute("mess", "You must be logged in to edit your profile.");
        request.getRequestDispatcher("Login.jsp").forward(request, response);
        return;
    }
    int userID = loggedInUser.getUserID(); // Lấy UserID từ đối tượng User trong session
        
    String fullname1 = request.getParameter("fullname1");
    String email = request.getParameter("email");
    String phoneNumber = request.getParameter("phoneNumber");
    String address1 = request.getParameter("address1");
    String sexParam = request.getParameter("sex"); 
    boolean sex = "male".equalsIgnoreCase(sexParam); 
    String dobParam = request.getParameter("dob"); 
    java.sql.Date dob = null;
    String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    String phoneRegex = "^(\\+\\d{1,3}[- ]?)?\\d{10}$";
    

    if (dobParam != null && !dobParam.isEmpty()) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Thay đổi định dạng chuẩn yyyy-MM-dd
            sdf.setLenient(false); 
            Date parsedDate = sdf.parse(dobParam); 
            dob = new java.sql.Date(parsedDate.getTime()); 
        } catch (ParseException e) {
            e.printStackTrace(); 
            request.setAttribute("mess", "Invalid date format. Please use yyyy-MM-dd.");
            request.getRequestDispatcher("EditProfile.jsp").forward(request, response);
            return;
        }
    } 

    if(!email.matches(emailRegex)){
        request.setAttribute("mess", "Email không đúng định dạng");
        request.setAttribute("fullname1", fullname1); // Lưu lại fullname vào request
        request.setAttribute("phoneNumber", phoneNumber); // Lưu lại phone number vào request
        request.setAttribute("address1", address1); // Lưu lại address vào request
        request.setAttribute("sex", sex); // Lưu lại sex vào request
        request.setAttribute("dob", dob); // Lưu lại dob vào request
        request.getRequestDispatcher("EditProfile.jsp").forward(request, response);
        return;
    }
    if (!phoneNumber.matches(phoneRegex)) {
        request.setAttribute("mess", "Số điện thoại không đúng định dạng");
        request.setAttribute("fullname1", fullname1); // Lưu lại fullname vào request
        request.setAttribute("email", email); // Lưu lại email vào request
        request.setAttribute("address1", address1); // Lưu lại address vào request
        request.setAttribute("sex", sex); // Lưu lại sex vào request
        request.setAttribute("dob", dob); // Lưu lại dob vào request
        request.getRequestDispatcher("EditProfile.jsp").forward(request, response);
        return;
    }
    List<User> users = (new UserDAO()).getAllUsers();
    for (User user : users) {
        if (user.getEmail().equals(email)) {
            request.setAttribute("mess", "Email đã tồn tại");
            request.setAttribute("fullname1", fullname1); // Lưu lại fullname vào request
            request.setAttribute("phoneNumber", phoneNumber); // Lưu lại phone number vào request
            request.setAttribute("address1", address1); // Lưu lại address vào request
            request.setAttribute("sex", sex); // Lưu lại sex vào request
            request.setAttribute("dob", dob); // Lưu lại dob vào request
            request.getRequestDispatcher("EditProfile.jsp").forward(request, response);
            return; // Thêm return để tránh chèn dữ liệu trùng
        }
    }
    UserDAO userDao = new UserDAO();
    User user = new User(userID, sex, dob, email, phoneNumber, address1, fullname1);
    userDao.updateUser(user);

        request.setAttribute("mess", "Cập nhật thành công,hãy đăng nhập lại để hiển thị thông tin mới!");
        request.getRequestDispatcher("EditProfile.jsp").forward(request, response);
    }

}
