-- Revert itaca-load:CreateLdLoadDefinitiveField from oracle
-- XXX Add DDLs here.
DROP SEQUENCE LD_LOAD_DEFINITIVE_FIELD_seq;
DROP TABLE LD_LOAD_DEFINITIVE_FIELD CASCADE CONSTRAINTS PURGE;
