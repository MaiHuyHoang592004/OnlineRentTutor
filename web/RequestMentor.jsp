<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="model.User" %>
<%@ page import="java.util.List" %>
<%@ page import="dal.SkillDAO" %>
<%@ page import="model.Skill" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>

<%
    User mentor = (User) request.getAttribute("mentor");
    List<Skill> skills = (List<Skill>) request.getAttribute("skills");
%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Tạo Request</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; background-color: #f4f4f4; }
        .container { width: 50%; margin: auto; background: white; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }
        h2 { text-align: center; color: #333; }
        label { font-weight: bold; margin-top: 10px; display: block; }
        input, textarea, select { width: 100%; padding: 8px; margin-top: 5px; border: 1px solid #ccc; border-radius: 5px; }
        .checkbox-group { display: flex; flex-wrap: wrap; gap: 10px; margin-top: 10px; }
        .checkbox-group label { display: flex; align-items: center; cursor: pointer; }
        .checkbox-group input { margin-right: 5px; }
        .buttons { display: flex; justify-content: space-between; margin-top: 20px; }
        .btn { padding: 10px 15px; border: none; border-radius: 5px; cursor: pointer; }
        .btn-primary { background-color: #dc3545; color: white; }
        .btn-secondary { background-color: #ddd; }
        .btn:hover { opacity: 0.8; }
    </style>
</head>
<body>
    <div class="container">
        <h2>Tạo Request</h2>

        <form action="MakeRequest" method="get">
            <input type="hidden" name="mentorId" value="${sessionScope.mentorId}">
            
            <label>Mentor: ${mentor.fullname}</label>
            <label>Price: ${sessionScope.price} VND</label>
            <label for="deadline">Request Deadline:</label>
            <input type="datetime-local" id="deadline" name="deadline" required>

            <label>Chọn Lịch:</label>
            <input type="date" id="schedule" name="schedule" required>
            
            <label>Chọn Slot:</label>
            <select name="slot">
                <option value="5:00:00">5:00</option>
                <option value="7:00:00">7:30</option>
                <option value="10:00:00">10:00</option>
                <option value="12:50:00">12:50</option>
                <option value="15:20:00">15:20</option>
                <option value="17:50:00">17:50</option>
                <option value="20:30:00">20:30</option>
            </select>

            <label for="content">Lí do:</label>
            <textarea id="reason" name="reason" rows="4" placeholder="Nhập yêu cầu..." required></textarea>

            <label>Chọn Kỹ Năng Cần Học:</label>
            <div>
                <c:if test="${skills != null}" >
                    <c:forEach var="skill" items="${sessionScope.skills}">
                        <div style="display: flex; width: 100%">
                            <input type="radio" name="skillId" value="${skill.id}">
                            <div style="width: 100%">${skill.name}</div>
                        </div>
                    </c:forEach>
                </c:if>
            </div>

            <div class="buttons">
                <button type="submit" class="btn btn-primary">Tạo Request</button>
                <button type="button" class="btn btn-secondary" onclick="window.history.back()">Đóng</button>
            </div>
        </form>
    </div>

    <script>
        function showCalendar() {
            alert("Hiển thị lịch (Chưa có tính năng này)");
        }
    </script>
</body>
</html>
