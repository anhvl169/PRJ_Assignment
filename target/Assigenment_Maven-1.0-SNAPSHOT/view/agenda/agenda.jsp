<%-- 
    Document   : agenda
    Created on : Jun 26, 2025, 11:53:52 PM
    Author     : vulea
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
    <head>
        <title>Agenda</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"/>
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
        </style>
    </head>
    <body>
        <div class="container mt-3">
            <h2>Tình hình nhân sự</h2>
            <div>
                <a href="${pageContext.request.contextPath}/home" class="btn btn-primary mb-3">Home</a>
            </div>

            <form method="get" action="${pageContext.request.contextPath}/agenda/view" class="row g-3">
                <div class="col-auto">
                    <label>Ngày bắt đầu:
                        <input type="date" name="from" value="${from}" required class="form-control">
                    </label>
                </div>
                <div class="col-auto">
                    <label>Ngày kết thúc:
                        <input type="date" name="to" value="${to}" required class="form-control">
                    </label>
                </div>
                <div class="col-auto">
                    <button type="submit" class="btn btn-success">Xem lịch</button>
                </div>
            </form>

            <table class="table table-bordered mt-3" id="agendaTable">
                <thead>
                    <tr>
                        <th>Nhân sự</th>
                            <c:forEach var="d" items="${dateRange}">
                            <th>${d}</th>
                            </c:forEach>
                    </tr>
                </thead>

                <tbody id="agendaBody">

                </tbody>

            </table>

        </div>

        <script>
            const employeeDaysOff = ${employeeDaysOff};
            const dateRange = ${dateRange};
            console.log('${fn:escapeXml(employeeDaysOff)}');
            console.log('${fn:escapeXml(dateRange)}');
            const tbody = document.getElementById("agendaBody");

            employeeDaysOff.forEach(emp => {
                const tr = document.createElement("tr");

                const tdName = document.createElement("td");
                let ename = emp.name;
                tdName.textContent = ename;
                tr.appendChild(tdName);
                console.log(emp.name);
                
                dateRange.forEach(date => {
                    const td = document.createElement("td");
                    const isOff = emp.daysOff.includes(date);
                    td.className = isOff ? 'bg-danger' : 'bg-success';
                    td.textContent = isOff ? 'Nghỉ' : 'Làm';
                    tr.appendChild(td);
                });

                tbody.appendChild(tr);
            });
        </script>


    </body>
</html>
