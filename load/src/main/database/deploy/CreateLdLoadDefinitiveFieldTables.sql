-- Deploy itaca-load:CreateLdLoadDefinitiveField to oracle
-- XXX Add DDLs here.
create table LD_LOAD_DEFINITIVE_FIELD
(
	LOAD_DEFINITIVE_FIELD_ID NUMBER not null primary key,
	LOAD_DEFINITIVE_TABLE_ID NUMBER,
	FIELD_NAME VARCHAR2(100),
	IS_KEY_FIELD NUMBER default 0
)
TABLESPACE LOAD LOGGING
/

comment on table LD_LOAD_DEFINITIVE_FIELD is 'load_definitive_field'
/

comment on column LD_LOAD_DEFINITIVE_FIELD.LOAD_DEFINITIVE_FIELD_ID is 'Primary key.'
/

comment on column LD_LOAD_DEFINITIVE_FIELD.LOAD_DEFINITIVE_TABLE_ID is 'Referencing definitive table.'
/

comment on column LD_LOAD_DEFINITIVE_FIELD.FIELD_NAME is 'Name of field within the referenced
table.'
/

comment on column LD_LOAD_DEFINITIVE_FIELD.IS_KEY_FIELD is 'A boolean Indicates if this field the
primary key.'
/

ALTER TABLE LD_LOAD_DEFINITIVE_FIELD ADD CONSTRAINT LD_LOAD_DEFINITIVE_FIELD_FK1 FOREIGN KEY ( LOAD_DEFINITIVE_TABLE_ID ) REFERENCES LD_LOAD_DEFINITIVE_TABLE ( LOAD_DEFINITIVE_TABLE_ID ) NOT DEFERRABLE

/

CREATE SEQUENCE ld_load_definitive_field_seq START WITH 1 INCREMENT BY 1 MAXVALUE 9999999999 MINVALUE 1 NOCACHE

/
