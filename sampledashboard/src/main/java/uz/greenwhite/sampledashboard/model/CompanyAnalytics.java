package uz.greenwhite.sampledashboard.model;

import java.math.BigDecimal;

public class CompanyAnalytics {
    private Long companyId;
    private Long totalDeals;
    private BigDecimal totalRevenue;
    private BigDecimal avgDealSize;
    private Long returnsCount;
    private BigDecimal returnRate;

    public CompanyAnalytics() {
    }

    public CompanyAnalytics(Long companyId, Long totalDeals, BigDecimal totalRevenue,
                            BigDecimal avgDealSize, Long returnsCount, BigDecimal returnRate) {
        this.companyId = companyId;
        this.totalDeals = totalDeals;
        this.totalRevenue = totalRevenue;
        this.avgDealSize = avgDealSize;
        this.returnsCount = returnsCount;
        this.returnRate = returnRate;
    }

    // Getters and Setters
    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
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

    public Long getReturnsCount() {
        return returnsCount;
    }

    public void setReturnsCount(Long returnsCount) {
        this.returnsCount = returnsCount;
    }

    public BigDecimal getReturnRate() {
        return returnRate;
    }

    public void setReturnRate(BigDecimal returnRate) {
        this.returnRate = returnRate;
    }
}
