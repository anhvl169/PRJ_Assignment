<%-- 
    Document   : list
    Created on : Jun 14, 2025, 10:40:42 AM
    Author     : vulea
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <title>Employee List</title>
    </head>
    <body>

        <h1>Employees List</h1>
        <form method="get">
            Tên: <input type="text" name="name" value="${param.name}" />
            Giới tính:
            <select name="gender">
                <option value="all" ${param.gender == 'all' ? 'selected' : ''}>Tất cả</option>
                <option value="true" ${param.gender == 'true' ? 'selected' : ''}>Nam</option>
                <option value="false" ${param.gender == 'false' ? 'selected' : ''}>Nữ</option>
            </select>
            Địa chỉ: <input type="text" name="address" value="${param.address}" />
            Phòng ban (ID): <input type="text" name="divisionID" value="${param.divisionID}" />
            <input type="submit" value="Tìm kiếm" />
        </form>

        <table border="1">
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Gender</th>
                <th>Address</th>
                <th>Division</th>
            </tr>
            <c:forEach var="e" items="${employees}">
                <tr>
                    <td>${e.employeeID}</td>
                    <td>${e.name}</td>
                    <td>${e.gender ? 'Male' : 'Female'}</td>
                    <td>${e.address}</td>
                    <td>
                        <c:choose>
                            <c:when test="${not empty e.division}">
                                ${e.division.divisionName}
                            </c:when>
                            <c:otherwise>
                                No Division
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>