CREATE TABLE IF NOT EXISTS account(
    id UUID PRIMARY KEY,
    customerFirstName VARCHAR(200),
    customerLastName VARCHAR(200),
    birthdate TIMESTAMP,
    netSalary DOUBLE PRECISION,
    number INT UNIQUE,
    debt BOOLEAN
);