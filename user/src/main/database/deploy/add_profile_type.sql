-- Deploy itaca-user:profile_type to oracle

----------------- user profile table type -----------------
CREATE TABLE usr_profile_type (
    profile_type_id   NUMBER(19) NOT NULL,
    value            VARCHAR2(1000)
)
    TABLESPACE LOGIN;

COMMENT ON COLUMN usr_profile_type.profile_type_id IS 'Profile type unique identifier';
COMMENT ON COLUMN usr_profile_type.value IS 'Value of the literal description';

CREATE INDEX profile_type_id_idx ON usr_profile_type(profile_type_id ASC)
    TABLESPACE LOGIN;

CREATE SEQUENCE usr_profile_type_id_seq START WITH 1 NOCACHE ORDER;

ALTER TABLE usr_profile_type ADD CONSTRAINT usr_profile_type_pk PRIMARY KEY ( profile_type_id )
    USING INDEX TABLESPACE LOGIN;

ALTER TABLE usr_profile_type ADD CONSTRAINT usr_profile_type_uk1 UNIQUE ( value )
    USING INDEX TABLESPACE LOGIN;

-- Trigger
CREATE OR REPLACE TRIGGER usr_profile_type_bi BEFORE
    INSERT ON usr_profile_type
    FOR EACH ROW
    WHEN (
        new.profile_type_id IS NULL
    )
BEGIN
    :new.profile_type_id := usr_profile_type_id_seq.nextval;
END;