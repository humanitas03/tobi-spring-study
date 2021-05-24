CREATE DATABASE IF NOT EXISTS springbook;

CREATE TABLE users (
    id varchar(10) primary key,
    name varchar(20) not null,
    password varchar(10) not null
)