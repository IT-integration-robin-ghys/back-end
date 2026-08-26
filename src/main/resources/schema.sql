CREATE EXTENSION IF NOT EXISTS timescaledb CASCADE;

DROP TABLE IF EXISTS "terrarium_request";

DROP TABLE IF EXISTS "sensor_measurement";

DROP TABLE IF EXISTS "terrarium";

DROP TABLE IF EXISTS "user";

CREATE TABLE IF NOT EXISTS "user" (
    id UUID PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS "terrarium" (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    user_id UUID,
    api_key VARCHAR(255),
    settings TEXT,
    FOREIGN KEY (user_id) REFERENCES "user"(id)
);

CREATE TABLE IF NOT EXISTS "terrarium_request" (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    terrarium_id UUID NOT NULL,
    status VARCHAR(255) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES "user"(id),
    FOREIGN KEY (terrarium_id) REFERENCES "terrarium"(id)
);

CREATE TABLE IF NOT EXISTS "sensor_measurement" (
    terrarium_id UUID NOT NULL,
    timestamp TIMESTAMP NOT NULL,
    temperature DOUBLE PRECISION NOT NULL,
    humidity DOUBLE PRECISION NOT NULL,
    PRIMARY KEY (terrarium_id, timestamp)
);

SELECT
    create_hypertable('sensor_measurement', 'timestamp');