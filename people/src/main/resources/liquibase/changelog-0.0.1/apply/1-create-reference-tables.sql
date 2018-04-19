

----------------- gender table -----------------
CREATE TABLE per_gender
  (
    gender_id   NUMBER (19) NOT NULL,
    name        VARCHAR2 (1000 CHAR),
    is_male     NUMBER (1) DEFAULT 0,
    is_female   NUMBER (1) DEFAULT 0,
    is_other    NUMBER (1) DEFAULT 0
  )
  PCTFREE 10 PCTUSED 40 TABLESPACE PEOPLE LOGGING STORAGE
  (
    PCTINCREASE 0 MINEXTENTS 1 MAXEXTENTS UNLIMITED FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT
  );

COMMENT ON TABLE per_gender IS 'Gender';
COMMENT ON COLUMN per_gender.gender_id IS 'Unique identifier';
COMMENT ON COLUMN per_gender.name IS 'Name of the gender';
COMMENT ON COLUMN per_gender.is_male IS 'Indicator for male';
COMMENT ON COLUMN per_gender.is_female IS 'Indicator for female';
COMMENT ON COLUMN per_gender.is_other IS 'Indicator for other';

-- Primary key
ALTER TABLE per_gender ADD CONSTRAINT per_gender_pk PRIMARY KEY ( gender_id )
    USING INDEX TABLESPACE PEOPLE;

-- Sequence
CREATE SEQUENCE per_gender_seq START WITH 1 INCREMENT BY 1 MAXVALUE 9999999999 MINVALUE 1 NOCACHE ;
----------------- gender table end -----------------

----------------- company type table -----------------
CREATE TABLE per_company_type
  (
    company_type_id NUMBER (19) NOT NULL,
    name                 VARCHAR2 (1000 CHAR)
  )
  PCTFREE 10 PCTUSED 40 TABLESPACE PEOPLE LOGGING STORAGE
  (
    PCTINCREASE 0 MINEXTENTS 1 MAXEXTENTS UNLIMITED FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT
  ) ;

COMMENT ON TABLE per_company_type IS 'Legal people types' ;
COMMENT ON COLUMN per_company_type.company_type_id IS 'Unique identifier' ;
COMMENT ON COLUMN per_company_type.name IS 'Name of the company type' ;

-- Primary key
ALTER TABLE per_company_type ADD CONSTRAINT per_company_type_pk PRIMARY KEY ( company_type_id )
    USING INDEX TABLESPACE PEOPLE;

-- Sequence
CREATE SEQUENCE per_company_type_seq START WITH 1 INCREMENT BY 1 MAXVALUE 9999999999 MINVALUE 1 NOCACHE ;
----------------- company type table end -----------------


----------------- civil status table -----------------
CREATE TABLE per_civil_status
  (
    civil_status_id NUMBER (19) NOT NULL ,
    name           VARCHAR2 (1000 CHAR)
  )
  PCTFREE 10 PCTUSED 40 TABLESPACE PEOPLE LOGGING STORAGE
  (
    PCTINCREASE 0 MINEXTENTS 1 MAXEXTENTS UNLIMITED FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT
  ) ;

COMMENT ON TABLE per_civil_status IS 'Civil status types' ;
COMMENT ON COLUMN per_civil_status.civil_status_id IS  'Unique identifier' ;
COMMENT ON COLUMN per_civil_status.name IS  'Name of the civil status type' ;

-- Primary key
ALTER TABLE per_civil_status ADD CONSTRAINT per_civil_status_pk PRIMARY KEY ( civil_status_id )
    USING INDEX TABLESPACE PEOPLE;

-- Sequence
CREATE SEQUENCE per_civil_status_seq START WITH 1 INCREMENT BY 1 MAXVALUE 9999999999 MINVALUE 1 NOCACHE ;
----------------- civil status table end -----------------

----------------- person_status table -----------------
CREATE TABLE per_person_status
  (
    person_status_id NUMBER (19) NOT NULL,
    name             VARCHAR2 (1000 CHAR),
    is_individual    NUMBER (1) DEFAULT 0,
    is_company       NUMBER (1) DEFAULT 0
  )
  PCTFREE 10 PCTUSED 40 TABLESPACE PEOPLE LOGGING STORAGE
  (
    PCTINCREASE 0 MINEXTENTS 1 MAXEXTENTS UNLIMITED FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT
  ) ;
COMMENT ON TABLE per_person_status IS 'Person statuses' ;
COMMENT ON COLUMN per_person_status.person_status_id IS 'Unique identifier' ;
COMMENT ON COLUMN per_person_status.name IS 'Name of the person status' ;
COMMENT ON COLUMN per_person_status.is_individual IS 'Indicates if this status is an idividual' ;
COMMENT ON COLUMN per_person_status.is_company IS 'Indicates if this status is a company' ;

-- Primary key
ALTER TABLE per_person_status ADD CONSTRAINT per_person_status_pk PRIMARY KEY ( person_status_id )
    USING INDEX TABLESPACE PEOPLE;

-- Sequence
CREATE SEQUENCE per_person_status_seq START WITH 1 INCREMENT BY 1 MAXVALUE 9999999999 MINVALUE 1 NOCACHE ;
----------------- person_status table -----------------

----------------- language table -----------------
CREATE TABLE per_language
  (
    language_id NUMBER (19) NOT NULL ,
    i18nKey     VARCHAR2 (100 CHAR)
  )
  PCTFREE 10 PCTUSED 40 TABLESPACE PEOPLE LOGGING STORAGE
  (
    PCTINCREASE 0 MINEXTENTS 1 MAXEXTENTS UNLIMITED FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT
  ) ;
COMMENT ON TABLE per_language IS 'Languages' ;
COMMENT ON COLUMN per_language.language_id IS 'Unique identifier' ;
COMMENT ON COLUMN per_language.i18nKey IS 'Valor del idioma' ;

-- Primary key
ALTER TABLE per_language ADD CONSTRAINT per_language_pk PRIMARY KEY ( language_id )
    USING INDEX TABLESPACE PEOPLE;

-- Sequence
CREATE SEQUENCE per_language_seq START WITH 1 INCREMENT BY 1 MAXVALUE 9999999999 MINVALUE 1 NOCACHE ;
----------------- language table end -----------------

----------------- Identification type -----------------
CREATE TABLE per_id_type (
    id_type_id      NUMBER (19) NOT NULL,
    name            VARCHAR2 (1000 CHAR),
    is_individual   NUMBER (1) DEFAULT 0,
    is_company      NUMBER (1) DEFAULT 0
)
TABLESPACE PEOPLE;

COMMENT ON TABLE  per_id_type IS               'Identification type for person';
COMMENT ON COLUMN per_id_type.id_type_id IS    'Unique ID for ID type';
COMMENT ON COLUMN per_id_type.name IS          'Identification type name';
COMMENT ON COLUMN per_id_type.is_individual IS 'Indicates if the ID type represents a individual';
COMMENT ON COLUMN per_id_type.is_company IS    'Indicates if the ID type represents a company';

-- Indexes
CREATE INDEX id_type_id_idx ON per_id_type(id_type_id ASC) TABLESPACE PEOPLE;

-- Primary key
ALTER TABLE per_id_type ADD CONSTRAINT per_id_type_pk PRIMARY KEY ( id_type_id )
    USING INDEX TABLESPACE PEOPLE;

-- Sequence
CREATE SEQUENCE per_id_type_seq START WITH 1 INCREMENT BY 1 MAXVALUE 9999999999 MINVALUE 1 NOCACHE ;
----------------- identification type end -----------------

----------------- Contact type -----------------
CREATE TABLE per_contact_type (
    contact_type_id NUMBER (19) NOT NULL ,
    value           VARCHAR2 (1000 CHAR)
)
TABLESPACE PEOPLE;

COMMENT ON TABLE per_contact_type IS                  'Contact types' ;
COMMENT ON COLUMN per_contact_type.contact_type_id IS 'Unique identifier' ;
COMMENT ON COLUMN per_contact_type.value IS           'Value. e.g.: Telephone, Fax, E-mail...' ;

-- Indexes
CREATE UNIQUE INDEX per_contact_type_pk ON per_contact_type (contact_type_id ASC) TABLESPACE PEOPLE;

-- Primary key
ALTER TABLE per_contact_type ADD CONSTRAINT per_contact_type_pk PRIMARY KEY ( contact_type_id ) USING INDEX per_contact_type_pk ;

-- Sequence
CREATE SEQUENCE per_contact_type_seq START WITH 1 INCREMENT BY 1 MAXVALUE 9999999999 MINVALUE 1 NOCACHE ;
----------------- Contact type end -----------------

----------------- Relation type -----------------
CREATE TABLE per_relation_type (
    relation_type_id NUMBER (19) NOT NULL ,
    value          VARCHAR2 (200 CHAR)
)
TABLESPACE PEOPLE;

COMMENT ON TABLE per_relation_type IS 'Types of relations' ;
COMMENT ON COLUMN per_relation_type.relation_type_id IS 'Unique identifier' ;
COMMENT ON COLUMN per_relation_type.value IS 'Name of the relation type' ;

-- Indexes
CREATE INDEX per_relation_type_id1 ON per_relation_type (value ASC) TABLESPACE PEOPLE;
CREATE UNIQUE INDEX per_relation_type_id2 ON per_relation_type (relation_type_id ASC) TABLESPACE PEOPLE;

-- Primary key
ALTER TABLE per_relation_type ADD CONSTRAINT per_relation_type_pk PRIMARY KEY ( relation_type_id ) ;

-- Sequence
CREATE SEQUENCE per_relation_type_seq START WITH 1 INCREMENT BY 1 MAXVALUE 9999999999 MINVALUE 1 NOCACHE ;
----------------- Relation type end -----------------

----------------- Company person type -----------------
CREATE TABLE per_company_person_type (
    company_person_type_id NUMBER (19) NOT NULL ,
    value                 VARCHAR2 (1000 CHAR)
)
TABLESPACE PEOPLE;

COMMENT ON TABLE per_company_person_type IS 'Types of company person' ;
COMMENT ON COLUMN per_company_person_type.company_person_type_id IS 'Unique identifier' ;
COMMENT ON COLUMN per_company_person_type.value IS 'Literal value' ;

-- Indexes
CREATE INDEX per_company_person_type_id1 ON per_company_person_type (company_person_type_id ASC) TABLESPACE PEOPLE;

-- Primary key
ALTER TABLE per_company_person_type ADD CONSTRAINT per_company_person_type_pk PRIMARY KEY ( company_person_type_id ) ;

-- Sequence
CREATE SEQUENCE per_company_person_type_seq INCREMENT BY 1 MAXVALUE 9999999999 MINVALUE 1 NOCACHE ;
----------------- Company person type end -----------------
