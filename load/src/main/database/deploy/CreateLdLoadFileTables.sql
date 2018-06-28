CREATE TABLE LD_LOAD_FILE
(
  LOAD_FILE_ID        NUMBER                    NOT NULL,
  LOAD_PROCESS_ID     NUMBER,
  FILENAME            VARCHAR2(1024 BYTE),
  FILE_SIZE           NUMBER,
  CHECKSUM            VARCHAR2(100 BYTE),
  PRELOAD_START_TIME  DATE,
  PRELOAD_END_TIME    DATE,
  LOAD_START_TIME     DATE,
  LOAD_END_TIME       DATE,
  STATUS_CODE         NUMBER,
  STATUS_MESSAGE      VARCHAR2(1024 BYTE),
  USER_LOAD_CANCEL    DATE
)
TABLESPACE LOAD LOGGING;


CREATE SEQUENCE SEQ_Load_File_Id   MINVALUE 1 MAXVALUE 99999999999  START WITH 1 INCREMENT BY 1 CACHE 20;


COMMENT ON COLUMN LD_LOAD_FILE.LOAD_FILE_ID IS 'Primary key';

COMMENT ON COLUMN LD_LOAD_FILE.LOAD_PROCESS_ID IS 'The load process id of which this file is
associated';

COMMENT ON COLUMN LD_LOAD_FILE.FILENAME IS 'Full path to the file name on the file system';

COMMENT ON COLUMN LD_LOAD_FILE.FILE_SIZE IS 'File size in kilo bytes';

COMMENT ON COLUMN LD_LOAD_FILE.CHECKSUM IS 'File checksum';

COMMENT ON COLUMN LD_LOAD_FILE.PRELOAD_START_TIME IS 'Date and time the preload was started';

COMMENT ON COLUMN LD_LOAD_FILE.PRELOAD_END_TIME IS 'Date and time the upload was completed';

COMMENT ON COLUMN LD_LOAD_FILE.LOAD_START_TIME IS 'Date and time the load was started';

COMMENT ON COLUMN LD_LOAD_FILE.LOAD_END_TIME IS 'Date and time the load was completed';

COMMENT ON COLUMN LD_LOAD_FILE.STATUS_CODE IS '-3 Load completed with errors.
-2 Preload completed with errors.
-1 Upload failed.
0 Not yet started.
100 Uploading in progress.
101 Uploading completed.
200 Preloading in progress.
201 Preloading in progress (errors
detected).
202 Preload completed.
300 Loading in progress.
301 loading in progress (errors detected).
302 Load successful.';

COMMENT ON COLUMN LD_LOAD_FILE.STATUS_MESSAGE IS 'Text based indication of status';

COMMENT ON COLUMN LD_LOAD_FILE.USER_LOAD_CANCEL IS 'Set to the time cancelled the preload/load
operation';


CREATE UNIQUE INDEX LD_LOAD_FILE_PK ON LD_LOAD_FILE (LOAD_FILE_ID);

ALTER TABLE LD_LOAD_FILE ADD (CONSTRAINT LD_LOAD_FILE_PK PRIMARY KEY (LOAD_FILE_ID) USING INDEX LD_LOAD_FILE_PK ENABLE VALIDATE);

ALTER TABLE LD_LOAD_FILE ADD (CONSTRAINT FK_loadprocessid FOREIGN KEY (LOAD_PROCESS_ID) REFERENCES LD_LOAD_PROCESS(LOAD_PROCESS_ID) ENABLE VALIDATE);