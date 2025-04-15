<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Bank, model.User" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    User u = (User) session.getAttribute("u");
    if (u == null) {
        response.sendRedirect("Login.jsp");
        return;
    }
%>
<% 
    Bank bank = (Bank) request.getAttribute("Bank");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Rút Tiền</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .withdraw-modal {
            max-width: 500px;
            margin: 30px auto;
            padding: 20px;
            background: white;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        .form-control:disabled {
            background: #f8f9fa;
        }
    </style>
</head>

    <body>
        <%if(u.getRole().equalsIgnoreCase("Mentor")) {%>
        <%@include file="MentorHeader.jsp" %>
        <%}else {%>
        <%@include file="MenteePanner.jsp" %>
        <%}%> 

    <div class="container">
        <div class="withdraw-modal">
            <h3 class="text-center">Rút Tiền</h3>
            <form action="wallet" method="post">
                <input type="hidden" name="type" value="withdraw">

                <div class="mb-3">
                    <label class="form-label">Cổng thanh toán</label>
                    <input type="text" class="form-control" value="<%= bank.getBankType() %>" disabled>
                </div>

                <div class="mb-3">
                    <label class="form-label">Chủ tài khoản</label>
                    <input type="text" class="form-control" value="<%= bank.getBankName() %>" disabled>
                </div>

                <div class="mb-3">
                    <label class="form-label">Số tài khoản</label>
                    <input type="text" class="form-control" value="<%= bank.getBankNo() %>" disabled>
                </div>

                <div class="mb-3">
                    <label class="form-label">Số tiền có thể rút</label>
                    <input type="text" class="form-control" value="<%= u.getWallet() %> VNĐ" disabled>
                </div>

                <div class="mb-3">
                    <label class="form-label">Bạn muốn rút</label>
                    <input type="number" name="amount" class="form-control" min="1000" max="<%= u.getWallet() %>" required>
                </div>

                <div class="text-center">
                    <button type="submit" class="btn btn-danger">Rút Tiền</button>
                    <a href="wallet" class="btn btn-secondary">Đóng</a>
                </div>
            </form>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
