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
                border-radius: 50%;
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
        <h2>Ðã Theo Dõi</h2>
        <table>
            <c:if test="${sessionScope.follows != null}">
                <c:forEach var="f" items="${sessionScope.follows}">
                    <tr>
                        <td><img src="/assets/images/avatar/${f.avatar}"></td>
                        <td>${f.fullname}</td>
                        <td>
                            <form action="listFollow" method="post">
                                <input type="hidden" name="followId" value="${f.followId}"/>
                                <button type="submit">Unfollow</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </c:if>
        </table>
        <%@include file="Footer.jsp" %>
    </body>
</html>
