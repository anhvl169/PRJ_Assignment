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
    private int requestID;

    @ManyToOne
    @JoinColumn(name = "createdBy", nullable = false)
    private Account createdBy;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fromDate;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date toDate;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String reason;

    @Column(length = 255)
    private String applicaText;

    @Column(length = 20, nullable = false)
    private String status = "Inprogress";

    @ManyToOne
    @JoinColumn(name = "approvedBy")
    private Account approvedBy;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate = new Date();

    public int getRequestID() {
        return requestID;
    }

    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }

    public Account getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Account createdBy) {
        this.createdBy = createdBy;
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

    public String getApplicaText() {
        return applicaText;
    }

    public void setApplicaText(String applicaText) {
        this.applicaText = applicaText;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Account getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(Account approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

}
