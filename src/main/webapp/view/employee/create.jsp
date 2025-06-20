<%-- 
    Document   : create
    Created on : Jun 14, 2025, 5:24:59 PM
    Author     : vulea
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <title>Create Employee</title>
    </head>
    <body>
        <h1>Create New Employee</h1>
        <form action="create" method="post">
            <label for="ename">Name:</label>
            <input type="text" id="ename" name="ename" required><br><br>

            <label for="gender">Gender:</label>
            <select id="gender" name="gender">
                <option value="true">Male</option>
                <option value="false">Female</option>
            </select><br><br>

            <label for="address">Address:</label>
            <input type="text" id="address" name="address"><br><br>

            <label for="did">Department:</label>
            <select id="did" name="did">
                <c:forEach var="department" items="${departments}">
                    <option value="${department.did}">${department.dname}</option>
                </c:forEach>
            </select><br><br>

            <input type="submit" value="Create">
        </form>
    </body>
</html>