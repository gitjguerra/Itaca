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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Rommel Vega
 *
 */
@Component
public class DynEntityProvider implements EntityProvider {

	/**
	 * Logger

	 */
	private static Logger logger = Logger.getLogger(DynEntityProvider.class);

	// Service Namespace
	public static final String NAMESPACE = "com.csi.itaca.dataview";

	// EDM Container
	public static final String CONTAINER_NAME = "Container";
	public static final FullQualifiedName CONTAINER = new FullQualifiedName(
			NAMESPACE, CONTAINER_NAME);

	// Entity Types Names
	public String numberDelResouce;
	public FullQualifiedName fullQualifiedName;

	// Entity Set Names
	//public static final String ES_ALL_TAB_COLS_NAME = "Tablas";

	@Autowired
	private JdbcTemplate jdbcTemplate;

    @Autowired
    private AllTabColsRepository colsService;

    public void setResourceName(String numberDelResouce) {
    	this.numberDelResouce = numberDelResouce;
		fullQualifiedName = new FullQualifiedName(NAMESPACE, numberDelResouce.toString());
	}
	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.rohitghatol.spring.odata.edm.providers.EntityProvider#getEntityType()
	 */

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
		logger.info("getDataAllTabCols");
		EntitySet entitySet = new EntitySetImpl();
		List<Entity> entityList = entitySet.getEntities();
		/*Consulta por nombre de tabla*/
		List<AllTabCols> ColsList = colsService.findByTableName(numberDelResouce);
		int colNum;
		String Columnas ="";

			for(colNum= 0; colNum < ColsList.size(); colNum++) {
				if  (Columnas=="")
						Columnas =  ColsList.get(colNum).getCOLUMN_NAME();
				else {
					 Columnas = Columnas+", "+ ColsList.get(colNum).getCOLUMN_NAME();
				}
				logger.info("Columnas SQL: "+ColsList.get(colNum).getCOLUMN_NAME().toString());
			}/*End For*/
			logger.info("Conteo colNum: "+colNum);
		// Consultamos Base de datos pasando Tabla y Columnas como parametros
		List<FilaGenerico> gnericRow =  colsService.findAllRows(Columnas, numberDelResouce.toString());
		int filas;
		int Columns;
		for(filas = 0; filas < gnericRow.size(); filas++) {
			logger.info("************************************/ gnericRow.get("+filas+") /***********************************");

			Entity row = new EntityImpl();
			int numCol =0 ;
			String typeCol="";
			while (numCol <ColsList.size()) {
				typeCol=ColsList.get(numCol).getDATA_TYPE().toString();

					if (typeCol.equals("NUMBER")){
						logger.info("typeCol.equals == NUMBER" );
						row.addProperty(new PropertyImpl(null, ColsList.get(numCol).getCOLUMN_NAME(),
								ValueType.PRIMITIVE, (Long.valueOf(gnericRow.get(filas).getCampos().get(numCol).toString()))));
					}
					if (typeCol.equals("VARCHAR2")){
						logger.info("typeCol.equals == VARCHAR2" );
						row.addProperty(new PropertyImpl(null, ColsList.get(numCol).getCOLUMN_NAME(),
								ValueType.PRIMITIVE, gnericRow.get(filas).getCampos().get(numCol)));
					}
					if (typeCol.equals("DATE")){
						logger.info("typeCol.equals == DATE" );
						row.addProperty(new PropertyImpl(null, ColsList.get(numCol).getCOLUMN_NAME(),
								ValueType.PRIMITIVE, gnericRow.get(filas).getCampos().get(numCol)));
					}
					if (typeCol.equals("CHAR")){
						logger.info(" typeCol.equals == CHAR" );
							row.addProperty(new PropertyImpl(null, ColsList.get(numCol).getCOLUMN_NAME(),
									ValueType.PRIMITIVE, gnericRow.get(filas).getCampos().get(numCol)));
					}
				logger.info(" gnericRow.get("+filas+").getCampos().get("+numCol+") "+ gnericRow.get(filas).getCampos().get(numCol));
				typeCol="";
				numCol++;
			}/* End While*/
			entityList.add(row);
		}/* End For*/

		return entitySet;
	}/*End EntitySet*/

	@Override
	public String getEntitySetName() {

		logger.info("getEntitySetName allTabColskList");

		//return ES_ALL_TAB_COLS_NAME;
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