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
@Table(name = "Accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int accountID;

    private String username;
    private String password;
    @OneToOne
    @JoinColumn(name = "employeeID")
    private Employee employee;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "Account_Role",
            joinColumns = @JoinColumn(name = "accountID"),
            inverseJoinColumns = @JoinColumn(name = "roleID")
    )
    private List<Role> roles;

    // Getters + setters
    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

}
