package com.csi.itaca.load.job;

import com.csi.itaca.load.model.dto.PreloadDataDTO;
import org.apache.log4j.Logger;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import java.util.Random;

public class PreloadFieldSetMapper implements FieldSetMapper<PreloadDataDTO> {

    private final static Logger logger = Logger.getLogger(PreloadFieldSetMapper.class);

    @Override
    public PreloadDataDTO mapFieldSet(FieldSet fieldSet) throws BindException {

        int nroRegistros = fieldSet.getFieldCount();

        // Put the principal data
        PreloadDataDTO data = new PreloadDataDTO();

        String[] names = fieldSet.getNames();
        String[] values = fieldSet.getValues();

        // TODO: DELETE HARDCODE random
        Random ramdom = new Random();
        data.setPreloadDataId(ramdom.nextLong());

        for(int i = 0; i < nroRegistros; i++) {
            switch(i){
                case 0:
                    data.setDataCol1(values[i]);
                    break;
                case 1:
                    data.setDataCol2(values[i]);
                    break;
                case 2:
                    data.setDataCol3(values[i]);
                    break;
                case 3:
                    data.setDataCol4(values[i]);
                    break;
                case 4:
                    data.setDataCol5(values[i]);
                    break;
                case 5:
                    data.setDataCol6(values[i]);
                    break;
                case 6:
                    data.setDataCol7(values[i]);
                    break;
                case 7:
                    data.setDataCol8(values[i]);
                    break;
                case 8:
                    data.setDataCol9(values[i]);
                    break;
                case 9:
                    data.setDataCol10(values[i]);
                    break;
                case 10:
                    data.setDataCol11(values[i]);
                    break;
                case 11:
                    data.setDataCol12(values[i]);
                    break;
                case 12:
                    data.setDataCol13(values[i]);
                    break;
                case 13:
                    data.setDataCol14(values[i]);
                    break;
                case 14:
                    data.setDataCol15(values[i]);
                    break;
                case 15:
                    data.setDataCol16(values[i]);
                    break;
                case 16:
                    data.setDataCol17(values[i]);
                    break;
                case 17:
                    data.setDataCol18(values[i]);
                    break;
                case 18:
                    data.setDataCol19(values[i]);
                    break;
                case 19:
                    data.setDataCol20(values[i]);
                    break;
            }
            logger.info("Campo " + (i+1) + ": " + fieldSet.readString(i));
        }
        return data;
    }
}