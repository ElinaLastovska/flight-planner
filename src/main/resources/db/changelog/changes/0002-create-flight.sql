--liquibase formatted sql
--changeset elina:2

CREATE TABLE Flight
(
    id              int         NOT NULL,
    Airport_from_id VARCHAR(50) NOT NULL,
    Airport_to_id   VARCHAR(50) NOT NULL,
    carrier         VARCHAR(50) NOT NULL,
    departureTime   TIMESTAMP,
    arrivalTime     TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (Airport_from_id) REFERENCES Airport (airport),
    FOREIGN KEY (Airport_to_id) REFERENCES Airport (airport)
);
