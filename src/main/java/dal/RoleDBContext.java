/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import model.Role;

/**
 *
 * @author vulea
 */
public class RoleDBContext extends DBContext {

    public ArrayList<Role> getByAccount(int accountId) {
        try (EntityManager em = getEntityManager()) {
            String jpql = "SELECT DISTINCT r FROM Account a JOIN a.roles r "
                    + "LEFT JOIN FETCH r.features "
                    + "WHERE a.accountID = :accountId";

            TypedQuery<Role> query = em.createQuery(jpql, Role.class);
            query.setParameter("accountId", accountId);
            List<Role> roles = query.getResultList();
            return new ArrayList<>(roles);
        }
    }

    public Role getByName(String roleName) {
        try (EntityManager em = getEntityManager()) {
            String jpql = "SELECT r FROM Role r WHERE r.roleName = :roleName";
            return em.createQuery(jpql, Role.class)
                    .setParameter("roleName", roleName)
                    .getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Role> list() {
        try (EntityManager em = getEntityManager()) {
            String jpql = "SELECT r FROM Role r";
            List<Role> roles = em.createQuery(jpql, Role.class).getResultList();
            return new ArrayList<>(roles);
        }
    }

    public Role getById(int id) {
        try (EntityManager em = getEntityManager()) {
            return em.find(Role.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
