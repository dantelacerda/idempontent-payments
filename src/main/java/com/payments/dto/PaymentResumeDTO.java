package com.payments.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.payments.util.Constants;

import java.util.Date;

public class PaymentResumeDTO {

    @JsonProperty("payment_id")
    protected String paymentId;
    @JsonProperty("payment_type")
    protected String paymentType;
    @JsonProperty("status")
    protected String paymentStatus;
    @JsonProperty("transfer_amount")
    protected double transferAmount;
    @JsonProperty("transfer_currency")
    protected String transferCurrency;
    @JsonProperty("created_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;

    public PaymentResumeDTO(PaymentParametersDTO paymentParametersDTO) {
        if(paymentParametersDTO instanceof CardPaymentDto) {
            CardPaymentDto card = (CardPaymentDto) paymentParametersDTO;
            this.paymentId = card.getPaymentId();
            this.paymentType = Constants.CREDIT_CARD_PAYMENT_TYPE;
            this.transferAmount = card.getPaymentAmount();
            this.transferCurrency = card.getPaymentCurrency();
            this.createdAt = card.getCreatedAt();
            this.paymentStatus = card.getStatus();
        } else if(paymentParametersDTO instanceof DepositDto) {
            DepositDto deposit = (DepositDto) paymentParametersDTO;
            this.paymentId = deposit.getDepositId();
            this.paymentType = Constants.DEPOSIT_PAYMENT_TYPE;
            this.transferAmount = deposit.getDepositAmount();
            this.transferCurrency = deposit.getDepositCurrency();
            this.createdAt = deposit.getCreatedAt();
            this.paymentStatus = deposit.getStatus();
        } else if(paymentParametersDTO instanceof P2PDto) {
            P2PDto p2p = (P2PDto) paymentParametersDTO;
            this.paymentId = p2p.getTransferId();
            this.paymentType = Constants.P2P_PAYMENT_TYPE;
            this.transferAmount = p2p.getTransferAmount();
            this.transferCurrency = p2p.getTransferCurrency();
            this.createdAt = p2p.getCreatedAt();
            this.paymentStatus = p2p.getStatus();
        }
    }

    public PaymentResumeDTO(){}

    public PaymentResumeDTO(String paymentId, String paymentType, String paymentStatus,
                      double transferAmount, String transferCurrency, Date createdAt) {
        this.paymentId = paymentId;
        this.paymentType = paymentType;
        this.paymentStatus = paymentStatus;
        this.transferAmount = transferAmount;
        this.transferCurrency = transferCurrency;
        this.createdAt = createdAt;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public double getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(double transferAmount) {
        this.transferAmount = transferAmount;
    }

    public String getTransferCurrency() {
        return transferCurrency;
    }

    public void setTransferCurrency(String transferCurrency) {
        this.transferCurrency = transferCurrency;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

}
