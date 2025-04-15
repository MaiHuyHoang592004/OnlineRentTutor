package controller;

import dal.BecomeMentorDAO;
import model.BecomeMentor;
import model.User;
import dal.DBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;

public class ViewFormController extends HttpServlet {
    private BecomeMentorDAO mentorDAO;

    @Override
    public void init() throws ServletException {
        // Initialize the DAO with database connection
        DBContext dbContext = new DBContext();
        Connection connection = dbContext.getConnection();
        mentorDAO = new BecomeMentorDAO(connection);
    }

    // Handles GET requests to show the mentor's information
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the current logged-in user from session
        HttpSession session = request.getSession();
        User loggedInUser = (User) session.getAttribute("u");

        // If no user is logged in, redirect to login page
        if (loggedInUser == null) {
            request.setAttribute("mess", "You must be logged in to view your profile.");
            request.getRequestDispatcher("Login.jsp").forward(request, response);
            return;
        }

        // Get the logged-in user's ID
        int userID = loggedInUser.getUserID();

        // Retrieve the mentor's information from the database
        BecomeMentor mentor = mentorDAO.getMentorByID(userID);
        
        // If mentor is not found, redirect or show error message
        if (mentor == null) {
            request.setAttribute("mess", "No mentor found for your account.");
            request.getRequestDispatcher("ViewForm.jsp").forward(request, response);
            return;
        }

        // Set the mentor object as request attributes to display in JSP
        request.setAttribute("mentor", mentor);
        request.setAttribute("fullname", mentor.getFullName());
        request.setAttribute("phoneNumber", mentor.getPhoneNumber());
        request.setAttribute("email", mentor.getEmail());
        request.setAttribute("address", mentor.getAddress());
        request.setAttribute("dob", mentor.getDob());
        request.setAttribute("skills", mentor.getSkills());
        request.setAttribute("professionIntroduction", mentor.getProfessionIntroduction());
        request.setAttribute("serviceDescription", mentor.getServiceDescription());
        request.setAttribute("cashPerSlot", mentor.getCashPerSlot());

        // Forward the request to the ViewForm.jsp page
        request.getRequestDispatcher("ViewForm.jsp").forward(request, response);
    }
}
