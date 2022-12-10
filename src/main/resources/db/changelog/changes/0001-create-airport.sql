--liquibase formatted sql
--changeset elina:1

CREATE TABLE airport
(
    airport VARCHAR(50) not null,
    country VARCHAR(50) NOT NULL,
    city    VARCHAR(50) NOT NULL,
    PRIMARY KEY (airport)
);
