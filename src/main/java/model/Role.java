/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import jakarta.persistence.*;
import java.util.List;

/**
 *
 * @author vulea
 */
@Entity
@Table(name = "Roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roleID;

    private String roleName;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "Role_Feature",
            joinColumns = @JoinColumn(name = "roleID"),
            inverseJoinColumns = @JoinColumn(name = "featureID")
    )
    private List<Feature> features;

    // Getters + setters
    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }

}
