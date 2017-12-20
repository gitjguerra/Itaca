package com.csi.itaca.tools.utils.beaner;

import org.dozer.DozerConverter;

import java.time.LocalTime;

/**
 * Clase de conversion entre LocalTimes
 * @author cmartin
 *
 */
public class LocalTimeCustomBeanerConverter extends DozerConverter<LocalTime, LocalTime> {

	public LocalTimeCustomBeanerConverter() {
		super(LocalTime.class, LocalTime.class);
	}

	@Override
	public LocalTime convertTo(LocalTime source, LocalTime destination) {
		if(source == null) {
			return null;
		}
		return LocalTime.from(source);
	}

	@Override
	public LocalTime convertFrom(LocalTime source, LocalTime destination) {
		if(source == null) {
			return null;
		}
		return LocalTime.from(source);
	}

}
