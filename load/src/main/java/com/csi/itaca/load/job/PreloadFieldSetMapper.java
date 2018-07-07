package com.csi.itaca.load.job;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class PreloadFieldSetMapper implements FieldSetMapper<Customer> {
    @Override
    public Customer mapFieldSet(FieldSet fieldSet) throws BindException {
        Customer customer = new Customer();

        customer.setId(fieldSet.readLong("id"));
        customer.setFirstName(fieldSet.readString("name"));
        customer.setLastName(fieldSet.readString("description"));

        return customer;
    }
}