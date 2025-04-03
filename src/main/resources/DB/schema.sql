/*CREATE DATABASE WishlistDB;
USE WishlistDB;

CREATE TABLE users (
                           user_id INT auto_increment primary key,
                           user_name VARCHAR(255) NOT NULL,
                           user_email VARCHAR(255) UNIQUE NOT NULL,
                           user_password VARCHAR(255) NOT NULL
);

CREATE TABLE wishlist (
                          wishlist_id INT auto_increment primary key,
                          wishlist_name VARCHAR(255) NOT NULL,
                          user_id INT,
                          FOREIGN KEY (user_id) REFERENCES users(user_id)
);
CREATE TABLE wish (
                      wish_id INT AUTO_INCREMENT PRIMARY KEY,
                      wish_name VARCHAR(255) NOT NULL,
                      wish_description VARCHAR(255),
                      wish_url VARCHAR(255),
                      wishlist_id INT,
                      FOREIGN KEY (wishlist_id) REFERENCES wishlist(wishlist_id)
);