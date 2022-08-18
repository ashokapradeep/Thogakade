CREATE TABLE customer(
    id VARCHAR(45) NOT NULL PRIMARY KEY ,
    name VARCHAR(45) NOT NULL ,
    address TEXT,
    salary DECIMAL(10,2) DEFAULT 0
);
DESCRIBE customer;