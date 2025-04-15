package controller;

import dal.BecomeMentorDAO;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.BecomeMentor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//them cai session nay vao cho login de lay roleID
//session.setAttribute("roleID", user.getRoleID());

@WebServlet(name = "BecomeListController", urlPatterns = {"/mentorRequests"})
public class BecomeListController extends HttpServlet {
    private BecomeMentorDAO mentorDAO;

    @Override
    public void init() throws ServletException {
        mentorDAO = new BecomeMentorDAO();
    }

    // Accept mentor request
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        // Check if user has roleID = 2 (which is the Mentor role)
        Integer roleID = (Integer) session.getAttribute("roleID");

        // If the user does not have the roleID of 2, redirect to a different page (like login or error)
//        if (roleID == null || roleID != 2) {
//            response.sendRedirect("Login.jsp"); // Redirect to an error or login page
//            return;
//        }

        // Fetch the list of mentors
        List<BecomeMentor> mentors = mentorDAO.getAllMentors();
        request.setAttribute("mentors", mentors);

        // Forward to the BecomeMentorList.jsp page with the list of mentors
        request.getRequestDispatcher("/manager/BecomeMentorList.jsp").forward(request, response);
    }

        @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        // Kiểm tra quyền truy cập (chỉ admin hoặc role phù hợp mới có quyền)
//        Integer roleID = (Integer) session.getAttribute("roleID");
//        if (roleID == null || roleID != 2) {
//            response.sendRedirect("Login.jsp");
//            return;
//        }

        // Nhận thông tin từ request
        String action = request.getParameter("action");
        int mentorID = Integer.parseInt(request.getParameter("mentorID"));

        if ("accept".equals(action)) {
            // Lấy danh sách kỹ năng từ request (nếu có)
            String[] skillIdsArray = request.getParameterValues("skills[]");
            List<Integer> skillIDs = new ArrayList<>();

            if (skillIdsArray != null) {
                for (String skillId : skillIdsArray) {
                    skillIDs.add(Integer.parseInt(skillId));
                }
            }

            // Chấp nhận mentor và thêm kỹ năng
            boolean success = mentorDAO.acceptMentor(mentorID, skillIDs);

            if (success) {
                request.setAttribute("message", "Mentor accepted successfully with skills.");
            } else {
                request.setAttribute("message", "Failed to accept mentor.");
            }
        }

        if ("reject".equals(action)) {
            boolean success = mentorDAO.rejectMentor(mentorID);

            if (success) {
                request.setAttribute("message", "Mentor rejected successfully.");
            } else {
                request.setAttribute("message", "Failed to reject mentor.");
            }
        }

        // Lấy danh sách mentor mới sau khi cập nhật
        List<BecomeMentor> mentors = mentorDAO.getAllMentors();
        request.setAttribute("mentors", mentors);

        // Chuyển hướng về trang danh sách
        request.getRequestDispatcher("/manager/BecomeMentorList.jsp").forward(request, response);
    }
}
