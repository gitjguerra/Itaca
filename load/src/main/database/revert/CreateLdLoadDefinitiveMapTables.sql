-- Revert itaca-load:CreateLdLoadDefinitiveMap from oracle
-- XXX Add DDLs here.
DROP SEQUENCE LD_LOAD_DEFINITIVE_MAP_seq;
DROP TABLE LD_LOAD_DEFINITIVE_MAP CASCADE CONSTRAINTS PURGE;
