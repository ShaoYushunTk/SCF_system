CREATE DATABASE IF NOT EXISTS scf_system;
USE scf_system;

DROP TABLE IF EXISTS company;
CREATE TABLE company (
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255),
    company_type VARCHAR(50),
    contact_info VARCHAR(255),
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    created_time TIMESTAMP,
    updated_time TIMESTAMP
);

DROP TABLE IF EXISTS core_enterprise;
CREATE TABLE core_enterprise (
     id VARCHAR(255) PRIMARY KEY,
     enterprise_industry VARCHAR(255),
     registered_capital DECIMAL(15,2),
     credit_rating VARCHAR(50),
     bank_number VARCHAR(255),
     liquid_assets DECIMAL(15,2),
     fixed_assets DECIMAL(15,2),
     liquid_liabilities DECIMAL(15,2),
     fixed_liabilities DECIMAL(15,2),
     created_by VARCHAR(255),
     updated_by VARCHAR(255),
     created_time TIMESTAMP,
     updated_time TIMESTAMP
);

DROP TABLE IF EXISTS small_middle_enterprise;
CREATE TABLE small_middle_enterprise (
     id VARCHAR(255) PRIMARY KEY,
     enterprise_industry VARCHAR(255),
     bank_number VARCHAR(255),
     core_enterprise_id VARCHAR(255),
     created_by VARCHAR(255),
     updated_by VARCHAR(255),
     created_time TIMESTAMP,
     updated_time TIMESTAMP
);

DROP TABLE IF EXISTS financial_institution;
CREATE TABLE financial_institution (
     id VARCHAR(255) PRIMARY KEY,
     enterprise_industry VARCHAR(255),
     loan_interest_rate VARCHAR(255),
     financial_institution_type VARCHAR(255),
     created_by VARCHAR(255),
     updated_by VARCHAR(255),
     created_time TIMESTAMP,
     updated_time TIMESTAMP
);

DROP TABLE IF EXISTS logistics_provider;
CREATE TABLE logistics_provider (
    id VARCHAR(255) PRIMARY KEY,
    enterprise_industry VARCHAR(255),
    transport_type VARCHAR(255),
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    created_time TIMESTAMP,
    updated_time TIMESTAMP
);

DROP TABLE IF EXISTS financing_info;
CREATE TABLE financing_info (
    id VARCHAR(255) PRIMARY KEY,
    apply_time TIMESTAMP,
    company_id VARCHAR(255),
    amount DECIMAL(15, 2),
    apply_comment VARCHAR(255),
    financing_type VARCHAR(50),
    approval_status VARCHAR(50),
    approval_time TIMESTAMP,
    approval_id VARCHAR(255),
    approval_comment VARCHAR(255),
    loan_time TIMESTAMP,
    is_repay SMALLINT,
    repay_time TIMESTAMP,
    repay_amount DECIMAL(15, 2),
    financial_institution_id VARCHAR(255),
    interest_rate DECIMAL(15, 2)
);

DROP TABLE IF EXISTS company_asset;
CREATE TABLE company_asset (
   id VARCHAR(255) PRIMARY KEY,
   company_id VARCHAR(255),
   financing_info_list JSON,
   cash DECIMAL(15, 2),
   order_assets JSON
);

DROP TABLE IF EXISTS orders;
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

DROP TABLE IF EXISTS fund_flow;
CREATE TABLE fund_flow (
    id VARCHAR(255) PRIMARY KEY,
    trading_time TIMESTAMP,
    payer VARCHAR(255),
    receiver VARCHAR(255),
    amount DECIMAL(15, 2),
    trading_type VARCHAR(255),
)

DROP TABLE IF EXISTS supply_chain;
CREATE TABLE supply_chain (
    id VARCHAR(255) PRIMARY KEY,
    core_enterprise_id VARCHAR(255),
    smes_list JSON,
    financial_institution_list JSON,
    logistics_company_list JSON,
)

DROP TABLE IF EXISTS employee;
CREATE TABLE employee (
    id VARCHAR(255) PRIMARY KEY,
    username VARCHAR(255),
    password VARCHAR(255),
    company_type VARCHAR(255),
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    created_time TIMESTAMP,
    updated_time TIMESTAMP
);

DROP TABLE IF EXISTS user;
CREATE TABLE user (
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255),
    phone VARCHAR(255),
    company_id VARCHAR(255),
    bank_number VARCHAR(255),
    is_valid int,
    contact_email VARCHAR(255),
    contact_address VARCHAR(255),
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    created_time TIMESTAMP,
    updated_time TIMESTAMP
);