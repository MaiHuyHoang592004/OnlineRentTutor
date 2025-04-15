<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Bank, model.User" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    User u = (User) session.getAttribute("u");
    if (u == null) {
        response.sendRedirect("Login.jsp");
        return;
    }
    Bank bank = (Bank) request.getAttribute("Bank");
    String alertMessage = (String) session.getAttribute("alert");
    if (alertMessage != null) {
        session.removeAttribute("alert"); // Remove it after displaying
    }
%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Wallet</title>
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/style.css" rel="stylesheet">

        <style>
            /* Wallet Container */
            .wallet-container {
                max-width: 900px;
                margin: 40px auto;
                padding: 20px;
                background: #fff;
                border-radius: 12px;
                box-shadow: 0px 2px 10px rgba(0, 0, 0, 0.1);
            }

            /* Wallet Balance */
            .wallet-balance {
                display: flex;
                justify-content: space-between;
                align-items: center;
                background: #fff;
                padding: 20px;
                border-radius: 10px;
                box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
            }

            .wallet-item {
                text-align: center;
                flex: 1;
                padding: 15px;
            }

            .wallet-item:not(:last-child) {
                border-right: 2px solid #eee;
            }

            .wallet-item span {
                display: block;
                font-size: 16px;
                color: #555;
                font-weight: 500;
            }

            .wallet-item .amount {
                font-size: 28px;
                font-weight: bold;
                color: #ff4500;
            }

            /* Action Buttons */
            .wallet-actions {
                display: flex;
                justify-content: center;
                margin-top: 20px;
            }

            .wallet-btn {
                display: flex;
                flex-direction: column;
                align-items: center;
                margin: 0 15px;
                text-align: center;
                font-size: 16px;
                font-weight: bold;
                cursor: pointer;
                transition: all 0.3s ease-in-out;
                padding: 12px 20px;
                border-radius: 8px;
                border: none;
            }

            .wallet-btn i {
                font-size: 24px;
                margin-bottom: 5px;
            }

            .wallet-btn.recharge {
                background: #28a745;
                color: #fff;
            }

            .wallet-btn.withdraw {
                background: #dc3545;
                color: #fff;
            }

            .wallet-btn:hover {
                opacity: 0.85;
            }

            /* Responsive */
            @media (max-width: 768px) {
                .wallet-balance {
                    flex-direction: column;
                    text-align: center;
                }

                .wallet-item:not(:last-child) {
                    border-right: none;
                    border-bottom: 2px solid #eee;
                }

                .wallet-actions {
                    flex-direction: column;
                    align-items: center;
                }

                .wallet-btn {
                    margin-bottom: 10px;
                }
            }
        </style>
    </head>
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
                            <a id="history" class="list-group-item list-group-item-action" href="transaction">Transaction history</a>
                            <a id="pay" class="list-group-item list-group-item-action" href="bank">Payment Setting</a>
                            <a id="wallet" class="list-group-item list-group-item-action active" href="#">Wallet</a>
                        </div>
                    </div>

                    <div class="col-lg-9 col-md-9 col-sm-12 col-xs-12">
                        <div class="aside">
                            <% if (bank == null) { %>
                            <h3>Bạn Cần Tạo Ví Trong Mục Thanh Toán Trước</h3>
                            <% } else { %>
                            <h3>Ví Của Bạn</h3>

                            <!-- Wallet Balance Display -->
                            <div class="wallet-container">
                                <div class="wallet-balance">
                                    <div class="wallet-item">
                                        <span>Số dư hiện tại</span>
                                        <div class="amount"><%= u.getWallet() %> vnđ</div>
                                    </div>
                                    <div class="wallet-item">
                                        <span>Số dư khả dụng</span>
                                        <div class="amount"><%= u.getWallet() - ((session.getAttribute("holdMoney") != null) ? (Double) session.getAttribute("holdMoney") : 0) %> vnđ</div>
                                    </div>
                                </div>

                                <!-- Buttons for Recharge & Withdraw -->
                                <div class="wallet-actions">
                                    <a href="Recharge.jsp" class="wallet-btn recharge">
                                        <i class="fas fa-wallet"></i> Nạp Tiền
                                    </a>
                                    <a href="wallet?type=withdraw" class="wallet-btn withdraw">
                                        <i class="fas fa-money-bill-alt"></i> Rút Tiền
                                    </a>
                                </div>
                            </div>
                            <% } %>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Success Alert -->
        <% if (alertMessage != null) { %>
        <div class="alert alert-success alert-dismissible fade show text-center" role="alert">
            <%= alertMessage %>
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>

        <!-- Auto Hide Alert -->
        <script>
            setTimeout(() => {
                document.querySelector(".alert").style.display = "none";
            }, 4000);
        </script>
        <% } %>

        <!-- JavaScript Libraries -->
        <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
