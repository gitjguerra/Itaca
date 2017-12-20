package com.csi.itaca.tools.utils.beaner;

import lombok.*;
import lombok.extern.apachecommons.CommonsLog;
import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.FieldsMappingOptions;
import org.dozer.loader.api.TypeMappingBuilder;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;
import org.springframework.util.SystemPropertyUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.time.ZoneId;
import java.time.temporal.Temporal;
import java.util.*;
import java.util.Map.Entry;

/**
 * Implementacion de interfaz beaner que se encarga, a través de la libreria
 * Dozer, de realizar copia de datos entre objetos java.
 *
 * @author cmartin
 * 
 **/
@CommonsLog
@Service
public class BeanerImpl implements Beaner {

	private Map<Class<?>, List<Class<?>>> inheritanceMap = new HashMap<>();
	private Map<Class<?>, List<Class<?>>> extensionsMap = new HashMap<>();

	private List<String> validPackages = new ArrayList<>();

	private List<String> mappingXMLFiles = new ArrayList<>();

	private Map<MappingCacheKey, DozerBeanMapper> mapperCache = new HashMap<>();

	public BeanerImpl() {
        mappingXMLFiles.add("dozer-custom-convert.xml");
	}

	/**
	 * Metodo que busca en el código los beans de java que se pueden copiar en
	 * itaca.
	 * 
	 * @param packagesToScan Ruta del package o lista de packages en el que buscar clases.
	 **/
	public void addPackageToScan(String... packagesToScan) {
        log.debug("**************** BEANER: MAPEANDO CLASES *******************************");
        for(String packageToScan : packagesToScan) {
            log.debug("\t" + packageToScan);
            try {
                validPackages.add(packageToScan);

                for (Class<?> clazz : findMyTypes(packageToScan)) {
                    if (clazz.isInterface())
                        continue;
                    if (clazz.getSuperclass().isAssignableFrom(Object.class))
                        continue;

                    Class<?> superClass = clazz.getSuperclass();

                    if (clazz.getAnnotation(Extension.class) != null) {

                        Extension extensionAnnotation = clazz.getAnnotation(Extension.class);
                        Class<?> extensionClass = extensionAnnotation.of() == Object.class ? superClass
                                : extensionAnnotation.of();

                        if (extensionClass != superClass) {

                            log.trace("\t\tExtension/Subtipo - " + clazz.getSimpleName() + " -> "
                                    + extensionClass.getSimpleName() + "/" + superClass.getSimpleName());

                            if (inheritanceMap.containsKey(superClass)) {
                                inheritanceMap.get(superClass).add(clazz);
                            } else {
                                List<Class<?>> subtipos = new ArrayList<>();
                                subtipos.add(clazz);
                                inheritanceMap.put(superClass, subtipos);
                            }
                        } else {
                            log.trace("\t\tExtension - " + clazz.getSimpleName() + " -> " + extensionClass.getSimpleName());
                        }

                        if (extensionsMap.containsKey(extensionClass)) {
                            extensionsMap.get(extensionClass).add(clazz);
                        } else {
                            List<Class<?>> subtipos = new ArrayList<>();
                            subtipos.add(clazz);
                            extensionsMap.put(extensionClass, subtipos);
                        }

                        continue;
                    }

                    log.trace("\t\tSubtipo - " + clazz.getSimpleName() + " -> " + superClass.getSimpleName());

                    if (inheritanceMap.containsKey(superClass)) {
                        inheritanceMap.get(superClass).add(clazz);
                    } else {
                        List<Class<?>> subtipos = new ArrayList<>();
                        subtipos.add(clazz);
                        inheritanceMap.put(superClass, subtipos);
                    }

                }
            } catch (ClassNotFoundException | IOException e) {
                final String error = "Ha ocurrido un error al leer las clases del package " + packageToScan;
                throw new BeanerException(error, e);
            }
        }
		log.debug("************************************************************************");
	}

	@Override
	public <T> T transform(Object source, Class<T> clazz) {

		if (source == null) {
			return null;
		}

		log.trace("::::::::::::::::::::::::::::::::: TRANSFORM :::::::::::::::::::::::::::::::::");
		log.trace(source.getClass().getSimpleName() + " -> " + clazz.getSimpleName());

		@SuppressWarnings("unchecked")
		Class<T> realDestinationClass = (Class<T>) getRealDestinationClass(source.getClass(), clazz);

		DozerBeanMapper mapper = getMapper(source.getClass(), realDestinationClass);
		T ret = mapper.map(source, realDestinationClass);

		completarBeans(source, ret);

		log.trace(":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
		return ret;
	}

	private <T> void completarBeans(Object source, T ret) {
		Class<?> sourceClass = source.getClass();
		Class<?> destinationClass = ret.getClass();

		for (Field fA : getAllFields(sourceClass)) {

			if (isBasicType(fA.getType()))
				continue;

			Field fB = getField(destinationClass, fA.getName());
			if (fB == null)
				continue;

			if (!fA.getType().isAssignableFrom(List.class) && !fA.getType().isAssignableFrom(Map.class)) {
				fA.setAccessible(true);
				fB.setAccessible(true);
				try {
					Object result = transform(fA.get(source), fB.getType());
					fB.set(ret, result);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}

	}

	@Override
	public <T> List<T> transform(List<?> source, Class<T> clazz) {

		ArrayList<T> list = new ArrayList<>();

		if (source == null) {
			return list;
		}

		for (Object o : source) {

			list.add(transform(o, clazz));
		}

		return list;
	}

	@Override
	public void update(Object source, Object destination) {
		final String error = "La funcionalidad update no esta implementada. Hablar con arquitectura";
		throw new BeanerException(error);
	}

	private DozerBeanMapper getMapper(Class<?> sourceClass, Class<?> destinationClass) {

		MappingCacheKey key = new MappingCacheKey(sourceClass, destinationClass);

		if (mapperCache.containsKey(key)) {
			return mapperCache.get(key);
		}

		synchronized (destinationClass) {
			if (mapperCache.containsKey(key)) {
				return mapperCache.get(key);

			} else {

				log.trace("Creando mapper para " + sourceClass.getSimpleName() + " <-> "
						+ destinationClass.getSimpleName());
				Map<String, EstruturaMappings> estructura = new HashMap<>();
				List<ClassMapping> classMappings = findAndPrepareMappings(sourceClass, destinationClass, null, "base",
						estructura);

				log.trace("--------------------------------- ESTRUCTURA ---------------------------------");
				printEstructura(estructura, "base", 0);
				log.trace("------------------------------------------------------------------------------");

				DozerBeanMapper mapper = createMappers(classMappings);
				mapperCache.put(key, mapper);
				mapperCache.put(new MappingCacheKey(destinationClass, sourceClass), mapper);

				return mapper;
			}
		}
	}

	private void printEstructura(Map<String, EstruturaMappings> estructura, String atributeName, int lvl) {
		String tabs = "";
		for (int i = 0; i < lvl; i++) {
			tabs += "\t";
		}
		for (Entry<String, EstruturaMappings> kv : estructura.entrySet()) {
			EstruturaMappings e = kv.getValue();
			if (!kv.getKey().equals(atributeName))
				return;

			log.trace(tabs + "MAP :: " + e.mapping.classA.getSimpleName() + " <-> " + e.mapping.classB.getSimpleName());

			for (AttributeMapping mapeo : e.mapping.mapeoDeAtributos) {
				log.trace(tabs + "\t[" + mapeo.getAtribute() + "] " + mapeo.getSupertipoA().getSimpleName() + " <-> "
						+ mapeo.getSupertipoB().getSimpleName());
				for (int i = 0; i < mapeo.getSubtipos().size(); i++) {
					log.trace(tabs + "\t\t" + mapeo.getSubtipos().get(i).tipoA.getSimpleName() + " <-> "
							+ mapeo.getSubtipos().get(i).tipoB.getSimpleName());
				}
				if (e.subEstruturas.get(mapeo.getAtribute()) != null) {
					printEstructura(e.subEstruturas, mapeo.getAtribute(), lvl + 2);
				}
			}
		}
	}

	private List<ClassMapping> findAndPrepareMappings(Class<?> sourceClass, Class<?> destinationClass,
			WorkInProgress workInProgress, String atributeName, Map<String, EstruturaMappings> estructura) {

		log.trace("findAndPrepareMappings for " + sourceClass.getSimpleName() + " and "
				+ destinationClass.getSimpleName());
		List<ClassMapping> classMappings = new ArrayList<>();

		List<String> mappedAttributes = new ArrayList<>();

		if (workInProgress == null) {
			workInProgress = new WorkInProgress();
		}

		if (workInProgress.contains(sourceClass, destinationClass)) {
			return classMappings;
		}

		workInProgress.add(sourceClass, destinationClass);

		Map<String, EstruturaMappings> subEstructura = new HashMap<>();

		List<AttributeMapping> mapeoDeAtributos = new ArrayList<>();
		boolean attribFound = false;
		for (Field fA : getAllFields(sourceClass)) {

			if (isBasicType(fA.getType()))
				continue;

			Field fB = getField(destinationClass, fA.getName());
			if (fB == null)
				continue;
			if (fA.getName().startsWith("_persistence"))
				continue;

			if (mappedAttributes.contains(fA.getName()))
				continue;
			mappedAttributes.add(fA.getName());

			attribFound = true;
			Class<?> fieldTypeA = null;
			Class<?> fieldTypeB = null;

			if (fA.getType().isAssignableFrom(List.class) || fA.getType().isAssignableFrom(Map.class)) {
				BeanerConfig annotationA = fA.getAnnotation(BeanerConfig.class);
				BeanerConfig annotationB = fB.getAnnotation(BeanerConfig.class);
				if (annotationA == null) {
					final String error = "Falta anotacion @BeanerConfig en " + sourceClass + " [" + fA.getName() + "]";
					log.error(error);
					throw new BeanerException(error);
				}
				if (annotationB == null) {
					final String error = "Falta anotacion @BeanerConfig en " + destinationClass + " [" + fB.getName()
							+ "]";
					log.error(error);
					throw new BeanerException(error);
				}

				fieldTypeA = annotationA.contentAs();
				fieldTypeB = annotationB.contentAs();
			} else {
				fieldTypeA = fA.getType();
				fieldTypeB = fB.getType();
			}
			log.trace("\tCommon atribute [" + fA.getName() + "] " + fieldTypeA.getSimpleName() + " | "
					+ fieldTypeB.getSimpleName());

			List<AsociacionDeTipos> subtiposCompatibles = new ArrayList<>();

			for (Class<?> subtipoA : getSubtipos(fieldTypeA)) {

				for (Class<?> subtipoB : getSubtipos(fieldTypeB)) {
					log.trace("\tHassCommonInterface? " + subtipoA.getSimpleName() + " | " + subtipoB.getSimpleName());
					if (hasCommonInterface(subtipoA, subtipoB)) {
						log.trace("\t\ttrue!");
						List<ClassMapping> subMappings = findAndPrepareMappings(subtipoA, subtipoB, workInProgress,
								fA.getName(), subEstructura);
						classMappings.addAll(subMappings);
						subtiposCompatibles.add(new AsociacionDeTipos(subtipoA, subtipoB));
					}
				}
			}
			if (!subtiposCompatibles.isEmpty()) {
				mapeoDeAtributos.add(new AttributeMapping(fA.getName(), fieldTypeA, fieldTypeB, subtiposCompatibles));

			} else {
				log.trace("\tNo Mappings needed [" + fA.getName() + "] " + fieldTypeA.getSimpleName() + " - "
						+ fieldTypeB.getSimpleName());
			}
		}
		if (!attribFound)
			log.trace("\tNo attribute found");

		if (!mapeoDeAtributos.isEmpty()) {
			ClassMapping classMapping = new ClassMapping(sourceClass, destinationClass, mapeoDeAtributos);
			classMappings.add(classMapping);
			estructura.put(atributeName, new EstruturaMappings(classMapping, subEstructura));
			// log.debug(":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
			log.trace("MAP :: " + sourceClass.getSimpleName() + " <-> " + destinationClass.getSimpleName());

			for (AttributeMapping mapeo : mapeoDeAtributos) {
				// log.debug("\t[" + mapeo.getAtribute() + "] " +
				// mapeo.getSupertipoA().getSimpleName() + " <-> " +
				// mapeo.getSupertipoB().getSimpleName());
				for (int i = 0; i < mapeo.getSubtipos().size(); i++) {
					// log.debug("\t\t" +
					// mapeo.getSubtipos().get(i).tipoA.getSimpleName() + " <->
					// " + mapeo.getSubtipos().get(i).tipoB.getSimpleName());
				}
			}
		}
		return classMappings;
	}

	Class<?> getRealDestinationClass(Class<?> sourceClass, Class<?> destinationClass) {

		for (Class<?> subtipo : getSubtipos(destinationClass)) {
			for (Class<?> interfaceA : subtipo.getInterfaces()) {
				for (Class<?> interfaceB : sourceClass.getInterfaces()) {
					if (interfaceA == interfaceB && isValidInterface(interfaceA)) {
						return subtipo;
					}
				}
			}
		}

		for (Class<?> extension : getExtensions(sourceClass)) {
			for (Class<?> interfaceA : extension.getInterfaces()) {
				for (Class<?> subtipo : getSubtipos(destinationClass)) {
					for (Class<?> interfaceB : subtipo.getInterfaces()) {
						if (interfaceA == interfaceB && isValidInterface(interfaceA)) {
							return subtipo;
						}
					}
				}
			}
		}

		for (Class<?> subtipo : getSubtipos(destinationClass)) {
			for (Class<?> extension : getExtensions(subtipo)) {
				for (Class<?> interfaceA : extension.getInterfaces()) {
					for (Class<?> interfaceB : sourceClass.getInterfaces()) {
						if (interfaceA == interfaceB && isValidInterface(interfaceA)) {
							return subtipo;
						}
					}
				}
			}
		}

		return destinationClass;
	}

	private List<Class<?>> getExtensions(Class<?> destinationClass) {
		List<Class<?>> ret = new ArrayList<>();
		if (extensionsMap.containsKey(destinationClass)) {
			ret.addAll(extensionsMap.get(destinationClass));

			for (Class<?> extension : extensionsMap.get(destinationClass)) {
				ret.addAll(getExtensions(extension));
			}
		}
		return ret;
	}

	private Field getField(Class<?> clazz, String fieldName) {

		try {
			return clazz.getDeclaredField(fieldName);
		} catch (NoSuchFieldException | SecurityException e) {
			if (clazz.getSuperclass() != null) {
				return getField(clazz.getSuperclass(), fieldName);
			}
		}
		return null;
	}

	private List<Field> getAllFields(Class<?> clazz) {
		List<Field> ret = new ArrayList<>();
		ret.addAll(Arrays.asList(clazz.getDeclaredFields()));
		if (clazz.getSuperclass() != null) {
			ret.addAll(getAllFields(clazz.getSuperclass()));
		}
		return ret;
	}

	private DozerBeanMapper createMappers(List<ClassMapping> classMappings) {

		log.trace("Benaer ClassMappings: " + classMappings);

		DozerBeanMapper mapper = new DozerBeanMapper(mappingXMLFiles);

		BeanMappingBuilder builder = new BeanMappingBuilder() {
			@Override
			protected void configure() {
				TypeMappingBuilder tmb;
				for (ClassMapping classMapping : classMappings) {
					tmb = mapping(classMapping.classA, classMapping.classB);

					for (AttributeMapping attributeMapping : classMapping.mapeoDeAtributos) {

						Class<?>[] subtiposA = new Class<?>[attributeMapping.subtipos.size()];
						Class<?>[] subtiposB = new Class<?>[attributeMapping.subtipos.size()];
						for (int i = 0; i < attributeMapping.subtipos.size(); i++) {
							subtiposA[i] = attributeMapping.subtipos.get(i).tipoA;
							subtiposB[i] = attributeMapping.subtipos.get(i).tipoB;
						}

						tmb.fields(attributeMapping.getAtribute(), attributeMapping.getAtribute(),
								FieldsMappingOptions.hintA(subtiposA), FieldsMappingOptions.hintB(subtiposB));
					}
				}

			}
		};

		if (classMappings.isEmpty()) {
			log.trace("No se han encontrado posibles mapeos");
		}
		mapper.addMapping(builder);

		return mapper;
	}

	private List<Class<?>> getSubtipos(Class<?> clazz) {
		List<Class<?>> ret = new ArrayList<>();

		ret.addAll(getSubtiposRecursivo(clazz));

		Collections.reverse(ret);
		ret.add(clazz);
		return ret;
	}

	private List<Class<?>> getSubtiposRecursivo(Class<?> clazz) {
		List<Class<?>> ret = new ArrayList<>();
		if (!inheritanceMap.containsKey(clazz)) {
			return ret;
		}
		for (Class<?> subtipo : inheritanceMap.get(clazz)) {
			ret.add(subtipo);
		}

		for (Class<?> subtipo : inheritanceMap.get(clazz)) {
			for (Class<?> c : getSubtiposRecursivo(subtipo)) {
				ret.add(c);
			}
		}
		return ret;
	}

	private boolean hasCommonInterface(Class<?> classA, Class<?> classB) {

		log.trace("\t\tBuscando interfaz comun " + classA.getSimpleName() + " - " + classB.getSimpleName());
		for (Class<?> interfaceA : classA.getInterfaces()) {
			if (!isValidInterface(interfaceA))
				continue;
			for (Class<?> interfaceB : classB.getInterfaces()) {
				if (interfaceA == interfaceB) {
					log.trace("\t\tInterfaz commun " + interfaceA.getSimpleName());
					return true;
				}
			}
		}

		log.trace("\t\tBuscando interfaz de extension " + classA.getSimpleName() + " - " + classB.getSimpleName());
		for (Class<?> interfaceB : classB.getInterfaces()) {
			if (!isValidInterface(interfaceB))
				continue;
			if (interfaceB.isAssignableFrom(classA)) {
				log.trace("\t\tInterfaz commun de extensionA " + interfaceB.getSimpleName());
				if (extensionsMap.containsKey(classB)) {
					return true;
				} else {
					log.trace("\t\t\t" + classB.getSimpleName() + " no tiene extensiones");
				}
			}
		}

		for (Class<?> interfaceA : classA.getInterfaces()) {
			if (!isValidInterface(interfaceA))
				continue;
			if (interfaceA.isAssignableFrom(classB)) {
				log.trace("\t\tInterfaz commun de extensionB " + interfaceA.getSimpleName());
				if (extensionsMap.containsKey(classA)) {
					return true;
				} else {
					log.trace("\t\t\t" + classA.getSimpleName() + " no tiene extensiones");
				}
			}
		}

		return false;
	}

	private boolean isValidInterface(Class<?> clazz) {
		for (String validPackage : validPackages) {
			if (clazz.getName().contains(validPackage))
				return true;
		}
		return false;
	}

	private boolean isBasicType(Class<?> clazz) {
		return clazz.isPrimitive() || String.class.isAssignableFrom(clazz) || Number.class.isAssignableFrom(clazz)
				|| Boolean.class.isAssignableFrom(clazz) || Date.class.isAssignableFrom(clazz)
				|| ZoneId.class.isAssignableFrom(clazz) || Temporal.class.isAssignableFrom(clazz)
				|| clazz.equals(Object.class) || byte[].class.isAssignableFrom(clazz);
	}

	private List<Class<?>> findMyTypes(String basePackage) throws IOException, ClassNotFoundException {

		ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
		MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resourcePatternResolver);

		List<Class<?>> candidates = new ArrayList<>();
		String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + resolveBasePackage(basePackage)
				+ "/" + "**/*.class";
		Resource[] resources = resourcePatternResolver.getResources(packageSearchPath);
		for (Resource resource : resources) {
			if (resource.isReadable()) {
				MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
				candidates.add(Class.forName(metadataReader.getClassMetadata().getClassName()));
			}
		}
		return candidates;
	}

	private String resolveBasePackage(String basePackage) {
		return ClassUtils.convertClassNameToResourcePath(SystemPropertyUtils.resolvePlaceholders(basePackage));
	}

	@Getter
	@Setter
	@ToString
	@NoArgsConstructor
	@AllArgsConstructor
	private static class EstruturaMappings {
		private ClassMapping mapping;
		private Map<String, EstruturaMappings> subEstruturas = new HashMap<>();
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@ToString
	private static class ClassMapping {
		private Class<?> classA;
		private Class<?> classB;
		private List<AttributeMapping> mapeoDeAtributos;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@ToString
	private static class AttributeMapping {
		private String atribute;
		private Class<?> supertipoA;
		private Class<?> supertipoB;
		private List<AsociacionDeTipos> subtipos = new ArrayList<>();

	}

	@AllArgsConstructor
	private static class AsociacionDeTipos {
		private Class<?> tipoA;
		private Class<?> tipoB;
	}

	@AllArgsConstructor
	@EqualsAndHashCode
	@NoArgsConstructor
	private static class MappingCacheKey {
		private Class<?> classA;
		private Class<?> classB;
	}

	private class WorkInProgress {
		private Set<Map<Class<?>, Class<?>>> progress = new HashSet<>();

		public void add(Class<?> classA, Class<?> classB) {
			Map<Class<?>, Class<?>> pair = new HashMap<>();
			pair.put(classA, classB);
			progress.add(pair);
		}

		public boolean contains(Class<?> classA, Class<?> classB) {
			Map<Class<?>, Class<?>> pair = new HashMap<>();
			Map<Class<?>, Class<?>> inverse = new HashMap<>();

			pair.put(classA, classB);
			inverse.put(classB, classA);

			if (progress.contains(pair))
				return true;
			if (progress.contains(inverse))
				return true;
			return false;
		}
	}

	public class BeanerException extends RuntimeException {
		private static final long serialVersionUID = 4372379797274862553L;

		public BeanerException(String message, Throwable e) {
			super(message, e);
		}

		public BeanerException(String message) {
			super(message);
		}
	}
}
