DROP INDEX per_person_fk1 FORCE;
DROP INDEX per_person_fk2 FORCE;
DROP INDEX per_person_fk3 FORCE;

DROP SEQUENCE per_person_seq;
DROP TABLE per_person  CASCADE CONSTRAINTS PURGE;

DROP INDEX per_person_detail_fk01 FORCE;
DROP INDEX per_person_detail_fk02 FORCE;
DROP INDEX per_person_detail_fk03 FORCE;
DROP INDEX per_person_detail_fk04 FORCE;
DROP INDEX per_person_detail_fk05 FORCE;

DROP SEQUENCE per_person_detail_seq;
DROP TABLE per_person_detail  CASCADE CONSTRAINTS PURGE;


DROP INDEX per_identification_ID1 FORCE;
DROP INDEX per_identification_ID2 FORCE;
DROP INDEX per_identification_ID3 FORCE;
DROP INDEX per_identification_ID4 FORCE;

DROP SEQUENCE per_identification_seq;
DROP TABLE per_identification  CASCADE CONSTRAINTS PURGE;







