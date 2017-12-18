package com.csi.itaca.config.model;

import java.util.Map;

/**
 * Gestor de configuraciones de Itaca. Las configuraciones se
 *        cargan desde scripts groovy en una caché al inicio. Los metodos
 *        descritos recuperan las configuraciones en base a los parametros
 *        indicados en el groovy.
 *
 * @author cmartin
 * @since 20-10-2015
 */
public interface Configurator {

	/**
	 * Metodo de recuperación de una configuración
	 * 
	 * @param configClass
	 *            Classe de definición de configuración
	 * @return Instancia de la configuración solicitada.
	 */
	<T extends ConfigurationBase> T getConfig(Class<T> configClass);

	/**
	 * Metodo de recuperación de una configuración filtrado por pefil.
	 * 
	 * @param configClass
	 *            Classe de definición de configuración
	 * @param perfil
	 *            Perfil de usuario ej. CENTRAL
	 * @return Instancia de la configuración solicitada
	 */
	<T extends ConfigurationBase> T getConfig(Class<T> configClass, String perfil);

	/**
	 * Metodo de recuperación de una configuración filtrada por perfil y modo
	 * 
	 * @param configClass
	 *            Classe de definición de configuración
	 * @param perfil
	 *            Perfil de usuario ej. CENTRAL
	 * @param modo
	 *            Modo de acceso. ej. ALTA, CONSULTA etc...
	 * @return Instancia de la configuración solicitada
	 */
	<T extends ConfigurationBase> T getConfig(Class<T> configClass, String perfil, String modo);

	/**
	 * Metodo de recuperación de una configuración filtrada por perfil, modo y
	 * por objeto.
	 * 
	 * @param configClass
	 *            Classe de definición de configuración
	 * @param perfil
	 *            Perfil de usuario ej. CENTRAL
	 * @param modo
	 *            Modo de acceso. ej. ALTA, CONSULTA etc...
	 * @param objeto
	 *            Objeto de filtraje, dene ser una instancia del objeto
	 *            parametrizado en la configuracion ej. Poliza0DTO
	 * @return Instancia de la configuración solicitada
	 */
	<T extends ConfigurationBase> T getConfig(Class<T> configClass, String perfil, String modo, Object objeto);

	/**
	 * Metodo para recargar las configuraciones.
	 */
	void reload();

	/**
	 * Metodo para registrar los módulos donde se encuentran las configuraciones
	 * groovy
	 * 
	 * @param name
	 *            Ruta base del package o ruta al directorio del disco donde se
	 *            encuentran las configuraciones groovy
	 */
	void registerModule(String name);
	
	Map<Class<? extends ConfigurationBase>, ConfigurationBase> getClassConfigs();
}