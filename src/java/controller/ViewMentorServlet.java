package controller;

import dal.MentorDAO;
import dal.SkillDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import model.Mentor;
import model.MentorDetail;
import model.Skill;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ViewMentorServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ViewMentorServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MentorDAO mentorDAO = new MentorDAO();
        SkillDAO skillDAO = new SkillDAO();
        HashMap<Mentor, MentorDetail> mentorList = null;
        
        // Nhận các tham số phân trang từ request
        int page = 1; // Mặc định trang 1
        int pageSize = 10; // Mỗi trang hiển thị 10 Mentor
        String pageParam = request.getParameter("page");
        if (pageParam != null) {
            page = Integer.parseInt(pageParam);
        }
        
        // Lọc và tìm kiếm
        String search = request.getParameter("search");
        String skillIdParam = request.getParameter("skill");
        String sortParam = request.getParameter("sort");
        Integer skillId = (skillIdParam != null && !skillIdParam.isEmpty()) ? Integer.parseInt(skillIdParam) : null;

        try {
            // Lấy danh sách Mentor (Phân trang)
            mentorList = mentorDAO.getAllWithDetail(page, pageSize);

            // Lấy danh sách kỹ năng của từng mentor
            HashMap<Integer, List<Skill>> mentorSkills = new HashMap<>();
            for (Mentor mentor : mentorList.keySet()) {
                List<Skill> skills = skillDAO.getSkillsByMentorId(mentor.getId());
                mentorSkills.put(mentor.getId(), skills);
            }

            // Lọc theo kỹ năng nếu có chọn kỹ năng
            if (skillId != null) {
                mentorList.entrySet().removeIf(entry -> 
                    mentorSkills.get(entry.getKey().getId()) == null || 
                    mentorSkills.get(entry.getKey().getId()).stream().noneMatch(skill -> skill.getId() == skillId)
                );
            }

            // Tìm kiếm theo tên Mentor
            if (search != null && !search.trim().isEmpty()) {
                mentorList.entrySet().removeIf(entry -> 
                    !entry.getKey().getFullname().toLowerCase().contains(search.toLowerCase())
                );
            }

            // Sắp xếp Mentor theo yêu cầu
            if (sortParam != null) {
                mentorList = sortMentors(mentorList, sortParam);
            }

            // Tổng số trang
            int totalMentors = mentorDAO.getTotalMentor();
            int totalPages = (int) Math.ceil((double) totalMentors / pageSize);
            
            // Gửi dữ liệu tới JSP
            request.setAttribute("mentorList", mentorList);
            request.setAttribute("mentorSkills", mentorSkills);
            request.setAttribute("skillsList", skillDAO.getAll());
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        request.getRequestDispatcher("ViewMentor.jsp").forward(request, response);
    }

    // Phương thức sắp xếp
    private HashMap<Mentor, MentorDetail> sortMentors(HashMap<Mentor, MentorDetail> mentorList, String sortParam) {
        List<Map.Entry<Mentor, MentorDetail>> sortedList = mentorList.entrySet().stream().collect(Collectors.toList());
        switch (sortParam) {
            case "rating_desc":
                sortedList.sort((a, b) -> Double.compare(b.getValue().getRate(), a.getValue().getRate()));
                break;
            case "rating_asc":
                sortedList.sort((a, b) -> Double.compare(a.getValue().getRate(), b.getValue().getRate()));
                break;
            case "request_desc":
                sortedList.sort((a, b) -> Integer.compare(b.getValue().getRequests(), a.getValue().getRequests()));
                break;
            case "request_asc":
                sortedList.sort((a, b) -> Integer.compare(a.getValue().getRequests(), b.getValue().getRequests()));
                break;
            case "complete_desc":
                sortedList.sort((a, b) -> Double.compare(b.getValue().getCompletePercent(), a.getValue().getCompletePercent()));
                break;
            case "complete_asc":
                sortedList.sort((a, b) -> Double.compare(a.getValue().getCompletePercent(), b.getValue().getCompletePercent()));
                break;
        }
        HashMap<Mentor, MentorDetail> sortedMap = new HashMap<>();
        for (Map.Entry<Mentor, MentorDetail> entry : sortedList) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }
}
