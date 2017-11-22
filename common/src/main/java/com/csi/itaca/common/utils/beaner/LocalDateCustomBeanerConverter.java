package com.csi.itaca.common.utils.beaner;

import org.dozer.DozerConverter;

import java.time.LocalDate;

/**
 * Clase de conversión entre LocalDates. 
 * @author cmartin
 *
 */
public class LocalDateCustomBeanerConverter extends DozerConverter<LocalDate, LocalDate> {

	public LocalDateCustomBeanerConverter() {
		super(LocalDate.class, LocalDate.class);
	}

	@Override
	public LocalDate convertTo(LocalDate source, LocalDate destination) {
		if(source == null) {
			return null;
		}
		return LocalDate.from(source);
	}

	@Override
	public LocalDate convertFrom(LocalDate source, LocalDate destination) {
		if(source == null) {
			return null;
		}
		return LocalDate.from(source);
	}

}
