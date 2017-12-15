package com.csi.itaca.config.service;

import com.csi.itaca.config.model.ConfigurationBase;
import com.csi.itaca.config.model.Configurator;
import com.csi.itaca.config.model.DatosCambioDeConfiguracion0;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


public class ConfigurationServiceImpl implements ConfigurationService {

	private static final Logger logger = LoggerFactory.getLogger(ConfigurationServiceImpl.class);

	@Autowired
	private Configurator configurator;
	
	@Override
	public void cambiarConfiguracion(DatosCambioDeConfiguracion0 datos) {

		ConfigurationBase config = configurator.getConfig(datos.getClaseConfiguracion());
		logger.info(config.toString());

	}

}
