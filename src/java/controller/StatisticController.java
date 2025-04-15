package controller;

import dal.MenteeDAO;
import dal.MentorDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.MenteeStatistic;
import model.MentorStatistic;
import model.User;

@WebServlet(name="StatisticController", urlPatterns={"/statistics"})
public class StatisticController extends HttpServlet {
    
     protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        User u = (User)request.getSession().getAttribute("u");
        if(u.getRole().equalsIgnoreCase("Mentee")) {
            MenteeDAO md = new MenteeDAO();
            MenteeStatistic ms = md.getMenteeStatistic(u.getUserID());
            request.setAttribute("mstatistic", ms);
        } else {
            MentorDAO mtd = new MentorDAO();
            MentorStatistic ms = mtd.getMentorStatistic(u.getUserID());
            request.setAttribute("mstatistic", ms);
        }
        request.getRequestDispatcher("statistic.jsp").forward(request, response);
    } 

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
