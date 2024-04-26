CREATE TABLE IF NOT EXISTS customers (
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    shared_key    VARCHAR(255) NOT NULL,
    business_id VARCHAR(255) NOT NULL,
    phone       VARCHAR(255) NOT NULL,
    email       VARCHAR(255) NOT NULL,
    start_date  DATE NOT NULL,
    end_date    DATE NOT NULL,
    created_at  TIMESTAMP
);