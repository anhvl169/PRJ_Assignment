<%-- 
    Document   : home
    Created on : Jun 22, 2025, 9:47:27 PM
    Author     : vulea
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Trang chủ</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"/>
    </head>
    <body class="bg-light">
        <div class="container mt-5">
            <a href="${pageContext.request.contextPath}/logout" class="btn btn-danger" style="float:right;">Logout</a>
            <h2>Xin chào, ${sessionScope.account.employee.name}</h2>
            <p>Chức năng bạn có quyền truy cập:</p>
            <div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4">

                <c:forEach var="f" items="${features}">
                    <c:if test="${f.urlPath != '/home'}">
                        <div class="col">
                            <div class="card h-100 shadow-sm border-0">
                                <div class="card-body">
                                    <h5 class="card-title">${f.featureName}</h5>
                                    <a href="${pageContext.request.contextPath}${f.urlPath}" class="btn btn-primary">
                                        Truy cập
                                    </a>
                                </div>
                            </div>
                        </div>
                    </c:if>
                </c:forEach>
            </div>
        </div>
    </body>
</html>
