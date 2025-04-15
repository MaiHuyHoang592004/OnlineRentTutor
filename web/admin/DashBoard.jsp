<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Panel</title>
        <!-- Bootstrap & FontAwesome -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <script
            src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.4/Chart.js">
        </script>
        <style>
            .sidebar {
                min-height: 100vh; /* Make sidebar full height */
                background-color: #b3d0ff;

            }
            .content {
                flex-grow: 1; /* Allows content to take remaining space */
                padding: 20px;
            }
            .header {
                display: flex;
                align-items: center; /* Align items vertically */
                justify-content: flex-end; /* Align content to the left */
                gap: 15px; /* Space between elements */
                padding: 10px; /* Add some spacing */
                background-color: aqua;
                border-radius: 27px;
                margin: 15px 0;
            }

            .header i {
                font-size: 24px; /* Increase icon size */
                cursor: pointer;
            }

            .profile {
                width: 40px;
                height: 40px;
                border-radius: 50%;
                background-color: #ffffff;
                border: 2px solid #ddd; /* Optional: border for visibility */
            }

            .active {
                background-color: cyan;
            }


            .overlay {
                position: fixed;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background: rgba(0, 0, 0, 0.5); /* Dim effect */
                z-index: 10; /* Behind the modal */
                display: none; /* Initially hidden */
            }

            .modal {
                position: fixed;
                top: 50%;
                left: 50%;
                transform: translate(-50%, -50%);
                width: 70%;
                height: 60%;
                background: white;
                z-index: 11; /* On top of the overlay */
                display: none;
                padding: 20px;
                border-radius: 8px;
            }


        </style>
    </head>

    <body>
        <div class="overlay" id="overlay">

        </div>

        <div class="d-flex">
            <!-- Sidebar -->
            <div class="sidebar text-primary col-md-2 p-3">
                <h4 class="text-center">Admin Manage</h4>
                <ul class="nav flex-column">
                    <li class="nav-item active">
                        <button class="nav-link text-success" onclick="navigate('dashboard')">
                            <i class="fa-solid fa-chart-simple" style="color: #FFD43B;"></i> Dashboard
                        </button>
                    </li>
                    <li class="nav-item">
                        <button class="nav-link text-success" onclick="navigate('manageMentor?page=1')">
                            <i class="fa-solid fa-user" style="color: #FFD43B;"></i> Manage Mentor
                        </button>
                    </li>
                    <li class="nav-item">
                        <button class="nav-link text-success" onclick="navigate('manageMentee?page=1')">
                            <i class="fa-solid fa-user" style="color: #FFD43B;"></i> Manage Mentee
                        </button>
                    </li>
                    <li class="nav-item">
                        <button class="nav-link text-success" onclick="navigate('manageAccount?page=1')">
                            <i class="fa-solid fa-user" style="color: #FFD43B;"></i> Manage Account
                        </button>
                    </li>
                    <li class="nav-item">
                        <button class="nav-link text-success" onclick="navigate('manageSkill?page=1')">
                            <i class="fa-solid fa-graduation-cap" style="color: #FFD43B;"></i> Manage Subject
                        </button>
                    </li>
                    
                    <li class="nav-item">
                        <a href="${pageContext.request.contextPath}/logout" class="nav-link text-success">
                            <i class="fa-solid fa-right-from-bracket" style="color: #FFD43B;"></i> Logout
                        </a>
                    </li>
                </ul>
            </div>


            <div class="container-fluid">
                <div class="header">
                    <i class="fa-solid fa-bell"></i>
                    <i class="fa-solid fa-message"></i>
                    <div class="profile"></div>
                </div>


                <!-- Content Row -->
                <div class="row">
                    <div class="col-xl-3 col-md-6 mb-4">
                        <div class="card border-left-primary shadow h-100 py-2">
                            <div class="card-body">
                                <div class="row no-gutters align-items-center">
                                    <div class="col mr-2">
                                        <div class="text-xs font-weight-bold text-primary text-uppercase mb-1">
                                            Earnings (Monthly)</div>
                                        <div class="h5 mb-0 font-weight-bold text-gray-800">${requestScope.earnMonth}</div>
                                    </div>
                                    <div class="col-auto">
                                        <i class="fas fa-calendar fa-2x text-gray-300"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Earnings (Monthly) Card Example -->
                    <div class="col-xl-3 col-md-6 mb-4">
                        <div class="card border-left-success shadow h-100 py-2">
                            <div class="card-body">
                                <div class="row no-gutters align-items-center">
                                    <div class="col mr-2">
                                        <div class="text-xs font-weight-bold text-success text-uppercase mb-1">
                                            Earnings (Total)</div>
                                        <div class="h5 mb-0 font-weight-bold text-gray-800">${requestScope.earnAll}</div>
                                    </div>
                                    <div class="col-auto">
                                        <i class="fas fa-dollar-sign fa-2x text-gray-300"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Earnings (Monthly) Card Example -->
                    <div class="col-xl-3 col-md-6 mb-4">
                        <div class="card border-left-info shadow h-100 py-2">
                            <div class="card-body">
                                <div class="row no-gutters align-items-center">
                                    <div class="col mr-2">
                                        <div class="text-xs font-weight-bold text-info text-uppercase mb-1">Tasks
                                        </div>
                                        <div class="row no-gutters align-items-center">
                                            <div class="col-auto">
                                                <div class="h5 mb-0 mr-3 font-weight-bold text-gray-800">50%</div>
                                            </div>
                                            <div class="col">
                                                <div class="progress progress-sm mr-2">
                                                    <div class="progress-bar bg-info" role="progressbar"
                                                         style="width: 50%" aria-valuenow="50" aria-valuemin="0"
                                                         aria-valuemax="100">

                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-auto">
                                        <i class="fas fa-clipboard-list fa-2x text-gray-300"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Pending Requests Card Example -->
                    <div class="col-xl-3 col-md-6 mb-4">
                        <div class="card border-left-warning shadow h-100 py-2" onclick="showReportDetail()">
                            <div class="card-body">
                                <div class="row no-gutters align-items-center">
                                    <div class="col mr-2">
                                        <div class="text-xs font-weight-bold text-warning text-uppercase mb-1">
                                            Pending Report</div>
                                        <div class="h5 mb-0 font-weight-bold text-gray-800">${requestScope.reports.size()}</div>
                                    </div>
                                    <div class="col-auto">
                                        <i class="fas fa-comments fa-2x text-gray-300"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Hidden popup report -->
                <div id="reportDetailPopup" class="modal" tabindex="-1" style="display: none;">
                    <div class="model-dialog">
                        <div class="model-content">
                            <div class="modal-header">
                                <h5 class="modal-title">Reports Table</h5>
                                <button type="button" class="btn-close" onclick="closeReportDetail()"></button>
                            </div>
                            <div class="modal-body">
                                <table class="table table-striped table-bordered">
                                    <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th>Author</th>
                                            <th>Content</th>
                                            <th>Time</th>
                                            <th>Status</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${requestScope.reports}" var="r">
                                            <tr>
                                                <td>${r.id}</td>
                                                <td>${r.user.fullname}</td>
                                                <td>${r.content}</td>
                                                <td>${r.sendTime}</td>
                                                <td>${r.status}</td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>

                        </div>
                    </div>
                </div>


                <!-- Content Row -->

                <div class="row">

                    <!-- Area Chart -->
                    <div class="col-xl-8 col-lg-7">
                        <div class="card shadow mb-4">
                            <!-- Card Header - Dropdown -->
                            <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
                                <h6 class="m-0 font-weight-bold text-primary">Earnings Overview In <span id="currentYear"></span></h6>
                                <div class="dropdown no-arrow">


                                </div>
                            </div>
                            <!-- Card Body -->
                            <div class="card-body">
                                <div class="chart-area">
                                    <canvas id="myAreaChart"></canvas>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Pie Chart -->
                    <div class="col-xl-4 col-lg-5">
                        <div class="card shadow mb-4">
                            <!-- Card Header - Dropdown -->
                            <div
                                class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
                                <h6 class="m-0 font-weight-bold text-primary">Chart Analyze Skills</h6>
                                <div class="dropdown no-arrow">

                                </div>
                            </div>
                            <!-- Card Body -->
                            <div class="card-body">
                                <div class="chart-pie pt- pb-2">
                                    <canvas id="myPieChart"></canvas>
                                </div>
                                <div id="analys"></div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Content Row -->
                <div class="row">

                    <!-- Content Column -->
                    <div class="col-lg-6 mb-4">

                        <!-- Project Card Example -->
                        <div class="card shadow mb-4">
                            <div class="card-header py-3">
                                <h6 class="m-0 font-weight-bold text-primary">Top Mentor</h6>
                            </div>
                            <div class="card-body">
                                <h4 class="small font-weight-bold">Mentor name <span
                                        class="float-right">60%</span></h4>
                                <div class="progress mb-4">
                                    <div class="progress-bar bg-info" role="progressbar" style="width: 60%"
                                         aria-valuenow="20" aria-valuemin="0" aria-valuemax="100"></div>
                                </div>
                                <h4 class="small font-weight-bold">Mentor name <span
                                        class="float-right">60%</span></h4>
                                <div class="progress mb-4">
                                    <div class="progress-bar bg-info" role="progressbar" style="width: 60%"
                                         aria-valuenow="20" aria-valuemin="0" aria-valuemax="100"></div>
                                </div>
                                <h4 class="small font-weight-bold">Mentor name <span
                                        class="float-right">60%</span></h4>
                                <div class="progress mb-4">
                                    <div class="progress-bar bg-info" role="progressbar" style="width: 60%"
                                         aria-valuenow="20" aria-valuemin="0" aria-valuemax="100"></div>
                                </div>
                                <h4 class="small font-weight-bold">Mentor name <span
                                        class="float-right">60%</span></h4>
                                <div class="progress mb-4">
                                    <div class="progress-bar bg-info" role="progressbar" style="width: 60%"
                                         aria-valuenow="20" aria-valuemin="0" aria-valuemax="100"></div>
                                </div>

                            </div>
                        </div>



                        <div class="col-lg-6 mb-4">
                        </div>
                    </div>
                </div>
            </div>

        </div> <!-- End Flexbox -->

        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    </body>

    <script>
                                    document.getElementById('currentYear').textContent = new Date().getFullYear();
                                    function navigate(url) {
                                        window.location.href = url;
                                    }

                                    function showReportDetail() {
                                        var popup = document.getElementById("reportDetailPopup");
                                        popup.style.display = "block";
                                        document.getElementById('overlay').style.display = 'block';
                                    }

                                    function closeReportDetail() {
                                        var popup = document.getElementById("reportDetailPopup");
                                        popup.style.display = "none";
                                        document.getElementById('overlay').style.display = 'none';
                                    }

                                    var transactions = [];
        <c:forEach var="transaction" items="${requestScope.transactions}">
                                    transactions.push({
                                        balance: ${transaction.balance},
                                        time: new Date('${transaction.time}')
                                    });
                                    console.log('Balance: ${transaction.balance}, Time: ${transaction.time}');
        </c:forEach>

// Initialize monthlyBalances with all months set to 0
                                    const monthlyBalances = {};
                                    for (let month = 1; month <= 12; month++) {
                                        monthlyBalances[month] = 0; // Initialize each month with a balance of 0
                                    }

// Group transactions by month and sum balances
                                    transactions.forEach(transaction => {
                                        const month = transaction.time.getMonth() + 1; // getMonth() returns 0-11, so +1 to get 1-12
                                        monthlyBalances[month] += transaction.balance; // Add the balance to the corresponding month
                                    });
// Array of month names
                                    const monthNames = [
                                        "January", "February", "March", "April", "May", "June",
                                        "July", "August", "September", "October", "November", "December"
                                    ];
// Prepare data for the chart
                                    const xValues = monthNames; // Use month names as labels
                                    const yValues = Array.from({length: 12}, (_, i) => monthlyBalances[i + 1]); // Balances for each month

                                    var ctx = document.getElementById('myAreaChart').getContext('2d');
                                    var cpx = document.getElementById('myPieChart').getContext('2d');
                                    new Chart(ctx, {
                                        type: "line",
                                        data: {
                                            labels: xValues, // Use month names as labels
                                            datasets: [{
                                                    label: 'Monthly Balance',
                                                    backgroundColor: "#FFCE56",
                                                    borderColor: "#FF6384",
                                                    data: yValues
                                                }]
                                        },
                                        options: {
                                            scales: {
                                                x: {
                                                    title: {
                                                        display: true,
                                                        text: 'Month'
                                                    }
                                                },
                                                y: {
                                                    title: {
                                                        display: true,
                                                        text: 'Balance'
                                                    },
                                                    beginAtZero: true // Ensure the y-axis starts at 0
                                                }
                                            }
                                        }
                                    });
                                    const xValuesSkill = []; // Labels (skill names)
                                    const yValuesSkill = []; // Data (skill counts)


        <c:forEach var="entry" items="${requestScope.skillCount}">
                                    xValuesSkill.push("${entry.key}");
                                    yValuesSkill.push(${entry.value});
        </c:forEach>
                                    const total = yValuesSkill.reduce((sum, value) => sum + value, 0);
                                    const percentages = yValuesSkill.map(value => ((value / total) * 100).toFixed(2));
                                    console.log(percentages);
                                    const analysDiv = document.getElementById("analys");
                                    for (let i = 0; i < xValuesSkill.length; i++) {
                                        analysDiv.innerHTML += xValuesSkill[i] + " : " + percentages[i] + "%" + "<br/>";
                                    }



                                    function generateColors(numColors) {
                                        const colors = [];
                                        const baseColors = [
                                            "#FF6384", "#36A2EB", "#FFCE56", "#4BC0C0", "#9966FF", "#FF9F40",
                                            "#FF6384", "#36A2EB", "#FFCE56", "#4BC0C0", "#9966FF", "#FF9F40"
                                        ];
                                        for (let i = 0; i < numColors; i++) {
                                            colors.push(baseColors[i % baseColors.length]); // Cycle through baseColors
                                        }
                                        return colors;
                                    }

                                    // Generate barColors based on the number of skills
                                    const barColors = generateColors(xValuesSkill.length);
                                    new Chart(cpx, {
                                        type: "pie",
                                        data: {
                                            labels: xValuesSkill,
                                            datasets: [{
                                                    backgroundColor: barColors,
                                                    data: yValuesSkill
                                                }]
                                        },
                                        options: {
                                            title: {
                                                display: true,
                                                text: "Skill Distribution"
                                            }
                                        }
                                    });
    </script>
</html>
