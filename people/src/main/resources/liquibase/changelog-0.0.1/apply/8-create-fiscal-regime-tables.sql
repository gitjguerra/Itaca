CREATE TABLE PER_FISCAL_REGIME
  (
    ANNUITY             NUMBER (4),
    EFFECT_DATE          DATE,
    ID_REGIME NUMBER (19) NOT NULL,
    PERSON_DETAIL_ID        NUMBER (19) NOT NULL,
    ID_REGIME_AMOUNTS NUMBER (19) NOT NULL
  )
  TABLESPACE PEOPLE LOGGING ;

CREATE SEQUENCE SEQ_PER_FISCAL_REGIME START WITH 1 MAXVALUE 9999999999 MINVALUE 1 INCREMENT BY 1 NOCYCLE NOCACHE NOORDER;

CREATE INDEX PER_FISCAL_REGIME_FK1 ON PER_FISCAL_REGIME
  (
   PERSON_DETAIL_ID ASC
  )
TABLESPACE PEOPLE LOGGING ;

ALTER TABLE PER_FISCAL_REGIME ADD CONSTRAINT PER_FISCAL_REGIME_PK PRIMARY KEY ( ID_REGIME ) ;
ALTER TABLE PER_FISCAL_REGIME ADD CONSTRAINT PER_FISCAL_REGIME_FK1 FOREIGN KEY ( PERSON_DETAIL_ID ) REFERENCES PER_PERSON_DETAIL ( PERSON_DETAIL_ID ) ;
