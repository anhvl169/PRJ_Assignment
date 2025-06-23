<%-- 
    Document   : leaveApprove
    Created on : Jun 24, 2025, 2:05:59 AM
    Author     : vulea
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Duyệt đơn xin nghỉ</title>
    </head>
    <body>
        <h2>Danh sách đơn chờ duyệt</h2>
        <table border="1">
            <tr>
                <th>Từ</th>
                <th>Đến</th>
                <th>Lý do</th>
                <th>Người tạo</th>
                <th>Hành động</th>
            </tr>
            <c:forEach var="r" items="${requests}">
                <tr>
                    <td>${r.fromDate}</td>
                    <td>${r.toDate}</td>
                    <td>${r.reason}</td>
                    <td>${r.createdBy.username}</td>
                    <td>
                        <form method="post">
                            <input type="hidden" name="id" value="${r.requestID}" />
                            <button name="action" value="approve">Duyệt</button>
                            <button name="action" value="reject">Từ chối</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>