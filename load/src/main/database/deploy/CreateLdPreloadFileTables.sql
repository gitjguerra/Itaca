CREATE TABLE LD_PRELOAD_FILE
(
  PRELOAD_FILE_ID        NUMBER                 NOT NULL,
  PRELOAD_DEFINITION_ID  NUMBER,
  NAME                   VARCHAR2(50 BYTE)      NOT NULL,
  DESCRIPTION            VARCHAR2(255 BYTE),
  FILENAME_FORMAT_REGEX  VARCHAR2(100 BYTE)
)
TABLESPACE LOAD LOGGING;

CREATE SEQUENCE SEQ_PRELOAD_FILE_ID   MINVALUE 1 MAXVALUE 99999999999  START WITH 1 INCREMENT BY 1 CACHE 20;


COMMENT ON COLUMN LD_PRELOAD_FILE.PRELOAD_FILE_ID IS 'Primary key';

COMMENT ON COLUMN LD_PRELOAD_FILE.PRELOAD_DEFINITION_ID IS 'Referencing preload definition.';

COMMENT ON COLUMN LD_PRELOAD_FILE.NAME IS 'Name of this item';

COMMENT ON COLUMN LD_PRELOAD_FILE.DESCRIPTION IS 'Description of this item';

COMMENT ON COLUMN LD_PRELOAD_FILE.FILENAME_FORMAT_REGEX IS 'Regular expression for the
filename format';


CREATE UNIQUE INDEX LD_PRELOAD_FILE_PK ON LD_PRELOAD_FILE(PRELOAD_FILE_ID);

ALTER TABLE LD_PRELOAD_FILE ADD (CONSTRAINT LD_PRELOAD_FILE_PK PRIMARY KEY (PRELOAD_FILE_ID) USING INDEX LD_PRELOAD_FILE_PK ENABLE VALIDATE);

ALTER TABLE LD_PRELOAD_FILE ADD (CONSTRAINT FK_PRELOADDEFINITIONID FOREIGN KEY (PRELOAD_DEFINITION_ID) REFERENCES LD_PRELOAD_DEFINITION (PRELOAD_DEFINITION_ID)ENABLE VALIDATE);