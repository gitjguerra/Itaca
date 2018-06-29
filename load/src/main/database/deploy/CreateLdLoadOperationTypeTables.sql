-- Deploy itaca-load:CreateLdLoadOperationType to oracle
-- XXX Add DDLs here.
create table LD_LOAD_OPERATION_TYPE
(
	LOAD_OPERATION_TYPE_ID NUMBER not null
		primary key,
	NAME VARCHAR2(100),
	DESCRIPTION VARCHAR2(200)
)
TABLESPACE LOAD LOGGING
/

comment on table LD_LOAD_OPERATION_TYPE is 'load_operation_type'
/

comment on column LD_LOAD_OPERATION_TYPE.LOAD_OPERATION_TYPE_ID is 'Primary key'
/

comment on column LD_LOAD_OPERATION_TYPE.NAME is 'Name of the operation'
/

comment on column LD_LOAD_OPERATION_TYPE.DESCRIPTION is 'Description of the operation'
/
CREATE SEQUENCE ld_load_operation_type_seq START WITH 1 INCREMENT BY 1 MAXVALUE 9999999999 MINVALUE 1 NOCACHE

/

--Preloaded data

Insert into LD_LOAD_OPERATION_TYPE (LOAD_OPERATION_TYPE_ID,NAME,DESCRIPTION) values ('1','Insert or Update','Operation type to handle insert or updates');

/