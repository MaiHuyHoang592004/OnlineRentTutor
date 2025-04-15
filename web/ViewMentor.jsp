<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Mentor" %>
<%@ page import="model.MentorDetail" %>
<%@ page import="model.Skill" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Danh Sách Mentor</title>
    <%@include file="MenteePanner.jsp" %>
    <style>
        table {
            width: 90%;
            margin: auto;
            border-collapse: collapse;
        }
        th, td {
            border: 1px solid black;
            padding: 10px;
            text-align: center;
        }
        th {
            background-color: #f2f2f2;
        }
        img {
            width: 50px;
            height: 50px;
            border-radius: 10%;
        }
        h2 {
            text-align: center;
        }
        .skill-list {
            display: flex;
            flex-wrap: wrap;
            gap: 5px;
            justify-content: center;
        }
        .skill-badge {
            background-color: #007bff;
            color: white;
            padding: 3px 8px;
            border-radius: 5px;
            font-size: 12px;
        }
        .filter-form {
            display: flex;
            justify-content: center;
            align-items: center;
            gap: 10px;
            margin-bottom: 20px;
            padding: 10px;
            background-color: #f9f9f9;
            border-radius: 8px;
            width: 90%;
            margin: auto;
        }
        .filter-form label {
            font-weight: bold;
        }
        .filter-form select,
        .filter-form input,
        .filter-form button {
            padding: 8px;
            border-radius: 5px;
            border: 1px solid #ccc;
        }
        .filter-form button {
            background-color: #007bff;
            color: white;
            cursor: pointer;
            border: none;
        }
        .filter-form button:hover {
            background-color: #0056b3;
        }
        .pagination {
    display: flex;
    justify-content: center;  /* Căn giữa các phần tử trong div */
    align-items: center;      /* Căn giữa theo chiều dọc */
    margin-top: 20px;
    margin-bottom: 20px;
}

.pagination a {
    margin: 0 5px;
    padding: 8px 12px;
    background-color: #007bff;
    color: white;
    text-decoration: none;
    border-radius: 5px;
}

.pagination a.active {
    background-color: #0056b3;
}

.pagination a.disabled {
    background-color: #ccc;
    pointer-events: none;
}

.pagination a:hover:not(.disabled) {
    background-color: #0056b3;
}

    </style>
</head>
<body>
    <h2>Danh Sách Mentor</h2>
    
    <!-- Form lọc và tìm kiếm -->
    <form method="GET" action="ViewMentor" class="filter-form">
        <label for="search">Tìm kiếm:</label>
        <input type="text" name="search" id="search" placeholder="Nhập tên mentor..." value="<%= request.getParameter("search") != null ? request.getParameter("search") : "" %>">
        
        <label for="skill">Lọc kỹ năng:</label>
        <select name="skill" id="skill">
            <option value="">Tất cả</option>
            <% List<Skill> skillsList = (List<Skill>) request.getAttribute("skillsList");
               if (skillsList != null) {
                   for (Skill skill : skillsList) { %>
                       <option value="<%= skill.getId() %>" <%= request.getParameter("skill") != null && request.getParameter("skill").equals(String.valueOf(skill.getId())) ? "selected" : "" %>><%= skill.getName() %></option>
                   <% }
               } %>
        </select>
        
         <label for="sort_rating">Sắp xếp theo đánh giá:</label>
        <select name="sort_rating" id="sort_rating">
            <option value="">Mặc định</option>
            <option value="rating_desc" <%= "rating_desc".equals(request.getParameter("sort_rating")) ? "selected" : "" %>>Cao đến thấp</option>
            <option value="rating_asc" <%= "rating_asc".equals(request.getParameter("sort_rating")) ? "selected" : "" %>>Thấp đến cao</option>
        </select>
        
        <button type="submit">Lọc</button>
    </form>
    
    <table>
        <tr>
            <th>Ảnh Đại Diện</th>
            <th>Họ và Tên</th>
            <th>Email</th>
            <th>Kỹ Năng</th>
            <th>Mô Tả</th>
            <th>Thành Tựu</th>
            <th>Trạng Thái</th>
            <th>Đánh Giá</th>
        </tr>
        <%
            HashMap<Mentor, MentorDetail> mentorList = (HashMap<Mentor, MentorDetail>) request.getAttribute("mentorList");
            HashMap<Integer, List<Skill>> mentorSkills = (HashMap<Integer, List<Skill>>) request.getAttribute("mentorSkills");
            if (mentorList != null && !mentorList.isEmpty()) {
                for (Mentor mentor : mentorList.keySet()) {
                    MentorDetail detail = mentorList.get(mentor);
                    List<Skill> skills = mentorSkills.get(mentor.getId());
        %>
        <tr>           
            <td><img src="<%= mentor.getAvatar() %>" alt="Avatar"></td>
            <td><a href="./MentorDetail?id=<%= mentor.getId() %>"><%= mentor.getFullname() %></a></td>
            <td><%= (detail != null) ? detail.getAccount() : "N/A" %></td>
            <td>
                <div class="skill-list">
                    <% if (skills != null && !skills.isEmpty()) {
                        for (Skill skill : skills) { %>
                        <span class="skill-badge"><%= skill.getName() %></span>
                    <% } } else { %>
                        <span>Không có kỹ năng</span>
                    <% } %>
                </div>
            </td>
            <td><%= mentor.getDescription() %></td>
            <td><%= mentor.getAchivement() %></td>
            <td><%= mentor.getMentorStatus() %></td>
            <td><%= (detail != null) ? detail.getRate() : "N/A" %> ⭐</td>
        </tr>
        <% } } else { %>
        <tr><td colspan="11">Không có dữ liệu</td></tr>
        <% } %>
    </table>
<%-- Phân trang --%>
<div class="pagination">
    <a href="?page=1" class="<%= (Integer.parseInt(request.getAttribute("currentPage").toString()) == 1) ? "disabled" : "" %>">First</a>
    <a href="?page=<%= Integer.parseInt(request.getAttribute("currentPage").toString()) - 1 %>" class="<%= (Integer.parseInt(request.getAttribute("currentPage").toString()) == 1) ? "disabled" : "" %>">Previous</a>

    <% 
        int currentPage = Integer.parseInt(request.getAttribute("currentPage").toString());
        int totalPages = Integer.parseInt(request.getAttribute("totalPages").toString());
        for (int i = 1; i <= totalPages; i++) { 
    %>
        <a href="?page=<%= i %>" class="<%= (i == currentPage) ? "active" : "" %>"><%= i %></a>
    <% } %>
    
    <a href="?page=<%= currentPage + 1 %>" class="<%= (currentPage == totalPages) ? "disabled" : "" %>">Next</a>
    <a href="?page=<%= totalPages %>" class="<%= (currentPage == totalPages) ? "disabled" : "" %>">Last</a>
</div>

    <%@include file="Footer.jsp" %>
</body>
</html>
