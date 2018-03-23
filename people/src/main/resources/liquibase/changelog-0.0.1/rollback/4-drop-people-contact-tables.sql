-- DROP INDEX CONTACT****************************************************************

DROP INDEX PER_CONTACT_ID1 FORCE;
DROP INDEX PER_CONTACT_ID2 FORCE;
DROP INDEX PER_CONTACT_ID3 FORCE;
DROP INDEX PER_CONTACT_ID4 FORCE;

-- DROP TABLE CONTACT END ****************************************************************

DROP TABLE PER_CONTACT CASCADE CONSTRAINTS PURGE;
DROP TABLE DIR_ADDRESS CASCADE CONSTRAINTS PURGE;
-- DROP CONTACT END ****************************************************************




COMMIT;
