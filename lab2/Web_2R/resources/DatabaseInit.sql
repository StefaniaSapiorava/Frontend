USE Accounts
CREATE TABLE Client (
    id INT PRIMARY KEY,
    name NVARCHAR(100) Not Null,
    contact NVARCHAR(100) Not Null,
);

USE Accounts
CREATE TABLE Account (
    id INT PRIMARY KEY,
    accountNumber NVARCHAR(100) Not Null,
    balance FLOAT Not Null,
	client INT Not Null,
	FOREIGN KEY (client) REFERENCES Client(id)
);

USE Accounts
CREATE TABLE Card (
	id INT PRIMARY KEY,
    cardNumber NVARCHAR(100) Not Null,
	account INT Not Null,
	client INT Not Null,
	FOREIGN KEY (client) REFERENCES Client(id),
	FOREIGN KEY (account) REFERENCES Account(id)
);

USE Accounts
CREATE TABLE Payment (
	id INT PRIMARY KEY,
	sender INT Not Null,
	reciever INT Not Null,
	amount FLOAT Not Null,
	FOREIGN KEY (sender) REFERENCES Account(id),
	FOREIGN KEY (reciever) REFERENCES Account(id)
);
