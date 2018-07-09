package com.csi.itaca.load.job;

import com.csi.itaca.load.model.dto.PreloadData;
import org.apache.log4j.Logger;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class PreloadFieldSetMapper implements FieldSetMapper<PreloadData> {

    private final static Logger logger = Logger.getLogger(PreloadFieldSetMapper.class);

    @Override
    public PreloadData mapFieldSet(FieldSet fieldSet) throws BindException {

        PreloadData data = new PreloadData();

        int nroRegistros = fieldSet.getFieldCount();

        for(int i = 0; i < nroRegistros; i++) {
            switch(i){
                case 1:
                    data.setDataCol1(fieldSet.readString(i));
                    break;
                case 2:
                    data.setDataCol2(fieldSet.readString(i));
                    break;
                case 3:
                    data.setDataCol3(fieldSet.readString(i));
                    break;
                case 4:
                    data.setDataCol4(fieldSet.readString(i));
                    break;
                case 5:
                    data.setDataCol5(fieldSet.readString(i));
                    break;
            }
            logger.info("Campo " + i + ": " + fieldSet.readString(i));
        }
        /*
        data.setPreloadDataId(fieldSet.readLong("id"));
        data.setLoadFileId(fieldSet.readLong("name"));
        data.setLoadedSuccessfully(fieldSet.readString("description"));
        data.setLineNumber(fieldSet.readLong("description"));
        data.setDataCol1(fieldSet.readString("description"));
        data.setDataCol2(fieldSet.readString("description"));
        data.setDataCol3(fieldSet.readString("description"));
        data.setDataCol4(fieldSet.readString("description"));
        data.setDataCol5(fieldSet.readString("description"));
        data.setDataCol4(fieldSet.readString("Filler"));
        */
        return data;
    }
}