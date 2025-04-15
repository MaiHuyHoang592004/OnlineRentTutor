<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Mentor" %>
<%@ page import="model.MentorDetail" %>
<%@ page import="model.Skill" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Danh S?ch Mentor</title>
        <%@include file="MentorHeader.jsp" %>
        <style>
    /* General styling for the page */
    body {
        font-family: 'Arial', sans-serif;
        background-color: #f4f7fa;
        padding: 40px;
        margin: 0;
    }

    h2 {
        text-align: center;
        color: #333;
        font-size: 28px;
        margin-bottom: 30px;
    }

    /* Table Styling */
    table {
        width: 90%;
        margin: auto;
        border-collapse: collapse;
        box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        border-radius: 8px;
        background-color: white;
    }

    th, td {
        padding: 12px;
        text-align: center;
        border: 1px solid #ddd;
    }

    th {
        background-color: #007bff;
        color: white;
        font-weight: bold;
    }

    td {
        background-color: #f9f9f9;
        color: #333;
    }

    td:hover {
        background-color: #f1f1f1;
    }

    img {
        width: 50px;
        height: 50px;
        border-radius: 50%;
    }

    .skill-list {
        display: flex;
        flex-wrap: wrap;
        gap: 10px;
        justify-content: center;
        padding: 10px;
    }

    .skill-badge {
        background-color: #007bff;
        color: white;
        padding: 5px 12px;
        border-radius: 5px;
        font-size: 12px;
    }

    /* Filter Form Styling */
    .filter-form {
        display: flex;
        justify-content: center;
        align-items: center;
        gap: 10px;
        margin-bottom: 20px;
        padding: 15px;
        background-color: #e9ecef;
        border-radius: 8px;
        box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        width: 90%;
        margin: auto;
    }

    .filter-form label {
        font-weight: bold;
        color: #333;
        font-size: 16px;
    }

    .filter-form select,
    .filter-form input,
    .filter-form button {
        padding: 8px;
        border-radius: 6px;
        border: 1px solid #ccc;
        font-size: 14px;
    }

    .filter-form button {
        background-color: #007bff;
        color: white;
        cursor: pointer;
        border: none;
        transition: background-color 0.3s ease;
    }

    .filter-form button:hover {
        background-color: #0056b3;
    }

    /* Pagination Styling */
    .pagination {
        display: flex;
        justify-content: center;
        align-items: center;
        margin-top: 20px;
        margin-bottom: 20px;
    }

    .pagination a {
        margin: 0 5px;
        padding: 8px 16px;
        background-color: #007bff;
        color: white;
        text-decoration: none;
        border-radius: 5px;
        transition: background-color 0.3s ease;
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

    /* Hover effects for table rows */
    tr:hover {
        background-color: #f1f1f1;
    }

    /* Reject Form and Button Styling */
    .reject-reason-div {
        display: none;
        margin-top: 10px;
        padding: 10px;
        background-color: #f8f9fa;
        border-radius: 5px;
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    }

    .reject-reason-div textarea {
        width: 100%;
        padding: 10px;
        border-radius: 6px;
        border: 1px solid #ccc;
        font-size: 14px;
        margin-top: 8px;
    }

    .reject-reason-div button {
        background: none;
        border: none;
        color: red;
        font-size: 20px;
        cursor: pointer;
        margin-top: 10px;
    }

    /* Accept and Reject Buttons Styling */
    .action-btns form {
        display: inline-block;
    }

    .action-btns button {
        padding: 5px 15px;
        font-size: 14px;
        cursor: pointer;
        border-radius: 6px;
        border: none;
    }

    .action-btns .accept-btn {
        background-color: #28a745;
        color: white;
        transition: background-color 0.3s ease;
    }

    .action-btns .accept-btn:hover {
        background-color: #218838;
    }

    .action-btns .reject-btn {
        background-color: #dc3545;
        color: white;
        transition: background-color 0.3s ease;
    }

    .action-btns .reject-btn:hover {
        background-color: #c82333;
    }
</style>
    </head>
    <body>
        <table class="table table-bordered table-striped" 
               style="border-radius: 10px; overflow: hidden; text-align: center; box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);">
            <thead style="background-color: #f7b205; color: white;">
                <tr>
                    <th style="padding: 12px;">Mentee</th>
                    <th style="padding: 12px;">Skill</th>
                    <th style="padding: 12px;">Request Time</th>
                    <th style="padding: 12px;">Deadline Time</th>
                    <th style="padding: 12px;">Status</th>
                    <th style="padding: 12px;">Reason</th>
                    <th style="padding: 12px;">Reject Reason</th>
                    <th style="padding: 12px;"></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="r" items="${requestScope.requests}">
                    <tr style="transition: background-color 0.3s ease;"
                        onmouseover="this.style.backgroundColor = '#f1f1f1'"
                        onmouseout="this.style.backgroundColor = 'white'">
                        <td style="padding: 10px;">${r.menteeName}</td>
                        <td style="padding: 10px;">${r.skillName}</td>
                        <td style="padding: 10px;">${r.requestTime}</td>
                        <td style="padding: 10px;">${r.deadlineTime}</td>
                        <td style="padding: 10px;">${r.status}</td>
                        <td style="padding: 10px;">${r.reason}</td>
                        <c:if test="${r.rejectReason != null}">
                            <td style="padding: 10px;">${r.rejectReason}</td>
                        </c:if>
<td style="padding: 10px;" class="action-btns">
    <c:if test="${r.status == 'Processing'}">
        <!-- Form Accept -->
        <form action="requestProcessing" method="post" id="acceptForm" style="display:inline-block; margin-right: 10px;">
            <input type="hidden" name="requestId" value="${r.id}"/>
            <input type="hidden" name="action" value="accept"/>
            <button type="submit" class="accept-btn">Accept</button>
        </form>

        <!-- Form Reject -->
        <form action="requestProcessing" method="post" id="rejectForm" onsubmit="return validateRejectForm()" style="display:inline-block;">
            <input type="hidden" name="requestId" value="${r.id}"/>
            <input type="hidden" name="action" value="reject"/>
            <button type="button" class="reject-btn" onclick="showRejectReason()">Reject</button>

            <!-- Textarea for reject reason (initially hidden) -->
            <div id="rejectReasonDiv" class="reject-reason-div">
                <label for="rejectReason">Reason for Rejection:</label>
                <textarea name="rejectReason" id="rejectReason" rows="4" cols="50"></textarea>
                <button type="button" onclick="closeRejectReason()">&times;</button>
            </div>

            <button type="submit" id="submitReject" style="display:none; padding: 5px 15px; font-size: 14px; cursor: pointer;">Submit Reject</button>
        </form>
    </c:if>
</td>

                            <script>
                                // Hi?n th? textarea v? submit button khi nh?n Reject
                                function showRejectReason() {
                                    document.getElementById('rejectReasonDiv').style.display = 'block';
                                    document.getElementById('submitReject').style.display = 'inline-block';
                                }

                                // ??ng textarea v? submit button khi kh?ng mu?n reject n?a
                                function closeRejectReason() {
                                    document.getElementById('rejectReasonDiv').style.display = 'none';
                                    document.getElementById('submitReject').style.display = 'none';
                                }

                                // Ki?m tra l? do khi g?i form Reject
                                function validateRejectForm() {
                                    var rejectReason = document.getElementById('rejectReason').value;
                                    if (rejectReason.trim() === "") {
                                        alert("Please provide a reason for rejection.");
                                        return false; // Ng?ng g?i form n?u l? do tr?ng
                                    }
                                    return true; // Cho ph?p g?i form n?u c? l? do
                                }
                            </script>


                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <%@include file="Footer.jsp" %>
    </body>
</html>
