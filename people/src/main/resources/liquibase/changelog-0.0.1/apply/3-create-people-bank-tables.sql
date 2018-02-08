-- BANK ********************************************************************
CREATE TABLE PER_BANK
  (
    ID_BANK             NUMBER (19) NOT NULL ,
    COD_BANK            VARCHAR2 (50) NOT NULL ,
    BANK_NAME         VARCHAR2 (200 CHAR) NOT NULL ,
    DRAFT_BANK_PORTAL NUMBER (1) ,
    CBIC                 CHAR (11)
  )  TABLESPACE PEOPLE ;
COMMENT ON TABLE PER_BANK
IS
  'Bancos' ;
  COMMENT ON COLUMN PER_BANK.ID_BANK
IS
  'Identificador del banco' ;
  COMMENT ON COLUMN PER_BANK.COD_BANK
IS
  'Código del banco' ;
  COMMENT ON COLUMN PER_BANK.BANK_NAME
IS
  'Nombre del banco' ;
  COMMENT ON COLUMN PER_BANK.DRAFT_BANK_PORTAL
IS
  'Tiene giro bancario por portal S/N' ;
  COMMENT ON COLUMN PER_BANK.CBIC
IS
  'Código SWIFT o código BIC (Business Identification Code) identificación de ámbito nacional e internacional.' ;
  ALTER TABLE PER_BANK ADD CONSTRAINT PER_BANK_PK PRIMARY KEY ( ID_BANK ) ;
  ALTER TABLE PER_BANK ADD CONSTRAINT PER_BANK_UK1 UNIQUE ( COD_BANK ) ;

  CREATE TABLE PER_CLASSIFICATION_ACCOUNT
    (
      ID_CLASSIFICATION_ACCOUNT NUMBER (19) NOT NULL ,
      VALUE_CLASSIFICATION_ACCOUNT VARCHAR2 (1000)
    )
    TABLESPACE PEOPLE ;
  COMMENT ON TABLE PER_CLASSIFICATION_ACCOUNT
IS
  'En esta entidad endremos la clasificación de cuentas. Podría ser Normalizada o No normalizada.' ;
  COMMENT ON COLUMN PER_CLASSIFICATION_ACCOUNT.ID_CLASSIFICATION_ACCOUNT
IS
  'Clave primaria' ;
  COMMENT ON COLUMN PER_CLASSIFICATION_ACCOUNT.VALUE_CLASSIFICATION_ACCOUNT
IS
  'Descripción multiidioma' ;
  ALTER TABLE PER_CLASSIFICATION_ACCOUNT ADD CONSTRAINT PER_CLASSIFICATION_ACCOUNT_PK PRIMARY KEY ( ID_CLASSIFICATION_ACCOUNT ) ;

  CREATE TABLE PER_BANK_ACCOUNT
    (
      ID_BANK_ACCOUNT               NUMBER (19) NOT NULL ,
      ID_PERSON_DETAIL              NUMBER (19) NOT NULL ,
      ID_CLASIFICATION_ACCOUNT      NUMBER (19) NOT NULL ,
      ID_TYPE_ACCOUNT               NUMBER (19) NOT NULL ,
      ACCOUNT                       VARCHAR2 (200 CHAR) NOT NULL ,
      PRINCIPAL                     NUMBER (1) NOT NULL ,
      DISPONIBLE                    NUMBER (1) NOT NULL ,
      ID_BANK                       NUMBER (19)
    )
    TABLESPACE PEOPLE ;
  COMMENT ON TABLE PER_BANK_ACCOUNT
IS
  'Cuentas bancarias relacionadas con la persona' ;
  COMMENT ON COLUMN PER_BANK_ACCOUNT.ID_BANK_ACCOUNT
IS
  'clave primaria' ;
  COMMENT ON COLUMN PER_BANK_ACCOUNT.ID_PERSON_DETAIL
IS
  'Detalle persona' ;
  COMMENT ON COLUMN PER_BANK_ACCOUNT.ID_CLASSIFICATION_ACCOUNT
IS
  'Clasificación de la cuenta (Normalizado y No normalizado)' ;
  COMMENT ON COLUMN PER_BANK_ACCOUNT.ID_TYPE_ACCOUNT
IS
  'Tipo de cuenta' ;
  COMMENT ON COLUMN PER_BANK_ACCOUNT.NUMBER_ACCOUNT
IS
  'Número de cuenta' ;
  COMMENT ON COLUMN PER_BANK_ACCOUNT.PRINCIPAL
IS
  'Cuenta por defecto' ;
  COMMENT ON COLUMN PER_BANK_ACCOUNT.DISPONIBLE
IS
  'Nos indica si la cuenta está disponible o no' ;
  COMMENT ON COLUMN PER_BANK_ACCOUNT.ID_BANK
IS
  'Banco al cual pertenece la cuenta' ;
  CREATE INDEX PER_BANK_ACCOUNT_ID1 ON PER_BANK_ACCOUNT
    (
      ID_BANK ASC
    )
    TABLESPACE PEOPLE ;
  CREATE INDEX PER_BANK_ACCOUNT_ID2 ON PER_BANK_ACCOUNT
    (
      ID_CLASSIFICATION_ACCOUNT ASC
    )
    TABLESPACE PEOPLE ;
  CREATE INDEX PER_BANK_ACCOUNT_ID3 ON PER_BANK_ACCOUNT
    (
      ID_TYPE_ACCOUNT ASC
    )
    TABLESPACE PEOPLE ;
  CREATE INDEX PER_BANK_ACCOUNT_ID4 ON PER_BANK_ACCOUNT
    (
      ID_PERSON_DETAIL ASC
    )
    TABLESPACE PEOPLE ;
  ALTER TABLE PER_BANK_ACCOUNT ADD CONSTRAINT PER_BANK_ACCOUNT_PK PRIMARY KEY ( ID_BANK_ACCOUNT ) ;
  ALTER TABLE PER_BANK_ACCOUNT ADD CONSTRAINT PER_BANK_ACCOUNT_UK1 UNIQUE ( ID_PERSON_DETAIL , ACCOUNT ) ;

  CREATE TABLE PER_TYPE_ACCOUNT
    (
      ID_TYPE_ACCOUNT           NUMBER (19) NOT NULL ,
      VALUE_TYPE_ACCOUNT        VARCHAR2 (200 CHAR)
    )
    TABLESPACE PEOPLE ;
  COMMENT ON TABLE PER_TYPE_ACCOUNT
IS
  'Diferentes tipos de cuenta del sistema' ;
  COMMENT ON COLUMN PER_TYPE_ACCOUNT.ID_TYPE_ACCOUNT
IS
  'Clave primaria' ;
  COMMENT ON COLUMN PER_TYPE_ACCOUNT.VALUE_TYPE_ACCOUNT
IS
  'Descripción multiidioma' ;
  ALTER TABLE PER_TYPE_ACCOUNT ADD CONSTRAINT PER_TYPE_ACCOUNT_PK PRIMARY KEY ( ID_TYPE_ACCOUNT ) ;
  ALTER TABLE PER_BANK_ACCOUNT ADD CONSTRAINT PER_BANK_ACCOUNT_FK1 FOREIGN KEY ( ID_PERSON_DETAIL ) REFERENCES PER_PERSON_DETAIL ( PERSON_DETAIL_ID ) NOT DEFERRABLE ;
  ALTER TABLE PER_BANK_ACCOUNT ADD CONSTRAINT PER_BANK_ACCOUNT_FK2 FOREIGN KEY ( ID_CLASSIFICATION_ACCOUNT ) REFERENCES PER_CLASSIFICATION_ACCOUNT ( ID_CLASSIFICATION_ACCOUNT ) NOT DEFERRABLE ;
  ALTER TABLE PER_BANK_ACCOUNT ADD CONSTRAINT PER_BANK_ACCOUNT_FK3 FOREIGN KEY ( ID_BANK ) REFERENCES PER_BANK ( ID_BANK ) NOT DEFERRABLE ;
  ALTER TABLE PER_BANK_ACCOUNT ADD CONSTRAINT PER_BANK_ACCOUNT_FK4 FOREIGN KEY ( ID_TYPE_ACCOUNT ) REFERENCES PER_TYPE_ACCOUNT ( ID_TYPE_ACCOUNT ) NOT DEFERRABLE ;
CREATE SEQUENCE SEQ_BANK START WITH 1 NOCACHE ORDER ;

CREATE SEQUENCE SEQ_CLASSIFICATION_ACCOUNT START WITH 1 NOCACHE ORDER ;

CREATE SEQUENCE SEQ_BANK_ACCOUNT START WITH 1 NOCACHE ORDER ;

CREATE SEQUENCE SEQ_TYPE_ACCOUNT START WITH 1 NOCACHE ORDER ;

COMMIT;
-- BANK END ********************************************************************

-- ************************** BANK CARD ************************************************************
CREATE TABLE PER_BANK_CARD
  (
    ID_BANK_CARD NUMBER (19) NOT NULL ,
    ID_PERSON_DETAIL      NUMBER (19) NOT NULL ,
    ID_CARD_TYPE    NUMBER (19) NOT NULL ,
    CARD             VARCHAR2 (200 CHAR) NOT NULL ,
    PRINCIPAL           NUMBER (1) NOT NULL ,
    AVAILABLE          NUMBER (1) NOT NULL ,
    EXPIRATION_DATE   DATE ,
    SECURITY_CODE    NUMBER ,
    ID_BANK            NUMBER (19)
  )
  TABLESPACE PEOPLE;
COMMENT ON TABLE PER_BANK_CARD
IS
  'Tarjetas bancarias de la persona' ;
  COMMENT ON COLUMN PER_BANK_CARD.ID_BANK_CARD
IS
  'Clave Primaria' ;
  COMMENT ON COLUMN PER_BANK_CARD.ID_PERSON_DETAIL
IS
  'Detalle de la persona' ;
  COMMENT ON COLUMN PER_BANK_CARD.ID_CARD_TYPE
IS
  'Tipo de tarjeta' ;
  COMMENT ON COLUMN PER_PER_BANK_CARD.CARD
IS
  'Número de la tarjeta' ;
  COMMENT ON COLUMN PER_BANK_CARD.PRINCIPAL
IS
  'Nos indica si es por defecto' ;
  COMMENT ON COLUMN PER_BANK_CARD.AVAILABLE
IS
  'Nos indica si la tarjeta esta disponible o no' ;
  COMMENT ON COLUMN PER_BANK_CARD.EXPIRATION_DATE
IS
  'Fecha vencimiento de la tarjeta' ;
  COMMENT ON COLUMN PER_BANK_CARD.SECURITY_CODE
IS
  'Código de seguridad de la tarjeta' ;
  COMMENT ON COLUMN PER_BANK_CARD.ID_BANK
IS
  'Banco al cual pertenece la tarjeta' ;
  CREATE INDEX PER_BANK_CARD_ID1 ON PER_BANK_CARD
    (
      ID_BANK ASC
    )
    TABLESPACE PEOPLE ;
  CREATE INDEX PER_BANK_CARD_ID2 ON PER_BANK_CARD
    (
      ID_CARD_TYPE ASC
    )
    TABLESPACE PEOPLE ;
  CREATE INDEX PER_BANK_CARD_ID3 ON PER_BANK_CARD
    (
      ID_PERSON_DETAIL ASC
    )
    TABLESPACE PEOPLE ;
  ALTER TABLE PER_BANK_CARD ADD CONSTRAINT PER_BANK_CARD_BANCARIA_PK PRIMARY KEY ( ID_BANK_CARD ) ;
  ALTER TABLE PER_BANK_CARD ADD CONSTRAINT PER_BANK_CARD_UK1 UNIQUE ( ID_PERSON_DETAIL , CARD ) ;


  CREATE TABLE PER_CARD_TYPE
    (
      ID_CARD_TYPE NUMBER (19) NOT NULL ,
      LITERAL         VARCHAR2 (200 CHAR)
    )
    TABLESPACE PEOPLE ;
  COMMENT ON TABLE PER_CARD_TYPE
IS
  'Tipos de tarjetas de la aplicación' ;
  COMMENT ON COLUMN PER_CARD_TYPE.ID_CARD_TYPE
IS
  'Clave Primaria' ;
  COMMENT ON COLUMN PER_CARD_TYPE.LITERAL
IS
  'Descripción multiidioma' ;
  ALTER TABLE PER_CARD_TYPE ADD CONSTRAINT PER_CARD_TYPE_PK PRIMARY KEY ( ID_CARD_TYPE ) ;

  ALTER TABLE PER_BANK_CARD ADD CONSTRAINT PER_BANK_CARD_FK1 FOREIGN KEY ( ID_BANK ) REFERENCES PER_BANK ( ID_BANK ) NOT DEFERRABLE ;
  ALTER TABLE PER_BANK_CARD ADD CONSTRAINT PER_BANK_CARD_FK2 FOREIGN KEY ( ID_CARD_TYPE ) REFERENCES PER_CARD_TYPE ( ID_CARD_TYPE ) NOT DEFERRABLE ;
  ALTER TABLE PER_BANK_CARD ADD CONSTRAINT PER_BANK_CARD_FK3 FOREIGN KEY ( ID_PERSON_DETAIL ) REFERENCES PER_PERSON_DETAIL ( ID_PERSON_DETAIL ) NOT DEFERRABLE ;

CREATE SEQUENCE SEQ_BANK_CARD START WITH 1 NOCACHE ORDER ;

CREATE SEQUENCE SEQ_CARD_TYPE START WITH 1 NOCACHE ORDER ;
-- ************************** BANK CARD ************************************************************
COMMIT;

