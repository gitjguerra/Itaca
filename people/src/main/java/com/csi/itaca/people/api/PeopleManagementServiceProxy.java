package com.csi.itaca.people.api;

import com.csi.itaca.people.model.dto.GenderDTO;
import com.csi.itaca.people.model.dto.PersonDTO;
import com.csi.itaca.people.model.filters.PeopleSearchFilter;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface PeopleManagementServiceProxy {

    // Parameters...
    String ID_PARAM                 = "id";
    String EXT_REF_PARAM            = "extRefCode";

    // End point URLs...
    String RESOURCE                 = "/people";
    String GET_PERSON               = RESOURCE + "/get";
    String SEARCH_PEOPLE            = RESOURCE + "/search";
    String SAVE_PERSON              = RESOURCE + "/save";
    String DELETE_PERSON            = RESOURCE + "/delete";
    String EXT_REF_EXISTS           = RESOURCE + "/extRefExists";


    // Lookup end point URLs...
    String LOOKUP                   = "/lookup";
    String LOOKUP_CIVIL_STATUS      = RESOURCE + LOOKUP +"/civilStatus";
    String LOOKUP_PERSON_STATUS     = RESOURCE + LOOKUP +"/personStatus";
    String LOOKUP_GENDER            = RESOURCE + LOOKUP +"/gender";
    String LOOKUP_LANGUAGES         = RESOURCE + LOOKUP +"/languages";
    String LOOKUP_ID_TYPES          = RESOURCE + LOOKUP +"/idTypes";
    String LOOKUP_COMPANY_TYPES     = RESOURCE + LOOKUP +"/companyTypes";

    /**
     * Gets a person.
     * @param id the person id.
     * @return a response body containing the requested person json object.
     */
    ResponseEntity getPerson(Long id);

    /**
     * Find a person based on the suppled key.
     * @param filter the filter to apply.
     * @param errTracking error tracking.
     * @return a list containing 1 more people depend upon the configuration. Will return an empty list if
     * no people were found.
     */
    ResponseEntity findPeople(PeopleSearchFilter filter, BindingResult errTracking);

    /**
     * Saves or updates person.
     * @param personToSaveOrUpdate the person to save/update.
     * @param errTracking error tracking.
     */
    ResponseEntity saveOrUpdatePerson(PersonDTO personToSaveOrUpdate,
                                      BindingResult errTracking);

    /**
     * Deletes a person.
     * @param id the id of the person to delete.
     * @return status ok response if the delete was successful.
     */
    ResponseEntity deletePerson(Long id);

    /**
     * Checks if provided external reference code is in use
     * @param externalReferenceCode external reference code to check.
     * @return true if provided external reference code exists, otherwise false.
     */
    ResponseEntity<Boolean> checkExternalReferenceExists(String externalReferenceCode);


    //////////////////////// Lookups ...

    /** @return a list of civil statuses*/
    ResponseEntity<List<GenderDTO>> lookupCivilStatus();

    /** @return a list of person statuses*/
    ResponseEntity<List<GenderDTO>> lookupPersonStatus();

    /** @return a list of genders.*/
    ResponseEntity<List<GenderDTO>> lookupGender();

    /** @return a list of languages.*/
    ResponseEntity<List<GenderDTO>> lookupLanguages();

    /** @return a list of idTypes.*/
    ResponseEntity<List<GenderDTO>> lookupIdTypes();

    /** @return a list of company types.*/
    ResponseEntity<List<GenderDTO>> lookupCompanyTypes();



    /*
    ItacaAPIResponse<List<? extends DetallePersona1DTO>> buscarDetallesPersonasDuplicadas(FiltroPersonasPaginaOrden peticion);

    ItacaAPIResponse<Long> countDetallesPersonasDuplicadas(FiltroBuscadorAltaPersonaDTO parametros);

    ItacaAPIResponse<List<? extends DetallePersona1DTO>> listDetallePersonas(FiltroDetallePersonaPaginaOrden peticion);

    ItacaAPIResponse<Long> countDetallePersonas(FiltroBuscadorPersonaDTO filtro);

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

    ItacaAPIResponse<List<TipoContacto0DTO>> getTiposContacto();

    ItacaAPIResponse<List<TipoRelacion0DTO>> getTiposRelacion();

    ItacaAPIResponse<List<TipoPersonaPublica0DTO>> getTiposPersonaPublica();

    ItacaAPIResponse<DetallePersona1DTO> sincronizarMetadatos(Long id);
    */
}
