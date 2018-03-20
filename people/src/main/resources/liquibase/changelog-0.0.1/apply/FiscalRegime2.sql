CREATE TABLE fis_fiscal_regime (
    id_fis_fiscal_regime   NUMBER(19) NOT NULL,
    value                   VARCHAR2(200 CHAR) NOT NULL,
    id_country                 NUMBER(19) NOT NULL
)
    TABLESPACE PEOPLE LOGGING;

CREATE INDEX fis_fiscal_regime_id ON
    fis_fiscal_regime ( id_fis_fiscal_regime ASC )
        TABLESPACE PEOPLE LOGGING;

ALTER TABLE fis_fiscal_regime ADD CONSTRAINT fis_fiscal_regime_pk PRIMARY KEY ( id_fis_fiscal_regime );

CREATE TABLE fis_fiscal_regime_amounts (
    taxes                   NUMBER NOT NULL,
    retention                   NUMBER NOT NULL,
    startDate                DATE NOT NULL,
    endDate                   DATE NOT NULL,
    fis_fiscal_regime_amounts_id NUMBER(19) NOT NULL,
    id_fis_fiscal_regime NUMBER(19) NOT NULL
) TABLESPACE PEOPLE LOGGING;

CREATE INDEX fis_fiscal_regime_amounts_id1 ON
    fis_fiscal_regime_amounts ( fis_fiscal_regime_amounts_id ASC )
        TABLESPACE PEOPLE LOGGING;

CREATE INDEX fis_fiscal_regime_amounts_fk1 ON
    fis_fiscal_regime_amounts ( id_fis_fiscal_regime ASC )
        TABLESPACE PEOPLE LOGGING;

ALTER TABLE fis_fiscal_regime_amounts ADD CONSTRAINT fis_fiscal_regime_amounts_pk PRIMARY KEY ( fis_fiscal_regime_amounts_id );

ALTER TABLE fis_fiscal_regime_amounts ADD CONSTRAINT fis_fiscal_regime_imp_fk1 FOREIGN KEY ( id_fis_fiscal_regime )
    REFERENCES fis_fiscal_regime ( id_fis_fiscal_regime )
NOT DEFERRABLE;

CREATE SEQUENCE sfis_fiscal_regime START WITH 1 NOCACHE ORDER;

CREATE SEQUENCE sfis_fiscal_regime_amounts START WITH 1 NOCACHE ORDER;