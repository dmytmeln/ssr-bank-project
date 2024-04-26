INSERT INTO users(firstname, lastname, password, email, phone_number)
VALUES ('Dmytro', 'Melnyk', 'asAS12!@', 'dimamel28@gmail.com', '380984035791');

INSERT INTO bank_accounts(id, balance, user_id)
VALUES (1, 1000, 1);

INSERT INTO transactions(id, bank_account_id, msg, type, money_amount, transaction_date)
VALUES (1, 1, 'msg', 'transaction_type', 1000, '2023-01-25 23:10:10');

INSERT INTO transactions(id, bank_account_id, msg, type, money_amount, transaction_date)
VALUES (2, 1, 'msg', 'transaction_type', 2000, '2023-02-25 20:10:10');