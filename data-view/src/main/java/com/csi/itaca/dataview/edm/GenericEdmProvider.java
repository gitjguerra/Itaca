package com.csi.itaca.dataview.edm;

import com.csi.itaca.dataview.service.AllTabColsRepository;
import com.csi.itaca.dataview.DataViewConfiguration;
import com.csi.itaca.dataview.service.EntityProvider;
import org.apache.olingo.commons.api.ODataException;
import org.apache.olingo.commons.api.edm.FullQualifiedName;
import org.apache.olingo.server.api.edm.provider.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Component
public class GenericEdmProvider extends EdmProvider {

	@Autowired
	private ApplicationContext ctx;

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
	public List<Schema> getSchemas() throws ODataException {

		// create Schema
		Schema schema = new Schema();
		schema.setNamespace(NAMESPACE);

		Map<String, EntityProvider> entityProviders = configuration.getEntityProviders(jdbcTemplate, colsService);

		// add EntityTypes
		List<EntityType> entityTypes = new ArrayList<EntityType>();
		for (String entity : entityProviders.keySet()) {

			EntityProvider entityProvider = entityProviders.get(entity);
			entityTypes.add(entityProvider.getEntityType());

		}

		schema.setEntityTypes(entityTypes);

		// add EntityContainer
		schema.setEntityContainer(getEntityContainer());

		// finally
		List<Schema> schemas = new ArrayList<Schema>();
		schemas.add(schema);

		return schemas;
	}

	@Override
	public EntityType getEntityType(FullQualifiedName entityTypeName) throws ODataException {

		EntityType result = null;
		Map<String, EntityProvider> entityProviders = configuration.getEntityProviders(jdbcTemplate, colsService);

		for (String entity : entityProviders.keySet()) {

			EntityProvider entityProvider = entityProviders.get(entity);
			EntityType entityType = entityProvider.getEntityType();
			if (entityType.getName().equals(entityTypeName.getName())) {
				result = entityType;
				break;
			}

		}
		return result;

	}

	@Override
	public EntitySet getEntitySet(FullQualifiedName entityContainer, String entitySetName) throws ODataException {
		EntitySet result = null;
		Map<String, EntityProvider> entityProviders = configuration.getEntityProviders(jdbcTemplate, colsService);

		for (String entity : entityProviders.keySet()) {

			EntityProvider entityProvider = entityProviders.get(entity);
			EntityType entityType = entityProvider.getEntityType();
			if (entityProvider.getEntitySetName().equals(entitySetName)) {
				result = new EntitySet();
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
	public EntityContainer getEntityContainer() throws ODataException {

		// create EntitySets
		List<EntitySet> entitySets = new ArrayList<EntitySet>();
		
		//Map<String, EntityProvider> entityProviders = ctx.getBeansOfType(EntityProvider.class);

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
		EntityContainer entityContainer = new EntityContainer();
		entityContainer.setName(CONTAINER_NAME);
		entityContainer.setEntitySets(entitySets);

		return entityContainer;
	}

	@Override
	public EntityContainerInfo getEntityContainerInfo(FullQualifiedName entityContainerName) throws ODataException {

		if (entityContainerName == null  || entityContainerName.equals(CONTAINER)) {
			EntityContainerInfo entityContainerInfo = new EntityContainerInfo();
			entityContainerInfo.setContainerName(CONTAINER);
			return entityContainerInfo;
		}

		return null;
	}
}
