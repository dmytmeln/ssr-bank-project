INSERT INTO users(firstname, lastname, pass, email, phone_number, creation_date)
VALUES ('dmytro', 'melnyk', 'mdm281004', 'dimamel28@gmail.com', '380984035791', '2023-01-25 22:10:10');

INSERT INTO bank_accounts(bank_account_id, balance, user_id)
VALUES (1, 1000, 1);

INSERT INTO transactions(transaction_history_id, bank_account_id, msg, transaction_type, money_amount, transaction_date)
VALUES (1, 1, 'msg', 'transaction_type', 1000, '2023-01-25 23:10:10');

INSERT INTO transactions(transaction_history_id, bank_account_id, msg, transaction_type, money_amount, transaction_date)
VALUES (2, 1, 'msg', 'transaction_type', 2000, '2023-02-25 20:10:10');