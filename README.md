# spanner-flyway-example
Example project for using Flyway, JPA, Hibernate and Spring Boot with Google Cloud Spanner.

This project relies on two other open source projects:
* spanner-jdbc (https://github.com/olavloite/spanner-jdbc): An open source JDBC Driver for Google Cloud Spanner
* spanner-hibernate (https://github.com/olavloite/spanner-hibernate): A Hibernate Dialect for Google Cloud Spanner

The project furthermore depends on an extension to the flyway project for the addition of support for Google Cloud Spanner.

The JDBC Driver that is supplied by Google for Cloud Spanner is quite limited, as it only supports SELECT-queries, and no DDL and DML statements. The JDBC Driver that is used by this example does support these kind of statements, although limited because of the underlying limitations of Google Cloud Spanner. The limitations are primarily related to the fact that Google Cloud Spanner only allow updates and deletes on one row at a time.

The spanner-hibernate library contains a Hibernate Dialect for Google Cloud Spanner.
