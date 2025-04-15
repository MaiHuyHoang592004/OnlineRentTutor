<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mentor Information</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css">
    <style>
        body {
            font-family: 'Roboto', sans-serif;
            background-color: #f9f9f9;
        }
        .card {
            margin-top: 50px;
        }
        .card-header {
            background-color: #f8f9fa;
            font-weight: bold;
            font-size: 1.2em;
        }
        .status-indicator {
            width: 12px;
            height: 12px;
            border-radius: 50%;
        }
        .status-active {
            background-color: #28a745;
        }
        .status-suspended {
            background-color: #ffc107;
        }
        .status-inactive {
            background-color: #dc3545;
        }
        
        /* Make label text bold */
        .form-group label {
            font-weight: bold;
        }
    </style>
    
</head>
<body>
    <%@ include file="MenteePanner.jsp" %>
    <div class="container">
        <div class="card">
            <div class="card-header">
                Mentor Information
            </div>
            <div class="card-body">
                <div class="form-row">
                    <div class="col-md-6 form-group">
                        <label for="fullname">Full Name:</label>
                        <p>${mentor.fullName != null ? mentor.fullName : "No data provided"}</p>
                    </div>
                    <div class="col-md-6 form-group">
                        <label for="phoneNumber">Phone Number:</label>
                        <p>${mentor.phoneNumber != null ? mentor.phoneNumber : "No data provided"}</p>
                    </div>
                </div>

                <div class="form-row">
                    <div class="col-md-6 form-group">
                        <label for="email">Email:</label>
                        <p>${mentor.email != null ? mentor.email : "No data provided"}</p>
                    </div>
                    <div class="col-md-6 form-group">
                        <label for="address">Address:</label>
                        <p>${mentor.address != null ? mentor.address : "No data provided"}</p>
                    </div>
                </div>

                <div class="form-row">
                    <div class="col-md-6 form-group">
                        <label for="dob">Gender:</label>
                        <p>
                            <c:choose>
                                <c:when test="${mentor.sex == true}">Female</c:when>
                                <c:otherwise>Male</c:otherwise>
                            </c:choose>
                        </p>
                    </div>
                    <div class="col-md-6 form-group">
                        <label for="dob">Date of Birth:</label>
                        <p>${mentor.dob != null ? mentor.dob : "No data provided"}</p>
                    </div>
                </div>

                <div class="form-row">
                    <div class="col-md-6 form-group">
                        <label for="skills">Skills:</label>
                        <c:choose>
                            <c:when test="${not empty mentor.skills}">
                                <ul>
                                    <c:forEach var="skillId" items="${fn:split(mentor.skills, ', ')}">
                                        <c:choose>
                                            <c:when test="${skillId == '1'}"> <li>Tiếng Anh Tiểu Học</li> </c:when>
                                            <c:when test="${skillId == '2'}"> <li>Tiếng Anh THCS</li> </c:when>
                                            <c:when test="${skillId == '3'}"> <li>Tiếng Anh THPT</li> </c:when>
                                            <c:when test="${skillId == '4'}"> <li>IELTS</li> </c:when>
                                            <c:when test="${skillId == '5'}"> <li>TOIEC</li> </c:when>
                                            <c:otherwise> <li>Unknown Skill</li> </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </ul>
                            </c:when>
                            <c:otherwise>No skills data provided</c:otherwise>
                        </c:choose>
                    </div>
                </div>

                <div class="form-row">
                    <div class="col-md-12 form-group">
                        <label for="professionIntroduction">Profession Introduction:</label>
                        <p>${mentor.professionIntroduction != null ? mentor.professionIntroduction : "No data provided"}</p>
                    </div>
                </div>

                <div class="form-row">
                    <div class="col-md-12 form-group">
                        <label for="serviceDescription">Service Description:</label>
                        <p>${mentor.serviceDescription != null ? mentor.serviceDescription : "No data provided"}</p>
                    </div>
                </div>

                <div class="form-row">
                    <div class="col-md-6 form-group">
                        <label for="cashPerSlot">Cash Per Slot:</label>
                        <p>${mentor.cashPerSlot != null ? mentor.cashPerSlot : "No data provided"} vnd</p>
                    </div>
                </div>
                
                <a href="editBecomeMentor" class="btn btn-warning">Edit Form</a>
                <a href="ConfirmDelete.jsp" class="btn btn-warning">Delete Form</a>
            </div>
        </div>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
