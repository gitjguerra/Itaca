create trigger LD_PRELOAD_DEFINITION_trigger
before insert on LD_PRELOAD_DEFINITION
for each row
begin
    select LD_PRELOAD_DEFINITION_SEQ.nextval into :new.PRELOAD_DEFINITION_ID from dual;
end;

create trigger LD_PRELOAD_FILE_trigger
before insert on LD_PRELOAD_FILE
for each row
begin
    select LD_PRELOAD_FILE_SEQ.nextval into :new.PRELOAD_FILE_ID from dual;
end;

create trigger LD_PRELOAD_ROW_TYPE_trigger
before insert on LD_PRELOAD_ROW_TYPE
for each row
begin
    select LD_PRELOAD_ROW_TYPE_SEQ.nextval into :new.PRELOAD_ROW_TYPE_ID from dual;
end;

create trigger LD_PRELOAD_FIELD_DEF_trigger
before insert on LD_PRELOAD_FIELD_DEFINITION
for each row
begin
    select LD_PRELOAD_FIELD_DEF_SEQ.nextval into :new.LD_PRELOAD_FIELD_DEF_ID from dual;
end;
