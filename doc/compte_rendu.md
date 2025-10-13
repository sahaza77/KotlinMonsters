#  Compte Rendu Kotlin Monster

## SPRINT 1 : Le Noyau du projet

Ce est un test en **_italique_** et en gras.
![ERD TODO.png](ERD%20TODO.png)

``` sql
CREATE TABLE Entraineurs(
                            id INTEGER PRIMARY KEY AUTO_INCREMENT,
                            nom VARCHAR(255),
                            argents INTEGER);

CREATE TABLE EspeceMonstre (
                               id INT PRIMARY KEY AUTO_INCREMENT,
                               nom VARCHAR(255) NOT NULL,
                               type VARCHAR(255) NOT NULL,
                               baseAttaque INT NOT NULL,
                               baseDefense INT NOT NULL,
                               baseVitesse INT NOT NULL,
                               baseAttaqueSpe INT NOT NULL,
                               baseDefenseSpe INT NOT NULL,
                               basePv INT NOT NULL,
                               modAttaque DOUBLE,
                               modDefense DOUBLE,
                               modVitesse DOUBLE,
                               modAttaqueSpe DOUBLE,
                               modDefenseSpe DOUBLE,
                               modPv DOUBLE,
                               description TEXT,
                               particularites TEXT,
                               caracteres TEXT
);

CREATE TABLE IndividuMonstre (
                                 id INT PRIMARY KEY AUTO_INCREMENT,
                                 nom VARCHAR(255),
                                 niveau INT,
                                 attaque INT,
                                 defense INT,
                                 vitesse INT,
                                 attaqueSpe INT,
                                 defenseSpe INT,
                                 pvMax INT,
                                 potentiel DOUBLE,
                                 exp DOUBLE,
                                 pv INT,

                                 espece_id INT,
                                 entraineur_equipe_id INT,
                                 entraineur_boite_id INT,

                                 CONSTRAINT fk_espece
                                     FOREIGN KEY (espece_id)
                                         REFERENCES EspeceMonstre(id)
                                         ON DELETE CASCADE,

                                 CONSTRAINT fk_entraineur_equipe
                                     FOREIGN KEY (entraineur_equipe_id)
                                         REFERENCES Entraineurs(id)
                                         ON DELETE SET NULL,

                                 CONSTRAINT fk_entraineur_boite
                                     FOREIGN KEY (entraineur_boite_id)
                                         REFERENCES Entraineurs(id)
                                         ON DELETE SET NULL
);

```