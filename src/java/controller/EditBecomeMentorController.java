package controller;

import dal.BecomeMentorDAO;
import model.User;
import dal.DBContext;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;

public class EditBecomeMentorController extends HttpServlet {
    private BecomeMentorDAO mentorDAO;

    // Handles GET requests to show the form
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("UpdateForm.jsp");
        dispatcher.forward(request, response);
    }

    // Handles POST requests to submit the form data or delete mentor
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User loggedInUser = (User) session.getAttribute("u"); // Lấy đối tượng User từ session
        if (loggedInUser == null) {
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

            // Update or Insert action
            int userID = loggedInUser.getUserID(); // Lấy UserID từ đối tượng User trong session
            String fullName = request.getParameter("fullname");
            String phoneNumber = request.getParameter("phoneNumber");
            String email = request.getParameter("email");
            String address = request.getParameter("address");
            boolean sex = Boolean.parseBoolean(request.getParameter("sex"));
            java.sql.Date dob = java.sql.Date.valueOf(request.getParameter("dob"));
            String skills = String.join(", ", request.getParameterValues("skills[]"));
            String professionIntroduction = request.getParameter("professionIntroduction");
            String serviceDescription = request.getParameter("serviceDescription");
            int cashPerSlot = Integer.parseInt(request.getParameter("cashPerSlot"));

            // Call update or insert logic here as before
            boolean success = mentorDAO.updateBecomeMentor(userID, fullName, phoneNumber, email, address, sex, dob, skills, professionIntroduction, serviceDescription, cashPerSlot);
            if (success) {
                request.setAttribute("mess", "Profile updated successfully!");
                request.getRequestDispatcher("UpdateForm.jsp").forward(request, response);
            } else {
                request.setAttribute("mess", "Failed to update profile.");
                request.getRequestDispatcher("UpdateForm.jsp").forward(request, response);
            }
        
    }
}
