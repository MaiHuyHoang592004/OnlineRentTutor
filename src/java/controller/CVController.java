/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.CVDAO;
import dal.MentorDAO;
import dal.SkillDAO;
import dal.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.CV;
import model.Mentor;
import model.Skill;
import model.User;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "CVController", urlPatterns = {"/cv"})
public class CVController extends HttpServlet {

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
        SkillDAO skillDAO = new SkillDAO();
        MentorDAO mentorDAO = new MentorDAO();
        CVDAO cvDAO = new CVDAO();

        try {
            // Lấy danh sách kỹ năng từ database
            List<Skill> skills = skillDAO.getAll(true);
            request.setAttribute("skills", skills);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Lấy thông tin user từ session
        User user = (User) request.getSession().getAttribute("u");
        Mentor mentor = null;
        CV cv = null;

        if (user != null) {
            // Lấy mentor theo user ID
            mentor = mentorDAO.getMentor(user.getUserID());
            if (mentor != null) {
                // Lấy CV nếu mentor có
                if (mentor.getCvID() != 0) {
                    try {
                        cv = cvDAO.getCV(mentor.getCvID());
                    } catch (Exception ex) {
                        Logger.getLogger(CVController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else {
                // Nếu không tìm thấy Mentor, tạo mới
                mentor = new Mentor();
                mentor.setId(user.getUserID());
            }
        }

        // Đặt dữ liệu vào request để chuyển sang JSP
        request.setAttribute("mentor", mentor);
        request.setAttribute("cv", cv);
        request.setAttribute("u", user);

        request.getRequestDispatcher("CV.jsp").forward(request, response);
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

        processRequest(request, response);
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
        try {
MentorDAO md = new MentorDAO();
CVDAO cvd = new CVDAO();
UserDAO ud = new UserDAO();
User u = (User) request.getSession().getAttribute("u");
Mentor m = null;
try {
    m = (Mentor) ud.getRole(u.getUserID(), u.getRole());
} catch (SQLException ex) {
    Logger.getLogger(CVController.class.getName()).log(Level.SEVERE, null, ex);
}
String type = request.getParameter("type");
if (type == null) {
    String achivement = request.getParameter("achivement");
    if (m == null || (achivement != null && !achivement.isEmpty() && (m.getAchivement() == null || !m.getAchivement().equals(achivement)))) {
        try {
            md.updateAchivement(u.getUserID(), achivement);
        } catch (Exception e) {
        }
    }
    String descripttion = request.getParameter("description");
    if (m == null || (descripttion != null && !descripttion.isEmpty() && (m.getDescription() == null || !m.getDescription().equals(descripttion)))) {
        try {
            md.updateDescription(u.getUserID(), descripttion);
        } catch (Exception e) {
        }
    }
    
} else {
    if (type.equalsIgnoreCase("create")) {
        String ProfessionIntro = request.getParameter("profession");
        String Description = request.getParameter("service");
        String[] skills = request.getParameterValues("skills");
        String sMoney = request.getParameter("cash");
        int money = Integer.parseInt(sMoney);
        try {
            cvd.createCV(u.getUserID(), ProfessionIntro, Description, skills, money);
        } catch (Exception e) {
        }
    }
//            if (type.equals("sendToAdmim")) {
//                String cvid = request.getParameter("id");
//                System.out.println(cvid);
//                try {
//                    cvd.sendToAdmim(cvid);
//                } catch (Exception e) {
//                    System.out.println("Send to admin: " + e);
//                }
//            }
    else if (type.equalsIgnoreCase("update")) {
        String Profession = request.getParameter("profession");
        String Descrip = request.getParameter("descrip");
        String CashPerSlot = request.getParameter("CashPerSlot");
        if (Profession != null && !Profession.isEmpty() && Descrip != null && !Descrip.isEmpty() && CashPerSlot != null && !CashPerSlot.isEmpty()) {
            try {
                int Money = Integer.parseInt(CashPerSlot);
                cvd.updateCV(m.getCvID(),Profession, Descrip, Money);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    } else if (type.equalsIgnoreCase("delete")) {
        String sid = request.getParameter("id");
        int id = Integer.parseInt(sid);
        try {
            cvd.removeSkill(u.getUserID(), id);
        } catch (Exception e) {
            
        }
    } else if (type.equalsIgnoreCase("add")) {
        String sid = request.getParameter("id");
        int id = Integer.parseInt(sid);
        try {
            cvd.addSkill(u.getUserID(), id);
        } catch (Exception e) {
            
        }
    }
}
request.getSession().setAttribute("Mentor", m);
Mentor r = (Mentor) UserDAO.getRole(u.getUserID(), u.getRole());
request.getSession().setAttribute("Mentor", r);
System.out.println(m);
response.sendRedirect("cv");
        } catch (SQLException ex) {
            Logger.getLogger(CVController.class.getName()).log(Level.SEVERE, null, ex);
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