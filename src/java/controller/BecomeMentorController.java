package controller;

import dal.BecomeMentorDAO;
import model.BecomeMentor;
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
import java.sql.Date;

public class BecomeMentorController extends HttpServlet {
    private BecomeMentorDAO mentorDAO;

    // Handles GET requests to show the form
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Just forward the request to a JSP for showing the form
        RequestDispatcher dispatcher = request.getRequestDispatcher("BecomeMentor.jsp");
        dispatcher.forward(request, response);
    }

    // Handles POST requests to submit the form data
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User loggedInUser = (User) session.getAttribute("u"); // Lấy đối tượng User từ session
        if (loggedInUser == null) {
            // Nếu không tìm thấy thông tin người dùng trong session (người dùng chưa đăng nhập)
            request.setAttribute("mess", "You must be logged in to edit your profile.");
            request.getRequestDispatcher("Login.jsp").forward(request, response);
            return;
        }

        // Initialize the DAO here (if not already done in init())
        if (mentorDAO == null) {
            DBContext dbContext = new DBContext();
            Connection connection = dbContext.getConnection();
            mentorDAO = new BecomeMentorDAO(connection);
        }

        int userID = loggedInUser.getUserID(); // Lấy UserID từ đối tượng User trong session
        String fullName = request.getParameter("fullname");
        String phoneNumber = request.getParameter("phoneNumber");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        boolean sex = Boolean.parseBoolean(request.getParameter("sex"));
        Date dob = Date.valueOf(request.getParameter("dob"));  // Assuming date is passed in yyyy-MM-dd format
        String skills = String.join(", ", request.getParameterValues("skills[]"));  // Retrieve skills as comma-separated string
        String professionIntroduction = request.getParameter("professionIntroduction");
        String serviceDescription = request.getParameter("serviceDescription");
        int cashPerSlot = Integer.parseInt(request.getParameter("cashPerSlot"));

        // Insert mentor into the database
        boolean success = mentorDAO.insertBecomeMentor(userID, fullName, phoneNumber, email, address, sex, dob, skills, professionIntroduction, serviceDescription, cashPerSlot);

        if (success) {
            BecomeMentor mentor = mentorDAO.getMentorByID(userID);
            request.setAttribute("mentor", mentor);
            request.setAttribute("fullname", mentor.getFullName());
            request.setAttribute("phoneNumber", mentor.getPhoneNumber());
            request.setAttribute("email", mentor.getEmail());
            request.setAttribute("address", mentor.getAddress());
                if (((User) session.getAttribute("u")).getSex() == true) {
                    session.setAttribute("gender", "Female");
                }else if (((User) session.getAttribute("u")).getSex() == false) {
                    session.setAttribute("gender", "Male");
                }
            request.setAttribute("dob", mentor.getDob());
            request.setAttribute("skills", mentor.getSkills());
            request.setAttribute("professionIntroduction", mentor.getProfessionIntroduction());
            request.setAttribute("serviceDescription", mentor.getServiceDescription());
            request.setAttribute("cashPerSlot", mentor.getCashPerSlot());
            request.setAttribute("mess", "Nộp form thành công . Chúng tôi sẽ liên hệ lại với bạn qua email!");
            request.getRequestDispatcher("BecomeMentor.jsp").forward(request, response);  // Return to the form page
        } else {
            // Set an error message and forward back to the form
            request.setAttribute("mess", "Bạn đã nộp form , vui lòng không nộp lần 2.");
            request.getRequestDispatcher("BecomeMentor.jsp").forward(request, response);
        }
    }
}
