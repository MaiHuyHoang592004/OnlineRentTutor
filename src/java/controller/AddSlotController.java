/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.ScheduleDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import model.Skill;
import model.User;

/**
 *
 * @author ACER
 */
public class AddSlotController extends HttpServlet {

    private ScheduleDAO dao;

    @Override
    public void init() throws ServletException {
        dao = new ScheduleDAO();
    }

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
            out.println("<title>Servlet AddSlotController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddSlotController at " + request.getContextPath() + "</h1>");
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int mentorID = Integer.parseInt(request.getParameter("mentorID"));
        int year = Integer.parseInt(request.getParameter("year"));
        int week = Integer.parseInt(request.getParameter("week"));
        System.out.println(mentorID);
        User u = (User) request.getSession().getAttribute("u");
        // Lấy danh sách kỹ năng của Mentor từ DB
        List<Skill> mentorSkills = dao.getSkillsForMentor(mentorID);
        //request.setAttribute("mentorSkills", mentorSkills);

        System.out.println(mentorSkills.toArray());
        // Truyền danh sách kỹ năng vào JSP
        request.setAttribute("mentorSkills", mentorSkills);

        // Forward đến trang add-slot.jsp
        request.getRequestDispatcher("/add-slot.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int mentorID = Integer.parseInt(request.getParameter("mentorID"));
            String selectedDate = request.getParameter("selectedDate");  // yyyy-MM-dd
            String slotHour = request.getParameter("slotHour");         // HH:mm
            String link = request.getParameter("link");  // ✅ thêm dòng này
            if (link == null || link.isEmpty()) {
                link = null; // Gán null nếu không có giá trị
            }

            // Kết hợp ngày + giờ => parse ra java.util.Date
            String combinedDateTime = selectedDate + " " + slotHour;
            java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(combinedDateTime);
            java.sql.Timestamp startAt = new java.sql.Timestamp(utilDate.getTime());

            // Convert sang java.sql.Timestamp
            java.sql.Timestamp sqlDate = new java.sql.Timestamp(utilDate.getTime());

            int skillID = Integer.parseInt(request.getParameter("skillID"));
            String status = request.getParameter("status");

            Calendar cal = Calendar.getInstance();
            cal.setTime(startAt);
            // ❌ Nếu slot đã tồn tại thì báo lỗi và quay lại
            if (dao.isSlotExist(mentorID, sqlDate)) {
                request.setAttribute("error", "❌ Slot này đã tồn tại. Vui lòng chọn slot khác.");

                // Gửi lại thông tin cần thiết để hiển thị lại form
                request.setAttribute("mentorSkills", dao.getSkillsForMentor(mentorID));
                request.setAttribute("mentorID", mentorID);
                request.setAttribute("year", cal.get(Calendar.YEAR));
                request.setAttribute("week", cal.get(Calendar.WEEK_OF_YEAR));

                // Forward lại form add
                request.getRequestDispatcher("/add-slot.jsp").forward(request, response);
                return;
            }

            // ✅ Nếu chưa có thì thêm mới
            dao.addSlot(mentorID, sqlDate, skillID, status, link);

            response.sendRedirect("schedule?mentorID=" + mentorID);
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("Lỗi thêm slot: " + e.getMessage());
        }
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
