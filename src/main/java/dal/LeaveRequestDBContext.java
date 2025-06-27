/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import model.Employee;
import model.LeaveRequests;

/**
 *
 * @author vulea
 */
public class LeaveRequestDBContext extends DBContext {
    private static final Logger LOGGER = Logger.getLogger(LeaveRequestDBContext.class.getName());
    public void insert(LeaveRequests request) {
        try (EntityManager em = getEntityManager()) {
            em.getTransaction().begin();
            em.persist(request);
            em.getTransaction().commit();
        }
    }

    public List<LeaveRequests> getByEmployee(int empId) {
        try (EntityManager em = getEntityManager()) {
            String jpql = "SELECT r FROM LeaveRequests r WHERE r.employee.employeeID = :eid";
            TypedQuery<LeaveRequests> query = em.createQuery(jpql, LeaveRequests.class);
            query.setParameter("eid", empId);
            return query.getResultList();
        }
    }

    //xem đơn của mình
    public List<LeaveRequests> getByAccount(int accountID) {
        try (EntityManager em = getEntityManager()) {
            String jpql = "SELECT r FROM LeaveRequests r WHERE r.createdBy.accountID = :aid";
            TypedQuery<LeaveRequests> query = em.createQuery(jpql, LeaveRequests.class);
            query.setParameter("aid", accountID);
            return query.getResultList();
        }
    }

    public List<LeaveRequests> getPendingRequests() {
        try (EntityManager em = getEntityManager()) {
            String jpql = "SELECT r FROM LeaveRequests r WHERE r.status = 'Inprogress'";
            return em.createQuery(jpql, LeaveRequests.class).getResultList();
        }
    }

    public LeaveRequests findById(int id) {
        try (EntityManager em = getEntityManager()) {
            return em.find(LeaveRequests.class, id);
        }
    }

    public void update(LeaveRequests request) {
        try (EntityManager em = getEntityManager()) {
            em.getTransaction().begin();
            em.merge(request);
            em.getTransaction().commit();
        }
    }

    //lấy đơn cấp dưới
    public List<LeaveRequests> getAllSubordinateRequests(int managerEmployeeId) {
        EntityManager em = getEntityManager();
        try {
            String sql = "            WITH EmployeeHierarchy AS (\n"
                    + "                SELECT e.EmployeeID, e.ManagerID\n"
                    + "                FROM Employees e\n"
                    + "                WHERE e.EmployeeID = :managerId\n"
                    + "\n"
                    + "                UNION ALL\n"
                    + "\n"
                    + "                SELECT e.EmployeeID, e.ManagerID\n"
                    + "                FROM Employees e\n"
                    + "                INNER JOIN EmployeeHierarchy eh ON e.ManagerID = eh.EmployeeID\n"
                    + "            )\n"
                    + "            SELECT lr.*\n"
                    + "            FROM EmployeeHierarchy eh\n"
                    + "            INNER JOIN Accounts a ON a.EmployeeID = eh.EmployeeID\n"
                    + "            INNER JOIN LeaveRequests lr ON lr.createdBy = a.AccountID";

            return em.createNativeQuery(sql, LeaveRequests.class)
                    .setParameter("managerId", managerEmployeeId)
                    .getResultList();

        } finally {
            em.close();
        }
    }

//    public List<LeaveRequests> getApprovedInRange(java.sql.Date fromDate, java.sql.Date toDate) {
//        EntityManager em = getEntityManager();
//        try {
//            return em.createQuery(
//                    "SELECT lr FROM LeaveRequests lr WHERE lr.status = 'Approved' AND lr.fromDate <= :toDate AND lr.toDate >= :fromDate", LeaveRequests.class)
//                    .setParameter("fromDate", fromDate)
//                    .setParameter("toDate", toDate)
//                    .getResultList();
//        } finally {
//            if (em != null && em.isOpen()) {
//                em.close();
//            }
//        }
//    }
    public List<LeaveRequests> getApprovedInRange(java.sql.Date fromDate, java.sql.Date toDate) {
        EntityManager em = getEntityManager();
        try {
            LOGGER.info("Querying leave requests from " + fromDate + " to " + toDate);
            Query query = em.createQuery(
                    "SELECT lr FROM LeaveRequests lr WHERE lr.status = 'Approved' AND lr.fromDate <= :toDate AND lr.toDate >= :fromDate", LeaveRequests.class)
                    .setParameter("fromDate", fromDate)
                    .setParameter("toDate", toDate);
            List<LeaveRequests> results = query.getResultList();
            LOGGER.info("Found " + results.size() + " approved leave requests");
            for (LeaveRequests lr : results) {
                LOGGER.info("Leave Request: EmployeeID=" + (lr.getCreatedBy() != null && lr.getCreatedBy().getEmployee() != null
                        ? lr.getCreatedBy().getEmployee().getEmployeeID() : "null")
                        + ", From=" + lr.getFromDate() + ", To=" + lr.getToDate() + ", Status=" + lr.getStatus());
            }
            return results;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}
