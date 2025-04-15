<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page import="model.User" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Your Profile</title>

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
        User u = (User) session.getAttribute("u");
        if (u == null) {
            response.sendRedirect("Login.jsp");
            return;
        }
    %>

    <body>
        <%if(u.getRole().equalsIgnoreCase("Mentor")) {%>
        <%@include file="MentorHeader.jsp" %>
        <%}else {%>
        <%@include file="MenteePanner.jsp" %>
        <%}%> 

        <!-- **************** MAIN CONTENT START **************** -->
        <main>
            <form action="edit" method="get">
                <section class="h-100 bg-light">
                    <div class="container py-5 h-100">
                        <div class="row d-flex justify-content-center align-items-center h-100">
                            <div class="col">
                                <div class="card card-registration my-4">
                                    <div class="row g-0">
                                        <div class="col-xl-6 d-none d-xl-block">
                                            <img src="assets/images/element/02.svg"
                                                 alt="Sample photo" class="img-fluid"
                                                 style="border-top-left-radius: .25rem; border-bottom-left-radius: .25rem;" />
                                        </div>
                                        <div class="col-xl-6">
                                            <div class="card-body p-md-5 text-black">
                                                <h3 class="mb-5 text-uppercase">Your Profile</h3>

                                                <div class="row">
                                                    <div class="col-md-6 mb-4">
                                                        <div data-mdb-input-init class="form-outline">
                                                            <label class="form-label" for="form3Example1m">Full name:</label>
                                                            <p class="h6"><%= u.getFullname() %></p>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-6 mb-4">
                                                            <div data-mdb-input-init class="form-outline">
                                                                <label class="form-label" for="form3Example1m1">Phone Number: </label>
                                                                <p class="h6"><%= u.getPhoneNumber() %></p>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-6 mb-4">
                                                            <div data-mdb-input-init class="form-outline">
                                                                <label class="form-label" for="form3Example1n1">Email:</label>
                                                                <p class="h6"><%= u.getEmail() %></p>                                                    
                                                            </div>
                                                        </div>
                                                        <div data-mdb-input-init class="form-outline mb-4">
                                                            <label class="form-label" for="form3Example8">Address:</label>
                                                            <p class="h6"><%= u.getAddress() %></p>                                           
                                                            <div data-mdb-input-init class="form-outline mb-4">
                                                                <label class="form-label" for="form3Example1n1">Gender:</label>

                                                                <p class="h6">
                                                                    <%= u.getSex() ? "Male" : "Female" %>
                                                                </p>
                                                            </div>
                                                            <div data-mdb-input-init class="form-outline mb-4">
                                                                <label class="form-label" for="form3Example9">Birthdate:</label>
                <!--                                                <p class="h6"><fmt:formatDate value="<%= u.getDob() %>" pattern="yyyy/MM/dd"/></p>-->
                                                                <p class="h6"><%= u.getDob() %></p>
                                                            </div>


                                                            <div class="d-flex justify-content-end pt-3">
                                                                <button class="btn btn-warning btn-lg ms-2"><a href="CustomerHome.jsp">Home</a></button>
                                                                <button type="submit" class="btn btn-warning btn-lg ms-2"><a>Edit</a></button>
                                                                </form>                                            
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                </section>
                                </form>

                                </main>
                                <!-- **************** MAIN CONTENT END **************** -->

                                <!-- =======================
                                Footer START -->
                                <%@include file="Footer.jsp" %>
                                <!-- Back to top -->
                                <div class="back-top"><i class="bi bi-arrow-up-short position-absolute top-50 start-50 translate-middle"></i></div>

                                <!-- Bootstrap JS -->
                                <script src="assets/vendor/bootstrap/dist/js/bootstrap.bundle.min.js"></script>

                                <!-- Vendors -->
                                <script src="assets/vendor/tiny-slider/tiny-slider.js"></script>
                                <script src="assets/vendor/glightbox/js/glightbox.js"></script>
                                <script src="assets/vendor/purecounterjs/dist/purecounter_vanilla.js"></script>

                                <!-- Template Functions -->
                                <script src="assets/js/functions.js"></script>

                                </body>
                                </html>