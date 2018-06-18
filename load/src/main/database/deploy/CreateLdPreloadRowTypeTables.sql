CREATE TABLE "LD_PRELOAD_ROW_TYPE"
(	"PRELOAD_ROW_TYPE_ID" NUMBER NOT NULL ENABLE,
   "PRELOAD_FILE_ID" NUMBER,
   "NAME" VARCHAR2(50 BYTE) NOT NULL ENABLE,
   "DESCRIPTION" VARCHAR2(255 BYTE),
   "IDENTIFIER_COLUMN_NO" NUMBER DEFAULT 1,
   "IDENTIFIER_VALUE" VARCHAR2(255 BYTE),
  CONSTRAINT "LD_PRELOAD_ROW_TYPE_PK" PRIMARY KEY ("PRELOAD_ROW_TYPE_ID")
    USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS
    STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
    PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
    TABLESPACE "DIR"  ENABLE,
  CONSTRAINT "FK_PRELOADFILEID" FOREIGN KEY ("PRELOAD_FILE_ID")
  REFERENCES "LD_PRELOAD_FILE" ("PRELOAD_FILE_ID") ENABLE
) SEGMENT CREATION IMMEDIATE
PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
TABLESPACE "DIR" ;

COMMENT ON COLUMN "LD_PRELOAD_ROW_TYPE"."PRELOAD_ROW_TYPE_ID" IS 'Primary key';
COMMENT ON COLUMN "LD_PRELOAD_ROW_TYPE"."PRELOAD_FILE_ID" IS 'Referencing preload file';
COMMENT ON COLUMN "LD_PRELOAD_ROW_TYPE"."NAME" IS 'Name of this item';
COMMENT ON COLUMN "LD_PRELOAD_ROW_TYPE"."DESCRIPTION" IS 'Description of this item';
COMMENT ON COLUMN "LD_PRELOAD_ROW_TYPE"."IDENTIFIER_COLUMN_NO" IS 'This row type identifier column number in
the file';
COMMENT ON COLUMN "LD_PRELOAD_ROW_TYPE"."IDENTIFIER_VALUE" IS 'This row type identifier value';