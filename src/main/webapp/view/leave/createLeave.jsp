<%-- 
    Document   : createLeave
    Created on : Jun 22, 2025, 11:03:55 PM
    Author     : vulea
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Tạo đơn xin nghỉ</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container mt-5">
            <div class="card">
                <div class="card-header bg-primary text-white">Tạo Đơn Xin Nghỉ</div>
                <div class="card-body">
                    <form method="post">
                        <div class="mb-3">
                            <label>Tiêu đề</label>
                            <input type="text" name="text" class="form-control" required>
                        </div>
                        <div class="mb-3">
                            <label>Từ ngày</label>
                            <input type="date" name="fromDate" class="form-control" required>
                        </div>
                        <div class="mb-3">
                            <label>Đến ngày</label>
                            <input type="date" name="toDate" class="form-control" required>
                        </div>
                        <div class="mb-3">
                            <label>Lý do</label>
                            <textarea name="reason" class="form-control" required></textarea>
                        </div>
                        <button type="submit" class="btn btn-success">Gửi đơn</button>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
