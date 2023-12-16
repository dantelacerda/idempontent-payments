package com.payments.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CardPaymentDto {
    private String paymentId;
    private int cardId;
    private int userId;
    private double paymentAmount;
    private String paymentCurrency;
    private String status;
    private Date createdAt;
    private String merchantName;
    private int merchantId;
    private int mccCode;

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getPaymentCurrency() {
        return paymentCurrency;
    }

    public void setPaymentCurrency(String paymentCurrency) {
        this.paymentCurrency = paymentCurrency;
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

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public int getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(int merchantId) {
        this.merchantId = merchantId;
    }

    public int getMccCode() {
        return mccCode;
    }

    public void setMccCode(int mccCode) {
        this.mccCode = mccCode;
    }
}