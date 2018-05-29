package com.csi.itaca.dataview.edm;

import com.csi.itaca.dataview.service.AllTabColsRepository;
import com.csi.itaca.dataview.DataViewConfiguration;
import com.csi.itaca.dataview.service.EntityProvider;
import org.apache.olingo.commons.api.edm.FullQualifiedName;
import org.apache.olingo.commons.api.edm.provider.*;
import org.apache.olingo.commons.api.ex.ODataException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Component
public class GenericEdmProvider extends CsdlAbstractEdmProvider {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private AllTabColsRepository colsService;

	@Autowired
	private DataViewConfiguration configuration;

	// Service Namespace
	public static final String NAMESPACE = "com.itaca.dataview";

	// EDM Container
	public static final String CONTAINER_NAME = "Container";

	public static final FullQualifiedName CONTAINER = new FullQualifiedName(NAMESPACE, CONTAINER_NAME);

	@Override
	public List<CsdlSchema> getSchemas() {

		// create Schema
		CsdlSchema schema = new CsdlSchema();
		schema.setNamespace(NAMESPACE);

		Map<String, EntityProvider> entityProviders = configuration.getEntityProviders(jdbcTemplate, colsService);

		// add EntityTypes
		List<CsdlEntityType> entityTypes = new ArrayList<CsdlEntityType>();
		for (String entity : entityProviders.keySet()) {

			EntityProvider entityProvider = entityProviders.get(entity);
			entityTypes.add(entityProvider.getEntityType());

		}

		schema.setEntityTypes(entityTypes);

		// add EntityContainer
		schema.setEntityContainer(getEntityContainer());

		// finally
		List<CsdlSchema> schemas = new ArrayList<CsdlSchema>();
		schemas.add(schema);

		return schemas;
	}

	@Override
	public CsdlEntityType getEntityType(FullQualifiedName entityTypeName) throws ODataException {

		CsdlEntityType result = null;
		Map<String, EntityProvider> entityProviders = configuration.getEntityProviders(jdbcTemplate, colsService);

		for (String entity : entityProviders.keySet()) {

			EntityProvider entityProvider = entityProviders.get(entity);
			CsdlEntityType entityType = entityProvider.getEntityType();
			if (entityType.getName().equals(entityTypeName.getName())) {
				result = entityType;
				break;
			}

		}
		return result;

	}

	@Override
	public CsdlEntitySet getEntitySet(FullQualifiedName entityContainer, String entitySetName) {
		CsdlEntitySet result = null;

		Map<String, EntityProvider> entityProviders = configuration.getEntityProviders(jdbcTemplate, colsService);
		for (String entity : entityProviders.keySet()) {
			EntityProvider entityProvider = entityProviders.get(entity);
			if (entityProvider.getEntitySetName().equals(entitySetName)) {
				result = new CsdlEntitySet();
				result.setName(entityProvider.getEntitySetName());
				result.setType(entityProvider.getFullyQualifiedName());
				break;
			}
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.olingo.server.api.edm.provider.EdmProvider#getEntityContainer
	 * ()
	 */
	@Override
	public CsdlEntityContainer getEntityContainer()  {

		// create EntitySets
		List<CsdlEntitySet> entitySets = new ArrayList<CsdlEntitySet>();
		

		Map<String, EntityProvider> entityProviders = configuration.getEntityProviders(jdbcTemplate, colsService);
		/*Map<String, String> map = new HashMap<String, String>();
		map.put("NAme Resource", "TABleNAME");
		map.put("key2", "value2");
		map.put("key3", "value3");*/
		for (String entity : entityProviders.keySet()) {
			EntityProvider entityProvider = entityProviders.get(entity);
			entitySets.add(getEntitySet(CONTAINER, entityProvider.getEntitySetName()));
		}

		// create EntityContainer
		CsdlEntityContainer entityContainer = new CsdlEntityContainer();
		entityContainer.setName(CONTAINER_NAME);
		entityContainer.setEntitySets(entitySets);

		return entityContainer;
	}

	@Override
	public CsdlEntityContainerInfo getEntityContainerInfo(FullQualifiedName entityContainerName) throws ODataException {

		if (entityContainerName == null  || entityContainerName.equals(CONTAINER)) {
			CsdlEntityContainerInfo entityContainerInfo = new CsdlEntityContainerInfo();
			entityContainerInfo.setContainerName(CONTAINER);
			return entityContainerInfo;
		}

		return null;
	}
}
