CREATE TABLE LD_LOAD_PROCESS
(
  LOAD_PROCESS_ID        NUMBER                 NOT NULL,
  USER_ID                NUMBER,
  CREATED_TIMESTAMP      DATE,
  PRELOAD_DEFINITION_ID  NUMBER
)
TABLESPACE LOAD LOGGING;


CREATE SEQUENCE SEQ_LOAD_PROCESS_ID   MINVALUE 1 MAXVALUE 99999999999  START WITH 1 INCREMENT BY 1 CACHE 20;

COMMENT ON COLUMN LD_LOAD_PROCESS.LOAD_PROCESS_ID IS 'Primary key';

COMMENT ON COLUMN LD_LOAD_PROCESS.USER_ID IS 'The user who initiated the upload';

COMMENT ON COLUMN LD_LOAD_PROCESS.CREATED_TIMESTAMP IS 'Date and time the load was created';

COMMENT ON COLUMN LD_LOAD_PROCESS.PRELOAD_DEFINITION_ID IS 'Associated preload definition';


CREATE UNIQUE INDEX LD_LOAD_PROCESS_PK ON LD_LOAD_PROCESS (LOAD_PROCESS_ID);

ALTER TABLE LD_LOAD_PROCESS ADD (CONSTRAINT LD_LOAD_PROCESS_PK PRIMARY KEY (LOAD_PROCESS_ID) USING INDEX LD_LOAD_PROCESS_PK ENABLE VALIDATE);