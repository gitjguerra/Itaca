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
                case 6:
                    data.setDataCol6(fieldSet.readString(i));
                    break;
                case 7:
                    data.setDataCol7(fieldSet.readString(i));
                    break;
                case 8:
                    data.setDataCol8(fieldSet.readString(i));
                    break;
                case 9:
                    data.setDataCol9(fieldSet.readString(i));
                    break;
                case 10:
                    data.setDataCol10(fieldSet.readString(i));
                    break;
                case 11:
                    data.setDataCol11(fieldSet.readString(i));
                    break;
                case 12:
                    data.setDataCol12(fieldSet.readString(i));
                    break;
                case 13:
                    data.setDataCol13(fieldSet.readString(i));
                    break;
                case 14:
                    data.setDataCol14(fieldSet.readString(i));
                    break;
                case 15:
                    data.setDataCol15(fieldSet.readString(i));
                    break;
                case 16:
                    data.setDataCol16(fieldSet.readString(i));
                    break;
                case 17:
                    data.setDataCol17(fieldSet.readString(i));
                    break;
                case 18:
                    data.setDataCol18(fieldSet.readString(i));
                    break;
                case 19:
                    data.setDataCol9(fieldSet.readString(i));
                    break;
                case 20:
                    data.setDataCol20(fieldSet.readString(i));
                    break;
            }
            logger.info("Campo " + i + ": " + fieldSet.readString(i));
        }
        return data;
    }
}