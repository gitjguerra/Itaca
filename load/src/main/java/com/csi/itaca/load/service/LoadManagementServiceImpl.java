package com.csi.itaca.load.service;

import com.csi.itaca.common.GlobalErrorConstants;
import com.csi.itaca.load.model.*;
import com.csi.itaca.load.model.dao.*;
import com.csi.itaca.load.model.dto.LoadFileDTO;
import com.csi.itaca.load.model.dto.PreloadDataDTO;
import com.csi.itaca.load.model.dto.PreloadDefinitionDTO;
import com.csi.itaca.load.repository.*;
import com.csi.itaca.load.utils.Constants;
import com.csi.itaca.tools.utils.beaner.Beaner;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.DataSource;
import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
    LoadProcessRepository loadProcessRepository;

    @Autowired
    private LoadFileRepository loadFileRepository;

    @Autowired
    private PreloadFileRepository preloadFileRepository;

    @Autowired
    private PreloadFieldDefinitionRepository fieldDefinitionRepository;

    @Autowired
    private PreloadDefinitionRepository preloadDefinitionRepository;

    @Autowired
    private PreloadDataRepository preloadDataRepository;

    @Autowired
    PreloadRowTypeRepository preloadRowTypeRepository;

    @Autowired
    private LoadRowOperationRepository loadRowOperationEntity;

    @Autowired
    private LoadRowOperationRepository loadRowOperationRepository;

    @Autowired
    private LoadOperationTypeRepository loadOperationTypeRepository;

    @Autowired
    private LoadDefinitiveTableRepository loadDefinitiveTableRepository;

    @Autowired
    public Constants constants;

    @Autowired
    private Beaner beaner;

    @Autowired
    public DataSource dataSource;

    // Global variables required
    private String query = "";
    private final static Logger logger = Logger.getLogger(LoadManagementServiceImpl.class);

    // Upload directory - Set with resource in the applioation.yml  example:     fileUploadDirectory: "/temp"
    @Value("${spring.batch.job.fileUploadDirectory}")
    private String fileUploadDirectory;

    // Upload file
    @Override
    public HttpStatus upload(MultipartFile file) {
        try {
            Path rootLocation = Paths.get(fileUploadDirectory);
            File fileToImport = new File(rootLocation + File.separator + file.getOriginalFilename());
            OutputStream outputStream = null;
            outputStream = new FileOutputStream(fileToImport);
            IOUtils.copy(file.getInputStream(), outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return HttpStatus.NOT_FOUND;
        } catch (IOException e) {
            e.printStackTrace();
            return HttpStatus.NOT_FOUND;
        }
        return HttpStatus.OK;
    }

    // Create Job
    @Override
    public LoadFileDTO create(Path rootLocation, File file, Long preloadDefinitionId, Errors errTracking) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        // Database connection
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        // where expression for find id tables
        String where = "";
        // Active user, if you not logon into the app please use a anonymousUser, add on the DB 'anonymousUser'
        String user = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = findUserId(user);
        // DateTime to execute job
        Date date = new Date();
        //  2.1. Set ld_load_file.preload_start_time to the current time.
        java.sql.Date preload_star_time = new java.sql.Date(date.getTime());
        //  2.2. Set ld_load_file.status_code to 200 indicating preload in progress.   (The status code is a Http status o JobExecution status ???)
        String statusCode = Constants.getPreloadingInProgress();
        String statusMessage = Constants.getPreloadingInProgressDesc();
        //  2.3. Determine file format type from file extension and choose appropriate file parser (CSV, Excel, TXT).
        String fileExtension = getFileExtension(file);
        //Long preloadDefinitionId = 1L;

        // 1. load_process_id
        //1.1. A record created in the ld_load_process table where load_process_id is associated.
        Long loadProcessId = findNextVal("SEQ_LOAD_PROCESS_ID.NEXTVAL");
        query = "INSERT INTO LD_LOAD_PROCESS (LOAD_PROCESS_ID, USER_ID, CREATED_TIMESTAMP, PRELOAD_DEFINITION_ID) VALUES(?, ?, ?, ?)";
        jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setLong(1, loadProcessId);
                statement.setLong(2, userId);
                statement.setDate(3, preload_star_time);
                statement.setLong(4, preloadDefinitionId);
                return statement;
            }
        });

        // 2. load_file_id
        //2.1. A record created in the ld_load_file table with same load_file_id and the same above load_process_id.
        Long loadFileId = findNextVal("SEQ_LOAD_FILE_ID.NEXTVAL");
        query = "INSERT INTO LD_LOAD_FILE (LOAD_FILE_ID, LOAD_PROCESS_ID, FILENAME, FILE_SIZE, CHECKSUM, PRELOAD_START_TIME, STATUS_CODE, STATUS_MESSAGE) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setLong(1, loadFileId);
                statement.setLong(2, loadProcessId);
                statement.setString(3, file.getName());
                statement.setLong(4, file.length());
                statement.setString(5, "");
                statement.setDate(6, preload_star_time);
                statement.setString(7, statusCode);
                statement.setString(8, statusMessage);
                return statement;
            }
        });

        LoadFileEntity loadFileEntity = loadFileRepository.findOne(loadFileId);
        if (loadFileEntity == null && errTracking != null) {
            errTracking.reject(GlobalErrorConstants.DB_ITEM_NOT_FOUND);
        }else{
            // Create Job with JobExplorer
        }
        return beaner.transform(loadFileEntity, LoadFileDTO.class);
    }

    // Execute Job
    @Override
    public BatchStatus executeJob(String file, String loadProcessId, String loadFileId) {

        // TODO:  Skip limit for Job
        // Skip limit for Job
        // Take and create dynamic ?????????

        JobExecution jobExecution = null;
        try {
            jobExecution = jobLauncher.run(sqlExecuteJob, new JobParametersBuilder()
                    .addString("fullPathFileName", file)
                    .addString("id_load_process", loadProcessId)
                    .addString("id_load_file", loadFileId)
                    .addLong("time", System.currentTimeMillis()).toJobParameters());
        } catch (JobExecutionAlreadyRunningException e) {
            e.printStackTrace();
        } catch (JobRestartException e) {
            e.printStackTrace();
        } catch (JobInstanceAlreadyCompleteException e) {
            e.printStackTrace();
        } catch (JobParametersInvalidException e) {
            e.printStackTrace();
        }
        return jobExecution.getStatus();
    }

    // Cancel Job
    @Override
    public BatchStatus stopJob() {
        return null;
    }


    // Job execute
    @Override
    public BatchStatus fileToDatabaseJob(Path rootLocation, File file) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {

        /*
        // Database connection
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        // where expression for find id tables
        String where = "";
        // Active user, if you not logon into the app please use a anonymousUser, add on the DB 'anonymousUser'
        String user = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = findUserId(user);
        // DateTime to execute job
        Date date = new Date();
        //  2.1. Set ld_load_file.preload_start_time to the current time.
        java.sql.Date preload_star_time = new java.sql.Date(date.getTime());
        //  2.2. Set ld_load_file.status_code to 200 indicating preload in progress.   (The status code is a Http status o JobExecution status ???)
        String statusCode = Constants.getPreloadingInProgress();
        String statusMessage = Constants.getPreloadingInProgressDesc();
        //  2.3. Determine file format type from file extension and choose appropriate file parser (CSV, Excel, TXT).
        String fileExtension = getFileExtension(file);

        //Launch the Batch Job
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

        // TODO: HOW PASS THE PRELOAD_DEFINITION_ID

        Long preloadDefinitionId = 1L;
        //  <editor-fold defaultstate="collapsed" desc="*** 1) Prerequisite(s) ***">
        // 1. load_process_id
        //1.1. A record created in the ld_load_process table where load_process_id is associated.
        Long loadProcessId = findNextVal("SEQ_LOAD_PROCESS_ID.NEXTVAL");
        query = "INSERT INTO LD_LOAD_PROCESS (LOAD_PROCESS_ID, USER_ID, CREATED_TIMESTAMP, PRELOAD_DEFINITION_ID) VALUES(?, ?, ?, ?)";
        jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setLong(1, loadProcessId);
                statement.setLong(2, userId);
                statement.setDate(3, preload_star_time);
                statement.setLong(4, preloadDefinitionId);
                return statement;
            }
        });

        // 2. load_file_id
        //2.1. A record created in the ld_load_file table with same load_file_id and the same above load_process_id.
        Long loadFileId = findNextVal("SEQ_LOAD_FILE_ID.NEXTVAL");
        query = "INSERT INTO LD_LOAD_FILE (LOAD_FILE_ID, LOAD_PROCESS_ID, FILENAME, FILE_SIZE, CHECKSUM, PRELOAD_START_TIME, STATUS_CODE, STATUS_MESSAGE) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setLong(1, loadFileId);
                statement.setLong(2, loadProcessId);
                statement.setString(3, file.getName());
                statement.setLong(4, file.length());
                statement.setString(5, "");
                statement.setDate(6, preload_star_time);
                statement.setString(7, statusCode);
                statement.setString(8, statusMessage);
                return statement;
            }
        });
        // id insert on LD_LOAD_FILE
        //where = " filename='" + file.getName() + "' AND LOAD_PROCESS_ID=" + idLoadProcess;
        //final Long idLoadFile = findInsertId("LD_LOAD_FILE", "LOAD_FILE_ID", where);
        //  </editor-fold>

        // Skip limit for Job
        JobExecution jobExecution = jobLauncher.run(sqlExecuteJob, new JobParametersBuilder()
                .addString("fullPathFileName", file.getName())
                .addString("id_load_process", loadProcessId.toString())
                .addString("id_load_file", loadFileId.toString())
                .addLong("time", System.currentTimeMillis()).toJobParameters());  // Se agrega para ejecutar multiples hilos

        // If all Ok return value
        return jobExecution.getStatus();
        */
        return null;
    }

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

    public Long findNextVal(String secuenceName){
        Long id = 0L;
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            String sql = "SELECT " + secuenceName + " FROM DUAL";
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
    // *********** LOAD *************** //
/*

    @Override
    public List<PreloadDataDTO> getPreloadData(LoadFileEntity loadFileId, Pagination pagination, Order order) {

        List<? extends PreloadDataEntity> preloadDataEntities = preloadDataRepository.findByLoadFileId(loadFileId);

        if (preloadDataEntities!=null) {

            for (PreloadData l : preloadDataEntities) {
                logger.info("Resultado en getPreloadData ("+l.getPreloadDataId()+ " --- " +l.getLoadFileId()+")");
            }
            return beaner.transform(preloadDataEntities, PreloadDataDTO.class);
        }
        else {
            return null;
        }
    }
*/


/*
    @Override
    public List<LoadFileDTO> getFile(Long loadProcessId, Pagination pagination, Order order) {
        Specification<LoadFileEntity> spec = (root, query, cb) -> {
            Predicate p = cb.equal(root.get(LoadFileEntity.LOAD_PROCESS_ID), loadProcessId);
            if (order != null && order.getField() != null) {
                if(order.isAscending()){
                    query.orderBy(cb.asc(root.get(JpaUtils.getField(LoadFileEntity.class, order))));
                } else {
                    query.orderBy(cb.desc(root.get(JpaUtils.getField(LoadFileEntity.class, order))));
                }
            }

            return p;
        };

        List<? extends LoadFileEntity> loadFileEntities = null;
        if (pagination != null) {
            PageRequest pr = new PageRequest(pagination.getPageNo() - 1, pagination.getItemsPerPage());
            loadFileEntities = loadFileRepository.findAll(spec, pr).getContent();
        }
        else {
            loadFileEntities = loadFileRepository.findAll(spec);
        }

        for (LoadFile l : loadFileEntities) {
            logger.info("Resultado solo con loadProcessId ("+l.getFileName()+ " " +l.getLoadFileId()+")");

        }

        return beaner.transform(loadFileEntities, LoadFileDTO.class);
    }
*/

    @Override
    public List<LoadFileDTO> getFile(LoadProcessEntity loadProcessId, Long loadFileId) {

        List<? extends LoadFileEntity> loadFileEntities = loadFileRepository.findByloadProcessIdAndLoadFileId(loadProcessId, loadFileId);

        if (loadFileEntities!=null) {

            for (LoadFile l : loadFileEntities) {
                logger.info("Resultado con los dos paramteros ("+l.getFileName()+ " -- " +l.getLoadFileId()+")");
            }
            return beaner.transform(loadFileEntities, LoadFileDTO.class);
        }
        else {
            return null;
        }
    }


    @Override
    public List<PreloadDataDTO> preloadedRowList(Long loadProcessId, LoadFileEntity loadFileId) {

        java.util.Date fecha = new Date();
        LoadFileEntity LoadFileEntityToSave = null;
        LoadFileEntity LoadFileSavedEntity = null;
        LoadRowOperationEntity loadRowOperationEntity = null;
        LoadOperationTypeEntity loadOperationTypeEntity = null;
        LoadDefinitiveTableEntity loadDefinitiveTableEntity = null;

        List<? extends PreloadDataEntity> preloadDataEntities = preloadDataRepository.findByLoadFileId_LoadProcessId_LoadProcessIdAndLoadFileIdOrderByLineNumber(loadProcessId, loadFileId);

        if (preloadDataEntities!=null) {

            for (PreloadData l : preloadDataEntities) {

                //Get a list of all data rows associated with file (-----> preloadedRowList )

                logger.info("Resultado de preloadedRowList ("+l.getLoadFileId()+ " -- " +l.getPreloadDataId()+")");
                LoadFileEntityToSave = loadFileRepository.findOne(l.getLoadFileId().getLoadFileId());

                //Set ld_load_file.load_start_time to the current time
                LoadFileEntityToSave.setPreloadStartTime(fecha);

                //Set ld_load_file.status_code to 300 indicating load in progress.
                LoadFileEntityToSave.setStatusCode(300L);

                LoadFileSavedEntity = loadFileRepository.save(LoadFileEntityToSave);
                logger.info("Resultado de LoadFileSavedEntity ("+ LoadFileSavedEntity.getLoadFileId() +")");

                //Get all operations associated to the current row type (--------> operationsList )

                loadRowOperationEntity = loadRowOperationRepository.findOne(l.getPreloadRowTypeId().getPreloadRowTypeId());
                //loadRowOperationEntity = (LoadRowOperationEntity) loadRowOperationRepository.findByPreloadRowTypeIdOrderByOperationOrder(l.getPreloadRowTypeId().getPreloadRowTypeId());
                logger.info("Resultado de operationsList -- loadRowOperationEntity getLoadRowOperationId ("+ loadRowOperationEntity.getLoadRowOperationId() +")");
                logger.info("Resultado de operationsList -- getLoadOperationTypeId en LoadOperationTypeId ("+ loadRowOperationEntity.getLoadOperationTypeId() +")");
                logger.info("Resultado de operationsList -- getPreloadRowTypeId ("+ loadRowOperationEntity.getPreloadRowTypeId() +")");
                logger.info("Resultado de operationsList -- getLoadDefinitiveTableId ("+ loadRowOperationEntity.getLoadDefinitiveTableId() +")");
                logger.info("Resultado de operationsList -- getKeyColumnNo ("+ loadRowOperationEntity.getKeyColumnNo() +")");
                logger.info("Resultado de operationsList -- getOperationOrder ("+ loadRowOperationEntity.getOperationOrder() +")");

                //Get operation type (there should only be one) (--------> operationType )

                loadOperationTypeEntity = loadOperationTypeRepository.findOne(loadRowOperationEntity.getLoadOperationTypeId());
                logger.info("Resultado de operationType -- loadOperationTypeEntity getName ("+ loadOperationTypeEntity.getName() +")");

                //Get target definitive table (there should only be one)
                loadDefinitiveTableEntity = loadDefinitiveTableRepository.findOne(loadRowOperationEntity.getLoadDefinitiveTableId());
                logger.info("Resultado de target definitive table -- loadDefinitiveTableEntity ("+ loadDefinitiveTableEntity.getTableName() +")");


                //Run Operation....

                //Set ld_load_file.load_end_time to the current time
                LoadFileEntityToSave.setLoadEndTime(fecha);

                //Set ld_load_file.status_code to 302 indicating load in progress.
                LoadFileEntityToSave.setStatusCode(302L);

                LoadFileSavedEntity = loadFileRepository.save(LoadFileEntityToSave);
                logger.info("Resultado de LoadFileSavedEntity End ("+ LoadFileSavedEntity.getLoadFileId() +")");

            }
            return beaner.transform(preloadDataEntities, PreloadDataDTO.class);
        }
        else {
            return null;
        }
    }

    @Override
    public List<PreloadRowTypeEntity> rowTypesServices(Long loadProcessId){
        LoadProcessEntity processEntity = new LoadProcessEntity();
        processEntity.setLoadProcessId(loadProcessId);
        PreloadFileEntity fileEntity = new PreloadFileEntity();
        PreloadDefinitionEntity definitionEntity = new PreloadDefinitionEntity();
        PreloadRowTypeEntity rowTypeEntity = new PreloadRowTypeEntity();

        List<LoadProcessEntity> loadProcessEntities = loadProcessRepository.findByLoadProcessId(processEntity.getLoadProcessId());
        for(LoadProcess load : loadProcessEntities){
            definitionEntity.setPreloadDefinitionId(load.getPreloadDefinitionId());
            List<PreloadFileEntity> preloadFileEntities = preloadFileRepository.findByPreloadDefinitionId(definitionEntity);
            for(PreloadFile preload : preloadFileEntities){
                fileEntity.setPreloadFileId(preload.getPreloadFileId());
                List<PreloadRowTypeEntity> preloadRowTypeEntities = preloadRowTypeRepository.findByPreloadFileId(fileEntity);
                return preloadRowTypeEntities;
            }
        }
        return null;
    }

    @Override
    public List<PreloadDefinitionDTO> getPreloadDefinitionList() {
        return  beaner.transform((List<PreloadDefinitionEntity>) preloadDefinitionRepository.findAll(), PreloadDefinitionDTO.class);
    }

    /*
    Method for find a skipLimit on the DB
    */
    public Integer getSkipLimit(Long processId){
        List<LoadProcessEntity> processEntities = loadProcessRepository.findByLoadProcessId(processId);
        if(processEntities.size()>0){
            PreloadDefinitionEntity entity = preloadDefinitionRepository.findOne(processEntities.get(0).getPreloadDefinitionId());
            processId = entity.getMaxPreloadHighErrors();
        }
        return Math.toIntExact(processId);
    }

}
