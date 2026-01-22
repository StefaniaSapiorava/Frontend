use TelStation

-- �������� ������� ��������� (abonent)
CREATE TABLE abonent (
    id INT IDENTITY PRIMARY KEY,
    name VARCHAR(60) NOT NULL,
    phone VARCHAR(50) NOT NULL,
    email VARCHAR(255) NOT NULL,
    is_blocked INTEGER DEFAULT 0
);

-- �������� ������� ����� (service)
CREATE TABLE service (
    id INT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    cost DECIMAL(10, 2) NOT NULL
);

-- �������� ������� ��� ����� ��������� � ����� (abonent_service)
CREATE TABLE abonent_service (
    abonent_id INT,
    service_id INT,
    date_added DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (abonent_id, service_id),
    FOREIGN KEY (abonent_id) REFERENCES abonent(id) ON DELETE CASCADE,
    FOREIGN KEY (service_id) REFERENCES service(id) ON DELETE CASCADE
);

-- �������� ������� ������ ��������� (account)
CREATE TABLE account (
    abonent_id INT PRIMARY KEY,
    balance DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
    FOREIGN KEY (abonent_id) REFERENCES abonent(id) ON DELETE CASCADE
);
