# DEPRECATED
This project is no longer actively maintained.

It is recommended that you use the [officially supported open source JDBC driver for Google Cloud Spanner](https://github.com/googleapis/java-spanner-jdbc) in combination with the [corresponding Hibernate dialect](https://github.com/GoogleCloudPlatform/google-cloud-spanner-hibernate).

## spanner-flyway-example
Example project for using Flyway, JPA, Hibernate and Spring Boot with Google Cloud Spanner.

This project relies on two other open source projects:
* spanner-jdbc (https://github.com/olavloite/spanner-jdbc): An open source JDBC Driver for Google Cloud Spanner
* spanner-hibernate (https://github.com/olavloite/spanner-hibernate): A Hibernate Dialect for Google Cloud Spanner

The project furthermore depends on an extension to the flyway project for support for Google Cloud Spanner. This extension can be found here: https://github.com/olavloite/flyway and is a fork of the original flyway repository.

The spanner-hibernate library contains a Hibernate Dialect for Google Cloud Spanner.
