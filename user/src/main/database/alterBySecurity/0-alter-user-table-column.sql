----------------- Modified the user table -----------------
-- The column password are the varchar2(50) and is necessary change to varchar2(100)
ALTER TABLE ITACA.USR_USER MODIFY PASSWORD varchar2(100);
-- The column ROLE_DESC is necessary for JWT role control
ALTER TABLE ITACA.USR_USER ADD ROLE_DESC VARCHAR2(50);
----------------- Modified the user table  -----------------

