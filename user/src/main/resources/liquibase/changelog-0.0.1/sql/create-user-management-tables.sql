---------------------------------------------------------------------------
-- User manager artifacts in the database
---------------------------------------------------------------------------

----------------- language table -----------------
CREATE TABLE usr_language (
    language_id NUMBER(19) NOT NULL,
    code        VARCHAR2(50) NOT NULL,
    value       VARCHAR2(1000) NOT NULL
)
    TABLESPACE #DATA_TABLESPACE# LOGGING;

COMMENT ON TABLE usr_language IS 'Languages';
COMMENT ON COLUMN usr_language.language_id IS 'Language identifier';
COMMENT ON COLUMN usr_language.code IS 'language code';
COMMENT ON COLUMN usr_language.value IS 'language description';

ALTER TABLE usr_language ADD CONSTRAINT usr_language_pk PRIMARY KEY ( language_id )
    USING INDEX TABLESPACE #INDEX_TABLESPACE# LOGGING;

ALTER TABLE usr_language ADD CONSTRAINT usr_language_uni UNIQUE ( value )
    USING INDEX TABLESPACE #INDEX_TABLESPACE# LOGGING;

CREATE SEQUENCE usr_language_id_seq START WITH 1 NOCACHE ORDER;
----------------- language table end -----------------

----------------- User table -----------------
CREATE TABLE usr_user (
    user_id             NUMBER(19) NOT NULL,
    username            VARCHAR2(50) NOT NULL,
    password            VARCHAR2(50 CHAR),
    first_name          VARCHAR2(100 CHAR),
    last_name           VARCHAR2(100 CHAR),
    company_code        VARCHAR2(100 CHAR),
    description         VARCHAR2(1000 CHAR),
    language_id         NUMBER(19) NOT NULL,
    company_start_date  DATE,
    company_end_date    DATE,
    email               VARCHAR2(100 CHAR),
    blocked             NUMBER(1) DEFAULT 0 NOT NULL,
    blocked_date        DATE
)
    TABLESPACE #DATA_TABLESPACE# LOGGING;

-- Primary key
ALTER TABLE usr_user ADD CONSTRAINT usr_user_pk PRIMARY KEY ( user_id )
    USING INDEX TABLESPACE #INDEX_TABLESPACE# LOGGING;

-- Primary key sequence
CREATE SEQUENCE usr_user_id_seq START WITH 1 NOCACHE ORDER;

-- Index primary key
CREATE INDEX usr_user_id_idx ON
    usr_user ( user_id ASC )
        TABLESPACE #INDEX_TABLESPACE# LOGGING;

-- Index user name
CREATE INDEX usr_username_idx ON
    usr_user ( username ASC )
        TABLESPACE #INDEX_TABLESPACE# LOGGING;

-- Index first_name
CREATE INDEX usr_first_name_idx ON
    usr_user ( first_name ASC )
        TABLESPACE #INDEX_TABLESPACE# LOGGING;

-- Index last_name
CREATE INDEX usr_last_name_idx ON
    usr_user ( last_name ASC )
        TABLESPACE #INDEX_TABLESPACE# LOGGING;

-- Index email
CREATE INDEX usr_email_idx ON
    usr_user ( email ASC )
        TABLESPACE #INDEX_TABLESPACE# LOGGING;

--- language foreign key
ALTER TABLE usr_user ADD CONSTRAINT usr_language_id_fk FOREIGN KEY ( language_id )
    REFERENCES usr_language ( language_id )
NOT DEFERRABLE;
----------------- User table END -----------------

