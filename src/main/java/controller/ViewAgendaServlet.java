/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import controller.authentication.BaseRBACController;
import dal.DivisionDBContext;
import dal.EmployeeDBContext;
import dal.LeaveRequestDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Account;
import model.Employee;
import model.LeaveRequests;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.EmployeeDaysOff;

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
            //lấy ngày từ form
            String fromRaw = req.getParameter("from");
            String toRaw = req.getParameter("to");

            LocalDate from = (fromRaw != null && !fromRaw.isEmpty())
                    ? LocalDate.parse(fromRaw)
                    : LocalDate.now();
            LocalDate to = (toRaw != null && !toRaw.isEmpty())
                    ? LocalDate.parse(toRaw)
                    : from.plusDays(6);

            Date fromDate = Date.from(from.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date toDate = Date.from(to.atStartOfDay(ZoneId.systemDefault()).toInstant());

            Employee employee = account.getEmployee();
            if (employee == null || employee.getDivision() == null) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Account or division not found.");
                return;
            }

            int divisionID = employee.getDivision().getDivisionID();
            List<Employee> employees = employeeDB.getByDivision(divisionID);
            List<LeaveRequests> leaveRequests = leaveDB.getApprovedInRange(fromDate, toDate);
            
            LOGGER.info("Found " + employees.size() + " employees in division " + divisionID);
            for (Employee emp : employees) {
                LOGGER.info("Employee: ID=" + emp.getEmployeeID() + ", Name=" + emp.getName());
            }
            
            // Danh sách ngày trong khoảng
            List<String> dateRange = new ArrayList<>();
            for (LocalDate d = from; !d.isAfter(to); d = d.plusDays(1)) {
                dateRange.add(d.toString());
            }

            // Chuẩn bị DTO
            List<EmployeeDaysOff> dtoList = new ArrayList<>();
            for (Employee emp : employees) {
                List<String> daysOff = new ArrayList<>();
                for (LeaveRequests lr : leaveRequests) {
                    if (lr.getCreatedBy() != null
                            && lr.getCreatedBy().getEmployee() != null
                            && emp.getEmployeeID() == lr.getCreatedBy().getEmployee().getEmployeeID()) {
                        LocalDate start = ((java.sql.Date) lr.getFromDate()).toLocalDate();
                        LocalDate end = ((java.sql.Date) lr.getToDate()).toLocalDate();
                        for (LocalDate d = start; !d.isAfter(end); d = d.plusDays(1)) {
                            daysOff.add(d.toString());
                        }
                    }
                }
                dtoList.add(new EmployeeDaysOff(emp.getEmployeeID(), emp.getName(), daysOff));
            }
            System.out.println(dtoList);
            req.setAttribute("from", from);
            req.setAttribute("to", to);
            req.setAttribute("employeeDaysOff", new Gson().toJson(dtoList));
            req.setAttribute("dateRange", new Gson().toJson(dateRange));

            req.getRequestDispatcher("/view/agenda/agenda.jsp").forward(req, resp);

        } catch (DateTimeParseException e) {
            LOGGER.severe("Invalid date format: " + e.getMessage());
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid date format");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error processing agenda", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing agenda");
        }
    }

}
