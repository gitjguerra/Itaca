
DROP INDEX PER_PUBLIC_PERSON_TYPE_ID1 FORCE;
DROP TABLE PER_PUBLIC_PERSON_TYPE CASCADE CONSTRAINTS PURGE;
DROP SEQUENCE SEQ_PER_PUBLIC_PERSON_TYPE;


DROP INDEX PER_PUBLIC_PERSON_ID1 FORCE;
DROP INDEX PER_PUBLIC_PERSON_ID2 FORCE;
DROP INDEX PER_PUBLIC_PERSON_ID3 FORCE;
DROP INDEX PER_PUBLIC_PERSON_ID4 FORCE;
DROP TABLE PER_PUBLIC_PERSON CASCADE CONSTRAINTS PURGE;
DROP SEQUENCE SEQ_PER_PUBLIC_PERSON;


COMMIT;