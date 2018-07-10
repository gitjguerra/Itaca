package com.csi.itaca.load.job;

import com.csi.itaca.load.model.dto.PreloadData;
import org.apache.log4j.Logger;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;

import java.util.LinkedHashMap;

@StepScope
public class PreloadReader {

    //@Autowired
    //static
    //DataSource dataSource;

    // Database connection
    //static JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

    private final static Logger logger = Logger.getLogger(PreloadReader.class);

    public static FlatFileItemReader<PreloadData> reader(@Value("#{jobParameters[fullPathFileName]}") String pathToFile, @Value("#{jobParameters[id_load_process]}") String id_load_process, @Value("#{jobParameters[id_load_file]}") String id_load_file) {

        //  <editor-fold defaultstate="collapsed" desc="*** 3) Process file ***">
            //  2.4. For each row in the file (loop):

        FlatFileItemReader<PreloadData> reader = new FlatFileItemReader<>();

            reader.setResource(new ClassPathResource(pathToFile));

            // Get filed definition
            /*
            List<PreloadRowTypeDTO> rowTypes = jdbcTemplate.query("select ld_preload_row_type.* from ld_load_process, ld_preload_file, ld_preload_row_type " +
                    "WHERE ld_load_process.LOAD_PROCESS_ID = " + id_load_process + " " +
                    "AND ld_load_process.preload_definition_id = ld_preload_file.preload_definition_id " +
                    "AND ld_preload_file.preload_file_id = ld_preload_row_type.preload_file_id", new BeanPropertyRowMapper(PreloadRowTypeDTO.class));

            HashMap<String,Long> fields = new HashMap<>();
            Long idRowType = 0L;
            Long length = 0L;
            for(PreloadRowTypeDTO rowType : rowTypes)
            {
                logger.info(rowType.getName());
                logger.info(rowType.getPreloadRowTypeId());
                idRowType = rowType.getPreloadRowTypeId().longValue();

                List<PreloadFieldDefinitionDTO> fieldDefinitions = jdbcTemplate.query("SELECT PRELOAD_FIELD_DEFINITION_ID, PRELOAD_ROW_TYPE_ID, COLUMN_NO, LENGTH, NAME, DESCRIPTION, PRELOAD_FIELD_TYPE_ID, REGEX, REQUIRED, REL_TYPE, REL_FIELD_DEFINITION_ID, REL_DB_TABLE_NAME, REL_DB_FIELD_NAME, ERROR_SEVERITY " +
                "FROM ITACA.LD_PRELOAD_FIELD_DEFINITION " +
                "WHERE PRELOAD_ROW_TYPE_ID = " + idRowType, new BeanPropertyRowMapper(PreloadFieldDefinitionDTO.class));
                for(PreloadFieldDefinitionDTO fieldDefinition : fieldDefinitions)
                {
                    logger.info(fieldDefinition.getName());
                    logger.info(fieldDefinition.getLength());

                    fields.put( fieldDefinition.getName(), fieldDefinition.getLength() );

                }

            }
            */

                        // ************** OK **************
                //          a) Insert new row in to ld_preload_data table with row loaded from the file.
                //          b) Determine row type. (find [found row type id])
                //              i. For each ld_preload_field_row_type found in preparation check identifier_column_no and identifier_value. When there is a match row type is found.
                //          c) Find all field definitions based on found row type found above:
                //              i. select * from ld_preload_field_definition where preload_row_type_id = [found row type id]

                // TODO: delete when activate last commentaries
                LinkedHashMap<String,Long> fields = new LinkedHashMap<>();

                reader.setLineMapper(preloadLineMapper(fields));

                            // ************** FALTA DE AQUI PARA ABAJO **************

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

    private static LineMapper<PreloadData> preloadLineMapper(LinkedHashMap<String,Long> fields) {
        DefaultLineMapper<PreloadData> mapper = new DefaultLineMapper<>();
        mapper.setLineTokenizer(preloadLineTokenizer(fields));
        mapper.setFieldSetMapper(preloadFieldSetMapper());
        return mapper;
    }

    public static LineTokenizer preloadLineTokenizer(LinkedHashMap<String,Long> fields) {

        // TODO: Delete data example
        fields.put("Tipo_Reg", (long) 1);
        fields.put("Fec_Envio", (long) 8);
        fields.put("Convenio", (long) 3);

        int cont = 0;
        int intLengthInit = 0;
        int intLengthFinal = 0;
        int min = 0;
        int max = 0;
        String[] strNames =  new String[3];
        Range[] strRanges = new Range[3];

        FixedLengthTokenizer tokenizer = new FixedLengthTokenizer();

        //    CREATE A DINAMIC FIELDS RANGE
        for(String name: fields.keySet()){
            min = max + 1;
            intLengthInit = fields.get(name).intValue();
            max = intLengthInit + max;

            strRanges[cont] = new Range(min, max);
            strNames[cont] = name;
            cont++;
        }
        tokenizer.setColumns(strRanges);
        tokenizer.setNames(strNames);

        return tokenizer;
    }

    public static FieldSetMapper<PreloadData> preloadFieldSetMapper() {
        return new PreloadFieldSetMapper();
    }

}