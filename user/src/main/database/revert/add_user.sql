-- Revert itaca-user:add_user from oracle

-- XXX Add DDLs here.
drop SEQUENCE  usr_user_id_seq;
drop table usr_user CASCADE CONSTRAINTS PURGE;