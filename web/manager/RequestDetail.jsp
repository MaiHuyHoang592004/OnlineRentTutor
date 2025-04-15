<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Request Details - Online Rent Tutor</title>
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
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h1 class="h3">Request Details</h1>
                <a href="RequestManagement" class="btn btn-outline-primary"><i class="bi bi-arrow-left"></i> Back to List</a>
            </div>
            
            <div class="card">
                <div class="card-body">
                    <div class="row mb-4">
                        <div class="col-md-6">
                            <h5 class="card-title">Request Information</h5>
                            <table class="table table-borderless">
                                <tr>
                                    <th width="30%">Request ID:</th>
                                    <td>${request.id}</td>
                                </tr>
                                <tr>
                                    <th>Subject:</th>
                                    <td>${request.subject}</td>
                                </tr>
                                <tr>
                                    <th>Status:</th>
                                    <td>
                                        <span class="badge ${request.status == 'Pending' ? 'bg-warning' : request.status == 'Approved' ? 'bg-success' : 'bg-danger'}">
                                            ${request.status}
                                        </span>
                                    </td>
                                </tr>
                                <tr>
                                    <th>Request Time:</th>
                                    <td>${request.requestTime}</td>
                                </tr>
                                <tr>
                                    <th>Deadline:</th>
                                    <td>${request.deadlineTime}</td>
                                </tr>
                            </table>
                        </div>
                        <div class="col-md-6">
                            <h5 class="card-title">People Information</h5>
                            <table class="table table-borderless">
                                <tr>
                                    <th width="30%">Mentee:</th>
                                    <td>${request.send}</td>
                                </tr>
                                <tr>
                                    <th>Mentor:</th>
                                    <td>${request.mentor != null ? request.mentor : 'Not Assigned'}</td>
                                </tr>
                                <tr>
                                    <th>Skills:</th>
                                    <td>${request.skillsName}</td>
                                </tr>
                            </table>
                        </div>
                    </div>
                    
                    <div class="mb-4">
                        <h5 class="card-title">Request Reason</h5>
                        <div class="p-3 bg-light rounded">
                            ${request.reason}
                        </div>
                    </div>
                    
                    <c:if test="${request.status == 'Rejected' && request.rejectReason != null}">
                        <div class="mb-4">
                            <h5 class="card-title">Rejection Reason</h5>
                            <div class="p-3 bg-light rounded">
                                ${request.rejectReason}
                            </div>
                        </div>
                    </c:if>
                    
                    <c:if test="${request.status == 'Pending'}">
                        <div class="d-flex gap-2 mt-4">
                            <form method="post" action="RequestDetail">
                                <input type="hidden" name="requestId" value="${request.id}">
                                <input type="hidden" name="action" value="approve">
                                <button type="submit" class="btn btn-success">✔ Approve Request</button>
                            </form>
                            
                            <button type="button" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#rejectModal">
                                ❌ Reject Request
                            </button>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
        
        <!-- Reject Modal -->
        <div class="modal fade" id="rejectModal" tabindex="-1" aria-labelledby="rejectModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <form method="post" action="RequestDetail">
                        <div class="modal-header">
                            <h5 class="modal-title" id="rejectModalLabel">Reject Request</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <input type="hidden" name="requestId" value="${request.id}">
                            <input type="hidden" name="action" value="reject">
                            <div class="mb-3">
                                <label for="rejectReason" class="form-label">Rejection Reason</label>
                                <textarea class="form-control" id="rejectReason" name="rejectReason" rows="4" required></textarea>
                                <div class="form-text">Please provide a reason for rejecting this request.</div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                            <button type="submit" class="btn btn-danger">Reject Request</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </main>

    <!-- Bootstrap JS -->
    <script src="../assets/vendor/bootstrap/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>