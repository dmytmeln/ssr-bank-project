CREATE TABLE IF NOT EXISTS users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    firstname VARCHAR(50) NOT NULL,
    lastname VARCHAR(50) NOT NULL,
    password VARCHAR(125) NOT NULL,
    email VARCHAR(50) NOT NULL UNIQUE,
    phone_number VARCHAR(50) NOT NULL UNIQUE,
    role VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS bank_accounts (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    balance DOUBLE NOT NULL DEFAULT 0,
    user_id BIGINT NOT NULL UNIQUE,

    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS transactions (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    bank_account_id BIGINT NOT NULL,
    msg VARCHAR(50) NOT NULL,
    type VARCHAR(50) NOT NULL,
    money_amount DOUBLE NOT NULL,
    transaction_date timestamp NOT NULL,

    FOREIGN KEY (bank_account_id) REFERENCES bank_accounts(id)
);