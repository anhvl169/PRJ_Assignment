<%-- 
    Document   : list
    Created on : Jun 14, 2025, 10:40:42 AM
    Author     : vulea
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <meta charset="UTF-8">
        <title>Danh sách nhân viên</title>
        <!-- Bootstrap 5 CDN -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="bg-light">

        <div class="container py-4">
            <h2 class="mb-4 text-primary">Danh sách nhân viên</h2>

            <!-- Form tìm kiếm -->
            <form method="get" class="row g-3 mb-4">
                <div class="col-md-3">
                    <input type="text" name="name" value="${param.name}" placeholder="Tên" class="form-control" />
                </div>
                <div class="col-md-2">
                    <select name="gender" class="form-select">
                        <option value="all" ${param.gender == 'all' ? 'selected' : ''}>Tất cả</option>
                        <option value="true" ${param.gender == 'true' ? 'selected' : ''}>Nam</option>
                        <option value="false" ${param.gender == 'false' ? 'selected' : ''}>Nữ</option>
                    </select>
                </div>
                <div class="col-md-3">
                    <input type="text" name="address" value="${param.address}" placeholder="Địa chỉ" class="form-control" />
                </div>
                <div class="col-md-3">
                    <select name="divisionID" class="form-select">
                        <option value="">Tất cả phòng ban</option>
                        <c:forEach var="d" items="${divisions}">
                            <option value="${d.divisionID}" ${param.divisionID == d.divisionID ? 'selected' : ''}>
                                ${d.divisionName}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-md-1">
                    <button type="submit" class="btn btn-primary w-100">Tìm</button>
                </div>
            </form>

            <!-- Bảng nhân viên -->
            <div class="table-responsive">
                <table class="table table-bordered table-hover table-striped align-middle">
                    <thead class="table-dark">
                        <tr>
                            <th>ID</th>
                            <th>Tên</th>
                            <th>Giới tính</th>
                            <th>Địa chỉ</th>
                            <th>Phòng ban</th>
                            <th>Role</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="e" items="${employees}">
                            <tr>
                                <td>${e.employeeID}</td>
                                <td>${e.name}</td>
                                <td>${e.gender ? 'Nam' : 'Nữ'}</td>
                                <td>${e.address}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${not empty e.division}">
                                            ${e.division.divisionName}
                                        </c:when>
                                        <c:otherwise>
                                            Không có
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:if test="${not empty e.account and not empty e.account.roles}">
                                        <c:forEach var="r" items="${e.account.roles}">
                                            <span class="badge bg-info text-dark">${r.roleName}</span><br />
                                        </c:forEach>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

        <!-- Bootstrap 5 JS Bundle (optional) -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>