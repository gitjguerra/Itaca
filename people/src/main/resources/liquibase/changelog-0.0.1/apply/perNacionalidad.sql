----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--perNATIONALITY.sql-------------------------------------------------------------------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

CREATE TABLE PER_NATIONALITY
  (
    ID_NATIONALITY NUMBER (19) NOT NULL ,
    BY_DEFAULT     NUMBER (1) ,
    ID_DET_PERSON  NUMBER (19) NOT NULL ,
    ID_COUNTRY         NUMBER (19) NOT NULL
  )
  TABLESPACE PEOPLE LOGGING ;
COMMENT ON COLUMN PER_NATIONALITY.ID_NATIONALITY
IS
  'CAMPO ID UNICO DE NATIONALITY' ;
  COMMENT ON COLUMN PER_NATIONALITY.BY_DEFAULT
IS
  'CAMPO NATIONALITY POR DEFECTO' ;
  COMMENT ON COLUMN PER_NATIONALITY.ID_DET_PERSONA
IS
  'RELACION LLAVE FORANEA PER_DET_PERSONA' ;
  COMMENT ON COLUMN PER_NATIONALITY.ID_COUNTRY
IS
  'CAMPO LLAVE FORANEA CON DIR_COUNTRY' ;
  CREATE INDEX PER_NATIONALITY_ID1 ON PER_NATIONALITY
    (
      ID_DET_PERSONA ASC
    )
    TABLESPACE PEOPLE LOGGING ;
  CREATE INDEX PER_NATIONALITY_ID2 ON PER_NATIONALITY
    (
      ID_COUNTRY ASC
    )
    TABLESPACE PEOPLE LOGGING ;
CREATE UNIQUE INDEX PER_NATIONALITY_ID3 ON PER_NATIONALITY
  (
    ID_NATIONALITY ASC
  )
  TABLESPACE PEOPLE LOGGING ;
  CREATE INDEX PER_NATIONALITY_ID4 ON PER_NATIONALITY
    (
      ID_DET_PERSONA ASC ,
      ID_COUNTRY ASC
    )
    TABLESPACE PEOPLE LOGGING ;
  ALTER TABLE PER_NATIONALITY ADD CONSTRAINT PER_NATIONALITY_PK PRIMARY KEY ( ID_NATIONALITY ) ;
  ALTER TABLE PER_NATIONALITY ADD CONSTRAINT PER_NATIONALITY_UK1 UNIQUE ( ID_DET_PERSONA , ID_COUNTRY ) ;
  ALTER TABLE PER_NATIONALITY ADD CONSTRAINT PER_NATIONALITY_FK1 FOREIGN KEY ( ID_DET_PERSONA ) REFERENCES PER_DET_PERSONA ( ID_DET_PERSONA ) NOT DEFERRABLE ;
  ALTER TABLE PER_NATIONALITY ADD CONSTRAINT PER_NATIONALITY_FK2 FOREIGN KEY ( ID_COUNTRY ) REFERENCES DIR_COUNTRY ( ID_COUNTRY ) NOT DEFERRABLE ;
CREATE SEQUENCE SEQ_NATIONALITY START WITH 1 NOCACHE ORDER ;
