---------------------------------------------------------------------------
-- User manager artifacts in the database
---------------------------------------------------------------------------


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

ALTER TABLE usr_profile_type ADD CONSTRAINT usr_profile_type_pk PRIMARY KEY ( profile_type_id )
    USING INDEX TABLESPACE LOGIN;

ALTER TABLE usr_profile_type ADD CONSTRAINT usr_profile_type_uk1 UNIQUE ( value )
    USING INDEX TABLESPACE LOGIN;

CREATE SEQUENCE usr_profile_type_id_seq START WITH 1 NOCACHE ORDER;
----------------- user profile table end -----------------

----------------- user profile table -----------------
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
----------------- user profile table end -----------------

----------------- language table -----------------
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
    TABLESPACE LOGIN;

-- Primary key
ALTER TABLE usr_user ADD CONSTRAINT usr_user_pk PRIMARY KEY ( user_id )
    USING INDEX TABLESPACE LOGIN;

-- Primary key sequence
CREATE SEQUENCE usr_user_id_seq START WITH 1 NOCACHE ORDER;

-- Index user name
CREATE INDEX usr_username_idx ON
    usr_user ( username ASC )
        TABLESPACE LOGIN;

-- Index first_name
CREATE INDEX usr_first_name_idx ON
    usr_user ( first_name ASC )
        TABLESPACE LOGIN;

-- Index last_name
CREATE INDEX usr_last_name_idx ON
    usr_user ( last_name ASC )
        TABLESPACE LOGIN;

-- Index email
CREATE INDEX usr_email_idx ON
    usr_user ( email ASC )
        TABLESPACE LOGIN;

-- unique username
ALTER TABLE usr_user ADD CONSTRAINT usr_username_uk1 UNIQUE ( username )
    USING INDEX TABLESPACE LOGIN;

--- language foreign key
ALTER TABLE usr_user ADD CONSTRAINT usr_language_id_fk FOREIGN KEY ( language_id )
    REFERENCES usr_language ( language_id )
NOT DEFERRABLE;
----------------- User table END -----------------

----------------- user config table -----------------

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

----------------- user config end -----------------










