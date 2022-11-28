--liquibase formatted sql
--changeset elina:1

CREATE TABLE Airport
(
    country VARCHAR(50) NOT NULL,
    city    VARCHAR(50) NOT NULL,
    airport VARCHAR(50) not null,
    PRIMARY KEY (airport)
);
