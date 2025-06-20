/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import jakarta.persistence.*;
import java.util.Date;

/**
 *
 * @author vulea
 */
@Entity
@Table(name = "LeaveRequests")
public class LeaveRequests {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int applicaID;

    @ManyToOne
    @JoinColumn(name = "createdBy", nullable = false)
    private Employee createdBy;

    @ManyToOne
    @JoinColumn(name = "approvedBy")
    private Employee approvedBy;

    private String applicaText;
    private Date fromDate;
    private Date toDate;
    private String reason;

    @Column(length = 20)
    private String status;

    public int getApplicaID() {
        return applicaID;
    }

    public void setApplicaID(int applicaID) {
        this.applicaID = applicaID;
    }

    public Employee getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Employee createdBy) {
        this.createdBy = createdBy;
    }

    public Employee getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(Employee approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getApplicaText() {
        return applicaText;
    }

    public void setApplicaText(String applicaText) {
        this.applicaText = applicaText;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
