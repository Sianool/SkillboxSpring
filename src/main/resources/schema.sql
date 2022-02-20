DROP TABLE IF EXISTS authors;
DROP TABLE IF EXISTS books;


create table authors
(
    id         INT,
    first_name VARCHAR(50),
    last_name  VARCHAR(50)
);

CREATE TABLE books
(
    id        INT AUTO_INCREMENT PRIMARY KEY,
    author    VARCHAR(250) NOT NULL,
    title     VARCHAR(250) NOT NULL,
    priceOld  VARCHAR(250) DEFAULT NULL,
    price     VARCHAR(250) DEFAULT NULL
);
