package uz.greenwhite.sampledashboard.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DailySales {
    private LocalDate dealDate;
    private Long totalDeals;
    private BigDecimal totalRevenue;
    private BigDecimal avgDealSize;

    public DailySales() {
    }

    public DailySales(LocalDate dealDate, Long totalDeals, BigDecimal totalRevenue, BigDecimal avgDealSize) {
        this.dealDate = dealDate;
        this.totalDeals = totalDeals;
        this.totalRevenue = totalRevenue;
        this.avgDealSize = avgDealSize;
    }

    // Getters and Setters
    public LocalDate getDealDate() {
        return dealDate;
    }

    public void setDealDate(LocalDate dealDate) {
        this.dealDate = dealDate;
    }

    public Long getTotalDeals() {
        return totalDeals;
    }

    public void setTotalDeals(Long totalDeals) {
        this.totalDeals = totalDeals;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public BigDecimal getAvgDealSize() {
        return avgDealSize;
    }

    public void setAvgDealSize(BigDecimal avgDealSize) {
        this.avgDealSize = avgDealSize;
    }
}