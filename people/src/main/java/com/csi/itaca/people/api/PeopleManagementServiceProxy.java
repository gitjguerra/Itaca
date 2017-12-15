package com.csi.itaca.people.api;

import com.csi.itaca.people.model.dto.PersonDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PeopleManagementServiceProxy {

    String ID_PARAM      = "id";


    // end point URLs
    String RESOURCE                 = "/person";
    String GET_PERSON                 = RESOURCE + "/get";


    // people

    /**
     * Gets a person
     * @param id the person id
     * @return a response body containing the requested person json object.
     */
    ResponseEntity getPerson(Long id);

    /*
    ItacaAPIResponse<List<? extends Persona1DTO>> buscarPersona(FiltroBuscadorAltaPersonaDTO filtro);

    ItacaAPIResponse<List<? extends DetallePersona1DTO>> buscarDetallesPersonasDuplicadas(FiltroPersonasPaginaOrden peticion);

    ItacaAPIResponse<Long> countDetallesPersonasDuplicadas(FiltroBuscadorAltaPersonaDTO parametros);

    ItacaAPIResponse<List<? extends DetallePersona1DTO>> listDetallePersonas(FiltroDetallePersonaPaginaOrden peticion);

    ItacaAPIResponse<Long> countDetallePersonas(FiltroBuscadorPersonaDTO filtro);

    ItacaAPIResponse<Persona1DTO> saveOrUpdatePersona1(Persona1DTO dto, Boolean personasDuplicadasPermitidas);

    ItacaAPIResponse<DetallePersona1DTO> getDetallePersona(Long idDetalle);



    //identifications

    ItacaAPIResponse<Boolean> buscarRefExterna(String refExterna);

    ItacaAPIResponse<List<? extends Identificador0DTO>> listIdentificadores(Long idDetallePersona);

    public ItacaAPIResponse<List<? extends Identificador0DTO>> getidentificadoresPersona(FiltroDetallePersonaIdentificadorPaginaOrden filtro);

    ItacaAPIResponse<Long> countIdentificadores(Long idDetallePersona);

    ItacaAPIVoidResponse deleteIdentificador(Long id) throws IdentificadorNoExisteException;

    ItacaAPIResponse<Identificador0DTO> saveOrUpdateIdentificador(Identificador0DTO identificador);

    ItacaAPIResponse<Identificador0DTO> getIdentificador(Long idIdentificador);


    //addresses

    ItacaAPIResponse<List<? extends RelDetPersonaDireccion1DTO>> listDireccionesPersonas(Long idDetallePersona);

    public ItacaAPIResponse<List<? extends RelDetPersonaDireccion1DTO>> getDireccionesPersona(FiltroDetallePersonaDireccionPaginaOrden filtro);

    ItacaAPIVoidResponse deleteDireccion(Long id) throws DireccionNoExisteException, DireccionDeContacto;

    ItacaAPIResponse<RelDetPersonaDireccion1DTO> saveOrUpdateDireccion(RelDetPersonaDireccion1DTO direccion);

    ItacaAPIResponse<Long> countDireccionesPersonas(Long idDetallePersona);


    // contacts

    ItacaAPIResponse<List<? extends Contacto0DTO>> listContactos(Long idDetallePersona);

    ItacaAPIResponse<Contacto0DTO> getContacto(Long idContacto);

    public ItacaAPIResponse<List<? extends Contacto0DTO>> getContactoPersona(FiltroDetallePersonaContactoPaginaOrden filtro);

    ItacaAPIVoidResponse deleteContacto(Long idContacto) throws ContactoNoExisteException;

    ItacaAPIResponse<Contacto0DTO> saveOrUpdateContacto(Contacto0DTO contacto);

    ItacaAPIResponse<Long> countContactos(Long idDetallePersona);



    // nationalities

    ItacaAPIResponse<List<? extends Nacionalidad0DTO>> getNacionalidades(FiltroNacionalidadPaginaOrden peticion);

    ItacaAPIVoidResponse deleteNacionalidad(Long idNacionalidad);

    ItacaAPIResponse<Nacionalidad0DTO> saveOrUpdateNacionalidad(Nacionalidad0DTO nacionalidad,
                                                                Boolean actualizarDuplicado) throws NacionalidadDuplicadaException;

    ItacaAPIResponse<Long> countNacionalidad(Long idDetallePersona);


    // account

    ItacaAPIResponse<CuentaBancaria0DTO> saveOrUpdateCuentaBancaria(CuentaBancaria0DTO CuentaBancaria);

    ItacaAPIResponse<List<? extends CuentaBancaria0DTO>> getCuentaBancaria(FiltroDetallePersonaCuentaPaginaOrden peticion);

    ItacaAPIResponse<Long> countCuentaBancaria(Long idDetallePersona);

    ItacaAPIResponse<List<? extends Banco0DTO>> listBancos();

    ItacaAPIResponse<List<? extends TipoCuenta0DTO>> listTipoCuenta();

    ItacaAPIResponse<List<? extends ClasificacionCuenta0DTO>> listClasificacionCuenta();

    ItacaAPIResponse<TarjetaBancaria0DTO> saveOrUpdateTarjetasBancarias(TarjetaBancaria0DTO tarjetaBancaria);

    ItacaAPIResponse<Long> countTarjetaBancaria(Long idDetallePersona);

    public ItacaAPIResponse<List<? extends TarjetaBancaria0DTO>> getTarjetaBancaria(FiltroDetallePersonaTarjetaPaginaOrden filtro);

    ItacaAPIResponse<List<? extends TipoTarjeta0DTO>> listTiposTarjeta();



    //////PERSONAS RELACIONADAS //////

    ItacaAPIResponse<PersonaRelacionada0DTO> saveOrUpdatePersonaRelacion(PersonaRelacionada0DTO personaRelacionada);

    ItacaAPIResponse<Long> countPersonarelacion(Long idPersona);

    public ItacaAPIResponse<List<? extends PersonaRelacionada0DTO>> getPersonaRelacionada(FiltroDetallePersonaRelacionadaPaginaOrden filtro);

    ItacaAPIVoidResponse deletePersonaRelacionada(Long idPersonaRelacionada) throws PersonaRelacionadaNoExisteException;

    ItacaAPIResponse<List<? extends DetallePersona1DTO>> buscarPersonaPorIdentificador(String codigoIdentificacion);


    // public person

    ItacaAPIResponse<List<? extends PersonaPublica0DTO>> getPersonasPublicas(FiltroPersonaPublicaPaginaOrden peticion);

    ItacaAPIResponse<PersonaPublica0DTO> saveOrUpdatePersonaPublica(PersonaPublica0DTO personaPublica);

    ItacaAPIResponse<Long> countPersonaPublica(Long idPersona);



    // Regime

    ItacaAPIResponse<Long> countRegimenFiscal(Long idPersona);

    ItacaAPIVoidResponse deleteRegimenFiscal(Long idRegimenFiscal) throws RegimenFiscalNoExisteException;

    ItacaAPIResponse<List<? extends RelDetPersonaRegFiscal0DTO>> getRegimenFiscalPersona(FiltroDetallePersonaRegimenFiscalPaginaOrden filtro);

    ItacaAPIResponse<RelDetPersonaRegFiscal0DTO> saveOrUpdateRegFiscal(RelDetPersonaRegFiscal0DTO regFiscal);


    // lookups

    ItacaAPIResponse<List<EstadoPersona0DTO>> getEstadosPersona();

    ItacaAPIResponse<List<EstadoCivil0DTO>> getEstadosCiviles();

    ItacaAPIResponse<List<Genero0DTO>> getGeneros();

    ItacaAPIResponse<List<Idioma0DTO>> getIdiomas();

    ItacaAPIResponse<List<TipoIdentificacion0DTO>> getTiposIdentificacion();

    ItacaAPIResponse<List<TipoPersonaJuridica0DTO>> getTiposPersonaJuridica();

    ItacaAPIResponse<List<TipoContacto0DTO>> getTiposContacto();

    ItacaAPIResponse<List<TipoRelacion0DTO>> getTiposRelacion();

    ItacaAPIResponse<List<TipoPersonaPublica0DTO>> getTiposPersonaPublica();

    ItacaAPIResponse<DetallePersona1DTO> sincronizarMetadatos(Long id);
    */
}
