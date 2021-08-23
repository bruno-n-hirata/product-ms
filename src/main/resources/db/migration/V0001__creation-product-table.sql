CREATE TABLE products (
    id BIGSERIAL,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(100) NOT NULL,
    price NUMERIC NOT NULL,

    CONSTRAINT pk_products PRIMARY KEY (id)
);
