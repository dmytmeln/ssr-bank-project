DROP DATABASE IF EXISTS bank;

CREATE DATABASE bank;

USE bank;

CREATE TABLE IF NOT EXISTS users (
                                     user_id INT PRIMARY KEY AUTO_INCREMENT,
                                     firstname VARCHAR(50) NOT NULL,
                                     lastname VARCHAR(50) NOT NULL,
                                     pass VARCHAR(50) NOT NULL,
                                     email VARCHAR(50) NOT NULL,
                                     phone_number VARCHAR(50) NOT NULL,
                                     creation_date timestamp NOT NULL
);

CREATE TABLE IF NOT EXISTS bank_accounts (
                                             bank_account_id INT PRIMARY KEY AUTO_INCREMENT,
                                             balance DOUBLE NOT NULL DEFAULT 0,
                                             user_id INT NOT NULL,

                                             FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE IF NOT EXISTS transaction_histories (
                                                     transaction_history_id INT PRIMARY KEY AUTO_INCREMENT,
                                                     bank_account_id INT NOT NULL,
                                                     msg VARCHAR(50) NOT NULL,
                                                     transaction_type VARCHAR(50) NOT NULL,
                                                     money_amount DOUBLE NOT NULL,
                                                     transaction_date timestamp NOT NULL,

                                                     FOREIGN KEY (bank_account_id) REFERENCES bank_accounts(bank_account_id)
);

INSERT INTO users(firstname, lastname, pass, email, phone_number, creation_date)
VALUES ('dmytro', 'melnyk', 'mdm281004', 'dimamel28@gmail.com', '380984035791', '2023-01-25 22:10:10');

INSERT INTO bank_accounts
VALUES (1, 1000, 1);

INSERT INTO transaction_histories
VALUES (1, 1, 'msg', 'transaction_type', 1000, '2023-01-25 23:10:10');

INSERT INTO transaction_histories
VALUES (2, 1, 'msg', 'transaction_type', 2000, '2023-02-25 20:10:10');