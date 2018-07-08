package com.csi.itaca.load.service;

import com.csi.itaca.load.job.JobCompletionNotificationListener;
import com.csi.itaca.load.model.dto.PreloadRowTypeDTO;
import com.csi.itaca.load.repository.PreloadFieldDefinitionRepository;
import com.csi.itaca.load.repository.PreloadFileRepository;
import com.csi.itaca.load.utils.Constants;
import org.apache.log4j.Logger;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.io.DigestInputStream;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@SuppressWarnings("unchecked")
@Service
public class LoadManagementServiceImpl implements LoadManagementService {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job sqlExecuteJob;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PreloadFileRepository preloadFileRepository;

    @Autowired
    private PreloadFieldDefinitionRepository fieldDefinitionRepository;

    @Autowired
    public Constants constants;

    @Autowired
    public DataSource dataSource;

    // TODO: Revisar para la carga dinamica segun el tipo de archivo
    private final static String csvFileType     = "csvFileToDatabaseStep";
    private final static String txtFileType     = "txtFileToDatabaseStep";
    private final static String excelFileType   = "excelFileToDatabaseStep";
    private final static String xmlFileType     = "xmlFileToDatabaseStep";

    // Global variables required
    private String query = "";
    private final static Logger logger = Logger.getLogger(LoadManagementServiceImpl.class);

    // Job execute
    @Override
    public HttpStatus fileToDatabaseJob(JobCompletionNotificationListener listener, Path rootLocation, File file) {

        // Database connection
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        // Active user
        String user = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = findUserId(user);
        // where expression for find id tables
        String where = "";
        // DateTime to execute job
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        //  2.1. Set ld_load_file.preload_start_time to the current time.
        java.sql.Date preload_star_time = new java.sql.Date(date.getTime());
        //  2.2. Set ld_load_file.status_code to 200 indicating preload in progress.
        String statusCode = HttpStatus.OK.toString();
        String statusMessage = HttpStatus.OK.name();
        //  2.3. Determine file format type from file extension and choose appropriate file parser (CSV, Excel, TXT).
        String fileExtension = getFileExtension(file);

        //Launch the Batch Job
        try {

            // ********************************* INITIAL PROCESS (PRELOAD) *********************************
            // Who init the process ?
            // How init the process ?
            // When init the process ?
                // ID_PRELOAD_DEFINITION PROCESS
                // ID_PRELOAD_FILE PROCESS
                // ID_PRELOAD_ROW_TYPE PROCESS
                // ID_PRELOAD_FILE_TYPE PROCESS
                // ID_PRELOAD_FIELD_DEFINITION PROCESS
            // ********************************* INITIAL PROCESS *********************************

        // TODO: Ask to architect for validate the process
        //  <editor-fold defaultstate="collapsed" desc="*** 1) Prerequisite(s) ***">
                // 1. load_process_id
                //1.1. A record created in the ld_load_process table where load_process_id is associated.
                query = "INSERT INTO LD_LOAD_PROCESS (LOAD_PROCESS_ID, USER_ID, CREATED_TIMESTAMP, PRELOAD_DEFINITION_ID) VALUES(?, ?, ?, ?)";
                jdbcTemplate.update(new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement statement = connection.prepareStatement(query);
                        // TODO:  Delete hardcode
                        statement.setLong(1, 2);
                        statement.setLong(2, userId);
                        statement.setDate(3, preload_star_time);
                        // TODO:  Delete hardcode
                        statement.setLong(4, 1);
                        return statement;
                    }
                });
                // Id insert on LD_LOAD_PROCESS
                // TODO:  Delete hardcode
                where = " PRELOAD_DEFINITION_ID=" + 1 + " AND LOAD_PROCESS_ID=" + 2;
                final Long idLoadProcess = findInsertId("LD_LOAD_PROCESS", "LOAD_PROCESS_ID", where);

                // TODO: Ask the architect -   Is checksun valid ???
                // Checksum
                /*
                MessageDigest md = MessageDigest.getInstance("MD5");
                InputStream is = Files.newInputStream(Paths.get(file.getPath()));
                DigestInputStream dis = new DigestInputStream(is, (Digest) md);
                byte[] digest = md.digest();
                */

            // 2. load_file_id
                //2.1. A record created in the ld_load_file table with same load_file_id and the same above load_process_id.
                query = "INSERT INTO LD_LOAD_FILE (LOAD_FILE_ID, LOAD_PROCESS_ID, FILENAME, FILE_SIZE, CHECKSUM, PRELOAD_START_TIME, LOAD_START_TIME, STATUS_CODE, STATUS_MESSAGE) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
                jdbcTemplate.update(new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement statement = connection.prepareStatement(query);
                        // TODO: delete hardcode
                        statement.setLong(1, 1);
                        // TODO: delete hardcode
                        statement.setLong(2, 2);
                        statement.setString(3, file.getName());
                        statement.setLong(4, file.length());
                        // TODO: delete hardcode
                        statement.setString(5, "Checksum");
                        statement.setDate(6, preload_star_time);
                        // TODO: delete hardcode
                        statement.setDate(7, null);
                        statement.setString(8, statusCode);
                        statement.setString(9, statusMessage);
                        return statement;
                    }
                });
                // id insert on LD_LOAD_FILE
                where = " filename='" + file.getName() + "' AND LOAD_PROCESS_ID=" + idLoadProcess;
                final Long idLoadFile = findInsertId("LD_LOAD_FILE", "LOAD_FILE_ID", where);
        //  </editor-fold>

        //  <editor-fold defaultstate="collapsed" desc="*** 2) Preparation file ***">
            // 1.1. Get a list of row types associated to this load:
            // Database connection
            List<PreloadRowTypeDTO> rows = jdbcTemplate.query("select ld_preload_row_type.* from ld_load_process, ld_preload_file, ld_preload_row_type " +
                    "WHERE ld_load_process.LOAD_PROCESS_ID = " + idLoadProcess + " " +
                    "AND ld_load_process.preload_definition_id = ld_preload_file.preload_definition_id " +
                    "AND ld_preload_file.preload_file_id = ld_preload_row_type.preload_file_id", new BeanPropertyRowMapper(PreloadRowTypeDTO.class));
        //  </editor-fold>

            JobExecution jobExecution = jobLauncher.run(sqlExecuteJob, new JobParametersBuilder()
                        .addString("fullPathFileName", file.getAbsolutePath())
                        .addLong("id_load_process", idLoadProcess)
                        .addLong("id_load_file", idLoadFile)
                        .addString("rows", String.valueOf(rows))
                        .addLong("time", System.currentTimeMillis()).toJobParameters());  // Se agrega para ejecutar multiples hilos

            } catch (JobExecutionAlreadyRunningException e) {
                e.printStackTrace();
            } catch (JobRestartException e) {
                e.printStackTrace();
            } catch (JobInstanceAlreadyCompleteException e) {
                e.printStackTrace();
            } catch (JobParametersInvalidException e) {
                e.printStackTrace();
            /*
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                */
            }
        // If all Ok return value
        return HttpStatus.OK;
    }

        // TODO: Review process with architect
        /*
        try {
            //  <editor-fold defaultstate="collapsed" desc="*** Create a LD_PRELOAD_ROW_TYPE ***">
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
            //  </editor-fold>

            //  <editor-fold defaultstate="collapsed" desc="*** Create a LD_PRELOAD_FIELD_DEFINITION ***">
                    /*
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

        /*
            String typeJob = "";
            Step typeStep = null;
            switch (fileType) {
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
                    return exito;
            }

            jobBuilderFactory.get(typeJob)
                    .incrementer(new RunIdIncrementer())
                    .listener(listener)
                    .flow(typeStep)
                    .end()
                    .build();
        /*
        } catch (Exception e) {
            logger.error("fail");
            return exito;
        }
        */


    private static String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }

    // find File_Type_Id
    private Long findFileType(String fileType) {
        Long id = 0L;
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            String sql = "SELECT PRELOAD_FIELD_TYPE_ID FROM LD_PRELOAD_FIELD_TYPE WHERE name ='"+ fileType + "'";
            id = jdbcTemplate.queryForObject(sql, Long.class);
        }
        catch (EmptyResultDataAccessException e) {
            if(logger.isDebugEnabled()){
                logger.debug(e);
            }
            return 0L;
        }
        return id;
    }

    // it's valid ?
    public boolean validateFileExt(File file){
        String fileType = getFileExtension((File) file);
        Long id = findFileType(fileType);
        if(id == 0){
            return false;
        }else{
            return true;
        }
    }

    public Long findInsertId(String tableName, String fieldIdName, String whereValue){
        Long id = 0L;
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            String sql = "SELECT " + fieldIdName + " FROM " + tableName + " WHERE " + whereValue;
            id = jdbcTemplate.queryForObject(sql, Long.class);
        }
        catch (EmptyResultDataAccessException e) {
            if(logger.isDebugEnabled()){
                logger.debug(e);
            }
            return 0L;
        }
        return id;
    }

    public Long findUserId(String user){
        Long id = 0L;
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            String sql = "SELECT USER_ID FROM USR_USER WHERE USERNAME = '" + user + "'";
            id = jdbcTemplate.queryForObject(sql, Long.class);
        }
        catch (EmptyResultDataAccessException e) {
            if(logger.isDebugEnabled()){
                logger.debug(e);
            }
            return 0L;
        }
        return id;
    }

    // metodo upload alternativo
    @Override
    public void store(MultipartFile multi, Path rootLocation) {
        File file = null;
        try {
            boolean exito = true;
            Date date = new Date();
            long time = date.getTime();
            file = multipartToFile(multi, multi.getOriginalFilename());

            // validate file extension
            exito = validateFileExt((File) file);

            if(exito){
                Files.copy(multi.getInputStream(), rootLocation.resolve(multi.getOriginalFilename()));

            }else{
                logger.info("process fail because the file not have a valid format");
            }

        } catch (Exception e) {
            throw new RuntimeException("FAIL!");
        }

    }
    // Metodo para realizar el upload alternativo
    public  static File multipartToFile(MultipartFile multipart, String fileName) throws IllegalStateException, IOException {
        File convFile = new File( multipart.getOriginalFilename());
        multipart.transferTo(convFile);
        return convFile;
    }

    // get de archivos subidos al servidor
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

    // TODO: Implementar metodo en rest controller para eliminar archivos subidos
    @Override
    public void deleteAll(Path rootLocation) {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    // TODO: Implementar metodo en rest controller para crear directorio de carga de archivos
    @Override
    public void init(Path rootLocation) {
        try {
            Files.createDirectory(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage!");
        }
    }

}
