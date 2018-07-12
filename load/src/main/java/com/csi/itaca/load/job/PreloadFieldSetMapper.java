package com.csi.itaca.load.job;

import com.csi.itaca.load.model.dto.PreloadData;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class PreloadFieldSetMapper implements FieldSetMapper<PreloadData> {
    @Override
    public PreloadData mapFieldSet(FieldSet fieldSet) throws BindException {
        PreloadData customer = new PreloadData();

        customer.setPreloadDataId(fieldSet.readLong("id"));
        customer.setLoadedSuccessfully(fieldSet.readString("name"));
        customer.setDataCol1(fieldSet.readString("description"));

        return customer;
    }
}