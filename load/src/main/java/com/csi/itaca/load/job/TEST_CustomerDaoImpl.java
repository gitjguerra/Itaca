package com.csi.itaca.load.job;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

// TODO: Only for test delete for production
@Repository
public class TEST_CustomerDaoImpl extends JdbcDaoSupport implements TEST_CustomerDao {

    @Autowired
    DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    @Override
    public void insert(List<? extends TEST_Customer> Customers) {
        String sql = "INSERT INTO customer " + "(id, first_name, last_name) VALUES (?, ?, ?)";
        getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                TEST_Customer customer = Customers.get(i);
                ps.setLong(1, customer.getId());
                ps.setString(2, customer.getFirstName());
                ps.setString(3, customer.getLastName());
            }

            public int getBatchSize() {
                return Customers.size();
            }
        });

    }

    @Override
    public List<TEST_Customer> loadAllCustomers() {
        String sql = "SELECT * FROM customer";
        List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);

        List<TEST_Customer> result = new ArrayList<TEST_Customer>();
        for (Map<String, Object> row : rows) {
            TEST_Customer customer = new TEST_Customer();
            Long valor = Long.parseLong(row.get("id").toString());
            customer.setId(valor);
            customer.setFirstName((String) row.get("first_name"));
            customer.setLastName((String) row.get("last_name"));
            result.add(customer);
        }

        return result;
    }
}