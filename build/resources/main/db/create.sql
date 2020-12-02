SET MODE PostgreSQL;

CREATE TABLE animals (
    id int PRIMARY KEY,
    name VARCHAR,
    type VARCHAR,
    health VARCHAR,
    age VARCHAR
);
CREATE TABLE locations (
    id int PRIMARY KEY,
    name VARCHAR
);
CREATE TABLE locations_sightings (
    id int PRIMARY KEY,
    location_id int,
    sighting_id int
 );
CREATE TABLE rangers (
    id int PRIMARY KEY,
    name VARCHAR,
    badge_number VARCHAR,
    email VARCHAR
);
CREATE TABLE rangers_sightings (
    id int PRIMARY KEY,
    ranger_id int,
    sighting_id int
);
CREATE TABLE sightings (
    id int PRIMARY KEY,
    animal_id int,
    ranger_id int,
    location_id int,
    time TIMESTAMP
);