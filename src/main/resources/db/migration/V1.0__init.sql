------------CREATE DB WITH USER-------------------
CREATE ROLE userjon WITH LOGIN PASSWORD '123';
CREATE DATABASE library WITH OWNER userjon;


------------------UUID-------------------
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

