/*==============================================================*/
/* Nom de SGBD :  MySQL 5.0                                     */
/* Date de création :  28/02/2024 08:24:35                      */
/*==============================================================*/


drop table if exists CLIENT;

drop table if exists PROSPECT;

drop table if exists SOCIETE;

drop table if exists ADRESSE;

drop table if exists CODE_POSTAL;

drop table if exists VILLE;

/*==============================================================*/
/* Table : ADRESSE                                              */
/*==============================================================*/
create table ADRESSE
(
   ID_ADRESSE           int not null auto_increment,
   ID_CP                int not null,
   NUM_ADRESSE          varchar(10) not null,
   RUE_ADRESSE          varchar(150) not null,
   primary key (ID_ADRESSE)
);

/*==============================================================*/
/* Table : CLIENT                                               */
/*==============================================================*/
create table CLIENT
(
   ID_CLIENT            int not null auto_increment,
   ID_SOCIETE           int not null,
   CA_CLIENT            float(15,2) not null CHECK (CA_CLIENT >= 200),
   NBRE_EMPLOYE         int not null CHECK (NBRE_EMPLOYE > 0),
   primary key (ID_CLIENT)
);

/*==============================================================*/
/* Table : CODE_POSTAL                                          */
/*==============================================================*/
create table CODE_POSTAL
(
   ID_CP                int not null auto_increment,
   ID_VILLE             int not null,
   NUM_CP               varchar(5) not null,
   primary key (ID_CP)
);

/*==============================================================*/
/* Table : PROSPECT                                             */
/*==============================================================*/
create table PROSPECT
(
   ID_PROSPECT         int not null auto_increment,
   ID_SOCIETE           int not null,
   DATE_PROSPECT        date not null,
   INTERET_PROSPECT     int not null CHECK (INTERET_PROSPECT IN (0,1)),
   primary key (ID_PROSPECT)
);

/*==============================================================*/
/* Table : SOCIETE                                              */
/*==============================================================*/
create table SOCIETE
(
   ID_SOCIETE           int not null auto_increment,
   NOM_SOCIETE          varchar(100) not null UNIQUE,
   ID_ADRESSE           int not null,
   TEL_SOCIETE          varchar(20) not null CHECK (LENGTH(TEL_SOCIETE) >= 10),
   MAIL_SOCIETE         varchar(100) not null CHECK (MAIL_SOCIETE REGEXP '@'),
   COM_SOCIETE          text,
   primary key (ID_SOCIETE)
);

/*==============================================================*/
/* Table : VILLE                                                */
/*==============================================================*/
create table VILLE
(
   ID_VILLE             int not null auto_increment,
   NOM_VILLE            varchar(50) not null,
   primary key (ID_VILLE)
);

alter table ADRESSE add constraint FK_ASSOCIATION_3 foreign key (ID_CP)
      references CODE_POSTAL (ID_CP) on delete restrict on update restrict;

alter table CLIENT add constraint FK_ASSOCIATION_5 foreign key (ID_SOCIETE)
      references SOCIETE (ID_SOCIETE) on delete restrict on update restrict;

alter table CODE_POSTAL add constraint FK_ASSOCIATION_4 foreign key (ID_VILLE)
      references VILLE (ID_VILLE) on delete restrict on update restrict;

alter table PROSPECT add constraint FK_ASSOCIATION_6 foreign key (ID_SOCIETE)
      references SOCIETE (ID_SOCIETE) on delete restrict on update restrict;

alter table SOCIETE add constraint FK_SE_SITUER foreign key (ID_ADRESSE)
      references ADRESSE (ID_ADRESSE) on delete restrict on update restrict;
	  
INSERT INTO `ville` (`ID_VILLE`, `NOM_VILLE`) VALUES (NULL, 'FROUARD');
INSERT INTO `ville` (`ID_VILLE`, `NOM_VILLE`) VALUES (NULL, 'NANCY');
INSERT INTO `ville` (`ID_VILLE`, `NOM_VILLE`) VALUES (NULL, 'METZ');

INSERT INTO `code_postal` (`ID_CP`, `ID_VILLE`, `NUM_CP`) VALUES (NULL, '1', '54390');
INSERT INTO `code_postal` (`ID_CP`, `ID_VILLE`, `NUM_CP`) VALUES (NULL, '2', '54000');
INSERT INTO `code_postal` (`ID_CP`, `ID_VILLE`, `NUM_CP`) VALUES (NULL, '3', '57000');

INSERT INTO `adresse` (`ID_ADRESSE`, `ID_CP`, `NUM_ADRESSE`, `RUE_ADRESSE`) VALUES (NULL, '1', '56', 'SQUARE EUGENE HERZOG');
INSERT INTO `adresse` (`ID_ADRESSE`, `ID_CP`, `NUM_ADRESSE`, `RUE_ADRESSE`) VALUES (NULL, '2', '1', 'PLACE STANISLAS');
INSERT INTO `adresse` (`ID_ADRESSE`, `ID_CP`, `NUM_ADRESSE`, `RUE_ADRESSE`) VALUES (NULL, '3', '1', 'PLACE D\'ARMES');

INSERT INTO `societe` (`ID_SOCIETE`, `NOM_SOCIETE`, `ID_ADRESSE`, `TEL_SOCIETE`, `MAIL_SOCIETE`, `COM_SOCIETE`) 
VALUES (NULL, 'AFPA', '1', '0972723936', 'contactafpapompey@afpa.fr', 'Centre de formation ');
INSERT INTO `societe` (`ID_SOCIETE`, `NOM_SOCIETE`, `ID_ADRESSE`, `TEL_SOCIETE`, `MAIL_SOCIETE`, `COM_SOCIETE`) 
VALUES (NULL, 'MAIRIE DE NANCY', '2', '0383853000', 'contact@nancy.fr', 'Bâtiment de la mairie placé sur une zone piétonne.');
INSERT INTO `societe` (`ID_SOCIETE`, `NOM_SOCIETE`, `ID_ADRESSE`, `TEL_SOCIETE`, `MAIL_SOCIETE`, `COM_SOCIETE`) 
VALUES (NULL, 'MAIRIE DE METZ', '1', '0800891891', 'contact@metz.fr', 'Mairie placé en face de la cathédrale');

INSERT INTO `client` (`ID_CLIENT`, `ID_SOCIETE`, `CA_CLIENT`, `NBRE_EMPLOYE`) VALUES (NULL, '1', '1000000.00', '20');

INSERT INTO `prospect` (`ID_PROSPECT`, `ID_SOCIETE`, `DATE_PROSPECT`, `INTERET_PROSPECT`) VALUES (NULL, '2', '2024-02-13', '1');
INSERT INTO `prospect` (`ID_PROSPECT`, `ID_SOCIETE`, `DATE_PROSPECT`, `INTERET_PROSPECT`) VALUES (NULL, '3', '2024-02-28', '0');