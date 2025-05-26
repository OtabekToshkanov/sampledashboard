# ClickHouse Analytics Dashboard

A Spring Boot application that provides real-time analytics dashboards for sales data stored in ClickHouse, with Keycloak OAuth2 authentication and company-based data isolation.

## Features

- **OAuth2 Authentication**: Secure login via Keycloak with company-based access control
- **Real-time Analytics**: Interactive dashboards showing deal summaries, daily sales, and company performance
- **Multi-tenant Support**: Data isolation based on user's company ID from Keycloak claims
- **ClickHouse Integration**: High-performance analytics on large datasets
- **Responsive UI**: Clean, modern interface with Thymeleaf templates

## Screenshots

### Dashboard View
- Deal summary by type (Orders vs Returns)
- Daily sales performance for last 30 days
- Company-wide analytics with return rates
- Quick metrics overview

### User Features
- Profile page showing user information and company assignment
- Secure logout with Keycloak session termination
- Company-based data filtering

## Prerequisites

- Java 17 or higher
- Docker and Docker Compose
- Keycloak server (configured with custom user attributes)
- Maven 3.6+

## Quick Start

### 1. Clone the repository
```bash
git clone https://github.com/yourusername/clickhouse-analytics-dashboard.git
cd clickhouse-analytics-dashboard
```

### 2. Start ClickHouse
```bash
docker-compose up -d
```

### 3. Create database schema
```bash
chmod +x mdeal_headers_clickhouse.sh
./mdeal_headers_clickhouse.sh
```

### 4. Load sample data
```bash
chmod +x insert_sample_data.sh
./insert_sample_data.sh
```

### 5. Configure Keycloak
Update `.env` file with your Keycloak settings:
```env
KEYCLOAK_CLIENT_ID=sampledashboard
KEYCLOAK_CLIENT_SECRET=your-client-secret
KEYCLOAK_ISSUER_URI=http://your-keycloak-server/realms/your-realm
```

### 6. Run the application
```bash
mvn spring-boot:run
```

The application will be available at `http://localhost:8181`

## Configuration

### Keycloak Setup

The application expects the following custom attributes in Keycloak user tokens:

- `companyId` - The company ID for data filtering (Long)
- `userId` - The user's unique identifier (Long)
- `fullName` - The user's display name (String)

### Application Properties

Key configuration in `application.yaml`:

```yaml
spring:
  datasource:
    url: jdbc:clickhouse://localhost:8123/default
    username: default
    password: ""

app:
  keycloak:
    company-id-attribute: companyId
    user-id-attribute: userId
    name-attribute: fullName
```

## Project Structure

```
src/main/java/uz/greenwhite/sampledashboard/
├── config/
│   ├── ClickHouseConfig.java    # ClickHouse datasource configuration
│   └── SecurityConfig.java       # Spring Security OAuth2 setup
├── controller/
│   ├── AnalyticsController.java  # Main dashboard endpoints
│   └── LogoutController.java     # Keycloak logout handling
├── model/
│   ├── CompanyAnalytics.java    # Company performance metrics
│   ├── DailySales.java          # Daily sales data
│   ├── DealSummary.java         # Deal type summaries
│   └── UserContext.java         # User session information
├── service/
│   ├── AnalyticsService.java    # Business logic for analytics
│   └── UserService.java         # User context extraction
└── SampledashboardApplication.java
```

## Database Schema

The application uses a single ClickHouse table `mdeal_headers` with the following key columns:

- `company_id` - Company identifier for multi-tenancy
- `deal_id` - Unique deal identifier
- `deal_kind` - 'O' for Order, 'R' for Return
- `deal_date` - Transaction date
- `total_amount` - Deal amount
- `status` - Deal status

## API Endpoints

- `GET /` - Main dashboard page
- `GET /profile` - User profile page
- `GET /logout` - Logout endpoint (terminates both app and Keycloak sessions)
- `GET /logged-out` - Post-logout confirmation page

## Security

- All endpoints except `/logged-out` require authentication
- Data access is automatically filtered by the user's company ID
- Refresh tokens are properly handled during logout
- Session cookies are cleared on logout

## Development

### Running Tests
```bash
mvn test
```

### Building for Production
```bash
mvn clean package
java -jar target/sampledashboard-0.0.1-SNAPSHOT.jar
```

### Docker Build
```bash
docker build -t clickhouse-dashboard .
docker run -p 8181:8181 clickhouse-dashboard
```

## Troubleshooting

### ClickHouse Connection Issues
- Ensure ClickHouse is running: `docker ps`
- Check connection settings in `application.yaml`
- Verify ClickHouse is accessible: `curl http://localhost:8123/`

### Keycloak Authentication Issues
- Verify client credentials in `.env`
- Check that custom user attributes are properly mapped in Keycloak
- Ensure redirect URIs are configured in Keycloak client settings

### No Data Showing
- Verify sample data was loaded: check the output of `insert_sample_data.sh`
- Ensure user's `companyId` attribute matches data in the database
- Check application logs for SQL errors

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments

- Built with Spring Boot and Spring Security
- Uses ClickHouse for high-performance analytics
- Secured with Keycloak OAuth2/OpenID Connect
- UI templates with Thymeleaf