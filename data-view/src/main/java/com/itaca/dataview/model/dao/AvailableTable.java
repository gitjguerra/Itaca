package com.itaca.dataview.model.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@PropertySource("classpath:application.yml")
public class AvailableTable {

    /*   Solucion1 con multiples valores separados por comas
    @Value("#{'${Table.tableName}'.split(',')}")
    private List<String> tableNames = new ArrayList<>();
    */

    @Value("${tableName}")
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
