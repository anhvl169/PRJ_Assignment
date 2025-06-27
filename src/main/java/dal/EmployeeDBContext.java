/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import model.Employee;

/**
 *
 * @author vulea
 */
public class EmployeeDBContext extends DBContext {

    public void insert(Employee employee) {
        try (EntityManager entityManager = getEntityManager()) {
            entityManager.getTransaction().begin();
            entityManager.persist(employee);
            entityManager.getTransaction().commit();
        }
    }

    public List<Employee> list() {
        try (EntityManager entityManager = getEntityManager()) {
            List<Employee> employees = entityManager.createQuery(
                    "SELECT e FROM Employee e LEFT JOIN FETCH e.division", Employee.class
            ).getResultList();
            System.out.println("Loaded employees: " + employees.size());
            return employees;
        }
    }

    public List<Employee> search(String name, String gender, String address, String divisionID) {
        try (EntityManager em = getEntityManager()) {
            StringBuilder jpql = new StringBuilder("SELECT e FROM Employee e WHERE 1=1");

            if (name != null && !name.isBlank()) {
                jpql.append(" AND e.name LIKE :name");
            }
            if (gender != null && !gender.equalsIgnoreCase("all")) {
                jpql.append(" AND e.gender = :gender");
            }
            if (address != null && !address.isBlank()) {
                jpql.append(" AND e.address LIKE :address");
            }
            if (divisionID != null && !divisionID.isBlank()) {
                jpql.append(" AND e.division.divisionID = :divisionID");
            }

            TypedQuery<Employee> query = em.createQuery(jpql.toString(), Employee.class);

            if (name != null && !name.isBlank()) {
                query.setParameter("name", "%" + name + "%");
            }
            if (gender != null && !gender.equalsIgnoreCase("all")) {
                query.setParameter("gender", gender.equals("true")); // 'true' → true
            }
            if (address != null && !address.isBlank()) {
                query.setParameter("address", "%" + address + "%");
            }
            if (divisionID != null && !divisionID.isBlank()) {
                query.setParameter("divisionID", Integer.parseInt(divisionID));
            }

            return query.getResultList();
        }
    }

    public List<Employee> getGroupLeaders() {
        EntityManager em = getEntityManager();
        String jpql = "       SELECT DISTINCT e FROM Employee e\n"
                + "        JOIN e.account a\n"
                + "        JOIN a.roles r\n"
                + "        WHERE r.roleName = 'Groupleader' ";

        TypedQuery<Employee> query = em.createQuery(jpql, Employee.class);
        return query.getResultList();
    }

    public Employee getById(int id) {
        try (EntityManager em = getEntityManager()) {
            return em.find(Employee.class, id);
        }
    }

    public List<Employee> getAll() {
        try (EntityManager em = getEntityManager()) {
            return em.createQuery("SELECT e FROM Employee e", Employee.class).getResultList();
        }
    }

    public List<Employee> getByDivision(int divisionID) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT e FROM Employee e WHERE e.division.divisionID = :did";
            TypedQuery<Employee> query = em.createQuery(jpql, Employee.class);
            query.setParameter("did", divisionID);
            return query.getResultList();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public List<Employee> getByManager(int managerID) {
        try (EntityManager em = getEntityManager()) {
            String jpql = "SELECT e FROM Employee e WHERE e.manager.employeeID = :mid";
            TypedQuery<Employee> query = em.createQuery(jpql, Employee.class);
            query.setParameter("mid", managerID);
            return query.getResultList();
        }
    }
}
