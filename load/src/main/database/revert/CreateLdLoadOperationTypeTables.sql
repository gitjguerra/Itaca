-- Revert itaca-load:CreateLdLoadOperationType from oracle
-- XXX Add DDLs here.
DROP SEQUENCE LD_LOAD_OPERATION_TYPE_seq;
DROP TABLE LD_LOAD_OPERATION_TYPE CASCADE CONSTRAINTS PURGE;