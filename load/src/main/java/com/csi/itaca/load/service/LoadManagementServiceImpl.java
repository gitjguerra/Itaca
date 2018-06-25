package com.csi.itaca.load.service;

import com.csi.itaca.load.model.dto.PreloadDataDTO;
import org.apache.log4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@SuppressWarnings("unchecked")
@Service
@EnableBatchProcessing
@Configuration
public class LoadManagementServiceImpl implements LoadManagementService {

    @Value("${batch.process.csvFile}")
    private String CSV_FILE;

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public DataSource dataSource;

    final static Logger logger = Logger.getLogger(LoadManagementServiceImpl.class);

    // begin reader, writer, and processor
    public FlatFileItemReader<PreloadDataDTO> csvPreloadReader() {
        FlatFileItemReader<PreloadDataDTO> reader = new FlatFileItemReader<PreloadDataDTO>();
        reader.setResource(new ClassPathResource(CSV_FILE));
        reader.setLineMapper(new DefaultLineMapper<PreloadDataDTO>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[] { "preloadDataId", "loadFileId", "loadedSuccessfully", "rowType", "lineNumber", "dataCol1", "dataCol2", "dataCol3" });
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<PreloadDataDTO>() {{
                setTargetType(PreloadDataDTO.class);
            }});
        }});
        return reader;
    }

    public ItemProcessor<PreloadDataDTO, PreloadDataDTO> csvPreloadProcessor() {
        return new PreloadProcessor();
    }

    public JdbcBatchItemWriter<PreloadDataDTO> csvPreloadWriter() {
        JdbcBatchItemWriter<PreloadDataDTO> csvPreloadWriter = new JdbcBatchItemWriter<PreloadDataDTO>();
        csvPreloadWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<PreloadDataDTO>());
        csvPreloadWriter.setSql("INSERT INTO LD_PRELOAD_DATA (preloadDataId, loadFileId, loadedSuccessfully, rowType, lineNumber, dataCol1, dataCol2, dataCol3) " +
                "VALUES (:preloadDataId, :loadFileId, :loadedSuccessfully, :rowType, :lineNumber, :dataCol1, :dataCol2, :dataCol3)");
        csvPreloadWriter.setDataSource(dataSource);
        return csvPreloadWriter;
    }
    // finish reader, writer, and processor

    @Override
    public Step csvFileToDatabaseStep() {
        return stepBuilderFactory.get("csvFileToDatabaseStep")
                .<PreloadDataDTO, PreloadDataDTO>chunk(1)
                .reader(csvPreloadReader())
                .processor(csvPreloadProcessor())
                .writer(csvPreloadWriter())
                .build();
    }

    @Override
    public Job csvFileToDatabaseJob(JobCompletionNotificationListener listener) {

        // TODO: Process preload
            // Preparation:
            //  1.1 Get a list of row types associated to this load

            // Process file:
            //  2.1. Set ld_load_file.preload_start_time to the current time.
            //  2.2. Set ld_load_file.status_code to 200 indicating preload in progress.
            //  2.3. Determine file format type from file extension and choose appropriate file parser (CSV, Excel, TXT)
            //  2.4. For each row in the file (loop):
            //          a) Insert new row in to ld_preload_data table with row loaded from the file.
            //          b) Determine row type. (find [found row type id])
            //              i. For each ld_preload_field_row_type found in preparation check identifier_column_no and identifier_value. When there is a match row type is found.
            //          c) Find all field definitions based on found row type found above:
            //              i. select * from ld_preload_field_definition where preload_row_type_id = [found row type id]
            //              ii. Validate field content: For each ld_preload_field_definition perform validation based on preload_field_type_id, regex & required.
            //              iii. Validate relation (file to database): For each rel_type = 2 or 3 look up
            //                  rel_db_table_name and rel_db_field_name. For example: Select * from
            //                  <<rel_db_table_name>> where <<rel_db_field_name>>=
            //                  <<field value from file>> AND ROWNUM = 1
            //          d) Check if the user has cancelled the load operation (ld_load_process.username_load_cancel).
            //  2.5. Global file validation
            //          a) Totals: To be considered
            //  2.6. Sets ld_load_file.status_code to 202 indicating successful preload without errors.
            //  2.7. Set ld_load_file.preload_end_time to the current time.

            // Alternate paths
            //      • 2.3) Upon failure to determine file indicate error in status_message in lnd_load_file table.
            //      • 2.3.a.ii) Upon validation failure create entry in lnd_error_fields table.
            //      • 2.4.c) Stop the process if the lnd_load_process.user_load_cancel is not null.
            //      • 2.6) Set status code to -2 if preload completed with errors.

        return jobBuilderFactory.get("csvFileToDatabaseJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(csvFileToDatabaseStep())
                .end()
                .build();
    }
}
