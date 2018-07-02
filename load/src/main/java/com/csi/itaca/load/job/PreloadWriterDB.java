package com.csi.itaca.load.job;

import com.csi.itaca.load.domain.DataIn;
import com.csi.itaca.load.domain.DataOut;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

@Component("writeJDBC")
@StepScope
public class PreloadWriterDB implements ItemWriter<DataOut> {

    @Override
    public void write(List<? extends DataOut> items) throws Exception {

    }
}