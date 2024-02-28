/*==============================================================*/
/* Nom de SGBD :  MySQL 5.0                                     */
/* Date de cr�ation :  28/02/2024 08:24:35                      */
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
   NUM_ADRESSE          int not null,
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
   NOM_SOCIETE          varchar(100) not null,
   CA_CLIENT            float(8,2) not null CHECK (CA_CLIENT >= 200),
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
   NUM_CP               int not null,
   primary key (ID_CP)
);

/*==============================================================*/
/* Table : PROSPECT                                             */
/*==============================================================*/
create table PROSPECT
(
   ID_PROSPECRT         int not null auto_increment,
   ID_SOCIETE           int not null,
   NOM_SOCIETE          varchar(100) not null,
   DATE_PROSPECT        date not null,
   INTERET_PROSPECT     int not null CHECK (INTERET_PROSPECT IN (0,1)),
   primary key (ID_PROSPECRT)
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
