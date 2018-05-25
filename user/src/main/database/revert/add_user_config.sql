-- Revert itaca-user:add_user_config from oracle

-- XXX Add DDLs here.
drop SEQUENCE  usr_user_config_id_seq;
drop table usr_user_config CASCADE CONSTRAINTS PURGE;
