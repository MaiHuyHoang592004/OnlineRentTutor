/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.SkillDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import model.Skill;

/**
 *
 * @author zzz
 */
public class SkillDetail extends HttpServlet {

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
            out.println("<title>Servlet SkillDetail</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SkillDetail at " + request.getContextPath() + "</h1>");
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
        SkillDAO dao = new SkillDAO();
        try {
            // Lấy tất cả các kỹ năng
            List<Skill> allSkills = dao.getAll(true);

            // Lấy tham số lọc từ request
            String skillFilter = request.getParameter("skillFilter");

            List<Skill> filteredSkills;

            if (skillFilter != null && !skillFilter.isEmpty()) {
                // Lọc kỹ năng theo tên nếu có lựa chọn
                filteredSkills = allSkills.stream()
                        .filter(skill -> skill.getName().equalsIgnoreCase(skillFilter))
                        .collect(Collectors.toList());
            } else {
                // Nếu không có lọc, sử dụng tất cả kỹ năng
                filteredSkills = allSkills;
            }

            // Lọc các kỹ năng tiếng Anh theo danh sách cố định
            List<String> englishSkillNames = Arrays.asList(
                    "Tiếng Anh Tiểu Học", "Tiếng Anh THCS", "Tiếng Anh THPT", "IELTS", "TOEIC"
            );

            List<Skill> englishSkills = filteredSkills.stream()
                    .filter(skill -> englishSkillNames.contains(skill.getName()))
                    .collect(Collectors.toList());

            request.setAttribute("skills", englishSkills);
            request.getRequestDispatcher("SkillDetail.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        processRequest(request, response);
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
