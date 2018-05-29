package com.csi.itaca.dataview.service;


import org.apache.olingo.commons.api.data.EntityCollection;
import org.apache.olingo.commons.api.edm.FullQualifiedName;
import org.apache.olingo.commons.api.edm.provider.CsdlEntityType;
import org.apache.olingo.server.api.uri.UriInfo;

/**
 * @author rohitghatol
 *
 */
public interface EntityProvider {


	CsdlEntityType getEntityType();

	String getEntitySetName();

	EntityCollection getEntitySet(UriInfo uriInfo);
	
	/**
	 * Gets the fully qualified name.
	 *
	 * @return the fully qualified name
	 */
	FullQualifiedName getFullyQualifiedName();
}
