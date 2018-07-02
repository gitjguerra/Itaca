package com.csi.itaca.load.job;

import java.util.List;

// TODO: Only for test delete for production
public interface CustomerDao {
    public void insert(List<? extends Customer> customers);
    List<Customer> loadAllCustomers();
}