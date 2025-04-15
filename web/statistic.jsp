<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="dal.UserDAO" %>
<%@page import="model.Skill, java.util.ArrayList, model.User, java.text.SimpleDateFormat, model.Mentor, model.Mentee, model.MenteeStatistic, model.MentorStatistic" %>

<!DOCTYPE html>
<html lang="en">

    <head>
         <style>
        /* General Body Styling */
        body {
            background-color: #f4f6f9;
            font-family: 'Poppins', sans-serif;
            margin-top: 20px;
            color: #333;
            line-height: 1.6;
        }

        h4, h3 {
            color: #4E5155;
            font-weight: 600;
        }

        p {
            font-size: 16px;
            line-height: 1.6;
            margin-bottom: 1rem;
        }

        /* Card Styling */
        .card {
            display: flex;
            flex-direction: column;
            background-color: #fff;
            border-radius: 10px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
            margin-bottom: 1.5rem;
            padding: 20px;
        }

        .col-md-6 {
            padding: 20px;
        }

        /* List Group Styling */
        .list-group-item {
            padding: 12px 18px;
            font-size: 16px;
            color: #555;
            background-color: #fff;
            border: none;
            transition: background-color 0.3s, color 0.3s;
            cursor: pointer;
        }

        .list-group-item:hover {
            background-color: #f4f4f4;
            color: #26B4FF;
        }

        .list-group-item.active {
            background-color: #26B4FF;
            color: #fff;
            font-weight: bold;
        }

        /* Buttons */
        .btn {
            padding: 10px 16px;
            font-size: 14px;
            border-radius: 4px;
            transition: background-color 0.3s ease, color 0.3s ease;
        }

        .btn:hover {
            background-color: #0056b3;
            color: #fff;
        }

        .btn-facebook, .btn-instagram {
            color: #fff;
            font-size: 18px;
            padding: 10px 20px;
            border-radius: 50px;
            margin-right: 10px;
        }

        .btn-facebook {
            background-color: #3B5998;
        }

        .btn-instagram {
            background-color: #e4405f;
        }

        /* Inputs */
        input, select, textarea {
            width: 100%;
            padding: 8px 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            background-color: #f8f9fa;
            margin-bottom: 15px;
        }

        /* Hover effect for statistics list */
        .statistics-list p:hover {
            background-color: #e9ecef;
            border-radius: 5px;
            padding-left: 10px;
            transition: 0.3s ease;
        }

        /* Responsive */
        @media (max-width: 768px) {
            .card, .list-group-item {
                margin-bottom: 10px;
            }
        }
    </style>
         <meta charset="utf-8">
  <meta content="width=device-width, initial-scale=1.0" name="viewport">
  <title>Statictic</title>

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

    <h4 class="font-weight-bold py-3 mb-4">
      TÀI KHOẢN
    </h4>

    <div class="card overflow-hidden">
  <div class="row no-gutters row-bordered row-border-light">
      <div class="col-md-3 pt-0">
          <div class="list-group list-group-flush account-settings-links">                  
              <a id="general" class="list-group-item list-group-item-action active" data-toggle="list" href="#">General</a>
              <%if(u.getRole().equalsIgnoreCase("Mentor")) {%>
              <a id="statistics" class="list-group-item list-group-item-action " data-toggle="list" href="#">Request statistics</a>
              <%}else {%>
              <a id="statistics" class="list-group-item list-group-item-action " data-toggle="list" href="#">Request statistics</a>
              <%}%>              
              <a id="history" class="list-group-item list-group-item-action " data-toggle="list" href="transaction">Transaction history</a>                      
              <a id="pay" class="list-group-item list-group-item-action " data-toggle="list" href="bank">Payment Setting</a>
              <a id="wallet" class="list-group-item list-group-item-action " data-toggle="list" href="wallet">Wallet</a>           
                              
          </div>
      </div>
        <div class="col-md-9">
            <div class="tab-content">
                 
                 <h3>Thống Kê Request</h3>
                            <%  if(u.getRole().equalsIgnoreCase("Mentee")) {
                                MenteeStatistic ms = (MenteeStatistic)request.getAttribute("mstatistic");%>
                                <div class="col-md-6">
                                    <div>
                                        <p>Tổng số Request đã gửi: <span style="color:black; font-weight: bold; text-transform: none"><%=ms.getTotalRequest()%> requests</span></p>
                                    </div>
                                    <hr>
                                    <div>
                                        <p>Tổng số Request bị từ chối: <span style="color:black; font-weight: bold; text-transform: none"><%=ms.getRejectedRequest()%> requests</span></p>
                                    </div>
                                    <hr>
                                    <div>
                                        <p>Tổng số Request được chấp thuận: <span style="color:black; font-weight: bold; text-transform: none"><%=ms.getAcceptedRequest()%> requests</span></p>
                                    </div>
                                    <hr>
                                    <div>
                                        <p class="control-label">Tổng thời gian học: <span style="color:black; font-weight: bold; text-transform: none"><%=ms.getTotalHours()%> giờ</span></p>
                                    </div>
                                    <hr>
                                    <div>
                                        <p>Tổng số Mentor yêu cầu: <span style="color:black; font-weight: bold; text-transform: none"><%=ms.getTotalMentor()%> mentors</span></p>
                                    </div>
                                </div>
                                    <%} else {
                                    MentorStatistic ms = (MentorStatistic)request.getAttribute("mstatistic");
                                    %>
                                <div class="col-md-6">
                                    <div>
                                        <p>Tổng số Request nhận được: <span style="color:black; font-weight: bold; text-transform: none"><%=ms.getInvitedRequest()%> requests</span></p>
                                    </div>
                                    <hr>
                                    <div>
                                        <p>Tổng số Request đã từ chối: <span style="color:black; font-weight: bold; text-transform: none"><%=ms.getRejectedRequest()%> requests</span></p>
                                    </div>
                                    <hr>
                                    <div>
                                        <p>Tỉ lệ từ chối Request: <span style="color:black; font-weight: bold; text-transform: none"><%=ms.getRejectPercent() * 100%> %</span></p>
                                    </div>
                                    <hr>
                                    <div>
                                        <p>Tổng số Request đã chấp thuận: <span style="color:black; font-weight: bold; text-transform: none"><%=ms.getAccepedRequest()%> requests</span></p>
                                    </div>
                                    <hr>
                                    <div>
                                        <p>Đánh Giá từ học viên: <span style="color:black; font-weight: bold; text-transform: none"><%=ms.getRating()%> sao</span></p>
                                    </div>
                                    <hr>
                                    <div>
                                        <p>Tỉ lệ hoàn thành request: <span style="color:black; font-weight: bold; text-transform: none"><%=ms.getCompletePercent()*100%> %</span></p>
                                    </div>
                                </div>
                                    <% }%>
        </div>
      </div>
    </div>

    

  </div>


    <!-- Footer End -->


    <!-- Back to Top -->
    <a href="#" class="btn btn-lg btn-primary btn-lg-square back-to-top"><i class="bi bi-arrow-up"></i></a>

</body>

</html>
