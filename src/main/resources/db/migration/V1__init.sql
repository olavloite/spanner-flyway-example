
-- Set connection to execute ddl operations asynchronously and automatically in batches for better performance
SET_CONNECTION_PROPERTY AsyncDdlOperations=true;
SET_CONNECTION_PROPERTY AutoBatchDdlOperations=true;

CREATE TABLE customer (
	id INT64 NOT NULL,
	uuid BYTES(16) NOT NULL,
	created TIMESTAMP NOT NULL,
	updated TIMESTAMP NOT NULL,
	firstName STRING(50),
	lastName STRING(100) NOT NULL
)
PRIMARY KEY (id)
;

CREATE TABLE invoice (
	id INT64 NOT NULL,
	uuid BYTES(16) NOT NULL,
	created TIMESTAMP NOT NULL,
	updated TIMESTAMP NOT NULL,
	customer INT64 NOT NULL,
	invoiceNumber STRING(15) NOT NULL,
	description STRING(200) NOT NULL,
	amount FLOAT64 NOT NULL,
	pdf BYTES(10000000)
)
PRIMARY KEY (id)
;
-- Execute batch
EXECUTE_DDL_BATCH;
-- Wait for asynchronous ddl operations to finish
WAIT_FOR_DDL_OPERATIONS;

-- Create indices in the background (without waiting for them to finish)
CREATE UNIQUE INDEX idx_customer_uuid ON customer (uuid);
CREATE INDEX idx_customer_lastName ON customer (lastName);

CREATE UNIQUE INDEX idx_invoice_uuid ON invoice (uuid);
CREATE INDEX idx_invoice_customer on invoice (customer);
CREATE INDEX idx_invoice_invoicenumber_description on invoice (invoiceNumber, description);

-- Reset the async and auto batch properties to their defaults
RESET_CONNECTION_PROPERTY AsyncDdlOperations;
RESET_CONNECTION_PROPERTY AutoBatchDdlOperations;
