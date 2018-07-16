CREATE TABLE LD_PRELOAD_FILE
(
  PRELOAD_FILE_ID        NUMBER            NOT NULL,
  PRELOAD_DEFINITION_ID  NUMBER,
  NAME                   VARCHAR2(50)      NOT NULL,
  DESCRIPTION            VARCHAR2(255),
  FILENAME_FORMAT_REGEX  VARCHAR2(100),
  file_type              VARCHAR2(10),
  file_separator        VARCHAR2(10),
  file_load_order        NUMBER
)
TABLESPACE LOAD LOGGING;

CREATE SEQUENCE SEQ_PRELOAD_FILE_ID   MINVALUE 1 MAXVALUE 99999999999  START WITH 1 INCREMENT BY 1 CACHE 20;


COMMENT ON COLUMN LD_PRELOAD_FILE.PRELOAD_FILE_ID IS 'Primary key';

COMMENT ON COLUMN LD_PRELOAD_FILE.PRELOAD_DEFINITION_ID IS 'Referencing preload definition.';

COMMENT ON COLUMN LD_PRELOAD_FILE.NAME IS 'Name of this item';

COMMENT ON COLUMN LD_PRELOAD_FILE.DESCRIPTION IS 'Description of this item';

COMMENT ON COLUMN LD_PRELOAD_FILE.FILENAME_FORMAT_REGEX IS 'Regular expression for the filename format';

COMMENT ON COLUMN LD_PRELOAD_FILE.file_type IS 'CVS, Excel or TXT.';

COMMENT ON COLUMN LD_PRELOAD_FILE.file_separator IS 'The separator for the fields in the file.';

COMMENT ON COLUMN LD_PRELOAD_FILE.file_load_order IS 'Order in which the files will the preloaded / loaded.';


CREATE UNIQUE INDEX LD_PRELOAD_FILE_PK ON LD_PRELOAD_FILE(PRELOAD_FILE_ID);

ALTER TABLE LD_PRELOAD_FILE ADD (CONSTRAINT LD_PRELOAD_FILE_PK PRIMARY KEY (PRELOAD_FILE_ID) USING INDEX LD_PRELOAD_FILE_PK ENABLE VALIDATE);

ALTER TABLE LD_PRELOAD_FILE ADD (CONSTRAINT FK_PRELOADDEFINITIONID FOREIGN KEY (PRELOAD_DEFINITION_ID) REFERENCES LD_PRELOAD_DEFINITION (PRELOAD_DEFINITION_ID)ENABLE VALIDATE);