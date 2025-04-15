<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="model.Skill" %>
<%@ page import="dal.SkillDAO" %>
<%@ page import="java.util.List" %>

<%
    List<Skill> skills = (List<Skill>) request.getAttribute("skills");
%>
<html>
    <head>
        <title>English Skills</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background: #f4f7fa;
                padding: 40px;
            }

            h2 {
                text-align: center;
                color: #2c3e50;
                margin-bottom: 30px;
            }

            .filter-form {
                text-align: center;
                margin-bottom: 40px;
            }

            .filter-form label {
                font-size: 18px;
                margin-right: 10px;
                color: #34495e;
            }

            .filter-form select {
                font-size: 16px;
                padding: 8px;
                border: 1px solid #ccc;
                border-radius: 6px;
                margin-right: 20px;
                width: 250px;
                background-color: #fff;
                transition: border-color 0.3s ease;
            }

            .filter-form select:focus {
                border-color: #3498db;
                outline: none;
            }

            .filter-form button {
                padding: 10px 20px;
                background-color: #3498db;
                color: #fff;
                border: none;
                border-radius: 6px;
                font-size: 16px;
                cursor: pointer;
                transition: background-color 0.3s ease;
            }

            .filter-form button:hover {
                background-color: #2980b9;
            }

            .skill-list {
                display: flex;
                flex-wrap: wrap;
                gap: 20px;
                justify-content: center;
                padding: 0;
            }

            .skill-card {
                background: #fff;
                border-radius: 12px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                width: 280px;
                padding: 20px;
                text-align: center;
                transition: transform 0.2s ease;
                cursor: pointer;
                transition: box-shadow 0.3s ease, transform 0.3s ease;
            }

            .skill-card:hover {
                transform: translateY(-5px);
                box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
            }

            .skill-card img {
                width: 80px;
                height: 80px;
                object-fit: cover;
                margin-bottom: 10px;
                border-radius: 50%;
            }

            .skill-card a {
                text-decoration: none;
                color: #2980b9;
                font-size: 18px;
                font-weight: bold;
            }

            .skill-card p {
                color: #555;
                margin-top: 10px;
                font-size: 14px;
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

        </style>
    </head>
    <body>
        <a href="CustomerHome.jsp" class="back-btn">← Back To Home</a>

                <h2>Danh sách kỹ năng tiếng Anh</h2>
        <form action="SkillDetail" method="get" class="filter-form">
            <label for="skillFilter">Lọc theo kỹ năng: </label>
            <select name="skillFilter" id="skillFilter">
                <option value="">Tất cả kỹ năng</option>
                <% 
                    // Lặp qua danh sách các kỹ năng và hiển thị trong select box
                    for (Skill skill : skills) { 
                %>
                <option value="<%= skill.getName() %>"><%= skill.getName() %></option>
                <% } %>
            </select>
            <button type="submit">Lọc</button>
        </form>


        <div class="skill-list">
            <% for (Skill skill : skills) { %>
            <div class="skill-card">
                <a href="mentorsbyskill?skillId=<%= skill.getId() %>"><%= skill.getName() %></a>
                <p><%= skill.getDescription() %></p>
            </div>
            <% } %>
        </div>
        <%@include file="Footer.jsp" %>
    </body>
</html>
