CREATE TABLE financing_info (
    id VARCHAR(255) PRIMARY KEY,
    apply_time TIMESTAMP,
    company_id VARCHAR(255),
    account DECIMAL(15, 2),
    apply_comment VARCHAR(255),
    financing_type VARCHAR(50),
    approval_status VARCHAR(50),
    approval_time TIMESTAMP,
    approval_id VARCHAR(255),
    approval_comment VARCHAR(255),
    loan_time TIMESTAMP,
    is_repay BOOLEAN,
    repay_time TIMESTAMP,
    repay_account DECIMAL(15, 2)
);

CREATE TABLE company_asset (
   id VARCHAR(255) PRIMARY KEY,
   company_id VARCHAR(255),
   financing_info_list JSON,
   cash DECIMAL(15, 2),
   order_assets JSON
);

CREATE TABLE orders (
    id VARCHAR(255) PRIMARY KEY,
    order_time TIMESTAMP,
    payer VARCHAR(255),
    receiver VARCHAR(255),
    goods_list JSON,
    amount DECIMAL(15, 2),
    order_status VARCHAR(255),
    logistics_provider_id VARCHAR(255),
    remark VARCHAR(255)
)