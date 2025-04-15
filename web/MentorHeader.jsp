<%-- 
    Document   : MentorHeader
    Created on : Feb 24, 2025, 9:05:03 PM
    Author     : ACER
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="model.User" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
    <title>Eduport - LMS, Education and Course Theme</title>

    <!-- Meta Tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="author" content="Webestica.com">
    <meta name="description" content="Eduport- LMS, Education and Course Theme">

    <!-- Favicon -->
    <link rel="shortcut icon" href="assets/images/favicon.ico">

    <!-- Google Font -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Heebo:wght@400;500;700&family=Roboto:wght@400;500;700&display=swap">

    <!-- Plugins CSS -->
    <link rel="stylesheet" type="text/css" href="assets/vendor/font-awesome/css/all.min.css">
    <link rel="stylesheet" type="text/css" href="assets/vendor/bootstrap-icons/bootstrap-icons.css">
    <link rel="stylesheet" type="text/css" href="assets/vendor/tiny-slider/tiny-slider.css">
    <link rel="stylesheet" type="text/css" href="assets/vendor/glightbox/css/glightbox.css">

    <!-- Theme CSS -->
    <link id="style-switch" rel="stylesheet" type="text/css" href="assets/css/style.css">

    <!-- Global site tag (gtag.js) - Google Analytics -->
    <script async src="https://www.googletagmanager.com/gtag/js?id=G-7N7LGGGWT1"></script>
    <script>
        window.dataLayer = window.dataLayer || [];
        function gtag() {
            dataLayer.push(arguments);
        }
        gtag('js', new Date());

        gtag('config', 'G-7N7LGGGWT1');
    </script>

</head>
<%
User user = (User) session.getAttribute("user");
if (user == null) {
    response.sendRedirect("Login.jsp");
    return;
}
%>

<!-- Header START -->
<header class="navbar-light navbar-sticky header-static">
    <!-- Logo Nav START -->
    <nav class="navbar navbar-expand-xl">
        <div class="container-fluid px-3 px-xl-5">
            <!-- Logo START -->
            <a class="navbar-brand" href="CustomerHome.jsp">
                <img class="light-mode-item navbar-brand-item" src="https://www.senviet.art/wp-content/uploads/2021/12/fptulogo.jpg" alt="logo">
                <img class="dark-mode-item navbar-brand-item" src="https://www.senviet.art/wp-content/uploads/2021/12/fptulogo.jpg" alt="logo">
            </a>
            <!-- Logo END -->

            <!-- Responsive navbar toggler -->
            <button class="navbar-toggler ms-auto" type="button" data-bs-toggle="collapse" data-bs-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-animation">
                    <span></span>
                    <span></span>
                    <span></span>
                </span>
            </button>

            <!-- Main navbar START -->
            <div class="navbar-collapse w-100 collapse" id="navbarCollapse">

                <!-- Nav category menu START -->
                <ul class="navbar-nav navbar-nav-scroll me-auto">
                    <!-- Nav item 1 Demos -->
                    <li class="nav-item dropdown dropdown-menu-shadow-stacked">
                        <a class="nav-link bg-primary bg-opacity-10 rounded-3 text-primary px-3 py-3 py-xl-0" href="#" id="categoryMenu" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><i class="bi bi-ui-radios-grid me-2"></i><span>Category</span></a>
                        <ul class="dropdown-menu" aria-labelledby="categoryMenu">

                            <!-- Dropdown submenu -->
                            <li class="dropdown-submenu dropend">
                                <a class="dropdown-item" href="#">Tieng anh cap 1</a>
                            </li>
                            <li> <a class="dropdown-item" href="#">Tieng anh cap 2</a></li>
                            <!-- Dropdown submenu -->
                            <li class="dropdown-submenu dropend">
                                <a class="dropdown-item" href="#">Tieng anh cap 3</a>
                            </li>
                            <li> <a class="dropdown-item" href="#">IELTS</a></li>
                            <li> <a class="dropdown-item" href="#">TOEIC</a></li>

                            <li> <hr class="dropdown-divider"></li>
                        </ul>
                    </li>
                </ul>

                <!-- Nav Main menu START -->
                <ul class="navbar-nav navbar-nav-scroll me-auto">
                    <!-- Back home page -->
                    <li class="nav-item dropdown">
                        <a class="nav-link active" href="MentorHome.jsp" id="demoMenu" aria-haspopup="true" aria-expanded="false">Homes</a>

                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link" href="requestProcessing" id="demoMenu" aria-haspopup="true" aria-expanded="false">List Request</a>

                    </li>
                    <li class="nav-item dropdown dropdown-fullwidth">
                    <li class="nav-item">
                        <a class="nav-link" aria-current="page" href="ChatList.jsp">Message</a>
                    </li>                              
                    </li>                    
                    <li class="nav-item dropdown dropdown-fullwidth">
                    <li class="nav-item">
                        <a class="nav-link" aria-current="page" href="./cv">My CV</a>
                    </li>
                    <li class="nav-item dropdown dropdown-fullwidth">
                    <li class="nav-item">
                        <a class="nav-link" aria-current="page" href="./schedule?mentorID=${sessionScope.u.userID}">My Schedule</a>
                    </li>                                     
                    <li class="nav-item dropdown dropdown-fullwidth">
                    <li class="nav-item">
                        <a class="nav-link" aria-current="page" href="wallet"><%= user.getWallet() %> vnd</a>
                    </li>  


            </div>
            <!-- Main navbar END -->

            <!-- Profile START -->
            <a class="h6" href="#"><%= user.getFullname() %></a>
            <div class="dropdown ms-1 ms-lg-0">
                <a class="avatar avatar-sm p-0" href="#" id="profileDropdown" role="button" data-bs-auto-close="outside" data-bs-display="static" data-bs-toggle="dropdown" aria-expanded="false">
                    <img class="avatar-img rounded-circle" src="<%= user.getAvatar() %>" alt="avatar">
                </a>
                <ul class="dropdown-menu dropdown-animation dropdown-menu-end shadow pt-3" aria-labelledby="profileDropdown">
                    <!-- Profile info -->
                    <li class="px-3">
                        <div class="d-flex align-items-center">
                            <!-- Avatar -->
                            <div class="avatar me-3">
                                <img class="avatar-img rounded-circle shadow" src="<%= user.getAvatar() %>" alt="avatar">
                            </div>
                            <div>
                                <a class="h6" href="#">${sessionScope.fullname}</a>
                                <p class="small m-0">${sessionScope.mail}</p>
                            </div>
                        </div>
                        <hr>
                    </li>
                    <!-- Links -->
                    <li><a class="dropdown-item" href="ShowProfile.jsp"><i class="bi bi-person fa-fw me-2"></i>Your Profile</a></li>
                    <li> <a class="dropdown-item" href="ChangePassword.jsp"><i class="fas fa-fw fa-edit me-1"></i>Change Password</a> </li>
                    <li> <a class="dropdown-item" href="DeleteAccount.jsp"><i class="fas fa-fw fa-trash-alt me-1"></i>Delete Account</a> </li>
                    <li><a class="dropdown-item bg-danger-soft-hover" href="logout"><i class="bi bi-power fa-fw me-2"></i>Sign Out</a></li>
                    <!-- Dark mode switch START -->
                    <li>
                        </div>
                </ul>
            </div>
            <!-- Profile START -->
        </div>
    </nav>
    <!-- Logo Nav END -->
</header>
