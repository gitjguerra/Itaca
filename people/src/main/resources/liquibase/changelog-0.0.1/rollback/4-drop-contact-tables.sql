-- DROP INDEX CONTACT****************************************************************

DROP INDEX PER_CONTACT_ID1;
DROP INDEX PER_CONTACT_ID2;
DROP INDEX PER_CONTACT_ID3;

-- DROP TABLE CONTACT END ****************************************************************

DROP TABLE PER_CONTACT CASCADE CONSTRAINTS;

-- DROP CONTACT END ****************************************************************

DROP SEQUENCE per_identification_seq;
DROP TABLE per_identification;

DROP SEQUENCE per_person_detail_seq;
DROP TABLE per_person_detail;

DROP SEQUENCE per_person_seq;
DROP TABLE per_person;


COMMIT;
