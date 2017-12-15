package com.csi.itaca.config.endpoint;

import com.csi.itaca.config.model.DatosCambioDeConfiguracion0DTO;
import org.springframework.http.ResponseEntity;

public interface ConfigurationServiceProxy {

	ResponseEntity cambiarConfiguracion(DatosCambioDeConfiguracion0DTO datos);
}
