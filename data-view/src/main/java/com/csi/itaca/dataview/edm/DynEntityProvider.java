/**
 * 
 */
package com.csi.itaca.dataview.edm;


import com.csi.itaca.dataview.model.dao.AllTabCols;
import com.csi.itaca.dataview.model.dao.AllTabColsRepository;
import com.csi.itaca.dataview.model.dao.FilaGenerico;
import com.csi.itaca.dataview.service.EntityProvider;
import org.apache.log4j.Logger;
import org.apache.olingo.commons.api.data.Entity;
import org.apache.olingo.commons.api.data.EntitySet;
import org.apache.olingo.commons.api.data.ValueType;
import org.apache.olingo.commons.api.edm.EdmEntitySet;
import org.apache.olingo.commons.api.edm.EdmPrimitiveTypeKind;
import org.apache.olingo.commons.api.edm.FullQualifiedName;
import org.apache.olingo.commons.core.data.EntityImpl;
import org.apache.olingo.commons.core.data.EntitySetImpl;
import org.apache.olingo.commons.core.data.PropertyImpl;
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
 * @author Rommel Vega
 *
 */
@Component
public class DynEntityProvider implements EntityProvider {

	/** Logger */
	private static Logger logger = Logger.getLogger(DynEntityProvider.class);

	// Service Namespace
	public static final String NAMESPACE = "com.csi.itaca.dataview";

	// EDM Container
	public static final String CONTAINER_NAME = "Container";

	public static final FullQualifiedName CONTAINER = new FullQualifiedName(NAMESPACE, CONTAINER_NAME);

	// Entity Types Names
	public String numberDelResouce;
	public FullQualifiedName fullQualifiedName;

	@Autowired
	private JdbcTemplate jdbcTemplate;

    @Autowired
    private AllTabColsRepository colsService;

    public void setResourceName(String numberDelResouce) {
    	this.numberDelResouce = numberDelResouce;
		fullQualifiedName = new FullQualifiedName(NAMESPACE, numberDelResouce.toString());
	}

	@Override
	public EntityType getEntityType() {

		logger.info("getEntityType ALL_TAB_COLS");
		logger.info("numberDelResouce: "+numberDelResouce);
		List<AllTabCols> allColsList = colsService.findByTableName(numberDelResouce);
		List<Property> columnList =  new ArrayList <Property>();
		// create EntityType properties
		int i;
		String typeCol="";
		for(i=0;i<allColsList.size();i++)
		{
			typeCol=allColsList.get(i).getDATA_TYPE().toString();
			logger.info("************************************/ allColsList.get("+i+") /***********************************");

			if (typeCol.equals("NUMBER")){
				logger.info("typeCol.equals == NUMBER" );
				columnList.add(new Property().setName(allColsList.get(i).getCOLUMN_NAME().toString()).setType(
						EdmPrimitiveTypeKind.Int32.getFullQualifiedName()));
			}
			if (typeCol.equals("VARCHAR2")){
				logger.info("typeCol.equals == VARCHAR2" );
				columnList.add( new Property().setName(allColsList.get(i).getCOLUMN_NAME().toString()).setType(
						EdmPrimitiveTypeKind.String.getFullQualifiedName()));
			}
			if (typeCol.equals("DATE")){
				logger.info("typeCol.equals == DATE" );
				columnList.add( new Property().setName(allColsList.get(i).getCOLUMN_NAME().toString()).setType(
						EdmPrimitiveTypeKind.Date.getFullQualifiedName()));
			}
			if (typeCol.equals("CHAR")){
				logger.info(" typeCol.equals == CHAR" );
				columnList.add( new Property().setName(allColsList.get(i).getCOLUMN_NAME().toString()).setType(
						EdmPrimitiveTypeKind.String.getFullQualifiedName()));
			}
		}/*End For*/

		// create PropertyRef for Key element
		PropertyRef propertyRef = new PropertyRef();
		//propertyRef.setPropertyName("COLUMN_ID");
		propertyRef.setPropertyName(columnList.get(0).getName().toString());
		logger.info("columnList.get(0).getName().toString():  "+columnList.get(0).getName().toString());
		// configure EntityType
		EntityType entityType = new EntityType();
		entityType.setName(numberDelResouce.toString());
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

		logger.info("getEntitySetAllTabCols");

		List<UriResource> resourcePaths = uriInfo.getUriResourceParts();
		logger.info("resourcePaths.get(0)"+resourcePaths.get(0));
		//logger.info("resourcePaths.get(1)"+resourcePaths.get(1));
		UriResourceEntitySet uriResourceEntitySet = (UriResourceEntitySet) resourcePaths
				.get(0); // in our example, the first segment is the EntitySet

		EdmEntitySet edmEntitySet = uriResourceEntitySet.getEntitySet();

		EntitySet entitySet = getData(edmEntitySet);

		return entitySet;
	}

	/**
	 * Helper method for providing some sample data.
	 *
	 * @param edmEntitySet for which the data is requested
	 * @return data of requested entity set
	 */
	private EntitySet getData(EdmEntitySet edmEntitySet) {

		// Get the columns...
		List<AllTabCols> colsList = colsService.findByTableName(numberDelResouce);
		StringBuffer columns = new StringBuffer();
		int colNum;
		for(colNum = 0; colNum < colsList.size(); colNum++) {
			if  (columns.length() == 0)
				columns.append(colsList.get(colNum).getCOLUMN_NAME());
			else {
				columns.append(", ").append(colsList.get(colNum).getCOLUMN_NAME());
			}
		}
		logger.debug("Table: "+numberDelResouce);
		logger.debug("Columns: "+columns);
		logger.debug("No of columns: "+colNum);


		// Consultamos Base de datos pasando Tabla y Columnas como parametros
		EntitySet entitySet = new EntitySetImpl();
		List<Entity> entityList = entitySet.getEntities();
		List<FilaGenerico> gnericRow =  colsService.findAllRows(columns.toString(), numberDelResouce);

		for(int record = 0; record < gnericRow.size(); record++) {
			Entity row = new EntityImpl();
			int numCol =0 ;
			while (numCol < colsList.size()) {

				String columnType = colsList.get(numCol).getDATA_TYPE();
				if (columnType.equals("NUMBER")){
					row.addProperty(new PropertyImpl(null, colsList.get(numCol).getCOLUMN_NAME(),
							ValueType.PRIMITIVE, Long.valueOf(gnericRow.get(record).getCampos().get(numCol).toString())));
				}
				else if (columnType.equals("VARCHAR2")){
					row.addProperty(new PropertyImpl(null, colsList.get(numCol).getCOLUMN_NAME(),
							ValueType.PRIMITIVE, gnericRow.get(record).getCampos().get(numCol)));
				}
				else if (columnType.equals("DATE")){
					row.addProperty(new PropertyImpl(null, colsList.get(numCol).getCOLUMN_NAME(),
							ValueType.PRIMITIVE, parseDate (gnericRow.get(record).getCampos().get(numCol))));
				}
				else if (columnType.equals("CHAR")){
					row.addProperty(new PropertyImpl(null, colsList.get(numCol).getCOLUMN_NAME(),
								ValueType.PRIMITIVE, gnericRow.get(record).getCampos().get(numCol)));
				}
				logger.debug("gnericRow.get("+record+").getCampos().get("+numCol+") "+columnType+" "+ gnericRow.get(record).getCampos().get(numCol));
				numCol++;
			}
			entityList.add(row);
		}

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
		return  numberDelResouce;
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