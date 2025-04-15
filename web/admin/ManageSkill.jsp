<%-- 
    Document   : ManageSkill
    Created on : Feb 15, 2025, 1:32:31 PM
    Author     : dungnv
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Panel</title>
        <!-- Bootstrap & FontAwesome -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
        <style>
            .sidebar {
                min-height: 100vh; /* Make sidebar full height */
                background-color: #b3d0ff;

            }
            .content {
                flex-grow: 1; /* Allows content to take remaining space */
                padding: 20px;
            }
            .header {
                display: flex;
                align-items: center; /* Align items vertically */
                justify-content: flex-end; /* Align content to the left */
                gap: 15px; /* Space between elements */
                padding: 10px; /* Add some spacing */
                background-color: aqua;
                border-radius: 27px;
                margin: 15px 0;
            }

            .header i {
                font-size: 24px; /* Increase icon size */
                cursor: pointer;
            }

            .profile {
                width: 40px;
                height: 40px;
                border-radius: 50%;
                background-color: #ffffff;
                border: 2px solid #ddd; /* Optional: border for visibility */
            }

            .active {
                background-color: cyan;
            }

            .skills-container {
                background: #fff;
                padding: 20px;
                border-radius: 10px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            }

            .skills-table {
                width: 100%;
                border-collapse: collapse;
            }

            .skills-table th, .skills-table td {
                border: 1px solid #ddd;
                padding: 10px;
                text-align: center;
            }

            .skills-table th {
                background: #007BFF;
                color: white;
            }

            .toggle-btn {
                padding: 5px 10px;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                color: white;
                background: #28a745;
                transition: 0.3s;
            }

            .toggle-btn:hover {
                background: #218838;
            }

            .switch {
                position: relative;
                display: inline-block;
                width: 60px;
                height: 34px;
            }

            /* Hide default HTML checkbox */
            .switch input {
                opacity: 0;
                width: 0;
                height: 0;
            }

            /* The slider */
            .slider {
                position: absolute;
                cursor: pointer;
                top: 0;
                left: 0;
                right: 0;
                bottom: 0;
                background-color: #ccc;
                -webkit-transition: .4s;
                transition: .4s;
            }

            .slider:before {
                position: absolute;
                content: "";
                height: 26px;
                width: 26px;
                left: 4px;
                bottom: 4px;
                background-color: white;
                -webkit-transition: .4s;
                transition: .4s;
            }

            input:checked + .slider {
                background-color: #2196F3;
            }

            input:focus + .slider {
                box-shadow: 0 0 1px #2196F3;
            }

            input:checked + .slider:before {
                -webkit-transform: translateX(26px);
                -ms-transform: translateX(26px);
                transform: translateX(26px);
            }

            /* Rounded sliders */
            .slider.round {
                border-radius: 34px;
            }

            .slider.round:before {
                border-radius: 50%;
            }

            .header-skill {
                display: flex;
                align-items: center;
                justify-content: space-between; /* Space between button and heading */
                padding: 10px;
            }

            .header-skill h5 {
                flex-grow: 1; /* Allows heading to take available space */
                text-align: center; /* Centers text */
                margin: 0; /* Remove default margin */
            }

            .modal {
                position: fixed;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background: rgba(0, 0, 0, 0.6); /* Dark background */
                z-index: 10; /* Ensure it appears on top */
                display: none; /* Initially hidden */
            }
            /* Style cơ bản cho các link phân trang */
            .paged-list a {
                text-decoration: none;
                padding: 8px 12px;
                margin: 0 4px;
                border: 1px solid #ddd;
                border-radius: 4px;
                color: #333;
                display: inline-block;
                transition: all 0.3s ease;
            }

            /* Khi hover */
            .paged-list a:hover {
                background-color: #f5f5f5;
                border-color: #999;
            }

            /* Trang hiện tại (active) */
            .paged-list a.active {
                background-color: #4CAF50;
                color: white;
                border-color: #4CAF50;
                font-weight: bold;
            }

            /* Disable hiệu ứng hover cho active */
            .paged-list a.active:hover {
                background-color: #4CAF50;
                border-color: #4CAF50;
            }

            
        </style>
    </head>

    <body>

        <div class="d-flex">
            <!-- Sidebar -->
            <div class="sidebar text-primary col-md-2 p-3">
                <h4 class="text-center">Admin Manage</h4>
                <ul class="nav flex-column">
                    <li class="nav-item">
                        <button class="nav-link text-success" onclick="navigate('dashboard')">
                            <i class="fa-solid fa-chart-simple" style="color: #FFD43B;"></i> Dashboard
                        </button>
                    </li>
                    <li class="nav-item">
                        <button class="nav-link text-success" onclick="navigate('manageMentor?page=1')">
                            <i class="fa-solid fa-user" style="color: #FFD43B;"></i> Manage Mentor
                        </button>
                    </li>
                    <li class="nav-item">
                        <button class="nav-link text-success" onclick="navigate('manageMentee?page=1')">
                            <i class="fa-solid fa-user" style="color: #FFD43B;"></i> Manage Mentee
                        </button>
                    </li>
                    <li class="nav-item">
                        <button class="nav-link text-success" onclick="navigate('manageAccount?page=1')">
                            <i class="fa-solid fa-user" style="color: #FFD43B;"></i> Manage Account
                        </button>
                    </li>
                    <li class="nav-item active">
                        <button class="nav-link text-success" onclick="navigate('manageSkill?page=1')">
                            <i class="fa-solid fa-graduation-cap" style="color: #FFD43B;"></i> Manage Subject
                        </button>
                    </li>
                    <li class="nav-item">
                        <a href="${pageContext.request.contextPath}/logout" class="nav-link text-success">
                            <i class="fa-solid fa-right-from-bracket" style="color: #FFD43B;"></i> Logout
                        </a>
                    </li>
                </ul>
            </div>

            <div id="addSkillModal" class="modal" tabindex="-1" style="display: none;">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">Add New Skill</h5>
                            <button type="button" class="btn-close" onclick="closeAddSkillPopup()"></button>
                        </div>
                        <div class="modal-body">
                            <form method="POST" action="../admin/addSkill">
                                <label for="skillName">Skill Name:</label>
                                <input type="text" id="skillName" name="skillName" class="form-control" required>
                                <label for="skillDesc">Description:</label>
                                <textarea id="skillDesc" class="form-control" name="des" required></textarea>

                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" onclick="closeAddSkillPopup()">Cancel</button>
                                    <button type="submit" class="btn btn-primary">Confirm</button>
                                </div>
                            </form>
                        </div>

                    </div>
                </div>
            </div>

            <div class="container-fluid">
                <div class="header">
                    <i class="fa-solid fa-bell"></i>
                    <i class="fa-solid fa-message"></i>
                    <div class="profile"></div>
                </div>
                <div class="skills-container">
                    <div class="d-flex header-skill">
                        <button type="button" class="btn btn-primary btn-rounded" onclick="showAddSkillPopup()">
                            Add Subject
                        </button>
                        <h5>Manage Skill</h5>
                    </div>

                    <div style="height: 300px">
                        <table class="skills-table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Name</th>
                                <th>Description</th>
                                <th>Enable</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${requestScope.skills}" var="s">
                                <tr>
                                    <td>${s.id}</td>
                                    <td>${s.name}</td>
                                    <td>${s.description}</td>
                                    <td>
                                        <label class="switch">
                                            <input type="checkbox" ${s.enable ? 'checked' : ''} 
                                                   onchange="toggleSkill(${s.id}, this)">
                                            <span class="slider round"></span>
                                        </label>
                                    </td>

                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    </div>
                    <div class="paged-list">
                        <c:forEach var="i" begin="1" end="${end_page}">
                            <a href="manageSkill?page=${i}" class="${i == current_page ? 'active' : ''}">
                                ${i}
                            </a>
                        </c:forEach>
                    </div>
                </div>
            </div>



        </div> <!-- End Flexbox -->

        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    </body>

    <script>
                                                       function navigate(url) {
                                                           window.location.href = url;
                                                       }

                                                       function toggleSkill(skillId, element) {
                                                           let enable = element.checked; // Get the updated state from the checkbox

                                                           fetch('../admin/manageSkill', {// Ensure this matches your servlet mapping
                                                               method: 'POST',
                                                               headers: {
                                                                   "Content-Type": "application/json"
                                                               },
                                                               body: JSON.stringify({
                                                                   id: skillId,
                                                                   enable: enable,
                                                                   action: "update"
                                                               })
                                                           })
                                                                   .then(response => response.json())
                                                                   .then(data => {
                                                                       if (data.success) {
                                                                           console.log(`Skill ID ${skillId} status updated to ${enable}.`);
                                                                       } else {
                                                                           console.error('Failed to update skill status.');
                                                                       }
                                                                   })
                                                                   .catch(error => console.error('Error:', error));
                                                       }
                                                       function showAddSkillPopup() {
                                                           document.getElementById('addSkillModal').style.display = 'block';
                                                       }

                                                       function closeAddSkillPopup() {
                                                           document.getElementById('addSkillModal').style.display = 'none';
                                                       }

                                                       function addSkill() {
                                                           const name = document.getElementById('skillName').value;
                                                           const description = document.getElementById('skillDesc').value;

                                                           fetch('../admin/manageSkill', {
                                                               method: 'POST',
                                                               headers: {
                                                                   'Content-Type': 'application/json'
                                                               },
                                                               body: JSON.stringify({
                                                                   name: name,
                                                                   description: description,
                                                                   action: 'create'
                                                               })
                                                           })
                                                                   .then(response => response.json())
                                                                   .then(data => {
                                                                       // Assuming your popup has an ID like 'popup' and you want to close it
                                                                       document.getElementById('popup').style.display = 'none';

                                                                       // Reload the page
                                                                       window.location.reload();
                                                                   })
                                                                   .catch(error => console.error('Error:', error));
                                                       }

    </script>
</html>