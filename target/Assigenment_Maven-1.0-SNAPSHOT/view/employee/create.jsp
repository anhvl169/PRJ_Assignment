<%-- 
    Document   : create
    Created on : Jun 14, 2025, 5:24:59 PM
    Author     : vulea
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <title>Thêm Nhân Viên</title>
    </head>
    <body>
        <h2>Thêm Nhân Viên</h2>
        <form action="${pageContext.request.contextPath}/employee/create" method="post">
            Họ tên: <input type="text" name="name" required><br><br>
            Địa chỉ: <input type="text" name="address"><br><br>
            Ngày sinh: <input type="date" name="dob" required><br><br>
            Giới tính:
            <select name="gender">
                <option value="true">Male</option>
                <option value="false">Female</option>
            </select><br><br>
            Phòng ban:
            <select name="divisionID">
                <c:forEach var="d" items="${divisions}">
                    <option value="${d.divisionID}">${d.divisionName}</option>
                </c:forEach>
            </select><br><br>
            <button type="submit">Thêm</button>
        </form>
    </body>
</html>