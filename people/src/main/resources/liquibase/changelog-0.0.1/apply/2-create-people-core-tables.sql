----------------- person table -----------------
CREATE TABLE per_person (
    person_id               NUMBER (19) NOT NULL ,
    id_type_id              NUMBER (19) NOT NULL ,
    identification_code     VARCHAR2 (50 CHAR) ,
    external_reference_code VARCHAR2 (50 CHAR) ,
    person_type             VARCHAR2 (50 CHAR) NOT NULL ,
    gender_id               NUMBER (19) ,
    date_of_birth           DATE ,
    company_type_id         NUMBER (19) ,
    start_date              DATE ,
    is_public_person        NUMBER (1) DEFAULT 0
  )
  PCTFREE 10 PCTUSED 40 TABLESPACE PEOPLE LOGGING STORAGE
  (
    PCTINCREASE 0 MINEXTENTS 1 MAXEXTENTS UNLIMITED FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT
  ) ;

COMMENT ON TABLE per_person IS 'person (individual or company)' ;
COMMENT ON COLUMN per_person.person_id IS 'Unique identifier' ;
COMMENT ON COLUMN per_person.id_type_id IS 'ID type' ;
COMMENT ON COLUMN per_person.identification_code IS 'identification code' ;
COMMENT ON COLUMN per_person.external_reference_code IS 'external reference code' ;
COMMENT ON COLUMN per_person.person_type IS 'Person type indicator (individual or company)' ;

COMMENT ON COLUMN per_person.gender_id IS 'Gender (individual only)' ;
COMMENT ON COLUMN per_person.date_of_birth IS 'Fecha de Nacimiento (individual only)' ;

COMMENT ON COLUMN per_person.company_type_id IS 'Company type (company only)' ;
COMMENT ON COLUMN per_person.start_date IS 'Start date (company only)' ;

COMMENT ON COLUMN per_person.is_public_person IS 'Indicator for whether this person in public' ;

-- Primary key
ALTER TABLE per_person ADD CONSTRAINT per_person_pk PRIMARY KEY ( person_id ) USING INDEX TABLESPACE PEOPLE;

-- Sequence
CREATE SEQUENCE per_person_seq START WITH 1 INCREMENT BY 1 MAXVALUE 9999999999 MINVALUE 1 NOCACHE ;

-- Foreign keys
CREATE INDEX per_person_fk1 ON per_person (id_type_id ASC) TABLESPACE PEOPLE;
CREATE INDEX per_person_fk2 ON per_person (company_type_id ASC) TABLESPACE PEOPLE;
CREATE INDEX per_person_fk3 ON per_person (gender_id ASC) TABLESPACE PEOPLE;
ALTER TABLE per_person ADD CONSTRAINT per_person_fk1 FOREIGN KEY ( id_type_id ) REFERENCES per_id_type ( id_type_id ) NOT DEFERRABLE ;
ALTER TABLE per_person ADD CONSTRAINT per_person_fk2 FOREIGN KEY ( company_type_id ) REFERENCES per_company_type ( company_type_id ) NOT DEFERRABLE ;
ALTER TABLE per_person ADD CONSTRAINT per_person_fk3 FOREIGN KEY ( gender_id ) REFERENCES per_gender ( gender_id ) NOT DEFERRABLE ;
----------------- person table end -----------------

----------------- person detail table -----------------
CREATE TABLE per_person_detail
  (
    person_detail_id  NUMBER (19) NOT NULL ,
    person_id         NUMBER (19) NOT NULL ,
    language_id       NUMBER (19) NOT NULL ,
    person_status_id  NUMBER (19) ,
    civil_status_id   NUMBER (19) ,
    country_id        NUMBER (19) ,
    name              VARCHAR2 (1000 CHAR) ,
    detail_type       VARCHAR2 (100 CHAR) NOT NULL ,
    name1             VARCHAR2 (200 CHAR) ,
    name2             VARCHAR2 (200 CHAR) ,
    surname1          VARCHAR2 (200 CHAR) ,
    surname2          VARCHAR2 (200 CHAR)
  )
  PCTFREE 10 PCTUSED 40 TABLESPACE PEOPLE LOGGING STORAGE
  (
    PCTINCREASE 0 MINEXTENTS 1 MAXEXTENTS UNLIMITED FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT
  ) ;

COMMENT ON TABLE per_person_detail IS 'detail for person (individual y company)' ;
COMMENT ON COLUMN per_person_detail.person_detail_id IS 'Unique identifier' ;
COMMENT ON COLUMN per_person_detail.person_id IS 'Associated person' ;

COMMENT ON COLUMN per_person_detail.language_id IS 'Associated language' ;
COMMENT ON COLUMN per_person_detail.country_id IS 'Associated country' ;
COMMENT ON COLUMN per_person_detail.name IS 'Composite name' ;
COMMENT ON COLUMN per_person_detail.detail_type IS 'detail_type (individual or company)' ;

COMMENT ON COLUMN per_person_detail.person_status_id IS 'person status (only individual)' ;
COMMENT ON COLUMN per_person_detail.civil_status_id IS 'civil status (only individual)' ;
COMMENT ON COLUMN per_person_detail.name1 IS 'first name (only individual)' ;
COMMENT ON COLUMN per_person_detail.name2 IS 'second name (only individual)' ;
COMMENT ON COLUMN per_person_detail.surname1 IS 'first surname (only individual)' ;
COMMENT ON COLUMN per_person_detail.surname2 IS 'secound surname (only individual)' ;

--  primary key
ALTER TABLE per_person_detail ADD CONSTRAINT per_person_detail_pk PRIMARY KEY ( person_detail_id ) USING INDEX TABLESPACE PEOPLE;

-- Sequence
CREATE SEQUENCE per_person_detail_seq START WITH 1 INCREMENT BY 1 MAXVALUE 9999999999 MINVALUE 1 NOCACHE ;

--  foreign keys
CREATE INDEX per_person_detail_fk01 ON per_person_detail ( person_id ASC) TABLESPACE PEOPLE;
CREATE INDEX per_person_detail_fk02 ON per_person_detail ( language_id ASC ) TABLESPACE PEOPLE;
CREATE INDEX per_person_detail_fk03 ON per_person_detail ( person_status_id ASC ) TABLESPACE PEOPLE;
CREATE INDEX per_person_detail_fk04 ON per_person_detail ( civil_status_id ASC ) TABLESPACE PEOPLE;
CREATE INDEX per_person_detail_fk05 ON per_person_detail ( country_id ASC ) TABLESPACE PEOPLE;
ALTER TABLE per_person_detail ADD CONSTRAINT per_person_detail_fk01 FOREIGN KEY ( person_id ) REFERENCES per_person ( person_id ) NOT DEFERRABLE ;
ALTER TABLE per_person_detail ADD CONSTRAINT per_person_detail_fk02 FOREIGN KEY ( language_id ) REFERENCES per_language ( language_id ) NOT DEFERRABLE ;
ALTER TABLE per_person_detail ADD CONSTRAINT per_person_detail_fk03 FOREIGN KEY ( person_status_id ) REFERENCES per_person_status ( person_status_id ) NOT DEFERRABLE ;
ALTER TABLE per_person_detail ADD CONSTRAINT per_person_detail_fk04 FOREIGN KEY ( civil_status_id ) REFERENCES per_civil_status ( civil_status_id ) NOT DEFERRABLE ;
ALTER TABLE per_person_detail ADD CONSTRAINT per_person_detail_fk05 FOREIGN KEY ( country_id ) REFERENCES dir_country ( country_id ) NOT DEFERRABLE ;

----------------- person detail table end -----------------

----------------- identification table -----------------
CREATE TABLE per_identification
  (
    identification_id       NUMBER (19) NOT NULL ,
    person_detail_id        NUMBER (19) NOT NULL ,
    id_type_id              NUMBER (19) NOT NULL ,
    country_id              NUMBER (19) NOT NULL ,
    identification_code     VARCHAR2 (50) NOT NULL ,
    issue_date              DATE
  )  PCTFREE 10 PCTUSED 40 TABLESPACE PEOPLE LOGGING STORAGE
  (
    PCTINCREASE 0 MINEXTENTS 1 MAXEXTENTS UNLIMITED FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT
  );
COMMENT ON TABLE per_identification IS 'Table to represent identification for each person';
COMMENT ON COLUMN per_identification.identification_id IS 'Unique identifier';
COMMENT ON COLUMN per_identification.person_detail_id IS 'Person detail foreign key';
COMMENT ON COLUMN per_identification.id_type_id IS 'Indentificatin type foreign key';
COMMENT ON COLUMN per_identification.country_id IS 'Country foreign key';
COMMENT ON COLUMN per_identification.identification_code IS 'Idenification code';
COMMENT ON COLUMN per_identification.issue_date IS 'Date idendification was issued';

--  indexes
CREATE INDEX per_identification_ID1 ON per_identification (identification_id ASC);
CREATE INDEX per_identification_ID2 ON per_identification (person_detail_id ASC);
CREATE INDEX per_identification_ID3 ON per_identification (id_type_id ASC);
CREATE INDEX per_identification_ID4 ON per_identification (country_id ASC);

--  foreign keys
ALTER TABLE per_identification ADD CONSTRAINT per_identification_PK PRIMARY KEY(identification_id);
ALTER TABLE per_identification ADD CONSTRAINT per_identification_FK1 FOREIGN KEY(country_id) REFERENCES dir_country (country_id) NOT DEFERRABLE;
ALTER TABLE per_identification ADD CONSTRAINT per_identification_FK2 FOREIGN KEY(person_detail_id) REFERENCES per_person_detail (person_detail_id) NOT DEFERRABLE;
ALTER TABLE per_identification ADD CONSTRAINT per_identification_FK3 FOREIGN KEY(id_type_id) REFERENCES per_id_type (id_type_id) NOT DEFERRABLE;

-- Sequence
CREATE SEQUENCE per_identification_seq START WITH 1 MAXVALUE 9999999999 MINVALUE 1 INCREMENT BY 1 NOCYCLE NOCACHE NOORDER;
----------------- identification table end -----------------
