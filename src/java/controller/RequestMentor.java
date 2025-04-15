/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.MentorDAO;
import dal.RequestMenteeDAO;
import dal.SkillDAO;
import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Skill;
import model.User;
import java.sql.Timestamp;

/**
 *
 * @author zzz
 */
public class RequestMentor extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet RequestMentor</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RequestMentor at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        PrintWriter out = response.getWriter();
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int week = calendar.get(Calendar.WEEK_OF_YEAR);
        User u = (User) session.getAttribute("u");
        if (u == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        int menteeId = u.getUserID();
        int mentorId = Integer.parseInt(request.getParameter("id"));
        request.setAttribute("year", year);
        request.setAttribute("week", week);
        SkillDAO skillDAO = new SkillDAO();
        List<Skill> skills = new ArrayList<>();
        try {
            skills = skillDAO.getSkillsByMentorId(mentorId);
        } catch (Exception ex) {
            Logger.getLogger(RequestMentor.class.getName()).log(Level.SEVERE, null, ex);
        }
        UserDAO ud = new UserDAO();
        MentorDAO mentorDAO = new MentorDAO();
        int price = mentorDAO.getPriceByMentorId(mentorId);
        User mentor = ud.getUserById(mentorId);
        session.setAttribute("skills", skills);
        session.setAttribute("mentorId", mentorId);
        session.setAttribute("mentor", mentor);
        session.setAttribute("price", price);
        request.getRequestDispatcher("RequestMentor.jsp").forward(request, response);

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
