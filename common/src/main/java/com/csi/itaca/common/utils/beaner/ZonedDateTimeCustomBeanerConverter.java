package com.csi.itaca.common.utils.beaner;

import org.dozer.DozerConverter;

import java.time.ZonedDateTime;

/**
 * Clase de conversion entre ZonedDateTime
 * @author cmartin
 *
 */
public class ZonedDateTimeCustomBeanerConverter extends DozerConverter<ZonedDateTime, ZonedDateTime> {

	public ZonedDateTimeCustomBeanerConverter() {
		super(ZonedDateTime.class, ZonedDateTime.class);
	}

	@Override
	public ZonedDateTime convertTo(ZonedDateTime source, ZonedDateTime destination) {
		if(source == null) {
			return null;
		}
		return ZonedDateTime.from(source);
	}

	@Override
	public ZonedDateTime convertFrom(ZonedDateTime source, ZonedDateTime destination) {
		if(source == null) {
			return null;
		}
		return ZonedDateTime.from(source);
	}

}
