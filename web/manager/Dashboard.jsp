<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Manager Dashboard - Online Rent Tutor</title>
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
            <!-- Stats START -->
            <div class="row g-4 mb-4">
                <div class="col-md-4">
                    <div class="card card-body bg-warning bg-opacity-15">
                        <h5>Total Mentees</h5>
                        <h4 class="text-warning mb-0">${totalMentees}</h4>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card card-body bg-success bg-opacity-10">
                        <h5>Active Mentors</h5>
                        <h4 class="text-success mb-0">${activeMentors}</h4>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card card-body bg-primary bg-opacity-10">
                        <h5>Pending Requests</h5>
                        <h4 class="text-primary mb-0">${pendingRequests}</h4>
                    </div>
                </div>
            </div>
            <!-- Stats END -->


            <!-- Page content END -->

            <!-- Back to top -->
            <div class="back-top"><i class="bi bi-arrow-up-short position-absolute top-50 start-50 translate-middle"></i></div>

            <!-- Bootstrap JS -->
            <script src="../assets/vendor/bootstrap/dist/js/bootstrap.bundle.min.js"></script>
            <!-- Vendors -->
            <script src="../assets/vendor/tiny-slider/tiny-slider.js"></script>
            <script src="../assets/vendor/glightbox/js/glightbox.js"></script>
            <!-- Template Functions -->
            <script src="../assets/js/functions.js"></script>
    </body>
</html>