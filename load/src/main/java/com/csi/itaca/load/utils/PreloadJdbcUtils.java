package com.csi.itaca.load.utils;

import com.csi.itaca.load.model.dto.PreloadFieldDefinitionDTO;
import com.csi.itaca.load.model.dto.PreloadRowTypeDTO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

@Component
public class PreloadJdbcUtils implements Serializable {

    @Autowired
    public DataSource dataSource;

    public LinkedHashMap<String,Long> getFieldDefinition(String id_load_process){

        final Logger logger = Logger.getLogger(PreloadJdbcUtils.class);

        // Database connection
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        List<PreloadRowTypeDTO> rowTypes = jdbcTemplate.query("select ld_preload_row_type.* from ld_load_process, ld_preload_file, ld_preload_row_type " +
                "WHERE ld_load_process.LOAD_PROCESS_ID = " + id_load_process + " " +
                "AND ld_load_process.preload_definition_id = ld_preload_file.preload_definition_id " +
                "AND ld_preload_file.preload_file_id = ld_preload_row_type.preload_file_id", new BeanPropertyRowMapper(PreloadRowTypeDTO.class));

        LinkedHashMap<String,Long> fields = new LinkedHashMap<>();
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
        return fields;
    }

}
