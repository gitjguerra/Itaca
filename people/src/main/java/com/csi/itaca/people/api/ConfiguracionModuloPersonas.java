package com.csi.itaca.people.api;

import com.csi.itaca.config.model.ConfigurationBase;
import lombok.*;

@Getter
@Setter
@ToString
public class ConfiguracionModuloPersonas extends ConfigurationBase {

	private Boolean personasDuplicadasPermitidas = false;
	private ClavePrimariaLogicaPersonaFisica clavePrimariaLogicaPersonaFisica = new ClavePrimariaLogicaPersonaFisica();
	private ClavePrimariaLogicaPersonaJuridica clavePrimariaLogicaPersonaJuridica = new ClavePrimariaLogicaPersonaJuridica();
	
	@Getter
	@Setter
	@ToString
	@NoArgsConstructor
	@EqualsAndHashCode
	public static class ClavePrimariaLogicaPersonaFisica {
		private Boolean codigoIdentificacionEsClaveLogica = true;
		private Boolean tipoIdentificacionEsClaveLogica = false;
		private Boolean referenciaExternaEsClaveLogica = false;
		private Boolean fechaNacimientoEsClaveLogica = false;
	}
	
	@Getter
	@Setter
	@ToString
	@NoArgsConstructor
	@EqualsAndHashCode
	public static class ClavePrimariaLogicaPersonaJuridica {
		private Boolean codigoIdentificacionEsClaveLogica = true;
		private Boolean tipoIdentificacionEsClaveLogica = false;
	}

}
