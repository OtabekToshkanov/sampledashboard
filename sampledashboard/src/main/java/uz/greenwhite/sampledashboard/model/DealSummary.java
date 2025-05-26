package uz.greenwhite.sampledashboard.model;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DealSummary {
    private String dealKind;
    private String dealType;
    private Long totalDeals;
    private BigDecimal totalAmount;
    private BigDecimal avgAmount;
    private String status;
}