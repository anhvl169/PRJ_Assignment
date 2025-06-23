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
import static java.lang.System.console;
import java.util.ArrayList;
import java.util.List;
import model.Account;
import model.LeaveRequests;

/**
 *
 * @author vulea
 */
@WebServlet("/leave/list")
public class LeaveListServlet extends BaseRBACController {

    private LeaveRequestDBContext leaveDB;

    @Override
    public void init() {
        leaveDB = new LeaveRequestDBContext();
    }

    @Override
    protected void processPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {

    }

    @Override
    protected void processGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        List<LeaveRequests> requests = leaveDB.getByAccount(account.getAccountID());
        System.out.println(account.getAccountID());
        req.setAttribute("requests", requests);
        req.getRequestDispatcher("../view/leave/leaveList.jsp").forward(req, resp);
    }

}
