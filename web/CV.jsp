<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Skill, java.util.ArrayList, model.User, java.text.SimpleDateFormat, model.Mentor, model.CV, dal.CVDAO, dal.MentorDAO , dal.UserDAO" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="en">
     <head>
        <title>Eduport - LMS, Education and Course Theme</title>
    </head>
     <style>
            body {
                background: #f7f7ff; /* Lấy giá trị mới nhất */
              
            }

            .ui-w-80 {
                width: 80px !important;
                height: auto;
            }

            .btn-default {
                border-color: rgba(24,28,33,0.1);
                background: rgba(0,0,0,0);
                color: red;
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
                border-color: rgba(0,0,0,0);
                background: #3B5998;
                color: #fff;
            }

            .btn-instagram {
                border-color: rgba(0,0,0,0);
                background: #000;
                color: #fff;
            }

            .card {
                position: relative;
                display: flex;
                flex-direction: column;
                min-width: 0;
                word-wrap: break-word;
                background-color: #fff;
                background-clip: border-box;
                border: 0 solid transparent;
                border-radius: .25rem;
                margin-bottom: 1.5rem;
                box-shadow: 0 2px 6px 0 rgb(218 218 253 / 65%), 0 2px 6px 0 rgb(206 206 238 / 54%);
            }

            .me-2 {
                margin-right: .5rem!important;
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

            .account-settings-multiselect ~ .select2-container {
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

            .modal {
                display: none;
                position: fixed;
                z-index: 1;
                left: 0;
                top: 0;
                width: 100%;
                height: 100%;
                overflow: auto;
                background-color: rgba(0,0,0,0.4);
            }

            .modal-content {
                background-color: #fefefe;
                margin: 15% auto;
                padding: 20px;
                border: 1px solid #888;
                width: 80%;
            }
            .modal-content-cv {
                margin: 15% auto;
                padding: 20px;
                position: relative;
                display: flex;
                flex-direction: column;
                width: 50%;
                margin-bottom: 100px;
                pointer-events: auto;
                background-color: #fff;
                background-clip: padding-box;
                border: 1px solid rgba(0,0,0,0.2);
                border-radius: .3rem;
                outline: 0;
                margin-top: 100px;
            }

            .centered-form {
                display: flex;
                flex-direction: column;
                align-items: center;
                justify-content: center;
                width: 300px;
                margin: 0 auto;
                padding: 20px;
                border: 1px solid #ccc;
                border-radius: 10px;
            }

            .input-field {
                width: 100%;
                margin-bottom: 10px;
            }

            .input-field input {
                width: 100%;
                padding: 10px;
                border: 1px solid #ccc;
                border-radius: 5px;
            }

            .centered-text {
                text-align: center;
                margin-bottom: 10px;
            }

            .margin-div {
                margin: 10px 0;
            }

            .submit-button {
                background-color: #4CAF50;
                color: white;
                padding: 15px 32px;
                text-align: center;
                text-decoration: none;
                display: inline-block;
                font-size: 16px;
                margin: 4px 2px;
                cursor: pointer;
                border: none;
                border-radius: 5px;
            }


.profile {
    margin-bottom: 25px;
}
.profile .jobster-user-info {
    display: inline-block;
    width: 100%;
    height: 100%;
}
.profile .jobster-user-info .profile-avatar {
    position: relative;
    
    
}
.profile .jobster-user-info .profile-avatar img {
    border-radius: 100%;
}
.profile .jobster-user-info .profile-avatar i {
    font-size: 16px;
    color: #21c87a;
    position: absolute;
    background: #ffffff;
    border-radius: 100%;
    cursor: pointer;
    height: 100px;
    width: 100px;
    line-height: 30px;
    text-align: center;
    bottom: 20px;
    right: -5px;
}

.about-candidate {
    padding: 25px 0px;
}
.about-candidate .candidate-info {
    margin-bottom: 20px;
}

.resume-experience {
    padding-left: 10px;
   margin-top: -1000px;
    padding-bottom: 60px;
    position: relative;
    padding-right: 50px;
   
}
.resume-experience:before {
    position: absolute;
    
    right: 0;
    width: 100%;
    height: 100%;
    background: #f6f6f6;
    content: "";
    z-index: -1;
    top: 0;
}
.resume-experience .jobster-candidate-timeline .jobster-timeline-item .jobster-timeline-cricle {
    border-color: #f6f6f6;
}

.user-dashboard-info-box .select2-container--default .select2-selection--single .select2-selection__rendered {
    font-weight: bold;
    color: #626262;
}

@media (max-width: 1199px) {
    .secondary-menu ul li a {
        padding: 10px 15px;
    }
}

@media (max-width: 991px) {
    .resume-experience {
        padding-left: 15px;
        padding-top: 30px;
        padding-bottom: 30px;
        padding-right: 15px;
    }
    .resume-experience:before {
        content: none;
    }
    .secondary-menu ul li {
        display: inline-block;
    }
}

@media (max-width: 575px) {
    .secondary-menu ul li a {
        padding: 4px 8px;
    }
}

/*****************************
    Progress Bar
*****************************/
.progress {
    position: relative;
    overflow: inherit;
    height: 3px;
    margin: 40px 0px 15px;
    width: 100%;
    display: inline-block;
}
.progress .progress-bar {
    height: 3px;
    background: #21c87a;
}
.progress .progress-bar-title {
    position: absolute;
    left: 0;
    top: -20px;
    color: #212529;
    font-size: 14px;
    font-weight: 600;
}
.progress .progress-bar-number {
    position: absolute;
    right: 0;
    color: #646f79;
    top: -20px;
}


/* Jobster Candidate */
.jobster-candidate-timeline {
    position: relative;
}
.jobster-candidate-timeline:before {
    content: "";
    position: absolute;
    left: 20px;
    width: 2px;
    top: 5px;
    bottom: 5px;
    height: calc(100% - 5px);
    background-color: #eeeeee;
}

.jobster-candidate-timeline .jobster-timeline-item {
    display: table;
    position: relative;
    margin-bottom: 20px;
    width: 100%;
}

.jobster-candidate-timeline .jobster-timeline-item .jobster-timeline-cricle {
    border-radius: 50%;
    border: 12px solid white;
    z-index: 1;
    top: 5px;
    left: 9px;
    position: absolute;
}
.jobster-candidate-timeline .jobster-timeline-item .jobster-timeline-cricle:before {
    content: "";
    position: absolute;
    left: 12px;
    width: 20px;
    top: -1px;
    bottom: 5px;
    height: 2px;
    background-color: #eeeeee;
}
.jobster-candidate-timeline .jobster-timeline-item .jobster-timeline-cricle > i {
    font-size: 15px;
    top: -8px;
    left: -7px;
    position: absolute;
    color: #21c87a;
}

.jobster-candidate-timeline .jobster-timeline-item .jobster-timeline-info {
    display: table-cell;
    vertical-align: top;
    padding: 5px 0 0 70px;
}
.jobster-candidate-timeline .jobster-timeline-item .jobster-timeline-info h6 {
    color: #21c87a;
    margin: 5px 0 0px;
}
.jobster-candidate-timeline .jobster-timeline-item .jobster-timeline-info span {
    color: #212529;
    font-size: 13px;
    font-weight: 500;
}

.jobster-candidate-timeline span.jobster-timeline-time {
    color: #646f79 !important;
}

.jobster-candidate-timeline .jobster-timeline-icon {
    border: 2px solid #eeeeee;
    width: 42px;
    height: 42px;
    border-radius: 50%;
    line-height: 42px;
    text-align: center;
    background: #ffffff;
    position: relative;
    margin-bottom: 20px;
}
.jobster-candidate-timeline .jobster-timeline-icon i {
    font-size: 16px;
    color: #212529;
}

.select2-container--default .select2-selection--single .select2-selection__arrow {
    top: 16px;
}
    </style>
 <%@include file="MentorHeader.jsp" %>

    <body>
     <%
    User u = (User) request.getAttribute("u");
    Mentor m = (Mentor) request.getAttribute("mentor");
    CV cv = (CV) request.getAttribute("cv");
    List<Skill> skills = (List<Skill>) request.getAttribute("skills");
%>


        <div class="container light-style flex-grow-1 container-p-y">
            <h4 class="font-weight-bold py-3 mb-4">
                UPDATE CV OF MENTOR
            </h4>

           
                    <div class="card overflow-hidden">
    <div class="row no-gutters row-bordered row-border-light">
        <div class="col-md-3 pt-0">
            <div class="list-group list-group-flush account-settings-links">
                <a id="general" class="list-group-item list-group-item-action active" data-toggle="list" href="#">General</a>
                <a id="cv" class="list-group-item list-group-item-action" data-toggle="list" href="#">CV</a>
                <a id="statistics" class="list-group-item list-group-item-action" data-toggle="list" href="#">Request statistics</a>
                <a id="password" class="list-group-item list-group-item-action" data-toggle="list" href="setting">Change password</a>
                <a id="history" class="list-group-item list-group-item-action" data-toggle="list" href="#">Transaction history</a>
                <a id="pay" class="list-group-item list-group-item-action" data-toggle="list" href="#">Pay</a>
                <a id="wallet" class="list-group-item list-group-item-action" data-toggle="list" href="#">Wallet</a>
            </div>
        </div>
        <div class="col-md-9">
            <div class="tab-content">
                <div class="container">
                    <div class="main-body">
                        <div class="row">
                            <div class="col-lg-4">
                                <div class="card">
                                    <div class="card-body">
                                        <hr>
                                        <div class="d-flex flex-column align-items-center text-center">
                                            <a class="avatar avatar-sm p-0" href="#" id="profileDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                                <img class="avatar-img rounded-circle" src="https://cdn4.iconfinder.com/data/icons/instagram-ui-twotone/48/Paul-18-512.png" alt="avatar">
                                            </a>
                                            <div class="mt-3">
                                                <h4><%= u.getFullname() %></h4>
                                                <p class="text-secondary mb-1"><%= u.getPhoneNumber() %></p>
                                                <p class="text-secondary mb-1"><%= u.getEmail() %></p>
                                                <p class="text-muted font-size-sm"><%= u.getAddress() %></p>
                                                <p class="text-muted font-size-sm"><%= u.getDob() %></p>
                                                <% if (cv != null) { %>
                                                    <p class="text-muted font-size-sm">Money of slot: <%= cv.getCashPerSlot() %></p>
                                                <% } %>
                                            </div>
                                            <% if (cv != null) { %>
                                                <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#formModalPrice" onclick="showPricePopup()">
                                                    Change price of slot
                                                </button>
                                            <% } %>
                                        </div>
                                        <hr>
                                        <c:if test="${param.error != null}">
                                            <script>
                                                setTimeout(() => { alert('${param.error}'); }, 2000);
                                            </script>
                                        </c:if>
                                        <c:if test="${param.success != null}">
                                            <script>
                                                setTimeout(() => { alert('${param.success}'); }, 2000);
                                            </script>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-8">
                                <div class="card">
                                    <h3 style="margin-left: 10px">Thành Tựu</h3>
                                    <form action="./cv" method="post">
                                        <div class="card-body">
                                            <div class="row mb-3">
                                                <div class="col-sm-3"><h6 class="mb-0">Description:</h6></div>
                                                <div class="col-sm-9 text-secondary">
                                                    <textarea class="form-control" name="description" id="description">${mentor.description != null ? mentor.description : ''}</textarea>
                                                </div>
                                            </div>
                                            <div class="row mb-3">
                                                <div class="col-sm-3"><h6 class="mb-0">Achievement:</h6></div>
                                                <div class="col-sm-9 text-secondary">
                                                    <textarea class="form-control" name="achivement" id="achivement">${mentor.achivement != null ? mentor.achivement : ''}</textarea>
                                                </div>
                                            </div>
                                            <div class="row mb-3">
                                                <button class="form-control btn-update" type="submit">Update</button>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                                <div class="card">
                                    <h3 style="margin-left: 10px">CV</h3>
                                    <% if (cv == null) { %>
                                        <h2 style="margin-left: 250px" class="control-label">Bạn Chưa Có CV</h2>
                                    <% } else { %>
                                        <form method="post">
                                            <input type="hidden" name="type" value="sendToAdmin">
                                            <input type="hidden" name="id" value="<%= cv.getId() %>">
                                            <div class="card-body">
                                                <div class="row mb-3">
                                                    <div class="col-sm-3"><h6 class="mb-0">Profession Introduction:</h6></div>
                                                    <div class="col-sm-9 text-secondary">
                                                        <textarea class="form-control" readonly name="profession"><%= cv.getProfessionIntroduction() == null ? "" : cv.getProfessionIntroduction() %></textarea>
                                                    </div>
                                                </div>
                                                <div class="row mb-3">
                                                    <div class="col-sm-3"><h6 class="mb-0">Description:</h6></div>
                                                    <div class="col-sm-9 text-secondary">
                                                        <textarea class="form-control" readonly name="descrip"><%= cv.getServiceDescription() == null ? "" : cv.getServiceDescription() %></textarea>
                                                    </div>
                                                </div>
                                            </div>
                                                    <div class="row mb-3">
                                                                    <div class="col-sm-3">
                                                                        <h6 class="mb-0">Skills:</h6>
                                                                    </div>
                                                                    <div class="col-sm-9 text-secondary">
                                                                        <%for(int i = 0; i < cv.getSkills().size(); i++) {%>
                                                                        <div class="choose-game" style="background:  center center no-repeat;margin: 0 8px 6px 0;border-radius: 10px;float: left;min-width: 100px;text-align: center;">
                                                                            <p class="overlay" style="text-shadow: 2px 0 0 #000;margin: 0;padding: 13px 16px;color: #fff;font-weight: 700;font-size: 13px;background: rgba(0,0,0,.75);border-radius: 10px;text-transform: capitalize;"><%=cv.getSkills().get(i).getName()%> </p>
                                                                        </div><%}%>
                                                                    </div>
                                                                </div>
                                            <% if (m != null && "Draft".equals(m.getMentorStatus())) { %>
                                                <button onclick="return confirm('Are you sure to send admin?')" class="btn btn-danger" type="submit">
                                                    Submit
                                                </button>
                                            <% } %>
                                            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#showCVDrarf" onclick="showCVDrarfPopUp()">
                                                                    Update CV
                                                                </button>
                                        </form>
                                    <% } %>
                                    <% if (cv == null) { %>
                                        <button class="form-control" id="createCV">Create CV</button>
                                    <% } %>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

        <% if(cv != null) { %>
        <div class="modal fade" id="formModalPrice" tabindex="-1" aria-labelledby="formModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="formModalLabel">Change price of slot</h5>
                    </div>
                    <div class="modal-body">
                        <form method="post" action="change-money">
                            <input type="hidden" name="type" value="update">
                            <input class="form-control" type="hidden" name="cvId" min="1" required value="<%=cv.getId()%>">
                            <div class="card-body">
                                <div class="row mb-3 p-0">
                                    <div class="col-sm-3 p-0">
                                        <h6 class="mb-0">Price of slot</h6>
                                    </div>
                                    <div class="col-sm-12 p-0">
                                        <input class="form-control" type="number" name="moneyOfSlot" min="1" required value="<%=cv.getCashPerSlot()%>">
                                    </div>
                                </div>
                                <div class="row mb-3">
                                    <button class="form-control" type="submit" class="btn btn-info btn-update">Save change</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <% } %>
        <%
            if(cv != null && m != null && ("Reject".equals(m.getMentorStatus()) || "Draft".equals(m.getMentorStatus()) || "Pending".equals(m.getMentorStatus()) || "Accepted".equals(m.getMentorStatus()))) {
        %>
        <div class="modal fade" id="showCVDrarf" tabindex="-1" aria-labelledby="formModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="formModalLabel">Update CV</h5>
                    </div>
                    <div class="modal-body">
                        <form method="post">
                            <input type="hidden" name="type" value="update">
                            <div class="card-body">
                                <div class="row mb-3">
                                    <div class="col-sm-3">
                                        <h6 class="mb-0">Profession Introduction:</h6>
                                    </div>
                                    <div class="col-sm-9 text-secondary">
                                        <textarea class="form-control" name="profession" placeholder="" required maxlength="255" autocomplete="off"><%= cv.getProfessionIntroduction() == null ? "" : cv.getProfessionIntroduction() %></textarea>
                                    </div>
                                </div>
                                <div class="row mb-3">
                                    <div class="col-sm-3">
                                        <h6 class="mb-0">Description:</h6>
                                    </div>
                                    <div class="col-sm-9 text-secondary">
                                        <textarea class="form-control" name="service" placeholder="" required maxlength="255" autocomplete="off"><%= cv.getServiceDescription() == null ? "" : cv.getServiceDescription() %></textarea>
                                    </div>
                                </div>
                                <div class="col-sm-9 text-secondary">
                                    <input class="form-control" type="hidden" name="CashPerSlot" min="1" required value="<%=cv.getCashPerSlot()%>">
                                </div>
                                <div class="row mb-3">
                                    <div class="col-sm-3">
                                        <h6 class="mb-0">Skills:</h6>
                                    </div>
                                    <div class="col-sm-9 text-secondary">
                                        <%for(int i = 0; i < cv.getSkills().size(); i++) {%>
                                        <div class="choose-game" title="Nhấn để xóa skill" onclick="deleteSkill(<%=cv.getSkills().get(i).getId()%>)" style="background:  center center no-repeat;margin: 0 8px 6px 0;border-radius: 10px;float: left;min-width: 100px;text-align: center;">
                                            <p class="overlay" style="text-shadow: 2px 0 0 #000;margin: 0;padding: 13px 16px;color: #fff;font-weight: 700;font-size: 13px;background: rgba(0,0,0,.75);border-radius: 10px;text-transform: capitalize;"><%=cv.getSkills().get(i).getName()%> </p>
                                        </div><%}%>
                                    </div>
                                    <div class="col-sm-9 text-secondary">
                                        <select style="margin-bottom: 0px;" onchange="if (this.selectedIndex)
                                                    changeSelection(this);">
                                            <option selected disable>Thêm skill mới</option><%for(int i = 0; i < skills.size(); i++) {%> <option value="<%=skills.get(i).getId()%>"><%=skills.get(i).getName()%> </option><%}%>
                                        </select>
                                    </div>
                                </div>
                                <div class="row mb-3">
                                    <button style="height: 100% ; width: 30% ; margin-left: 300px" class="form-control" type="submit" class="btn-update" id="<%=(m == null || m.getCvID() == 0) ? "createCV" : "updateCV"%>">
                                        <%=(m == null || m.getCvID() == 0) ? "createCV" : "updateCV"%> 
                                    </button>
                                        <%=cv != null ? "</form>" : ""%>                   
                                </div>
                                
                            </div>
                        </form>  
                    </div>
                </div>
            </div>
        </div>
        <%
            }
        %>
        <div class="modal fade" id="rejectReasonModal" tabindex="-1" role="dialog" aria-labelledby="rejectReasonModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="rejectReasonModalLabel">Reject reason</h5>
                    </div>
                    <div class="modal-body" id="rejectReasonContent">
                    </div>
                </div>
            </div>
        </div>
        <script>
            function showRejectReason(reason) {
                document.getElementById('rejectReasonContent').innerText = reason;
                $('#rejectReasonModal').modal('show');
            }
        </script>
        <script>
            function showPricePopup() {
                $('#formModalPrice').modal('show');
            }
        </script>
        <script>
            function showCVDrarfPopUp() {
                $('#showCVDrarf').modal('show');
            }
        </script>
        <script>
            function deleteSkill(id) {
            <%if(cv != null && cv.getSkills().size() > 1) {%>
                let param = "type=delete&id=" + id;
                fetch("cv",
                        {
                            headers: {
                                'Content-Type': 'application/x-www-form-urlencoded'
                            },
                            method: "POST",
                            body: param
                        })
                setTimeout(function () {
                    window.location.reload();
                }, 100);
            <%} else {%>
                alert('Bạn phải có ít nhất 1 skill!');
            <%}%>
            }
            function changeSelection(select) {
                let param = "type=add&id=" + select.value;
                fetch("cv",
                        {
                            headers: {
                                'Content-Type': 'application/x-www-form-urlencoded'
                            },
                            method: "POST",
                            body: param
                        })
                setTimeout(function () {
                    window.location.reload();
                }, 100);
                //console.log(select.value);
            }

            let cog = document.getElementsByClassName('fas fa-cog')[0].parentNode.children[1];
            let collapse = cog.parentNode.parentNode.parentNode.parentNode.children[1];
            document.getElementsByClassName('fas fa-cog')[0].parentNode.onclick = function () {
                if (cog.classList.contains("fa-chevron-right")) {
                    cog.classList.add("fa-chevron-down");
                    cog.classList.remove("fa-chevron-right");
                    collapse.classList.remove("collapse");
                    collapse.classList.add("collapsing");
                    setTimeout(function () {
                        collapse.style = "height: 72px;";
                    }, 1);
                    setTimeout(function () {
                        collapse.classList.remove("collapsing");
                        collapse.classList.add("collapse");
                        collapse.style = "";
                        collapse.classList.add("in");
                    }, 300);
                } else {
                    cog.classList.remove("fa-chevron-down");
                    cog.classList.add("fa-chevron-right");
                    collapse.style = "height: 72px;";
                    collapse.classList.remove("collapse");
                    collapse.classList.add("collapsing");
                    setTimeout(function () {
                        collapse.style = "height: 0px;";
                    }, 1);
                    setTimeout(function () {
                        collapse.classList.remove("collapsing");
                        collapse.classList.add("collapse");
                        collapse.style = "height: 0px;";
                        collapse.classList.remove("in");
                    }, 300);
                }
            }
            function isValidUrl(string) {
                try {
                    new URL(string);
                    return true;
                } catch (err) {
                    return false;
                }
            }

        </script>
        <script>
            if (document.getElementById('createCV') != null) {
                document.getElementById('createCV').onclick = function () {
                    var modal = document.createElement('div');
                    modal.className = 'modal';
                    var form = document.createElement('form');
                    form.method = 'post';
                    form.className = 'modal-content-cv';
                    form.innerHTML = `
        <form method="post" class="centered-form">
         <div style="display: flex; align-items: center; justify-content: center; text-align: center;">
        <img style="height :50px ; width 50px" alt="logo playerduo" src="https://img.lovepik.com/free-png/20210926/lovepik-cartoon-book-png-image_401449837_wh1200.png" style="border-radius: 20%; margin-right: 10px;">
        <h1 style="margin: 5px;">CV</h1>
    </div>
            <div class="input-field">
                <span>Profession Introduction</span>
                <textarea style="width: 100%" type="text" name="profession" placeholder="" maxlength="500" autocomplete="false" required value=""></textarea>
            </div>
            <div class="input-field">
          <span>Service Description</span>
                <textarea style="width: 100%" type="text" name="service" placeholder="" required maxlength="500" autocomplete="false" value=""></textarea>
            </div>
            <div class="centered-text">
                <span>Chọn kĩ năng bạn dạy:</span>
            </div>
                <select name="skills" style="height: 100px" required multiple>
            <%for(int i = 0; i < skills.size(); i++) {%><option value="<%=skills.get(i).getId()%>"><%=skills.get(i).getName()%></option><%
        }%></select>
            <div class="margin-div"></div>
            <div class="centered-text">
                <input type="hidden" name="cash" step="0" min="0" value="0" placeholder="Giá Thuê Trên Slot">
            </div>
            <button type="submit" class="submit-button"><span>Tạo CV</span></button>
            <input type="hidden" name="type" value="create">
        </form>

        `;

                    modal.appendChild(form);
                    document.body.appendChild(modal);
                    modal.style.display = "block";
                    window.onclick = function (event) {
                        if (event.target == modal) {
                            modal.style.display = "none";
                        }
                    }
                }
            }
        </script>
        <script>
            // Chọn các phần tử link
            const statisticsLink = document.getElementById('statistics');
            const historyLink = document.getElementById('history');
            const payLink = document.getElementById('pay');
            const walletLink = document.getElementById('wallet');
            const generalLink = document.getElementById('general');
            const cvLink = document.getElementById('cv');

            // Thêm sự kiện click cho từng link
            statisticsLink.addEventListener('click', function () {
                window.location.href = 'statistics';
            });

            historyLink.addEventListener('click', function () {
                window.location.href = 'transaction';
            });

            payLink.addEventListener('click', function () {
                window.location.href = 'bank';
            });

            walletLink.addEventListener('click', function () {
                window.location.href = 'wallet';
            });

            cvLink.addEventListener('click', function () {
                window.location.href = 'cv';
            });
            generalLink.addEventListener('click', function () {
                window.location.href = 'profile';
            });

        </script>

        <script src="assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
        <script src="assets/vendor/php-email-form/validate.js"></script>
        <script src="assets/vendor/aos/aos.js"></script>
        <script src="assets/vendor/glightbox/js/glightbox.min.js"></script>
        <script src="assets/vendor/purecounter/purecounter_vanilla.js"></script>
        <script src="assets/vendor/swiper/swiper-bundle.min.js"></script>

        <!-- Main JS File -->
        <script src="assets/js/main.js"></script>
        <!-- JavaScript Libraries -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <!-- Bootstrap JS -->
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <script src="lib/wow/wow.min.js"></script>
        <script src="lib/easing/easing.min.js"></script>
        <script src="lib/waypoints/waypoints.min.js"></script>
        <script src="lib/owlcarousel/owl.carousel.min.js"></script>

        <!-- Template Javascript -->
        <script src="js/main.js"></script>
        <%@include file="Footer.jsp" %>%>
    </body>
</html>