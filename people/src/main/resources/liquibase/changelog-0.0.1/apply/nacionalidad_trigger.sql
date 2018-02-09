CREATE OR REPLACE TRIGGER PER_NACIONALIDAD_BI BEFORE
  INSERT ON PER_NACIONALIDAD FOR EACH ROW WHEN (NEW.ID_NACIONALIDAD IS NULL) BEGIN :NEW.ID_NACIONALIDAD := SEQ_NACIONALIDAD.NEXTVAL;
END;
/
