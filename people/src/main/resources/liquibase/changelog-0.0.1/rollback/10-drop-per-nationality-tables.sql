

DROP INDEX PER_NATIONALITY_ID1 FORCE;
DROP INDEX PER_NATIONALITY_ID2 FORCE;
DROP INDEX PER_NATIONALITY_ID3 FORCE;
DROP INDEX PER_NATIONALITY_ID4 FORCE;
DROP TABLE PER_NATIONALITY CASCADE CONSTRAINTS;
DROP TABLE PER_RELATION_PER CASCADE CONSTRAINTS;
DROP SEQUENCE SEQ_NATIONALITY;


COMMIT;