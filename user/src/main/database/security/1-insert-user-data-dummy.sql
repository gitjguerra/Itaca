INSERT INTO ITACA.USR_USER
(USER_ID, USERNAME, PASSWORD, FIRST_NAME, LAST_NAME, COMPANY_CODE, DESCRIPTION, LANGUAGE_ID, COMPANY_START_DATE, COMPANY_END_DATE, EMAIL, BLOCKED, BLOCKED_DATE, ROLE_DESC)
VALUES(2, 'jperez', '$2a$04$I9Q2sDc4QGGg5WNTLmsz0.fvGv3OjoZyj81PrSFyGOqMphqfS2qKu', 'Jose', 'Perez', '01', '01', 1, TIMESTAMP '2018-06-11 00:00:00.000000', TIMESTAMP '2018-06-11 00:00:00.000000', 'jose.perez@gmail.com', 1, TIMESTAMP '2018-06-11 00:00:00.000000', 'ROLE_USER');
INSERT INTO ITACA.USR_USER
(USER_ID, USERNAME, PASSWORD, FIRST_NAME, LAST_NAME, COMPANY_CODE, DESCRIPTION, LANGUAGE_ID, COMPANY_START_DATE, COMPANY_END_DATE, EMAIL, BLOCKED, BLOCKED_DATE, ROLE_DESC)
VALUES(3, 'jdoe', '$2a$04$I9Q2sDc4QGGg5WNTLmsz0.fvGv3OjoZyj81PrSFyGOqMphqfS2qKu', 'Jhon', 'Doe', '01', '01', 2, NULL, NULL, 'jhon.doe@gmail.com', 1, NULL, 'ROLE_USER');
INSERT INTO ITACA.USR_USER
(USER_ID, USERNAME, PASSWORD, FIRST_NAME, LAST_NAME, COMPANY_CODE, DESCRIPTION, LANGUAGE_ID, COMPANY_START_DATE, COMPANY_END_DATE, EMAIL, BLOCKED, BLOCKED_DATE, ROLE_DESC)
VALUES(1, 'jguerra', '$2a$04$I9Q2sDc4QGGg5WNTLmsz0.fvGv3OjoZyj81PrSFyGOqMphqfS2qKu', 'Jose', 'Guerra', '01', '01', 1, NULL, NULL, 'cervecera.artesanal@gmail.com', 0, NULL, 'ROLE_ADMIN');


