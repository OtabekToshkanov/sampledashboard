package uz.greenwhite.sampledashboard.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import uz.greenwhite.sampledashboard.model.CompanyAnalytics;
import uz.greenwhite.sampledashboard.model.DailySales;
import uz.greenwhite.sampledashboard.model.DealSummary;
import uz.greenwhite.sampledashboard.model.UserContext;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnalyticsService {
    private final JdbcTemplate jdbcTemplate;
    private final UserService userService;

    public List<DealSummary> getDealSummary() {
        UserContext user = userService.getCurrentUser();

        String sql = """
                SELECT 
                    deal_kind,
                    multiIf(deal_kind = 'O', 'Order', deal_kind = 'R', 'Return', 'Unknown') as deal_type,
                    count() as deal_count,
                    sum(total_amount) as amount_sum,
                    avg(total_amount) as amount_avg,
                    arrayStringConcat(groupArray(DISTINCT status), ', ') as status_list
                FROM mdeal_headers 
                WHERE company_id = %d
                GROUP BY deal_kind
                ORDER BY deal_kind
                """.formatted(user.getCompanyId());

        return jdbcTemplate.query(sql, new RowMapper<DealSummary>() {
            @Override
            public DealSummary mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new DealSummary(
                        rs.getString("deal_kind"),
                        rs.getString("deal_type"),
                        rs.getLong("deal_count"),
                        rs.getBigDecimal("amount_sum"),
                        rs.getBigDecimal("amount_avg"),
                        rs.getString("status_list")
                );
            }
        });
    }

    public List<DailySales> getDailySales() {
        UserContext user = userService.getCurrentUser();

        String sql = """
                SELECT 
                    deal_date,
                    count() as deal_count,
                    sum(total_amount) as revenue_sum,
                    avg(total_amount) as deal_avg
                FROM mdeal_headers 
                WHERE deal_kind = 'O' AND company_id = %d
                GROUP BY deal_date
                ORDER BY deal_date DESC
                LIMIT 30
                """.formatted(user.getCompanyId());

        return jdbcTemplate.query(sql, new RowMapper<DailySales>() {
            @Override
            public DailySales mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new DailySales(
                        rs.getDate("deal_date").toLocalDate(),
                        rs.getLong("deal_count"),
                        rs.getBigDecimal("revenue_sum"),
                        rs.getBigDecimal("deal_avg")
                );
            }
        });
    }

    public List<CompanyAnalytics> getCompanyAnalytics() {
        UserContext user = userService.getCurrentUser();

        String sql = """
                SELECT 
                    company_id,
                    count() as deal_count,
                    sumIf(total_amount, deal_kind = 'O') as revenue_sum,
                    avgIf(total_amount, deal_kind = 'O') as deal_avg,
                    countIf(deal_kind = 'R') as return_count,
                    multiIf(
                        countIf(deal_kind = 'O') > 0, 
                        (countIf(deal_kind = 'R') * 100.0) / countIf(deal_kind = 'O'), 
                        0
                    ) as return_percentage
                FROM mdeal_headers 
                WHERE company_id IN %d
                GROUP BY company_id
                ORDER BY company_id
                """.formatted(user.getCompanyId());

        return jdbcTemplate.query(sql, new RowMapper<CompanyAnalytics>() {
            @Override
            public CompanyAnalytics mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new CompanyAnalytics(
                        rs.getLong("company_id"),
                        rs.getLong("deal_count"),
                        rs.getBigDecimal("revenue_sum"),
                        rs.getBigDecimal("deal_avg"),
                        rs.getLong("return_count"),
                        rs.getBigDecimal("return_percentage")
                );
            }
        });
    }

    public UserContext getCurrentUserContext() {
        return userService.getCurrentUser();
    }
}