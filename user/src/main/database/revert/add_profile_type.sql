-- Revert itaca-user:profile_type from oracle

-- XXX Add DDLs here.
drop SEQUENCE  usr_profile_type_id_seq;
drop table usr_profile_type CASCADE CONSTRAINTS PURGE;