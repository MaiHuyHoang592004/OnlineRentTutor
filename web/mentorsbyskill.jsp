<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<%@ page import="model.Mentor" %>
<%@ page import="model.MentorDetail" %>

<%
    HashMap<Mentor, MentorDetail> mentors = (HashMap<Mentor, MentorDetail>) request.getAttribute("mentors");
%>
<html>
    <head>
        <title>Mentors</title>
        <style>
            body {
                font-family: 'Segoe UI', sans-serif;
                background-color: #eef2f7;
                padding: 40px;
            }
            h2 {
                text-align: center;
                color: #34495e;
            }
            .mentor-list {
                display: flex;
                flex-wrap: wrap;
                gap: 20px;
                justify-content: center;
            }
            .mentor-card {
                background-color: #fff;
                border-radius: 12px;
                width: 300px;
                padding: 20px;
                box-shadow: 0 4px 10px rgba(0,0,0,0.1);
                transition: box-shadow 0.3s ease;
            }
            .mentor-card:hover {
                box-shadow: 0 6px 16px rgba(0,0,0,0.2);
            }
            .mentor-card img {
                border-radius: 50%;
                width: 80px;
                height: 80px;
                object-fit: cover;
                display: block;
                margin: 0 auto 10px auto;
            }
            .mentor-name {
                text-align: center;
                font-size: 18px;
                font-weight: bold;
                color: #2c3e50;
                margin-bottom: 5px;
            }
            .mentor-rating {
                text-align: center;
                color: #f39c12;
                margin-bottom: 10px;
            }
            .mentor-desc {
                font-size: 14px;
                color: #555;
                text-align: center;
            }
            .back-btn {
                display: inline-block;
                margin-bottom: 20px;
                background-color: #3498db;
                color: white;
                padding: 8px 16px;
                text-decoration: none;
                border-radius: 6px;
                transition: background-color 0.3s ease;
            }
            .back-btn:hover {
                background-color: #2980b9;

            }
            /* Nút Gửi Yêu Cầu */
            .btn {
                display: inline-block;
                background-color: #2ecc71; /* Màu xanh lá */
                color: white;
                padding: 10px 20px;
                font-size: 16px;
                border-radius: 6px;
                border: none;
                width: 100%;
                text-align: center;
                cursor: pointer;
                transition: background-color 0.3s ease, transform 0.3s ease;
                font-weight: bold;
            }

            /* Khi hover */
            .btn:hover {
                background-color: #27ae60; /* Màu xanh đậm hơn */
                transform: translateY(-2px);
            }

            /* Khi focus (chọn) */
            .btn:focus {
                outline: none;
                box-shadow: 0 0 5px 2px rgba(46, 204, 113, 0.5); /* Đường viền sáng khi click vào */
            }
        </style>
    </head>
    <body>
        <a href="./SkillDetail" class="back-btn">← Back</a>
        <h2>Mentor</h2>
        <div class="mentor-list">
            <% for (Map.Entry<Mentor, MentorDetail> entry : mentors.entrySet()) {
                Mentor m = entry.getKey();
                MentorDetail d = entry.getValue();
            %>
            <div class="mentor-card">
                <img src="images/<%= m.getAvatar() %>" alt="Mentor Avatar">
                <div class="mentor-name"><%= m.getFullname() %></div>
                <div class="mentor-rating"><%= d.getRate() %> ⭐</div>
                <div class="mentor-desc"><%= m.getDescription() %></div>
                <div style="padding-left: 10px; padding-right: 10px">
                    <a>-------------------------------------------</a>
                    <a href="RequestMentor?id=<%= m.getId() %>">
                        <button class="btn">Gửi Yêu Cầu</button>
                    </a>
                </div>
            </div>
            <% } %>
        </div>
    </body>
</html>
