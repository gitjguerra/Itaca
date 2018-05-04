/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.csi.itaca.dataview.edm;



import com.csi.itaca.dataview.model.dao.AllTabColsRepository;
import com.csi.itaca.dataview.service.EntityProvider;
import org.apache.olingo.commons.api.ODataException;
import org.apache.olingo.commons.api.edm.FullQualifiedName;
import org.apache.olingo.server.api.edm.provider.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
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
	private String recursoURI;

	// Service Namespace
	public static final String NAMESPACE = "com.itaca.dataview";

	// EDM Container
	public static final String CONTAINER_NAME = "Container";
	public static final FullQualifiedName CONTAINER = new FullQualifiedName(
			NAMESPACE, CONTAINER_NAME);

	@Override
	public List<Schema> getSchemas() throws ODataException {

		// create Schema
		Schema schema = new Schema();
		schema.setNamespace(NAMESPACE);

		Map<String, EntityProvider> entityProviders = getEntityProviders();

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
	public EntityType getEntityType(FullQualifiedName entityTypeName)
			throws ODataException {

		EntityType result = null;
		Map<String, EntityProvider> entityProviders = getEntityProviders();

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
	public EntitySet getEntitySet(FullQualifiedName entityContainer,
			String entitySetName) throws ODataException {
		recursoURI =entitySetName;
		EntitySet result = null;
		Map<String, EntityProvider> entityProviders = getEntityProviders();

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

		Map<String, EntityProvider> entityProviders = getEntityProviders();
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

	public  Map<String, EntityProvider> getEntityProviders() {
		Map<String, EntityProvider> entityProviders = new HashMap<>();

		DynEntityProvider dynEntityProvider = new DynEntityProvider();

		dynEntityProvider.setResourceName(recursoURI);
		dynEntityProvider.setJdbcTemplate(jdbcTemplate);
		dynEntityProvider.setColsService(colsService);
		entityProviders.put(recursoURI, dynEntityProvider);
		/*dynEntityProvider = new DynEntityProvider();
		dynEntityProvider.setResourceName( "Product");
		entityProviders.put("Product", dynEntityProvider);*/
	return entityProviders;
	}


	@Override
	public EntityContainerInfo getEntityContainerInfo(
			FullQualifiedName entityContainerName) throws ODataException {

		// This method is invoked when displaying the service document at e.g.
		// http://localhost:8080/DemoService/DemoService.svc
		if (entityContainerName == null
				|| entityContainerName.equals(CONTAINER)) {
			EntityContainerInfo entityContainerInfo = new EntityContainerInfo();
			entityContainerInfo.setContainerName(CONTAINER);
			return entityContainerInfo;
		}

		return null;
	}
}
