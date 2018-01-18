package com.csi.itaca.config.tools;

import com.csi.itaca.config.model.ConfigurationBase;
import com.csi.itaca.config.model.Configurator;
import com.csi.itaca.config.model.ConfiguratorConfig;
import groovy.lang.Binding;
import groovy.lang.Closure;
import groovy.lang.GroovyClassLoader;
import groovy.lang.Script;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.apachecommons.CommonsLog;
import org.codehaus.groovy.control.CompilationFailedException;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.Map.Entry;
import java.util.function.Consumer;

/**
 * 
 * @author cmartin
 * @since 30-11-2016
 * 
 *        Implementación de la interfaz Configurator. Esta implementación
 *        instancia las classes groovy desde el classpath o desde un directorio
 *        del filesystem y lo ejecuta después de hacer un "binding" de la
 *        clossure "ConfigMethodClosureCall" con la que se ejecutan en un orden
 *        controlado por tokens y retornando un Map que posteriormente se
 *        convierte con las librerias de groovy a la clase de definición de
 *        configuración itaca. Una vez conseguida la clase se añade al Map/cache
 *        que corresponde a su definición.
 * 
 *        Se publican los metodos de recuperación de las configuraciones de las
 *        caches segun parámetros.
 * 
 */
@CommonsLog
public class ConfiguratorImpl implements Configurator, Serializable {

	private static final long serialVersionUID = 3908364243665373843L;

	/**
	 * Specifies where to find configuration files "classpath" or "filesystem".
	 */
	@Value("${configurator.mode}")
	private String mode;

	/**
	 * The location of the configuration files on the file system. "configurator.mode" needs be et to "filesystem".
	 */
	@Value("${configurator.filesystem.basedir:}")
	private String filesystemBaseDir;

	/** Specifies whether to automatically reload configuration. */
	@Value("${configurator.autoreload}")
	private boolean autoreload;


	private List<String> registeredModules;

	@Getter
	Map<Class<? extends ConfigurationBase>, ConfigurationBase> classConfigs;
	Map<Class<? extends ConfigurationBase>, Map<String, ConfigurationBase>> perfilConfigs;
	Map<Class<? extends ConfigurationBase>, Map<String, Map<String, ConfigurationBase>>> perfilModoConfigs;
	Map<Class<? extends ConfigurationBase>, Map<String, Map<String, List<Closure<Boolean>>>>> perfilModoCondicionCondiciones;
	Map<Class<? extends ConfigurationBase>, Map<String, Map<String, List<ConfigurationBase>>>> perfilModoCondicionConfigs;

	public ConfiguratorImpl() {
		classConfigs = new HashMap<>();
		perfilConfigs = new HashMap<>();
		perfilModoConfigs = new HashMap<>();

		perfilModoCondicionCondiciones = new HashMap<>();
		perfilModoCondicionConfigs = new HashMap<>();
		registeredModules = new ArrayList<>();

	}

	@PostConstruct
	private void init() {

		if (autoreload) {
			throw new ConfiguratorFeatureNotAvailableException("Automatic recharge is not available");
		}
	}

	/**
	 * Inicialización de la carga de configuraciones
	 * 
	 * @param name
	 *            Ruta a donde se encuentran los groovies.
	 * 
	 */
	@Override
	public void registerModule(String name) {
		log.info("****************** LOAD CONFIGURATIONS START *******************");
		log.info("Modulo: " + name);

		registeredModules.add(name);

		if ("classpath".equals(mode)) {
			registerModuleByClasspath(name);
		} else if ("filesystem".equals(mode)) {
			compileModuleScriptByFilesystem(
					filesystemBaseDir + name.replaceAll("\\.", File.separator + File.separator) + File.separator);
			// registerModuleByFilesystem(filesystemBaseDir +
			// name.replaceAll(".", File.pathSeparator) + File.pathSeparator);
		} else {
			throw new ConfiguratorFeatureNotAvailableException("El modo: " + mode + " no está disponible");
		}

		imprimirConfiguraciones();
		log.info("**************** LOAD CONFIGURATIONS COMPLETED *****************");

	}

	private void compileModuleScriptByFilesystem(String filesystemPath) {
		GroovyClassLoader groovyClassLoader = new GroovyClassLoader();
		try {

			Files.walk(Paths.get(filesystemPath)).filter(Files::isRegularFile).filter((path) -> {
				return path.getFileName().toString().substring(path.getFileName().toString().lastIndexOf("."))
						.equals(".groovy");
			}).forEach((path) -> {
				log.info("GROOVY FILESYSTEM: " + path.toAbsolutePath());
				try {
					@SuppressWarnings("unchecked")
					Class<Script> c = (Class<Script>) groovyClassLoader.parseClass(path.toAbsolutePath().toFile());
					runScript(c);

				} catch (CompilationFailedException | IOException e) {
					throw new ConfiguratorException("No se ha podido cargar el script " + path.getFileName(), e);
				}
			});

		} catch (IOException e) {
			throw new ConfiguratorException("El path al filesystem es incorrecto: " + filesystemPath, e);
		} finally {
			try {
				groovyClassLoader.close();
			} catch (IOException e) {
				throw new ConfiguratorException("No se ha podido cerrar el class loader", e);
			}
		}

	}

	private void registerModuleByClasspath(String name) {

		ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
		scanner.addIncludeFilter(new AssignableTypeFilter(Script.class));

		Set<BeanDefinition> scripts = scanner.findCandidateComponents(name);
		log.info(scripts.size() + " scripts de configuracion encontrados (classpath).");

		for (BeanDefinition beanDefinition : scripts) {
			try {
				log.debug("Leyendo script de configuracion: " + beanDefinition.getBeanClassName());
				@SuppressWarnings("unchecked")
				Class<Script> c = (Class<Script>) Class.forName(beanDefinition.getBeanClassName());
				runScript(c);

			} catch (ClassNotFoundException e) {
				final String err = "El script de configuracion: " + beanDefinition.getClass().getName() + " no existe.";

				log.error(err);
				throw new ConfiguratorException(err, e);
			}
		}

	}

	private void runScript(Class<Script> c) {
		Binding b = new Binding();
		b.setProperty("config", this.new ConfigMethodClosureCall(this));

		try {
			Script s = c.newInstance();
			s.setBinding(b);
			s.run();

		} catch (InstantiationException | IllegalAccessException e) {
			final String err = "El script de configuracion: " + c.getClass().getName()
					+ " no existe o contiene errores.";

			log.error(err);
			throw new ConfiguratorException(err, e);
		}

	}

	private void imprimirConfiguraciones() {
		log.debug("Configuraciones genericas cargadas: " + classConfigs.size());
		for (Entry<Class<? extends ConfigurationBase>, ConfigurationBase> keyValue : classConfigs.entrySet()) {
			log.trace("\t" + keyValue.getKey().getClass().getSimpleName());
		}
		log.debug("Configuraciones de perfil cargadas: " + perfilConfigs.size());
		for (Entry<Class<? extends ConfigurationBase>, Map<String, ConfigurationBase>> keyValue : perfilConfigs
				.entrySet()) {
			log.trace("\t" + keyValue.getKey().getSimpleName() + ": " + keyValue.getValue().size() + " "
					+ keyValue.getValue().keySet().toString());
			for (Entry<String, ConfigurationBase> value : keyValue.getValue().entrySet()) {
				log.trace("\t\tPerfil: " + value.getKey());
				log.trace("\t\t\t" + value.getValue().toString());
			}
		}
		log.debug("Configuraciones de perfil y modo cargadas: " + perfilModoConfigs.size());
		for (Entry<Class<? extends ConfigurationBase>, Map<String, Map<String, ConfigurationBase>>> keyValue : perfilModoConfigs
				.entrySet()) {
			log.trace("\t" + keyValue.getKey().getSimpleName() + ": " + keyValue.getValue().size() + " "
					+ keyValue.getValue().keySet().toString());
			for (Entry<String, Map<String, ConfigurationBase>> keyValue2 : keyValue.getValue().entrySet()) {
				log.trace("\t\tPerfil: " + keyValue2.getKey() + ": " + keyValue2.getValue().size() + " "
						+ keyValue2.getValue().keySet().toString());
				for (Entry<String, ConfigurationBase> value2 : keyValue2.getValue().entrySet()) {
					log.trace("\t\t\tModo: " + value2.getKey());
					log.trace("\t\t\t\t" + value2.getValue().toString());
				}
			}
		}
		log.debug("Configuraciones de perfil, modo y condicion cargadas: " + perfilModoCondicionConfigs.size());
		for (Entry<Class<? extends ConfigurationBase>, Map<String, Map<String, List<ConfigurationBase>>>> keyValue : perfilModoCondicionConfigs
				.entrySet()) {
			log.trace("\t" + keyValue.getKey().getSimpleName() + ": " + keyValue.getValue().size() + " "
					+ keyValue.getValue().keySet().toString());
			for (Entry<String, Map<String, List<ConfigurationBase>>> keyValue2 : keyValue.getValue().entrySet()) {
				log.trace("\t\tPerfil: " + keyValue2.getKey() + ": " + keyValue2.getValue().size() + " "
						+ keyValue2.getValue().keySet().toString());
				for (Entry<String, List<ConfigurationBase>> value2 : keyValue2.getValue().entrySet()) {
					log.trace("\t\t\tModo: " + value2.getKey() + " " + value2.getValue().size());
					for (ConfigurationBase value3 : value2.getValue()) {
						log.trace("\t\t\t\t: " + value3);
					}
				}
			}
		}
	}

	@SuppressWarnings("serial")
	class ConfigMethodClosureCall extends Closure<ConfigMethodClosureCall.ConfigCall> {
		private ConfigCall cc = this.new ConfigCall();

		public ConfigMethodClosureCall(Object owner, Object thisObject) {
			super(owner, thisObject);
		}

		public ConfigMethodClosureCall(Object owner) {
			super(owner);
		}

		public ConfigCall doCall(Object o) {
			cc.getNextTokenSetter().accept(o);

			if (cc.finished) {
				log.trace("Procesando la configuración: " + cc.getClazz() + " modo: " + cc.getModo_() + " perfil: "
						+ cc.getPerfil_() + " condicion: " + cc.getCondicion() + " ...");
				ConfigurationBase cb = DefaultGroovyMethods
						.asType(asMapDeepTransformation(cc.getConfigMap(), cc.getClazz()), cc.getClazz());
				log.trace("... procesado ok");
				ConfiguratorImpl.this.addConfiguration(cc.getClazz(), cc.getPerfil_(), cc.getModo_(),
						cc.getCondicion_(), cc.getPrioridad_(), cb);
				log.trace("... añadido ok");

				cc = this.new ConfigCall();
			}

			return cc;
		}

		private Map<String, Object> asMapDeepTransformation(Map<String, Object> map, Class<? extends Object> clazz) {

			Map<String, Object> m = new HashMap<>();

			for (Entry<String, Object> e : map.entrySet()) {
				if (e.getKey().equals("as"))
					continue;

				Class<? extends Object> targetClass;

				targetClass = findPropertyClass(clazz, e.getKey());
				Class<?> contentTargetClass = findPropertyContentClass(clazz, e.getKey());

				if ((e.getValue() instanceof Map) || (e.getValue() instanceof List)) {

					if (targetClass.isAssignableFrom(List.class)) {

						@SuppressWarnings("unchecked")
						List<Object> source = (List<Object>) e.getValue();

						List<Object> target = new ArrayList<>();

						source.forEach((i) -> {

							if (contentTargetClass != null) {
								if (contentTargetClass.isAssignableFrom(i.getClass())) {
									target.add(i);
								} else {

									if (i instanceof Map) {
										@SuppressWarnings("unchecked")
										Map<String, Object> listItem = (Map<String, Object>) i;
										Class<?> contentClass = (Class<?>) listItem.get("as");

										if (contentClass != null) {

											if (contentTargetClass.isAssignableFrom(contentClass)) {

												target.add(DefaultGroovyMethods.asType(
														asMapDeepTransformation(listItem, contentClass), contentClass));

											} else {

												throw new ConfiguratorException(
														"La anotacion @ConfigurationConfig y el parametro del mapa configClass son de tipos incompatibles. Revisar la configuración o la definición de: "
																+ clazz);

											}
										} else {

											target.add(DefaultGroovyMethods.asType(
													asMapDeepTransformation(listItem, contentTargetClass),
													contentTargetClass));

										}
									}
								}
							} else {

								throw new ConfiguratorException(
										"Debería estar informada la anotación @ConfigClass en la definición de configuración: "
												+ clazz);

							}
						});

						m.put(e.getKey(), target);

					} else if (targetClass.isAssignableFrom(Map.class)) {

						@SuppressWarnings("unchecked")
						Map<String, Object> source = (Map<String, Object>) e.getValue();

						Map<String, Object> target = new HashMap<>();

						source.entrySet().forEach((entry) -> {

							if (contentTargetClass != null) {
								if (contentTargetClass.isAssignableFrom(entry.getValue().getClass())) {
									target.put(entry.getKey(), entry.getValue());
								} else {
									if (entry.getValue() instanceof Map) {

										@SuppressWarnings("unchecked")
										Map<String, Object> entryMap = (Map<String, Object>) entry.getValue();
										Class<?> configClass = (Class<?>) entryMap.get("as");

										if (configClass != null) {
											if (contentTargetClass.isAssignableFrom(configClass)) {
												target.put(entry.getKey(), DefaultGroovyMethods.asType(
														asMapDeepTransformation(entryMap, configClass), configClass));
											} else {

												throw new ConfiguratorException(
														"La anotacion @ConfigurationConfig y el parametro del mapa configClass son de tipos incompatibles. Revisar la configuración o la definición de: "
																+ clazz);

											}
										} else {

											target.put(entry.getKey(),
													DefaultGroovyMethods.asType(
															asMapDeepTransformation(entryMap, contentTargetClass),
															contentTargetClass));
										}
									}
								}
							} else {

								throw new ConfiguratorException(
										"Debería estar informada la anotación @ConfigClass en la definición de configuración: "
												+ clazz);

							}
						});

						m.put(e.getKey(), target);

					} else {
						if (contentTargetClass != null && targetClass != null) {
							final String error = "La definicion de la clase es incorrecta."
									+ " No se puede utilizar la anotación @ConfigurationConfig"
									+ " con propiedades de la definición que no sean Map o List"
									+ " -> ConfigurationConfig: " + contentTargetClass + " tipo de la propiedad: "
									+ targetClass + " Configuración: " + clazz;

							throw new ConfiguratorException(error);
						}

						if (targetClass.isAssignableFrom(e.getValue().getClass())) {

							m.put(e.getKey(), e.getValue());

						} else {

							if (e.getValue() instanceof Map) {

								@SuppressWarnings("unchecked")
								Map<String, Object> entryMap = (Map<String, Object>) e.getValue();
								Class<?> configClass = (Class<?>) entryMap.get("as");

								if (configClass != null) {

									if (targetClass.isAssignableFrom(configClass)) {
										@SuppressWarnings("unchecked")
										Map<String, Object> p = asMapDeepTransformation(
												(Map<String, Object>) e.getValue(), configClass);
										m.put(e.getKey(), DefaultGroovyMethods.asType(p, configClass));
									} else {

										throw new ConfiguratorException(
												"La definición de la propiedad en la clase de definición y el parametro del mapa configClass son de tipos incompatibles. Revisar la configuración o la definición de: "
														+ clazz);

									}
								} else {

									@SuppressWarnings("unchecked")
									Map<String, Object> p = asMapDeepTransformation((Map<String, Object>) e.getValue(),
											targetClass);
									m.put(e.getKey(), DefaultGroovyMethods.asType(p, targetClass));

								}
							}
						}
					}

				} else {

					if (e.getValue() != null && contentTargetClass != null) {
						final String error = "La propiedad anotada no es un contenedor. " + " -> ConfigurationConfig: "
								+ contentTargetClass + " Configuración: " + clazz;

						throw new ConfiguratorException(error);
					}
					m.put(e.getKey(), e.getValue());
				}
			}

			return m;
		}

		private Class<?> findPropertyClass(Class<?> clazz, String propertyName) {
			try {
				Field f = clazz.getDeclaredField(propertyName);
				return f.getType();
			} catch (NoSuchFieldException e) {

				if (null != clazz.getSuperclass() && !clazz.getSuperclass().isAssignableFrom(Object.class)) {
					return findPropertyClass(clazz.getSuperclass(), propertyName);
				}

				final String err = "Hay que añadir las anotaciones @ConfigurationConfig de forma adecuada en la clase: "
						+ clazz.getName()
						+ " y/o verificar que coinciden los nombres de las propiedades de las clases de definición.";

				log.error(err);
				throw new ConfiguratorException(err, e);
			}

		}

		private Class<?> findPropertyContentClass(Class<?> clazz, String propertyName) {
			try {
				Field f = clazz.getDeclaredField(propertyName);
				ConfiguratorConfig c = f.getAnnotation(ConfiguratorConfig.class);
				if (c == null)
					return null;
				return c.contentAs();
			} catch (NoSuchFieldException e) {

				if (!clazz.getSuperclass().isAssignableFrom(Object.class)) {
					return findPropertyContentClass(clazz.getSuperclass(), propertyName);
				}

				final String err = "Hay que añadir las anotaciones @ConfigurationConfig de forma adecuada en la clase: "
						+ clazz.getName();

				log.error(err);
				throw new ConfiguratorException(err, e);
			}

		}

		@ToString
		@Getter
		class ConfigCall {

			private Class<ConfigurationBase> clazz;

			private String perfil_, modo_;
			private Map<String, Object> configMap;

			private Consumer<Object> nextTokenSetter = this::setClassToken;
			private boolean finished = false;
			private Closure<Boolean> condicion_;
			private Integer prioridad_ = 0;

			public ConfigMethodClosureCall getPerfil() {
				nextTokenSetter = this::setPerfilToken;

				return ConfigMethodClosureCall.this;
			}

			public ConfigMethodClosureCall getModo() {
				nextTokenSetter = this::setModoToken;

				return ConfigMethodClosureCall.this;
			}

			public ConfigMethodClosureCall getCondicion() {
				nextTokenSetter = this::setCondicionToken;

				return ConfigMethodClosureCall.this;
			}

			public ConfigMethodClosureCall getPrioridad() {
				nextTokenSetter = this::setPrioridadToken;

				return ConfigMethodClosureCall.this;
			}

			public ConfigMethodClosureCall getComo() {
				nextTokenSetter = this::setConfigMapToken;

				return ConfigMethodClosureCall.this;
			}

			@SuppressWarnings("unchecked")
			private void setClassToken(Object o) {
				clazz = (Class<ConfigurationBase>) o;
			}

			@SuppressWarnings("unchecked")
			private void setConfigMapToken(Object o) {
				configMap = (Map<String, Object>) o;

				finished = true;
			}

			private void setModoToken(Object o) {
				modo_ = (String) o;
			}

			private void setPerfilToken(Object o) {
				perfil_ = (String) o;
			}

			@SuppressWarnings("unchecked")
			private void setCondicionToken(Object o) {
				condicion_ = (Closure<Boolean>) o;
			}

			private void setPrioridadToken(Object o) {
				prioridad_ = (Integer) o;
			}
		}
	}

	private void addConfiguration(Class<? extends ConfigurationBase> clazz, String perfilesConfiguracion,
			String modosConfiguracion, Closure<Boolean> condicion, Integer prioridad, ConfigurationBase config) {

		if (clazz != null) {

			if (condicion != null) {

				if ((perfilesConfiguracion != null) && (modosConfiguracion != null)) {

					String[] perfiles = perfilesConfiguracion.split(",");
					String[] modos = modosConfiguracion.split(",");

					if (!perfilModoCondicionConfigs.containsKey(clazz)) {
						perfilModoCondicionConfigs.put(clazz, new HashMap<>());
						perfilModoCondicionCondiciones.put(clazz, new HashMap<>());
					}

					for (String perfil : perfiles) {
						if (!perfilModoCondicionConfigs.get(clazz).containsKey(perfil)) {
							perfilModoCondicionConfigs.get(clazz).put(perfil, new HashMap<>());
							perfilModoCondicionCondiciones.get(clazz).put(perfil, new HashMap<>());
						}

						for (String modo : modos) {
							if (!perfilModoCondicionConfigs.get(clazz).get(perfil).containsKey(modo)) {
								perfilModoCondicionConfigs.get(clazz).get(perfil).put(modo, new ArrayList<>());
								perfilModoCondicionCondiciones.get(clazz).get(perfil).put(modo, new ArrayList<>());
							}

							while (perfilModoCondicionConfigs.get(clazz).get(perfil).get(modo).size() <= prioridad) {
								perfilModoCondicionConfigs.get(clazz).get(perfil).get(modo).add(null);
								perfilModoCondicionCondiciones.get(clazz).get(perfil).get(modo).add(null);
							}

							if (perfilModoCondicionConfigs.get(clazz).get(perfil).get(modo).get(prioridad) == null) {
								perfilModoCondicionConfigs.get(clazz).get(perfil).get(modo).set(prioridad, config);
								perfilModoCondicionCondiciones.get(clazz).get(perfil).get(modo).set(prioridad,
										condicion);

							} else {
								final String err = "No se pudo añadir la configuración porque ya existe otra con la misma clave: clase = "
										+ clazz.getSimpleName() + " perfil = " + perfil + " modo = " + modo
										+ " prioridad = " + prioridad;

								log.error(err);
								throw new ConfiguratorException(err);
							}
						}

					}

				} else {
					final String err = "Error añadiendo configuración " + clazz.getSimpleName()
							+ " : Cuando se informa la 'condición' es obligatorio informar el 'modo' y el 'perfil'";

					log.error(err);
					throw new ConfiguratorException(err);
				}

			} else if ((perfilesConfiguracion != null) && (modosConfiguracion != null)) {

				String[] perfiles = perfilesConfiguracion.split(",");
				String[] modos = modosConfiguracion.split(",");

				if (!perfilModoConfigs.containsKey(clazz)) {
					perfilModoConfigs.put(clazz, new HashMap<>());
				}
				for (String perfil : perfiles) {
					if (!perfilModoConfigs.get(clazz).containsKey(perfil)) {
						perfilModoConfigs.get(clazz).put(perfil, new HashMap<>());
					}

					for (String modo : modos) {
						if (!perfilModoConfigs.get(clazz).get(perfil).containsKey(modo)) {
							perfilModoConfigs.get(clazz).get(perfil).put(modo, config);
						} else {
							final String err = "Se ha sobreescrito la configuracion: " + clazz.getSimpleName()
									+ " para el perfil:" + perfil + " y el modo:" + modo;

							log.error(err);
							throw new ConfiguratorException(err);
						}
					}
				}

			} else if ((perfilesConfiguracion != null) && (modosConfiguracion == null)) {

				String[] perfiles = perfilesConfiguracion.split(",");

				if (!perfilConfigs.containsKey(clazz)) {
					perfilConfigs.put(clazz, new HashMap<>());
				}

				for (String perfil : perfiles) {

					if (!perfilConfigs.get(clazz).containsKey(perfil)) {
						perfilConfigs.get(clazz).put(perfil, config);
					} else {
						final String err = "Se ha sobreescrito la configuracion: " + clazz.getSimpleName()
								+ " para el perfil:" + perfil;

						log.error(err);
						throw new ConfiguratorException(err);
					}
				}

			} else if ((perfilesConfiguracion == null) && (modosConfiguracion == null)) {
				if (!classConfigs.containsKey(clazz)) {
					classConfigs.put(clazz, config);
				} else {
					final String err = "Se ha sobreescrito la configuracion: " + clazz.getSimpleName();

					log.error(err);
					throw new ConfiguratorException(err);
				}
			} else {
				final String err = "No es posible indicar el modo sin indicar el perfil: " + clazz.getSimpleName();

				log.error(err);
				throw new ConfiguratorException(err);
			}
		} else {
			final String err = "La clase de definición de la configuración es obligatoria.";

			log.error(err);
			throw new ConfiguratorException(err);
		}
	}

	/**
	 * Método para la recuperación de una configuración.
	 *
	 * Clase de configuración.
	 *
	 * @param configClass
	 *
	 *            Instancia con la configuración.
	 * @return ConfigurationBase Instancia con la configuración.
	 */
	@Override

	public <T extends ConfigurationBase> T getConfig(Class<T> configClass) {

		if (classConfigs.containsKey(configClass)) {
			@SuppressWarnings("unchecked")
			T configurationBase = (T) classConfigs.get(configClass);
			return configurationBase;
		} else {
			final String err = "La configuracion para :" + configClass + " no existe";

			log.error(err);
			throw new ConfigurationNotFoundException(err);
		}

	}

	/**
	 * Método para la recuperación de una configuración.
	 *
	 * @param configClass
	 *            Clase de configuración.
	 *
	 * @param perfil
	 *            Perfil de configuración
	 *
	 * @return ConfigurationBase Instancia con la configuración.
	 */
	@Override
	public <T extends ConfigurationBase> T getConfig(Class<T> configClass, String perfil) {

		if (perfilConfigs.containsKey(configClass) && perfilConfigs.get(configClass).containsKey(perfil)) {

			@SuppressWarnings("unchecked")
			T configurationBase = (T) perfilConfigs.get(configClass).get(perfil);
			return configurationBase;

		} else {
			final String err = "El perfil: " + perfil + " para la configuracion:" + configClass + " no existe";

			log.error(err);
			throw new ConfigurationNotFoundException(err);
		}

	}

	/**
	 * Método para la recuperación de una configuración.
	 *
	 * @param configClass
	 *            Clase de configuración.
	 *
	 * @param perfil
	 *            Perfil de configuración
	 *
	 * @param modo
	 *            Modo de configuración
	 *
	 * @return ConfigurationBase Instancia con la configuración.
	 */
	@Override
	public <T extends ConfigurationBase> T getConfig(Class<T> configClass, String perfil, String modo) {

		if (perfilModoConfigs.containsKey(configClass) && perfilModoConfigs.get(configClass).containsKey(perfil)
				&& perfilModoConfigs.get(configClass).get(perfil).containsKey(modo)) {

			@SuppressWarnings("unchecked")
			T configurationBase = (T) perfilModoConfigs.get(configClass).get(perfil).get(modo);
			return configurationBase;

		} else {
			final String err = "El modo:" + modo + " para el perfil: " + perfil + " y para la configuracion:"
					+ configClass + " no existe";

			log.error(err);
			throw new ConfigurationNotFoundException(err);

		}

	}

	/**
	 * Método para la recuperación de una configuración.
	 *
	 * @param configClass
	 *            Clase de configuración.
	 *
	 * @param perfil
	 *            Perfil de configuración
	 *
	 * @param modo
	 *            Modo de configuración
	 *
	 * @param objeto
	 *            objeto complejo para recuperación de configuración desde una
	 *            closure y por prioridad.
	 *
	 * @return ConfigurationBase Instancia con la configuración.
	 */
	@Override
	public <T extends ConfigurationBase> T getConfig(Class<T> configClass, String perfil, String modo, Object objeto) {

		if (perfilModoCondicionCondiciones.containsKey(configClass)
				&& perfilModoCondicionCondiciones.get(configClass).containsKey(perfil)
				&& perfilModoCondicionCondiciones.get(configClass).get(perfil).containsKey(modo)) {

			final List<Closure<Boolean>> condiciones = perfilModoCondicionCondiciones.get(configClass).get(perfil)
					.get(modo);

			for (int prioridad = 0; prioridad < condiciones.size(); prioridad++) {
				Closure<Boolean> condicion = condiciones.get(prioridad);

				if (condicion.call(objeto)) {
					@SuppressWarnings("unchecked")
					T r = (T) perfilModoCondicionConfigs.get(configClass).get(perfil).get(modo).get(prioridad);
					return r;
				}
			}

			final String err = "No existen ninguna configuración para: clase = " + configClass.getSimpleName()
					+ " perfil = " + perfil + " modo = " + modo + " que cumpla la condición para objeto = " + objeto;

			log.error(err);
			throw new ConfigurationNotFoundException(err);

		} else {
			final String err = "No existen configuraciones con condiciones para: clase = " + configClass.getSimpleName()
					+ " perfil = " + perfil + " modo = " + modo;

			log.error(err);
			throw new ConfigurationNotFoundException(err);
		}
	}

	@Override
	public void reload() {
		throw new ConfiguratorException("Esta funcionalidad aun no está disponible");
	}
}
