<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Request Management - Online Rent Tutor</title>
    <!-- Meta Tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Favicon -->
    <link rel="shortcut icon" href="../assets/images/favicon.ico">
    <!-- Google Font -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Heebo:wght@400;500;700&family=Roboto:wght@400;500;700&display=swap">
    <!-- Plugins CSS -->
    <link rel="stylesheet" type="text/css" href="../assets/vendor/font-awesome/css/all.min.css">
    <link rel="stylesheet" type="text/css" href="../assets/vendor/bootstrap-icons/bootstrap-icons.css">
    <link rel="stylesheet" type="text/css" href="../assets/vendor/tiny-slider/tiny-slider.css">
    <link rel="stylesheet" type="text/css" href="../assets/vendor/glightbox/css/glightbox.css">
    <!-- Theme CSS -->
    <link rel="stylesheet" type="text/css" href="../assets/css/style.css">
</head>
<body>
            <style>
            /* General Container Styling for Sidebar */
            #mentee-sidebar {
                background-color: #000; /* Black background */
                padding: 10px 20px;
                box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1); /* Light shadow for the bar */
                width: 100%; /* Full width */
                display: flex;
                justify-content: center; /* Center the content horizontally */
                border: 2px solid #000; /* Add black border around the entire sidebar */
                border-radius: 8px; /* Optional: add rounded corners */
            }

            /* List Styling for Sidebar */
            #mentee-sidebar ul {
                list-style-type: none;
                padding-left: 0;
                display: flex;
                align-items: center;
                justify-content: center; /* Center the list items horizontally */
                width: 100%;
            }

            /* List Item Styling for Sidebar */
            #mentee-sidebar li {
                margin-right: 30px; /* Space between items */
            }

            /* Link Styling for Sidebar */
            #mentee-sidebar a {
                color: #fff; /* White color for text */
                text-decoration: none;
                font-size: 16px;
                background-color: #000; /* Black background */
                padding: 8px 15px; /* Add padding around the text */
                border-radius: 4px; /* Rounded corners for the background */
                transition: color 0.3s ease, background-color 0.3s ease, border-bottom 0.3s ease;
            }

            /* Hover Effect for Sidebar */
            #mentee-sidebar a:hover {
                background-color: #333; /* Darker black when hovered */
                color: #fff; /* Keep the white text color */
                border-bottom: 2px solid #fff; /* Add white underline when hovering */
            }

            /* Active Link Styling for Sidebar */
            #mentee-sidebar a.active {
                background-color: #444; /* Darker background when active */
                color: #fff; /* White text color for active link */
                border-bottom: 2px solid #fff; /* Underline active link */
            }

            /* Currency styling for Sidebar */
            #mentee-sidebar li:last-child {
                font-weight: bold; /* Make the last item (currency) bold */
                color: #333; /* Dark color for the currency */
            }

            /* Icon Styling for Sidebar */
            #mentee-sidebar a i {
                margin-right: 5px; /* Space between the icon and text */
            }

            /* Responsive Adjustment for Sidebar */
            @media (max-width: 768px) {
                #mentee-sidebar ul {
                    flex-direction: column;
                    align-items: center; /* Center vertically when items stack */
                }

                #mentee-sidebar li {
                    margin-right: 0;
                    margin-bottom: 10px;
                }
            }
        </style>

        <!-- Sidebar START -->
        <div id="mentee-sidebar">
            <ul>
                <li><a href="Dashboard" class="active"><i class="bi bi-house-door"></i>Manager Dashboard</a></li>
                <li><a href="RequestManagement"><i class="bi bi-card-list"></i>Request Management</a></li>
                <li><a href="TransactionManagement"><i class="bi bi-cash-stack"></i>Transaction Management</a></li>
                <li><a href="${pageContext.request.contextPath}/mentorRequests"><i class="bi bi-cash-stack"></i>Manager form</a></li>
                <li><a href="${pageContext.request.contextPath}/logout"><i class="bi bi-box-arrow-right"></i>Logout</a></li>
            </ul>
        </div>
        <!-- Sidebar END -->

    <!-- Page content START -->
   <main class="page-content">
        <div class="container py-3">
            <h1 class="h3 mb-4">Request Management</h1>
            
            <!-- Search, filter and sort controls -->
            <div class="row mb-3 g-3">
                <div class="col-sm-4">
                    <div class="input-group">
                        <input type="text" id="searchRequest" class="form-control" placeholder="Search request...">
                        <button class="btn btn-primary" type="button" onclick="searchRequests()"><i class="bi bi-search"></i></button>
                    </div>
                </div>

            <!-- Request table -->
            <div class="card">
                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table table-hover">
                            <thead>
                                <tr>
                                    <th>#</th>
                                    <th>Mentee</th>
                                    <th>Skill</th>
                                    <th>Mentor</th>
                                    <th>Status</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody id="requestTable">
                                <c:set var="pageStart" value="${(currentPage == null || currentPage < 1) ? 0 : (currentPage-1)*10}"/>
                                <c:set var="pageEnd" value="${pageStart + 9}"/>
                                <c:forEach items="${requestList}" var="request" varStatus="status" begin="${pageStart}" end="${pageEnd}">
                                    <tr>
                                        <td>${status.index + 1}</td>
                                        <td>${request.send}</td>
                                        <td>${request.skillsName}</td>
                                        <td>${request.mentor != null ? request.mentor : 'Not Assigned'}</td>
                                        <td>
                                            <span class="badge 
                                                ${request.status == 'Pending' ? 'bg-warning' :
                                                  request.status == 'Approved' ? 'bg-success' : 
                                                  'bg-danger'}">
                                                ${request.status}
                                            </span>
                                        </td>
                                        <td>
                                            <a href="RequestDetail?requestId=${request.id}" class="btn btn-sm btn-primary mb-1"><i class="bi bi-eye"></i> View Details</a>
                                            
                                            <c:if test="${request.status == 'Pending'}">
                                                <form method="post" action="RequestManagement" class="d-inline-block">
                                                    <input type="hidden" name="requestId" value="${request.id}">
                                                    <input type="hidden" name="action" value="approve">
                                                    <button type="submit" class="btn btn-sm btn-success">✔ Approve</button>
                                                </form>
                                                <form method="post" action="RequestManagement" class="d-inline-block">
                                                    <input type="hidden" name="requestId" value="${request.id}">
                                                    <input type="hidden" name="action" value="reject">
                                                    <button type="submit" class="btn btn-sm btn-danger">❌ Reject</button>
                                                </form>
                                                
                                                <!-- Reject Modal -->
                                                <div class="modal fade" id="rejectModal${request.id}" tabindex="-1" aria-labelledby="rejectModalLabel${request.id}" aria-hidden="true">
                                                    <div class="modal-dialog">
                                                        <div class="modal-content">
                                                            <div class="modal-header">
                                                                <h5 class="modal-title" id="rejectModalLabel${request.id}">Reject Request</h5>
                                                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                            </div>
                                                            <form method="post" action="RequestManagement">
                                                                <div class="modal-body">
                                                                    <input type="hidden" name="requestId" value="${request.id}">
                                                                    <input type="hidden" name="action" value="reject">
                                                                    <div class="mb-3">
                                                                        <label for="rejectReason${request.id}" class="form-label">Rejection Reason</label>
                                                                        <textarea class="form-control" id="rejectReason${request.id}" name="rejectReason" rows="3" required></textarea>
                                                                    </div>
                                                                </div>
                                                                <div class="modal-footer">
                                                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                                                                    <button type="submit" class="btn btn-danger">Confirm Rejection</button>
                                                                </div>
                                                            </form>
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:if>
                                            <c:if test="${request.status == 'Approved'}">
                                                <form method="post" action="RequestManagement" class="mt-1">
                                                    <input type="hidden" name="requestId" value="${request.id}">
                                                    <input type="hidden" name="action" value="assignSlot">
                                                    <select name="slotId" class="form-select d-inline-block w-auto">
                                                        <option value="1">Slot 1</option>
                                                        <option value="2">Slot 2</option>
                                                        <option value="3">Slot 3</option>
                                                    </select>
                                                    <button type="submit" class="btn btn-sm btn-info">📅 Assign Slot</button>
                                                </form>
                                            </c:if>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <nav>
                        <ul class="pagination justify-content-center" id="pagination"></ul>
                    </nav>
                </div>
            </div>
            <!-- Pagination -->
            <nav aria-label="Page navigation" class="mt-4">
                <ul class="pagination justify-content-center">
                    <c:if test="${totalPages > 1}">
                        <li class="page-item ${currentPage <= 1 ? 'disabled' : ''}">
                            <a class="page-link" href="RequestManagement?page=${currentPage - 1}${not empty param.status ? '&status='.concat(param.status) : ''}${not empty param.sort ? '&sort='.concat(param.sort) : ''}${not empty param.order ? '&order='.concat(param.order) : ''}" aria-label="Previous">
                                <span aria-hidden="true">&laquo;</span>
                            </a>
                        </li>
                        
                        <c:choose>
                            <c:when test="${totalPages <= 5}">
                                <c:forEach begin="1" end="${totalPages}" var="i">
                                    <li class="page-item ${currentPage == i ? 'active' : ''}"><a class="page-link" href="RequestManagement?page=${i}${not empty param.status ? '&status='.concat(param.status) : ''}${not empty param.sort ? '&sort='.concat(param.sort) : ''}${not empty param.order ? '&order='.concat(param.order) : ''}">${i}</a></li>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <c:set var="startPage" value="${Math.max(1, currentPage - 2)}"/>
                                <c:set var="endPage" value="${Math.min(totalPages, startPage + 4)}"/>
                                <c:if test="${startPage > 1}">
                                    <li class="page-item"><a class="page-link" href="RequestManagement?page=1${not empty param.status ? '&status='.concat(param.status) : ''}${not empty param.sort ? '&sort='.concat(param.sort) : ''}${not empty param.order ? '&order='.concat(param.order) : ''}">1</a></li>
                                    <c:if test="${startPage > 2}">
                                        <li class="page-item disabled"><span class="page-link">...</span></li>
                                    </c:if>
                                </c:if>
                                
                                <c:forEach begin="${startPage}" end="${endPage}" var="i">
                                    <li class="page-item ${currentPage == i ? 'active' : ''}"><a class="page-link" href="RequestManagement?page=${i}${not empty param.status ? '&status='.concat(param.status) : ''}${not empty param.sort ? '&sort='.concat(param.sort) : ''}${not empty param.order ? '&order='.concat(param.order) : ''}">${i}</a></li>
                                </c:forEach>
                                
                                <c:if test="${endPage < totalPages}">
                                    <c:if test="${endPage < totalPages - 1}">
                                        <li class="page-item disabled"><span class="page-link">...</span></li>
                                    </c:if>
                                    <li class="page-item"><a class="page-link" href="RequestManagement?page=${totalPages}${not empty param.status ? '&status='.concat(param.status) : ''}${not empty param.sort ? '&sort='.concat(param.sort) : ''}${not empty param.order ? '&order='.concat(param.order) : ''}">${totalPages}</a></li>
                                </c:if>
                            </c:otherwise>
                        </c:choose>
                        
                        <li class="page-item ${currentPage >= totalPages ? 'disabled' : ''}">
                            <a class="page-link" href="RequestManagement?page=${currentPage + 1}${not empty param.status ? '&status='.concat(param.status) : ''}${not empty param.sort ? '&sort='.concat(param.sort) : ''}${not empty param.order ? '&order='.concat(param.order) : ''}" aria-label="Next">
                                <span aria-hidden="true">&raquo;</span>
                            </a>
                        </li>
                    </c:if>
                </ul>
            </nav>
        </div>
    </main>

    <script>
        function searchRequests() {
            const searchValue = document.getElementById('searchRequest').value.toLowerCase();
            const rows = document.getElementById('requestTable').getElementsByTagName('tr');
            
            for (let row of rows) {
                const text = row.textContent.toLowerCase();
                row.style.display = text.includes(searchValue) ? '' : 'none';
            }
        }

        function filterRequests() {
            const status = document.getElementById('filterStatus').value;
            window.location.href = 'RequestManagement?status=' + status + '&page=1';
        }

        function sortRequests() {
            const sortBy = document.getElementById('sortBy').value;
            const sortOrder = document.getElementById('sortOrder').value;
            window.location.href = 'RequestManagement?sort=' + sortBy + '&order=' + sortOrder + '&page=1';
        }

        // Instant search
        document.getElementById('searchRequest').addEventListener('keyup', searchRequests);
    </script>
</body>
</html>