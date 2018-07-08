package com.csi.itaca.load.job;

import com.csi.itaca.load.model.dto.PreloadData;
import com.csi.itaca.load.model.dto.PreloadRowTypeDTO;
import org.apache.log4j.Logger;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

@StepScope
public class PreloadReader {

    private final static Logger logger = Logger.getLogger(PreloadReader.class);

    // Preparation file: Get a list of row types associated to this load:
    private static final String SELECT_ALL_ROWSTYPES_SQL_FULL =
            "select ld_preload_row_type.* from ld_load_process, ld_preload_file, ld_preload_row_type " +
            "WHERE ld_load_process.LOAD_PROCESS_ID = ? " +
            "AND ld_load_process.preload_definition_id = ld_preload_file.preload_definition_id " +
            "AND ld_preload_file.preload_file_id = ld_preload_row_type.preload_file_id ";

    private static final String SELECT_ALL_FIELDDEFINITION_SQL_FULL =
            "select * from ld_preload_field_definition where preload_row_type_id = ?";

    public static FlatFileItemReader<PreloadData> reader(@Value("#{jobParameters[fullPathFileName]}") String pathToFile, @Value("#{jobParameters[id_load_process]}") String id_load_process, @Value("#{jobParameters[id_load_file]}") String id_load_file) {

        //  <editor-fold defaultstate="collapsed" desc="*** 3) Process file ***">
            //  2.4. For each row in the file (loop):

            FlatFileItemReader<PreloadData> reader = new FlatFileItemReader<>();
            reader.setResource(new ClassPathResource(pathToFile));

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
            // ******* TEMPORAL PARA LAS PRUEBAS INICIALES DE LECTURA *********
            String strFields = "PRELOAD_DATA_ID, LOAD_FILE_ID, LOADED_SUCCESSFULLY, CREATED_TIMESTAMP, ROW_TYPE, LINE_NUMBER, DATA_COL1, DATA_COL2, DATA_COL3, DATA_COL4, DATA_COL5";

            // D2017072500100100000100315001851405              A20170725 UF000000000,28            1002            69194141  S70EJE   170934784            000C09 EJE001                 KAREN CONDOREP00000000              NO                                                                                                                                                                                                                                                               060162
            // - Body:   insert (PRELOAD_DATA_ID, LOAD_FILE_ID, LOADED_SUCCESSFULLY, CREATED_TIMESTAMP, ROW_TYPE, LINE_NUMBER, DATA_COL1, DATA_COL2, DATA_COL3, DATA_COL4, DATA_COL5, DATA_COL6, DATA_COL7, DATA_COL8, DATA_COL9, DATA_COL10, DATA_COL11)
            //String strFields = "'Tipo Reg', 'Fec Envio', 'Cod. Conv.', 'Tipo Trans.', 'Cod Ramo', 'Cod Linea', 'Cod Producto', 'Num Propuesta', 'Num. Poliza', 'Cod Plan', 'Fec Venta'";

            // T2017072600000236000000137,42
            // - Footer:
            // String strFields = "";    NOW is Empty in database
            // String strFields = "";    NOW is Empty in database

            reader.setLineMapper(preloadLineMapper());

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

                // ***** Where implement this? *****
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
                // ***** Where implement this? *****

        return reader;

        //  </editor-fold>

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
        //tokenizer.setColumns(new Range[] { new Range(1, 1), new Range(2, 9), new Range(10-12), new Range(13-160) });
        tokenizer.setColumns(new Range[] { new Range(1, 1) });
        tokenizer.setNames(new String[] { "id" });
        //tokenizer.setNames(new String[] { "id", "name", "description" });
        return tokenizer;
    }

    public static FieldSetMapper<PreloadData> preloadFieldSetMapper() {
        return new PreloadFieldSetMapper();
    }

}