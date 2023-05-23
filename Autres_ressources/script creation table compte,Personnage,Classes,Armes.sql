-- executer ce script dans mysql workbench avant d'exécuter le projet
 DROP DATABASE IF EXISTS SeigneurDesAnneaux;
 CREATE DATABASE SeigneurDesAnneaux;
 USE SeigneurDesAnneaux;

 -- création table Compte
 CREATE TABLE Compte (
                         id_compte int NOT NULL AUTO_INCREMENT,
                         nom_utilisateur varchar(60),
						 date_creation date,
                         premiun boolean,
                         nombre_personnage int,
                         
                         CONSTRAINT id_compte_pk PRIMARY KEY (id_compte)
);
INSERT INTO Compte (nom_utilisateur,date_creation,premiun,nombre_personnage) VALUES
                              ('Chat','2023-05-23',false,1),
                              ('Orignal','2023-05-19',true,2),
                              ('Marmotte','1991-09-20',true,10);

 CREATE TABLE Classes (
                         id_classe int NOT NULL AUTO_INCREMENT,
                         nom_classe varchar(60),
						 puissance int,
                         agileté int,
                         constitution int,
                         intelligence int,
                         
                         CONSTRAINT id_classe_pk PRIMARY KEY (id_classe)
);
INSERT INTO Classes (nom_classe,puissance,agileté,constitution,intelligence) VALUES
                              ('Barbare',5,3,5,1);
                              
  
 -- création table Armes
 CREATE TABLE Armes (
                         id_arme int NOT NULL AUTO_INCREMENT,
                         nom_arme varchar(60),
						 dommage int,
                         portee int,
                         precission int,
                         type_stat varchar(60) ,
                         CONSTRAINT id_arme_pk PRIMARY KEY (id_arme)
);
INSERT INTO Armes (nom_arme,dommage,portee,precission,type_stat) VALUES
                              ('Hachette',10,2,0.8,'puissance') ;                             


-- création table Personnages
CREATE TABLE Personnages (
						 id_personnage int not null AUTO_INCREMENT,
                         nom_personnage varchar(60) not null,
                         id_classe int,
						 id_arme int,
                         date_creation date,
                         niveau int,
                         id_compte varchar(60) ,
                         CONSTRAINT id_personnage_pk PRIMARY KEY (id_personnage),
                         CONSTRAINT classes_id_classe_FK    FOREIGN KEY ( id_classe) REFERENCES Classes(id_classe),
                         CONSTRAINT Armes_id_arme_FK    FOREIGN KEY ( id_arme) REFERENCES Armes(id_arme)
);
INSERT INTO Personnages (nom_personnage,id_classe,id_arme,date_creation,niveau,id_compte) VALUES
                              ('ChatHeroique', 1, 1 , '2023-05-23',41,1);
                             
                             
                              
      
 

                              
 
-- validation                               
