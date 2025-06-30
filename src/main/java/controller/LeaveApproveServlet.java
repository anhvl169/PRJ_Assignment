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
import java.util.List;
import model.Account;
import model.LeaveRequests;

/**
 *
 * @author vulea
 */
@WebServlet("/leave/approve")

public class LeaveApproveServlet extends BaseRBACController {

    private LeaveRequestDBContext leaveDB;

    @Override
    public void init() {
        leaveDB = new LeaveRequestDBContext();
    }

    @Override
    protected void processPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        int requestID = Integer.parseInt(req.getParameter("id"));
        String action = req.getParameter("action");

        LeaveRequests request = leaveDB.findById(requestID);
        if (request == null || !request.getStatus().equals("Inprogress")) {
            resp.sendError(400, "Invalid or already processed request");
            return;
        }

        // Kiểm tra người duyệt có phải là manager trực tiếp của người tạo hay không
        int approverID = account.getEmployee().getEmployeeID();
        int requesterManagerID = request.getCreatedBy().getEmployee().getManager().getEmployeeID(); // người quản lý trực tiếp

        if (approverID != requesterManagerID) {
            resp.sendError(403, "You are not authorized to approve this request");
            return;
        }

        request.setStatus(action.equals("approve") ? "Approved" : "Rejected");
        request.setApprovedBy(account);
        leaveDB.update(request);

        resp.sendRedirect(req.getContextPath() + "/leave/approve");
    }

    @Override
    protected void processGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        //lấy leave request đang trong trạng thái inprogress chưa được duyệt
        List<LeaveRequests> pendingRequests = leaveDB.getPendingRequests();

        //lấy tất cả các đơn của cấp dưới
        int empId = account.getEmployee().getEmployeeID();
        List<LeaveRequests> allRequests = leaveDB.getAllSubordinateRequests(empId);

        req.setAttribute("allRequests", allRequests);
        req.setAttribute("requests", pendingRequests);
        req.getRequestDispatcher("/view/leave/leaveApprove.jsp").forward(req, resp);
    }

}
