package com.csi.itaca.dataview.edm;

import com.csi.itaca.dataview.model.dao.AllTabColsRepository;
import com.csi.itaca.dataview.model.dao.FilaGenerico;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.olingo.commons.api.edm.EdmEntitySet;
import org.apache.olingo.commons.api.edm.EdmEntityType;
import org.apache.olingo.commons.api.http.HttpStatusCode;
import org.apache.olingo.server.api.processor.EntityProcessor;
import org.apache.olingo.commons.api.format.ContentType;
import org.apache.olingo.server.api.*;
import org.apache.olingo.server.api.deserializer.DeserializerException;
import org.apache.olingo.server.api.serializer.SerializerException;
import org.apache.olingo.server.api.uri.UriInfo;
import org.apache.olingo.server.api.uri.UriInfoResource;
import org.apache.olingo.server.api.uri.UriResource;
import org.apache.olingo.server.api.uri.UriResourceEntitySet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;
import java.util.Locale;

@Component
public class DynamicEntityProcessor implements EntityProcessor {

    private OData odata;
    private ServiceMetadata serviceMetadata;
    private String paramURI;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private AllTabColsRepository colsService;
    /** Logger */
    private static Logger log = Logger.getLogger(DynamicEntityProcessor.class);

    @Override
    public void init(OData odata, ServiceMetadata serviceMetadata) {
        this.odata = odata;
        this.serviceMetadata = serviceMetadata;
    }

    @Override
    public void readEntity(ODataRequest request, ODataResponse response, UriInfo uriInfo, ContentType responseFormat) throws ODataApplicationException, SerializerException {
        log.info("read");
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

        String sql = "INSERT INTO "+edmEntityType.getName()+" ("+fields+") VALUES ("+values+")";
        jdbcTemplate.update(sql);
    }

    @Override
    public void updateEntity(ODataRequest request, ODataResponse response, UriInfo uriInfo, ContentType requestFormat, ContentType responseFormat) throws ODataApplicationException, DeserializerException, SerializerException {
        log.info("update");
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
        }
        // Get the Primary columns to
        int filas;
        String columnName="";
        List<FilaGenerico> gnericRow =  colsService.findAllConstraints(edmEntityType.getName().toString());

        for(filas = 0; filas < gnericRow.size(); filas++) {
            columnName = gnericRow.get(filas).getCampos().get(0).toString();
        }


        String sql = "UPDATE "+edmEntityType.getName()+" SET "+fieldstoUpdate+" WHERE "+ columnName +"="+paramURI;
        jdbcTemplate.update(sql);
    }

    @Override
    public void deleteEntity(ODataRequest request, ODataResponse response, UriInfo uriInfo) throws ODataApplicationException {
        log.info("delete");
        EdmEntitySet edmEntitySet = getEdmEntitySet(uriInfo);
        EdmEntityType edmEntityType = edmEntitySet.getEntityType();

        List<UriResource> resourcePaths = uriInfo.getUriResourceParts();
        List<FilaGenerico> gnericRow =  colsService.findAllConstraints(edmEntityType.getName().toString());

        paramURI = ((UriResourceEntitySet) resourcePaths.get(0)).getKeyPredicates().get(0).getText().toString();
        int filas;
        String columnName="";



        for(filas = 0; filas < gnericRow.size(); filas++) {
            columnName = gnericRow.get(filas).getCampos().get(0).toString();
        }
        //TODO: R.V. The paramURI  could be more than one row.
        String sql = "DELETE "+edmEntityType.getName()+"  WHERE "+ columnName +"="+paramURI;
        jdbcTemplate.update(sql);
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
