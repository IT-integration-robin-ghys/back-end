DROP TABLE IF EXISTS "terrarium";
DROP TABLE IF EXISTS "user";
DROP TABLE IF EXISTS "terrarium_request";

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
    temperatures DOUBLE PRECISION [],
    humidities DOUBLE PRECISION [],
    user_id UUID,
    FOREIGN KEY (user_id) REFERENCES "user"(id)
);

CREATE TABLE IF NOT EXISTS "terrarium_request" (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    terrarium_id UUID NOT NULL
)