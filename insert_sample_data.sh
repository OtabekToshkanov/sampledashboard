#!/bin/bash

# ClickHouse connection details
CLICKHOUSE_HOST="localhost"
CLICKHOUSE_PORT="8123"
CLICKHOUSE_USER="default"
CLICKHOUSE_PASSWORD="your_secure_password_here"

echo "Inserting sample data into mdeal_headers table..."

# Insert sample data
curl -X POST "http://${CLICKHOUSE_HOST}:${CLICKHOUSE_PORT}/" \
  --user "${CLICKHOUSE_USER}:${CLICKHOUSE_PASSWORD}" \
  --data "
INSERT INTO mdeal_headers (
    company_id, deal_id, filial_id, subfilial_id, room_id, person_id, owner_person_id,
    corr_extra_ref_set, currency_id, deal_kind, deal_time, deal_date, delivery_date,
    delivery_number, booked_date, base_status, book_status, status, robot_id,
    sales_manager_id, manager_id, expeditor_id, van_id, contract_id, invoice_number,
    batch_number, order_deal_id, exchange_deal_id, margin_kind, margin_value,
    margin_amount, margin_amount_base, vat_amount, total_amount, total_amount_base,
    total_amount_without_margin, total_amount_base_without_margin, booked_payment_amount,
    booked_payment_amount_base, payment_type_id, source_table, source_id, note,
    return_reason_id, delivery_address_short, delivery_address_full, delivery_latlng,
    code, barcode, with_marking, marking_attaching_method, self_shipment,
    mml_plan_product_count, mml_fact_product_count, cash_register_id,
    sent_virtual_cashbox, c_has_action, c_has_gift, c_has_margin, c_has_return,
    c_has_visit, c_total_weight_netto, c_total_weight_brutto, c_total_litr,
    created_by, created_on, modified_by, modified_on, modified_id
) VALUES
(
    1001, 1001001, 101, 201, 301, 501, 502,
    'REF-2024-001', 840, 'O', '2024-03-15 10:30:45', '2024-03-15', '2024-03-16',
    'DEL-001', '2024-03-15', 'A', 'CONFIRMED', 'ACTIVE', 401,
    601, 701, 801, 901, 1201, 'INV-2024-001',
    NULL, NULL, NULL, 'P', 15.50,
    125.75, 125.75, 25.15, 1000.00, 1000.00,
    874.25, 874.25, 800.00,
    800.00, 1301, 'POS_SALES', 2001, 'First sample deal',
    NULL, '123 Main St', '123 Main Street, City, State 12345', '40.7128,-74.0060',
    'DEAL001', '1234567890123', 'Y', 'O', 'N',
    5, 5, 1401,
    'Y', 'N', 'Y', 'Y', 'N',
    'N', 12.5000, 15.2500, 8.7500,
    1501, '2024-03-15 10:30:45', 1501, '2024-03-15 10:30:45', 1
),
(
    1001, 1001002, 101, NULL, 301, 503, 502,
    NULL, 840, 'R', '2024-03-16 14:20:30', '2024-03-16', '2024-03-16',
    'RET-001', '2024-03-16', 'B', 'RETURNED', 'RETURNED', 401,
    602, 701, NULL, NULL, NULL, 'RET-INV-001',
    2001, 1001001, NULL, 'A', 0.00,
    -50.25, -50.25, -10.05, -400.00, -400.00,
    -349.75, -349.75, -400.00,
    -400.00, 1302, 'RETURNS', 2002, 'Product return - defective item',
    1601, '123 Main St', '123 Main Street, City, State 12345', '40.7128,-74.0060',
    'RET001', '1234567890124', 'N', NULL, 'Y',
    2, 2, 1401,
    'N', 'N', 'N', 'N', 'Y',
    'N', 3.2500, 4.1000, 2.1500,
    1502, '2024-03-16 14:20:30', 1502, '2024-03-16 14:20:30', 2
),
(
    1002, 1002001, 102, 202, 302, 504, 505,
    'REF-2024-002', 978, 'O', '2024-03-17 09:15:22', '2024-03-17', '2024-03-18',
    'DEL-002', '2024-03-17', 'D', 'PENDING', 'DRAFT', 402,
    603, 702, 902, 1001, 1202, 'INV-2024-002',
    NULL, NULL, NULL, 'P', 12.00,
    360.00, 375.84, 72.00, 3000.00, 3132.00,
    2640.00, 2756.16, 2500.00,
    2610.00, 1303, 'WHOLESALE', 2003, 'Large wholesale order',
    NULL, '456 Business Ave', '456 Business Avenue, Industrial District, City 54321', '40.7589,-73.9851',
    'DEAL002', '1234567890125', 'Y', 'V', 'N',
    25, 25, 1402,
    'Y', 'Y', 'N', 'Y', 'N',
    'Y', 125.7500, 150.2500, 45.5000,
    1503, '2024-03-17 09:15:22', 1503, '2024-03-17 09:15:22', 3
),
(
    1001, 1001003, 103, 203, 303, 506, 502,
    'ONLINE-2024-001', 840, 'O', '2024-03-18 16:45:10', '2024-03-18', '2024-03-20',
    'SHIP-001', '2024-03-18', 'B', 'BOOKED', 'PROCESSING', 403,
    604, 703, 903, 1101, 1203, 'E-INV-001',
    NULL, NULL, NULL, 'A', 75.00,
    75.00, 75.00, 15.00, 500.00, 500.00,
    425.00, 425.00, 450.00,
    450.00, 1304, 'ECOMMERCE', 2004, 'Online store purchase',
    NULL, '789 Residential St', '789 Residential Street, Suburb, City 67890', '40.6782,-73.9442',
    'WEB001', '1234567890126', 'N', NULL, 'Y',
    3, 3, NULL,
    'N', 'Y', 'N', 'N', 'N',
    'N', 2.1500, 2.8500, 1.2500,
    1504, '2024-03-18 16:45:10', 1504, '2024-03-18 16:45:10', 4
),
(
    1003, 1003001, 104, NULL, 304, 507, 508,
    NULL, 643, 'O', '2024-03-19 11:30:55', '2024-03-19', '2024-03-19',
    NULL, '2024-03-19', 'C', 'COMPLETED', 'COMPLETED', 404,
    605, NULL, NULL, NULL, NULL, 'CASH-001',
    NULL, NULL, NULL, 'P', 20.00,
    40.00, 35.60, 8.00, 200.00, 178.00,
    160.00, 142.40, 200.00,
    178.00, 1305, 'CASH_SALES', 2005, 'Walk-in cash purchase',
    NULL, 'Store Pickup', 'Customer pickup at store location', NULL,
    'CASH01', '1234567890127', 'N', NULL, 'Y',
    1, 1, 1403,
    'Y', 'N', 'N', 'N', 'N',
    'Y', 0.5000, 0.7500, 0.3500,
    1505, '2024-03-19 11:30:55', 1505, '2024-03-19 11:30:55', 5
);
"

# Check if data was inserted successfully
if [ $? -eq 0 ]; then
    echo "Sample data inserted successfully!"

    # Show inserted data
    echo -e "\nShowing inserted data:"
    curl -X POST "http://${CLICKHOUSE_HOST}:${CLICKHOUSE_PORT}/" \
      --user "${CLICKHOUSE_USER}:${CLICKHOUSE_PASSWORD}" \
      --data "SELECT
        company_id,
        deal_id,
        deal_kind,
        deal_date,
        status,
        total_amount,
        currency_id,
        delivery_address_short
      FROM mdeal_headers
      ORDER BY deal_date, deal_id
      FORMAT Pretty;"

    echo -e "\nTotal records count:"
    curl -X POST "http://${CLICKHOUSE_HOST}:${CLICKHOUSE_PORT}/" \
      --user "${CLICKHOUSE_USER}:${CLICKHOUSE_PASSWORD}" \
      --data "SELECT COUNT(*) as total_records FROM mdeal_headers;"

    echo -e "\nDeals by type:"
    curl -X POST "http://${CLICKHOUSE_HOST}:${CLICKHOUSE_PORT}/" \
      --user "${CLICKHOUSE_USER}:${CLICKHOUSE_PASSWORD}" \
      --data "SELECT
        deal_kind,
        CASE
          WHEN deal_kind = 'O' THEN 'Order'
          WHEN deal_kind = 'R' THEN 'Return'
          ELSE 'Unknown'
        END as deal_type,
        COUNT(*) as count,
        SUM(total_amount) as total_sum
      FROM mdeal_headers
      GROUP BY deal_kind
      FORMAT Pretty;"

else
    echo "Failed to insert data!"
fi