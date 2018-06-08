-- Revert itaca-common:CreateCountryTable from oracle

DROP SEQUENCE dir_country_seq;
DROP TABLE dir_country CASCADE CONSTRAINTS PURGE;