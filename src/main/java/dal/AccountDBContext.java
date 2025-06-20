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

/**
 *
 * @author vulea
 */
public class AccountDBContext extends DBContext {

    public Account getAccountByUsernameAndPassword(String username, String password) {
        EntityManager em = getEntityManager();
        try {
            // JOIN FETCH để load roles và features luôn khi lấy account
            String jpql = "SELECT DISTINCT a FROM Account a "
                    + "LEFT JOIN FETCH a.roles r "
                    + "LEFT JOIN FETCH r.features "
                    + "WHERE a.username = :username AND a.password = :password";

            return em.createQuery(jpql, Account.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close(); // Quan trọng để tránh leak connection
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
}
