package com.csi.itaca.common.utils.beaner;

import org.dozer.DozerConverter;

import java.time.ZoneId;

/**
 * Clase de conversion entre ZoneIds.
 * @author cmartin
 *
 */
public class ZoneIdCustomBeanerConverter extends DozerConverter<ZoneId, ZoneId> {

	public ZoneIdCustomBeanerConverter() {
		super(ZoneId.class, ZoneId.class);
	}

	@Override
	public ZoneId convertTo(ZoneId source, ZoneId destination) {
		if(source == null) {
			return null;
		}
		return ZoneId.of(source.getId());
	}

	@Override
	public ZoneId convertFrom(ZoneId source, ZoneId destination) {
		if(source == null) {
			return null;
		}
		return ZoneId.of(source.getId());
	}

}
