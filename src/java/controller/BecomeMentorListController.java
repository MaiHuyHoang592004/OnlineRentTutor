package controller;

import dal.BecomeMentorDAO;
import model.BecomeMentor;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class BecomeMentorListController extends HttpServlet {

    private BecomeMentorDAO mentorDAO;

    @Override
    public void init() throws ServletException {
        mentorDAO = new BecomeMentorDAO();  // Initialize DAO instance
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get all mentor applications
        List<BecomeMentor> mentors = mentorDAO.getAllMentors();
        
        // Set the mentors as a request attribute to pass to the JSP
        request.setAttribute("mentors", mentors);
        
        // Forward the request to BecomeMentorList.jsp
        request.getRequestDispatcher("/manager/BecomeMentorList.jsp").forward(request, response);
    }
}
