CREATE DATABASE TelStation
ON
(
	NAME = TelStation_data,
	FILENAME = "C:\Program Files\Microsoft SQL Server\MSSQL15.SQLEXPRESS\MSSQL\DATA\TelStation.mdf",
	SIZE = 10MB,
	MAXSIZE = 100MB,
	FILEGROWTH = 5MB
)
LOG ON
(
	NAME = TelStation_log,
	FILENAME = "C:\Program Files\Microsoft SQL Server\MSSQL15.SQLEXPRESS\MSSQL\DATA\TelStation.ldf",
	SIZE = 5MB,
	MAXSIZE = 50MB,
	FILEGROWTH = 5MB
)