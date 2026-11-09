CREATE DATABASE IF NOT EXISTS tienda_app;
USE tienda_app;

CREATE TABLE IF NOT EXISTS roles(
id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS users(
id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
username VARCHAR (50) NOT NULL UNIQUE,
email VARCHAR (255) NOT NULL UNIQUE,
password VARCHAR (255) NOT NULL,
active BOOLEAN NOT NULL DEFAULT TRUE,
rol_id INT NOT NULL,
FOREIGN KEY (rol_id) REFERENCES roles (id)
);

CREATE TABLE IF NOT EXISTS categories(
id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
name VARCHAR (100) NOT NULL UNIQUE 
);

CREATE TABLE IF NOT EXISTS products(
id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
name VARCHAR (150) NOT NULL,
description VARCHAR(500), 
price DECIMAL(12, 2) NOT NULL,
stock INT NOT NULL,
category_id INT NOT NULL,
seller_id INT NOT NULL,
FOREIGN KEY (category_id) REFERENCES categories (id),
FOREIGN KEY (seller_id) REFERENCES users (id)
);

INSERT INTO roles (id, name) VALUES (1, 'ADMIN'), (2, 'CLIENTE'), (3, 'VENDEDOR')
ON DUPLICATE KEY UPDATE name=VALUES(name);

INSERT INTO users (username, email, password, active, rol_id)
VALUES ('admin', 'admin@tienda.com', '$2a$10$wE9lRxgX692.l4vJz5.jX.2P0s2gD0Y.V2uE68U48GvE.Bq5Xm.a', true, 1)
ON DUPLICATE KEY UPDATE id=id;

UPDATE users SET rol_id = 1 WHERE  username = 'Juan';
UPDATE users SET rol_id = 3 WHERE  username = 'pedro_vendedor';