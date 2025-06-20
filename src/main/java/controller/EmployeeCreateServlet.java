/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import controller.authentication.BaseRBACController;
import dal.DivisionDBContext;
import dal.EmployeeDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import model.Division;
import model.Employee;
import java.sql.Date;
import model.Account;

/**
 *
 * @author vulea
 */
@WebServlet("/request/creates")
public class EmployeeCreateServlet extends BaseRBACController {

    private EmployeeDBContext employeeDBContext;
    private DivisionDBContext divisionDBContext;

    @Override
    public void init() {
        employeeDBContext = new EmployeeDBContext();
        divisionDBContext = new DivisionDBContext();
    }

    @Override
    protected void processPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        String name = req.getParameter("name");
        String address = req.getParameter("address");
        String dob = req.getParameter("dob");
        boolean gender = Boolean.parseBoolean(req.getParameter("gender"));
        int divisionID = Integer.parseInt(req.getParameter("divisionID"));

        Division division = divisionDBContext.getById(divisionID);

        Employee employee = new Employee();
        employee.setName(name);
        employee.setAddress(address);
        employee.setDob(Date.valueOf(dob));
        employee.setGender(gender);
        employee.setDivision(division);

        employeeDBContext.insert(employee);

        resp.getWriter().println("Inserted " + employee.getEmployeeID());
    }

    @Override
    protected void processGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        List<Division> divisions = divisionDBContext.getAll();
        req.setAttribute("divisions", divisions);
        req.getRequestDispatcher("../view/employee/create.jsp").forward(req, resp);
    }
}
