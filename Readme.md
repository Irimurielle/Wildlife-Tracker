# Wildlife_Tracker
## Description
This application allows Rangers to track wildlife sightings in the area.
## Author
 Murielle IRIBORI
## Live Site

## Setup instruction
* git clone
* Open through vscode or atom
* Make neccessary changes
* Run it
## Database setup
* CREATE DATABASE Wildlife_tracker;
* \c wildlife_tracker;
* CREATE TABLE animals(id serial PRIMARY KEY, name varchar,type varchar, health varchar,age varchar);
* CREATE TABLE locations(id serial PRIMARY KEY, name varchar);
* CREATE TABLE rangers(id serial PRIMARY KEY, name varchar, badge_id varchar,email varchar);
* CREATE TABLE sightings(id serial PRIMARY KEY, animal_id int,ranger_id int , location_id int,timestamp timestamp);
* CREATE TABLE locations_sightings(id serial PRIMARY KEY,location_id int , sighting_id int);
* CREATE TABLE rangers_sightings(id serial PRIMARY KEY,ranger_id int , sighting_id int);
* CREATE DATABASE wildlife_tracker_test WITH TEMPLATE wildlife_tracker;
## Technologies Used
* Java
* markdown
* Handlebars
* psql
## Contact Information 
Email: [irimurielle@gmail.com]

Copyright (c) 2020 [MIT LICENSE](./License)