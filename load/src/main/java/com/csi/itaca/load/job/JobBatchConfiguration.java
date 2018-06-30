package com.csi.itaca.load.job;


import com.csi.itaca.load.domain.DataIn;
import com.csi.itaca.load.domain.DataOut;
import com.csi.itaca.load.model.dao.PreloadDefinitionEntity;
import com.csi.itaca.load.model.dao.PreloadFileEntity;
import com.csi.itaca.load.repository.PreloadDefinitionRepository;
import com.csi.itaca.load.repository.PreloadFileRepository;
import com.csi.itaca.load.service.CustomRowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileFooterCallback;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.io.Writer;

@Configuration
@EnableBatchProcessing
public class JobBatchConfiguration {

    private static final Logger log = LoggerFactory.getLogger(JobBatchConfiguration.class);
    public static final int CHUNK_SIZE = 100;

    @Autowired
    private PreloadFileRepository preloadFileRepository;

    @Autowired
    private PreloadDefinitionRepository preloadDefinitionRepository;


    @Bean
    public ItemReader<DataIn> preloadDefinitionReader(DataSource dataSource) {
        JdbcCursorItemReader reader = new JdbcCursorItemReader();
        reader.setDataSource(dataSource);
        reader.setRowMapper(new CustomRowMapper());
        reader.setSql("SELECT name, description FROM LD_PRELOAD_FIELD_DEFINITION");
        return reader;
    }

    @Bean
    public ItemReader<DataIn> reader(DataSource dataSource) {
        JdbcCursorItemReader reader = new JdbcCursorItemReader();
        reader.setDataSource(dataSource);
        reader.setRowMapper(new CustomRowMapper());
        reader.setSql("SELECT name, description FROM LD_PRELOAD_FIELD_DEFINITION");
        return reader;
    }

    @Bean
    public ItemProcessor<DataIn, DataOut> processor() {
        return new ItemProcessor<DataIn, DataOut>() {
            @Override
            public DataOut process(DataIn dataIn) throws Exception {
                DataOut dataOut = new DataOut();
                dataOut.setNAME("CAMBIO1");
                dataOut.setDESCRIPTION("CAMBIO2");
                return dataOut;
            }
        };
    }

    @Bean
    public ItemWriter<DataOut> writer() {

        FlatFileItemWriter<DataOut> writer = new FlatFileItemWriter<>();

        FileSystemResource fileSystemResource = new FileSystemResource(new File("C:\\temp"));
        writer.setResource(new ClassPathResource("output.txt"));
        //writer.setResource(fileSystemResource);

        DelimitedLineAggregator<DataOut> delLineAgg = new DelimitedLineAggregator<DataOut>();
        delLineAgg.setDelimiter(",");

        BeanWrapperFieldExtractor<DataOut> fieldExtractor = new BeanWrapperFieldExtractor<>();
        fieldExtractor.setNames(new String[] {"text1-cambio1","text2-cambio1"});
        delLineAgg.setFieldExtractor(fieldExtractor);
        writer.setLineAggregator(delLineAgg);

        writer.setHeaderCallback(new FlatFileHeaderCallback() {
            @Override
            public void writeHeader(Writer writer) throws IOException {
                writer.write("HEADER");
            }
        });


        writer.setFooterCallback(new FlatFileFooterCallback() {
            @Override
            public void writeFooter(Writer writer) throws IOException {
                writer.write("FOOTER");
            }
        });


        return writer;

    }


    @Bean
    public Job sqlExecuteJob(JobBuilderFactory jobs, @Qualifier("preloadDefinitionStep") Step step_preload_def, @Qualifier("preloadFileStep") Step step_preload_file, @Qualifier("preloadRowTypeStep") Step step_preload_row_type, @Qualifier("preloadFieldDefStep") Step step_preload_field_def, JobExecutionListener listener) {

        //  <editor-fold defaultstate="collapsed" desc=" - STEP 1 - *** Create a LD_PRELOAD_DEFINITION ***">
        /*

        // ***** read filename and insert into db (LD_PRELOAD_DEFINITION) ****

        PreloadDefinitionEntity DefinitionEntity = new PreloadDefinitionEntity();
        DefinitionEntity.setName("C:\\temp\\output.txt");
        DefinitionEntity.setDescription("Preload File");
        DefinitionEntity.setMaxPreloadLowErrors(1L);
        DefinitionEntity.setMaxPreloadMediumErrors(1L);
        DefinitionEntity.setMaxPreloadHighErrors(1L);
        DefinitionEntity.setLoadIfPreloadOk("LOAD_IF_PRELOAD_OK");
        DefinitionEntity.setAutoLoadDirectory("AUTO_LOAD_DIRECTORY");
        DefinitionEntity.setAutoCronScheduling("AUTO_CRON_SCHEDULING");
        preloadDefinitionRepository.save(DefinitionEntity);
        final Long preload_Definition_id = findInsertId("LD_PRELOAD_FILE", "PRELOAD_FILE_ID", "name", file.getName());
        */
        /*
        query = "INSERT INTO LD_PRELOAD_DEFINITION (NAME, DESCRIPTION, MAX_PRELOAD_LOW_ERRORS, MAX_PRELOAD_MEDIUM_ERRORS, MAX_PRELOAD_HIGH_ERRORS, LOAD_IF_PRELOAD_OK, AUTO_LOAD_DIRECTORY, AUTO_CRON_SCHEDULING) values (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, file.getName());
                statement.setString(2, "DEFINITION FILE");
                statement.setLong(3, 1);
                statement.setLong(4, 1);
                statement.setLong(5, 1);
                statement.setString(6, "LOAD_IF_PRELOAD_OK");
                statement.setString(7, "AUTO_LOAD_DIRECTORY");
                statement.setString(8, "AUTO_CRON_SCHEDULING");
                return statement;
            }
        });
        // Identificador del registro insertado en preload_file
        final Long preload_definition_id = findInsertId("LD_PRELOAD_DEFINITION", "PRELOAD_DEFINITION_ID", "name", file.getName());
        */
        //  </editor-fold>

        //  <editor-fold defaultstate="collapsed" desc=" - STEP 2 - *** Create a LD_PRELOAD_FILE ***">

        // ***** read filename and insert into db (LD_PRELOAD_FILE) ****

        /*
        //update the entered fields
        PreloadFileEntity fileEntity = new PreloadFileEntity();
        fileEntity.setPreloadDefinitionId(preload_definition_id);
        fileEntity.setName(file.getName());
        fileEntity.setDescription("Preload File");
        preloadFileRepository.save(fileEntity);

        // Use With Transaction
        //entityManager.flush();
        //entityManager.clear();

            /*
            query = "INSERT INTO LD_PRELOAD_FILE (PRELOAD_DEFINITION_ID, NAME, DESCRIPTION) VALUES(?, ?, ?)";
            jdbcTemplate.update(new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement statement = connection.prepareStatement(query);
                        statement.setLong(1, 19L);
                        statement.setString(2, file.getName());
                        statement.setString(3, "PRELOAD FILE");
                        return statement;
                    }
                });
            */
        // Identificador del registro insertado en preload_file
        //final Long preload_file_id = findInsertId("LD_PRELOAD_FILE", "PRELOAD_FILE_ID", "name", file.getName());
        //  </editor-fold>

        //  <editor-fold defaultstate="collapsed" desc=" - STEP 3 - *** Create a LD_PRELOAD_ROW_TYPE ***">
        /*

        // ***** read filename and insert into db (LD_PRELOAD_ROW_TYPE) ****

        query = "INSERT INTO LD_PRELOAD_ROW_TYPE (PRELOAD_FILE_ID, NAME, DESCRIPTION, IDENTIFIER_COLUMN_NO, IDENTIFIER_VALUE) VALUES(?, ?, ?, ?, ?)";
        jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setLong(1, preload_file_id);
                statement.setString(2, file.getName());
                statement.setString(3, "PRELOAD FILE");
                statement.setLong(4, 1L);
                statement.setString(5, "IDENTIFIER_VALUE");
                return statement;
            }
        });

        // Identificador del registro insertado en preload_row_type
        final Long preload_row_type_id = findInsertId("LD_PRELOAD_ROW_TYPE", "PRELOAD_ROW_TYPE_ID", "name", file.getName());
        */
        //  </editor-fold>

        //  <editor-fold defaultstate="collapsed" desc=" - STEP 4 - *** Create a LD_PRELOAD_FIELD_DEFINITION ***">
                /*

                // ********** Read the file fields, validate and insert into db (LD_PRELOAD_FIELD_DEFINITION) ***********

                PreloadFieldDefinitionEntity preloadFieldDefinitionEntity = new PreloadFieldDefinitionEntity();
                preloadFieldDefinitionEntity.setPreloadFieldTypeId();
                preloadFieldDefinitionEntity.setPreloadRowTypeId(preload_row_type_id);
                preloadFieldDefinitionEntity.setName();
                preloadFieldDefinitionEntity.setDescription();
                preloadFieldDefinitionEntity.setRegex();
                preloadFieldDefinitionEntity.setRelType();
                preloadFieldDefinitionEntity.setRelDbTableName();
                preloadFieldDefinitionEntity.setRelFieldDefinitionId();
                preloadFieldDefinitionEntity.setRelDbFieldName();
                preloadFieldDefinitionEntity.setColumnNo();
                preloadFieldDefinitionEntity.setRequired();
                preloadFieldDefinitionEntity.setErrorSeverity();
                fieldDefinitionRepository.save(preloadFieldDefinitionEntity);

                query = "INSERT INTO LD_PRELOAD_FIELD_DEFINITION (PRELOAD_ROW_TYPE_ID, COLUMN_NO, NAME, DESCRIPTION, PRELOAD_FIELD_TYPE_ID, REGEX, REQUIRED, REL_TYPE, REL_FIELD_DEFINITION_ID, REL_DB_TABLE_NAME, REL_DB_FIELD_NAME, ERROR_SEVERITY) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                jdbcTemplate.update(new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement statement = connection.prepareStatement(query);
                        statement.setLong(1, preload_row_type_id);
                        statement.setLong(2, 1L);
                        statement.setString(3, file.getName());
                        statement.setString(4, "PRELOAD FIELD DEFINITION");
                        statement.setString(5, "PRELOAD FIELD TYPE");
                        // find file Type
                        Long fileTypeId = findFileType(fileType);
                        statement.setLong(6, fileTypeId);
                        statement.setString(7, "REGEX");
                        statement.setString(8, "REQUIRED");
                        statement.setLong(9, 1L);

                        // find file definition   ***  How take a relation ****         ?????
                        //Long relFileDefinitionId = findRelFileDefinition(fileType);
                        statement.setLong(10, 1L);

                        statement.setString(11, "REL_DB_TABLE_NAME");
                        statement.setString(12, "REL_DB_FIELD_NAME");
                        return statement;
                    }
                });
                */
        //  </editor-fold>


        Job job = jobs.get("job1")
                .listener(listener)
                .flow(step_preload_def)
                .next(step_preload_file)
                .next(step_preload_row_type)
                .next(step_preload_field_def)
                .end()
                .build();

        return job;


    }

    @Qualifier("preloadDefinitionStep")
    @Bean
    public Step preloadDefinitionStep(StepBuilderFactory stepBuilderFactory, ItemReader<DataIn> reader,
                      ItemWriter<DataOut> writer, ItemProcessor<DataIn, DataOut> processor) {

        return stepBuilderFactory.get("preloadDefinitionStep")
                .<DataIn, DataOut> chunk(100)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Qualifier("preloadFileStep")
    @Bean
    public Step preloadFileStep(StepBuilderFactory stepBuilderFactory, ItemReader<DataIn> reader,
                      ItemWriter<DataOut> writer, ItemProcessor<DataIn, DataOut> processor) {

        return stepBuilderFactory.get("preloadFileStep")
                .<DataIn, DataOut> chunk(100)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Qualifier("preloadRowTypeStep")
    @Bean
    public Step preloadRowTypeStep(StepBuilderFactory stepBuilderFactory, ItemReader<DataIn> reader,
                      ItemWriter<DataOut> writer, ItemProcessor<DataIn, DataOut> processor) {

        return stepBuilderFactory.get("preloadRowTypeStep")
                .<DataIn, DataOut> chunk(100)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Qualifier("preloadFieldDefStep")
    @Bean
    public Step preloadFieldDefStep(StepBuilderFactory stepBuilderFactory, ItemReader<DataIn> reader,
                      ItemWriter<DataOut> writer, ItemProcessor<DataIn, DataOut> processor) {

        return stepBuilderFactory.get("preloadFieldDefStep")
                .<DataIn, DataOut> chunk(100)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }


}
