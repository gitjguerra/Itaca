package com.csi.itaca.dataview.edm;

import com.csi.itaca.dataview.DataViewConfiguration;
import com.csi.itaca.dataview.service.AllTabColsRepository;
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
import org.apache.olingo.server.api.uri.queryoption.FilterOption;
import org.apache.olingo.server.api.uri.queryoption.expression.Expression;
import org.apache.olingo.server.api.uri.queryoption.expression.ExpressionVisitException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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

		// 2nd and Half: Check if filter system query option is provided and apply the expression if necessary
		FilterOption filterOption = uriInfo.getFilterOption();
		if(filterOption != null) {
			// Apply $filter system query option
			List<Entity> entityList = entitySet.getEntities();
			Iterator<Entity> entityIterator = entityList.iterator();

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
		//********************************************************************************

		// 3rd: create a serializer based on the requested format (json)
		ODataSerializer serializer = odata.createSerializer(responseFormat);

		// 4th: Now serialize the content: transform from the EntitySet object to InputStream
		EdmEntityType edmEntityType = edmEntitySet.getEntityType();
		ContextURL contextUrl = ContextURL.with().entitySet(edmEntitySet).build();

		final String id = request.getRawBaseUri() + "/" + edmEntitySet.getName();
		EntityCollectionSerializerOptions opts =
				EntityCollectionSerializerOptions.with().id(id).contextURL(contextUrl).build();
		SerializerResult serializedContent = serializer.entityCollection(serviceMetadata, edmEntityType, entitySet, opts);

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
		return entityCollection;
	}


}
