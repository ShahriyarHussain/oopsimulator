CREATE TABLE animal (
    name VARCHAR(20),
    carnivorous BOOLEAN,
    region VARCHAR(30),
    height INTEGER,
    weight INTEGER
);

CREATE TABLE cat (
    breed VARCHAR(20),
    wild BOOLEAN,
    paw VARCHAR(20)
) INHERITS (ANIMAL);


