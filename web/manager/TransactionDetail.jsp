<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Transaction Detail - Online Rent Tutor</title>
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
        <li><a href="RequestManagement.jsp"><i class="bi bi-card-list"></i>Request Management</a></li>
        <li><a href="TransactionManagement"><i class="bi bi-cash-stack"></i>Transaction Management</a></li>
        <li><a href="${pageContext.request.contextPath}/mentorRequests"><i class="bi bi-cash-stack"></i>Manager form</a></li>
        <li><a href="${pageContext.request.contextPath}/logout"><i class="bi bi-box-arrow-right"></i>Logout</a></li>
    </ul>
</div>

    <!-- Page content START -->
    <main class="page-content">
        <div class="container py-3">
            <div class="row mb-4">
                <div class="col-12">
                    <div class="d-sm-flex justify-content-between align-items-center">
                        <h1 class="h3 mb-2 mb-sm-0">Transaction Detail</h1>
                        <a href="TransactionManagement" class="btn btn-sm btn-primary mb-0"><i class="bi bi-arrow-left"></i> Back to Transaction List</a>
                    </div>
                </div>
            </div>

            <!-- Transaction Details Card -->
            <div class="card bg-transparent border rounded-3 mb-4">
                <div class="card-header bg-light border-bottom">
                    <h5 class="card-header-title">Transaction Information</h5>
                </div>
                <div class="card-body">
                    <div class="row g-4">
                        <!-- Transaction ID -->
                        <div class="col-md-6">
                            <div class="bg-light p-4 rounded-3 position-relative overflow-hidden h-100">
                                <div class="d-flex justify-content-between align-items-center">
                                    <div>
                                        <h6 class="mb-0">Transaction ID</h6>
                                        <h4 class="mb-0 mt-2">#${transaction.id}</h4>
                                    </div>
                                    <i class="bi bi-hash fs-1 text-success opacity-25"></i>
                                </div>
                            </div>
                        </div>

                        <!-- Transaction Date -->
                        <div class="col-md-6">
                            <div class="bg-light p-4 rounded-3 position-relative overflow-hidden h-100">
                                <div class="d-flex justify-content-between align-items-center">
                                    <div>
                                        <h6 class="mb-0">Transaction Date</h6>
                                        <h4 class="mb-0 mt-2">${transaction.time}</h4>
                                    </div>
                                    <i class="bi bi-calendar-date fs-1 text-success opacity-25"></i>
                                </div>
                            </div>
                        </div>

                        <!-- Transaction Amount -->
                        <div class="col-md-6">
                            <div class="bg-light p-4 rounded-3 position-relative overflow-hidden h-100">
                                <div class="d-flex justify-content-between align-items-center">
                                    <div>
                                        <h6 class="mb-0">Amount</h6>
                                        <h4 class="mb-0 mt-2">${transaction.balance} VND</h4>
                                    </div>
                                    <i class="bi bi-cash fs-1 text-success opacity-25"></i>
                                </div>
                            </div>
                        </div>

                        <!-- Transaction Type -->
                        <div class="col-md-6">
                            <div class="bg-light p-4 rounded-3 position-relative overflow-hidden h-100">
                                <div class="d-flex justify-content-between align-items-center">
                                    <div>
                                        <h6 class="mb-0">Type</h6>
                                        <h4 class="mb-0 mt-2">${transaction.type}</h4>
                                    </div>
                                    <i class="bi bi-arrow-left-right fs-1 text-success opacity-25"></i>
                                </div>
                            </div>
                        </div>

                        <!-- Transaction Fee -->
                        <div class="col-md-6">
                            <div class="bg-light p-4 rounded-3 position-relative overflow-hidden h-100">
                                <div class="d-flex justify-content-between align-items-center">
                                    <div>
                                        <h6 class="mb-0">Fee</h6>
                                        <h4 class="mb-0 mt-2">${transaction.fee}</h4>
                                    </div>
                                    <i class="bi bi-percent fs-1 text-success opacity-25"></i>
                                </div>
                            </div>
                        </div>

                        <!-- Transaction Status -->
                        <div class="col-md-6">
                            <div class="bg-light p-4 rounded-3 position-relative overflow-hidden h-100">
                                <div class="d-flex justify-content-between align-items-center">
                                    <div>
                                        <h6 class="mb-0">Status</h6>
                                        <h4 class="mb-0 mt-2">
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
                                        </h4>
                                    </div>
                                    <i class="bi bi-check-circle fs-1 text-success opacity-25"></i>
                                </div>
                            </div>
                        </div>

                        <!-- Transaction Content -->
                        <div class="col-12">
                            <div class="bg-light p-4 rounded-3">
                                <h6 class="mb-3">Transaction Content</h6>
                                <p class="mb-0">${transaction.content}</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- User Information Card -->
            <div class="card bg-transparent border rounded-3">
                <div class="card-header bg-light border-bottom">
                    <h5 class="card-header-title">User Information</h5>
                </div>
                <div class="card-body">
                    <div class="row g-4">
                        <!-- User ID -->
                        <div class="col-md-6">
                            <div class="bg-light p-4 rounded-3 position-relative overflow-hidden h-100">
                                <div class="d-flex justify-content-between align-items-center">
                                    <div>
                                        <h6 class="mb-0">User ID</h6>
                                        <h4 class="mb-0 mt-2">#${transaction.userID}</h4>
                                    </div>
                                    <i class="bi bi-person-badge fs-1 text-primary opacity-25"></i>
                                </div>
                            </div>
                        </div>

                        <!-- User Name -->
                        <div class="col-md-6">
                            <div class="bg-light p-4 rounded-3 position-relative overflow-hidden h-100">
                                <div class="d-flex justify-content-between align-items-center">
                                    <div>
                                        <h6 class="mb-0">User Name</h6>
                                        <h4 class="mb-0 mt-2">${transaction.userName}</h4>
                                    </div>
                                    <i class="bi bi-person fs-1 text-primary opacity-25"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>
      <!-- After the User Information Card, add a new card for Bank Information -->
<!-- Bank Information Card -->
<c:if test="${bank != null}">
    <div class="card bg-transparent border rounded-3 mt-4">
        <div class="card-header bg-light border-bottom">
            <h5 class="card-header-title">Bank Information</h5>
        </div>
        <div class="card-body">
            <div class="row g-4">
                <!-- Bank Type -->
                <div class="col-md-4">
                    <div class="bg-light p-4 rounded-3 position-relative overflow-hidden h-100">
                        <div class="d-flex justify-content-between align-items-center">
                            <div>
                                <h6 class="mb-0">Bank Type</h6>
                                <h4 class="mb-0 mt-2">${bank.bankType}</h4>
                            </div>
                            <i class="bi bi-bank fs-1 text-primary opacity-25"></i>
                        </div>
                    </div>
                </div>

                <!-- Bank Name -->
                <div class="col-md-4">
                    <div class="bg-light p-4 rounded-3 position-relative overflow-hidden h-100">
                        <div class="d-flex justify-content-between align-items-center">
                            <div>
                                <h6 class="mb-0">Bank Name</h6>
                                <h4 class="mb-0 mt-2">${bank.bankName}</h4>
                            </div>
                            <i class="bi bi-building fs-1 text-primary opacity-25"></i>
                        </div>
                    </div>
                </div>

                <!-- Bank Account Number -->
                <div class="col-md-4">
                    <div class="bg-light p-4 rounded-3 position-relative overflow-hidden h-100">
                        <div class="d-flex justify-content-between align-items-center">
                            <div>
                                <h6 class="mb-0">Account Number</h6>
                                <h4 class="mb-0 mt-2">${bank.bankNo}</h4>
                            </div>
                            <i class="bi bi-credit-card fs-1 text-primary opacity-25"></i>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</c:if>
<!-- After the Bank Information Card section, before the closing main tag -->
<!-- Action Buttons for Pending Withdrawals -->
<c:if test="${transaction.status eq 'Pending' && transaction.content eq 'R�t ti?n'}">
    <div class="container mt-4">
        <div class="row">
            <div class="col-12">
                <div class="card bg-transparent border rounded-3">
                    <div class="card-header bg-light border-bottom">
                        <h5 class="card-header-title">Withdrawal Actions</h5>
                    </div>
                    <div class="card-body">
                        <div class="d-flex justify-content-between">
                            <p class="mb-0">Please approve or reject this withdrawal request:</p>
                            <div>
                                <form action="TransactionDetail" method="post" class="d-inline">
                                    <input type="hidden" name="action" value="accept">
                                    <input type="hidden" name="id" value="${transaction.id}">
                                    <button type="submit" class="btn btn-success">
                                        <i class="bi bi-check-circle me-2"></i>Accept Withdrawal
                                    </button>
                                </form>
                                <form action="TransactionDetail" method="post" class="d-inline ms-2">
                                    <input type="hidden" name="action" value="reject">
                                    <input type="hidden" name="id" value="${transaction.id}">
                                    <button type="submit" class="btn btn-danger">
                                        <i class="bi bi-x-circle me-2"></i>Reject Withdrawal
                                    </button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</c:if>

<!-- Display success/error messages if available -->
<c:if test="${not empty message}">
    <div class="container mt-4">
        <div class="alert ${messageType eq 'success' ? 'alert-success' : 'alert-danger'} alert-dismissible fade show" role="alert">
            ${message}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </div>
</c:if>
    <!-- Page content END -->

    <!-- Bootstrap JS -->
    <script src="../assets/vendor/bootstrap/dist/js/bootstrap.bundle.min.js"></script>
    <!-- Template Functions -->
    <script src="../assets/js/functions.js"></script>
</body>
</html>