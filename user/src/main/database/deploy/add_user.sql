-- Deploy itaca-user:add_user to oracle

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

-- Trigger
CREATE OR REPLACE TRIGGER usr_user_bi BEFORE
    INSERT ON usr_user
    FOR EACH ROW
    WHEN (
        new.user_id IS NULL
    )
BEGIN
    :new.user_id := usr_user_id_seq.nextval;
END;
