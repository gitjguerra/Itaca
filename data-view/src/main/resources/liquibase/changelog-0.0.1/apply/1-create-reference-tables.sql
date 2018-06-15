

----------------- AUDIT table -----------------
CREATE TABLE dat_audit
  (
    audit_id              NUMBER (19) NOT NULL,
    audit_timestamp       TIMESTAMP,
    audit_username        VARCHAR2 (1000 CHAR),
    audit_operation       VARCHAR2 (1000 CHAR),
    audit_sql_command     VARCHAR2 (1000 CHAR)
  )
  PCTFREE 10 PCTUSED 40 TABLESPACE DATAVIEW LOGGING STORAGE
  (
    PCTINCREASE 0 MINEXTENTS 1 MAXEXTENTS UNLIMITED FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT
  );

COMMENT ON TABLE dat_audit IS 'AUDIT TRANSACTIONS';
COMMENT ON COLUMN dat_audit.audit_id IS 'Unique identifier';
COMMENT ON COLUMN dat_audit.audit_timestamp IS 'timestamp activity';
COMMENT ON COLUMN dat_audit.audit_username IS 'username activity';
COMMENT ON COLUMN dat_audit.audit_operation IS 'operation activity';
COMMENT ON COLUMN dat_audit.audit_sql_command IS 'sql command execute';

-- Primary key
ALTER TABLE dat_audit ADD CONSTRAINT dat_audit_pk PRIMARY KEY ( audit_id )
    USING INDEX TABLESPACE DATAVIEW;

-- Sequence
CREATE SEQUENCE dat_audit_seq START WITH 1 INCREMENT BY 1 MAXVALUE 9999999999 MINVALUE 1 NOCACHE ;
----------------- AUDIT table end -----------------
