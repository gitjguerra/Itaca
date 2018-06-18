
CREATE TABLE "LD_PRELOAD_DATA"
(	"PRELOAD_DATA_ID" NUMBER NOT NULL ENABLE,
   "LOAD_FILE_ID" NUMBER,
   "LOADED_SUCCESSFULLY" VARCHAR2(1 BYTE),
   "ROW_TYPE" NUMBER,
   "LINE_NUMBER" NUMBER,
   "DATA_COL1" VARCHAR2(1024 BYTE),
   "DATA_COL2" VARCHAR2(1024 BYTE),
   "DATA_COL3" VARCHAR2(1024 BYTE),
  CONSTRAINT "LD_PRELOAD_DATA_PK" PRIMARY KEY ("PRELOAD_DATA_ID")
    USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS
    STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
    PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
    TABLESPACE "DIR"  ENABLE
) SEGMENT CREATION IMMEDIATE
PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
TABLESPACE "DIR" ;

COMMENT ON COLUMN "LD_PRELOAD_DATA"."PRELOAD_DATA_ID" IS 'Primary key';
COMMENT ON COLUMN "LD_PRELOAD_DATA"."LOAD_FILE_ID" IS 'The upload file id of which the current row is
associated';
COMMENT ON COLUMN "LD_PRELOAD_DATA"."LOADED_SUCCESSFULLY" IS 'True if this row was successfully loaded into
the business tables';
COMMENT ON COLUMN "LD_PRELOAD_DATA"."ROW_TYPE" IS 'Row type used to parse this row from the file';
COMMENT ON COLUMN "LD_PRELOAD_DATA"."LINE_NUMBER" IS 'The associated line number within the file';
COMMENT ON COLUMN "LD_PRELOAD_DATA"."DATA_COL1" IS 'Data source column 1';
COMMENT ON COLUMN "LD_PRELOAD_DATA"."DATA_COL2" IS 'Data source column 2';
COMMENT ON COLUMN "LD_PRELOAD_DATA"."DATA_COL3" IS 'Data source column 3';
