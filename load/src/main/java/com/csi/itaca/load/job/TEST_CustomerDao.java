package com.csi.itaca.load.job;

import java.util.List;

// TODO: Only for test delete for production
public interface TEST_CustomerDao {
    public void insert(List<? extends TEST_Customer> customers);
    List<TEST_Customer> loadAllCustomers();
}