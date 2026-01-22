CREATE DATABASE Accounts
ON
(
	NAME = Accounts_data,
	FILENAME = "C:\Program Files\Microsoft SQL Server\MSSQL15.SQLEXPRESS\MSSQL\DATA\Accounts.mdf",
	SIZE = 10MB,
	MAXSIZE = 100MB,
	FILEGROWTH = 5MB
)
LOG ON
(
	NAME = Accounts_log,
	FILENAME = "C:\Program Files\Microsoft SQL Server\MSSQL15.SQLEXPRESS\MSSQL\DATA\Accounts.ldf",
	SIZE = 5MB,
	MAXSIZE = 50MB,
	FILEGROWTH = 5MB
)