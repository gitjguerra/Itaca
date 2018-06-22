-- BANK ********************************************************************
CREATE TABLE PER_BANK
(
  BANK_ID             NUMBER (19) NOT NULL ,
  BANK_COD            VARCHAR2 (50) NOT NULL ,
  BANK_NAME         VARCHAR2 (200 CHAR) NOT NULL ,
  DRAFT_BANK_PORTAL NUMBER (1) ,
  CBIC                 CHAR (11)
)  TABLESPACE PEOPLE ;
COMMENT ON TABLE PER_BANK
IS
'Bancos' ;
COMMENT ON COLUMN PER_BANK.BANK_ID
IS
'Identificador del banco' ;
COMMENT ON COLUMN PER_BANK.BANK_COD
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
ALTER TABLE PER_BANK ADD CONSTRAINT PER_BANK_PK PRIMARY KEY ( BANK_ID ) ;
ALTER TABLE PER_BANK ADD CONSTRAINT PER_BANK_UK1 UNIQUE ( BANK_COD ) ;

CREATE TABLE PER_CLASIFICATION_ACCOUNT
(
  CLASIFICATION_ACCOUNT_ID NUMBER (19) NOT NULL ,
  VALUE_CLASIFICATION_ACCOUNT VARCHAR2 (1000)
)
TABLESPACE PEOPLE ;
COMMENT ON TABLE PER_CLASIFICATION_ACCOUNT
IS
'En esta entidad endremos la clasificación de cuentas. Podría ser Normalizada o No normalizada.' ;
COMMENT ON COLUMN PER_CLASIFICATION_ACCOUNT.CLASIFICATION_ACCOUNT_ID
IS
'Clave primaria' ;
COMMENT ON COLUMN PER_CLASIFICATION_ACCOUNT.VALUE_CLASIFICATION_ACCOUNT
IS
'Descripción multiidioma' ;
ALTER TABLE PER_CLASIFICATION_ACCOUNT ADD CONSTRAINT PER_CLASIFICATION_ACCOUNT_PK PRIMARY KEY ( CLASIFICATION_ACCOUNT_ID ) ;

CREATE TABLE PER_BANK_ACCOUNT
(
  BANK_ACCOUNT_ID               NUMBER (19) NOT NULL ,
  PERSON_DETAIL_ID              NUMBER (19) NOT NULL ,
  CLASIFICATION_ACCOUNT_ID      NUMBER (19) NOT NULL ,
  TYPE_ACCOUNT_ID               NUMBER (19) NOT NULL ,
  ACCOUNT                       VARCHAR2 (200 CHAR) NOT NULL ,
  PRINCIPAL                     NUMBER (1) NOT NULL ,
  AVAILABLE                     NUMBER (1) NOT NULL ,
  BANK_ID                       NUMBER (19)
)
TABLESPACE PEOPLE ;
COMMENT ON TABLE PER_BANK_ACCOUNT
IS
'Cuentas bancarias relacionadas con la persona' ;
COMMENT ON COLUMN PER_BANK_ACCOUNT.BANK_ACCOUNT_ID
IS
'clave primaria' ;
COMMENT ON COLUMN PER_BANK_ACCOUNT.PERSON_DETAIL_ID
IS
'Detalle persona' ;
COMMENT ON COLUMN PER_BANK_ACCOUNT.CLASIFICATION_ACCOUNT_ID
IS
'Clasificación de la cuenta (Normalizado y No normalizado)' ;
COMMENT ON COLUMN PER_BANK_ACCOUNT.TYPE_ACCOUNT_ID
IS
'Tipo de cuenta' ;
COMMENT ON COLUMN PER_BANK_ACCOUNT.ACCOUNT
IS
'Número de cuenta' ;
COMMENT ON COLUMN PER_BANK_ACCOUNT.PRINCIPAL
IS
'Cuenta por defecto' ;
COMMENT ON COLUMN PER_BANK_ACCOUNT.AVAILABLE
IS
'Nos indica si la cuenta está disponible o no' ;
COMMENT ON COLUMN PER_BANK_ACCOUNT.BANK_ID
IS
'Banco al cual pertenece la cuenta' ;
CREATE INDEX PER_BANK_ACCOUNT_ID1 ON PER_BANK_ACCOUNT
(
  BANK_ID ASC
)
TABLESPACE PEOPLE ;
CREATE INDEX PER_BANK_ACCOUNT_ID2 ON PER_BANK_ACCOUNT
(
  CLASIFICATION_ACCOUNT_ID ASC
)
TABLESPACE PEOPLE ;
CREATE INDEX PER_BANK_ACCOUNT_ID3 ON PER_BANK_ACCOUNT
(
  TYPE_ACCOUNT_ID ASC
)
TABLESPACE PEOPLE ;
CREATE INDEX PER_BANK_ACCOUNT_ID4 ON PER_BANK_ACCOUNT
(
  PERSON_DETAIL_ID ASC
)
TABLESPACE PEOPLE ;
ALTER TABLE PER_BANK_ACCOUNT ADD CONSTRAINT PER_BANK_ACCOUNT_PK PRIMARY KEY ( BANK_ACCOUNT_ID ) ;
ALTER TABLE PER_BANK_ACCOUNT ADD CONSTRAINT PER_BANK_ACCOUNT_UK1 UNIQUE ( PERSON_DETAIL_ID , ACCOUNT ) ;

CREATE TABLE PER_TYPE_ACCOUNT
(
  TYPE_ACCOUNT_ID           NUMBER (19) NOT NULL ,
  VALUE_TYPE_ACCOUNT        VARCHAR2 (200 CHAR)
)
TABLESPACE PEOPLE ;
COMMENT ON TABLE PER_TYPE_ACCOUNT
IS
'Diferentes tipos de cuenta del sistema' ;
COMMENT ON COLUMN PER_TYPE_ACCOUNT.TYPE_ACCOUNT_ID
IS
'Clave primaria' ;
COMMENT ON COLUMN PER_TYPE_ACCOUNT.VALUE_TYPE_ACCOUNT
IS
'Descripción multiidioma' ;
ALTER TABLE PER_TYPE_ACCOUNT ADD CONSTRAINT PER_TYPE_ACCOUNT_PK PRIMARY KEY ( TYPE_ACCOUNT_ID ) ;
ALTER TABLE PER_BANK_ACCOUNT ADD CONSTRAINT PER_BANK_ACCOUNT_FK1 FOREIGN KEY ( PERSON_DETAIL_ID ) REFERENCES PER_PERSON_DETAIL ( PERSON_DETAIL_ID ) NOT DEFERRABLE ;
ALTER TABLE PER_BANK_ACCOUNT ADD CONSTRAINT PER_BANK_ACCOUNT_FK2 FOREIGN KEY ( CLASIFICATION_ACCOUNT_ID ) REFERENCES PER_CLASIFICATION_ACCOUNT ( CLASIFICATION_ACCOUNT_ID ) NOT DEFERRABLE ;
ALTER TABLE PER_BANK_ACCOUNT ADD CONSTRAINT PER_BANK_ACCOUNT_FK3 FOREIGN KEY ( BANK_ID ) REFERENCES PER_BANK ( BANK_ID ) NOT DEFERRABLE ;
ALTER TABLE PER_BANK_ACCOUNT ADD CONSTRAINT PER_BANK_ACCOUNT_FK4 FOREIGN KEY ( TYPE_ACCOUNT_ID ) REFERENCES PER_TYPE_ACCOUNT ( TYPE_ACCOUNT_ID ) NOT DEFERRABLE ;
CREATE SEQUENCE SEQ_BANK START WITH 1 NOCACHE ORDER ;

CREATE SEQUENCE SEQ_CLASIFICATION_ACCOUNT START WITH 1 NOCACHE ORDER ;

CREATE SEQUENCE SEQ_BANK_ACCOUNT START WITH 1 NOCACHE ORDER ;

CREATE SEQUENCE SEQ_TYPE_ACCOUNT START WITH 1 NOCACHE ORDER ;

COMMIT;
-- BANK END ********************************************************************

-- ************************** BANK CARD ************************************************************
CREATE TABLE PER_BANK_CARD
(
  BANK_CARD_ID NUMBER (19) NOT NULL ,
  PERSON_DETAIL_ID      NUMBER (19) NOT NULL ,
  CARD_TYPE_ID    NUMBER (19) NOT NULL ,
  CARD             VARCHAR2 (200 CHAR) NOT NULL ,
  PRINCIPAL           NUMBER (1) NOT NULL ,
  AVAILABLE          NUMBER (1) NOT NULL ,
  EXPIRATION_DATE   DATE ,
  SECURITY_CODE    NUMBER ,
  BANK_ID            NUMBER (19)
)
TABLESPACE PEOPLE;
COMMENT ON TABLE PER_BANK_CARD
IS
'Tarjetas bancarias de la persona' ;
COMMENT ON COLUMN PER_BANK_CARD.BANK_CARD_ID
IS
'Clave Primaria' ;
COMMENT ON COLUMN PER_BANK_CARD.PERSON_DETAIL_ID
IS
'Detalle de la persona' ;
COMMENT ON COLUMN PER_BANK_CARD.CARD_TYPE_ID
IS
'Tipo de tarjeta' ;
COMMENT ON COLUMN PER_BANK_CARD.CARD
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
COMMENT ON COLUMN PER_BANK_CARD.BANK_ID
IS
'Banco al cual pertenece la tarjeta' ;
CREATE INDEX PER_BANK_CARD_ID1 ON PER_BANK_CARD
(
  BANK_ID ASC
)
TABLESPACE PEOPLE ;
CREATE INDEX PER_BANK_CARD_ID2 ON PER_BANK_CARD
(
  CARD_TYPE_ID ASC
)
TABLESPACE PEOPLE ;
CREATE INDEX PER_BANK_CARD_ID3 ON PER_BANK_CARD
(
  PERSON_DETAIL_ID ASC
)
TABLESPACE PEOPLE ;
ALTER TABLE PER_BANK_CARD ADD CONSTRAINT PER_BANK_CARD_BANCARIA_PK PRIMARY KEY ( BANK_CARD_ID ) ;
ALTER TABLE PER_BANK_CARD ADD CONSTRAINT PER_BANK_CARD_UK1 UNIQUE ( PERSON_DETAIL_ID , CARD ) ;


CREATE TABLE PER_CARD_TYPE
(
  CARD_TYPE_ID NUMBER (19) NOT NULL ,
  LITERAL         VARCHAR2 (200 CHAR)
)
TABLESPACE PEOPLE ;
COMMENT ON TABLE PER_CARD_TYPE
IS
'Tipos de tarjetas de la aplicación' ;
COMMENT ON COLUMN PER_CARD_TYPE.CARD_TYPE_ID
IS
'Clave Primaria' ;
COMMENT ON COLUMN PER_CARD_TYPE.LITERAL
IS
'Descripción multiidioma' ;
ALTER TABLE PER_CARD_TYPE ADD CONSTRAINT PER_CARD_TYPE_PK PRIMARY KEY ( CARD_TYPE_ID ) ;

ALTER TABLE PER_BANK_CARD ADD CONSTRAINT PER_BANK_CARD_FK1 FOREIGN KEY ( BANK_ID ) REFERENCES PER_BANK ( BANK_ID ) NOT DEFERRABLE ;
ALTER TABLE PER_BANK_CARD ADD CONSTRAINT PER_BANK_CARD_FK2 FOREIGN KEY ( CARD_TYPE_ID ) REFERENCES PER_CARD_TYPE ( CARD_TYPE_ID ) NOT DEFERRABLE ;
ALTER TABLE PER_BANK_CARD ADD CONSTRAINT PER_BANK_CARD_FK3 FOREIGN KEY ( PERSON_DETAIL_ID ) REFERENCES PER_PERSON_DETAIL ( PERSON_DETAIL_ID ) NOT DEFERRABLE ;

CREATE SEQUENCE SEQ_BANK_CARD START WITH 1 NOCACHE ORDER ;

CREATE SEQUENCE SEQ_CARD_TYPE START WITH 1 NOCACHE ORDER ;
-- ************************** BANK CARD ************************************************************
COMMIT;