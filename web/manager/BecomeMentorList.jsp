<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Become Mentor Requests</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css">
</head>
<body>

<div class="container mt-5">
    <h2>Become Mentor Requests</h2>

    <table class="table">
        <thead>
            <tr>
                <th>Full Name</th>
                <th>Phone Number</th>
                <th>Email</th>
                <th>Address</th>
                <th>Skills</th>
                <th>Profession Introduction</th>
                <th>Service Description</th>
                <th>Cash per Slot</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="mentor" items="${mentors}">
                <tr>
                    <td>${mentor.fullName}</td>
                    <td>${mentor.phoneNumber}</td>
                    <td>${mentor.email}</td>
                    <td>${mentor.address}</td>
                    <td>${mentor.skills}</td>
                    <td>${mentor.professionIntroduction}</td>
                    <td>${mentor.serviceDescription}</td>
                    <td>${mentor.cashPerSlot}</td>
                    <td>
                        <!-- Action buttons for accept and reject -->
                        <form action="mentorRequests" method="POST" style="display:inline;">
                            <input type="hidden" name="mentorID" value="${mentor.mentorID}" />
                            <input type="hidden" name="action" value="accept" />
                            <button type="submit" class="btn btn-success">Accept</button>
                        </form>

                        <form action="mentorRequests" method="POST" style="display:inline;">
                            <input type="hidden" name="mentorID" value="${mentor.mentorID}" />
                            <input type="hidden" name="action" value="reject" />
                            <button type="submit" class="btn btn-danger">Reject</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <c:if test="${not empty message}">
        <div class="alert alert-info">
            ${message}
        </div>
    </c:if>

    <!-- Back button -->
    <a href="${pageContext.request.contextPath}/manager/Dashboard" class="btn btn-secondary mt-3">Back to Dashboard</a>
</div>

</body>
</html>
