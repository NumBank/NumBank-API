-- INIT DATABASE WITH CREATE TABLE
-- ACCOUNT
CREATE TABLE IF NOT EXISTS "account" (
    id UUID PRIMARY KEY,
    customerFirstName VARCHAR(200),
    customerLastName VARCHAR(200),
    birthdate DATE,
    netSalary DOUBLE PRECISION,
    number VARCHAR(10) UNIQUE,
    debt BOOLEAN
);

-- BALANCEHISTORY
CREATE TABLE IF NOT EXISTS "balancehistory" (
    id SERIAL PRIMARY KEY,
    value DOUBLE PRECISION,
    updateDateTime TIMESTAMP DEFAULT current_timestamp,
    accountId UUID REFERENCES account(id)
);

-- CATEGORY
CREATE TABLE IF NOT EXISTS "category" (
    id SERIAL PRIMARY KEY,
    name VARCHAR(200),
    type VARCHAR(200)
);

-- TRANSACTION
CREATE TABLE IF NOT EXISTS "transaction" (
    id UUID PRIMARY KEY,
    amount DOUBLE PRECISION,
    label VARCHAR(10) CHECK (label IN ('DEBIT', 'CREDIT')),
    dateEffect TIMESTAMP DEFAULT current_timestamp,
    saveDate TIMESTAMP DEFAULT current_timestamp,
    extern BOOLEAN DEFAULT false,
    status BOOLEAN DEFAULT false,
    accountId UUID REFERENCES account(id),
    categoryId INT REFERENCES category(id)
);

-- TRANSFERT
CREATE TABLE IF NOT EXISTS "transfert" (
    id UUID PRIMARY KEY,
    amount DOUBLE PRECISION,
    label TEXT,
    dateEffect TIMESTAMP DEFAULT current_timestamp,
    saveDate TIMESTAMP DEFAULT current_timestamp,
    extern BOOLEAN DEFAULT false,
    status BOOLEAN DEFAULT false,
    accountIdSender UUID REFERENCES account(id),
    accountIdRecipient UUID REFERENCES account(id)
);

-- MONEY WITH DRAWAL
CREATE TABLE IF NOT EXISTS "moneyWithDrawal" (
    id SERIAL PRIMARY KEY,
    amount DOUBLE PRECISION,
    withDrawalDate TIMESTAMP DEFAULT current_timestamp,
    accountId UUID REFERENCES account(id)
);
