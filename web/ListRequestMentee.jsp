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
        <table class="table table-bordered table-striped" 
               style="border-radius: 10px; overflow: hidden; text-align: center; box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);">
            <thead style="background-color: #f7b205; color: white;">
                <tr>
                    <th style="padding: 12px;">Mentor</th>
                    <th style="padding: 12px;">Skill</th>
                    <th style="padding: 12px;">Request Time</th>
                    <th style="padding: 12px;">Deadline Time</th>
                    <th style="padding: 12px;">Status</th>
                    <th style="padding: 12px;">Reason</th>
                    <th style="padding: 12px;">Reason Reject</th>
                    <th style="padding: 12px;"></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="r" items="${requests}">
                    <tr style="transition: background-color 0.3s ease;"
                        onmouseover="this.style.backgroundColor = '#f1f1f1'"
                        onmouseout="this.style.backgroundColor = 'white'">
                        <td style="padding: 10px;">${r.mentorName}</td>
                        <td style="padding: 10px;">${r.skillName}</td>
                        <td style="padding: 10px;">${r.requestTime}</td>
                        <td style="padding: 10px;">${r.deadlineTime}</td>
                        <td style="padding: 10px;">${r.status}</td>
                        <td style="padding: 10px;">${r.reason}</td>
                        <c:if test="${r.rejectReason != null}">
                            <td style="padding: 10px;">${r.rejectReason}</td>
                        </c:if>
                        <td style="padding: 10px;">
                            <c:if test="${r.status == 'Pending'}">
                                <button>Cancel</button>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <%@include file="Footer.jsp" %>
    </body>
</html>
