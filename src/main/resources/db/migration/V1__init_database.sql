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
    id UUID PRIMARY KEY,
    value DOUBLE PRECISION,
    updateDateTime TIMESTAMP,
    accountId UUID REFERENCES account(id)
);

-- CATEGORY
CREATE TABLE IF NOT EXISTS "category" (
    id UUID PRIMARY KEY,
    name VARCHAR(200),
    type VARCHAR(200)
);

-- TRANSACTION
CREATE TABLE IF NOT EXISTS "transaction" (
    id UUID PRIMARY KEY,
    amount DOUBLE PRECISION,
    lable TEXT,
    dateEffect TIMESTAMP,
    saveDate TIMESTAMP,
    extern BOOLEAN,
    status BOOLEAN,
    accountIdSender UUID REFERENCES account(id),
    accountIdRecipient UUID REFERENCES account(id),
    categoryId UUID REFERENCES category(id)
);
