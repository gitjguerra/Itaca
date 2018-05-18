package com.csi.itaca.dataview;

import com.csi.itaca.dataview.edm.GenericEntityProvider;
import com.csi.itaca.dataview.service.AllTabColsRepository;
import com.csi.itaca.dataview.service.EntityProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DataViewConfiguration {

    /*   Solucion1 con multiples valores separados por comas
    @Value("#{'${Table.tableName}'.split(',')}")
    private List<String> tableNames = new ArrayList<>();
    */

    @Value("#{'${tableName}'.split(',')}")
    private List<String> tableNames = new ArrayList<>();

    /*@Value("${tableMap}")
    private Map<String, String> map = new HashMap<String, String>();*/

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
                    GenericEntityProvider genericEntityProvider = new GenericEntityProvider();
                    genericEntityProvider.setResourceName(tableName);
                    genericEntityProvider.setJdbcTemplate(jdbcTemplate);
                    genericEntityProvider.setColsService(colsService);
                    entityProviders.put(tableName, genericEntityProvider);
                }
            }
        }

        return entityProviders;
    }

    public List<String> getTableNames() {
        return tableNames;
    }

    public void setTableNames(List<String> tableNames) {
        this.tableNames = tableNames;
    }

    public List<String> getUiDisplayNameKey() {
        return uiDisplayNameKey;
    }

    public void setUiDisplayNameKey(List<String> uiDisplayNameKey) {
        this.uiDisplayNameKey = uiDisplayNameKey;
    }

    public List<String> getUiDescriptionkey() {
        return uiDescriptionkey;
    }

    public void setUiDescriptionkey(List<String> uiDescriptionkey) {
        this.uiDescriptionkey = uiDescriptionkey;
    }

    public List<String> getForeignTables() {
        return foreignTables;
    }

    public void setForeignTables(List<String> foreignTables) {
        this.foreignTables = foreignTables;
    }

    public List<String> getCreatePermission() {
        return createPermission;
    }

    public void setCreatePermission(List<String> createPermission) {
        this.createPermission = createPermission;
    }

    public List<String> getReadPermission() {
        return readPermission;
    }

    public void setReadPermission(List<String> readPermission) {
        this.readPermission = readPermission;
    }

    public List<String> getUpdatePermission() {
        return updatePermission;
    }

    public void setUpdatePermission(List<String> updatePermission) {
        this.updatePermission = updatePermission;
    }

    public List<String> getDeletePermission() {
        return deletePermission;
    }

    public void setDeletePermission(List<String> deletePermission) {
        this.deletePermission = deletePermission;
    }
}
