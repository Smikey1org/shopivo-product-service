For this basic Spring Boot version, Hibernate creates/updates the schema.

Production recommendation:
use Flyway or Liquibase and set:

spring.jpa.hibernate.ddl-auto=validate

The requested product columns are represented directly by Product.java.
