package com.csi.itaca.load.job;

import java.util.List;

import org.springframework.batch.item.ItemWriter;


// TODO: Change for data objects ITACA
public class PreloadWriter implements ItemWriter<Customer> {

    private final CustomerDao customerDao;

    public PreloadWriter(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Override
    public void write(List<? extends Customer> customers) throws Exception {
        customerDao.insert(customers);
    }
}