<%-- 
    Document   : create
    Created on : Jun 14, 2025, 5:24:59 PM
    Author     : vulea
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Thêm Nhân Viên</title>
        <!-- Bootstrap 5 CSS CDN -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="bg-light">

        <div class="container mt-5">
            <div>
                <a href="${pageContext.request.contextPath}/home" class="btn btn-primary">
                    Home
                </a>
            </div>
            <div class="card shadow">
                <div class="card-header bg-primary text-white">
                    <h4 class="mb-0">Thêm Nhân Viên</h4>
                </div>
                <div class="card-body">
                    <form action="${pageContext.request.contextPath}/request/creates" method="post">
                        <div class="mb-3">
                            <label for="name" class="form-label">Họ tên</label>
                            <input type="text" class="form-control" id="name" name="name" required>
                        </div>

                        <div class="mb-3">
                            <label for="address" class="form-label">Địa chỉ</label>
                            <input type="text" class="form-control" id="address" name="address">
                        </div>

                        <div class="mb-3">
                            <label for="dob" class="form-label">Ngày sinh</label>
                            <input type="date" class="form-control" id="dob" name="dob" required>
                        </div>

                        <div class="mb-3">
                            <label for="gender" class="form-label">Giới tính</label>
                            <select class="form-select" id="gender" name="gender">
                                <option value="true">Nam</option>
                                <option value="false">Nữ</option>
                            </select>
                        </div>

                        <div class="mb-3">
                            <label for="divisionID" class="form-label">Phòng ban</label>
                            <select class="form-select" id="divisionID" name="divisionID">
                                <c:forEach var="d" items="${divisions}">
                                    <option value="${d.divisionID}">${d.divisionName}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="mb-3">
                            <label for="managerID">GroupLeade):</label>
                            <select name="managerID" id="managerID">
                                <option value="">-- Không chọn --</option>
                                <c:forEach var="e" items="${groupLeaders}">
                                    <option value="${e.employeeID}">${e.name}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Chọn Quyền</label>
                            <c:forEach var="role" items="${roles}">
                                <div class="form-check">
                                    <input class="form-check-input" type="checkbox" name="roleIDs" value="${role.roleID}" id="role${role.roleID}">
                                    <label class="form-check-label" for="role${role.roleID}">
                                        ${role.roleName}
                                    </label>
                                </div>
                            </c:forEach>
                        </div>

                        <div class="d-grid gap-2">
                            <button type="submit" class="btn btn-success">Thêm Nhân Viên</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <!-- Bootstrap 5 JS + Popper -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>