<%@ page import="java.util.List" %>
<%@ page import="model.Mentor" %>
<%@ page import="model.MentorDetail" %>
<%@ page import="model.Skill" %>
<%@ page import="model.User" %>
<%@ page import="model.Rate" %>
<%@ page import="model.Request" %>
<%@ page import="model.Mentor" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Chi Tiết Mentor</title>
        <%@include file="MenteePanner.jsp" %>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"/>
        <style>
            th, td {
                text-align: center;
                vertical-align: middle;
            }
            .slot-cell {
                height: 100px;
                cursor: pointer;
            }
            .done {
                background-color: #d4edda;
            }
            .notyet {
                background-color: #f8d7da;
            }
            .empty {
                background-color: #f1f1f1;
            }
            .sunday {
                color: red;
            }
            .saturday {
                color: blue;
            }
            a.meet-link {
                display: inline-block;
                padding: 8px 16px;
                background-color: #007bff; /* Màu nền xanh */
                color: white; /* Màu chữ trắng */
                text-decoration: none; /* Xóa gạch dưới */
                border-radius: 4px; /* Bo góc */
                font-weight: bold; /* Làm đậm chữ */
                transition: background-color 0.3s ease, transform 0.2s ease; /* Hiệu ứng chuyển đổi mượt mà */
            }

            a.meet-link:hover {
                background-color: #0056b3; /* Màu nền khi hover (nhấn chuột) */
                transform: scale(1.1); /* Phóng to nút khi hover */
            }

            a.meet-link:active {
                transform: scale(1); /* Khi nhấn, trở lại kích thước ban đầu */
            }
            body {
                font-family: Arial, sans-serif;
                margin: 30px;
            }
            .container {
                width: 70%;
                margin: auto;
                border: 1px solid #ddd;
                padding: 20px;
                border-radius: 10px;
                box-shadow: 2px 2px 10px #aaa;
                text-align: center;
            }
            h2 {
                color: #007bff;
            }
            .info {
                text-align: left;
                margin-bottom: 20px;
            }
            .info p {
                font-size: 16px;
                line-height: 1.5;
            }
            .skill-list {
                margin-bottom: 20px;
            }
            .rating-form {
                margin-top: 20px;
            }
            .star {
                color: gold;
                font-size: 20px;
            }
            .review-list {
                text-align: left;
                margin-top: 20px;
            }
            .review-item {
                display: flex;
                align-items: center;
                margin-bottom: 10px;
            }
            .avatar {
                width: 40px;
                height: 40px;
                border-radius: 50%;
                margin-right: 10px;
            }
            .btn {
                background-color: #007bff;
                color: white;
                padding: 10px 20px;
                border: none;
                border-radius: 5px;
                cursor: pointer;
            }
            .btn:hover {
                background-color: #0056b3;
            }
            .img {
                width: 150px;
                height: 150px;
                border-radius: 50%;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <a href="./ViewMentor" class="btn btn-back">⬅ Quay lại danh sách Mentor</a>

            <%-- Lấy dữ liệu mentor --%>
            <%
                Mentor mentor = (Mentor) request.getAttribute("mentor");
                List<Skill> skills = (List<Skill>) request.getAttribute("skills");
                List<Rate> rates = (List<Rate>) request.getAttribute("rates");
                Boolean canRateObj = (Boolean) request.getAttribute("canRate");
                boolean canRate = (canRateObj != null) ? canRateObj : false;
                User mentee = (User) session.getAttribute("u");
                if (mentor != null) {
            %>

            <h2>Thông Tin Mentor: <%= mentor.getFullname() %></h2>
            <img src="<%= mentor.getAvatar() %>" alt="Avatar" class="img">
            <div class="info">
                <p><strong>Thành Tựu:</strong> <%= mentor.getAchivement() %></p>
                <p><strong>Mô Tả:</strong> <%= mentor.getDescription() %></p>
                <p><strong>Trạng Thái:</strong> <%= mentor.getMentorStatus() %></p>
            </div>

            <%-- Hiển thị danh sách kỹ năng --%>
            <div class="skill-list">
                <h3>Kỹ Năng</h3>
                <ul>
                    <% if (skills != null && !skills.isEmpty()) { %>
                    <% for (Skill skill : skills) { %>
                    <li><%= skill.getName() %> - <%= skill.getDescription() %></li>
                        <% } %>
                        <% } else { %>
                    <p>Chưa có kỹ năng nào được cập nhật.</p>
                    <% } %>
                </ul>
            </div>

            <%-- Các nút hành động --%>
            <div style="display: flex; justify-content: center">
                <div>
                    <form action="followMentor" method="get">
                        <input type="hidden" name="mentorId" value="<%= mentor.getId() %>"/>
                        <button class ="btn">Follow</button>
                    </form>
                </div>
                <div style="padding-left: 10px; padding-right: 10px">
                    <a href="RequestMentor?id=<%= mentor.getId() %>"><button class="btn">Gửi Yêu Cầu</button></a>
                </div>
                <%
        Integer conversationID = (Integer) request.getAttribute("conversationID");
        if (conversationID == null) conversationID = -1; // Nếu không tìm thấy, đặt giá trị mặc định
                %>
                <div>
                    <a href="ChatServlet?conversationID=<%= conversationID %>">
                        <button class="btn" <%= (conversationID == -1) ? "disabled" : "" %>>Chat</button>
                    </a>
                </div>

            </div>
            <div>
                <a href="./schedule?id=<%= mentor.getId()%>">
                    <button class="btn">Schedule của mentor</button>
                </a>
            </div>

            <table class="table table-bordered">
                <thead class="table-primary">
                    <tr>
                        <th>Slot</th>
                            <c:forEach var="day" items="${weekDays}" varStatus="status">
                            <th class="${status.index == 0 ? 'sunday' : status.index == 6 ? 'saturday' : ''}">${day}</th>
                            </c:forEach>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="slot" begin="0" end="6">
                        <tr>
                            <td><strong>Slot ${slot}</strong></td>
                            <c:forEach var="day" items="${weekDays}">
                                <td class="slot-cell">
                                    <c:forEach var="s" items="${schedules}">
                                        <c:if test="${s.slot == slot && s.date == day}">
                                            <div class="${s.status == 'Done' ? 'done' : 'notyet'} p-1">
                                                <strong>${s.skillName}</strong><br/>
                                                ${s.status}<br/>
                                                <small>${s.rejectReason}</small><br/>
                                            </div>
                                        </c:if>
                                    </c:forEach>
                                </td>
                            </c:forEach>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>



            <%-- Hiển thị form đánh giá nếu mentee có thể đánh giá mentor --%>
            <% if (mentee != null) { %>
            <div class="rating-form" style="max-width: 500px; margin: 30px auto; padding: 20px; border: 1px solid #ddd; border-radius: 10px; background-color: #f9f9f9; box-shadow: 0 2px 8px rgba(0,0,0,0.05);">
                <h3 style="margin-bottom: 20px; text-align: center; color: #333;">Đánh giá Mentor</h3>
                <form action="createRate" method="get" style="display: flex; flex-direction: column; gap: 15px;">
                    <input type="hidden" name="mentorId" value="<%= mentor.getId() %>">
                    <input type="hidden" name="menteeId" value="<%= mentee.getUserID() %>">

                    <label for="rating" style="font-weight: bold; color: #444;">Số sao (1-5):</label>
                    <select name="rating" required style="padding: 10px; border-radius: 6px; border: 1px solid #ccc; font-size: 16px;">
                        <option value="1">⭐</option>
                        <option value="2">⭐⭐</option>
                        <option value="3">⭐⭐⭐</option>
                        <option value="4">⭐⭐⭐⭐</option>
                        <option value="5">⭐⭐⭐⭐⭐</option>
                    </select>

                    <label for="comment" style="font-weight: bold; color: #444;">Bình luận:</label>
                    <textarea name="comment" required rows="4" style="padding: 10px; border-radius: 6px; border: 1px solid #ccc; font-size: 16px; resize: vertical;"></textarea>

                    <button type="submit" style="padding: 10px 20px; border: none; border-radius: 6px; background-color: #4CAF50; color: white; font-size: 16px; cursor: pointer; transition: background-color 0.3s;">
                        Gửi Đánh Giá
                    </button>
                </form>
            </div>

            <% } %>

            <%-- Hiển thị đánh giá từ các mentee khác --%>
            <h3>Đánh Giá Từ Mentee</h3>
            <div class="review-list" style="display: flex; flex-direction: column; gap: 16px; margin: 20px 0; padding: 10px; border-top: 1px solid #ccc;">
                <% if (rates != null) { %>
                <% for (Rate rate : rates) { %>
                <div class="review-item" style="display: flex; align-items: flex-start; gap: 12px; padding: 12px; border: 1px solid #eee; border-radius: 8px; background-color: #fafafa;">
                    <img src="<%= mentee.getAvatar() %>" class="avatar" style="width: 50px; height: 50px; border-radius: 50%; object-fit: cover; border: 1px solid #ddd;">
                    <div style="flex: 1;">
                        <div style="font-weight: bold; font-size: 16px; color: #333;"><%= rate.getSenderName() %></div>
                        <div style="color: #f5a623; margin: 4px 0;">
                            <% for (int i = 0; i < rate.getNoStar(); i++) { %>
                            ⭐
                            <% } %>
                        </div>
                        <p style="margin: 0; color: #555;">"<%= rate.getRateTime() %>"</p>
                        <p style="margin: 0; color: #555;">"<%= rate.getContent() %>"</p>
                    </div>
                </div>
                <% } %>
                <% } else { %>
                <p style="color: #888; font-style: italic;">Chưa có đánh giá nào.</p>
                <% } %>
            </div>
            <% } else { %>
            <p style="color: red;">Không tìm thấy thông tin mentor.</p>
            <% } %>

        </div>

    </body>

</html>
