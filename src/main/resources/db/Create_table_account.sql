CREATE TABLE IF NOT EXISTS account(
    id SERIAL PRIMARY KEY,
    customerFirstName VARCHAR(200),
    customerLastName VARCHAR(200),
    birthdate DATE,
    netSalary DOUBLE PRECISION,
    number VARCHAR(10) UNIQUE,
    debt BOOLEAN
);
