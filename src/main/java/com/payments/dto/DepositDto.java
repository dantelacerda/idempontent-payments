package com.payments.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DepositDto {
    private String depositId;
    private int userId;
    private double depositAmount;
    private String depositCurrency;
    private String status;
    private Date createdAt;
    private Date expiresAt;
    private String paymentMethodCode;

    public String getDepositId() {
        return depositId;
    }

    public void setDepositId(String depositId) {
        this.depositId = depositId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(double depositAmount) {
        this.depositAmount = depositAmount;
    }

    public String getDepositCurrency() {
        return depositCurrency;
    }

    public void setDepositCurrency(String depositCurrency) {
        this.depositCurrency = depositCurrency;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            this.createdAt = dateFormat.parse(createdAt);
        } catch (ParseException e) {
            e.printStackTrace(); // Handle parsing exception as needed
        }
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(String expiresAt) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            this.expiresAt = dateFormat.parse(expiresAt);
        } catch (ParseException e) {
            e.printStackTrace(); // Handle parsing exception as needed
        }
    }

    public String getPaymentMethodCode() {
        return paymentMethodCode;
    }

    public void setPaymentMethodCode(String paymentMethodCode) {
        this.paymentMethodCode = paymentMethodCode;
    }
}