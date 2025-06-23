<%-- 
    Document   : leaveList
    Created on : Jun 24, 2025, 12:56:42 AM
    Author     : vulea
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Danh sách đơn xin nghỉ</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container mt-5">
            <h2 class="mb-4 text-center">Danh sách đơn xin nghỉ</h2>

            <div class="table-responsive">
                <table class="table table-striped table-bordered align-middle">
                    <thead class="table-primary text-center">
                        <tr>
                            <th scope="col">Title</th>
                            <th scope="col">From</th>
                            <th scope="col">To</th>              
                            <th scope="col">Created By</th>                  
                            <th scope="col">Trạng thái</th>
                            <th scope="col">Duyệt bởi</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="r" items="${requests}">
                            <tr>
                                <td>${r.reason}</td>
                                <td>${r.fromDate}</td>
                                <td>${r.toDate}</td>                       
                                <td>
                                    <c:choose>
                                        <c:when test="${r.createdBy.employee != null}">
                                            ${r.createdBy.employee.name}
                                        </c:when>
                                        <c:otherwise>
                                            Không có tên người dùng
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <span class="badge
                                          <c:choose>
                                              <c:when test="${r.status == 'Pending'}">bg-warning text-dark</c:when>
                                              <c:when test="${r.status == 'Approved'}">bg-success</c:when>
                                              <c:when test="${r.status == 'Rejected'}">bg-danger</c:when>
                                              <c:otherwise>bg-secondary</c:otherwise>
                                          </c:choose>">
                                        ${r.status}
                                    </span>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${r.approvedBy.employee != null}">
                                            ${r.approvedBy.employee.name}
                                        </c:when>
                                        <c:otherwise>
                                            Chưa có người duyệt
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
