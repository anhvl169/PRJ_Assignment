/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.List;

/**
 *
 * @author vulea
 */
public class EmployeeDaysOff {

    public int id;
    public String name;
    public List<String> daysOff;

    public EmployeeDaysOff(int id, String name, List<String> daysOff) {
        this.id = id;
        this.name = name;
        this.daysOff = daysOff;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getDaysOff() {
        return daysOff;
    }

    public void setDaysOff(List<String> daysOff) {
        this.daysOff = daysOff;
    }

}
