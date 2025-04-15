<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.User" %>

<%
    User u = (User) session.getAttribute("u");
    if (u == null) {
        response.sendRedirect("Login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Nạp Tiền qua VNPay</title>
    
    <!-- Bootstrap & Custom CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">

    <style>
        body {
            background: #f5f5f5;
            font-family: 'Arial', sans-serif;
        }

        .recharge-container {
            max-width: 500px;
            margin: 50px auto;
            padding: 30px;
            background: white;
            border-radius: 10px;
            box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
            text-align: center;
        }

        h2 {
            font-weight: bold;
            margin-bottom: 20px;
            color: #333;
        }

        .form-group label {
            font-size: 16px;
            font-weight: 600;
            display: block;
            text-align: left;
            color: #555;
        }

        .form-control {
            width: 100%;
            padding: 12px;
            font-size: 16px;
            border: 2px solid #ddd;
            border-radius: 8px;
            transition: all 0.3s ease-in-out;
        }

        .form-control:focus {
            border-color: #007bff;
            outline: none;
            box-shadow: 0 0 8px rgba(0, 123, 255, 0.2);
        }

        .btn-recharge {
            width: 100%;
            padding: 12px;
            font-size: 18px;
            font-weight: bold;
            color: white;
            background: #28a745;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            transition: all 0.3s ease-in-out;
        }

        .btn-recharge:hover {
            background: #218838;
        }

        .btn-cancel {
            display: inline-block;
            margin-top: 15px;
            color: #dc3545;
            font-size: 16px;
            text-decoration: none;
            font-weight: bold;
            transition: all 0.3s ease-in-out;
        }

        .btn-cancel:hover {
            color: #c82333;
            text-decoration: underline;
        }
    </style>
</head>
    <body>
        <%if(u.getRole().equalsIgnoreCase("Mentor")) {%>
        <%@include file="MentorHeader.jsp" %>
        <%}else {%>
        <%@include file="MenteePanner.jsp" %>
        <%}%> 

    <div class="recharge-container">
        <h2>Nạp Tiền qua VNPay</h2>

        <form action="wallet" method="post">
            <input type="hidden" name="type" value="recharge">

            <div class="form-group">
                <label for="amount">Nhập số tiền muốn nạp (VND):</label>
                <input type="number" class="form-control" name="amount" id="amount" min="1000" required>
            </div>
            <br>
            <button type="submit" class="btn-recharge">Thanh toán qua VNPay</button>
        </form>

        <a href="wallet" class="btn-cancel">Hủy</a>
    </div>

    <!-- JavaScript -->
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
