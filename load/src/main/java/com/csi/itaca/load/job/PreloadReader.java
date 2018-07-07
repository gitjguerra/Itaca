package com.csi.itaca.load.job;

import com.csi.itaca.load.model.dto.PreloadData;
import com.csi.itaca.load.model.dto.PreloadRowTypeDTO;
import org.apache.log4j.Logger;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@StepScope
public class PreloadReader {

    @Autowired
    public DataSource dataSource;

    private final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    private final static Logger logger = Logger.getLogger(PreloadReader.class);

    // Get a list of row types associated to this load:
    private final String SELECT_ALL_ROWSTYPES_SQL_FULL =
            "select ld_preload_row_type.* from ld_load_process, ld_preload_file, ld_preload_row_type " +
            "WHERE ld_load_process.PRELOAD_DEFINITION_ID = ? " +
            "AND ld_load_process.preload_definition_id = ld_preload_file.preload_definition_id " +
            "AND ld_preload_file.preload_file_id = ld_preload_row_type.preload_file_id ";

    private final String SELECT_ALL_FIELDDEFINITION_SQL_FULL =
            "select * from ld_preload_field_definition where preload_row_type_id = ?";

    public static FlatFileItemReader<PreloadData> reader(@Value("#{jobParameters[fullPathFileName]}") String pathToFile) {

        FlatFileItemReader<PreloadData> reader = new FlatFileItemReader<PreloadData>();

        // 1. load_process_id
        //1.1. A record created in the ld_load_process table where load_process_id is associated.
        /*
        INSERT INTO ITACA.LD_LOAD_PROCESS
                (LOAD_PROCESS_ID, USER_ID, CREATED_TIMESTAMP, PRELOAD_DEFINITION_ID, USERNAME_LOAD_CANCEL)
        VALUES(0, 0, '', 0, '');
        */


        // 2. load_file_id
        //2.1. A record created in the ld_load_file table with same load_file_id and the same above load_process_id.
            // LATER


        // 1.1. Get a list of row types associated to this load:
            /*
            select ld_preload_row_type.* from ld_load_process, ld_preload_file, ld_preload_row_type
            WHERE ld_load_process.PRELOAD_DEFINITION_ID = 1
            AND ld_load_process.preload_definition_id = ld_preload_file.preload_definition_id
            AND ld_preload_file.preload_file_id = ld_preload_row_type.preload_file_id
            */
            // row type:
                    // 1 Head
                    // 2 Body
                    // 3 Footer
                // Select fields:     List<PreloadRowTypeDTO> rows = rowTypesFields();
                    // LATER


        // 2.1. Set ld_load_file.preload_start_time to the current time.
            // LATER

        // 2.2. Set ld_load_file.status_code to 200 indicating preload in progress.
            // LATER

        // 2.3. Determine file format type from file extension and choose appropriate file parser (CSV, Excel, TXT)
            // LATER

        // **** Construct the line with dynamic fields
            // select field definition with row type
            /*
            select * from ld_preload_field_definition where preload_row_type_id = 'ROW_TYPE';
                donde 1 es el "ROW_TYPE" Head
                donde 2 es el "ROW_TYPE" Body
                donde 3 es el "ROW_TYPE" Footer
            */

            // Later construct the line for LD_PRELOAD_DATA
            // C20170726000
            // - Header:   insert (PRELOAD_DATA_ID, LOAD_FILE_ID, LOADED_SUCCESSFULLY, CREATED_TIMESTAMP, ROW_TYPE, LINE_NUMBER, DATA_COL1, DATA_COL2, DATA_COL3)
            //strFields = "'Tipo Reg', 'load_file_id', 'Fec Envio', 'Convenio', 'Filer'";
            String strFields = "PRELOAD_DATA_ID, LOAD_FILE_ID, LOADED_SUCCESSFULLY, CREATED_TIMESTAMP, ROW_TYPE, LINE_NUMBER, DATA_COL1, DATA_COL2, DATA_COL3, DATA_COL4, DATA_COL5";


        // D2017072500100100000100315001851405              A20170725 UF000000000,28            1002            69194141  S70EJE   170934784            000C09 EJE001                 KAREN CONDOREP00000000              NO                                                                                                                                                                                                                                                               060162
            // - Body:   insert (PRELOAD_DATA_ID, LOAD_FILE_ID, LOADED_SUCCESSFULLY, CREATED_TIMESTAMP, ROW_TYPE, LINE_NUMBER, DATA_COL1, DATA_COL2, DATA_COL3, DATA_COL4, DATA_COL5, DATA_COL6, DATA_COL7, DATA_COL8, DATA_COL9, DATA_COL10, DATA_COL11)
            //String strFields = "'Tipo Reg', 'Fec Envio', 'Cod. Conv.', 'Tipo Trans.', 'Cod Ramo', 'Cod Linea', 'Cod Producto', 'Num Propuesta', 'Num. Poliza', 'Cod Plan', 'Fec Venta'";

            // T2017072600000236000000137,42
            // - Footer:
            // String strFields = "";    NOW is Empty in database
            // String strFields = "";    NOW is Empty in database



        // 2.4. For each row in the file (loop):
        reader.setResource(new ClassPathResource(pathToFile));
        reader.setLineMapper(preloadLineMapper());
        return reader;
    }

    private static LineMapper<PreloadData> preloadLineMapper() {
        DefaultLineMapper<PreloadData> mapper = new DefaultLineMapper<>();
        mapper.setLineTokenizer(preloadLineTokenizer());
        mapper.setFieldSetMapper(preloadFieldSetMapper());
        return mapper;
    }

    public static LineTokenizer preloadLineTokenizer() {
        FixedLengthTokenizer tokenizer = new FixedLengthTokenizer();
        // TODO: colocar longitudes dinamicas
        tokenizer.setColumns(new Range[] { new Range(1, 1), new Range(2, 5), new Range(6, 10) });
        tokenizer.setNames(new String[] { "id", "name", "description" });
        return tokenizer;
    }

    public static FieldSetMapper<PreloadData> preloadFieldSetMapper() {
        return new PreloadFieldSetMapper();
    }

    // find row types
    /*
    private List<PreloadRowTypeDTO> rowTypesFields() {
        List<PreloadRowTypeDTO> rowTypes = jdbcTemplate.query(
                SELECT_ALL_ROWSTYPES_SQL_FULL,
                new Object[]{"PRELOAD_ROW_TYPE_ID, PRELOAD_FILE_ID, NAME, DESCRIPTION, IDENTIFIER_COLUMN_NO, IDENTIFIER_VALUE"},
                new RowMapper<PreloadRowTypeDTO>() {
                    public PreloadRowTypeDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
                        PreloadRowTypeDTO rows = new PreloadRowTypeDTO();
                        rows.setPreloadRowTypeId(rs.getLong(1));
                        rows.setPreloadFileId(rs.getLong(2));
                        rows.setDescription(rs.getString(3));
                        rows.setIdentifierColumnNo(rs.getLong(4));
                        rows.setIdentifierValue(rs.getString(5));
                        rows.setName(rs.getString(6));
                        return rows;
                    }
                });
        return rowTypes;
    }
    */
}