-- Revert itaca-user:add_user_language from oracle

-- XXX Add DDLs here.
drop SEQUENCE  usr_language_id_seq;
drop table usr_language CASCADE CONSTRAINTS PURGE;