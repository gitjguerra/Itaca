package com.csi.itaca.config.endpoint;

import com.csi.itaca.config.model.DatosCambioDeConfiguracion0DTO;
import com.csi.itaca.config.service.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

public class ConfigurationServiceProxyRestServer implements ConfigurationServiceProxy {

	public final static String BASE_CONTROLLER_PATH = "configuracion";
	
	@Autowired
	private ConfigurationService business;
	
	@Override
	@RequestMapping(value = "/update", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity cambiarConfiguracion(@Valid @RequestBody DatosCambioDeConfiguracion0DTO datos) {
		business.cambiarConfiguracion(datos);
		return new ResponseEntity(HttpStatus.OK);
	}

}
