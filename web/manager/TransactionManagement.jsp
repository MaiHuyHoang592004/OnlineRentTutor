<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%-- Role-based access control --%>
<c:if test="${empty sessionScope.user || sessionScope.user.getRoleID() != 2}">
    <c:redirect url="/login.jsp">
        <c:param name="message" value="You need to login as a manager to access this page"/>
    </c:redirect>
</c:if>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Transaction Management - Online Rent Tutor</title>
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

    <!-- Page content START -->
    <main class="page-content">
        <div class="container py-3">
            <h1 class="h3 mb-4">Transaction Management</h1>
            
            <!-- Search and filter controls -->
            <div class="row mb-4">
                <div class="col-md-4">
                    <form action="TransactionManagement" method="get" class="d-flex">
                        <input type="text" name="search" class="form-control" placeholder="Search by ID or content" value="${search}">                        
                        <button type="submit" class="btn btn-primary ms-2"><i class="bi bi-search"></i></button>
                    </form>
                </div>
                <div class="col-md-3">
                    <form action="TransactionManagement" method="get" id="statusForm">
                        <input type="hidden" name="search" value="${search}">                        
                        <input type="hidden" name="sort" value="${sort}">                        
                        <select name="status" class="form-select" onchange="document.getElementById('statusForm').submit()">
                            <option value="" ${empty status ? 'selected' : ''}>All Status</option>
                            <option value="Success" ${status eq 'Success' ? 'selected' : ''}>Completed</option>
                            <option value="Pending" ${status eq 'Pending' ? 'selected' : ''}>Pending</option>
                            <option value="Failed" ${status eq 'Failed' ? 'selected' : ''}>Failed</option>
                        </select>
                    </form>
                </div>
                <div class="col-md-3">
                    <form action="TransactionManagement" method="get" id="sortForm">
                        <input type="hidden" name="search" value="${search}">                        
                        <input type="hidden" name="status" value="${status}">                        
                        <select name="sort" class="form-select" onchange="document.getElementById('sortForm').submit()">
                            <option value="" ${empty sort ? 'selected' : ''}>Sort By Time (Default)</option>
                            <option value="balance" ${sort eq 'balance' ? 'selected' : ''}>Sort By Amount</option>
                            <option value="status" ${sort eq 'status' ? 'selected' : ''}>Sort By Status</option>
                        </select>
                    </form>
                </div>
            </div>
            
            <!-- Transaction table -->
<div class="card">
    <div class="card-body">
        <div class="table-responsive border-0">
            <table class="table table-hover align-middle p-4 mb-0 table-shrink">
                <thead class="table-light">
                    <tr>
                        <th scope="col" class="border-0 rounded-start">#ID</th>
                        <th scope="col" class="border-0">Date</th>
                        <th scope="col" class="border-0">User</th>
                        <th scope="col" class="border-0">Amount</th>
                        <th scope="col" class="border-0">Type</th>
                        <th scope="col" class="border-0">Content</th>
                        <th scope="col" class="border-0">Status</th>
                        <th scope="col" class="border-0 rounded-end">Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:if test="${empty transactions}">
                        <tr>
                            <td colspan="8" class="text-center">No transactions found</td>
                        </tr>
                    </c:if>
                    <c:forEach var="transaction" items="${transactions}">
                        <tr>
                            <td>${transaction.id}</td>
                            <td>${transaction.time}</td>
                            <td>
                                <div class="d-flex align-items-center position-relative">
                                    <div class="ms-1">
                                        <h6 class="mb-0">
                                            <a href="#" class="stretched-link">${transaction.userName} (ID: ${transaction.userID})</a>
                                        </h6>
                                    </div>
                                </div>
                            </td>
                            <td>${transaction.balance} vnd</td>
                            <td>${transaction.type}</td>
                            <td>${transaction.content}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${transaction.status eq 'Success'}">
                                        <span class="badge bg-success">Completed</span>
                                    </c:when>
                                    <c:when test="${transaction.status eq 'Pending'}">
                                        <span class="badge bg-warning">Pending</span>
                                    </c:when>
                                    <c:when test="${transaction.status eq 'Failed'}">
                                        <span class="badge bg-danger">Failed</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge bg-secondary">${transaction.status}</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <div class="d-flex gap-2">
                                    <a href="TransactionDetail?id=${transaction.id}" class="btn btn-sm btn-primary-soft" data-bs-toggle="tooltip" title="View">
                                        <i class="bi bi-eye"></i>
                                    </a>
                                    <c:if test="${transaction.status eq 'Success'}">
                                        <a href="#" class="btn btn-sm btn-secondary-soft" data-bs-toggle="tooltip" title="Invoice">
                                            <i class="bi bi-file-earmark-text"></i>
                                        </a>
                                    </c:if>
                                </div>
                                <c:if test="${transaction.status eq 'Pending' && transaction.content eq 'Rút tiền'}">
                                    <div class="d-flex gap-2 mt-2">
                                        <form action="TransactionDetail" method="post" class="d-inline">
                                            <input type="hidden" name="action" value="accept">
                                            <input type="hidden" name="id" value="${transaction.id}">
                                            <button type="submit" class="btn btn-sm btn-success" title="Accept Withdrawal" onclick="return confirm('Bạn có chắc chắn muốn chấp nhận giao dịch này?');">
                                                <i class="bi bi-check-circle"></i>
                                            </button>
                                        </form>
                                        <form action="TransactionDetail" method="post" class="d-inline">
                                            <input type="hidden" name="action" value="reject">
                                            <input type="hidden" name="id" value="${transaction.id}">
                                            <button type="submit" class="btn btn-sm btn-danger" title="Reject Withdrawal" onclick="return confirm('Bạn có chắc chắn muốn từ chối giao dịch này?');">
                                                <i class="bi bi-x-circle"></i>
                                            </button>
                                        </form>
                                    </div>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
                    <!-- Pagination -->
                    <c:if test="${totalPages > 0}">
                        <div class="d-flex justify-content-between align-items-center mt-4">
                            <p class="mb-0">Showing ${(currentPage-1)*10 + 1} to ${currentPage*10 > totalRecords ? totalRecords : currentPage*10} of ${totalRecords} entries</p>
                            <nav aria-label="Page navigation">
                                <ul class="pagination justify-content-center mb-0">
                                    <!-- Previous button -->
                                    <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                                        <a class="page-link" href="TransactionManagement?page=${currentPage - 1}&search=${search}&status=${status}&sort=${sort}" aria-label="Previous">
                                            <span aria-hidden="true">&laquo;</span>
                                        </a>
                                    </li>
                                    
                                    <!-- Page numbers -->
                                    <c:forEach begin="1" end="${totalPages}" var="i">
                                        <li class="page-item ${currentPage == i ? 'active' : ''}">
                                            <a class="page-link" href="TransactionManagement?page=${i}&search=${search}&status=${status}&sort=${sort}">${i}</a>
                                        </li>
                                    </c:forEach>
                                    
                                    <!-- Next button -->
                                    <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                                        <a class="page-link" href="TransactionManagement?page=${currentPage + 1}&search=${search}&status=${status}&sort=${sort}" aria-label="Next">
                                            <span aria-hidden="true">&raquo;</span>
                                        </a>
                                    </li>
                                </ul>
                            </nav>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </main>