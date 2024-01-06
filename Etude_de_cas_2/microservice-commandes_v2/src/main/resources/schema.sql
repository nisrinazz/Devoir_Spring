CREATE TABLE COMMANDE
(
    id          INT PRIMARY KEY,
    description       VARCHAR(255) NOT NULL,
    quantite INT NOT NULL,
    date       DATE NOT NULL,
    montant        DECIMAL  NOT NULL

);