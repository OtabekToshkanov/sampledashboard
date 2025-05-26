#!/bin/bash

# ClickHouse connection details
CLICKHOUSE_HOST="localhost"
CLICKHOUSE_PORT="8123"
CLICKHOUSE_USER="default"
CLICKHOUSE_PASSWORD="your_secure_password_here"

# Create the table using curl
curl -X POST "http://${CLICKHOUSE_HOST}:${CLICKHOUSE_PORT}/" \
  --user "${CLICKHOUSE_USER}:${CLICKHOUSE_PASSWORD}" \
  --data "
CREATE TABLE mdeal_headers (
    company_id                            UInt64,
    deal_id                               UInt64,
    filial_id                             UInt64,
    subfilial_id                          Nullable(UInt64),
    room_id                               UInt64,
    person_id                             UInt64,
    owner_person_id                       UInt64,
    corr_extra_ref_set                    Nullable(String),
    currency_id                           UInt64,
    deal_kind                             FixedString(1),
    deal_time                             DateTime64(3),
    deal_date                             Date,
    delivery_date                         Date,
    delivery_number                       Nullable(String),
    booked_date                           Date,
    base_status                           FixedString(1),
    book_status                           Nullable(String),
    status                                String,
    robot_id                              UInt64,
    sales_manager_id                      UInt64,
    manager_id                            Nullable(UInt64),
    expeditor_id                          Nullable(UInt64),
    van_id                                Nullable(UInt64),
    contract_id                           Nullable(UInt64),
    invoice_number                        Nullable(String),
    batch_number                          Nullable(UInt64),
    order_deal_id                         Nullable(UInt64),
    exchange_deal_id                      Nullable(UInt64),
    margin_kind                           FixedString(1),
    margin_value                          Decimal64(6),
    margin_amount                         Decimal64(6),
    margin_amount_base                    Decimal64(6),
    vat_amount                            Decimal64(6),
    total_amount                          Decimal64(6),
    total_amount_base                     Decimal64(6),
    total_amount_without_margin           Decimal64(6),
    total_amount_base_without_margin      Decimal64(6),
    booked_payment_amount                 Decimal64(6),
    booked_payment_amount_base            Decimal64(6),
    payment_type_id                       Nullable(UInt64),
    source_table                          String,
    source_id                             Nullable(UInt64),
    note                                  Nullable(String),
    return_reason_id                      Nullable(UInt64),
    delivery_address_short                Nullable(String),
    delivery_address_full                 Nullable(String),
    delivery_latlng                       Nullable(String),
    code                                  Nullable(String),
    barcode                               Nullable(String),
    with_marking                          FixedString(1),
    marking_attaching_method              Nullable(FixedString(1)),
    self_shipment                         FixedString(1),
    mml_plan_product_count                UInt64,
    mml_fact_product_count                UInt64,
    cash_register_id                      Nullable(UInt64),
    sent_virtual_cashbox                  FixedString(1),
    c_has_action                          FixedString(1),
    c_has_gift                            FixedString(1),
    c_has_margin                          FixedString(1),
    c_has_return                          FixedString(1),
    c_has_visit                           FixedString(1),
    c_total_weight_netto                  Decimal(14,4),
    c_total_weight_brutto                 Decimal(14,4),
    c_total_litr                          Decimal(14,4),
    created_by                            UInt64,
    created_on                            DateTime64(3),
    modified_by                           UInt64,
    modified_on                           DateTime64(3),
    modified_id                           UInt64
) ENGINE = MergeTree()
PARTITION BY toYYYYMM(deal_date)
ORDER BY (company_id, deal_id)
PRIMARY KEY (company_id, deal_id)
SETTINGS index_granularity = 8192;
"

# Check if table was created successfully
if [ $? -eq 0 ]; then
    echo "Table 'mdeal_headers' created successfully!"

    # Verify table structure
    echo "Verifying table structure..."
    curl -X POST "http://${CLICKHOUSE_HOST}:${CLICKHOUSE_PORT}/" \
      --user "${CLICKHOUSE_USER}:${CLICKHOUSE_PASSWORD}" \
      --data "DESCRIBE TABLE mdeal_headers FORMAT Pretty;"
else
    echo "Failed to create table!"
fi