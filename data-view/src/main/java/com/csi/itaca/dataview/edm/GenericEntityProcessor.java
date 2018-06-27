package com.csi.itaca.dataview.edm;

import com.csi.itaca.common.GlobalConstants;
import com.csi.itaca.dataview.DataViewConfiguration;
import com.csi.itaca.dataview.model.dto.AuditDTO;
import com.csi.itaca.dataview.service.AllTabColsRepository;
import com.csi.itaca.dataview.model.GenericRecord;
import com.csi.itaca.dataview.service.DataViewManagementServiceImpl;
import com.csi.itaca.dataview.service.DynRowMapper;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import org.apache.catalina.connector.Response;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.olingo.commons.api.data.ContextURL;
import org.apache.olingo.commons.api.edm.EdmEntitySet;
import org.apache.olingo.commons.api.edm.EdmEntityType;
import org.apache.olingo.commons.api.http.HttpHeader;
import org.apache.olingo.commons.api.http.HttpStatusCode;
import org.apache.olingo.server.api.processor.EntityProcessor;
import org.apache.olingo.commons.api.format.ContentType;
import org.apache.olingo.server.api.*;
import org.apache.olingo.server.api.deserializer.DeserializerException;
import org.apache.olingo.server.api.serializer.*;
import org.apache.olingo.server.api.uri.UriInfo;
import org.apache.olingo.server.api.uri.UriInfoResource;
import org.apache.olingo.server.api.uri.UriResource;
import org.apache.olingo.server.api.uri.UriResourceEntitySet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Component
public class GenericEntityProcessor implements EntityProcessor {

    /** Logger */
    private static Logger log = Logger.getLogger(GenericEntityProcessor.class);
    private static final String JSON_OK = "{\"status\": \"successful\"}";
    private static final String JSON_FAIL = "{\"status\": \"failure\"}";

    private OData odata;
    private ServiceMetadata serviceMetadata;
    private String paramURI;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private AllTabColsRepository colsService;
    @Autowired
    private DataViewConfiguration configuration;
    @Autowired
    DataViewManagementServiceImpl dataView;

    @Override
    public void init(OData odata, ServiceMetadata serviceMetadata) {
        this.odata = odata;
        this.serviceMetadata = serviceMetadata;
    }

    @Override
    public void readEntity(ODataRequest request, ODataResponse response, UriInfo uriInfo, ContentType responseFormat) throws ODataApplicationException, SerializerException {
        log.info("read");
        EdmEntitySet edmEntitySet = getEdmEntitySet(uriInfo);
        EdmEntityType edmEntityType = edmEntitySet.getEntityType();

        List<UriResource> resourcePaths = uriInfo.getUriResourceParts();
        String primaryKey = ((UriResourceEntitySet) resourcePaths.get(0)).getKeyPredicates().get(0).getText();
        GenericEntityProvider genericEntityProvider = configuration.getEntityProvider(edmEntityType.getName(),jdbcTemplate, colsService);
        String primaryKeyFieldName = genericEntityProvider.getEntityType().getKey().get(0).getName();
        String sql = "SELECT "+genericEntityProvider.getColumnsCommaSeparated()+" FROM "+edmEntityType.getName()+" WHERE "+ primaryKeyFieldName +"="+primaryKey;

        // Get the data
        List<GenericRecord> shouldBeOnlyOneRecord = jdbcTemplate.query(sql,new DynRowMapper());

        // prepare the response...
        if (shouldBeOnlyOneRecord.size() == 1) {
            shouldBeOnlyOneRecord.get(0);

            ContextURL contextUrl = ContextURL.with().entitySet(edmEntitySet).build();

            ODataSerializer serializer = odata.createSerializer(responseFormat);

            final String id = request.getRawBaseUri() + "/" + edmEntitySet.getName();
            EntitySerializerOptions opts = EntitySerializerOptions.with().contextURL(contextUrl).build();

            SerializerResult serializedContent = serializer.entity(serviceMetadata, edmEntityType,
                        shouldBeOnlyOneRecord.get(0).getEntity(genericEntityProvider.getColumnDefinitionList()), opts);

            // Finally: configure the response object: set the body, headers and status code
            response.setContent(serializedContent.getContent());
            response.setStatusCode(HttpStatusCode.OK.getStatusCode());
            response.setHeader(HttpHeader.CONTENT_TYPE, responseFormat.toContentTypeString());

            //  <editor-fold defaultstate="collapsed" desc="*** Audit ***">
                AuditDTO dto = new AuditDTO();
                dto.setOperation(GlobalConstants.READ_PROCESS);	//  * @param operation type operation (create, update, get or delete)
                dto.setSqlCommand(sql);	//  * @param sqlCommand sql transact the activity
                dto.setTimeStamp(new Date());   					//  * @param timeStamp the time stamp th audit.
                String user = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                dto.setUserName(user);		//  * @param userName the user produces activity
                dataView.auditTransaction(dto);
            //  </editor-fold>

        }
    }

    @Override
    public void createEntity(ODataRequest request, ODataResponse response, UriInfo uriInfo, ContentType requestFormat, ContentType responseFormat) throws ODataApplicationException, DeserializerException, SerializerException {
        log.info("create");

        EdmEntitySet edmEntitySet = getEdmEntitySet(uriInfo);
        EdmEntityType edmEntityType = edmEntitySet.getEntityType();

        InputStream requestInputStream = request.getBody();

        StringBuffer fields = new StringBuffer();
        StringBuffer values = new StringBuffer();
        try {
            JsonFactory jsonFactory = new JsonFactory();
            JsonParser jsonParser = jsonFactory.createParser(requestInputStream);

            // loop until token equal to "}"
            while (jsonParser.nextToken() != JsonToken.END_OBJECT) {

                String fieldName = jsonParser.getCurrentName();
                if (fieldName!=null) {
                    jsonParser.nextToken();
                    String value = jsonParser.getText();

                    // build the fields
                    if (fields.length() != 0) {
                        fields.append(",");
                    }
                    fields.append(fieldName);

                    // build the values
                    if (values.length() != 0) {
                        values.append(",");
                    }

                    //TODO: The type should be determined from the column type in the DB.
                    if (StringUtils.isNumericSpace(value)) {
                        values.append(value);
                    }
                    else {
                        values.append("'").append(value).append("'");
                    }
                }
            }
        }
        catch (Exception e) {
            log.error("error creating entity"+e);
        }

        try{
            String sql = "INSERT INTO "+edmEntityType.getName()+" ("+fields+") VALUES ("+values+")";
            jdbcTemplate.update(sql);

            //  <editor-fold defaultstate="collapsed" desc="*** Audit ***">
                AuditDTO dto = new AuditDTO();
                dto.setOperation(GlobalConstants.CREATE_PROCESS);	    //  * @param operation type operation (create, update, get or delete)
                dto.setSqlCommand(sql);	                                //  * @param sqlCommand sql transact the activity
                dto.setTimeStamp(new Date());   					    //  * @param timeStamp the time stamp th audit.
                String user = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                dto.setUserName(user);		//  * @param userName the user produces activity
                dataView.auditTransaction(dto);
            //  </editor-fold>

            // if the operation is complete response Ok
            response.setStatusCode(Response.SC_OK);
        }catch(Exception e) {
            log.error("Error ", e);
        }
    }

    @Override
    public void updateEntity(ODataRequest request, ODataResponse response, UriInfo uriInfo, ContentType requestFormat, ContentType responseFormat) throws ODataApplicationException, DeserializerException, SerializerException {
        log.info("update");
        String result = JSON_OK;
        List<UriResource> resourcePaths = uriInfo.getUriResourceParts();
        EdmEntitySet edmEntitySet = getEdmEntitySet(uriInfo);
        EdmEntityType edmEntityType = edmEntitySet.getEntityType();
        String fieldstoUpdate="";
        paramURI = ((UriResourceEntitySet) resourcePaths.get(0)).getKeyPredicates().get(0).getText().toString();
        InputStream requestInputStream = request.getBody();

        StringBuffer fields = new StringBuffer();
        StringBuffer values = new StringBuffer();
        try {
            JsonFactory jsonFactory = new JsonFactory();
            JsonParser jsonParser = jsonFactory.createParser(requestInputStream);
            // loop until token equal to "}"
            while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
                String fieldName = jsonParser.getCurrentName();
                if (fieldName!=null) {
                    jsonParser.nextToken();
                    String value = jsonParser.getText();
                    //TODO: The type should be determined from the column type in the DB.
                    if (StringUtils.isNumericSpace(value)) {

                    }else{
                        value="'"+value+"'";
                    }
                    if(fieldstoUpdate.isEmpty()){
                        fieldstoUpdate= fieldName+"="+value;
                    }else{
                        fieldstoUpdate= fieldstoUpdate+","+fieldName+"="+value;
                    }

                }
            }
        }
        catch (Exception e) {
            log.error("error updating entity"+e);
            result = JSON_FAIL;
        }
        // Get the Primary columns to
        int filas;
        String columnName="";
        List<GenericRecord> gnericRow =  colsService.findAllConstraints(edmEntityType.getName().toString());

        for(filas = 0; filas < gnericRow.size(); filas++) {
            columnName = gnericRow.get(filas).getFields().get(0).toString();
        }

        try{
            String sql = "UPDATE "+edmEntityType.getName()+" SET "+fieldstoUpdate+" WHERE "+ columnName +"="+paramURI;
            jdbcTemplate.update(sql);

            //  <editor-fold defaultstate="collapsed" desc="*** Audit ***">
            AuditDTO dto = new AuditDTO();
            dto.setOperation(GlobalConstants.UPDATE_PROCESS);	//  * @param operation type operation (create, update, get or delete)
            dto.setSqlCommand(sql);	//  * @param sqlCommand sql transact the activity
            dto.setTimeStamp(new Date());   					//  * @param timeStamp the time stamp th audit.
            String user = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            dto.setUserName(user);		//  * @param userName the user produces activity
            dataView.auditTransaction(dto);
            //  </editor-fold>

            // Finally: configure the response object: set the body, headers and status code
            InputStream stream = new ByteArrayInputStream(result.getBytes(StandardCharsets.UTF_8));
            response.setContent(stream);
            response.setStatusCode(HttpStatusCode.OK.getStatusCode());
            response.setHeader(HttpHeader.CONTENT_TYPE, responseFormat.toContentTypeString());
        }catch(Exception e) {
            log.error("Error ", e);
        }
    }

    @Override
    public void deleteEntity(ODataRequest request, ODataResponse response, UriInfo uriInfo) throws ODataApplicationException {
        log.info("delete");
        EdmEntitySet edmEntitySet = getEdmEntitySet(uriInfo);
        EdmEntityType edmEntityType = edmEntitySet.getEntityType();

        List<UriResource> resourcePaths = uriInfo.getUriResourceParts();
        List<GenericRecord> gnericRow =  colsService.findAllConstraints(edmEntityType.getName().toString());

        paramURI = ((UriResourceEntitySet) resourcePaths.get(0)).getKeyPredicates().get(0).getText().toString();
        int filas;
        String columnName="";

        for(filas = 0; filas < gnericRow.size(); filas++) {
            columnName = gnericRow.get(filas).getFields().get(0).toString();
        }

        try{
            //TODO: R.V. The paramURI  could be more than one row.
            String sql = "DELETE "+edmEntityType.getName()+"  WHERE "+ columnName +"="+paramURI;
            jdbcTemplate.update(sql);

            //  <editor-fold defaultstate="collapsed" desc="*** Audit ***">
            AuditDTO dto = new AuditDTO();
            dto.setOperation(GlobalConstants.DELETE_PROCESS);	//  * @param operation type operation (create, update, get or delete)
            dto.setSqlCommand(sql);	//  * @param sqlCommand sql transact the activity
            dto.setTimeStamp(new Date());   					//  * @param timeStamp the time stamp th audit.
            String user = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            dto.setUserName(user);		//  * @param userName the user produces activity
            dataView.auditTransaction(dto);
            //  </editor-fold>

            // if the operation is complete response Ok
            response.setStatusCode(Response.SC_OK);
        }catch(Exception e) {
            log.error("Error ", e);
        }

    }


    public static EdmEntitySet getEdmEntitySet(UriInfoResource uriInfo) throws ODataApplicationException {

        List<UriResource> resourcePaths = uriInfo.getUriResourceParts();
        // To get the entity set we have to interpret all URI segments
        if (!(resourcePaths.get(0) instanceof UriResourceEntitySet)) {
            // Here we should interpret the whole URI but in this example we do not support navigation so we throw an
            // exception
            throw new ODataApplicationException("Invalid resource type for first segment.", HttpStatusCode.NOT_IMPLEMENTED
                    .getStatusCode(), Locale.ENGLISH);
        }

        UriResourceEntitySet uriResource = (UriResourceEntitySet) resourcePaths.get(0);

        return uriResource.getEntitySet();
    }

}
