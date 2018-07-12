package com.csi.itaca.load.job;

import java.util.List;

import com.csi.itaca.load.model.PreloadDataDao;
import com.csi.itaca.load.model.dto.PreloadData;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;


// TODO: Change for data objects ITACA
@Component
public class PreloadWriter implements ItemWriter<PreloadData> {

    private final PreloadDataDao preloadDataDao;

    public PreloadWriter(PreloadDataDao preloadDataDao) {
        this.preloadDataDao = preloadDataDao;
    }

    @Override
    public void write(List<? extends PreloadData> items) throws Exception {
        // a) Insert new row in to ld_preload_data table with row loaded from the file.
        preloadDataDao.insert(items);
        // b) Determine row type. (find [found row type id])
        // i. For each ld_preload_field_row_type found in preparation check identifier_column_no and identifier_value.
        // When there is a match row type is found.

        // c) Find all field definitions based on found row type found above:
        // i. select * from ld_preload_field_definition where preload_row_type_id = [found row type id]

        // ii. Validate field content: For each ld_preload_field_definition perform validation based on
        // preload_field_type_id, regex & required.
        // iii. Validate relation (file to database): For each rel_type = 2 or 3 look up
        // rel_db_table_name and rel_db_field_name. For example: Select * from
        // <<rel_db_table_name>> where <<rel_db_field_name>>=<<field value from file>> AND ROWNUM = 1

        // d) Check if the user has cancelled the load operation (ld_load_process.username_load_cancel).  ????
    }
}