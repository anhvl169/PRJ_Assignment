/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import model.Division;

/**
 *
 * @author vulea
 */
public class DivisionDBContext extends DBContext {

    public List<Division> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Division getById(int divisionID) {
        try (EntityManager em = getEntityManager()) {
            return em.find(Division.class, divisionID);
        }
    }

    public List<Division> list() {
        try (EntityManager em = getEntityManager()) {
            TypedQuery<Division> query = em.createQuery("SELECT d FROM Division d", Division.class);
            return query.getResultList();
        }
    }

}
