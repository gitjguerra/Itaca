DROP INDEX PER_RELATION_TYPE FORCE;
DROP UNIQUE INDEX PER_RELATION_TYPE_ID2 FORCE;
DROP INDEX PER_RELATION_TYPE_PER_ID1 FORCE;
DROP INDEX PER_RELATION_PER_ID2 FORCE;
DROP INDEX PER_RELATION_PER_ID3 FORCE;

DROP TABLE PER_RELATION_TYPE CASCADE CONSTRAINTS PURGE;
DROP TABLE PER_RELATION_PER CASCADE CONSTRAINTS PURGE;

DROP SEQUENCE SEQ_RELATION_PER;
DROP SEQUENCE SEQ_RELATION_TYPE;


COMMIT;