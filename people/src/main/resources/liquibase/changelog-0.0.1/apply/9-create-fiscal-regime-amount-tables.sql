CREATE TABLE per_regime (
    id_regime   NUMBER(19) NOT NULL,
    value                   VARCHAR2(200 CHAR) NOT NULL,
    country_id                 NUMBER(19) NOT NULL
)
    TABLESPACE PEOPLE LOGGING;

CREATE INDEX per_regime_id ON
    per_regime ( id_regime ASC )
        TABLESPACE PEOPLE LOGGING;

ALTER TABLE per_regime ADD CONSTRAINT per_regime_pk PRIMARY KEY ( id_regime );

CREATE TABLE per_regime_amounts (
    taxes                   NUMBER NOT NULL,
    retention                   NUMBER NOT NULL,
    startDate                DATE NOT NULL,
    endDate                   DATE NOT NULL,
    id_regime_amounts NUMBER(19) NOT NULL,
    id_regime NUMBER(19) NOT NULL
) TABLESPACE PEOPLE LOGGING;

CREATE INDEX per_regime_amounts_id1 ON
    per_regime_amounts ( id_regime_amounts ASC )
        TABLESPACE PEOPLE LOGGING;

CREATE INDEX per_regime_amounts_fk1 ON
    per_regime_amounts ( id_regime ASC )
        TABLESPACE PEOPLE LOGGING;

ALTER TABLE per_regime_amounts ADD CONSTRAINT per_regime_amounts_pk PRIMARY KEY ( id_regime_amounts );

ALTER TABLE per_regime_amounts ADD CONSTRAINT per_regime_imp_fk1 FOREIGN KEY ( id_regime )
    REFERENCES per_regime ( id_regime )
NOT DEFERRABLE;

CREATE SEQUENCE sper_regime START WITH 1 NOCACHE ORDER;

CREATE SEQUENCE sper_regime_amounts START WITH 1 NOCACHE ORDER;


ALTER TABLE PER_FISCAL_REGIME ADD CONSTRAINT PER_FISCAL_REGIME_FK2 FOREIGN KEY ( ID_REGIME_AMOUNTS ) REFERENCES PER_REGIME_AMOUNTS ( ID_REGIME_AMOUNTS ) ;