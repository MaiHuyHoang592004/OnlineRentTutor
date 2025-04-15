package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dal.MentorDAO;
import dal.SkillDAO;
import dal.RateDAO;
import dal.RequestDAO;
import java.util.List;
import model.Mentor;
import model.Skill;
import model.Rate;
import model.User;
import dal.ConversationDAO;
import dal.ScheduleDAO;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Locale;
import model.Conversation;
import model.ScheduleView;

/**
 *
 * @author zzz
 */
public class MentorDetailServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public MentorDetailServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int mentorId = Integer.parseInt(request.getParameter("id"));
            System.out.println(mentorId);
            User mentee = (User) request.getSession().getAttribute("u");

            if (mentee == null) {
                response.sendRedirect("Login.jsp");
                return;
            }

            MentorDAO mentorDAO = new MentorDAO();
            SkillDAO skillDAO = new SkillDAO();
            RateDAO rateDAO = new RateDAO();
            RequestDAO requestDAO = new RequestDAO();
            ScheduleDAO scheduleDAO = new ScheduleDAO();

            // Lấy thông tin mentor
            Mentor mentor = mentorDAO.getMentor(mentorId);

            // Lấy danh sách kỹ năng của mentor
            List<Skill> skills = skillDAO.getSkillsByMentorId(mentorId);
            int price = mentorDAO.getPriceByMentorId(mentorId);

            // Lấy danh sách đánh giá
            List<Rate> rates = rateDAO.getRates(mentorId);

            // Kiểm tra nếu mentee có thể đánh giá mentor (request đã được chấp nhận)
            boolean canRate = requestDAO.isRequestAccepted(mentee.getUserID(), mentorId);
            
            int year = request.getParameter("year") != null ? Integer.parseInt(request.getParameter("year")) : LocalDate.now().getYear();
            int week = request.getParameter("week") != null ? Integer.parseInt(request.getParameter("week")) : LocalDate.now().get(WeekFields.of(Locale.getDefault()).weekOfYear());

            List<ScheduleView> schedules = scheduleDAO.getWeeklySchedule(mentorId, year, week);
           

            // Tính toán startOfWeek và ngày đầu và cuối của từng tuần
            LocalDate startOfWeek = LocalDate.now().withYear(year).with(WeekFields.of(Locale.getDefault()).weekOfYear(), week).with(WeekFields.of(Locale.getDefault()).dayOfWeek(), 1);
            List<String> weekDays = new ArrayList<>();
            for (int i = 0; i < 7; i++) {
                weekDays.add(startOfWeek.plusDays(i).toString());  // Lưu trữ các ngày trong tuần
            }

//        System.out.println(schedules.get(0));
//        System.out.println(schedules.get(0));

            request.setAttribute("year", year);
            request.setAttribute("week", week);
            request.setAttribute("schedules", schedules);

            request.setAttribute("weekDays", weekDays);  // Truyền danh sách các ngày vào JSP

            // Đưa dữ liệu lên request
            request.setAttribute("mentor", mentor);
            request.setAttribute("skills", skills);
            request.setAttribute("rates", rates);
            request.setAttribute("canRate", canRate);
            request.setAttribute("price", price);

            ConversationDAO conversationDAO = new ConversationDAO();
            int conversationID = conversationDAO.getConversationID(mentee.getUserID(), mentor.getId());
            request.setAttribute("conversationID", conversationID);
            // Forward đến trang JSP
            request.getRequestDispatcher("ViewMentorDetail.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
