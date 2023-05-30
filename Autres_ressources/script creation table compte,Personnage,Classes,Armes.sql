-- executer ce script dans mysql workbench avant d'exécuter le projet
DROP
DATABASE IF EXISTS SeigneurDesAnneaux;
 CREATE
DATABASE SeigneurDesAnneaux;
 USE
SeigneurDesAnneaux;

 -- création table Compte
CREATE TABLE Compte
(
    id_compte          int NOT NULL AUTO_INCREMENT,
    nom_utilisateur    varchar(60),
    mot_de_passe       varchar(60),
    date_creation      date,
    premium            boolean,
    nombre_personnages int,

    CONSTRAINT id_compte_pk PRIMARY KEY (id_compte)
);
INSERT INTO Compte (nom_utilisateur, mot_de_passe, date_creation, premium, nombre_personnages)
VALUES ('Chat', 'Poisson', '2023-05-23', false, 1),
       ('Orignal', 'Cheese', '2023-05-19', true, 2),
       ('Marmotte', 'Castor', '1991-09-20', true, 10),
       ('Tyzral', 'Dino', '2001-05-03', true, 99),
       ('Knik', 'Knok', '1991-09-20', true, 99)
;

CREATE TABLE Classes
(
    id_classe    int NOT NULL AUTO_INCREMENT,
    nom_classe   varchar(60),
    puissance    int,
    agileté      int,
    constitution int,
    intelligence int,

    CONSTRAINT id_classe_pk PRIMARY KEY (id_classe)
);
INSERT INTO Classes (nom_classe, puissance, agileté, constitution, intelligence)
VALUES ('Barbare', 5, 3, 5, 1),
       ('Magichat', 1, 3, 2, 5),
       ('Voleugnal', 2, 5, 3, 2),
       ('ChaGnal', 3, 3, 3, 3)
;


-- création table Armes
CREATE TABLE Arme
(
    id_arme    int NOT NULL AUTO_INCREMENT,
    nom_arme   varchar(60),
    dommage    double,
    portee     double,      -- en metre
    precission int,         -- sur 100
    type_stat  varchar(60), -- str,agi,int
    CONSTRAINT id_arme_pk PRIMARY KEY (id_arme)
);
INSERT INTO Arme (nom_arme, dommage, portee, precission, type_stat)
VALUES ('Hachette', 10, 1, 80, 'str'),
       ('baton chatcroyable', 3, 1.5, 100, 'int'),
       ('dague', 10, 0.5, 95, 'agi'),
       ('Épée longue', 8, 1, 75, 'str'),
       ('Arc court', 6, 2, 85, 'agi'),
       ('Baguette de feu', 10, 1.5, 90, 'int'),
       ('Hache de bataille', 12, 1, 70, 'str'),
       ('Bâton de glace', 8, 1.5, 85, 'int'),
       ('Fleuret', 6, 1, 90, 'agi'),
       ('Marteau de guerre', 10, 1, 80, 'str'),
       ('Livre des sorts', 4, 1.5, 95, 'int'),
       ('Dague empoisonnée', 6, 0.5, 95, 'agi'),
       ('Arbalète légère', 8, 3, 80, 'agi');


-- création table Personnages
CREATE TABLE Personnage
(
    id_personnage  int         not null AUTO_INCREMENT,
    nom_personnage varchar(60) not null,
    id_classe      int,
    id_arme        int,
    date_creation  date,
    niveau         int,
    id_compte      varchar(60),
    CONSTRAINT id_personnage_pk PRIMARY KEY (id_personnage),
    CONSTRAINT classes_id_classe_FK FOREIGN KEY (id_classe) REFERENCES Classes (id_classe),
    CONSTRAINT Arme_id_arme_FK FOREIGN KEY (id_arme) REFERENCES Arme (id_arme)
);
INSERT INTO Personnage (nom_personnage, id_classe, id_arme, date_creation, niveau, id_compte)
VALUES ('ChatHeroique', 1, 1, '2023-05-23', 41, 1),
       ('ChatGratte', 2, 1, '2023-05-29', 10, 1),
       ('ChatPue', 3, 3, '2023-05-29', 49, 1),
       ('ChatBotte', 4, 3, '2023-05-29', 59, 1),
       ('Chato', 4, 3, '2023-05-29', 70, 1),





       ('Orignal1', 1, 1, '2023-06-01', 10, 2),
       ('Orignal2', 1, 1, '2023-04-02', 20, 2),
       ('Orignal3', 1, 1, '2023-03-29', 30, 2),
       ('Orignal4', 1, 1, '2023-02-15', 40, 2),

        ('Tyzral', 2, 2, '2000-01-01', 99, 4),

       ('Knik', 4, 3, '2000-01-01', 99, 5);
;

-- validation
select *
from Arme;
                         
