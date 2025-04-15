<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.time.LocalDate, java.time.temporal.WeekFields, java.util.Locale" %>

<%
    int year = request.getParameter("year") != null ? Integer.parseInt(request.getParameter("year")) : LocalDate.now().getYear();
    int week = request.getParameter("week") != null ? Integer.parseInt(request.getParameter("week")) : LocalDate.now().get(WeekFields.of(Locale.getDefault()).weekOfYear());
    int mentorID = request.getParameter("mentorID") != null ? Integer.parseInt(request.getParameter("mentorID")) : 1;

    WeekFields weekFields = WeekFields.of(Locale.getDefault());
    LocalDate startOfWeek = LocalDate.now().withYear(year).with(weekFields.weekOfYear(), week).with(weekFields.dayOfWeek(), 1);
%>

<html>

        <title>Tạo Slot Mới</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"/>
    </head>
    <body class="container mt-5">
        <h2 class="mb-4">Tạo Slot Mới - Tuần <%= week %> / <%= year %></h2>

        <c:if test="${not empty error}">
            <div class="alert alert-danger">
                ${error}
            </div>
        </c:if>

        <form action="addSlot" method="post">
            <input type="hidden" name="mentorID" value="<%= mentorID %>" />
            <div class="mb-3">
                <label>Chọn ngày trong tuần:</label>
                <select name="selectedDate" class="form-select">
                    <% for (int i = 0; i < 7; i++) {
                        LocalDate d = startOfWeek.plusDays(i);
                    %>
                    <option value="<%= d %>"><%= d.getDayOfWeek() %> - <%= d %></option>
                    <% } %>
                </select>
            </div>
            <div class="mb-3">
                <label>Khung giờ (slot):</label>
                <select name="slotHour" class="form-select">
                    <option value="05:00">Slot 0 - 05:00</option>
                    <option value="07:30">Slot 1 - 07:30</option>
                    <option value="10:00">Slot 2 - 10:00</option>
                    <option value="12:50">Slot 3 - 12:50</option>
                    <option value="15:20">Slot 4 - 15:20</option>
                    <option value="17:50">Slot 5 - 17:50</option>
                    <option value="20:30">Slot 6 - 20:30</option>
                </select>
            </div>
            <div class="mb-3">
                <label>Kỹ năng:</label>
                <select name="skillID" class="form-select">
                    <c:forEach var="skill" items="${mentorSkills}">
                        <option value="${skill.id}">${skill.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="mb-3">
                <label>Meeting Link (optional):</label>
                <input type="text" name="link" class="form-control" placeholder="https://..." />
            </div>
            <div class="mb-3">
                <label>Trạng thái:</label>
                <select name="status" class="form-select">
                    <option value="Not Yet">Chưa xác nhận</option>
                    <option value="Done">Đã xác nhận</option>
                </select>
            </div>
            <button type="submit" class="btn btn-primary">Tạo mới</button>
            <a href="schedule?mentorID=<%= mentorID %>&year=<%= year %>&week=<%= week %>" class="btn btn-secondary">Quay lại</a>
        </form>
    </body>
</html>
