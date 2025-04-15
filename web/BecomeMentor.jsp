<!--<!DOCTYPE html>
<%@ page import="java.sql.*" %>
<%@ page import="javax.servlet.*" %>
<%@ page import="javax.servlet.http.*" %>
--><html lang="en">
    <head>
        <title>Become a Mentor</title>

        <!--	 Meta Tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="author" content="Webestica.com">
        <meta name="description" content="Eduport- LMS, Education and Course Theme">

        <!--	 Favicon -->
        <link rel="shortcut icon" href="assets/images/favicon.ico">

        <!--	 Google Font -->
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Heebo:wght@400;500;700&family=Roboto:wght@400;500;700&display=swap">

        <!--	 Plugins CSS -->
        <link rel="stylesheet" type="text/css" href="assets/vendor/font-awesome/css/all.min.css">
        <link rel="stylesheet" type="text/css" href="assets/vendor/bootstrap-icons/bootstrap-icons.css">
        <link rel="stylesheet"  href="assets/css/Registercss.css"> <!-- css -->
        <!--	 Theme CSS -->
        <link id="style-switch" rel="stylesheet" type="text/css" href="assets/css/style.css">

    </head>
<%
    Connection connection = null;
    Statement stmt = null;
    ResultSet rs = null;

    String dbUrl = "jdbc:sqlserver://localhost:1433;databaseName=DB_SWP391";
    String dbUser = "sa";
    String dbPassword = "sa";

    try {
        // T?o k?t n?i
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);

        String sql = "SELECT SkillID, SkillName FROM [dbo].[Skills] WHERE enable = 1";
        stmt = connection.createStatement();
        rs = stmt.executeQuery(sql);
    } catch (Exception e) {
        e.printStackTrace();
    }
%>
    <body>
        <%@include file="MenteePanner.jsp" %>
        <form action="becomeMentor" method="post">
                <div class="container py-5 h-100">
                    <div class="row d-flex justify-content-center align-items-center h-100">
                        <div class="col">
                            <div class="card card-registration my-4">
                                <div class="row g-0">
                                    <div class="col-xl-6 d-none d-xl-block">
                                        <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-registration/img4.webp"
                                             alt="Sample photo" class="img-fluid"
                                             style="border-top-left-radius: .25rem; border-bottom-left-radius: .25rem;" />
                                    </div>
                                    <div class="col-xl-6">
                                        <div class="card-body p-md-5 text-black">
                                    <h3 class="mb-5 text-uppercase">Become a Mentor</h3>

                                    <div class="row">
                                        <div class="col-md-6 mb-4">
                                            <div data-mdb-input-init class="form-outline">
                                                <label class="form-label" for="form3Example1m">Full name:</label>
                                                <input type="text" id="form3Example1m" class="form-control form-control-lg" name="fullname" placeholder="Enter full name" value="${sessionScope.fullname}"/>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-6 mb-4">
                                            <div data-mdb-input-init class="form-outline">
                                                <label class="form-label" for="form3Example1m1">Phone Number:</label>
                                                <input type="text" id="form3Example1m1" class="form-control form-control-lg" name="phoneNumber" placeholder="Enter phone number" value="${sessionScope.phone}"/>
                                            </div>
                                        </div>
                                        <div class="col-md-6 mb-4">
                                            <div data-mdb-input-init class="form-outline">
                                                <label class="form-label" for="form3Example1n1">Email:</label>
                                                <input type="email" id="form3Example1n1" class="form-control form-control-lg" name="email" placeholder="E-mail" value="${sessionScope.mail}"/>
                                            </div>
                                        </div>
                                    </div>

                                    <div data-mdb-input-init class="form-outline mb-4">
                                        <label class="form-label" for="form3Example8">Address:</label>
                                        <input type="text" id="form3Example8" class="form-control form-control-lg" name="address" placeholder="Enter address" value="${sessionScope.address}"/>
                                    </div>

                                    <div class="d-md-flex justify-content-start align-items-center mb-4 py-2">
                                        <h6 class="mb-0 me-4">Gender: </h6>
                                        <div class="form-check form-check-inline mb-0 me-4">
                                            <input class="form-check-input" type="radio" name="sex" id="female" value="option1"/>
                                            <label class="form-check-label" for="femaleGender">Female</label>
                                        </div>
                                        <div class="form-check form-check-inline mb-0 me-4">
                                            <input class="form-check-input" type="radio" name="sex" id="male" value="option2"/>
                                            <label class="form-check-label" for="maleGender">Male</label>
                                        </div>
                                    </div>

                                    <div data-mdb-input-init class="form-outline mb-4">
                                        <fmt:formatDate value="${sessionScope.birthdate}" pattern="yyyy/MM/dd"/>
                                        <label class="form-label" for="form3Example9">Birthdate:</label>
                                        <input type="date" id="form3Example9" class="form-control form-control-lg" name="dob" value="${sessionScope.birthdate}"/>
                                    </div>

                                    <!-- Thêm checkbox cho các k? n?ng -->
                                    <div class="form-outline mb-4">
                                        <label class="form-label">Skills:</label>
                                        <div class="form-check">
                                            <% 
                                                // T?o checkbox cho t?ng k? n?ng ???c l?y t? c? s? d? li?u
                                                while (rs.next()) {
                                                    String skillName = rs.getString("SkillName");
                                                    String skillID = rs.getString("SkillID");
                                            %>
                                                <input class="form-check-input" type="checkbox" value="<%= skillID %>" name="skills[]">
                                                <label class="form-check-label"><%= skillName %></label><br>
                                            <% 
                                                }
                                            %>
                                        </div>
                                    </div>

                                    <!-- Thêm các tr??ng ProfessionIntroduction, ServiceDescription, CashPerSlot -->
                                    <div data-mdb-input-init class="form-outline mb-4">
                                        <label class="form-label" for="form3ExampleProfessionIntroduction">Profession Introduction:</label>
                                        <textarea id="form3ExampleProfessionIntroduction" class="form-control form-control-lg" name="professionIntroduction" placeholder="Enter your profession introduction" rows="4" required></textarea>
                                    </div>

                                    <div data-mdb-input-init class="form-outline mb-4">
                                        <label class="form-label" for="form3ExampleServiceDescription">Service Description:</label>
                                        <textarea id="form3ExampleServiceDescription" class="form-control form-control-lg" name="serviceDescription" placeholder="Describe the services you offer" rows="4" required></textarea>
                                    </div>

                                    <div data-mdb-input-init class="form-outline mb-4">
                                        <label class="form-label" for="form3ExampleCashPerSlot">Cash Per Slot:</label>
                                        <input type="number" id="form3ExampleCashPerSlot" class="form-control form-control-lg" name="cashPerSlot" placeholder="Enter cash per slot" required/>
                                    </div>
                                                <% String mess = (String) request.getAttribute("mess");
                                            if (mess != null) { %>
                                                <p style="color: red;"><%= mess %></p>
                                                <% } %>
                                    <div class="d-flex justify-content-end pt-3">
                                        <button class="btn btn-warning btn-lg ms-2"><a href="CustomerHome.jsp">Home</a></button>
                                        <button class="btn btn-warning btn-lg ms-2"><a href="viewForm">View Form</a></button>
                                        <button type="reset" class="btn btn-warning btn-lg ms-2">Reset All</button>
                                        <button type="submit" class="btn btn-warning btn-lg ms-2">Submit form</button>
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
                                        <%
    // ?óng k?t n?i c? s? d? li?u
    try {
        if (rs != null) rs.close();
        if (stmt != null) stmt.close();
        if (connection != null) connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
%>
    </body>
</html>