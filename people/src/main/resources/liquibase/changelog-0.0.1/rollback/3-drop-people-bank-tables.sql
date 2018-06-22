-- DROP BANK ****************************************************************
DROP INDEX PER_BANK_ACCOUNT_ID1 FORCE;
DROP INDEX PER_BANK_ACCOUNT_ID2 FORCE;
DROP INDEX PER_BANK_ACCOUNT_ID3 FORCE;
DROP INDEX PER_BANK_ACCOUNT_ID4 FORCE;

DROP SEQUENCE SEQ_BANK;
DROP SEQUENCE SEQ_CLASIFICATION_ACCOUNT;
DROP SEQUENCE SEQ_BANK_ACCOUNT;
DROP SEQUENCE SEQ_TYPE_ACCOUNT;
DROP SEQUENCE SEQ_BANK_CARD;
DROP SEQUENCE SEQ_CARD_TYPE;

DROP TABLE PER_BANK CASCADE CONSTRAINTS PURGE;
DROP TABLE PER_CLASIFICATION_ACCOUNT CASCADE CONSTRAINTS PURGE;
DROP TABLE PER_BANK_ACCOUNT CASCADE CONSTRAINTS PURGE;
DROP TABLE PER_TYPE_ACCOUNT CASCADE CONSTRAINTS PURGE;
DROP TABLE PER_BANK_CARD CASCADE CONSTRAINTS PURGE;
DROP TABLE PER_CARD_TYPE CASCADE CONSTRAINTS PURGE;






-- DROP BANK END ****************************************************************
COMMIT;