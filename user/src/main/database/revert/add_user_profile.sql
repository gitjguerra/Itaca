-- Revert itaca-user:add_user_profile from oracle

-- XXX Add DDLs here.
drop SEQUENCE  usr_profile_id_seq;
drop table usr_profile CASCADE CONSTRAINTS PURGE;