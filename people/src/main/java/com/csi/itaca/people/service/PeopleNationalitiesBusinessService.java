package com.csi.itaca.people.service;

import com.csi.itaca.people.model.dto.NationalityDTO;
import com.csi.itaca.tools.utils.jpa.Order;
import com.csi.itaca.tools.utils.jpa.Pagination;
import org.springframework.validation.Errors;

import java.util.List;

public interface PeopleNationalitiesBusinessService {

	List<NationalityDTO> getPeopleNationalities(Long personDetailId);
	List<NationalityDTO> getPeopleNationalities(Long personDetail, Pagination pagination, Order order);
	boolean deleteNationality(Long idNationality, Errors errTracking);
	NationalityDTO getNationality(Long idNationality, Errors errTracking);
	NationalityDTO saveOrUpdateNationality(NationalityDTO NationalityDTO, Errors errTracking);
	Long countNationalities(Long personDetailId);

}