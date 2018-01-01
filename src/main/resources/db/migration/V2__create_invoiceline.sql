
SET_CONNECTION_PROPERTY AsyncDdlOperations=true;
SET_CONNECTION_PROPERTY AutoBatchDdlOperations=true;

CREATE TABLE invoiceline (
	id INT64 NOT NULL,
	uuid BYTES(16) NOT NULL,
	created TIMESTAMP NOT NULL,
	updated TIMESTAMP NOT NULL,
	lineNumber INT64 NOT NULL,
	amount FLOAT64 NOT NULL,
	description STRING(100) NOT NULL
)
PRIMARY KEY (id, lineNumber),
INTERLEAVE IN PARENT invoice
;
-- Execute batch
EXECUTE_DDL_BATCH;
-- Wait for asynchronous ddl operations to finish
WAIT_FOR_DDL_OPERATIONS;

CREATE UNIQUE INDEX idx_customer_uuid ON customer (uuid);

RESET_CONNECTION_PROPERTY AsyncDdlOperations;
RESET_CONNECTION_PROPERTY AutoBatchDdlOperations;
