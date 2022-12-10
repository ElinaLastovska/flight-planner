--liquibase formatted sql
--changeset elina:2

CREATE TABLE flight
(
    id             Integer     NOT NULL AUTO_INCREMENT,
    airport_from   VARCHAR(50) NOT NULL,
    airport_to     VARCHAR(50) NOT NULL,
    carrier        VARCHAR(50) NOT NULL,
    departure_time TIMESTAMP   NOT NULL,
    arrival_time   TIMESTAMP   NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (airport_from) REFERENCES airport (airport),
    FOREIGN KEY (airport_to) REFERENCES airport (airport)
);


-- CREATE TABLE flight
-- (
--     CONSTRAINT flight_from_id_fkey FOREIGN KEY (from_id) REFERENCES airport (airport),
--     CONSTRAINT flight_to_id_fkey FOREIGN KEY (to_id) REFERENCES airport (airport)
-- );