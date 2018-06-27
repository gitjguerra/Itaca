package com.csi.itaca.load.service;

import com.csi.itaca.load.model.dto.PreloadDataDTO;
import com.csi.itaca.load.repository.LoadFileRepository;
import com.csi.itaca.load.utils.Constants;
import org.apache.log4j.Logger;
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
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

// TODO:  **** temporary change for datasource of itaca ****
// **** temporary change for datasource of itaca ****
import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

@SuppressWarnings("unchecked")
@Service
@EnableBatchProcessing
public class LoadManagementServiceImpl implements LoadManagementService {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    private LoadFileRepository loadFileRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public Constants constants;

    // TODO:  **** temporary change for datasource of itaca ****
    @Autowired
    public DataSource dataSource;

    private final static String csvFileType     = "csvFileToDatabaseStep";
    private final static String txtFileType     = "txtFileToDatabaseStep";
    private final static String excelFileType   = "excelFileToDatabaseStep";
    private final static String xmlFileType     = "xmlFileToDatabaseStep";
    private final static Logger logger = Logger.getLogger(LoadManagementServiceImpl.class);
    private String query = "";

    private void createBatchProcess(String name, long size, String checksum, java.sql.Date preload_star_time, java.sql.Date loadStartTime, String status_code, String status_message) {

        //TODO: agregar el id de la definicion
        Long preload_definition_id = 1L;

        String user = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        // Create a LD_LOAD_PROCESS
        query = "INSERT INTO ITACA.LD_LOAD_PROCESS (USER_ID, CREATED_TIMESTAMP, PRELOAD_DEFINITION_ID) VALUES(?, ?, ?);";
        // Armored with prepare statament to avoid hacker attacks
        jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, user);
                statement.setDate(2, preload_star_time);
                statement.setLong(3, preload_definition_id);
                return statement;
            }
        });

        // Create a LD_LOAD_FILE
        query = "INSERT INTO ITACA.LD_LOAD_FILE (FILENAME, FILE_SIZE, CHECKSUM, PRELOAD_START_TIME, LOAD_START_TIME, STATUS_CODE, STATUS_MESSAGE) VALUES(?, ?, ?, ?, ?, ?, ?);";
        // Armored with prepare statament to avoid hacker attacks
        jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, name);
                statement.setLong(2, size);
                statement.setString(3, checksum);
                statement.setDate(4, preload_star_time);
                statement.setDate(5, loadStartTime);
                statement.setString(6, status_code);
                statement.setString(7, status_message);
                return statement;
            }
        });
    }

    @Override
    public void store(MultipartFile file, Path rootLocation) {
        try {
            Files.copy(file.getInputStream(), rootLocation.resolve(file.getOriginalFilename()));

            Date date = new Date();
            long time = date.getTime();

            // create batch process
            createBatchProcess(file.getName(), file.getSize(), "", new java.sql.Date(date.getTime()), new java.sql.Date(date.getTime()), constants.getUploadingInProgress(), "Upload process");

        } catch (Exception e) {
            throw new RuntimeException("FAIL!");
        }
    }

    @Override
    public Resource loadFile(File file) {
        try {
            Resource resource = new UrlResource(file.toURI());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("FAIL!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("FAIL!");
        }
    }

    @Override
    public void deleteAll(Path rootLocation) {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void init(Path rootLocation) {
        try {
            Files.createDirectory(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage!");
        }
    }

    // begin reader, writer, and processor file
    public FlatFileItemReader<PreloadDataDTO> csvPreloadReader() {
        FlatFileItemReader<PreloadDataDTO> reader = new FlatFileItemReader<PreloadDataDTO>();
        reader.setResource(new ClassPathResource("itaca_example.csv"));
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
        // TODO:  **** temporary change for datasource of itaca ****
        // **** Change for Datasource of itaca ****
        csvPreloadWriter.setDataSource(dataSource);
        return csvPreloadWriter;
    }
    // finish reader, writer, and processor file

    @Override
    public HttpStatus fileToDatabaseJob(JobCompletionNotificationListener listener, File file) {

        // TODO: Process preload
            // Preparation:

                // ***** UPLOAD done previously *****

            //  1.1 Get a list of row types associated to this load
                    // Where take the data ????   example: load_process_id ???
        
                        //select ld_preload_row_type.* from ld_load_process,
                        //ld_preload_file, ld_preload_row_type
                        //WHERE ld_load_process.LOAD_PROCESS_ID = 1
                        //AND ld_load_process.preload_definition_id = ld_preload_file.preload_definition_id
                        //AND ld_preload_file.preload_file_id = ld_preload_row_type.PRELOAD_FILE_ID

            // Process file:
            //  2.1. Set ld_load_file.preload_start_time to the current time.  OK
            //  2.2. Set ld_load_file.status_code to 200 indicating preload in progress.  OK
            //  2.3. Determine file format type from file extension and choose appropriate file parser (CSV, Excel, TXT) Only csv, while.
                String fileType = getFileExtension(file);
        //  2.4. For each row in the file (loop):
            //          a) Insert new row in to ld_preload_data table with row loaded from the file.    ***** That is done for the csvPreloadWriter *****
            //          b) Determine row type. (find [found row type id])     ***** That is done with the filename ???? *****
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

        String typeJob = "";
        Step typeStep = null;
        switch(fileType){
            case "csv":
                typeJob = csvFileType;
                typeStep = csvFileToDatabaseStep();
                break;
            case "txt":
                typeJob = txtFileType;
                typeStep = txtFileToDatabaseStep();
                break;
            case "excel":
                typeJob = excelFileType;
                typeStep = excelFileToDatabaseStep();
                break;
            case "xml":
                typeJob = xmlFileType;
                typeStep = xmlFileToDatabaseStep();
                break;
            default:
                logger.error("*** unrecognized format ****");
                return HttpStatus.INTERNAL_SERVER_ERROR;
        }

        jobBuilderFactory.get(typeJob)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(typeStep)
                .end()
                .build();

        // Process post validation (Intra File & file to file):
                // ◦ Find all data rows where fields relate to other fields:
                // ▪ Select ld_preload_data.* from ld_preload_data where ld_preload_data.row_type_id in = (
                    // ◦ Get a list of row types associated to this load:
                    // ▪ select ld_preload_row_type.preload_row_type_id from ld_load_process, ld_preload_file, ld_preload_row_type where ld_load_process = [above load_process_id] ld_load_process.preload_definition_id = ld_preload_file. preload_definition_id & ld_preload_file.preload_file_id=ld_preload_row_type.preload_file)
                // ◦ For each data row find all field definitions that using Intra file or file to file relationship:
                    // ▪ Select ld_preload_field_definition.* from ld_preload_field_definition where preload_row_type_id = [row_type from data row] and (rel_type = 3 or rel_type = 1)
                        //▪ For each field definition find all data rows that is using the ID from rel_field_definition_id field definition:
                            //• Select ld_preload_data.* from ld_preload_data, ld_preload_field_definition where ld_preload_data.row_type = ld_preload_field_definition.preload_row_type_id & ld_preload_field_definition.preload_field_definition_id = << rel_field_definition_id>>
                            //• Load related definition(rel_field_definition_id):
                                // Select * from ld_preload_field_definition where preload_field_definition_id = <<rel_field_definition_id>>
        return HttpStatus.OK;
    }

    @Override
    public Step csvFileToDatabaseStep() {
        return stepBuilderFactory.get(csvFileType)     // Process for csv, for each file type there are one
                .<PreloadDataDTO, PreloadDataDTO>chunk(1)
                .reader(csvPreloadReader())                        // Line/Row of the file
                .processor(csvPreloadProcessor())                  // cvs processor for validate
                .writer(csvPreloadWriter())                        // write to db process
                .build();
    }

    // TODO: process others file types
    public Step txtFileToDatabaseStep() {
        return null;
    }
    @Override
    public Step excelFileToDatabaseStep() {
        return null;
    }
    @Override
    public Step xmlFileToDatabaseStep() {
        return null;
    }

    private static String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }
}
