-- Revert itaca-load:CreateLdLoadAtomicOperation from oracle
-- XXX Add DDLs here.
DROP SEQUENCE LD_LOAD_ATOMIC_OPERATION_seq;
DROP TABLE LD_LOAD_ATOMIC_OPERATION CASCADE CONSTRAINTS PURGE;
