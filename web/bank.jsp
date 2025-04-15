<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="model.Skill, java.util.ArrayList, model.User, java.text.SimpleDateFormat, model.Mentor, model.Mentee, model.MenteeStatistic, model.MentorStatistic, model.Bank" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <style>
        body {
            background: #f5f5f5;
            margin-top: 20px;
        }

        .ui-w-80 {
            width: 80px !important;
            height: auto;
        }

        .btn-default {
            border-color: rgba(24, 28, 33, 0.1);
            background: rgba(0, 0, 0, 0);
            color: #4E5155;
        }

        label.btn {
            margin-bottom: 0;
        }

        .btn-outline-primary {
            border-color: #26B4FF;
            background: transparent;
            color: #26B4FF;
        }

        .btn {
            cursor: pointer;
        }

        .text-light {
            color: #babbbc !important;
        }

        .btn-facebook {
            border-color: rgba(0, 0, 0, 0);
            background: #3B5998;
            color: #fff;
        }

        .btn-instagram {
            border-color: rgba(0, 0, 0, 0);
            background: #000;
            color: #fff;
        }

        .card {
            background-clip: padding-box;
            box-shadow: 0 1px 4px rgba(24, 28, 33, 0.012);
        }

        .row-bordered {
            overflow: hidden;
        }

        .account-settings-fileinput {
            position: absolute;
            visibility: hidden;
            width: 1px;
            height: 1px;
            opacity: 0;
        }

        .account-settings-links .list-group-item.active {
            font-weight: bold !important;
        }

        html:not(.dark-style) .account-settings-links .list-group-item.active {
            background: transparent !important;
        }

        .account-settings-multiselect~.select2-container {
            width: 100% !important;
        }

        .light-style .account-settings-links .list-group-item {
            padding: 0.85rem 1.5rem;
            border-color: rgba(24, 28, 33, 0.03) !important;
        }

        .light-style .account-settings-links .list-group-item.active {
            color: #4e5155 !important;
        }

        .material-style .account-settings-links .list-group-item {
            padding: 0.85rem 1.5rem;
            border-color: rgba(24, 28, 33, 0.03) !important;
        }

        .material-style .account-settings-links .list-group-item.active {
            color: #4e5155 !important;
        }

        .dark-style .account-settings-links .list-group-item {
            padding: 0.85rem 1.5rem;
            border-color: rgba(255, 255, 255, 0.03) !important;
        }

        .dark-style .account-settings-links .list-group-item.active {
            color: #fff !important;
        }

        .light-style .account-settings-links .list-group-item.active {
            color: #4E5155 !important;
        }

        .light-style .account-settings-links .list-group-item {
            padding: 0.85rem 1.5rem;
            border-color: rgba(24, 28, 33, 0.03) !important;
        }

        .withdraw-form label {
            font-weight: bold;
            margin-top: 15px;
        }

        .withdraw-form input,
        .withdraw-form select {
            width: 100%;
            padding: 10px;
            margin-top: 5px;
            margin-bottom: 15px;
            border: 1px solid #ced4da;
            border-radius: 4px;
        }

        .withdraw-form button.btn-update {
            background-color: #26B4FF;
            color: #fff;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-weight: bold;
        }

        .withdraw-form button.btn-update:hover {
            background-color: #1e90ff;
        }
    </style>

         <meta charset="utf-8">
  <meta content="width=device-width, initial-scale=1.0" name="viewport">
  <title>Payment Setting</title>
</head>
<% 
    User u = (User) session.getAttribute("u");
    if (u == null) {
%>
    <p>User not found in session.</p>
<% 
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
              
              <a id="history" class="list-group-item list-group-item-action " data-toggle="list" href="transaction">Transaction history</a>                      
              <a id="pay" class="list-group-item list-group-item-action active" data-toggle="list" href="#">Payment Setting</a>
              <a id="wallet" class="list-group-item list-group-item-action " data-toggle="list" href="wallet">Wallet</a>      
              </div>
              </div>
                <div class="col-md-9">
                    <div class="container">
                         <%
                                Bank bank = (Bank)request.getAttribute("Bank");
                                String mess = (String) request.getAttribute("mess");
                            %>
                        <h2>Cài đặt thanh toán</h2>
                        <form class="withdraw-form row" method="post">
                            <div class="col-md-6">
                                <label for="bankName">Cổng thanh toán:</label>
                                <select id="bankName" name="bankName" required>
                                    <option selected="" disabled="">--- Chọn ngân hàng ---</option>
                                        <option value="Vietcombank" <%=(bank != null && bank.getBankType().contains("Vietcombank")) ? "selected" : ""%>>Vietcombank</option>
                                        <option value="Vietinbank" <%=(bank != null && bank.getBankType().contains("Vietinbank")) ? "selected" : ""%>>Vietinbank</option>
                                        <option value="BIDV" <%=(bank != null && bank.getBankType().contains("BIDV")) ? "selected" : ""%>>BIDV</option>
                                        <option value="Sacombank" <%=(bank != null && bank.getBankType().contains("Sacombank")) ? "selected" : ""%>>Sacombank</option>
                                        <option value="Á Châu" <%=(bank != null && bank.getBankType().contains("Á Châu")) ? "selected" : ""%>>Á Châu</option>
                                        <option value="MBBank" <%=(bank != null && bank.getBankType().contains("MBBank")) ? "selected" : ""%>>MBBank</option>
                                        <option value="Techcombank" <%=(bank != null && bank.getBankType().contains("Techcombank")) ? "selected" : ""%>>Techcombank</option>
                                        <option value="DongA" <%=(bank != null && bank.getBankType().contains("DongA")) ? "selected" : ""%>>Đông Á</option>
                                        <option value="VP bank" <%=(bank != null && bank.getBankType().contains("VP bank")) ? "selected" : ""%>>VP bank</option>
                                        <option value="Eximbank" <%=(bank != null && bank.getBankType().contains("Eximbank")) ? "selected" : ""%>>Eximbank</option>
                                        <option value="TP bank" <%=(bank != null && bank.getBankType().contains("TP bank")) ? "selected" : ""%>>TP bank</option>
                                        <option value="Ocean bank" <%=(bank != null && bank.getBankType().contains("Ocean bank")) ? "selected" : ""%>>Ocean bank</option>
                                        <option value="OCB" <%=(bank != null && bank.getBankType().contains("OCB")) ? "selected" : ""%>>OCB</option>
                                        <option value="SHBank" <%=(bank != null && bank.getBankType().contains("SHBank")) ? "selected" : ""%>>SHBank</option>
                                    </select>
                                <label for="bankAccountName">Chủ tài khoản:</label>
                                <input style="text-align: left" type="text" name="bankAccountName" required placeholder="Ví dụ: NGUYEN VAN A" maxlength="100" autocomplete="false" value="<%=bank != null ? bank.getBankName() : ""%>">
                                <label for="bankAccountNumber">Số tài khoản:</label>
                                 <input style="text-align: left" type="text" name="bankAccountNumber" required placeholder="Ví dụ: 0123456789" maxlength="100" autocomplete="false" value="<%=bank != null ? bank.getBankNo() : ""%>">
                                <button type="submit" class="btn-update">Cập nhật</button>
                                            <% if (mess != null) { %>
                <div class="alert <%=(mess.contains("thành công") ? "alert-success" : "alert-danger")%> mt-3">
                    <%= mess %>
                </div>
            <% } %>
                            </div>
                        </form>
                                
                    </div>
                </div>
            </div>
        </div>
    </div>
                                 



    <!-- Footer End -->

        <a href="#" class="btn btn-lg btn-primary btn-lg-square back-to-top"><i class="bi bi-arrow-up"></i></a>

    </body>
</html>
