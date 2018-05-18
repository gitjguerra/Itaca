package com.csi.itaca.dataview.edm;

import com.csi.itaca.dataview.DataViewConfiguration;
import com.csi.itaca.dataview.service.AllTabColsRepository;
import com.csi.itaca.dataview.service.EntityProvider;
import org.apache.olingo.commons.api.data.ContextURL;
import org.apache.olingo.commons.api.data.EntitySet;
import org.apache.olingo.commons.api.edm.EdmEntitySet;
import org.apache.olingo.commons.api.edm.EdmEntityType;
import org.apache.olingo.commons.api.format.ContentType;
import org.apache.olingo.commons.api.format.ODataFormat;
import org.apache.olingo.commons.api.http.HttpHeader;
import org.apache.olingo.commons.api.http.HttpStatusCode;
import org.apache.olingo.server.api.*;
import org.apache.olingo.server.api.processor.EntityCollectionProcessor;
import org.apache.olingo.server.api.serializer.EntityCollectionSerializerOptions;
import org.apache.olingo.server.api.serializer.ODataSerializer;
import org.apache.olingo.server.api.serializer.SerializerException;
import org.apache.olingo.server.api.uri.UriInfo;
import org.apache.olingo.server.api.uri.UriResource;
import org.apache.olingo.server.api.uri.UriResourceEntitySet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;
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

		// 1st we have retrieve the requested EntitySet from the uriInfo object
		// (representation of the parsed service URI)
		List<UriResource> resourcePaths = uriInfo.getUriResourceParts();

		// in our example, the first segment is the EntitySet
		UriResourceEntitySet uriResourceEntitySet = (UriResourceEntitySet) resourcePaths.get(0);
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
	 * @param uriInfo for which the data is requested
	 * @return data of requested entity set
	 */
	private EntitySet getData(UriInfo uriInfo) {

		List<UriResource> resourcePaths = uriInfo.getUriResourceParts();
		// in our example, the first segment is the EntitySet
		UriResourceEntitySet uriResourceEntitySet = (UriResourceEntitySet) resourcePaths.get(0);

		EdmEntitySet edmEntitySet = uriResourceEntitySet.getEntitySet();

		EntitySet entitySet = null;

		Map<String, EntityProvider> entityProviders = configuration.getEntityProviders(jdbcTemplate, colsService);

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


}
