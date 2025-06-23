/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import model.LeaveRequests;

/**
 *
 * @author vulea
 */
public class LeaveRequestDBContext extends DBContext {

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
    }

