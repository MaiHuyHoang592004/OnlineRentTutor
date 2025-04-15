<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.time.LocalDate, java.time.temporal.WeekFields, java.util.Locale" %>
<%@page import="model.User" %>
<%@page import="model.Mentor" %>

<%
       User u = (User) session.getAttribute("u");
       if (u == null) {
           response.sendRedirect("Login.jsp");
           return;
       }
%>
<html>
    <head>
        <title>Mentor Schedule</title>
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

        </style>
    </head>
    <% if(u.getRole().equalsIgnoreCase("Mentor")) { %>
<a href="CustomerHome.jsp"> <button  class="btn btn-success mb-3">
        Back
    </button></a>
    <% } else { %>
    <button onclick="window.history.back();" class="btn btn-success mb-3">
        Back
    </button>
    <% } %>
    <body class="container mt-5">
        <h2 class="text-center mb-4">Weekly Schedule - Week ${week} / ${year}</h2>

        <form method="get" action="schedule" class="row g-2 mb-3 align-items-center">
            <input type="hidden" name="mentorID" value="${mentorID}" />
            <div class="col-auto">
                <label class="form-label fw-bold text-danger">YEAR</label>
                <select name="year" class="form-select">
                    <c:forEach var="y" begin="2023" end="2026">
                        <option value="${y}" ${y == year ? 'selected' : ''}>${y}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-auto">
                <label class="form-label fw-bold text-primary">WEEK</label>
                <select name="week" class="form-select">
                    <c:forEach var="w" begin="1" end="52">
                        <option value="${w}" ${w == week ? 'selected' : ''}>Week ${w}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-auto">
                <button class="btn btn-primary">View</button>
            </div>
        </form>
        <%if(u.getRole().equalsIgnoreCase("Mentor")) {%>
        <a href="addSlot?mentorID=${mentorID}&year=${year}&week=${week}" class="btn btn-success mb-3">
            + Add New Slot
        </a>
        <%}else {%>
        <a></a>
        <%}%>


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
                                        <div class="${s.status.trim() == 'Done' ? 'done' : 'notyet'} p-1">
                                            <strong>${s.skillName}</strong><br/>
                                            <c:if test="${not empty s.link}">
                                                <small><a href="${s.link}" target="_blank" class="meet-link">Google Meet</a></small><br/>
                                            </c:if>
                                            ${s.status}<br/>
                                            <small>${s.rejectReason}</small><br/>
                                            <a href="#" class="text-warning me-2" data-bs-toggle="modal" data-bs-target="#editSlotModal"
                                               data-slotid="${s.slotID}" data-status="${s.status}" data-reason="${s.rejectReason}"
                                               data-date="${s.date}" data-slot="${s.slot}" data-skill="${s.skillName}">
                                                ✏️
                                            </a>
                                            <a href="#" class="text-danger" data-bs-toggle="modal" data-bs-target="#confirmDeleteModal"
                                               data-slotid="${s.slotID}">🗑</a>
                                        </div>
                                    </c:if>
                                </c:forEach>
                            </td>
                        </c:forEach>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <!-- Edit Modal -->
        <%if(u.getRole().equalsIgnoreCase("Mentor")) {%>
        <div class="modal fade" id="editSlotModal" tabindex="-1" aria-labelledby="editSlotModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <form action="updateSlot" method="post" class="modal-content">

                    <div class="modal-header">
                        <h5 class="modal-title" id="editSlotModalLabel">Edit Slot</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                    </div>

                    <div class="modal-body">
                        <input type="hidden" name="mentorID" value="${mentorID}" />
                        <input type="hidden" name="slotID" id="editSlotID" />
                        <div class="mb-3">
                            <label>Slot:</label>
                            <input type="text" id="editSlotNumber" class="form-control" readonly />
                        </div>
                        <div class="mb-3">
                            <label>Date:</label>
                            <input type="text" id="editDate" class="form-control" readonly />
                        </div>
                        <div class="mb-3">
                            <label>Skill:</label>
                            <input type="text" id="editSkill" class="form-control" readonly />
                        </div>   
                        <div class="mb-3">
                            <label>Status:</label>
                            <select name="status" id="editStatus" class="form-control">
                                <option value="Not Yet">Not Yet</option>
                                <option value="Done">Done</option>
                            </select>
                        </div>
                        <div class="mb-3">
                            <label>Reject Reason:</label>
                            <input type="text" name="rejectReason" id="editReason" class="form-control" />
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-warning">Save</button>
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                    </div>
                </form>
            </div>
        </div>
        <%}else {%>
        <a></a>
        <%}%>


        <!-- Delete Modal -->
        <%if(u.getRole().equalsIgnoreCase("Mentor")) {%>
        <div class="modal fade" id="confirmDeleteModal" tabindex="-1" aria-labelledby="confirmDeleteModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <form action="deleteSlot" method="get" class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="confirmDeleteModalLabel">Confirm Deletion</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                    </div>
                    <div class="modal-body">
                        <input type="hidden" name="slotID" id="deleteSlotID" />
                        <input type="hidden" name="mentorID" value="${mentorID}" />
                        <p>Are you sure you want to delete this slot?</p>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-danger">Delete</button>
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                    </div>
                </form>
            </div>
        </div>
        <%}else {%>
        <a></a>
        <%}%>


        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script>
        const editModal = document.getElementById('editSlotModal');
        editModal.addEventListener('show.bs.modal', function (event) {
            const button = event.relatedTarget;

            const slotId = button.getAttribute('data-slotid');
            const status = button.getAttribute('data-status');
            const reason = button.getAttribute('data-reason');
            const skill = button.getAttribute('data-skill');
            const date = button.getAttribute('data-date');
            const slotNumber = button.getAttribute('data-slot');

            document.getElementById('editSlotID').value = slotId;
            document.getElementById('editStatus').value = status;
            document.getElementById('editReason').value = reason;
            document.getElementById('editSkill').value = skill;
            document.getElementById('editDate').value = date;
            document.getElementById('editSlotNumber').value = slotNumber;

            // Optional: reset option selection to match status
            const select = document.getElementById('editStatus');
            for (let i = 0; i < select.options.length; i++) {
                select.options[i].selected = select.options[i].value === status;
            }
        });




        const deleteModal = document.getElementById('confirmDeleteModal');
        deleteModal.addEventListener('show.bs.modal', function (event) {
            const button = event.relatedTarget;
            document.getElementById('deleteSlotID').value = button.getAttribute('data-slotid');
        });
        </script>

    </body>
</html>
