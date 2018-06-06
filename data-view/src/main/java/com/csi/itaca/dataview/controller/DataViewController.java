package com.csi.itaca.dataview.controller;

import java.io.IOException;
import java.nio.charset.Charset;

import com.csi.itaca.common.GlobalConstants;
import com.csi.itaca.dataview.exception.EdmException;
import com.csi.itaca.dataview.DataViewConfiguration;
import com.csi.itaca.dataview.model.dto.AuditDTO;
import com.csi.itaca.dataview.service.DataViewManagementServiceImpl;
import org.apache.log4j.Logger;
import org.apache.olingo.commons.api.edm.provider.CsdlEdmProvider;
import org.apache.olingo.commons.api.edmx.EdmxReference;
import org.apache.olingo.commons.api.http.HttpHeader;
import org.apache.olingo.commons.api.http.HttpMethod;
import org.apache.olingo.server.api.*;
import org.apache.olingo.server.api.processor.EntityCollectionProcessor;
import org.apache.olingo.server.api.processor.EntityProcessor;
import org.apache.olingo.server.api.serializer.SerializerException;
import org.apache.olingo.server.core.ODataHandlerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

// TODO: Auto-generated Javadoc

/**
 * The Class EDMController.
 *
 * @author itaca
 */
@RestController
@RequestMapping("odata")
public class DataViewController {

	/** Logger */
	private static Logger logger = Logger.getLogger(DataViewController.class);

	private static String URI = "odata/";

	/** The split. */
	private int split = 0;

	/** The ctx. */
	@Autowired
	private ApplicationContext ctx;

	/** The edm provider. */
	@Autowired
	private CsdlEdmProvider edmProvider;

	/** The enity collection processor. */
	@Autowired
	private EntityCollectionProcessor enityCollectionProcessor;

	@Autowired
	private EntityProcessor entityProcessor;

	@Autowired
	DataViewConfiguration dataViewConfiguration;

	@Autowired
	DataViewManagementServiceImpl dataView;

	/**
	 * Process.
	 *
	 * @param req the req
	 * @return the response entity
	 */
	@RequestMapping(value = "*")
	public ResponseEntity<String> process(HttpServletRequest req, HttpServletResponse res) {

		System.out.println("TableName = " + dataViewConfiguration.getTableNames());
		System.out.println("uiDisplayNameKey = " + dataViewConfiguration.getUiDisplayNameKey());
		System.out.println("uiDescriptionkey = " + dataViewConfiguration.getUiDescriptionkey());
		System.out.println("foreignTables = " + dataViewConfiguration.getForeignTables());
		System.out.println("createPermission = " + dataViewConfiguration.getCreatePermission());
		System.out.println("readPermission = " + dataViewConfiguration.getReadPermission());
		System.out.println("updatePermission = " + dataViewConfiguration.getUpdatePermission());
		System.out.println("deletePermission = " + dataViewConfiguration.getDeletePermission());

		//  <editor-fold defaultstate="collapsed" desc="*** Audit ***">
			AuditDTO dto = new AuditDTO();
			dto.setOperation(GlobalConstants.INITIAL_ACTIVITY);	//  * @param operation type operation (create, update, get or delete)
			dto.setSqlCommand(GlobalConstants.EMPTY_PROCESS);	//  * @param sqlCommand sql transact the activity
			dto.setTimeStamp(new Date());   					//  * @param timeStamp the time stamp th audit.
			dto.setUserName(GlobalConstants.DEFAULT_USER);		//  * @param userName the user produces activity
			dataView.auditTransaction(dto);
		//  </editor-fold>

		try {

			OData odata = OData.newInstance();
			ServiceMetadata edm = odata.createServiceMetadata(edmProvider,new ArrayList<EdmxReference>());

			ODataHttpHandler handler = odata.createHandler(edm);
			handler.register(enityCollectionProcessor);
			handler.register(entityProcessor);

			ODataResponse response = handler.process(createODataRequest(req,split));


			String responseStr = StreamUtils.copyToString(response.getContent(), Charset.defaultCharset());
			MultiValueMap<String, String> headers = new HttpHeaders();
			for (String key : response.getAllHeaders().keySet()) {
				headers.add(key, response.getAllHeaders().get(key).toString().replace("[","").replace("]",""));
			}

			//  <editor-fold defaultstate="collapsed" desc="*** Audit ***">
			dto = new AuditDTO();
			dto.setOperation(GlobalConstants.FINAL_ACTIVITY);	//  * @param operation type operation (create, update, get or delete)
			dto.setSqlCommand(GlobalConstants.EMPTY_PROCESS);	//  * @param sqlCommand sql transact the activity
			dto.setTimeStamp(new Date());   					//  * @param timeStamp the time stamp th audit.
			dto.setUserName(GlobalConstants.DEFAULT_USER);		//  * @param userName the user produces activity
			dataView.auditTransaction(dto);
			//  </editor-fold>

			return new ResponseEntity<>(responseStr, headers, HttpStatus.valueOf(response.getStatusCode()));
		} catch (Exception ex) {
			throw new EdmException();
		}

	}

	/**
	 * Creates the o data request.
	 *
	 * @param httpRequest the http request
	 * @param split the split
	 * @return the o data request
	 * @throws ODataHandlerException
	 *             the o data translated exception
	 */
	private ODataRequest createODataRequest(final HttpServletRequest httpRequest, final int split)
			throws ODataHandlerException {
		try {

			logger.info("createODataRequest");

			ODataRequest odRequest = new ODataRequest();

			odRequest.setBody(httpRequest.getInputStream());
			extractHeaders(odRequest, httpRequest);
			extractMethod(odRequest, httpRequest);
			extractUri(odRequest, httpRequest, split);

			return odRequest;
		} catch (final IOException e) {
			throw new ODataHandlerException("An I/O exception occurred.", e,
					SerializerException.MessageKeys.IO_EXCEPTION);
		}
	}

	/**
	 * Extract method.
	 *
	 * @param odRequest
	 *            the od request
	 * @param httpRequest
	 *            the http request
	 * @throws ODataHandlerException
	 *             the o data translated exception
	 */
	private void extractMethod(final ODataRequest odRequest,
							   final HttpServletRequest httpRequest)
			throws ODataHandlerException {
		try {

			logger.info("extractMethod");

			HttpMethod httpRequestMethod = HttpMethod.valueOf(httpRequest
					.getMethod());

			if (httpRequestMethod == HttpMethod.POST) {
				String xHttpMethod = httpRequest
						.getHeader(HttpHeader.X_HTTP_METHOD);
				String xHttpMethodOverride = httpRequest
						.getHeader(HttpHeader.X_HTTP_METHOD_OVERRIDE);

				if (xHttpMethod == null && xHttpMethodOverride == null) {
					odRequest.setMethod(httpRequestMethod);
				} else if (xHttpMethod == null) {
					odRequest
							.setMethod(HttpMethod.valueOf(xHttpMethodOverride));
				} else if (xHttpMethodOverride == null) {
					odRequest.setMethod(HttpMethod.valueOf(xHttpMethod));
				} else {
					if (!xHttpMethod.equalsIgnoreCase(xHttpMethodOverride)) {
						throw new ODataHandlerException(
								"Ambiguous X-HTTP-Methods",
								ODataHandlerException.MessageKeys.AMBIGUOUS_XHTTP_METHOD,
								xHttpMethod, xHttpMethodOverride);
					}
					odRequest.setMethod(HttpMethod.valueOf(xHttpMethod));
				}
			} else {
				odRequest.setMethod(httpRequestMethod);
			}
		} catch (IllegalArgumentException e) {
			throw new ODataHandlerException("Invalid HTTP method"
					+ httpRequest.getMethod(),
					ODataHandlerException.MessageKeys.INVALID_HTTP_METHOD,
					httpRequest.getMethod());
		}
	}

	/**
	 * Extract uri.
	 *
	 * @param odRequest
	 *            the od request
	 * @param httpRequest
	 *            the http request
	 * @param split
	 *            the split
	 */
	private void extractUri(final ODataRequest odRequest,
							final HttpServletRequest httpRequest, final int split) {

		logger.info("extractUri");

		String rawRequestUri = httpRequest.getRequestURL().toString();

		String rawODataPath;
		if (!"".equals(httpRequest.getServletPath())) {
			int beginIndex;
			beginIndex = rawRequestUri.indexOf(URI);
			beginIndex += URI.length();
			rawODataPath = rawRequestUri.substring(beginIndex);
		} else if (!"".equals(httpRequest.getContextPath())) {
			int beginIndex;
			beginIndex = rawRequestUri.indexOf(httpRequest.getContextPath());
			beginIndex += httpRequest.getContextPath().length();
			rawODataPath = rawRequestUri.substring(beginIndex);
		} else {
			rawODataPath = httpRequest.getRequestURI();
		}

		String rawServiceResolutionUri;
		if (split > 0) {
			rawServiceResolutionUri = rawODataPath;
			for (int i = 0; i < split; i++) {
				int e = rawODataPath.indexOf("/", 1);
				if (-1 == e) {
					rawODataPath = "";
				} else {
					rawODataPath = rawODataPath.substring(e);
				}
			}
			int end = rawServiceResolutionUri.length() - rawODataPath.length();
			rawServiceResolutionUri = rawServiceResolutionUri.substring(0, end);
		} else {
			rawServiceResolutionUri = null;
		}

		String rawBaseUri = rawRequestUri.substring(0, rawRequestUri.length()
				- rawODataPath.length());

		odRequest.setRawQueryPath(httpRequest.getQueryString());
		odRequest.setRawRequestUri(rawRequestUri
				+ (httpRequest.getQueryString() == null ? "" : "?"
				+ httpRequest.getQueryString()));

		odRequest.setRawODataPath(rawODataPath);
		odRequest.setRawBaseUri(rawBaseUri);
		odRequest.setRawServiceResolutionUri(rawServiceResolutionUri);
	}

	/**
	 * Extract headers.
	 *
	 * @param odRequest
	 *            the od request
	 * @param req
	 *            the req
	 */
	private void extractHeaders(final ODataRequest odRequest,
								final HttpServletRequest req) {

		logger.info("extractHeaders");

		for (Enumeration<?> headerNames = req.getHeaderNames(); headerNames
				.hasMoreElements();) {
			String headerName = (String) headerNames.nextElement();
			List<String> headerValues = new ArrayList<String>();
			for (Enumeration<?> headers = req.getHeaders(headerName); headers
					.hasMoreElements();) {
				String value = (String) headers.nextElement();
				headerValues.add(value);
			}
			odRequest.addHeader(headerName, headerValues);
		}
	}

}
