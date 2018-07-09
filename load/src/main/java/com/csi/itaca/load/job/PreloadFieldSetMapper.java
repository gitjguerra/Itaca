package com.csi.itaca.load.job;

import com.csi.itaca.load.model.dto.PreloadData;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class PreloadFieldSetMapper implements FieldSetMapper<PreloadData> {
    @Override
    public PreloadData mapFieldSet(FieldSet fieldSet) throws BindException {
        PreloadData data = new PreloadData();

        data.setDataCol1(fieldSet.readString(0));
        data.setDataCol2(fieldSet.readString(1));
        data.setDataCol3(fieldSet.readString(2));
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