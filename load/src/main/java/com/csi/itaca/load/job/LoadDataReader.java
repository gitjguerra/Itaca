package com.csi.itaca.load.job;

import com.csi.itaca.load.model.dto.PreloadDataDTO;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@JobScope
public class LoadDataReader extends JdbcCursorItemReader<PreloadDataDTO> {

    // Colocar acá query de la tabla a leer
    @Autowired
    public LoadDataReader(DataSource dataSource) {
        this.setSql("select * from employees " +
                "left join dept_emp on employees.emp_no = dept_emp.emp_no " +
                "left join departments on dept_emp.dept_no = departments.dept_no");
        this.setRowMapper(new LoadRowMapper());
        this.setDataSource(dataSource);
    }

    // Mapear los datos a insertar en la otra tabla
    private static class LoadRowMapper implements RowMapper<PreloadDataDTO> {
        @Override
        public PreloadDataDTO mapRow(ResultSet resultSet, int i) throws SQLException {
            PreloadDataDTO preloadData = new PreloadDataDTO();
            /*
            employee.setEmpNo(resultSet.getInt("emp_no"));
            employee.setBirthDate(resultSet.getDate("birth_date"));
            employee.setFirstName(resultSet.getString("first_name"));
            employee.setLastName(resultSet.getString("last_name"));
            employee.setGender(Gender.valueOf(resultSet.getString("gender")));
            employee.setHireDate(resultSet.getDate("hire_date"));

            List<Department> departments = new ArrayList<>();
            Department department = new Department();
            department.setDeptNo(resultSet.getString("dept_no"));
            department.setName(resultSet.getString("dept_name"));
            departments.add(department);
            employee.setDepartments(departments);
            */

            return preloadData;
        }
    }
}