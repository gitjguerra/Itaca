package com.csi.itaca.preload.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.csi.itaca.preload.model.dto.PreloadDataDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Date startTime, stopTime;

    @Override
    public void beforeJob(JobExecution jobExecution) {
        startTime = new Date();
        System.out.println("Job starts at :"+startTime);
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("============ JOB FINISHED ============ Verifying the results....\n");

            List<PreloadDataDTO> results = jdbcTemplate.query("SELECT PRELOAD_DATA_ID, LOAD_FILE_ID, LOADED_SUCCESSFULLY, ROW_TYPE, LINE_NUMBER, DATA_COL1, DATA_COL2, DATA_COL3 FROM ITACA.LD_PRELOAD_DATA;", new RowMapper<PreloadDataDTO>() {
                @Override
                public PreloadDataDTO mapRow(ResultSet rs, int row) throws SQLException {
                    return new PreloadDataDTO(rs.getLong(1), rs.getLong(2), rs.getString(3),
                            rs.getLong(4), rs.getLong(5), rs.getString(6),
                            rs.getString(7), rs.getString(8));
                }
            });

            for (PreloadDataDTO PreloadDataDTO : results) {
                log.info("Discovered <" + PreloadDataDTO + "> in the database.");
            }

            stopTime = new Date();
            System.out.println("Job stops at :"+stopTime);
        }
    }

}