
-- Gender
INSERT INTO "PER_GENDER" (GENDER_ID, NAME, IS_MALE, IS_FEMALE, IS_OTHER) VALUES ('1', 'Male', '1', '0', '0');
INSERT INTO "PER_GENDER" (GENDER_ID, NAME, IS_MALE, IS_FEMALE, IS_OTHER) VALUES ('2', 'Female', '0', '1', '0');
INSERT INTO "PER_GENDER" (GENDER_ID, NAME, IS_MALE, IS_FEMALE, IS_OTHER) VALUES ('3', 'Other', '0', '0', '1');

-- Company type
INSERT INTO "PER_COMPANY_TYPE" (COMPANY_TYPE_ID, NAME) VALUES ('1', 'reinsurance.companay');
INSERT INTO "PER_COMPANY_TYPE" (COMPANY_TYPE_ID, NAME) VALUES ('2', 'company');
INSERT INTO "PER_COMPANY_TYPE" (COMPANY_TYPE_ID, NAME) VALUES ('3', 'broker');
INSERT INTO "PER_COMPANY_TYPE" (COMPANY_TYPE_ID, NAME) VALUES ('4', 'coinsurance');
INSERT INTO "PER_COMPANY_TYPE" (COMPANY_TYPE_ID, NAME) VALUES ('5', 'single.company');

-- Civil status
INSERT INTO "PER_CIVIL_STATUS" (CIVIL_STATUS_ID, NAME) VALUES ('1', 'marrried');
INSERT INTO "PER_CIVIL_STATUS" (CIVIL_STATUS_ID, NAME) VALUES ('2', 'widowed');
INSERT INTO "PER_CIVIL_STATUS" (CIVIL_STATUS_ID, NAME) VALUES ('3', 'separated');
INSERT INTO "PER_CIVIL_STATUS" (CIVIL_STATUS_ID, NAME) VALUES ('4', 'single');

-- Person status
INSERT INTO "PER_PERSON_STATUS" (PERSON_STATUS_ID, NAME, IS_INDIVIDUAL, IS_COMPANY) VALUES ('1', 'alive', '1', '0');
INSERT INTO "PER_PERSON_STATUS" (PERSON_STATUS_ID, NAME, IS_INDIVIDUAL, IS_COMPANY) VALUES ('2', 'died', '1', '0');
INSERT INTO "PER_PERSON_STATUS" (PERSON_STATUS_ID, NAME, IS_INDIVIDUAL, IS_COMPANY) VALUES ('3', 'quote.only', '1', '0');
INSERT INTO "PER_PERSON_STATUS" (PERSON_STATUS_ID, NAME, IS_INDIVIDUAL, IS_COMPANY) VALUES ('4', 'not.applicable', '1', '0');

-- ID Type
INSERT INTO "PER_ID_TYPE" (ID_TYPE_ID, NAME, IS_INDIVIDUAL, IS_COMPANY) VALUES ('1', 'birth.certificate', '1', '0');
INSERT INTO "PER_ID_TYPE" (ID_TYPE_ID, NAME, IS_INDIVIDUAL, IS_COMPANY) VALUES ('2', 'company.registration', '0', '1');
INSERT INTO "PER_ID_TYPE" (ID_TYPE_ID, NAME, IS_INDIVIDUAL, IS_COMPANY) VALUES ('3', 'drivers.license', '1', '0');
INSERT INTO "PER_ID_TYPE" (ID_TYPE_ID, NAME, IS_INDIVIDUAL, IS_COMPANY) VALUES ('4', 'foriegn.id.card', '1', '0');
INSERT INTO "PER_ID_TYPE" (ID_TYPE_ID, NAME, IS_INDIVIDUAL, IS_COMPANY) VALUES ('5', 'maltese.id.card', '1', '0');
INSERT INTO "PER_ID_TYPE" (ID_TYPE_ID, NAME, IS_INDIVIDUAL, IS_COMPANY) VALUES ('6', 'passport', '1', '0');
INSERT INTO "PER_ID_TYPE" (ID_TYPE_ID, NAME, IS_INDIVIDUAL, IS_COMPANY) VALUES ('7', 'personal.nit', '1', '0');
INSERT INTO "PER_ID_TYPE" (ID_TYPE_ID, NAME, IS_INDIVIDUAL, IS_COMPANY) VALUES ('8', 'system.identifier', '1', '0');

-- Language
INSERT INTO "PER_LANGUAGE" (LANGUAGE_ID, I18NKEY) VALUES ('1', 'es-ES');
INSERT INTO "PER_LANGUAGE" (LANGUAGE_ID, I18NKEY) VALUES ('2', 'en-GB');

commit;

