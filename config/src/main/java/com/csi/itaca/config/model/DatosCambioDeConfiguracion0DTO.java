package com.csi.itaca.config.model;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class DatosCambioDeConfiguracion0DTO implements DatosCambioDeConfiguracion0 {

	private Class<? extends ConfigurationBase> claseConfiguracion;
	
	private String modulo;
	
	private String tipo;
	
	private String campo;
	
	private Object valor;
}
