----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--perNATIONALITY.sql-------------------------------------------------------------------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

CREATE TABLE PER_NATIONALITY
(
  ID_NATIONALITY NUMBER (19) NOT NULL ,
  BY_DEFAULT     NUMBER (1) ,
  PERSON_DETAIL_ID  NUMBER (19) NOT NULL ,
  COUNTRY_ID         NUMBER (19) NOT NULL
)
TABLESPACE PEOPLE LOGGING ;
COMMENT ON COLUMN PER_NATIONALITY.ID_NATIONALITY
IS
'CAMPO ID UNICO DE NATIONALITY' ;
COMMENT ON COLUMN PER_NATIONALITY.BY_DEFAULT
IS
'CAMPO NATIONALITY POR DEFECTO' ;
COMMENT ON COLUMN PER_NATIONALITY.PERSON_DETAIL_ID
IS
'RELACION LLAVE FORANEA PER_DET_PERSONA' ;
COMMENT ON COLUMN PER_NATIONALITY.COUNTRY_ID
IS
'CAMPO LLAVE FORANEA CON DIR_COUNTRY' ;
CREATE INDEX PER_NATIONALITY_ID1 ON PER_NATIONALITY
(
  PERSON_DETAIL_ID ASC
)
TABLESPACE PEOPLE LOGGING ;
CREATE INDEX PER_NATIONALITY_ID2 ON PER_NATIONALITY
(
  COUNTRY_ID ASC
)
TABLESPACE PEOPLE LOGGING ;
CREATE UNIQUE INDEX PER_NATIONALITY_ID3 ON PER_NATIONALITY
(
  ID_NATIONALITY ASC
)
TABLESPACE PEOPLE LOGGING ;
CREATE INDEX PER_NATIONALITY_ID4 ON PER_NATIONALITY
(
  PERSON_DETAIL_ID ASC ,
  COUNTRY_ID ASC
)
TABLESPACE PEOPLE LOGGING ;
ALTER TABLE PER_NATIONALITY ADD CONSTRAINT PER_NATIONALITY_PK PRIMARY KEY ( ID_NATIONALITY ) ;
ALTER TABLE PER_NATIONALITY ADD CONSTRAINT PER_NATIONALITY_UK1 UNIQUE ( PERSON_DETAIL_ID , COUNTRY_ID ) ;
ALTER TABLE PER_NATIONALITY ADD CONSTRAINT PER_NATIONALITY_FK1 FOREIGN KEY ( PERSON_DETAIL_ID ) REFERENCES PER_PERSON_DETAIL ( PERSON_DETAIL_ID ) NOT DEFERRABLE ;
ALTER TABLE PER_NATIONALITY ADD CONSTRAINT PER_NATIONALITY_FK2 FOREIGN KEY ( COUNTRY_ID ) REFERENCES DIR_COUNTRY ( COUNTRY_ID ) NOT DEFERRABLE ;
CREATE SEQUENCE SEQ_NATIONALITY START WITH 1 NOCACHE ORDER ;
