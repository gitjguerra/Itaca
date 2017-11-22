package com.csi.itaca.common.utils.beaner;

import org.dozer.DozerConverter;

import java.time.LocalDateTime;

/**
 * Clase de conversion entre LocalDateTimes
 * @author cmartin
 *
 */
public class LocalDateTimeCustomBeanerConverter extends DozerConverter<LocalDateTime, LocalDateTime> {

	public LocalDateTimeCustomBeanerConverter() {
		super(LocalDateTime.class, LocalDateTime.class);
	}

	@Override
	public LocalDateTime convertTo(LocalDateTime source, LocalDateTime destination) {
		if(source == null) {
			return null;
		}
		return LocalDateTime.from(source);
	}

	@Override
	public LocalDateTime convertFrom(LocalDateTime source, LocalDateTime destination) {
		if(source == null) {
			return null;
		}
		return LocalDateTime.from(source);
	}

}
