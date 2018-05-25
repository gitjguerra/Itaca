-- Deploy itaca-user:add_user_language to oracle

CREATE TABLE usr_language (
    language_id NUMBER(19) NOT NULL,
    code        VARCHAR2(50) NOT NULL,
    value       VARCHAR2(1000) NOT NULL
)
    TABLESPACE LOGIN;

COMMENT ON TABLE usr_language IS 'Languages';
COMMENT ON COLUMN usr_language.language_id IS 'Language unique identifier';
COMMENT ON COLUMN usr_language.code IS 'language code';
COMMENT ON COLUMN usr_language.value IS 'language description';

-- Primary key
ALTER TABLE usr_language ADD CONSTRAINT usr_language_pk PRIMARY KEY ( language_id )
    USING INDEX TABLESPACE LOGIN;

-- Primary key sequence
CREATE SEQUENCE usr_language_id_seq START WITH 1 NOCACHE ORDER;

-- Index language...
ALTER TABLE usr_language ADD CONSTRAINT usr_language_uni UNIQUE ( value )
    USING INDEX TABLESPACE LOGIN;

-- Trigger
CREATE OR REPLACE TRIGGER usr_language_bi BEFORE
    INSERT ON usr_language
    FOR EACH ROW
    WHEN (
        new.language_id IS NULL
    )
BEGIN
    :new.language_id := usr_language_id_seq.nextval;
END;