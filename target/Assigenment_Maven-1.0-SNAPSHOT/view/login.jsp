<%-- 
    Document   : login
    Created on : Jun 20, 2025, 7:57:13 PM
    Author     : vulea
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <title>Đăng nhập</title>
    </head>
    <body>
        <h2>Đăng nhập</h2>
        <form method="post" action="${pageContext.request.contextPath}/login">
            <label>Tài khoản:</label>
            <input type="text" name="username" required /><br/>

            <label>Mật khẩu:</label>
            <input type="password" name="password" required /><br/>

            <input type="submit" value="Đăng nhập" />

            <p style="color:red">${error}</p>
        </form>
    </body>
</html>
