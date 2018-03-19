CREATE TABLE PER_RELATION_TYPE
(
  RELATION_TYPE_ID NUMBER (19) NOT NULL ,
  VALUE          VARCHAR2 (200 CHAR)
)
TABLESPACE PEOPLE LOGGING ;

COMMENT ON TABLE PER_RELATION_TYPE
IS
'Types of relationship of the person' ;

COMMENT ON COLUMN PER_RELATION_TYPE.RELATION_TYPE_ID
IS
'Unique identifier of the type of relationship' ;

COMMENT ON COLUMN PER_RELATION_TYPE.VALUE
IS
'value of the type of relationship literal' ;

CREATE INDEX PER_RELATION_TYPE ON PER_RELATION_TYPE
(
  VALUE ASC
)
TABLESPACE PEOPLE LOGGING;

CREATE UNIQUE INDEX PER_RELATION_TYPE_ID2 ON PER_RELATION_TYPE
(
  RELATION_TYPE_ID ASC
)
TABLESPACE PEOPLE LOGGING ;

ALTER TABLE PER_RELATION_TYPE ADD CONSTRAINT PER_RELATION_TYPE_PK PRIMARY KEY ( RELATION_TYPE_ID ) ;

CREATE TABLE PER_RELATION_PER
(
  RELATION_PER_ID  NUMBER (19) NOT NULL ,
  PERSON_DETAIL_ID   NUMBER (19) NOT NULL ,
  RELATION_TYPE_ID NUMBER (19) NOT NULL ,
  PERSON_DETAIL2_ID  NUMBER (19) NOT NULL
)
TABLESPACE PEOPLE LOGGING ;

CREATE INDEX PER_RELATION_TYPE_PER_ID1 ON PER_RELATION_PER
(
  RELATION_TYPE_ID ASC ,
  PERSON_DETAIL_ID ASC
)
TABLESPACE PEOPLE LOGGING ;

CREATE INDEX PER_RELATION_PER_ID2 ON PER_RELATION_PER
(
  PERSON_DETAIL_ID ASC
)
TABLESPACE PEOPLE LOGGING ;

CREATE INDEX PER_RELATION_PER_ID3 ON PER_RELATION_PER
(
  PERSON_DETAIL2_ID ASC
)
TABLESPACE PEOPLE LOGGING ;

ALTER TABLE PER_RELATION_PER ADD CONSTRAINT PER_RELATION_PER_PK PRIMARY KEY ( RELATION_PER_ID ) ;
ALTER TABLE PER_RELATION_PER ADD CONSTRAINT PER_RELATION_PER_FK1 FOREIGN KEY ( PERSON_DETAIL_ID ) REFERENCES PER_PERSON_DETAIL ( PERSON_DETAIL_ID ) NOT DEFERRABLE ;
ALTER TABLE PER_RELATION_PER ADD CONSTRAINT PER_RELATION_PER_FK2 FOREIGN KEY ( RELATION_TYPE_ID ) REFERENCES PER_RELATION_TYPE ( RELATION_TYPE_ID ) NOT DEFERRABLE ;
ALTER TABLE PER_RELATION_PER ADD CONSTRAINT PER_RELATION_PER_FK3 FOREIGN KEY ( PERSON_DETAIL2_ID ) REFERENCES PER_PERSON_DETAIL ( PERSON_DETAIL_ID ) NOT DEFERRABLE ;

CREATE SEQUENCE SEQ_RELATION_PER START WITH 1 ;

CREATE SEQUENCE SEQ_RELATION_TYPE START WITH 1 ;

COMMIT;