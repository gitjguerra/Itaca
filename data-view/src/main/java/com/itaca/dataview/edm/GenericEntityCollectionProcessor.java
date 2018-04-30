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
package com.itaca.dataview.edm;

import com.itaca.dataview.model.dao.AllTabColsRepository;
import com.itaca.dataview.service.EntityProvider;
import org.apache.olingo.commons.api.data.ContextURL;
import org.apache.olingo.commons.api.data.EntitySet;
import org.apache.olingo.commons.api.edm.EdmEntitySet;
import org.apache.olingo.commons.api.edm.EdmEntityType;
import org.apache.olingo.commons.api.format.ContentType;
import org.apache.olingo.commons.api.format.ODataFormat;
import org.apache.olingo.commons.api.http.HttpHeader;
import org.apache.olingo.commons.api.http.HttpStatusCode;
import org.apache.olingo.server.api.*;
import org.apache.olingo.server.api.deserializer.DeserializerException;
import org.apache.olingo.server.api.processor.EntityCollectionProcessor;
import org.apache.olingo.server.api.serializer.EntityCollectionSerializerOptions;
import org.apache.olingo.server.api.serializer.ODataSerializer;
import org.apache.olingo.server.api.serializer.SerializerException;
import org.apache.olingo.server.api.uri.UriInfo;
import org.apache.olingo.server.api.uri.UriParameter;
import org.apache.olingo.server.api.uri.UriResource;
import org.apache.olingo.server.api.uri.UriResourceEntitySet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// TODO: Auto-generated Javadoc

/**
 * The Class ProductEntityCollectionProcessor.
 */
@Component
public class GenericEntityCollectionProcessor implements
		EntityCollectionProcessor {

	@Autowired
	private ApplicationContext ctx;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private AllTabColsRepository colsService;

	/** The odata. */
	private OData odata;
	private String recursoURI;
	// our processor is initialized with the OData context object
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.olingo.server.api.processor.Processor#init(org.apache.olingo
	 * .server.api.OData, org.apache.olingo.server.api.ServiceMetadata)
	 */
	public void init(OData odata, ServiceMetadata serviceMetadata) {
		this.odata = odata;
	}

	// the only method that is declared in the EntityCollectionProcessor
	// interface
	// this method is called, when the user fires a request to an EntitySet
	// in our example, the URL would be:
	// http://localhost:8080/ExampleService1/ExampleServlet1.svc/Products
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.olingo.server.api.processor.EntityCollectionProcessor#
	 * readEntityCollection(org.apache.olingo.server.api.ODataRequest,
	 * org.apache.olingo.server.api.ODataResponse,
	 * org.apache.olingo.server.api.uri.UriInfo,
	 * org.apache.olingo.commons.api.format.ContentType)
	 */
	public void readEntityCollection(ODataRequest request,
			ODataResponse response, UriInfo uriInfo, ContentType responseFormat)
			throws ODataApplicationException, SerializerException {

		// 1st we have retrieve the requested EntitySet from the uriInfo object
		// (representation of the parsed service URI)
		List<UriResource> resourcePaths = uriInfo.getUriResourceParts();
		UriResourceEntitySet uriResourceEntitySet = (UriResourceEntitySet) resourcePaths
				.get(0); // in our example, the first segment is the EntitySet
        recursoURI = uriResourceEntitySet.toString();
		EdmEntitySet edmEntitySet = uriResourceEntitySet.getEntitySet();

		// 2nd: fetch the data from backend for this requested EntitySetName //
		// it has to be delivered as EntitySet object
		EntitySet entitySet = getData(uriInfo);

		// 3rd: create a serializer based on the requested format (json)
		ODataFormat format = ODataFormat.fromContentType(responseFormat);
		ODataSerializer serializer = odata.createSerializer(format);

		// 4th: Now serialize the content: transform from the EntitySet object
		// to InputStream
		EdmEntityType edmEntityType = edmEntitySet.getEntityType();
		ContextURL contextUrl = ContextURL.with().entitySet(edmEntitySet)
				.build();

		EntityCollectionSerializerOptions opts = EntityCollectionSerializerOptions
				.with().contextURL(contextUrl).build();
		InputStream serializedContent = serializer.entityCollection(
				edmEntityType, entitySet, opts);

		// Finally: configure the response object: set the body, headers and
		// status code
		response.setContent(serializedContent);
		response.setStatusCode(HttpStatusCode.OK.getStatusCode());
		response.setHeader(HttpHeader.CONTENT_TYPE,
				responseFormat.toContentTypeString());
	}

	/**
	 * Helper method for providing some sample data.
	 *
	 * @param uriInfo
	 *            for which the data is requested
	 * @return data of requested entity set
	 */
	private EntitySet getData(UriInfo uriInfo) {
		List<UriResource> resourcePaths = uriInfo.getUriResourceParts();
		UriResourceEntitySet uriResourceEntitySet = (UriResourceEntitySet) resourcePaths
				.get(0); // in our example, the first segment is the EntitySet
		EdmEntitySet edmEntitySet = uriResourceEntitySet.getEntitySet();

		EntitySet entitySet = null;

		//Map<String, EntityProvider> entityProviders = ctx.getBeansOfType(EntityProvider.class);
		Map<String, EntityProvider> entityProviders = getEntityProviders();

		for (String entity : entityProviders.keySet()) {
			EntityProvider entityProvider = entityProviders.get(entity);
			if (entityProvider
					.getEntityType().getName()
					
					.equals(edmEntitySet.getEntityType().getName())) {
				entitySet = entityProvider.getEntitySet(uriInfo);
				break;
			}
		}
		return entitySet;
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


	public void createEntity(ODataRequest request, ODataResponse response, UriInfo uriInfo,
							 ContentType requestFormat, ContentType responseFormat)
			throws ODataApplicationException, DeserializerException, SerializerException {
		/*
		// 1. Retrieve the entity type from the URI
		EdmEntitySet edmEntitySet = Util.getEdmEntitySet(uriInfo);
		EdmEntityType edmEntityType = edmEntitySet.getEntityType();

		// 2. create the data in backend
		// 2.1. retrieve the payload from the POST request for the entity to create and deserialize it
		InputStream requestInputStream = request.getBody();
		ODataDeserializer deserializer = odata.createDeserializer(requestFormat);
		DeserializerResult result = deserializer.entity(requestInputStream, edmEntityType);
		Entity requestEntity = result.getEntity();
		// 2.2 do the creation in backend, which returns the newly created entity
		Entity createdEntity = null;

		try {
			/*storage.beginTransaction();
			createdEntity = storage.createEntityData(edmEntitySet, requestEntity, request.getRawBaseUri());
			storage.commitTransaction();*/
		/*} catch( ODataApplicationException e ) {
			storage.rollbackTransaction();
			throw e;
		}
		*/
		// 3. serialize the response (we have to return the created entity)
		/*ContextURL contextUrl = ContextURL.with().entitySet(edmEntitySet).build();
		EntitySerializerOptions options = EntitySerializerOptions.with().contextURL(contextUrl).build(); // expand and select currently not supported

		ODataSerializer serializer = odata.createSerializer(responseFormat);
		SerializerResult serializedResponse = serializer.entity(serviceMetadata, edmEntityType, createdEntity, options);

		//4. configure the response object
		response.setContent(serializedResponse.getContent());
		response.setStatusCode(HttpStatusCode.CREATED.getStatusCode());
		response.setHeader(HttpHeader.CONTENT_TYPE, responseFormat.toContentTypeString());*/
	}

	public void updateEntity(ODataRequest request, ODataResponse response, UriInfo uriInfo,
							 ContentType requestFormat, ContentType responseFormat)
			throws ODataApplicationException, DeserializerException, SerializerException {

		// 1. Retrieve the entity set which belongs to the requested entity
		List<UriResource> resourcePaths = uriInfo.getUriResourceParts();
		// Note: only in our example we can assume that the first segment is the EntitySet
		UriResourceEntitySet uriResourceEntitySet = (UriResourceEntitySet) resourcePaths.get(0);
		EdmEntitySet edmEntitySet = uriResourceEntitySet.getEntitySet();
		EdmEntityType edmEntityType = edmEntitySet.getEntityType();

		// 2. update the data in backend
		// 2.1. retrieve the payload from the PUT request for the entity to be updated
		InputStream requestInputStream = request.getBody();
		//ODataDeserializer deserializer = odata.createDeserializer(requestFormat);

		/*DeserializerResult result = deserializer.entity(requestInputStream, edmEntityType);
		Entity requestEntity = result.getEntity();
		// 2.2 do the modification in backend
		List<UriParameter> keyPredicates = uriResourceEntitySet.getKeyPredicates();
		// Note that this updateEntity()-method is invoked for both PUT or PATCH operations
		HttpMethod httpMethod = request.getMethod();
		storage.updateEntityData(edmEntitySet, keyPredicates, requestEntity, httpMethod);
			*/
		//3. configure the response object
		response.setStatusCode(HttpStatusCode.NO_CONTENT.getStatusCode());
	}


	public void deleteEntity(ODataRequest request, ODataResponse response, UriInfo uriInfo)
			throws ODataApplicationException {

		// 1. Retrieve the entity set which belongs to the requested entity
		List<UriResource> resourcePaths = uriInfo.getUriResourceParts();
		// Note: only in our example we can assume that the first segment is the EntitySet
		UriResourceEntitySet uriResourceEntitySet = (UriResourceEntitySet) resourcePaths.get(0);
		EdmEntitySet edmEntitySet = uriResourceEntitySet.getEntitySet();

		// 2. delete the data in backend
		List<UriParameter> keyPredicates = uriResourceEntitySet.getKeyPredicates();
		//storage.deleteEntityData(edmEntitySet, keyPredicates);

		//3. configure the response object
		response.setStatusCode(HttpStatusCode.NO_CONTENT.getStatusCode());
	}

}
