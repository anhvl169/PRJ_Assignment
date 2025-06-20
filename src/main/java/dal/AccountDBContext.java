/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import model.Account;

/**
 *
 * @author vulea
 */
public class AccountDBContext extends DBContext {

    public Account getAccountByUsernameAndPassword(String username, String password) {
        try (EntityManager em = getEntityManager()) {
            TypedQuery<Account> query = em.createQuery(
                    "SELECT a FROM Account a WHERE a.username = :username AND a.password = :password",
                    Account.class
            );
            query.setParameter("username", username);
            query.setParameter("password", password);

            return query.getSingleResult(); // trả về account nếu đúng
        } catch (NoResultException e) {
            return null; // không có user
        }
    }
}
