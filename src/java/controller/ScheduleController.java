package controller;

import dal.ScheduleDAO;
import dal.SkillDAO;
import model.ScheduleView;
import model.Skill;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.*;
import model.User;
import model.Mentor;

public class ScheduleController extends HttpServlet {

    private ScheduleDAO scheduleDAO;
    private SkillDAO skillDAO;

    @Override
    public void init() throws ServletException {
        scheduleDAO = new ScheduleDAO();
        skillDAO = new SkillDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User u = (User) request.getSession().getAttribute("u");
        int mentorID = request.getParameter("mentorID") != null ? Integer.parseInt(request.getParameter("mentorID")) : 6;
        int year = request.getParameter("year") != null ? Integer.parseInt(request.getParameter("year")) : LocalDate.now().getYear();
        int week = request.getParameter("week") != null ? Integer.parseInt(request.getParameter("week")) : LocalDate.now().get(WeekFields.of(Locale.getDefault()).weekOfYear());

        List<ScheduleView> schedules = scheduleDAO.getWeeklySchedule(mentorID, year, week);
        List<Skill> mentorSkills = skillDAO.getSkillsByMentor(mentorID);

        // Tính toán startOfWeek và ngày đầu và cuối của từng tuần
        LocalDate startOfWeek = LocalDate.now().withYear(year).with(WeekFields.of(Locale.getDefault()).weekOfYear(), week).with(WeekFields.of(Locale.getDefault()).dayOfWeek(), 1);
        List<String> weekDays = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            weekDays.add(startOfWeek.plusDays(i).toString());  // Lưu trữ các ngày trong tuần
        }

//        System.out.println(schedules.get(0));
//        System.out.println(schedules.get(0));
        request.setAttribute("mentorID", mentorID);
        request.setAttribute("year", year);
        request.setAttribute("week", week);
        request.setAttribute("schedules", schedules);
        request.setAttribute("mentorSkills", mentorSkills);
        request.setAttribute("weekDays", weekDays);  // Truyền danh sách các ngày vào JSP

        request.getRequestDispatcher("schedule.jsp").forward(request, response);
    }
}
