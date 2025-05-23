package uz.greenwhite.sampledashboard.model;

import java.math.BigDecimal;

public class DealSummary {
    private String dealKind;
    private String dealType;
    private Long totalDeals;
    private BigDecimal totalAmount;
    private BigDecimal avgAmount;
    private String status;

    public DealSummary() {
    }

    public DealSummary(String dealKind, String dealType, Long totalDeals,
                       BigDecimal totalAmount, BigDecimal avgAmount, String status) {
        this.dealKind = dealKind;
        this.dealType = dealType;
        this.totalDeals = totalDeals;
        this.totalAmount = totalAmount;
        this.avgAmount = avgAmount;
        this.status = status;
    }

    // Getters and Setters
    public String getDealKind() {
        return dealKind;
    }

    public void setDealKind(String dealKind) {
        this.dealKind = dealKind;
    }

    public String getDealType() {
        return dealType;
    }

    public void setDealType(String dealType) {
        this.dealType = dealType;
    }

    public Long getTotalDeals() {
        return totalDeals;
    }

    public void setTotalDeals(Long totalDeals) {
        this.totalDeals = totalDeals;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getAvgAmount() {
        return avgAmount;
    }

    public void setAvgAmount(BigDecimal avgAmount) {
        this.avgAmount = avgAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}