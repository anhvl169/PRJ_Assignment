/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import controller.authentication.BaseRBACController;
import dal.DivisionDBContext;
import dal.EmployeeDBContext;
import dal.LeaveRequestDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import model.Account;
import model.Employee;
import model.LeaveRequests;

/**
 *
 * @author vulea
 */
@WebServlet("/agenda/view")
public class ViewAgendaServlet extends BaseRBACController {

    private static final Logger LOGGER = Logger.getLogger(ViewAgendaServlet.class.getName());
    private LeaveRequestDBContext leaveDB;
    private DivisionDBContext divisionDB;
    private EmployeeDBContext employeeDB;

    @Override
    public void init() {
        leaveDB = new LeaveRequestDBContext();
        divisionDB = new DivisionDBContext();
        employeeDB = new EmployeeDBContext();
    }

    @Override
    protected void processPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected void processGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        try {
            // 1. Lấy khoảng thời gian từ request (hoặc mặc định 7 ngày gần nhất)
            String fromRaw = req.getParameter("from");
            String toRaw = req.getParameter("to");

            LocalDate from = (fromRaw != null && !fromRaw.isEmpty())
                    ? LocalDate.parse(fromRaw)
                    : LocalDate.now();
            LocalDate to = (toRaw != null && !toRaw.isEmpty())
                    ? LocalDate.parse(toRaw)
                    : from.plusDays(6);

            java.util.Date fromUtil = Date.from(from.atStartOfDay(ZoneId.systemDefault()).toInstant());
            java.util.Date toUtil = Date.from(to.atStartOfDay(ZoneId.systemDefault()).toInstant());
            java.sql.Date fromDate = new java.sql.Date(fromUtil.getTime());
            java.sql.Date toDate = new java.sql.Date(toUtil.getTime());

            LOGGER.info("Processing agenda from " + from + " to " + to);

            // 2. Lấy danh sách nhân sự theo phòng ban của account
            Employee employee = account.getEmployee();
            if (employee == null || employee.getDivision() == null) {
                throw new ServletException("Account or division not found for user");
            }
            int divisionID = employee.getDivision().getDivisionID();
            List<Employee> employees = employeeDB.getByDivision(divisionID);
            LOGGER.info("Found " + employees.size() + " employees for division ID: " + divisionID);
            for (Employee emp : employees) {
                LOGGER.info("Employee: " + emp.getName() + ", ID: " + emp.getEmployeeID());
            }
            
            // 3. Lấy đơn xin nghỉ được duyệt trong khoảng thời gian
            List<LeaveRequests> leaveRequests = leaveDB.getApprovedInRange(fromDate, toDate);
            LOGGER.info("Found " + leaveRequests.size() + " approved leave requests");
            for (LeaveRequests lr : leaveRequests) {
                LOGGER.info("Leave Request: EmployeeID=" + (lr.getCreatedBy() != null && lr.getCreatedBy().getEmployee() != null
                        ? lr.getCreatedBy().getEmployee().getEmployeeID() : "null")
                        + ", From=" + lr.getFromDate() + ", To=" + lr.getToDate() + ", Status=" + lr.getStatus());
            }

            // 4. Tạo danh sách ngày
            List<LocalDate> dateRange = new ArrayList<>();
            LocalDate current = from;
            while (!current.isAfter(to)) {
                dateRange.add(current);
                current = current.plusDays(1);
            }

            // 5. Mapping: Map<Employee, Map<LocalDate, Boolean>>
            Map<Employee, Map<LocalDate, Boolean>> agenda = new LinkedHashMap<>();
            for (Employee emp : employees) {
                Map<LocalDate, Boolean> dailyStatus = new LinkedHashMap<>();
                for (LocalDate day : dateRange) {
                    boolean isOff = leaveRequests.stream()
                            .filter(lr -> lr.getCreatedBy() != null && lr.getCreatedBy().getEmployee() != null)
                            .filter(lr -> lr.getCreatedBy().getEmployee().getEmployeeID() == emp.getEmployeeID())
                            .anyMatch(lr -> {
                                LocalDate start = lr.getFromDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                                LocalDate end = lr.getToDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                                LOGGER.fine("Checking day " + day + " against " + start + " to " + end + " for EmployeeID " + emp.getEmployeeID());
                                // Workaround: Use inclusive range check
                                boolean withinRange = !day.isBefore(start.minusDays(1)) && !day.isAfter(end.plusDays(1));
                                LOGGER.fine("Within range: " + withinRange);
                                return withinRange;
                            });
                    dailyStatus.put(day, isOff);
                    LOGGER.fine("Employee " + emp.getName() + " on " + day + ": " + (isOff ? "Nghỉ" : "Làm"));
                }
                agenda.put(emp, dailyStatus);
            }

            // 6. Gửi dữ liệu về JSP
            req.setAttribute("from", from);
            req.setAttribute("to", to);
            req.setAttribute("dateRange", dateRange);
            req.setAttribute("agenda", agenda);

            req.getRequestDispatcher("/view/agenda/agenda.jsp").forward(req, resp);
        } catch (DateTimeParseException e) {
            LOGGER.severe("Invalid date format: " + e.getMessage());
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid date format");
        } catch (Exception e) {
            LOGGER.severe("Error processing agenda: " + e.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing agenda");
        }
    }

}
