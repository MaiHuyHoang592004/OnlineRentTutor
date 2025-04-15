<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Thêm Slot Mới</title>
    <!-- Bootstrap 5 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
            font-family: 'Arial', sans-serif;
        }
        .container {
            max-width: 600px;
            background: #ffffff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            margin-top: 50px;
        }
        .form-label {
            font-weight: bold;
        }
        .btn-group {
            display: flex;
            justify-content: space-between;
        }
    </style>
</head>
<body>

<div class="container">
    <h2 class="text-center text-primary mb-4">Thêm Slot Mới</h2>

    <form action="schedule" method="post">
        <input type="hidden" name="action" value="add">
        <input type="hidden" name="mentorId" value="<%= session.getAttribute("userId") %>">

        <div class="mb-3">
            <label class="form-label">Giờ bắt đầu:</label>
            <input type="datetime-local" name="start" required class="form-control">
        </div>

        <div class="mb-3">
            <label class="form-label">Số giờ dạy:</label>
            <input type="number" name="hour" value="1.333" step="0.01" required class="form-control">
        </div>

        <div class="mb-3">
            <label class="form-label">Meet Link:</label>
            <input type="text" name="link" placeholder="Nhập link meet" required maxlength="255" class="form-control">
        </div>

        <div class="mb-3">
            <label class="form-label">Lịch học:</label>
            <select name="type" required class="form-select">
                <option value="byDay">Theo Ngày Cụ Thể</option>
                <option value="byWeek">Theo Ngày Trong Tuần</option>
            </select>
        </div>

        <div class="mb-3">
            <label class="form-label">Chọn slot dạy học:</label>
            <select name="slot" class="form-select">
                <option value="0">Slot 0 (05:00 - 07:20)</option>
                <option value="1">Slot 1 (07:30 - 09:50)</option>
                <option value="2">Slot 2 (10:00 - 12:20)</option>
                <option value="3">Slot 3 (12:50 - 15:10)</option>
                <option value="4">Slot 4 (15:20 - 17:40)</option>
                <option value="5">Slot 5 (17:50 - 20:10)</option>
                <option value="6">Slot 6 (20:30 - 22:50)</option>
            </select>
        </div>

        <div class="mb-3">
            <label class="form-label">Ngày trong tuần (Nếu chọn theo tuần):</label><br>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="checkbox" name="weekday" value="mon">
                <label class="form-check-label">Thứ 2</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="checkbox" name="weekday" value="tue">
                <label class="form-check-label">Thứ 3</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="checkbox" name="weekday" value="wen">
                <label class="form-check-label">Thứ 4</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="checkbox" name="weekday" value="thu">
                <label class="form-check-label">Thứ 5</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="checkbox" name="weekday" value="fri">
                <label class="form-check-label">Thứ 6</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="checkbox" name="weekday" value="sat">
                <label class="form-check-label">Thứ 7</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="checkbox" name="weekday" value="sun">
                <label class="form-check-label">CN</label>
            </div>
        </div>

        <div class="mb-3">
            <label class="form-label">Thời gian thực hiện:</label>
            <div class="row">
                <div class="col">
                    <input type="date" name="fromDay" required class="form-control">
                </div>
                <div class="col">
                    <input type="date" name="toDay" required class="form-control">
                </div>
            </div>
        </div>

        <div class="btn-group">
            <button type="submit" class="btn btn-success">Xác Nhận</button>
            <a href="schedule.jsp" class="btn btn-secondary">Hủy</a>
        </div>
    </form>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
