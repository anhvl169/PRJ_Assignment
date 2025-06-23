/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import controller.authentication.BaseRBACController;
import dal.DivisionDBContext;
import dal.EmployeeDBContext;
import dal.RoleDBContext;
import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import model.Division;
import model.Employee;
import java.sql.Date;
import java.util.ArrayList;
import model.Account;
import model.Role;

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
    protected void processPost(HttpServletRequest req, HttpServletResponse resp, Account account)
            throws ServletException, IOException {

        String name = req.getParameter("name");
        String address = req.getParameter("address");
        String dob = req.getParameter("dob");
        boolean gender = Boolean.parseBoolean(req.getParameter("gender"));
        int divisionID = Integer.parseInt(req.getParameter("divisionID"));

        // Lấy thông tin phòng ban
        Division division = divisionDBContext.getById(divisionID);

        // Tạo employee
        Employee employee = new Employee();
        employee.setName(name);
        employee.setAddress(address);
        employee.setDob(Date.valueOf(dob));
        employee.setGender(gender);
        employee.setDivision(division);

        // Lấy role từ form
        String[] roleIDs = req.getParameterValues("roleIDs");
        List<Role> assignedRoles = new ArrayList<>();
        RoleDBContext roleDBContext = new RoleDBContext();
        if (roleIDs != null) {
            for (String rid : roleIDs) {
                Role r = roleDBContext.getById(Integer.parseInt(rid));
                if (r != null) {
                    assignedRoles.add(r);
                }
            }
        }

        // Tạo account sau khi employee đã có ID
        Account newAcc = new Account();
        newAcc.setUsername("emp" + Math.abs(name.hashCode()));
        newAcc.setPassword("12345");
        newAcc.setRoles(assignedRoles);
        newAcc.setEmployee(employee);
        employee.setAccount(newAcc); // Quan hệ 2 chiều

        // Lưu vào DB (Persist employee trước để sinh ID)
        try (EntityManager em = employeeDBContext.getEntityManager()) {
            em.getTransaction().begin();
            em.persist(employee);     // persist trước để sinh employeeID
            em.flush();               // flush để chắc chắn ID được sinh
            em.persist(newAcc);       // sau đó persist Account
            em.getTransaction().commit();
        }

        resp.sendRedirect(req.getContextPath() + "/request/list");
    }

    @Override
    protected void processGet(HttpServletRequest req, HttpServletResponse resp, Account account)
            throws ServletException, IOException {
        List<Division> divisions = divisionDBContext.getAll();
        RoleDBContext roleDBContext = new RoleDBContext();
        List<Role> roles = roleDBContext.list();

        req.setAttribute("divisions", divisions);
        req.setAttribute("roles", roles);

        req.getRequestDispatcher("/view/employee/create.jsp").forward(req, resp);
    }
}
