-- auto-generated definition
CREATE SEQUENCE SEQ_TYPE_ADDRESS
  MAXVALUE 9999999999
  NOCACHE
/

-- Start of DDL Script for Table SYSTEM.DIR_FORMAT_1
-- Generated 21-feb.-2018 16:55:28 from SYSTEM@XE

CREATE TABLE dir_format_1
(id_address                     NUMBER(19,0) NOT NULL,
 id_poblacion                   NUMBER(19,0) NOT NULL,
 id_cod_postal                  NUMBER(19,0) NOT NULL,
 id_type_via                    NUMBER(19,0) NOT NULL,
 nombre_via                     VARCHAR2(100 CHAR) NOT NULL,
 numero_via                     VARCHAR2(20 CHAR),
 complemento                    VARCHAR2(200 CHAR))
SEGMENT CREATION IMMEDIATE
PCTFREE     10
INITRANS    1
MAXTRANS    255
TABLESPACE  people
STORAGE   (
INITIAL     65536
NEXT        1048576
MINEXTENTS  1
MAXEXTENTS  2147483645
)
NOCACHE
MONITORING
NOPARALLEL
LOGGING
/
-- Constraints for DIR_FORMAT_1

ALTER TABLE dir_format_1
  ADD CONSTRAINT dir_format_1_pk PRIMARY KEY (id_address)
/


-- Comments for DIR_FORMAT_1

COMMENT ON TABLE dir_format_1 IS 'Format de ADDRESS 1 (Espana, Andorra)'
/
COMMENT ON COLUMN dir_format_1.complemento IS 'Descripcion complementaria'
/
COMMENT ON COLUMN dir_format_1.id_address IS 'Identificador de la ADDRESS'
/
COMMENT ON COLUMN dir_format_1.id_cod_postal IS 'Identificador del Codigo Postal'
/
COMMENT ON COLUMN dir_format_1.id_poblacion IS 'Identificador de la  poblacion'
/
COMMENT ON COLUMN dir_format_1.id_type_via IS 'Codigo de TYPE de via (1-Calle, 2 - Avenida ...)'
/
COMMENT ON COLUMN dir_format_1.nombre_via IS 'Nombre de la via'
/
COMMENT ON COLUMN dir_format_1.numero_via IS 'Numero de via'
/

-- End of DDL Script for Table SYSTEM.DIR_FORMAT_1

