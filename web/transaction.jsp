<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Skill, java.util.ArrayList, model.User, java.text.SimpleDateFormat, model.Mentor, model.Mentee, model.MenteeStatistic, model.MentorStatistic, model.Bank, model.Transaction" %>
<!doctype html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Transaction</title>
        <link href="assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="assets/css/main.css" rel="stylesheet">

        <style>
            body {
                background: #f5f5f5;
                margin-top: 20px;
            }
            .table-container {
                margin-top: 20px;
            }
            .pagination {
                justify-content: center;
            }
            .hidden {
                display: none;
            }
            .filter-form {
                display: flex;
                flex-wrap: wrap;
                gap: 20px;  /* Khoảng cách giữa các trường */
                justify-content: flex-start;
                align-items: center;
            }

            .filter-form .form-group {
                margin: 0;  /* Loại bỏ khoảng cách mặc định */
                flex-basis: 18%;  /* Các trường sẽ chiếm 18% chiều rộng */
            }

            .filter-form label {
                display: block;  /* Đảm bảo label luôn nằm trên các input */
                margin-bottom: 5px;
            }

            .filter-form .form-control {
                width: 100%;  /* Đảm bảo input chiếm đầy chiều rộng */
                margin-bottom: 10px; /* Khoảng cách dưới các input */
            }

            .filter-form button {
                margin-top: 20px;
                flex-basis: auto;
            }

        </style>

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
        <div class="container light-style flex-grow-1 container-p-y">
            <h4 class="font-weight-bold py-3 mb-4">TÀI KHOẢN</h4>

            <div class="card overflow-hidden">
                <div class="row no-gutters row-bordered row-border-light">
                    <div class="col-md-3 pt-0">
                        <div class="list-group list-group-flush account-settings-links">
                            <%if(u.getRole().equalsIgnoreCase("Mentor")) {%>
                            <a id="statistics" class="list-group-item list-group-item-action " data-toggle="list" href="statistics">Request statistics</a>
                            <%}else {%>
                            <a id="statistics" class="list-group-item list-group-item-action " data-toggle="list" href="statistics">Request statistics</a>
                            <%}%>
                            <a id="history" class="list-group-item list-group-item-action active" href="transaction">Transaction history</a>
                            <a id="pay" class="list-group-item list-group-item-action" href="bank">Payment Setting</a>
                            <a id="wallet" class="list-group-item list-group-item-action" href="wallet">Wallet</a>
                        </div>
                    </div>

                    <div class="col-lg-9 col-md-9 col-sm-12 col-xs-12">
                        <div class="aside">
                            <h3>Your Transactions</h3>
                            <form method="get" action="transaction" class="filter-form">
                                <div class="form-group">
                                    <label for="transactionType">Kiểu giao dịch</label>
                                    <select id="transactionType" class="form-control" name="transactionType">
                                        <option value="">Tất cả</option>
                                        <option value="+" <%= " +".equals(request.getParameter("transactionType")) ? "selected" : "" %>>+</option>
                                        <option value="-" <%= " -".equals(request.getParameter("transactionType")) ? "selected" : "" %>>-</option>
                                    </select>
                                </div>

                                <div class="form-group">
                                    <label for="amountFilter">Sắp xếp theo số tiền</label>
                                    <select id="amountFilter" class="form-control" name="amountFilter">
                                        <option value="">Tất cả</option>
                                        <option value="asc" <%= "asc".equals(request.getParameter("amountFilter")) ? "selected" : "" %>>Tăng dần</option>
                                        <option value="desc" <%= "desc".equals(request.getParameter("amountFilter")) ? "selected" : "" %>>Giảm dần</option>
                                    </select>
                                </div>

                                <div class="form-group">
                                    <label for="statusFilter">Trạng thái</label>
                                    <select id="statusFilter" class="form-control" name="statusFilter">
                                        <option value="">Tất cả</option>
                                        <option value="Success" <%= "Success".equals(request.getParameter("statusFilter")) ? "selected" : "" %>>Success</option>
                                        <option value="Pending" <%= "Pending".equals(request.getParameter("statusFilter")) ? "selected" : "" %>>Pending</option>
                                        <option value="Fail" <%= "Fail".equals(request.getParameter("statusFilter")) ? "selected" : "" %>>Fail</option>
                                    </select>
                                </div>


                                <div class="form-group">
                                    <label for="fromDate">Từ ngày</label>
                                    <input type="date" id="fromDate" class="form-control" name="fromDate" value="<%= request.getParameter("fromDate") != null ? request.getParameter("fromDate") : "" %>">
                                </div>

                                <div class="form-group">
                                    <label for="toDate">Đến ngày</label>
                                    <input type="date" id="toDate" class="form-control" name="toDate" value="<%= request.getParameter("toDate") != null ? request.getParameter("toDate") : "" %>">
                                </div>

                                <button type="submit" class="btn btn-primary">Lọc</button>
                            </form>


                            <div class="table-container">
                                <div class="table-responsive">
                                    <table class="table table-striped table-bordered table-hover">
                                        <thead>
                                            <tr>
                                                <th>#</th>
                                                <th>Kiểu</th>
                                                <th>Số tiền</th>
                                                <th>Nội dung</th>
                                                <th>Thời gian</th>
                                                <th>Trạng thái</th>
                                            </tr>
                                        </thead>
                                        <tbody id="transactionTable">
                                            <%  
                                                ArrayList<Transaction> transactions = (ArrayList<Transaction>) request.getAttribute("transactions");
                                                int totalTransactions = transactions.size();
                                                int totalPages = (int) Math.ceil((double) totalTransactions / 10);
                                            
                                                for(int i = 0; i < totalTransactions; i++) { 
                                            %>
                                            <tr class="transaction-row" id="row-<%=i%>">
                                                <td><%= i + 1 %></td>
                                                <td style="font-weight: bold; color: <%= transactions.get(i).getType().equalsIgnoreCase("+") ? "green" : "red" %>">
                                                    <%= transactions.get(i).getType() %>
                                                </td>
                                                <td><%= transactions.get(i).getBalance() %></td>
                                                <td><%= transactions.get(i).getContent() %></td>
                                                <td><%= transactions.get(i).getTime() %></td>
                                                <td style="color: <%= transactions.get(i).getStatus().equalsIgnoreCase("success") ? "green" : "red" %>">
                                                    <%= transactions.get(i).getStatus() %>
                                                </td>
                                            </tr>
                                            <% } %>
                                        </tbody>
                                    </table>

                                    <% if (totalTransactions == 0) { %>
                                    <div class="text-center"><span>Không có giao dịch nào</span></div>
                                    <% } %>

                                    <div class="pagination-container">
                                        <ul class="pagination">
                                            <li class="page-item disabled" id="firstPage"><a class="page-link" href="#">First</a></li>
                                            <li class="page-item disabled" id="prevPage"><a class="page-link" href="#">Previous</a></li>
                                                <% for(int i = 1; i <= totalPages; i++) { %>
                                            <li class="page-item <%= i == 1 ? "active" : "" %>">
                                                <a class="page-link" href="#" onclick="changePage(<%= i %>, event)"><%= i %></a>
                                            </li>
                                            <% } %>
                                            <li class="page-item <%= totalPages > 1 ? "" : "disabled" %>" id="nextPage"><a class="page-link" href="#">Next</a></li>
                                            <li class="page-item <%= totalPages > 1 ? "" : "disabled" %>" id="lastPage"><a class="page-link" href="#">Last</a></li>
                                        </ul>
                                    </div>
                                </div>
                            </div> <!-- End Table Container -->
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script>
            let totalPages = <%= totalPages %>;
            let currentPage = 1;

            function changePage(page, event) {
                event.preventDefault();
                if (page < 1 || page > totalPages)
                    return;

                currentPage = page;
                let start = (currentPage - 1) * 10;
                let end = start + 10;

                document.querySelectorAll('.transaction-row').forEach((row, index) => {
                    row.style.display = (index >= start && index < end) ? "table-row" : "none";
                });

                document.querySelectorAll('.pagination .page-item').forEach(item => item.classList.remove("active"));
                // Adjust the page index correctly
                document.querySelectorAll('.pagination .page-item')[page].classList.add("active");

                document.getElementById("prevPage").classList.toggle("disabled", currentPage === 1);
                document.getElementById("nextPage").classList.toggle("disabled", currentPage === totalPages);
                document.getElementById("firstPage").classList.toggle("disabled", currentPage === 1);
                document.getElementById("lastPage").classList.toggle("disabled", currentPage === totalPages);
            }

// Previous button
            document.getElementById("prevPage").addEventListener("click", function (event) {
                event.preventDefault();
                if (currentPage > 1)
                    changePage(currentPage - 1, event);
            });

// Next button
            document.getElementById("nextPage").addEventListener("click", function (event) {
                event.preventDefault();
                if (currentPage < totalPages)
                    changePage(currentPage + 1, event);
            });

// First button
            document.getElementById("firstPage").addEventListener("click", function (event) {
                event.preventDefault();
                changePage(1, event);
            });

// Last button
            document.getElementById("lastPage").addEventListener("click", function (event) {
                event.preventDefault();
                changePage(totalPages, event);
            });

// Display the first page by default
            changePage(1, new Event('load'));

        </script>

    </body>
</html>
