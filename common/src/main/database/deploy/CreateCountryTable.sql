-- Deploy itaca-common:CreateCountryTable to oracle


----------------- country table -----------------
CREATE TABLE dir_country
  (
    country_id        NUMBER (19) NOT NULL ,
    iso_code          VARCHAR2 (100 CHAR) NOT NULL ,
    name              VARCHAR2 (1000 CHAR)
  )
  PCTFREE 10 PCTUSED 40 TABLESPACE DIR LOGGING STORAGE
  (
    PCTINCREASE 0 MINEXTENTS 1 MAXEXTENTS UNLIMITED FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT
  ) ;

COMMENT ON TABLE dir_country IS 'Countries reference table.' ;
COMMENT ON COLUMN dir_country.country_id IS 'Unique identifier' ;
COMMENT ON COLUMN dir_country.iso_code IS 'ISO code' ;
COMMENT ON COLUMN dir_country.name IS 'Country name' ;

--  primary key
ALTER TABLE dir_country ADD CONSTRAINT dir_country_pk PRIMARY KEY ( country_id ) USING INDEX TABLESPACE DIR;
-- Sequence
CREATE SEQUENCE dir_country_seq START WITH 1 INCREMENT BY 1 MAXVALUE 9999999999 MINVALUE 1 NOCACHE ;
----------------- country table end -----------------


INSERT INTO "DIR_COUNTRY" (COUNTRY_ID, ISO_CODE, NAME) VALUES ('1', 'ESP', 'Spain');
INSERT INTO "DIR_COUNTRY" (COUNTRY_ID, ISO_CODE, NAME) VALUES ('2', 'COL', 'Colombia');
INSERT INTO "DIR_COUNTRY" (COUNTRY_ID, ISO_CODE, NAME) VALUES ('3', 'CHL', 'Chile');
INSERT INTO "DIR_COUNTRY" (COUNTRY_ID, ISO_CODE, NAME) VALUES ('4', 'UK', 'United Kingdom');

commit;
