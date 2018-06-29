-- Deploy itaca-load:CreateLdLoadAtomicOperation to oracle
-- XXX Add DDLs here.
create table LD_LOAD_ATOMIC_OPERATION
(
	LOAD_ATOMIC_OPERATION_ID NUMBER not null
		primary key,
	LOAD_OPERATION_TYPE_ID NUMBER,
	ATOMIC_OP_CODE NUMBER not null,
	NAME VARCHAR2(50),
	DESCRIPTION VARCHAR2(200)
)
TABLESPACE LOAD LOGGING
/

comment on table LD_LOAD_ATOMIC_OPERATION is 'load_atomic_operation'
/

comment on column LD_LOAD_ATOMIC_OPERATION.LOAD_ATOMIC_OPERATION_ID is 'Primary key.'
/

comment on column LD_LOAD_ATOMIC_OPERATION.LOAD_OPERATION_TYPE_ID is 'Associated operation type.'
/

comment on column LD_LOAD_ATOMIC_OPERATION.ATOMIC_OP_CODE is 'Code associated to hard coded
atomic operation.'
/

comment on column LD_LOAD_ATOMIC_OPERATION.NAME is 'Name of the operation.'
/

comment on column LD_LOAD_ATOMIC_OPERATION.DESCRIPTION is 'Description of the operation.'
/
ALTER TABLE LD_LOAD_ATOMIC_OPERATION ADD CONSTRAINT LD_LOAD_ATOMIC_OPERATION_FK1 FOREIGN KEY ( LOAD_OPERATION_TYPE_ID ) REFERENCES LD_LOAD_OPERATION_TYPE ( LOAD_OPERATION_TYPE_ID ) NOT DEFERRABLE

/

CREATE SEQUENCE ld_load_atomic_operation_seq START WITH 1 INCREMENT BY 1 MAXVALUE 9999999999 MINVALUE 1 NOCACHE

/
