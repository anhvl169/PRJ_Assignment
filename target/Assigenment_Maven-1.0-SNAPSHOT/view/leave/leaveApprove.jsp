<%-- 
    Document   : leaveApprove
    Created on : Jun 24, 2025, 2:05:59 AM
    Author     : vulea
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page import="model.Role" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Duyệt đơn xin nghỉ</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="bg-light p-4">
        <div class="container">
            <div>
                <a href="${pageContext.request.contextPath}/home" class="btn btn-primary">
                    Home
                </a>
            </div>
            <h2 class="mb-4 text-center text-primary">Danh sách đơn chờ duyệt</h2>

            <div class="mb-3">
                <p><strong>Processed by :</strong>${sessionScope.account.employee.name}
                    <strong>Role :</strong>
                    <c:forEach var="role" items="${sessionScope.account.roles}">
                        <span class="badge bg-info text-dark me-2">${role.roleName}</span>
                    </c:forEach>
                </p>
            </div>

            <table class="table table-bordered table-striped bg-white shadow-sm">
                <thead class="table-primary">
                    <tr>
                        <th scope="col">Từ ngày</th>
                        <th scope="col">Đến ngày</th>
                        <th scope="col">Lý do</th>
                        <th scope="col">Người tạo</th>
                        <th scope="col" class="text-center">Hành động</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="r" items="${requests}">
                        <tr>
                            <td>${r.fromDate}</td>
                            <td>${r.toDate}</td>
                            <td>${r.reason}</td>
                            <td>${r.createdBy.username}</td>
                            <td class="text-center">
                                <form method="post" class="d-inline">
                                    <input type="hidden" name="id" value="${r.requestID}" />
                                    <button name="action" value="approve" class="btn btn-success btn-sm">Duyệt</button>
                                </form>
                                <form method="post" class="d-inline">
                                    <input type="hidden" name="id" value="${r.requestID}" />
                                    <button name="action" value="reject" class="btn btn-danger btn-sm">Từ chối</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <c:if test="${empty requests}">
                <div class="alert alert-info text-center">Không có đơn nào đang chờ duyệt.</div>
            </c:if>


            <h2 class="mt-5 mb-4 text-center text-primary">Danh sách đơn của cấp dưới</h2>  
            <table class="table table-bordered table-striped bg-white shadow-sm">
                <thead class="table-primary">
                    <tr>
                        <th>Người tạo</th>
                        <th>Từ ngày</th>
                        <th>Đến ngày</th>
                        <th>Lý do</th>
                        <th>Trạng thái</th>
                    </tr>
                </thead>
                <c:forEach var="a" items="${allRequests}">
                    <tr>
                        <td>${a.createdBy.employee.name}</td>
                        <td>${a.fromDate}</td>
                        <td>${a.toDate}</td>
                        <td>${a.reason}</td>
                        <td>
                            <span class="badge
                                  <c:choose>
                                      <c:when test="${a.status == 'Inprogress'}">bg-warning text-dark</c:when>
                                      <c:when test="${a.status == 'Approved'}">bg-success</c:when>
                                      <c:when test="${a.status == 'Rejected'}">bg-danger</c:when>
                                      <c:otherwise>bg-secondary</c:otherwise>
                                  </c:choose>">
                                ${a.status}
                            </span>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </body>
</html>