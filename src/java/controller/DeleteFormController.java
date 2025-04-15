package controller;

import dal.BecomeMentorDAO;
import dal.DBContext;
import model.User;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;

public class DeleteFormController extends HttpServlet {

    private BecomeMentorDAO mentorDAO;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        // Lấy đối tượng user từ session
        HttpSession session = request.getSession();
        User loggedInUser = (User) session.getAttribute("u"); // Lấy đối tượng User từ session

        if (loggedInUser == null) {
            // Nếu không tìm thấy thông tin người dùng trong session (người dùng chưa đăng nhập)
            request.setAttribute("mess", "You must be logged in to perform this action.");
            request.getRequestDispatcher("Login.jsp").forward(request, response);
            return;
        }

        // Initialize the DAO
        if (mentorDAO == null) {
            DBContext dbContext = new DBContext();
            Connection connection = dbContext.getConnection();
            mentorDAO = new BecomeMentorDAO(connection);
        }
        int userID = loggedInUser.getUserID();
        // Xóa Mentor khỏi database
        boolean success = mentorDAO.deleteBecomeMentor(userID);

        if (success) {
            // Nếu xóa thành công, thông báo thành công và chuyển hướng đến trang danh sách
            request.setAttribute("mess", "Mentor deleted successfully.");
            response.sendRedirect("CustomerHome.jsp");  // Chuyển hướng về trang danh sách Mentor
        } else {
            // Nếu xóa không thành công, thông báo lỗi và quay lại trang form
            request.setAttribute("error", "Failed to delete mentor.");
            request.getRequestDispatcher("ViewForm.jsp").forward(request, response);
        }
    }
}
