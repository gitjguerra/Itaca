package config

import com.csi.itaca.people.api.ConfiguracionModuloPersonas

config ConfiguracionModuloPersonas.class como (
	personasDuplicadasPermitidas: false,
	clavePrimariaLogicaPersonaFisica:
		(ConfiguracionModuloPersonas.ClavePrimariaLogicaPersonaFisica) [codigoIdentificacionEsClaveLogica: true,
																		tipoIdentificacionEsClaveLogica: true,
																		fechaNacimientoEsClaveLogica: true],
	clavePrimariaLogicaPersonaJuridica:
		(ConfiguracionModuloPersonas.ClavePrimariaLogicaPersonaJuridica) [codigoIdentificacionEsClaveLogica: true,
																		  tipoIdentificacionEsClaveLogica: true]
)
