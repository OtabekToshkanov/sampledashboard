package uz.greenwhite.sampledashboard.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class ClickHouseConfig {
    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.clickhouse.jdbc.ClickHouseDriver");

        // Build URL with explicit parameters
        String fullUrl = "jdbc:clickhouse://localhost:8123/default" +
                "?user=" + username +
                "&password=" + (password != null ? password : "") +
                "&ssl=false" +
                "&compress=false" +
                "&socket_timeout=30000" +
                "&connection_timeout=10000";

        dataSource.setUrl(fullUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password != null ? password : "");

        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}