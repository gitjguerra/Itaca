-- Deploy itaca-user:add_user_profile to oracle

CREATE TABLE usr_profile (
    profile_id        NUMBER(19) NOT NULL,
    profile_type_id   NUMBER(19) NOT NULL,
    value             VARCHAR2(1000) NOT NULL
)
    TABLESPACE LOGIN;

COMMENT ON COLUMN usr_profile.profile_id IS 'Profile unique identifier';
COMMENT ON COLUMN usr_profile.profile_type_id IS 'Identifier for the associated user profile type';
COMMENT ON COLUMN usr_profile.value IS 'Description of the profile';

-- Primary key
ALTER TABLE usr_profile ADD CONSTRAINT usr_profile_pk PRIMARY KEY ( profile_id )
    USING INDEX TABLESPACE LOGIN;

-- Primary key sequence
CREATE SEQUENCE usr_profile_id_seq START WITH 1 NOCACHE ORDER;

-- Constraint for value
ALTER TABLE usr_profile ADD CONSTRAINT usr_profile_uk1 UNIQUE ( value )
    USING INDEX TABLESPACE LOGIN;

-- Indexes...
CREATE INDEX usr_profile_id1 ON usr_profile ( profile_type_id ASC )
        TABLESPACE LOGIN;

CREATE INDEX usr_profile_id2 ON usr_profile ( profile_id ASC, profile_type_id ASC )
        TABLESPACE LOGIN;

--- Foreign key for profile_type_id
ALTER TABLE usr_profile ADD CONSTRAINT profile_type_id_fk1 FOREIGN KEY ( profile_type_id )
    REFERENCES usr_profile_type ( profile_type_id )
NOT DEFERRABLE;

-- Trigger
CREATE OR REPLACE TRIGGER usr_profile_bi BEFORE
    INSERT ON usr_profile
    FOR EACH ROW
    WHEN (
        new.profile_id IS NULL
    )
BEGIN
    :new.profile_id := usr_profile_id_seq.nextval;
END;