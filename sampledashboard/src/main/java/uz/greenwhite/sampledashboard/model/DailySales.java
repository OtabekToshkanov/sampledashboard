package uz.greenwhite.sampledashboard.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DailySales {
    private LocalDate dealDate;
    private Long totalDeals;
    private BigDecimal totalRevenue;
    private BigDecimal avgDealSize;
}