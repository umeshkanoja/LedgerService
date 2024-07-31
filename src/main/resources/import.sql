INSERT INTO customer (ID, User_name, Email) VALUES('31ffd3ff-1e95-4c28-992c-f4c3e2a6f277','Djokovic', 'djokovic@gmail.com');
INSERT INTO customer (ID, User_name, Email) VALUES('f5fa4936-1541-4007-9a4f-2af79f80d036','Monfils', 'Monfils@gmail.com');
INSERT INTO customer (ID, User_name, Email) VALUES('e5c1a07e-cccb-47ee-9276-6a0ba00e56cf','Isner', 'Isner@gmail.com');

INSERT INTO account (ID, Customer_id, Balance, Currency) VALUES(random_uuid(), '31ffd3ff-1e95-4c28-992c-f4c3e2a6f277', 200, 'Bitcoin');
INSERT INTO account (ID, Customer_id, Balance, Currency) VALUES(random_uuid(), '31ffd3ff-1e95-4c28-992c-f4c3e2a6f277', 400, 'Ethereum');
INSERT INTO account (ID, Customer_id, Balance, Currency) VALUES(random_uuid(), '31ffd3ff-1e95-4c28-992c-f4c3e2a6f277', 600, 'Matic');

INSERT INTO account (ID, Customer_id, Balance, Currency) VALUES(random_uuid(), 'f5fa4936-1541-4007-9a4f-2af79f80d036', 200, 'Bitcoin');
INSERT INTO account (ID, Customer_id, Balance, Currency) VALUES(random_uuid(), 'f5fa4936-1541-4007-9a4f-2af79f80d036', 400, 'Ethereum');
INSERT INTO account (ID, Customer_id, Balance, Currency) VALUES(random_uuid(), 'f5fa4936-1541-4007-9a4f-2af79f80d036', 600, 'Matic');

INSERT INTO account (ID, Customer_id, Balance, Currency) VALUES(random_uuid(), 'e5c1a07e-cccb-47ee-9276-6a0ba00e56cf', 200, 'Bitcoin');
INSERT INTO account (ID, Customer_id, Balance, Currency) VALUES(random_uuid(), 'e5c1a07e-cccb-47ee-9276-6a0ba00e56cf', 400, 'Ethereum');
INSERT INTO account (ID, Customer_id, Balance, Currency) VALUES(random_uuid(), 'e5c1a07e-cccb-47ee-9276-6a0ba00e56cf', 600, 'Matic');