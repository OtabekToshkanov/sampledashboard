# ClickHouse MDeal Headers Project

A ClickHouse database project for managing deal header records with sample data and Docker containerization.

## Overview

This project provides a complete setup for a ClickHouse database with a `mdeal_headers` table designed to store comprehensive deal/transaction information. The project includes table creation scripts, sample data insertion, and Docker configuration for easy deployment.

## Features

- **ClickHouse Database**: High-performance columnar database optimized for analytics
- **Deal Management**: Comprehensive table structure for storing deal headers and metadata
- **Docker Support**: Easy deployment using Docker Compose
- **Sample Data**: Pre-configured sample data for testing and development
- **Automated Scripts**: Bash scripts for table creation and data insertion

## Project Structure

```
├── docker-compose.yml          # Docker Compose configuration
├── create-table.sh            # Table creation script
├── insert-sample-data.sh      # Sample data insertion script
└── README.md                  # This file
```

## Prerequisites

- Docker and Docker Compose
- Bash shell (for running scripts)
- curl (for HTTP requests to ClickHouse)

## Quick Start

### 1. Start ClickHouse Server

```bash
docker-compose up -d
```

This will start a ClickHouse server with the following ports:
- **8123**: HTTP interface
- **9000**: Native TCP interface  
- **9009**: HTTPS interface

### 2. Create the Table

```bash
chmod +x create-table.sh
./create-table.sh
```

### 3. Insert Sample Data

```bash
chmod +x insert-sample-data.sh
./insert-sample-data.sh
```

### 4. Verify Installation

Access ClickHouse web interface at: `http://localhost:8123/play`

Or query via curl:
```bash
curl -X POST "http://localhost:8123/" \
  --user "default:" \
  --data "SELECT COUNT(*) FROM mdeal_headers;"
```

## Table Schema

The `mdeal_headers` table contains the following key fields:

| Field | Type | Description |
|-------|------|-------------|
| `company_id` | UInt64 | Company identifier |
| `deal_id` | UInt64 | Unique deal identifier |
| `deal_kind` | FixedString(1) | Deal type (O=Order, R=Return) |
| `deal_date` | Date | Date of the deal |
| `status` | String | Current deal status |
| `total_amount` | Decimal64(6) | Total deal amount |
| `currency_id` | UInt64 | Currency identifier |
| `person_id` | UInt64 | Customer identifier |
| `delivery_address_short` | String | Delivery address |

### Table Features

- **Partitioning**: By month (`toYYYYMM(deal_date)`)
- **Primary Key**: `(company_id, deal_id)`
- **Engine**: MergeTree for optimal performance
- **Index Granularity**: 8192 for balanced performance

## Sample Data

The project includes 5 sample records representing different deal scenarios:

1. **Regular Sale**: Standard product sale with delivery
2. **Return**: Product return with negative amounts
3. **Wholesale Order**: Large business order
4. **E-commerce**: Online store purchase
5. **Cash Sale**: Walk-in cash transaction

## Configuration

### Docker Environment Variables

```yaml
environment:
  - CLICKHOUSE_DB=default
  - CLICKHOUSE_USER=default
  - CLICKHOUSE_DEFAULT_ACCESS_MANAGEMENT=1
```

### Connection Details

- **Host**: localhost
- **HTTP Port**: 8123
- **User**: default
- **Password**: (empty)

## Usage Examples

### Query Total Sales by Company

```sql
SELECT 
    company_id,
    COUNT(*) as deal_count,
    SUM(total_amount) as total_sales
FROM mdeal_headers 
WHERE deal_kind = 'O'
GROUP BY company_id;
```

### Get Recent Deals

```sql
SELECT 
    deal_id,
    deal_date,
    status,
    total_amount,
    delivery_address_short
FROM mdeal_headers 
WHERE deal_date >= today() - 30
ORDER BY deal_date DESC;
```

### Analyze Deal Types

```sql
SELECT 
    deal_kind,
    CASE 
        WHEN deal_kind = 'O' THEN 'Order'
        WHEN deal_kind = 'R' THEN 'Return'
        ELSE 'Unknown'
    END as deal_type,
    COUNT(*) as count,
    AVG(total_amount) as avg_amount
FROM mdeal_headers
GROUP BY deal_kind;
```

## Data Management

### Backup Data

```bash
# Export data to CSV
curl -X POST "http://localhost:8123/" \
  --user "default:" \
  --data "SELECT * FROM mdeal_headers FORMAT CSV" > backup.csv
```

### Clear Data

```bash
# Truncate table
curl -X POST "http://localhost:8123/" \
  --user "default:" \
  --data "TRUNCATE TABLE mdeal_headers;"
```

## Monitoring

### Check Table Size

```sql
SELECT 
    table,
    formatReadableSize(sum(bytes)) as size,
    sum(rows) as rows
FROM system.parts 
WHERE table = 'mdeal_headers'
GROUP BY table;
```

### Performance Monitoring

```sql
-- Query execution statistics
SELECT * FROM system.query_log 
WHERE query LIKE '%mdeal_headers%' 
ORDER BY event_time DESC 
LIMIT 10;
```

## Development

### Adding New Fields

1. Modify the table schema in `create-table.sh`
2. Update sample data in `insert-sample-data.sh`
3. Recreate the table:
   ```bash
   # Drop existing table
   curl -X POST "http://localhost:8123/" --user "default:" --data "DROP TABLE mdeal_headers;"
   
   # Recreate with new schema
   ./create-table.sh
   ./insert-sample-data.sh
   ```

### Custom Configuration

Modify `docker-compose.yml` to adjust:
- Memory limits
- Port mappings
- Volume mounts
- Environment variables

## Troubleshooting

### Connection Issues

```bash
# Test connection
curl -v http://localhost:8123/

# Check container logs
docker logs clickhouse-server
```

### Permission Issues

```bash
# Fix script permissions
chmod +x *.sh

# Check ClickHouse user permissions
docker exec -it clickhouse-server clickhouse-client --query "SHOW USERS"
```

### Data Issues

```bash
# Verify table exists
curl -X POST "http://localhost:8123/" \
  --user "default:" \
  --data "SHOW TABLES;"

# Check table structure
curl -X POST "http://localhost:8123/" \
  --user "default:" \
  --data "DESCRIBE TABLE mdeal_headers;"
```
