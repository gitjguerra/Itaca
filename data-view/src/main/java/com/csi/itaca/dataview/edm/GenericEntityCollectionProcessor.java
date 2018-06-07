package com.csi.itaca.dataview.edm;

import com.csi.itaca.common.GlobalConstants;
import com.csi.itaca.dataview.DataViewConfiguration;
import com.csi.itaca.dataview.model.dto.AuditDTO;
import com.csi.itaca.dataview.service.AllTabColsRepository;
import com.csi.itaca.dataview.service.DataViewManagementServiceImpl;
import com.csi.itaca.dataview.service.EntityProvider;
import com.csi.itaca.dataview.service.FilterExpressionVisitor;
import org.apache.olingo.commons.api.data.ContextURL;
import org.apache.olingo.commons.api.data.Entity;
import org.apache.olingo.commons.api.data.EntityCollection;
import org.apache.olingo.commons.api.edm.EdmEntitySet;
import org.apache.olingo.commons.api.edm.EdmEntityType;
import org.apache.olingo.commons.api.format.ContentType;
import org.apache.olingo.commons.api.http.HttpHeader;
import org.apache.olingo.commons.api.http.HttpStatusCode;
import org.apache.olingo.server.api.*;
import org.apache.olingo.server.api.processor.EntityCollectionProcessor;
import org.apache.olingo.server.api.serializer.EntityCollectionSerializerOptions;
import org.apache.olingo.server.api.serializer.ODataSerializer;
import org.apache.olingo.server.api.serializer.SerializerException;
import org.apache.olingo.server.api.serializer.SerializerResult;
import org.apache.olingo.server.api.uri.UriInfo;
import org.apache.olingo.server.api.uri.UriResource;
import org.apache.olingo.server.api.uri.UriResourceEntitySet;
import org.apache.olingo.server.api.uri.queryoption.*;
import org.apache.olingo.server.api.uri.queryoption.expression.Expression;
import org.apache.olingo.server.api.uri.queryoption.expression.ExpressionVisitException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * The Class ProductEntityCollectionProcessor.
 */
@Component
public class GenericEntityCollectionProcessor implements EntityCollectionProcessor {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private AllTabColsRepository colsService;
	@Autowired
	private DataViewConfiguration configuration;
	@Autowired
	DataViewManagementServiceImpl dataView;

	/** The odata. */
	private OData odata;
	private ServiceMetadata serviceMetadata;

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
		this.serviceMetadata = serviceMetadata;
	}
	/**
	 * The only method that is declared in the EntityCollectionProcessor interface
	 * this method is called, when the user fires a request to an EntitySet.
	 * An example the URL would be:
	 * http://http://localhost:8081/itaca/odata/USR_USER
	 * @param request
	 * @param response
	 * @param uriInfo
	 * @param responseFormat
	 * @throws ODataApplicationException
	 * @throws SerializerException
	 */
	@Override
	public void readEntityCollection(ODataRequest request, ODataResponse response, UriInfo uriInfo,
									 ContentType responseFormat) throws ODataApplicationException, SerializerException {

		// 1st we have retrieve the requested EntitySet from the uriInfo object (representation of the parsed service URI)
		List<UriResource> resourcePaths = uriInfo.getUriResourceParts();
		UriResourceEntitySet uriResourceEntitySet = (UriResourceEntitySet) resourcePaths.get(0); // in our example, the first segment is the EntitySet
		EdmEntitySet edmEntitySet = uriResourceEntitySet.getEntitySet();

		// 2nd: fetch the data from backend for this requested EntitySetName // it has to be delivered as EntitySet object
		EntityCollection entitySet = getData(edmEntitySet, uriInfo);

		// 2nd and Half: apply System Query Options
		// modify the result set according to the query options, specified by the end user
		List<Entity> entityList = entitySet.getEntities();
		EntityCollection returnEntityCollection = new EntityCollection();

		// 3rd if necessary: Check if filter system query option is provided and apply the expression if necessary
		FilterOption filterOption = uriInfo.getFilterOption();
		if(filterOption != null) {
			// Apply $filter system query option
			List<Entity> entityListIterator = entitySet.getEntities();
			Iterator<Entity> entityIterator = entityListIterator.iterator();

			// Evaluate the expression for each entity
			// If the expression is evaluated to "true", keep the entity otherwise remove it from the entityList
			while (entityIterator.hasNext()) {
				// To evaluate the the expression, create an instance of the Filter Expression Visitor and pass
				// the current entity to the constructor
				Entity currentEntity = entityIterator.next();
				Expression filterExpression = filterOption.getExpression();
				FilterExpressionVisitor expressionVisitor = new FilterExpressionVisitor(currentEntity);

				// Start evaluating the expression
				Object visitorResult = null;
				try {
					visitorResult = filterExpression.accept(expressionVisitor);
				} catch (ExpressionVisitException e) {
					e.printStackTrace();
				}

				// The result of the filter expression must be of type Edm.Boolean
				if(visitorResult instanceof Boolean) {
					if(!Boolean.TRUE.equals(visitorResult)) {
						// The expression evaluated to false (or null), so we have to remove the currentEntity from entityList
						entityIterator.remove();
					}
				} else {
					throw new ODataApplicationException("A filter expression must evaulate to type Edm.Boolean",
							HttpStatusCode.BAD_REQUEST.getStatusCode(), Locale.ENGLISH);
				}
			}

		}

			// implement $count
			CountOption countOption = uriInfo.getCountOption();
			if (countOption != null) {
				boolean isCount = countOption.getValue();
				if(isCount){
					returnEntityCollection.setCount(entityList.size());
				}
			}

			// handle $skip
			SkipOption skipOption = uriInfo.getSkipOption();
			if (skipOption != null) {
				int skipNumber = skipOption.getValue();
				if (skipNumber >= 0) {
					if(skipNumber <= entityList.size()) {
						entityList = entityList.subList(skipNumber, entityList.size());
					} else {
						// The client skipped all entities
						entityList.clear();
					}
				} else {
					throw new ODataApplicationException("Invalid value for $skip", HttpStatusCode.BAD_REQUEST.getStatusCode(), Locale.ROOT);
				}
			}

			// handle $top
			TopOption topOption = uriInfo.getTopOption();
			if (topOption != null) {
				int topNumber = topOption.getValue();
				if (topNumber >= 0) {
					if(topNumber <= entityList.size()) {
						entityList = entityList.subList(0, topNumber);
					}  // else the client has requested more entities than available => return what we have
				} else {
					throw new ODataApplicationException("Invalid value for $top", HttpStatusCode.BAD_REQUEST.getStatusCode(), Locale.ROOT);
				}
			}

			// after applying the query options, create EntityCollection based on the reduced list
			for(Entity entity : entityList){
				returnEntityCollection.getEntities().add(entity);
			}

		// 4th: apply system query options
		// Note: $select is handled by the lib, we only configure ContextURL + SerializerOptions
		// for performance reasons, it might be necessary to implement the $select manually
		SelectOption selectOption = uriInfo.getSelectOption();
		// and serialize the content: transform from the EntitySet object to InputStream
		EdmEntityType edmEntityType = edmEntitySet.getEntityType();
		// we need the property names of the $select, in order to build the context URL
		String selectList = odata.createUriHelper().buildContextURLSelectList(edmEntityType,
		null, selectOption);
		ContextURL contextUrl = ContextURL.with()
		.entitySet(edmEntitySet)
		.selectList(selectList)
		.build();

		// 5th: create a serializer based on the requested format (json)
		ODataSerializer serializer = odata.createSerializer(responseFormat);

		// 6th: Now serialize the content: transform from the EntitySet object to InputStream
		// adding the selectOption to the serializerOpts will tell the lib to do the job
		final String id = request.getRawBaseUri() + "/" + edmEntitySet.getName();
		EntityCollectionSerializerOptions opts = EntityCollectionSerializerOptions.with()
				.contextURL(contextUrl)
				.select(selectOption)
				.count(countOption)
				.id(id)
				.build();

		SerializerResult serializedContent = null;
		if(returnEntityCollection!=null){
			serializedContent = serializer.entityCollection(serviceMetadata, edmEntityType, returnEntityCollection, opts);
		}else{
			serializedContent = serializer.entityCollection(serviceMetadata, edmEntityType, entitySet, opts);
		}

		// Finally: configure the response object: set the body, headers and status code
		response.setContent(serializedContent.getContent());
		response.setStatusCode(HttpStatusCode.OK.getStatusCode());
		response.setHeader(HttpHeader.CONTENT_TYPE, responseFormat.toContentTypeString());
	}

	/**
	 * Helper method for providing some sample data.
	 * @param uriInfo for which the data is requested
	 * @return data of requested entity set
	 */
	private EntityCollection getData(EdmEntitySet edmEntitySet, UriInfo uriInfo) {

		List<UriResource> resourcePaths = uriInfo.getUriResourceParts();
		// in our example, the first segment is the EntitySet
		UriResourceEntitySet uriResourceEntitySet = (UriResourceEntitySet) resourcePaths.get(0);

//		EdmEntitySet edmEntitySet = uriResourceEntitySet.getEntitySet();

		EntityCollection entityCollection = null;

		Map<String, EntityProvider> entityProviders = configuration.getEntityProviders(jdbcTemplate, colsService);

		for (String entity : entityProviders.keySet()) {
			EntityProvider entityProvider = entityProviders.get(entity);
			if (entityProvider.getEntityType().getName().equals(edmEntitySet.getEntityType().getName())) {
				entityCollection = entityProvider.getEntitySet(uriInfo);
				break;
			}
		}

		//  <editor-fold defaultstate="collapsed" desc="*** Audit ***">
			AuditDTO dto = new AuditDTO();
			dto.setOperation(GlobalConstants.READ_PROCESS);	//  * @param operation type operation (create, update, get or delete)
			dto.setSqlCommand("Get data: " + uriInfo.getUriResourceParts());	//  * @param sqlCommand sql transact the activity
			dto.setTimeStamp(new Date());   					//  * @param timeStamp the time stamp th audit.
			// TODO: colocar el usuario actual
			dto.setUserName(GlobalConstants.DEFAULT_USER);		//  * @param userName the user produces activity
			dataView.auditTransaction(dto);
		//  </editor-fold>

		return entityCollection;
	}


}
