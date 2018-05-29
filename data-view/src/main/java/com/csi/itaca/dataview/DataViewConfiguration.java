package com.csi.itaca.dataview;

import com.csi.itaca.dataview.edm.GenericEntityProvider;
import com.csi.itaca.dataview.service.AllTabColsRepository;
import com.csi.itaca.dataview.service.EntityProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Getter
public class DataViewConfiguration {

    @Value("#{'${tableName}'.split(',')}")
    private List<String> tableNames = new ArrayList<>();

    @Value("${uiDisplayNameKey}")
    private List<String> uiDisplayNameKey = new ArrayList<>();

    @Value("${uiDescriptionkey}")
    private List<String> uiDescriptionkey = new ArrayList<>();

    @Value("${foreignTables}")
    private List<String> foreignTables = new ArrayList<>();

    @Value("${createPermission}")
    private List<String> createPermission = new ArrayList<>();

    @Value("${readPermission}")
    private List<String> readPermission = new ArrayList<>();

    @Value("${updatePermission}")
    private List<String> updatePermission = new ArrayList<>();

    @Value("${deletePermission}")
    private List<String> deletePermission = new ArrayList<>();


    /**
     * Gets all the available entities based on the table indicated in the configuration.
     * @return Map of all entities.
     */
    public Map<String, EntityProvider> getEntityProviders(JdbcTemplate jdbcTemplate, AllTabColsRepository colsService) {

        List<String> configTableNames = getTableNames();
        Map<String, EntityProvider> entityProviders = new HashMap<>();

        for (String tableName: configTableNames) {
            if (tableName!=null) {
                tableName = tableName.trim();
                if (!tableName.isEmpty()) {
                    GenericEntityProvider genericEntityProvider = getEntityProvider(tableName, jdbcTemplate, colsService);
                    entityProviders.put(tableName, genericEntityProvider);
                }
            }
        }

        return entityProviders;
    }

    /**
     * Get GenericEntityProvider of a given entity.
     * @return GenericEntityProvider.
     */
    public GenericEntityProvider getEntityProvider(String entityName,
                                                   JdbcTemplate jdbcTemplate,
                                                   AllTabColsRepository colsService) {
        // TODO: remove the jdbcTemplate, colsService parameters
        GenericEntityProvider genericEntityProvider = new GenericEntityProvider();
        genericEntityProvider.setResourceName(entityName);
        genericEntityProvider.setJdbcTemplate(jdbcTemplate);
        genericEntityProvider.setColsService(colsService);
        return genericEntityProvider;
    }
}
