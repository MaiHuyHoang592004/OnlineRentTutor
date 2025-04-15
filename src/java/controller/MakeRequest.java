/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dal.RequestMenteeDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;

/**
 *
 * @author zzz
 */
public class MakeRequest extends HttpServlet {
  
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
            out.println("<title>Servlet MakeRequest</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MakeRequest at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
        User u = (User) session.getAttribute("u");
        if (u == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        int menteeId = u.getUserID();
        int mentorId = (int) session.getAttribute("mentorId");
        String schedule = request.getParameter("schedule");//có kiểu date bên jsp
        String slot = request.getParameter("slot");// có kiểu string bên jsp dạng 7:00:00
        String requestTimeRaw = schedule + " " + slot;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date parsedDate = null;
        try {
            parsedDate = sdf.parse(requestTimeRaw);
        } catch (ParseException ex) {
            Logger.getLogger(RequestMentor.class.getName()).log(Level.SEVERE, null, ex);
        }
        Timestamp requestTime = new Timestamp(parsedDate.getTime());
        SimpleDateFormat sdfde = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        String deadlineRaw = request.getParameter("deadline");
        java.util.Date parsedDeadline = null;
        try {
            parsedDeadline = sdfde.parse(deadlineRaw);
        } catch (ParseException ex) {
            Logger.getLogger(RequestMentor.class.getName()).log(Level.SEVERE, null, ex);
        }
        Timestamp deadline = new Timestamp(parsedDeadline.getTime());
        String reason = request.getParameter("reason");
        int skillId = Integer.parseInt(request.getParameter("skillId"));
        RequestMenteeDAO med = new RequestMenteeDAO();
        med.createRequest(menteeId, mentorId, reason, requestTime, deadline, skillId);
        String url = "./MentorDetail?id="+mentorId;
        request.getRequestDispatcher(url).forward(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
