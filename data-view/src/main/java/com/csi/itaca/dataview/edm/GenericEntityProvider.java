package com.csi.itaca.dataview.edm;

import com.csi.itaca.dataview.model.ColumnDefinition;
import com.csi.itaca.dataview.service.AllTabColsRepository;
import com.csi.itaca.dataview.model.GenericRecord;
import com.csi.itaca.dataview.service.EntityProvider;
import org.apache.log4j.Logger;
import org.apache.olingo.commons.api.data.Entity;
import org.apache.olingo.commons.api.data.EntitySet;
import org.apache.olingo.commons.api.edm.EdmEntitySet;
import org.apache.olingo.commons.api.edm.EdmPrimitiveTypeKind;
import org.apache.olingo.commons.api.edm.FullQualifiedName;
import org.apache.olingo.commons.core.data.EntitySetImpl;
import org.apache.olingo.server.api.edm.provider.EntityType;
import org.apache.olingo.server.api.edm.provider.Property;
import org.apache.olingo.server.api.edm.provider.PropertyRef;
import org.apache.olingo.server.api.uri.UriInfo;
import org.apache.olingo.server.api.uri.UriResource;
import org.apache.olingo.server.api.uri.UriResourceEntitySet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author Brian Boothe
 *
 */
@Component
public class GenericEntityProvider implements EntityProvider {

	/** Logger */
	private static Logger logger = Logger.getLogger(GenericEntityProvider.class);

	// Service Namespace
	public static final String NAMESPACE = "com.csi.itaca.dataview";

	// EDM Container
	public static final String CONTAINER_NAME = "Container";

	public static final FullQualifiedName CONTAINER = new FullQualifiedName(NAMESPACE, CONTAINER_NAME);

	/** Name of table in the database. */
	public String tableName;

	public FullQualifiedName fullQualifiedName;

	@Autowired
	private JdbcTemplate jdbcTemplate;

    @Autowired
    private AllTabColsRepository colsService;

    public void setResourceName(String tableName) {
    	this.tableName = tableName;
		fullQualifiedName = new FullQualifiedName(NAMESPACE, tableName);
	}


	public List<ColumnDefinition> getColumnDefinitionList() {
    	return colsService.findByTableName(tableName);
	}

	/**
	 * Gets a comma separated list of fields in this table.
	 * @return comma separated list of fields.
	 */
	public String getColumnsCommaSeparated() {
		List<ColumnDefinition> colsList = colsService.findByTableName(tableName);
		StringBuffer columns = new StringBuffer();
		for(int colNum = 0; colNum < colsList.size(); colNum++) {
			if  (columns.length() == 0)
				columns.append(colsList.get(colNum).getCOLUMN_NAME());
			else {
				columns.append(", ").append(colsList.get(colNum).getCOLUMN_NAME());
			}
		}
		return columns.toString();
	}

	/**
	 * Gets a comma separated list of fields in this table.
	 * @param colsList column definition list.
	 * @return comma separated list of fields.
	 */
	public String getColumnsCommaSeparated(List<ColumnDefinition> colsList) {

		StringBuffer columns = new StringBuffer();
		for(int colNum = 0; colNum < colsList.size(); colNum++) {
			if  (columns.length() == 0)
				columns.append(colsList.get(colNum).getCOLUMN_NAME());
			else {
				columns.append(", ").append(colsList.get(colNum).getCOLUMN_NAME());
			}
		}
		return columns.toString();
	}

	@Override
	public EntityType getEntityType() {

		logger.debug(" getEntityType() Table name: "+ tableName);

		List<ColumnDefinition> allColsList = colsService.findByTableName(tableName);
		List<Property> columnList =  new ArrayList <>();

		for(int i=0; i<allColsList.size(); i++)
		{
			String typeCol=allColsList.get(i).getDATA_TYPE();
			if (typeCol.equals("NUMBER")){
				columnList.add(new Property().setName(allColsList.get(i).getCOLUMN_NAME()).setType(
						EdmPrimitiveTypeKind.Int32.getFullQualifiedName()));
			}
			else if (typeCol.equals("VARCHAR2")){
				columnList.add( new Property().setName(allColsList.get(i).getCOLUMN_NAME()).setType(
						EdmPrimitiveTypeKind.String.getFullQualifiedName()));
			}
			else if (typeCol.equals("DATE")){
				columnList.add( new Property().setName(allColsList.get(i).getCOLUMN_NAME()).setType(
						EdmPrimitiveTypeKind.Date.getFullQualifiedName()));
			}
			else if (typeCol.equals("CHAR")){
				columnList.add( new Property().setName(allColsList.get(i).getCOLUMN_NAME()).setType(
						EdmPrimitiveTypeKind.String.getFullQualifiedName()));
			}
		}

		// create PropertyRef for Key element
		PropertyRef propertyRef = new PropertyRef();
		logger.debug("Table key: "+columnList.get(0).getName());
		propertyRef.setPropertyName(columnList.get(0).getName());

		// configure EntityType
		EntityType entityType = new EntityType();
		entityType.setName(tableName);
		entityType.setProperties(columnList);
		entityType.setKey(Arrays.asList(propertyRef));

		return entityType;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.rohitghatol.spring.odata.edm.providers.EntityProvider#getEntitySet
	 * (org.apache.olingo.server.api.uri.UriInfo)
	 */
	@Override
	public EntitySet getEntitySet(UriInfo uriInfo) {

		logger.info("getEntitySet()");

		List<UriResource> resourcePaths = uriInfo.getUriResourceParts();
		UriResourceEntitySet uriResourceEntitySet = (UriResourceEntitySet) resourcePaths.get(0);
		EdmEntitySet edmEntitySet = uriResourceEntitySet.getEntitySet();
		EntitySet entitySet = getData(edmEntitySet);
		return entitySet;
	}

	/**
	 * Gets the data for this entity.
	 * @param edmEntitySet for which the data is requested
	 * @return data of requested entity set
	 */
	private EntitySet getData(EdmEntitySet edmEntitySet) {
		logger.debug("getData() Table: "+ tableName);

		// Get all the columns for this entity ...
		List<ColumnDefinition> colsList = colsService.findByTableName(tableName);
		StringBuffer columns = new StringBuffer();
		int colNum;
		for(colNum = 0; colNum < colsList.size(); colNum++) {
			if  (columns.length() == 0)
				columns.append(colsList.get(colNum).getCOLUMN_NAME());
			else {
				columns.append(", ").append(colsList.get(colNum).getCOLUMN_NAME());
			}
		}
		logger.debug("Columns: "+columns);
		logger.debug("No. of columns: "+colNum);

		// Get all the data...
		EntitySet entitySet = new EntitySetImpl();
		List<Entity> entityList = entitySet.getEntities();
		List<GenericRecord> genericRow =  colsService.findAllRows(columns.toString(), tableName);

		for(int record = 0; record < genericRow.size(); record++) {
			entityList.add(genericRow.get(record).getEntity(colsList) );
		}
		logger.debug(genericRow.size() + " rows retrieved.");

		return entitySet;
	}

	/**
	 * Parse a date string.
	 * @param dateStr date in string form.
	 * @return standard java date object.
	 */
	private Date parseDate(Object dateStr) {
		try {
			return new SimpleDateFormat("yyyy-MM-dd").parse(dateStr.toString());
		}
		catch(Exception e) {
			return null;
		}
	}

	@Override
	public String getEntitySetName() {
		return tableName;
	}

	@Override
	public FullQualifiedName getFullyQualifiedName() {
		return fullQualifiedName;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void setColsService(AllTabColsRepository colsService) {
		this.colsService = colsService;
	}

}