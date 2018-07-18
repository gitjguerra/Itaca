package com.csi.itaca.load.job;

import com.csi.itaca.load.model.dto.PreloadDataDTO;
import org.apache.log4j.Logger;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

public class PreloadFieldSetMapper implements FieldSetMapper<PreloadDataDTO> {

    private final static Logger logger = Logger.getLogger(PreloadFieldSetMapper.class);

    @Override
    public PreloadDataDTO mapFieldSet(FieldSet fieldSet) {

        int nroRegistros = fieldSet.getFieldCount();
        PreloadDataDTO data = new PreloadDataDTO();
        String[] values = fieldSet.getValues();

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
                case 20:
                    data.setDataCol21(values[i]);
                    break;
                case 21:
                    data.setDataCol22(values[i]);
                    break;
                case 22:
                    data.setDataCol23(values[i]);
                    break;
                case 23:
                    data.setDataCol24(values[i]);
                    break;
                case 24:
                    data.setDataCol25(values[i]);
                    break;
                case 25:
                    data.setDataCol26(values[i]);
                    break;
                case 26:
                    data.setDataCol27(values[i]);
                    break;
                case 27:
                    data.setDataCol28(values[i]);
                    break;
                case 28:
                    data.setDataCol29(values[i]);
                    break;
                case 29:
                    data.setDataCol30(values[i]);
                    break;
                case 30:
                    data.setDataCol31(values[i]);
                    break;
                case 31:
                    data.setDataCol32(values[i]);
                    break;
                case 32:
                    data.setDataCol33(values[i]);
                    break;
                case 33:
                    data.setDataCol34(values[i]);
                    break;
                case 34:
                    data.setDataCol35(values[i]);
                    break;
                case 35:
                    data.setDataCol36(values[i]);
                    break;
                case 36:
                    data.setDataCol37(values[i]);
                    break;
                case 37:
                    data.setDataCol38(values[i]);
                    break;
                case 38:
                    data.setDataCol39(values[i]);
                    break;
                case 39:
                    data.setDataCol40(values[i]);
                    break;
                case 40:
                    data.setDataCol41(values[i]);
                    break;
                case 41:
                    data.setDataCol42(values[i]);
                    break;
                case 42:
                    data.setDataCol43(values[i]);
                    break;
                case 43:
                    data.setDataCol44(values[i]);
                    break;
                case 44:
                    data.setDataCol45(values[i]);
                    break;
                case 45:
                    data.setDataCol46(values[i]);
                    break;
                case 46:
                    data.setDataCol47(values[i]);
                    break;
                case 47:
                    data.setDataCol48(values[i]);
                    break;
                case 48:
                    data.setDataCol49(values[i]);
                    break;
                case 49:
                    data.setDataCol50(values[i]);
                    break;
            }
            logger.info("Campo " + (i+1) + ": " + fieldSet.readString(i));
        }
        return data;
    }
}