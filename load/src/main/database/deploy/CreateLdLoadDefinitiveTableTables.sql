-- Deploy itaca-load:CreateLdLoadDefinitiveTable to oracle
-- XXX Add DDLs here.
create table LD_LOAD_DEFINITIVE_TABLE
(
	LOAD_DEFINITIVE_TABLE_ID NUMBER not null
		primary key,
	TABLE_NAME VARCHAR2(100)
)
TABLESPACE LOAD LOGGING
/

comment on table LD_LOAD_DEFINITIVE_TABLE is 'load_definitive_table'
/

comment on column LD_LOAD_DEFINITIVE_TABLE.TABLE_NAME is 'Name of the definitive table'
/

CREATE SEQUENCE ld_load_definitive_table_seq START WITH 1 INCREMENT BY 1 MAXVALUE 9999999999 MINVALUE 1 NOCACHE
/