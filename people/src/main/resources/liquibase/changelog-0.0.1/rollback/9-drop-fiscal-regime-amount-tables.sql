DROP INDEX fis_fiscal_regime_amounts_id1 FORCE;
DROP INDEX fis_fiscal_regime_amounts_fk1 FORCE;

DROP TABLE  fis_fiscal_regime CASCADE CONSTRAINTS PURGE;
DROP TABLE fis_fiscal_regime_amounts CASCADE CONSTRAINTS PURGE;


DROP SEQUENCE sfis_fiscal_regime;
DROP SEQUENCE sfis_fiscal_regime;

COMMIT;