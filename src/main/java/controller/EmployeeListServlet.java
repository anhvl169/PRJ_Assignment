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
import model.Account;
import model.Division;
import model.Employee;

/**
 *
 * @author vulea
 */
@WebServlet("/request/list")
public class EmployeeListServlet extends BaseRBACController {

    private EmployeeDBContext employeeDBContext;

    @Override
    public void init() throws ServletException {
        employeeDBContext = new EmployeeDBContext();

    }

    @Override
    protected void processPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected void processGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        // Lấy các tham số tìm kiếm
        String name = req.getParameter("name");
        String gender = req.getParameter("gender");
        String address = req.getParameter("address");
        String divisionID = req.getParameter("divisionID");

        // Nếu không có điều kiện tìm kiếm -> lấy toàn bộ
        List<Employee> employees;
        if ((name == null || name.isBlank())
                && (gender == null || gender.equalsIgnoreCase("all"))
                && (address == null || address.isBlank())
                && (divisionID == null || divisionID.isBlank())) {
            employees = employeeDBContext.list(); // Không có lọc
        } else {
            employees = employeeDBContext.search(name, gender, address, divisionID); // Có lọc
        }
        //gửi divisions tới list.jsp để dùng dropdown
        DivisionDBContext divisionDBContext = new DivisionDBContext();
        List<Division> divisions = divisionDBContext.list();
        req.setAttribute("divisions", divisions);

        // Gửi kết quả và điều kiện tìm kiếm ngược lại cho form
        req.setAttribute("employees", employees);
        req.setAttribute("name", name);
        req.setAttribute("gender", gender);
        req.setAttribute("address", address);
        req.setAttribute("divisionID", divisionID);

        req.getRequestDispatcher("../view/employee/list.jsp").forward(req, resp);
    }
}
