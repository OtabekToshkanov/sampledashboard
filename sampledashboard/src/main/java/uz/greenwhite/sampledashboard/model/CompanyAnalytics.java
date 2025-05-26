package uz.greenwhite.sampledashboard.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CompanyAnalytics {
    private Long companyId;
    private Long totalDeals;
    private BigDecimal totalRevenue;
    private BigDecimal avgDealSize;
    private Long returnsCount;
    private BigDecimal returnRate;
}
