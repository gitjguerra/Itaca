package com.csi.itaca.load.job;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.csi.itaca.load.model.dto.PreloadDataDTO;
import com.csi.itaca.load.service.LoadManagementBatchService;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class PreloadWriter implements ItemWriter<PreloadDataDTO> {

    private final LoadManagementBatchService loadManagementBatchService;

    public PreloadWriter(LoadManagementBatchService batchService) {
        this.loadManagementBatchService = batchService;
    }

    @Override
    public void write(List<? extends PreloadDataDTO> items){
        loadManagementBatchService.insert(items);
        // ii. Validate field content: For each ld_preload_field_definition perform validation based on
        // preload_field_type_id, regex & required.
        /*
        // TODO: Revisar caso required para añadirlo a la validacion
        String pattern = "([0-9]{4}[0-9]{2}[0-9]{2})";
        String cadena = "";
        Pattern pat = Pattern.compile(pattern);
        Matcher mat = pat.matcher(cadena);
        if (mat.matches()) {
            System.out.println("Valido");
        } else {
            System.out.println("NO Valido");
        }
        // Otro metodo
        Pattern patN = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,15}$");
        Matcher matN = patN.matcher(nick);
        if (!matN.find()) {
            JOptionPane.showMessageDialog(null, "El Nick esta Mal");
        }
        */

        // TODO: Segunda fase de PRELOAD
        // iii. Validate relation (file to database): For each rel_type = 2 or 3 look up
            // rel_db_table_name and rel_db_field_name. For example: Select * from
            // <<rel_db_table_name>> where <<rel_db_field_name>>=<<field value from file>> AND ROWNUM = 1
        // d) Check if the user has cancelled the load operation (ld_load_process.username_load_cancel).  ????
    }
}