/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.util.List;
import model.Account;
import model.Role;

/**
 *
 * @author vulea
 */
public class AccountDBContext extends DBContext {

//    public Account getAccountByUsernameAndPassword(String username, String password) {
//        EntityManager em = getEntityManager();
//        try {
//            // Bước 1: Lấy account + roles
//            Account acc = em.createQuery(
//                    "SELECT DISTINCT a FROM Account a "
//                    + "LEFT JOIN FETCH a.roles r "
//                    + "WHERE a.username = :username AND a.password = :password", Account.class)
//                    .setParameter("username", username)
//                    .setParameter("password", password)
//                    .getSingleResult();
//
//            for (Role role : acc.getRoles()) {
//                role.getFeatures().size();
//            }
//
//            return acc;
//        } catch (NoResultException e) {
//            return null;
//        } finally {
//            em.close();
//        }
//    }
    public Account getAccountByUsernameAndPassword(String username, String password) {
        EntityManager em = getEntityManager();
        try {
            // Use a transaction for consistency
            em.getTransaction().begin();

            // Fetch account with roles, but limit features to avoid excessive memory use
            Account acc = em.createQuery(
                    "SELECT DISTINCT a FROM Account a "
                    + "LEFT JOIN FETCH a.roles r "
                    + "WHERE a.username = :username AND a.password = :password", Account.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .getSingleResult();

            // Initialize features lazily if needed, but avoid forcing size() unless necessary
            // Remove the loop unless features are required immediately
             for (Role role : acc.getRoles()) {
                 role.getFeatures().size(); // Comment out unless needed
             }
            em.getTransaction().commit();
            return acc;
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e; // Re-throw to log or handle higher up
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public List<Account> list() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT a FROM Account a", Account.class).getResultList();
        } finally {
            em.close();
        }
    }

    public Account get(String username, String password) {
        try (EntityManager em = getEntityManager()) {
            String jpql = "SELECT a FROM Account a "
                    + "JOIN FETCH a.employee "
                    + "LEFT JOIN FETCH a.roles r "
                    + "LEFT JOIN FETCH r.features "
                    + "WHERE a.username = :username AND a.password = :password";
            return em.createQuery(jpql, Account.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
