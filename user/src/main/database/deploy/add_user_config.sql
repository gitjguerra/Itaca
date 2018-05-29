-- Deploy itaca-user:add_user_config to oracle

CREATE TABLE usr_user_config (
    user_config_id   NUMBER(19) NOT NULL,
    profile_id       NUMBER(19) NOT NULL,
    user_id          NUMBER(19) NOT NULL
)
    TABLESPACE LOGIN;

COMMENT ON COLUMN usr_user_config.user_config_id IS 'User configuration unique identifier';
COMMENT ON COLUMN usr_user_config.profile_id IS 'Associated profile';
COMMENT ON COLUMN usr_user_config.user_id IS 'Associated user';

-- Primary key
ALTER TABLE usr_user_config ADD CONSTRAINT usr_user_config_pk PRIMARY KEY ( user_config_id )
    USING INDEX TABLESPACE LOGIN;

-- Primary key sequence
CREATE SEQUENCE usr_user_config_id_seq START WITH 1 NOCACHE ORDER;

-- Indexes...
CREATE INDEX usr_user_config_id2 ON usr_user_config ( user_id ASC )
        TABLESPACE LOGIN;

CREATE INDEX usr_user_config_id3 ON usr_user_config ( profile_id ASC )
        TABLESPACE LOGIN;

CREATE INDEX usr_user_config_id4 ON usr_user_config ( user_id ASC, profile_id ASC )
        TABLESPACE LOGIN;

-- Foreign keys...
ALTER TABLE usr_user_config ADD CONSTRAINT usr_user_config_fk1 FOREIGN KEY ( profile_id )
    REFERENCES usr_profile ( profile_id )
NOT DEFERRABLE;

ALTER TABLE usr_user_config ADD CONSTRAINT usr_user_config_fk2 FOREIGN KEY ( user_id )
    REFERENCES usr_user ( user_id )
NOT DEFERRABLE;

-- Trigger
CREATE OR REPLACE TRIGGER usr_user_config_bi BEFORE
    INSERT ON usr_user_config
    FOR EACH ROW
    WHEN (
        new.user_config_id IS NULL
    )
BEGIN
    :new.user_config_id := usr_user_config_id_seq.nextval;
END;
