----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--PER_PUBLIC_PERSON_TYPE ------------------------------------------------------------------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

CREATE TABLE PER_PUBLIC_PERSON_TYPE
(
PERSON_PUBLIC_TYPE_ID NUMBER (19) NOT NULL ,
VALUE VARCHAR2 (1000 CHAR)
)
TABLESPACE PEOPLE LOGGING ;
COMMENT ON TABLE PER_PUBLIC_PERSON_TYPE
IS
'Types of public person' ;
COMMENT ON COLUMN PER_PUBLIC_PERSON_TYPE.PERSON_PUBLIC_TYPE_ID
IS
'Unique identifier of Type of public person' ;
COMMENT ON COLUMN PER_PUBLIC_PERSON_TYPE.VALUE
IS
'Literal value' ;
CREATE INDEX PER_PUBLIC_PERSON_TYPE_ID1 ON PER_PUBLIC_PERSON_TYPE
(
PERSON_PUBLIC_TYPE_ID ASC
)
TABLESPACE PEOPLE LOGGING ;
ALTER TABLE PER_PUBLIC_PERSON_TYPE ADD CONSTRAINT PER_PUBLIC_PERSON_TYPE_PK PRIMARY KEY ( PERSON_PUBLIC_TYPE_ID ) ;
CREATE SEQUENCE SEQ_PER_PUBLIC_PERSON_TYPE INCREMENT BY 1 MAXVALUE 9999999999 MINVALUE 1 NOCACHE ;

COMMIT;

----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--PER_PUBLIC_PERSON ------------------------------------------------------------------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


CREATE TABLE PER_PUBLIC_PERSON
(
  ID_PER_PUBLIC_PERSON  NUMBER (19) NOT NULL ,
  ID_TYPE_PUBLIC_PERSON NUMBER (19) NOT NULL ,
  ID_PERSON             NUMBER (19) NOT NULL
)
TABLESPACE PEOPLE LOGGING ;
COMMENT ON COLUMN PER_PUBLIC_PERSON.ID_PER_PUBLIC_PERSON
IS
'Identificador unico de la relacion Persona con Tipo de persona publica' ;
COMMENT ON COLUMN PER_PUBLIC_PERSON.ID_TYPE_PUBLIC_PERSON
IS
'Tipo de persona publica' ;
COMMENT ON COLUMN PER_PUBLIC_PERSON.ID_PERSON
IS
'Identificador de la Persona' ;
CREATE INDEX PER_PUBLIC_PERSON_ID1 ON PER_PUBLIC_PERSON
(
  ID_PER_PUBLIC_PERSON ASC
)
TABLESPACE PEOPLE LOGGING ;
CREATE INDEX PER_PUBLIC_PERSON_ID2 ON PER_PUBLIC_PERSON
(
  ID_TYPE_PUBLIC_PERSON ASC
)
TABLESPACE PEOPLE LOGGING ;
CREATE INDEX PER_PUBLIC_PERSON_ID3 ON PER_PUBLIC_PERSON
(
  ID_PERSON ASC
)
TABLESPACE PEOPLE LOGGING ;
CREATE INDEX PER_PUBLIC_PERSON_ID4 ON PER_PUBLIC_PERSON
(
  ID_TYPE_PUBLIC_PERSON ASC ,
  ID_PERSON ASC
)
TABLESPACE PEOPLE LOGGING ;
ALTER TABLE PER_PUBLIC_PERSON ADD CONSTRAINT PER_PUBLIC_PERSON_PK PRIMARY KEY ( ID_PER_PUBLIC_PERSON ) ;
ALTER TABLE PER_PUBLIC_PERSON ADD CONSTRAINT PER_PUBLIC_PERSON_FK1 FOREIGN KEY ( ID_TYPE_PUBLIC_PERSON ) REFERENCES PER_PUBLIC_PERSON_TYPE ( PERSON_PUBLIC_TYPE_ID ) NOT DEFERRABLE ;
ALTER TABLE PER_PUBLIC_PERSON ADD CONSTRAINT PER_PUBLIC_PERSON_FK2 FOREIGN KEY ( ID_PERSON ) REFERENCES PER_PERSON ( PERSON_ID ) NOT DEFERRABLE ;
CREATE SEQUENCE SEQ_PER_PUBLIC_PERSON INCREMENT BY 1 MAXVALUE 9999999999 MINVALUE 1 NOCACHE ;