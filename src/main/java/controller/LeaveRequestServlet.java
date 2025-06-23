/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import controller.authentication.BaseRBACController;
import dal.LeaveRequestDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import model.Account;
import model.Employee;
import model.LeaveRequests;

/**
 *
 * @author vulea
 */
@WebServlet("/request/leave")
public class LeaveRequestServlet extends BaseRBACController {

    private LeaveRequestDBContext leaveDB;

    @Override
    public void init() {
        leaveDB = new LeaveRequestDBContext();
    }

    @Override
    protected void processPost(HttpServletRequest req, HttpServletResponse resp, Account account)
            throws ServletException, IOException {

        try {
            String fromDateStr = req.getParameter("fromDate");
            String toDateStr = req.getParameter("toDate");
            String reason = req.getParameter("reason");
            String applicaText = req.getParameter("text"); // lấy từ ô mô tả chi tiết
            Date fromDate = Date.valueOf(fromDateStr);
            Date toDate = Date.valueOf(toDateStr);

            LeaveRequests leave = new LeaveRequests();
            leave.setCreatedBy(account);
            leave.setFromDate(fromDate);
            leave.setToDate(toDate);
            leave.setReason(reason);
            leave.setApplicaText(applicaText);
            leave.setStatus("Inprogress");

            leaveDB.insert(leave);

            resp.sendRedirect(req.getContextPath() + "/leave/list");

        } catch (IOException ex) {
            throw new ServletException("Lỗi khi tạo đơn nghỉ phép: " + ex.getMessage(), ex);
        }
    }

    @Override
    protected void processGet(HttpServletRequest req, HttpServletResponse resp, Account account)
            throws ServletException, IOException {
        req.getRequestDispatcher("/view/leave/createLeave.jsp").forward(req, resp);
    }

}
