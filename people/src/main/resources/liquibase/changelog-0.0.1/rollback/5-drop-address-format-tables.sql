DROP INDEX dir_format_1_id1 FORCE;
DROP INDEX dir_format_1_id2 FORCE;
DROP INDEX dir_format_1_id3 FORCE;
DROP INDEX dir_format_1_id4 FORCE;

DROP TABLE dir_format_1 CASCADE CONSTRAINTS PURGE;
DROP SEQUENCE SEQ_TYPE_ADDRESS;

COMMIT;