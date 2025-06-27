<%-- 
    Document   : agenda
    Created on : Jun 26, 2025, 11:53:52 PM
    Author     : vulea
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Agenda</title>
        <style>
            table {
                border-collapse: collapse;
                width: 100%;
                text-align: center;
            }
            th, td {
                border: 1px solid black;
                padding: 5px;
            }
            .work {
                background-color: lightgreen;
            }
            .off {
                background-color: red;
                color: white;
            }
            form {
                margin-bottom: 20px;
            }
            .debug {
                color: blue;
                font-size: 12px;
            }
        </style>
    </head>
    <body>
        <h2>Tình hình nhân sự</h2>

        <!-- ✅ FORM CHỌN NGÀY -->
        <form method="get" action="${pageContext.request.contextPath}/agenda/view">
            <label>Ngày bắt đầu: 
                <input type="date" name="from" value="${from}" required>
            </label>
            <label>Ngày kết thúc: 
                <input type="date" name="to" value="${to}" required>
            </label>
            <button type="submit">Xem lịch</button>
        </form>

        <!-- ✅ DEBUG OUTPUT -->
        <div class="debug">
            <p>From: ${from}</p>
            <p>To: ${to}</p>
            <p>Employee Count: ${agenda.size()}</p>
            <c:forEach var="entry" items="${agenda}" varStatus="loop">
                <p>Employee ${loop.count}: ${entry.key.name} (ID: ${entry.key.employeeID})</p>
            </c:forEach>
        </div>

        <!-- ✅ BẢNG AGENDA -->
        <c:if test="${not empty agenda}">
            <table>
                <thead>
                    <tr>
                        <th>Nhân sự</th>
                        <c:forEach var="day" items="${dateRange}">
                            <th>${day.dayOfMonth}/${day.monthValue}</th>
                        </c:forEach>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="entry" items="${agenda}">
                        <tr>
                            <td>${entry.key.name} (ID: ${entry.key.employeeID})</td>
                            <c:forEach var="day" items="${dateRange}">
                                <c:set var="off" value="${entry.value[day]}" />
                                <td class="${off ? 'off' : 'work'}">
                                    ${off ? 'Nghỉ' : 'Làm'}
                                </td>
                            </c:forEach>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
        <c:if test="${empty agenda}">
            <p class="debug">No agenda data available.</p>
        </c:if>
    </body>
</html>